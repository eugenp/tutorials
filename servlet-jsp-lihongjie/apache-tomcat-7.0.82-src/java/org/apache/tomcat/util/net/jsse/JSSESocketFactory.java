/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.tomcat.util.net.jsse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPathParameters;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CertSelector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.CertPathTrustManagerParameters;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;

import org.apache.tomcat.util.compat.JreCompat;
import org.apache.tomcat.util.compat.JreVendor;
import org.apache.tomcat.util.file.ConfigFileLoader;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.net.Constants;
import org.apache.tomcat.util.net.SSLUtil;
import org.apache.tomcat.util.net.ServerSocketFactory;
import org.apache.tomcat.util.res.StringManager;

/**
 * SSL server socket factory. It <b>requires</b> a valid RSA key and
 * JSSE.<br>
 * keytool -genkey -alias tomcat -keyalg RSA</br>
 * Use "changeit" as password (this is the default we use).
 *
 * @author Harish Prabandham
 * @author Costin Manolache
 * @author Stefan Freyr Stefansson
 * @author EKR -- renamed to JSSESocketFactory
 * @author Jan Luehe
 * @author Bill Barker
 */
public class JSSESocketFactory implements ServerSocketFactory, SSLUtil {

    private static final org.apache.juli.logging.Log log =
        org.apache.juli.logging.LogFactory.getLog(JSSESocketFactory.class);
    private static final StringManager sm =
        StringManager.getManager("org.apache.tomcat.util.net.jsse.res");

    // Defaults - made public where re-used
    private static final String defaultProtocol = Constants.SSL_PROTO_TLS;
    private static final String defaultKeystoreType = "JKS";
    private static final String defaultKeystoreFile
        = System.getProperty("user.home") + "/.keystore";
    private static final int defaultSessionCacheSize = 0;
    private static final int defaultSessionTimeout = 86400;
    private static final String ALLOW_ALL_SUPPORTED_CIPHERS = "ALL";
    public static final String DEFAULT_KEY_PASS = "changeit";

    private AbstractEndpoint<?> endpoint;

    private final boolean rfc5746Supported;
    private final String[] defaultServerProtocols;
    private final String[] defaultServerCipherSuites;

    protected SSLServerSocketFactory sslProxy = null;
    protected String[] enabledCiphers;
    protected String[] enabledProtocols;
    protected boolean allowUnsafeLegacyRenegotiation = false;

    /**
     * Flag to state that we require client authentication.
     */
    protected boolean requireClientAuth = false;

    /**
     * Flag to state that we would like client authentication.
     */
    protected boolean wantClientAuth    = false;


    public JSSESocketFactory (AbstractEndpoint<?> endpoint) {
        this.endpoint = endpoint;

        String sslProtocol = endpoint.getSslProtocol();
        if (sslProtocol == null) {
            sslProtocol = defaultProtocol;
        }

        SSLContext context;
        try {
             context = SSLContext.getInstance(sslProtocol);
             context.init(null,  null,  null);
        } catch (NoSuchAlgorithmException e) {
            // This is fatal for the connector so throw an exception to prevent
            // it from starting
            throw new IllegalArgumentException(e);
        } catch (KeyManagementException e) {
            // This is fatal for the connector so throw an exception to prevent
            // it from starting
            throw new IllegalArgumentException(e);
        }

        // Supported cipher suites aren't accessible directly from the
        // SSLContext so use the SSL server socket factory
        SSLServerSocketFactory ssf = context.getServerSocketFactory();
        String supportedCiphers[] = ssf.getSupportedCipherSuites();
        boolean found = false;
        for (String cipher : supportedCiphers) {
            if ("TLS_EMPTY_RENEGOTIATION_INFO_SCSV".equals(cipher)) {
                found = true;
                break;
            }
        }
        rfc5746Supported = found;

        // There is no standard way to determine the default protocols and
        // cipher suites so create a server socket to see what the defaults are
        SSLServerSocket socket;
        try {
            socket = (SSLServerSocket) ssf.createServerSocket();
        } catch (IOException e) {
            // This is very likely to be fatal but there is a slim chance that
            // the JSSE implementation just doesn't like creating unbound
            // sockets so allow the code to proceed.
            defaultServerCipherSuites = new String[0];
            defaultServerProtocols = new String[0];
            log.warn(sm.getString("jsse.noDefaultCiphers", endpoint.getName()));
            log.warn(sm.getString("jsse.noDefaultProtocols", endpoint.getName()));
            return;
        }

        try {
            // Many of the default ciphers supported by older JRE versions are
            // now considered insecure. This code attempts to filter them out
            List<String> filteredCiphers = new ArrayList<String>();
            for (String cipher : socket.getEnabledCipherSuites()) {
                // Remove export ciphers - FREAK
                if (cipher.toUpperCase(Locale.ENGLISH).contains("EXP")) {
                    log.debug(sm.getString("jsse.excludeDefaultCipher", cipher));
                    continue;
                }
                // Remove DES ciphers
                if (cipher.toUpperCase(Locale.ENGLISH).contains("_DES_")) {
                    log.debug(sm.getString("jsse.excludeDefaultCipher", cipher));
                    continue;
                }
                // Remove RC4 ciphers
                if (cipher.toUpperCase(Locale.ENGLISH).contains("_RC4_")) {
                    log.debug(sm.getString("jsse.excludeDefaultCipher", cipher));
                    continue;
                }
                // Remove DHE ciphers unless running on Java 8 or above 
                if (!JreCompat.isJre8Available() &&
                        cipher.toUpperCase(Locale.ENGLISH).contains("_DHE_")) {
                    log.debug(sm.getString("jsse.excludeDefaultCipher", cipher));
                    continue;
                }
                // Remove kRSA ciphers when running on Java 7 or above. Can't
                // remove them for Java 6 since they are likely to be the only
                // ones left
                if (JreCompat.isJre7Available() &&
                        (cipher.toUpperCase(Locale.ENGLISH).startsWith("TLS_RSA_") ||
                         cipher.toUpperCase(Locale.ENGLISH).startsWith("SSL_RSA_"))) {
                    log.debug(sm.getString("jsse.excludeDefaultCipher", cipher));
                    continue;
                }
                filteredCiphers.add(cipher);
            }
    
            defaultServerCipherSuites = filteredCiphers.toArray(new String[filteredCiphers.size()]);
            if (defaultServerCipherSuites.length == 0) {
                log.warn(sm.getString("jsse.noDefaultCiphers", endpoint.getName()));
            }

            // Filter out all the SSL protocols (SSLv2 and SSLv3) from the defaults
            // since they are no longer considered secure
            List<String> filteredProtocols = new ArrayList<String>();
            for (String protocol : socket.getEnabledProtocols()) {
                if (protocol.toUpperCase(Locale.ENGLISH).contains("SSL")) {
                    log.debug(sm.getString("jsse.excludeDefaultProtocol", protocol));
                    continue;
                }
                filteredProtocols.add(protocol);
            }
            defaultServerProtocols = filteredProtocols.toArray(new String[filteredProtocols.size()]);
            if (defaultServerProtocols.length == 0) {
                log.warn(sm.getString("jsse.noDefaultProtocols", endpoint.getName()));
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log.warn(sm.getString("jsse.exceptionOnClose"), e);
            }
        }
    }


    @Override
    public ServerSocket createSocket (int port)
        throws IOException
    {
        init();
        ServerSocket socket = sslProxy.createServerSocket(port);
        initServerSocket(socket);
        return socket;
    }

    @Override
    public ServerSocket createSocket (int port, int backlog)
        throws IOException
    {
        init();
        ServerSocket socket = sslProxy.createServerSocket(port, backlog);
        initServerSocket(socket);
        return socket;
    }

    @Override
    public ServerSocket createSocket (int port, int backlog,
                                      InetAddress ifAddress)
        throws IOException
    {
        init();
        ServerSocket socket = sslProxy.createServerSocket(port, backlog,
                                                          ifAddress);
        initServerSocket(socket);
        return socket;
    }

    @Override
    public Socket acceptSocket(ServerSocket socket)
        throws IOException
    {
        SSLSocket asock = null;
        try {
             asock = (SSLSocket)socket.accept();
        } catch (SSLException e){
          throw new SocketException("SSL handshake error" + e.toString());
        }
        return asock;
    }

    @Override
    public void handshake(Socket sock) throws IOException {
        // We do getSession instead of startHandshake() so we can call this multiple times
        SSLSession session = ((SSLSocket)sock).getSession();
        if (session.getCipherSuite().equals("SSL_NULL_WITH_NULL_NULL"))
            throw new IOException("SSL handshake failed. Ciper suite in SSL Session is SSL_NULL_WITH_NULL_NULL");

        if (!allowUnsafeLegacyRenegotiation && !rfc5746Supported) {
            // Prevent further handshakes by removing all cipher suites
            ((SSLSocket) sock).setEnabledCipherSuites(new String[0]);
        }
    }

    @Override
    public String[] getEnableableCiphers(SSLContext context) {
        String requestedCiphersStr = endpoint.getCiphers();

        if (ALLOW_ALL_SUPPORTED_CIPHERS.equals(requestedCiphersStr)) {
            return context.getSupportedSSLParameters().getCipherSuites();
        }
        if ((requestedCiphersStr == null)
                || (requestedCiphersStr.trim().length() == 0)) {
            return defaultServerCipherSuites;
        }

        List<String> requestedCiphers = new ArrayList<String>();
        for (String rc : requestedCiphersStr.split(",")) {
            final String cipher = rc.trim();
            if (cipher.length() > 0) {
                requestedCiphers.add(cipher);
            }
        }
        if (requestedCiphers.isEmpty()) {
            return defaultServerCipherSuites;
        }
        List<String> ciphers = new ArrayList<String>(requestedCiphers);
        String[] supportedCipherSuiteArray = context.getSupportedSSLParameters().getCipherSuites();
        // The IBM JRE will accept cipher suites names SSL_xxx or TLS_xxx but
        // only returns the SSL_xxx form for supported cipher suites. Therefore
        // need to filter the requested cipher suites using both forms with an
        // IBM JRE.
        List<String> supportedCipherSuiteList;
        if (JreVendor.IS_IBM_JVM) {
            supportedCipherSuiteList = new ArrayList<String>(supportedCipherSuiteArray.length * 2);
            for (String name : supportedCipherSuiteArray) {
                supportedCipherSuiteList.add(name);
                if (name.startsWith("SSL")) {
                    supportedCipherSuiteList.add("TLS" + name.substring(3));
                }
            }
        } else {
            supportedCipherSuiteList = Arrays.asList(supportedCipherSuiteArray);
        }
        ciphers.retainAll(supportedCipherSuiteList);

        if (ciphers.isEmpty()) {
            log.warn(sm.getString("jsse.requested_ciphers_not_supported",
                    requestedCiphersStr));
        }
        if (log.isDebugEnabled()) {
            log.debug(sm.getString("jsse.enableable_ciphers", ciphers));
            if (ciphers.size() != requestedCiphers.size()) {
                List<String> skipped = new ArrayList<String>(requestedCiphers);
                skipped.removeAll(ciphers);
                log.debug(sm.getString("jsse.unsupported_ciphers", skipped));
            }
        }

        return ciphers.toArray(new String[ciphers.size()]);
    }

    /*
     * Gets the SSL server's keystore password.
     */
    protected String getKeystorePassword() {
        String keystorePass = endpoint.getKeystorePass();
        if (keystorePass == null) {
            keystorePass = endpoint.getKeyPass();
        }
        if (keystorePass == null) {
            keystorePass = DEFAULT_KEY_PASS;
        }
        return keystorePass;
    }

    /*
     * Gets the SSL server's keystore.
     */
    protected KeyStore getKeystore(String type, String provider, String pass)
            throws IOException {

        String keystoreFile = endpoint.getKeystoreFile();
        if (keystoreFile == null)
            keystoreFile = defaultKeystoreFile;

        return getStore(type, provider, keystoreFile, pass);
    }

    /*
     * Gets the SSL server's truststore.
     */
    protected KeyStore getTrustStore(String keystoreType,
            String keystoreProvider) throws IOException {
        KeyStore trustStore = null;

        String truststoreFile = endpoint.getTruststoreFile();
        if(truststoreFile == null) {
            truststoreFile = System.getProperty("javax.net.ssl.trustStore");
        }
        if(log.isDebugEnabled()) {
            log.debug("Truststore = " + truststoreFile);
        }

        String truststorePassword = endpoint.getTruststorePass();
        if( truststorePassword == null) {
            truststorePassword =
                System.getProperty("javax.net.ssl.trustStorePassword");
        }
        if(log.isDebugEnabled()) {
            log.debug("TrustPass = " + truststorePassword);
        }

        String truststoreType = endpoint.getTruststoreType();
        if( truststoreType == null) {
            truststoreType = System.getProperty("javax.net.ssl.trustStoreType");
        }
        if(truststoreType == null) {
            truststoreType = keystoreType;
        }
        if(log.isDebugEnabled()) {
            log.debug("trustType = " + truststoreType);
        }

        String truststoreProvider = endpoint.getTruststoreProvider();
        if( truststoreProvider == null) {
            truststoreProvider =
                System.getProperty("javax.net.ssl.trustStoreProvider");
        }
        if (truststoreProvider == null) {
            truststoreProvider = keystoreProvider;
        }
        if(log.isDebugEnabled()) {
            log.debug("trustProvider = " + truststoreProvider);
        }

        if (truststoreFile != null){
            try {
                trustStore = getStore(truststoreType, truststoreProvider,
                        truststoreFile, truststorePassword);
            } catch (IOException ioe) {
                Throwable cause = ioe.getCause();
                if (cause instanceof UnrecoverableKeyException) {
                    // Log a warning we had a password issue
                    log.warn(sm.getString("jsse.invalid_truststore_password"),
                            cause);
                    // Re-try
                    trustStore = getStore(truststoreType, truststoreProvider,
                            truststoreFile, null);
                } else {
                    // Something else went wrong - re-throw
                    throw ioe;
                }
            }
        }

        return trustStore;
    }

    /*
     * Gets the key- or truststore with the specified type, path, and password.
     */
    private KeyStore getStore(String type, String provider, String path,
            String pass) throws IOException {

        KeyStore ks = null;
        InputStream istream = null;
        try {
            if (provider == null) {
                ks = KeyStore.getInstance(type);
            } else {
                ks = KeyStore.getInstance(type, provider);
            }
            if(!("PKCS11".equalsIgnoreCase(type) ||
                    "".equalsIgnoreCase(path))) {
                istream = ConfigFileLoader.getInputStream(path);
            }

            char[] storePass = null;
            if (pass != null && !"".equals(pass)) {
                storePass = pass.toCharArray();
            }
            ks.load(istream, storePass);
        } catch (FileNotFoundException fnfe) {
            log.error(sm.getString("jsse.keystore_load_failed", type, path,
                    fnfe.getMessage()), fnfe);
            throw fnfe;
        } catch (IOException ioe) {
            // May be expected when working with a trust store
            // Re-throw. Caller will catch and log as required
            throw ioe;
        } catch(Exception ex) {
            String msg = sm.getString("jsse.keystore_load_failed", type, path,
                    ex.getMessage());
            log.error(msg, ex);
            throw new IOException(msg);
        } finally {
            if (istream != null) {
                try {
                    istream.close();
                } catch (IOException ioe) {
                    // Do nothing
                }
            }
        }

        return ks;
    }

    /**
     * Reads the keystore and initializes the SSL socket factory.
     */
    void init() throws IOException {
        try {

            String clientAuthStr = endpoint.getClientAuth();
            if("true".equalsIgnoreCase(clientAuthStr) ||
               "yes".equalsIgnoreCase(clientAuthStr)) {
                requireClientAuth = true;
            } else if("want".equalsIgnoreCase(clientAuthStr)) {
                wantClientAuth = true;
            }

            SSLContext context = createSSLContext();
            context.init(getKeyManagers(), getTrustManagers(), null);

            // Configure SSL session cache
            SSLSessionContext sessionContext =
                context.getServerSessionContext();
            if (sessionContext != null) {
                configureSessionContext(sessionContext);
            }

            // create proxy
            sslProxy = context.getServerSocketFactory();

            // Determine which cipher suites to enable
            enabledCiphers = getEnableableCiphers(context);
            enabledProtocols = getEnableableProtocols(context);

            allowUnsafeLegacyRenegotiation = "true".equals(
                    endpoint.getAllowUnsafeLegacyRenegotiation());

            // Check the SSL config is OK
            checkConfig();

        } catch(Exception e) {
            if( e instanceof IOException )
                throw (IOException)e;
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public SSLContext createSSLContext() throws Exception {

        // SSL protocol variant (e.g., TLS, SSL v3, etc.)
        String protocol = endpoint.getSslProtocol();
        if (protocol == null) {
            protocol = defaultProtocol;
        }

        SSLContext context = SSLContext.getInstance(protocol);

        return context;
    }

    @Override
    public KeyManager[] getKeyManagers() throws Exception {
        String keystoreType = endpoint.getKeystoreType();
        if (keystoreType == null) {
            keystoreType = defaultKeystoreType;
        }

        String algorithm = endpoint.getAlgorithm();
        if (algorithm == null) {
            algorithm = KeyManagerFactory.getDefaultAlgorithm();
        }

        return getKeyManagers(keystoreType, endpoint.getKeystoreProvider(),
                algorithm, endpoint.getKeyAlias());
    }

    @Override
    public TrustManager[] getTrustManagers() throws Exception {
        String truststoreType = endpoint.getTruststoreType();
        if (truststoreType == null) {
            truststoreType = System.getProperty("javax.net.ssl.trustStoreType");
        }
        if (truststoreType == null) {
            truststoreType = endpoint.getKeystoreType();
        }
        if (truststoreType == null) {
            truststoreType = defaultKeystoreType;
        }

        String algorithm = endpoint.getTruststoreAlgorithm();
        if (algorithm == null) {
            algorithm = TrustManagerFactory.getDefaultAlgorithm();
        }

        return getTrustManagers(truststoreType, endpoint.getKeystoreProvider(),
                algorithm);
    }

    @Override
    public void configureSessionContext(SSLSessionContext sslSessionContext) {
        int sessionCacheSize;
        if (endpoint.getSessionCacheSize() != null) {
            sessionCacheSize = Integer.parseInt(
                    endpoint.getSessionCacheSize());
        } else {
            sessionCacheSize = defaultSessionCacheSize;
        }

        int sessionTimeout;
        if (endpoint.getSessionTimeout() != null) {
            sessionTimeout = Integer.parseInt(endpoint.getSessionTimeout());
        } else {
            sessionTimeout = defaultSessionTimeout;
        }

        sslSessionContext.setSessionCacheSize(sessionCacheSize);
        sslSessionContext.setSessionTimeout(sessionTimeout);
    }

    /**
     * Gets the initialized key managers.
     */
    protected KeyManager[] getKeyManagers(String keystoreType,
                                          String keystoreProvider,
                                          String algorithm,
                                          String keyAlias)
                throws Exception {

        KeyManager[] kms = null;

        String keystorePass = getKeystorePassword();

        KeyStore ks = getKeystore(keystoreType, keystoreProvider, keystorePass);
        if (keyAlias != null && !ks.isKeyEntry(keyAlias)) {
            throw new IOException(
                    sm.getString("jsse.alias_no_key_entry", keyAlias));
        }

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
        String keyPass = endpoint.getKeyPass();
        if (keyPass == null) {
            keyPass = keystorePass;
        }
        kmf.init(ks, keyPass.toCharArray());

        kms = kmf.getKeyManagers();
        if (keyAlias != null) {
            String alias = keyAlias;
            if (JSSESocketFactory.defaultKeystoreType.equals(keystoreType)) {
                alias = alias.toLowerCase(Locale.ENGLISH);
            }
            for(int i=0; i<kms.length; i++) {
                kms[i] = new JSSEKeyManager((X509KeyManager)kms[i], alias);
            }
        }

        return kms;
    }

    /**
     * Gets the initialized trust managers.
     */
    protected TrustManager[] getTrustManagers(String keystoreType,
            String keystoreProvider, String algorithm)
        throws Exception {
        String crlf = endpoint.getCrlFile();

        String className = endpoint.getTrustManagerClassName();
        if(className != null && className.length() > 0) {
             ClassLoader classLoader = getClass().getClassLoader();
             Class<?> clazz = classLoader.loadClass(className);
             if(!(TrustManager.class.isAssignableFrom(clazz))){
                throw new InstantiationException(sm.getString(
                        "jsse.invalidTrustManagerClassName", className));
             }
             Object trustManagerObject = clazz.newInstance();
             TrustManager trustManager = (TrustManager) trustManagerObject;
             return new TrustManager[]{ trustManager };
        }

        TrustManager[] tms = null;

        KeyStore trustStore = getTrustStore(keystoreType, keystoreProvider);
        if (trustStore != null || endpoint.getTrustManagerClassName() != null) {
            if (crlf == null) {
                TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance(algorithm);
                tmf.init(trustStore);
                tms = tmf.getTrustManagers();
            } else {
                TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance(algorithm);
                CertPathParameters params =
                    getParameters(algorithm, crlf, trustStore);
                ManagerFactoryParameters mfp =
                    new CertPathTrustManagerParameters(params);
                tmf.init(mfp);
                tms = tmf.getTrustManagers();
            }
        }

        return tms;
    }

    /**
     * Return the initialization parameters for the TrustManager.
     * Currently, only the default <code>PKIX</code> is supported.
     *
     * @param algorithm The algorithm to get parameters for.
     * @param crlf The path to the CRL file.
     * @param trustStore The configured TrustStore.
     * @return The parameters including the CRLs and TrustStore.
     */
    protected CertPathParameters getParameters(String algorithm,
                                                String crlf,
                                                KeyStore trustStore)
        throws Exception {
        CertPathParameters params = null;
        if("PKIX".equalsIgnoreCase(algorithm)) {
            PKIXBuilderParameters xparams =
                new PKIXBuilderParameters(trustStore, new X509CertSelector());
            Collection<? extends CRL> crls = getCRLs(crlf);
            CertStoreParameters csp = new CollectionCertStoreParameters(crls);
            CertStore store = CertStore.getInstance("Collection", csp);
            xparams.addCertStore(store);
            xparams.setRevocationEnabled(true);
            String trustLength = endpoint.getTrustMaxCertLength();
            if(trustLength != null) {
                try {
                    xparams.setMaxPathLength(Integer.parseInt(trustLength));
                } catch(Exception ex) {
                    log.warn("Bad maxCertLength: "+trustLength);
                }
            }

            params = xparams;
        } else {
            throw new CRLException("CRLs not supported for type: "+algorithm);
        }
        return params;
    }


    /**
     * Load the collection of CRLs.
     *
     */
    protected Collection<? extends CRL> getCRLs(String crlf)
        throws IOException, CRLException, CertificateException {

        Collection<? extends CRL> crls = null;
        InputStream is = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            is = ConfigFileLoader.getInputStream(crlf);
            crls = cf.generateCRLs(is);
        } catch(IOException iex) {
            throw iex;
        } catch(CRLException crle) {
            throw crle;
        } catch(CertificateException ce) {
            throw ce;
        } finally {
            if(is != null) {
                try{
                    is.close();
                } catch(Exception ex) {
                    // Ignore
                }
            }
        }
        return crls;
    }

    @Override
    public String[] getEnableableProtocols(SSLContext context) {
        String[] requestedProtocols = endpoint.getSslEnabledProtocolsArray();
        if ((requestedProtocols == null) || (requestedProtocols.length == 0)) {
            return defaultServerProtocols;
        }

        List<String> protocols = new ArrayList<String>(
                Arrays.asList(requestedProtocols));
        protocols.retainAll(Arrays.asList(context.getSupportedSSLParameters()
                .getProtocols()));

        if (protocols.isEmpty()) {
            log.warn(sm.getString("jsse.requested_protocols_not_supported",
                    Arrays.asList(requestedProtocols)));
        }
        if (log.isDebugEnabled()) {
            log.debug(sm.getString("jsse.enableable_protocols", protocols));
            if (protocols.size() != requestedProtocols.length) {
                List<String> skipped = new ArrayList<String>(
                        Arrays.asList(requestedProtocols));
                skipped.removeAll(protocols);
                log.debug(sm.getString("jsse.unsupported_protocols", skipped));
            }
        }
        return protocols.toArray(new String[protocols.size()]);
    }

    /**
     * Configure Client authentication for this version of JSSE.  The
     * JSSE included in Java 1.4 supports the 'want' value.  Prior
     * versions of JSSE will treat 'want' as 'false'.
     * @param socket the SSLServerSocket
     */
    protected void configureClientAuth(SSLServerSocket socket){
        if (wantClientAuth){
            socket.setWantClientAuth(wantClientAuth);
        } else {
            socket.setNeedClientAuth(requireClientAuth);
        }
    }

    /**
     * Configures SSLEngine to honor cipher suites ordering based upon
     * endpoint configuration.
     *
     * @throws InvalidAlgorithmParameterException If the runtime JVM doesn't
     *         support this setting.
     */
    protected void configureUseServerCipherSuitesOrder(SSLServerSocket socket) {
        String useServerCipherSuitesOrderStr = endpoint
                .getUseServerCipherSuitesOrder().trim();

        // Only use this feature if the user explicitly requested its use.
        if(!"".equals(useServerCipherSuitesOrderStr)) {
            boolean useServerCipherSuitesOrder =
                    ("true".equalsIgnoreCase(useServerCipherSuitesOrderStr)
                            || "yes".equalsIgnoreCase(useServerCipherSuitesOrderStr));
            JreCompat jreCompat = JreCompat.getInstance();
            jreCompat.setUseServerCipherSuitesOrder(socket, useServerCipherSuitesOrder);
        }
    }

    /**
     * Configures the given SSL server socket with the requested cipher suites,
     * protocol versions, and need for client authentication
     */
    private void initServerSocket(ServerSocket ssocket) {

        SSLServerSocket socket = (SSLServerSocket) ssocket;

        socket.setEnabledCipherSuites(enabledCiphers);
        socket.setEnabledProtocols(enabledProtocols);
        
        // we don't know if client auth is needed -
        // after parsing the request we may re-handshake
        configureClientAuth(socket);
        configureUseServerCipherSuitesOrder(socket);
    }

    /**
     * Checks that the certificate is compatible with the enabled cipher suites.
     * If we don't check now, the JIoEndpoint can enter a nasty logging loop.
     * See bug 45528.
     */
    private void checkConfig() throws IOException {
        // Create an unbound server socket
        ServerSocket socket = sslProxy.createServerSocket();
        initServerSocket(socket);

        try {
            // Set the timeout to 1ms as all we care about is if it throws an
            // SSLException on accept.
            socket.setSoTimeout(1);

            socket.accept();
            // Will never get here - no client can connect to an unbound port
        } catch (SSLException ssle) {
            // SSL configuration is invalid. Possibly cert doesn't match ciphers
            IOException ioe = new IOException(sm.getString(
                    "jsse.invalid_ssl_conf", ssle.getMessage()));
            ioe.initCause(ssle);
            throw ioe;
        } catch (Exception e) {
            /*
             * Possible ways of getting here
             * socket.accept() throws a SecurityException
             * socket.setSoTimeout() throws a SocketException
             * socket.accept() throws some other exception (after a JDK change)
             *      In these cases the test won't work so carry on - essentially
             *      the behaviour before this patch
             * socket.accept() throws a SocketTimeoutException
             *      In this case all is well so carry on
             */
        } finally {
            // Should be open here but just in case
            if (!socket.isClosed()) {
                socket.close();
            }
        }

    }
}
