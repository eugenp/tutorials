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

package org.apache.tomcat.util.net;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jni.Address;
import org.apache.tomcat.jni.Error;
import org.apache.tomcat.jni.File;
import org.apache.tomcat.jni.Library;
import org.apache.tomcat.jni.OS;
import org.apache.tomcat.jni.Poll;
import org.apache.tomcat.jni.Pool;
import org.apache.tomcat.jni.SSL;
import org.apache.tomcat.jni.SSLContext;
import org.apache.tomcat.jni.SSLSocket;
import org.apache.tomcat.jni.Sockaddr;
import org.apache.tomcat.jni.Socket;
import org.apache.tomcat.jni.Status;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.net.AbstractEndpoint.Acceptor.AcceptorState;
import org.apache.tomcat.util.net.AbstractEndpoint.Handler.SocketState;
import org.apache.tomcat.util.security.PrivilegedSetTccl;


/**
 * APR tailored thread pool, providing the following services:
 * <ul>
 * <li>Socket acceptor thread</li>
 * <li>Socket poller thread</li>
 * <li>Sendfile thread</li>
 * <li>Worker threads pool</li>
 * </ul>
 *
 * When switching to Java 5, there's an opportunity to use the virtual
 * machine's thread pool.
 *
 * @author Mladen Turk
 * @author Remy Maucherat
 */
public class AprEndpoint extends AbstractEndpoint<Long> {


    // -------------------------------------------------------------- Constants


    private static final Log log = LogFactory.getLog(AprEndpoint.class);

    protected static final Set<String> SSL_PROTO_ALL = new HashSet<String>();

    static {
        /* Default used if SSLProtocol is not configured, also
           used if SSLProtocol="All" */
        SSL_PROTO_ALL.add(Constants.SSL_PROTO_TLSv1);
        SSL_PROTO_ALL.add(Constants.SSL_PROTO_TLSv1_1);
        SSL_PROTO_ALL.add(Constants.SSL_PROTO_TLSv1_2);
    }

    // ----------------------------------------------------------------- Fields
    /**
     * Root APR memory pool.
     */
    protected long rootPool = 0;


    /**
     * Server socket "pointer".
     */
    protected long serverSock = 0;


    /**
     * APR memory pool for the server socket.
     */
    protected long serverSockPool = 0;


    /**
     * SSL context.
     */
    protected long sslContext = 0;


    protected ConcurrentLinkedQueue<SocketWrapper<Long>> waitingRequests =
        new ConcurrentLinkedQueue<SocketWrapper<Long>>();
    @Override
    public void removeWaitingRequest(SocketWrapper<Long> socketWrapper) {
        waitingRequests.remove(socketWrapper);
    }
    
    
    private final Map<Long,AprSocketWrapper> connections =
            new ConcurrentHashMap<Long, AprSocketWrapper>();

    // ------------------------------------------------------------ Constructor

    public AprEndpoint() {
        // Need to override the default for maxConnections to align it with what
        // was pollerSize (before the two were merged)
        setMaxConnections(8 * 1024);
    }

    // ------------------------------------------------------------- Properties


    /**
     * Defer accept.
     */
    protected boolean deferAccept = true;
    public void setDeferAccept(boolean deferAccept) { this.deferAccept = deferAccept; }
    @Override
    public boolean getDeferAccept() { return deferAccept; }


    /**
     * Size of the sendfile (= concurrent files which can be served).
     */
    protected int sendfileSize = 1 * 1024;
    public void setSendfileSize(int sendfileSize) { this.sendfileSize = sendfileSize; }
    public int getSendfileSize() { return sendfileSize; }


    /**
     * Handling of accepted sockets.
     */
    protected Handler handler = null;
    public void setHandler(Handler handler ) { this.handler = handler; }
    public Handler getHandler() { return handler; }


    /**
     * Poll interval, in microseconds. The smaller the value, the more CPU the poller
     * will use, but the more responsive to activity it will be.
     */
    protected int pollTime = 2000;
    public int getPollTime() { return pollTime; }
    public void setPollTime(int pollTime) { if (pollTime > 0) { this.pollTime = pollTime; } }


    /**
     * Use sendfile for sending static files.
     */
    protected boolean useSendfile = false;
    /*
     * When the endpoint is created and configured, the APR library will not
     * have been initialised. This flag is used to determine if the default
     * value of useSendFile should be changed if the APR library indicates it
     * supports send file once it has been initialised. If useSendFile is set
     * by configuration, that configuration will always take priority.
     */
    private boolean useSendFileSet = false;
    public void setUseSendfile(boolean useSendfile) {
        useSendFileSet = true;
        this.useSendfile = useSendfile;
    }
    @Override
    public boolean getUseSendfile() { return useSendfile; }


    /**
     * Allow comet request handling.
     */
    protected boolean useComet = true;
    public void setUseComet(boolean useComet) { this.useComet = useComet; }
    @Override
    public boolean getUseComet() { return useComet; }
    @Override
    public boolean getUseCometTimeout() { return false; } // Not supported
    @Override
    public boolean getUsePolling() { return true; } // Always supported


    /**
     * Sendfile thread count.
     */
    protected int sendfileThreadCount = 0;
    public void setSendfileThreadCount(int sendfileThreadCount) { this.sendfileThreadCount = sendfileThreadCount; }
    public int getSendfileThreadCount() { return sendfileThreadCount; }


    /**
     * The socket poller.
     */
    protected Poller poller = null;
    public Poller getPoller() {
        return poller;
    }


    /**
     * The socket poller.
     */
    protected AsyncTimeout asyncTimeout = null;
    public AsyncTimeout getAsyncTimeout() {
        return asyncTimeout;
    }


    /**
     * The static file sender.
     */
    protected Sendfile sendfile = null;
    public Sendfile getSendfile() {
        return sendfile;
    }


    /**
     * SSL protocols.
     */
    protected String SSLProtocol = "all";
    public String getSSLProtocol() { return SSLProtocol; }
    public void setSSLProtocol(String SSLProtocol) { this.SSLProtocol = SSLProtocol; }


    /**
     * SSL password (if a cert is encrypted, and no password has been provided, a callback
     * will ask for a password).
     */
    protected String SSLPassword = null;
    public String getSSLPassword() { return SSLPassword; }
    public void setSSLPassword(String SSLPassword) { this.SSLPassword = SSLPassword; }


    /**
     * SSL cipher suite.
     */
    protected String SSLCipherSuite = "HIGH:!aNULL:!eNULL:!EXPORT:!DES:!RC4:!MD5:!kRSA";
    public String getSSLCipherSuite() { return SSLCipherSuite; }
    public void setSSLCipherSuite(String SSLCipherSuite) { this.SSLCipherSuite = SSLCipherSuite; }


    /**
     * SSL certificate file.
     */
    protected String SSLCertificateFile = null;
    public String getSSLCertificateFile() { return SSLCertificateFile; }
    public void setSSLCertificateFile(String SSLCertificateFile) { this.SSLCertificateFile = SSLCertificateFile; }


    /**
     * SSL certificate key file.
     */
    protected String SSLCertificateKeyFile = null;
    public String getSSLCertificateKeyFile() { return SSLCertificateKeyFile; }
    public void setSSLCertificateKeyFile(String SSLCertificateKeyFile) { this.SSLCertificateKeyFile = SSLCertificateKeyFile; }


    /**
     * SSL certificate chain file.
     */
    protected String SSLCertificateChainFile = null;
    public String getSSLCertificateChainFile() { return SSLCertificateChainFile; }
    public void setSSLCertificateChainFile(String SSLCertificateChainFile) { this.SSLCertificateChainFile = SSLCertificateChainFile; }


    /**
     * SSL CA certificate path.
     */
    protected String SSLCACertificatePath = null;
    public String getSSLCACertificatePath() { return SSLCACertificatePath; }
    public void setSSLCACertificatePath(String SSLCACertificatePath) { this.SSLCACertificatePath = SSLCACertificatePath; }


    /**
     * SSL CA certificate file.
     */
    protected String SSLCACertificateFile = null;
    public String getSSLCACertificateFile() { return SSLCACertificateFile; }
    public void setSSLCACertificateFile(String SSLCACertificateFile) { this.SSLCACertificateFile = SSLCACertificateFile; }


    /**
     * SSL CA revocation path.
     */
    protected String SSLCARevocationPath = null;
    public String getSSLCARevocationPath() { return SSLCARevocationPath; }
    public void setSSLCARevocationPath(String SSLCARevocationPath) { this.SSLCARevocationPath = SSLCARevocationPath; }


    /**
     * SSL CA revocation file.
     */
    protected String SSLCARevocationFile = null;
    public String getSSLCARevocationFile() { return SSLCARevocationFile; }
    public void setSSLCARevocationFile(String SSLCARevocationFile) { this.SSLCARevocationFile = SSLCARevocationFile; }


    /**
     * SSL verify client.
     */
    protected String SSLVerifyClient = "none";
    public String getSSLVerifyClient() { return SSLVerifyClient; }
    public void setSSLVerifyClient(String SSLVerifyClient) { this.SSLVerifyClient = SSLVerifyClient; }


    /**
     * SSL verify depth.
     */
    protected int SSLVerifyDepth = 10;
    public int getSSLVerifyDepth() { return SSLVerifyDepth; }
    public void setSSLVerifyDepth(int SSLVerifyDepth) { this.SSLVerifyDepth = SSLVerifyDepth; }


    /**
     * SSL allow insecure renegotiation for the the client that does not
     * support the secure renegotiation.
     */
    protected boolean SSLInsecureRenegotiation = false;
    public void setSSLInsecureRenegotiation(boolean SSLInsecureRenegotiation) { this.SSLInsecureRenegotiation = SSLInsecureRenegotiation; }
    public boolean getSSLInsecureRenegotiation() { return SSLInsecureRenegotiation; }

    protected boolean SSLHonorCipherOrder = false;
    /**
     * Set to <code>true</code> to enforce the <i>server's</i> cipher order
     * instead of the default which is to allow the client to choose a
     * preferred cipher.
     */
    public void setSSLHonorCipherOrder(boolean SSLHonorCipherOrder) { this.SSLHonorCipherOrder = SSLHonorCipherOrder; }
    public boolean getSSLHonorCipherOrder() { return SSLHonorCipherOrder; }

    /**
     * Disables compression of the SSL stream. This thwarts CRIME attack
     * and possibly improves performance by not compressing uncompressible
     * content such as JPEG, etc.
     */
    protected boolean SSLDisableCompression = false;

    /**
     * Set to <code>true</code> to disable SSL compression. This thwarts CRIME
     * attack.
     */
    public void setSSLDisableCompression(boolean SSLDisableCompression) { this.SSLDisableCompression = SSLDisableCompression; }
    public boolean getSSLDisableCompression() { return SSLDisableCompression; }

    /**
     * Port in use.
     */
    @Override
    public int getLocalPort() {
        long s = serverSock;
        if (s == 0) {
            return -1;
        } else {
            long sa;
            try {
                sa = Address.get(Socket.APR_LOCAL, s);
                Sockaddr addr = Address.getInfo(sa);
                return addr.port;
            } catch (Exception e) {
                return -1;
            }
        }
    }


    /**
     * This endpoint does not support <code>-1</code> for unlimited connections,
     * nor does it support setting this attribute while the endpoint is running.
     *
     * {@inheritDoc}
     */
    @Override
    public void setMaxConnections(int maxConnections) {
        if (maxConnections == -1) {
            log.warn(sm.getString("endpoint.apr.maxConnections.unlimited",
                    Integer.valueOf(getMaxConnections())));
            return;
        }
        if (running) {
            log.warn(sm.getString("endpoint.apr.maxConnections.running",
                    Integer.valueOf(getMaxConnections())));
            return;
        }
        super.setMaxConnections(maxConnections);
    }


    // --------------------------------------------------------- Public Methods

    /**
     * Number of keepalive sockets.
     */
    public int getKeepAliveCount() {
        if (poller == null) {
            return 0;
        }

        return poller.getConnectionCount();
    }


    /**
     * Number of sendfile sockets.
     */
    public int getSendfileCount() {
        if (sendfile == null) {
            return 0;
        }

        return sendfile.getSendfileCount();
    }


    // ----------------------------------------------- Public Lifecycle Methods


    /**
     * Initialize the endpoint.
     */
    @Override
    public void bind() throws Exception {

        // Create the root APR memory pool
        try {
            rootPool = Pool.create(0);
        } catch (UnsatisfiedLinkError e) {
            throw new Exception(sm.getString("endpoint.init.notavail"));
        }

        // Create the pool for the server socket
        serverSockPool = Pool.create(rootPool);
        // Create the APR address that will be bound
        String addressStr = null;
        if (getAddress() != null) {
            addressStr = getAddress().getHostAddress();
        }
        int family = Socket.APR_INET;
        if (Library.APR_HAVE_IPV6) {
            if (addressStr == null) {
                if (!OS.IS_BSD && !OS.IS_WIN32 && !OS.IS_WIN64)
                    family = Socket.APR_UNSPEC;
            } else if (addressStr.indexOf(':') >= 0) {
                family = Socket.APR_UNSPEC;
            }
         }

        long inetAddress = Address.info(addressStr, family,
                getPort(), 0, rootPool);
        // Create the APR server socket
        serverSock = Socket.create(Address.getInfo(inetAddress).family,
                Socket.SOCK_STREAM,
                Socket.APR_PROTO_TCP, rootPool);
        if (OS.IS_UNIX) {
            Socket.optSet(serverSock, Socket.APR_SO_REUSEADDR, 1);
        }
        // Deal with the firewalls that tend to drop the inactive sockets
        Socket.optSet(serverSock, Socket.APR_SO_KEEPALIVE, 1);
        // Bind the server socket
        int ret = Socket.bind(serverSock, inetAddress);
        if (ret != 0) {
            throw new Exception(sm.getString("endpoint.init.bind", "" + ret, Error.strerror(ret)));
        }
        // Start listening on the server socket
        ret = Socket.listen(serverSock, getBacklog());
        if (ret != 0) {
            throw new Exception(sm.getString("endpoint.init.listen", "" + ret, Error.strerror(ret)));
        }
        if (OS.IS_WIN32 || OS.IS_WIN64) {
            // On Windows set the reuseaddr flag after the bind/listen
            Socket.optSet(serverSock, Socket.APR_SO_REUSEADDR, 1);
        }

        // Enable Sendfile by default if it has not been configured but usage on
        // systems which don't support it cause major problems
        if (!useSendFileSet) {
            useSendfile = Library.APR_HAS_SENDFILE;
        } else if (useSendfile && !Library.APR_HAS_SENDFILE) {
            useSendfile = false;
        }

        // Initialize thread count default for acceptor
        if (acceptorThreadCount == 0) {
            // FIXME: Doesn't seem to work that well with multiple accept threads
            acceptorThreadCount = 1;
        }

        // Delay accepting of new connections until data is available
        // Only Linux kernels 2.4 + have that implemented
        // on other platforms this call is noop and will return APR_ENOTIMPL.
        if (deferAccept) {
            if (Socket.optSet(serverSock, Socket.APR_TCP_DEFER_ACCEPT, 1) == Status.APR_ENOTIMPL) {
                deferAccept = false;
            }
        }

        // Initialize SSL if needed
        if (isSSLEnabled()) {

            if (SSLCertificateFile == null) {
                // This is required
                throw new Exception(sm.getString("endpoint.apr.noSslCertFile"));
            }

            // SSL protocol
            int value = SSL.SSL_PROTOCOL_NONE;
            if (SSLProtocol == null || SSLProtocol.length() == 0) {
                value = SSL.SSL_PROTOCOL_ALL;
            } else {

                Set<String> protocols = new HashSet<String>();

                // List of protocol names, separated by "+" or "-".
                // Semantics is adding ("+") or removing ("-") from left
                // to right, starting with an empty protocol set.
                // Tokens are individual protocol names or "all" for a
                // default set of supported protocols.

                // Split using a positive lookahead to keep the separator in
                // the capture so we can check which case it is.
                for (String protocol : SSLProtocol.split("(?=[-+])")) {
                    String trimmed = protocol.trim();
                    // Ignore token which only consists of prefix character
                    if (trimmed.length() > 1) {
                        if (trimmed.charAt(0) == '-') {
                            trimmed = trimmed.substring(1).trim();
                            if (trimmed.equalsIgnoreCase(Constants.SSL_PROTO_ALL)) {
                                protocols.removeAll(SSL_PROTO_ALL);
                            } else {
                                protocols.remove(trimmed);
                            }
                        } else {
                            if (trimmed.charAt(0) == '+') {
                                trimmed = trimmed.substring(1).trim();
                            }
                            if (trimmed.equalsIgnoreCase(Constants.SSL_PROTO_ALL)) {
                                protocols.addAll(SSL_PROTO_ALL);
                            } else {
                                protocols.add(trimmed);
                            }
                        }
                    }
                }

                for (String protocol : protocols) {
                    if (Constants.SSL_PROTO_SSLv2.equalsIgnoreCase(protocol)) {
                        value |= SSL.SSL_PROTOCOL_SSLV2;
                    } else if (Constants.SSL_PROTO_SSLv3.equalsIgnoreCase(protocol)) {
                        value |= SSL.SSL_PROTOCOL_SSLV3;
                    } else if (Constants.SSL_PROTO_TLSv1.equalsIgnoreCase(protocol)) {
                        value |= SSL.SSL_PROTOCOL_TLSV1;
                    } else if (Constants.SSL_PROTO_TLSv1_1.equalsIgnoreCase(protocol)) {
                        value |= SSL.SSL_PROTOCOL_TLSV1_1;
                    } else if (Constants.SSL_PROTO_TLSv1_2.equalsIgnoreCase(protocol)) {
                        value |= SSL.SSL_PROTOCOL_TLSV1_2;
                    } else {
                        // Protocol not recognized, fail to start as it is safer than
                        // continuing with the default which might enable more than the
                        // is required
                        throw new Exception(sm.getString(
                                "endpoint.apr.invalidSslProtocol", SSLProtocol));
                    }
                }
            }

            // Create SSL Context
            try {
                sslContext = SSLContext.make(rootPool, value, SSL.SSL_MODE_SERVER);
            } catch (Exception e) {
                // If the sslEngine is disabled on the AprLifecycleListener
                // there will be an Exception here but there is no way to check
                // the AprLifecycleListener settings from here
                throw new Exception(
                        sm.getString("endpoint.apr.failSslContextMake"), e);
            }

            if (SSLInsecureRenegotiation) {
                boolean legacyRenegSupported = false;
                try {
                    legacyRenegSupported = SSL.hasOp(SSL.SSL_OP_ALLOW_UNSAFE_LEGACY_RENEGOTIATION);
                    if (legacyRenegSupported)
                        SSLContext.setOptions(sslContext, SSL.SSL_OP_ALLOW_UNSAFE_LEGACY_RENEGOTIATION);
                } catch (UnsatisfiedLinkError e) {
                    // Ignore
                }
                if (!legacyRenegSupported) {
                    // OpenSSL does not support unsafe legacy renegotiation.
                    log.warn(sm.getString("endpoint.warn.noInsecureReneg",
                                          SSL.versionString()));
                }
            }

            // Set cipher order: client (default) or server
            if (SSLHonorCipherOrder) {
                boolean orderCiphersSupported = false;
                try {
                    orderCiphersSupported = SSL.hasOp(SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                    if (orderCiphersSupported)
                        SSLContext.setOptions(sslContext, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                } catch (UnsatisfiedLinkError e) {
                    // Ignore
                }
                if (!orderCiphersSupported) {
                    // OpenSSL does not support ciphers ordering.
                    log.warn(sm.getString("endpoint.warn.noHonorCipherOrder",
                                          SSL.versionString()));
                }
            }

            // Disable compression if requested
            if (SSLDisableCompression) {
                boolean disableCompressionSupported = false;
                try {
                    disableCompressionSupported = SSL.hasOp(SSL.SSL_OP_NO_COMPRESSION);
                    if (disableCompressionSupported)
                        SSLContext.setOptions(sslContext, SSL.SSL_OP_NO_COMPRESSION);
                } catch (UnsatisfiedLinkError e) {
                    // Ignore
                }
                if (!disableCompressionSupported) {
                    // OpenSSL does not support ciphers ordering.
                    log.warn(sm.getString("endpoint.warn.noDisableCompression",
                                          SSL.versionString()));
                }
            }

            // List the ciphers that the client is permitted to negotiate
            SSLContext.setCipherSuite(sslContext, SSLCipherSuite);
            // Load Server key and certificate
            SSLContext.setCertificate(sslContext, SSLCertificateFile, SSLCertificateKeyFile, SSLPassword, SSL.SSL_AIDX_RSA);
            // Set certificate chain file
            SSLContext.setCertificateChainFile(sslContext, SSLCertificateChainFile, false);
            // Support Client Certificates
            SSLContext.setCACertificate(sslContext, SSLCACertificateFile, SSLCACertificatePath);
            // Set revocation
            SSLContext.setCARevocation(sslContext, SSLCARevocationFile, SSLCARevocationPath);
            // Client certificate verification
            value = SSL.SSL_CVERIFY_NONE;
            if ("optional".equalsIgnoreCase(SSLVerifyClient)) {
                value = SSL.SSL_CVERIFY_OPTIONAL;
            } else if ("require".equalsIgnoreCase(SSLVerifyClient)) {
                value = SSL.SSL_CVERIFY_REQUIRE;
            } else if ("optionalNoCA".equalsIgnoreCase(SSLVerifyClient)) {
                value = SSL.SSL_CVERIFY_OPTIONAL_NO_CA;
            }
            SSLContext.setVerify(sslContext, value, SSLVerifyDepth);
            // For now, sendfile is not supported with SSL
            if (useSendfile) {
                useSendfile = false;
                if (useSendFileSet) {
                    log.warn(sm.getString("endpoint.apr.noSendfileWithSSL"));
                }
            }
        }
    }


    /**
     * Start the APR endpoint, creating acceptor, poller and sendfile threads.
     */
    @Override
    public void startInternal() throws Exception {

        if (!running) {
            running = true;
            paused = false;

            // Create worker collection
            if (getExecutor() == null) {
                createExecutor();
            }

            initializeConnectionLatch();

            // Start poller thread
            poller = new Poller();
            poller.init();
            Thread pollerThread = new Thread(poller, getName() + "-Poller");
            pollerThread.setPriority(threadPriority);
            pollerThread.setDaemon(true);
            pollerThread.start();

            // Start sendfile thread
            if (useSendfile) {
                sendfile = new Sendfile();
                sendfile.init();
                Thread sendfileThread =
                        new Thread(sendfile, getName() + "-Sendfile");
                sendfileThread.setPriority(threadPriority);
                sendfileThread.setDaemon(true);
                sendfileThread.start();
            }

            startAcceptorThreads();

            // Start async timeout thread
            asyncTimeout = new AsyncTimeout();
            Thread timeoutThread = new Thread(asyncTimeout,
                    getName() + "-AsyncTimeout");
            timeoutThread.setPriority(threadPriority);
            timeoutThread.setDaemon(true);
            timeoutThread.start();
        }
    }


    /**
     * Stop the endpoint. This will cause all processing threads to stop.
     */
    @Override
    public void stopInternal() {
        releaseConnectionLatch();
        if (!paused) {
            pause();
        }
        if (running) {
            running = false;
            poller.stop();
            asyncTimeout.stop();
            unlockAccept();
            for (AbstractEndpoint.Acceptor acceptor : acceptors) {
                long waitLeft = 10000;
                while (waitLeft > 0 &&
                        acceptor.getState() != AcceptorState.ENDED &&
                        serverSock != 0) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                    waitLeft -= 50;
                }
                if (waitLeft == 0) {
                    log.warn(sm.getString("endpoint.warn.unlockAcceptorFailed",
                            acceptor.getThreadName()));
                   // If the Acceptor is still running force
                   // the hard socket close.
                   if (serverSock != 0) {
                       Socket.shutdown(serverSock, Socket.APR_SHUTDOWN_READ);
                       serverSock = 0;
                   }
                }
            }
            try {
                poller.destroy();
            } catch (Exception e) {
                // Ignore
            }
            poller = null;
            connections.clear();
            if (useSendfile) {
                try {
                    sendfile.destroy();
                } catch (Exception e) {
                    // Ignore
                }
                sendfile = null;
            }
        }
        shutdownExecutor();
    }


    /**
     * Deallocate APR memory pools, and close server socket.
     */
    @Override
    public void unbind() throws Exception {
        if (running) {
            stop();
        }

        // Destroy pool if it was initialised
        if (serverSockPool != 0) {
            Pool.destroy(serverSockPool);
            serverSockPool = 0;
        }

        // Close server socket if it was initialised
        if (serverSock != 0) {
            Socket.close(serverSock);
            serverSock = 0;
        }

        sslContext = 0;

        // Close all APR memory pools and resources if initialised
        if (rootPool != 0) {
            Pool.destroy(rootPool);
            rootPool = 0;
        }

        handler.recycle();
    }


    // ------------------------------------------------------ Protected Methods

    @Override
    protected AbstractEndpoint.Acceptor createAcceptor() {
        return new Acceptor();
    }


    /**
     * Process the specified connection.
     */
    protected boolean setSocketOptions(long socket) {
        // Process the connection
        int step = 1;
        try {

            // 1: Set socket options: timeout, linger, etc
            if (socketProperties.getSoLingerOn() && socketProperties.getSoLingerTime() >= 0)
                Socket.optSet(socket, Socket.APR_SO_LINGER, socketProperties.getSoLingerTime());
            if (socketProperties.getTcpNoDelay())
                Socket.optSet(socket, Socket.APR_TCP_NODELAY, (socketProperties.getTcpNoDelay() ? 1 : 0));
            Socket.timeoutSet(socket, socketProperties.getSoTimeout() * 1000);

            // 2: SSL handshake
            step = 2;
            if (sslContext != 0) {
                SSLSocket.attach(sslContext, socket);
                if (SSLSocket.handshake(socket) != 0) {
                    if (log.isDebugEnabled()) {
                        log.debug(sm.getString("endpoint.err.handshake") + ": " + SSL.getLastError());
                    }
                    return false;
                }
            }

        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            if (log.isDebugEnabled()) {
                if (step == 2) {
                    log.debug(sm.getString("endpoint.err.handshake"), t);
                } else {
                    log.debug(sm.getString("endpoint.err.unexpected"), t);
                }
            }
            // Tell to close the socket
            return false;
        }
        return true;
    }


    /**
     * Allocate a new poller of the specified size.
     */
    protected long allocatePoller(int size, long pool, int timeout) {
        try {
            return Poll.create(size, pool, 0, timeout * 1000);
        } catch (Error e) {
            if (Status.APR_STATUS_IS_EINVAL(e.getError())) {
                log.info(sm.getString("endpoint.poll.limitedpollsize", "" + size));
                return 0;
            } else {
                log.error(sm.getString("endpoint.poll.initfail"), e);
                return -1;
            }
        }
    }

    /**
     * Process given socket. This is called when the socket has been
     * accepted.
     */
    protected boolean processSocketWithOptions(long socket) {
        try {
            // During shutdown, executor may be null - avoid NPE
            if (running) {
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("endpoint.debug.socket",
                            Long.valueOf(socket)));
                }
                AprSocketWrapper wrapper =
                        new AprSocketWrapper(Long.valueOf(socket));
                wrapper.setKeepAliveLeft(getMaxKeepAliveRequests());
                wrapper.setSecure(isSSLEnabled());
                connections.put(Long.valueOf(socket), wrapper);
                getExecutor().execute(new SocketWithOptionsProcessor(wrapper));
            }
        } catch (RejectedExecutionException x) {
            log.warn("Socket processing request was rejected for:"+socket,x);
            return false;
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            // This means we got an OOM or similar creating a thread, or that
            // the pool and its queue are full
            log.error(sm.getString("endpoint.process.fail"), t);
            return false;
        }
        return true;
    }


    /**
     * Process given socket. Called in non-comet mode, typically keep alive
     * or upgraded protocol.
     */
    public boolean processSocket(long socket, SocketStatus status) {
        try {
            Executor executor = getExecutor();
            if (executor == null) {
                log.warn(sm.getString("endpoint.warn.noExector",
                        Long.valueOf(socket), null));
            } else {
                SocketWrapper<Long> wrapper =
                        connections.get(Long.valueOf(socket));
                // Make sure connection hasn't been closed
                if (wrapper != null) {
                    executor.execute(new SocketProcessor(wrapper, status));
                }
            }
        } catch (RejectedExecutionException x) {
            log.warn("Socket processing request was rejected for:"+socket,x);
            return false;
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            // This means we got an OOM or similar creating a thread, or that
            // the pool and its queue are full
            log.error(sm.getString("endpoint.process.fail"), t);
            return false;
        }
        return true;
    }


    @Override
    public void processSocketAsync(SocketWrapper<Long> socket,
            SocketStatus status) {
        try {
            synchronized (socket) {
                if (waitingRequests.remove(socket)) {
                    SocketProcessor proc = new SocketProcessor(socket, status);
                    ClassLoader loader = Thread.currentThread().getContextClassLoader();
                    try {
                        //threads should not be created by the webapp classloader
                        if (Constants.IS_SECURITY_ENABLED) {
                            PrivilegedAction<Void> pa = new PrivilegedSetTccl(
                                    getClass().getClassLoader());
                            AccessController.doPrivileged(pa);
                        } else {
                            Thread.currentThread().setContextClassLoader(
                                    getClass().getClassLoader());
                        }
                        Executor executor = getExecutor();
                        if (executor == null) {
                            log.warn(sm.getString("endpoint.warn.noExector",
                                    socket, status));
                            return;
                        } else {
                            executor.execute(proc);
                        }
                    } finally {
                        if (Constants.IS_SECURITY_ENABLED) {
                            PrivilegedAction<Void> pa = new PrivilegedSetTccl(loader);
                            AccessController.doPrivileged(pa);
                        } else {
                            Thread.currentThread().setContextClassLoader(loader);
                        }
                    }
                }
            }
        } catch (RejectedExecutionException x) {
            log.warn("Socket processing request was rejected for: "+socket, x);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            // This means we got an OOM or similar creating a thread, or that
            // the pool and its queue are full
            log.error(sm.getString("endpoint.process.fail"), t);
        }
    }

    private void closeSocket(long socket) {
        // If not running the socket will be destroyed by
        // parent pool or acceptor socket.
        // In any case disable double free which would cause JVM core.

        connections.remove(Long.valueOf(socket));

        // While the connector is running, destroySocket() will call
        // countDownConnection(). Once the connector is stopped, the latch is
        // removed so it does not matter that destroySocket() does not call
        // countDownConnection() in that case
        Poller poller = this.poller;
        if (poller != null) {
            if (!poller.close(socket)) {
                destroySocket(socket);
            }
        }
    }

    /*
     * This method should only be called if there is no chance that the socket
     * is currently being used by the Poller. It is generally a bad idea to call
     * this directly from a known error condition.
     */
    private void destroySocket(long socket) {
        connections.remove(Long.valueOf(socket));
        if (log.isDebugEnabled()) {
            String msg = sm.getString("endpoint.debug.destroySocket",
                    Long.valueOf(socket));
            if (log.isTraceEnabled()) {
                log.trace(msg, new Exception());
            } else {
                log.debug(msg);
            }
        }
        // Be VERY careful if you call this method directly. If it is called
        // twice for the same socket the JVM will core. Currently this is only
        // called from Poller.closePollset() to ensure kept alive connections
        // are closed when calling stop() followed by start().
        if (socket != 0) {
            Socket.destroy(socket);
            countDownConnection();
        }
    }

    
    @Override
    protected Log getLog() {
        return log;
    }

    // --------------------------------------------------- Acceptor Inner Class
    /**
     * The background thread that listens for incoming TCP/IP connections and
     * hands them off to an appropriate processor.
     */
    protected class Acceptor extends AbstractEndpoint.Acceptor {

        private final Log log = LogFactory.getLog(AprEndpoint.Acceptor.class);

        @Override
        public void run() {

            int errorDelay = 0;

            // Loop until we receive a shutdown command
            while (running) {

                // Loop if endpoint is paused
                while (paused && running) {
                    state = AcceptorState.PAUSED;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                }

                if (!running) {
                    break;
                }
                state = AcceptorState.RUNNING;

                try {
                    //if we have reached max connections, wait
                    countUpOrAwaitConnection();

                    long socket = 0;
                    try {
                        // Accept the next incoming connection from the server
                        // socket
                        socket = Socket.accept(serverSock);
                        if (log.isDebugEnabled()) {
                            long sa = Address.get(Socket.APR_REMOTE, socket);
                            Sockaddr addr = Address.getInfo(sa);
                            log.debug(sm.getString("endpoint.apr.remoteport",
                                    Long.valueOf(socket),
                                    Long.valueOf(addr.port)));
                        }
                    } catch (Exception e) {
                        //we didn't get a socket
                        countDownConnection();
                        // Introduce delay if necessary
                        errorDelay = handleExceptionWithDelay(errorDelay);
                        // re-throw
                        throw e;
                    }
                    // Successful accept, reset the error delay
                    errorDelay = 0;

                    if (running && !paused) {
                        // Hand this socket off to an appropriate processor
                        if (!processSocketWithOptions(socket)) {
                            // Close socket right away
                            closeSocket(socket);
                        }
                    } else {
                        // Close socket right away
                        // No code path could have added the socket to the
                        // Poller so use destroySocket()
                        destroySocket(socket);
                    }
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    if (running) {
                        String msg = sm.getString("endpoint.accept.fail");
                        if (t instanceof Error) {
                            Error e = (Error) t;
                            if (e.getError() == 233) {
                                // Not an error on HP-UX so log as a warning
                                // so it can be filtered out on that platform
                                // See bug 50273
                                log.warn(msg, t);
                            } else {
                                log.error(msg, t);
                            }
                        } else {
                                log.error(msg, t);
                        }
                    }
                }
                // The processor will recycle itself when it finishes
            }
            state = AcceptorState.ENDED;
        }
    }


    /**
     * Async timeout thread
     */
    protected class AsyncTimeout implements Runnable {

        private volatile boolean asyncTimeoutRunning = true;

        /**
         * The background thread that checks async requests and fires the
         * timeout if there has been no activity.
         */
        @Override
        public void run() {

            // Loop until we receive a shutdown command
            while (asyncTimeoutRunning) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Ignore
                }
                long now = System.currentTimeMillis();
                Iterator<SocketWrapper<Long>> sockets =
                    waitingRequests.iterator();
                while (sockets.hasNext()) {
                    SocketWrapper<Long> socket = sockets.next();
                    if (socket.async) {
                        long access = socket.getLastAccess();
                        if (socket.getTimeout() > 0 &&
                                (now-access)>socket.getTimeout()) {
                            // Prevent multiple timeouts
                            socket.setTimeout(-1);
                            processSocketAsync(socket,SocketStatus.TIMEOUT);
                        }
                    }
                }

                // Loop if endpoint is paused
                while (paused && asyncTimeoutRunning) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                }

            }
        }


        protected void stop() {
            asyncTimeoutRunning = false;
        }
    }


    // -------------------------------------------------- SocketInfo Inner Class

    public static class SocketInfo {
        public long socket;
        public int timeout;
        public int flags;
        public boolean read() {
            return (flags & Poll.APR_POLLIN) == Poll.APR_POLLIN;
        }
        public boolean write() {
            return (flags & Poll.APR_POLLOUT) == Poll.APR_POLLOUT;
        }
        public static int merge(int flag1, int flag2) {
            return ((flag1 & Poll.APR_POLLIN) | (flag2 & Poll.APR_POLLIN))
                | ((flag1 & Poll.APR_POLLOUT) | (flag2 & Poll.APR_POLLOUT));
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Socket: [");
            sb.append(socket);
            sb.append("], timeout: [");
            sb.append(timeout);
            sb.append("], flags: [");
            sb.append(flags);
            return sb.toString();
        }
    }


    // ---------------------------------------------- SocketTimeouts Inner Class

    public static class SocketTimeouts {
        protected int size;

        protected long[] sockets;
        protected long[] timeouts;
        protected int pos = 0;

        public SocketTimeouts(int size) {
            this.size = 0;
            sockets = new long[size];
            timeouts = new long[size];
        }

        public void add(long socket, long timeout) {
            sockets[size] = socket;
            timeouts[size] = timeout;
            size++;
        }

        /**
         * Removes the specified socket from the poller.
         *
         * @return The configured timeout for the socket or zero if the socket
         *         was not in the list of socket timeouts
         */
        public long remove(long socket) {
            long result = 0;
            for (int i = 0; i < size; i++) {
                if (sockets[i] == socket) {
                    result = timeouts[i];
                    sockets[i] = sockets[size - 1];
                    timeouts[i] = timeouts[size - 1];
                    size--;
                    break;
                }
            }
            return result;
        }

        public long check(long date) {
            while (pos < size) {
                if (date >= timeouts[pos]) {
                    long result = sockets[pos];
                    sockets[pos] = sockets[size - 1];
                    timeouts[pos] = timeouts[size - 1];
                    size--;
                    return result;
                }
                pos++;
            }
            pos = 0;
            return 0;
        }

    }


    // -------------------------------------------------- SocketList Inner Class

    public static class SocketList {
        protected int size;
        protected int pos;

        protected long[] sockets;
        protected int[] timeouts;
        protected int[] flags;

        protected SocketInfo info = new SocketInfo();

        public SocketList(int size) {
            this.size = 0;
            pos = 0;
            sockets = new long[size];
            timeouts = new int[size];
            flags = new int[size];
        }

        public int size() {
            return this.size;
        }

        public SocketInfo get() {
            if (pos == size) {
                return null;
            } else {
                info.socket = sockets[pos];
                info.timeout = timeouts[pos];
                info.flags = flags[pos];
                pos++;
                return info;
            }
        }

        public void clear() {
            size = 0;
            pos = 0;
        }

        public boolean add(long socket, int timeout, int flag) {
            if (size == sockets.length) {
                return false;
            } else {
                for (int i = 0; i < size; i++) {
                    if (sockets[i] == socket) {
                        flags[i] = SocketInfo.merge(flags[i], flag);
                        return true;
                    }
                }
                sockets[size] = socket;
                timeouts[size] = timeout;
                flags[size] = flag;
                size++;
                return true;
            }
        }

        public boolean remove(long socket) {
            for (int i = 0; i < size; i++) {
                if (sockets[i] == socket) {
                    sockets[i] = sockets[size - 1];
                    timeouts[i] = timeouts[size - 1];
                    flags[size] = flags[size -1];
                    size--;
                    return true;
                }
            }
            return false;
        }

        public void duplicate(SocketList copy) {
            copy.size = size;
            copy.pos = pos;
            System.arraycopy(sockets, 0, copy.sockets, 0, size);
            System.arraycopy(timeouts, 0, copy.timeouts, 0, size);
            System.arraycopy(flags, 0, copy.flags, 0, size);
        }

    }

    // ------------------------------------------------------ Poller Inner Class

   public class Poller implements Runnable {

        /**
         * Pointers to the pollers.
         */
        protected long[] pollers = null;

        /**
         * Actual poller size.
         */
        protected int actualPollerSize = 0;

        /**
         * Amount of spots left in the poller.
         */
        protected int[] pollerSpace = null;

        /**
         * Amount of low level pollers in use by this poller.
         */
        protected int pollerCount;

        /**
         * Timeout value for the poll call.
         */
        protected int pollerTime;

        /**
         * Variable poller timeout that adjusts depending on how many poll sets
         * are in use so that the total poll time across all poll sets remains
         * equal to pollTime.
         */
        private int nextPollerTime;

        /**
         * Root pool.
         */
        protected long pool = 0;

        /**
         * Socket descriptors.
         */
        protected long[] desc;

        /**
         * List of sockets to be added to the poller.
         */
        protected SocketList addList = null;

        
        /**
         * List of sockets to be closed.
         */
        private SocketList closeList = null;


        /**
         * Structure used for storing timeouts.
         */
        protected SocketTimeouts timeouts = null;


        /**
         * Last run of maintain. Maintain will run approximately once every one
         * second (may be slightly longer between runs).
         */
        protected long lastMaintain = System.currentTimeMillis();


        /**
         * The number of connections currently inside this Poller. The correct
         * operation of the Poller depends on this figure being correct. If it
         * is not, it is possible that the Poller will enter a wait loop where
         * it waits for the next connection to be added to the Poller before it
         * calls poll when it should still be polling existing connections.
         * Although not necessary at the time of writing this comment, it has
         * been implemented as an AtomicInteger to ensure that it remains
         * thread-safe.
         */
        private AtomicInteger connectionCount = new AtomicInteger(0);
        public int getConnectionCount() { return connectionCount.get(); }


        private volatile boolean pollerRunning = true;

        /**
         * Create the poller. With some versions of APR, the maximum poller size
         * will be 62 (recompiling APR is necessary to remove this limitation).
         */
        protected void init() {

            pool = Pool.create(serverSockPool);

            // Single poller by default
            int defaultPollerSize = getMaxConnections();

            if ((OS.IS_WIN32 || OS.IS_WIN64) && (defaultPollerSize > 1024)) {
                // The maximum per poller to get reasonable performance is 1024
                // Adjust poller size so that it won't reach the limit. This is
                // a limitation of XP / Server 2003 that has been fixed in
                // Vista / Server 2008 onwards.
                actualPollerSize = 1024;
            } else {
                actualPollerSize = defaultPollerSize;
            }

            timeouts = new SocketTimeouts(defaultPollerSize);

            // At the moment, setting the timeout is useless, but it could get
            // used again as the normal poller could be faster using maintain.
            // It might not be worth bothering though.
            long pollset = allocatePoller(actualPollerSize, pool, -1);
            if (pollset == 0 && actualPollerSize > 1024) {
                actualPollerSize = 1024;
                pollset = allocatePoller(actualPollerSize, pool, -1);
            }
            if (pollset == 0) {
                actualPollerSize = 62;
                pollset = allocatePoller(actualPollerSize, pool, -1);
            }

            pollerCount = defaultPollerSize / actualPollerSize;
            pollerTime = pollTime / pollerCount;
            nextPollerTime = pollerTime;

            pollers = new long[pollerCount];
            pollers[0] = pollset;
            for (int i = 1; i < pollerCount; i++) {
                pollers[i] = allocatePoller(actualPollerSize, pool, -1);
            }

            pollerSpace = new int[pollerCount];
            for (int i = 0; i < pollerCount; i++) {
                pollerSpace[i] = actualPollerSize;
            }

            desc = new long[actualPollerSize * 2];
            connectionCount.set(0);
            addList = new SocketList(defaultPollerSize);
            closeList = new SocketList(defaultPollerSize);
        }


        /*
         * This method is synchronized so that it is not possible for a socket
         * to be added to the Poller's addList once this method has completed.
         */
        protected synchronized void stop() {
            pollerRunning = false;
        }


        /**
         * Destroy the poller.
         */
        protected void destroy() {
            // Wait for pollerTime before doing anything, so that the poller
            // threads exit, otherwise parallel destruction of sockets which are
            // still in the poller can cause problems
            try {
                synchronized (this) {
                    this.notify();
                    this.wait(pollTime / 1000);
                }
            } catch (InterruptedException e) {
                // Ignore
            }
            // Close all sockets in the add queue
            SocketInfo info = addList.get();
            while (info != null) {
                boolean comet =
                        connections.get(Long.valueOf(info.socket)).isComet();
                if (!comet || !processSocket(info.socket, SocketStatus.STOP)) {
                    // Poller isn't running at this point so use destroySocket()
                    // directly
                    destroySocket(info.socket);
                }
                info = addList.get();
            }
            addList.clear();
            // Close all sockets still in the poller
            for (int i = 0; i < pollerCount; i++) {
                int rv = Poll.pollset(pollers[i], desc);
                if (rv > 0) {
                    for (int n = 0; n < rv; n++) {
                        boolean comet = connections.get(
                                Long.valueOf(desc[n*2+1])).isComet();
                        if (!comet || !processSocket(desc[n*2+1], SocketStatus.STOP)) {
                            destroySocket(desc[n*2+1]);
                        }
                    }
                }
            }
            Pool.destroy(pool);
            connectionCount.set(0);
        }


        /**
         * Add specified socket and associated pool to the poller. The socket
         * will be added to a temporary array, and polled first after a maximum
         * amount of time equal to pollTime (in most cases, latency will be much
         * lower, however). Note: If both read and write are false, the socket
         * will only be checked for timeout; if the socket was already present
         * in the poller, a callback event will be generated and the socket will
         * be removed from the poller.
         *
         * @param socket to add to the poller
         * @param timeout to use for this connection
         * @param read to do read polling
         * @param write to do write polling
         */
        public void add(long socket, int timeout, boolean read, boolean write) {
            add(socket, timeout,
                    (read ? Poll.APR_POLLIN : 0) |
                    (write ? Poll.APR_POLLOUT : 0));
        }

        private void add(long socket, int timeout, int flags) {
            if (log.isDebugEnabled()) {
                String msg = sm.getString("endpoint.debug.pollerAdd",
                        Long.valueOf(socket), Integer.valueOf(timeout),
                        Integer.valueOf(flags));
                if (log.isTraceEnabled()) {
                    log.trace(msg, new Exception());
                } else {
                    log.debug(msg);
                }
            }
            if (timeout <= 0) {
                // Always put a timeout in
                timeout = Integer.MAX_VALUE;
            }
            boolean ok = false;
            synchronized (this) {
                // Add socket to the list. Newly added sockets will wait
                // at most for pollTime before being polled. Don't add the
                // socket once the poller has stopped but destroy it straight
                // away
                if (pollerRunning && addList.add(socket, timeout, flags)) {
                    ok = true;
                    this.notify();
                }
            }
            if (!ok) {
                // Can't do anything: close the socket right away
                boolean comet = connections.get(
                        Long.valueOf(socket)).isComet();
                if (!comet || !processSocket(socket, SocketStatus.ERROR)) {
                    closeSocket(socket);
                }
            }
        }


        /**
         * Add specified socket to one of the pollers. Must only be called from
         * {@link Poller#run()}.
         */
        protected boolean addToPoller(long socket, int events) {
            int rv = -1;
            for (int i = 0; i < pollers.length; i++) {
                if (pollerSpace[i] > 0) {
                    rv = Poll.add(pollers[i], socket, events);
                    if (rv == Status.APR_SUCCESS) {
                        pollerSpace[i]--;
                        connectionCount.incrementAndGet();
                        return true;
                    }
                }
            }
            return false;
        }


        protected boolean close(long socket) {
            if (!pollerRunning) {
                return false;
            }
            synchronized (this) {
                if (!pollerRunning) {
                    return false;
                }
                closeList.add(socket, 0, 0);
                this.notify();
                return true;
            }
        }


        /**
         * Remove specified socket from the pollers. Must only be called from
         * {@link Poller#run()}.
         */
        private boolean removeFromPoller(long socket) {
            if (log.isDebugEnabled()) {
                log.debug(sm.getString("endpoint.debug.pollerRemove",
                        Long.valueOf(socket)));
            }
            int rv = -1;
            for (int i = 0; i < pollers.length; i++) {
                if (pollerSpace[i] < actualPollerSize) {
                    rv = Poll.remove(pollers[i], socket);
                    if (rv != Status.APR_NOTFOUND) {
                        pollerSpace[i]++;
                        connectionCount.decrementAndGet();
                        if (log.isDebugEnabled()) {
                            log.debug(sm.getString("endpoint.debug.pollerRemoved",
                                    Long.valueOf(socket)));
                        }
                        break;
                    }
                }
            }
            timeouts.remove(socket);
            return (rv == Status.APR_SUCCESS);
        }

        /**
         * Timeout checks.
         */
        protected void maintain() {

            long date = System.currentTimeMillis();
            // Maintain runs at most once every 5s, although it will likely get
            // called more
            if ((date - lastMaintain) < 5000L) {
                return;
            } else {
                lastMaintain = date;
            }
            long socket = timeouts.check(date);
            while (socket != 0) {
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("endpoint.debug.socketTimeout",
                            Long.valueOf(socket)));
                }
                removeFromPoller(socket);
                boolean comet = connections.get(
                        Long.valueOf(socket)).isComet();
                if (!comet || !processSocket(socket, SocketStatus.TIMEOUT)) {
                    destroySocket(socket);
                }
                socket = timeouts.check(date);
            }

        }

        /**
         * Displays the list of sockets in the pollers.
         */
        @Override
        public String toString() {
            StringBuffer buf = new StringBuffer();
            buf.append("Poller");
            long[] res = new long[actualPollerSize * 2];
            for (int i = 0; i < pollers.length; i++) {
                int count = Poll.pollset(pollers[i], res);
                buf.append(" [ ");
                for (int j = 0; j < count; j++) {
                    buf.append(desc[2*j+1]).append(" ");
                }
                buf.append("]");
            }
            return buf.toString();
        }

        /**
         * The background thread that listens for incoming TCP/IP connections
         * and hands them off to an appropriate processor.
         */
        @Override
        public void run() {

            SocketList localAddList = new SocketList(getMaxConnections());
            SocketList localCloseList = new SocketList(getMaxConnections());

            // Loop until we receive a shutdown command
            while (pollerRunning) {

                // Loop if endpoint is paused
                while (pollerRunning && paused) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                }
                // Check timeouts if the poller is empty
                while (pollerRunning && connectionCount.get() < 1 &&
                        addList.size() < 1 && closeList.size() < 1) {
                    try {
                        if (getSoTimeout() > 0 && pollerRunning) {
                            maintain();
                        }
                        synchronized (this) {
                            // Make sure that no sockets have been placed in the
                            // addList or closeList since the check above.
                            // Without this check there could be a 10s pause
                            // with no processing since the notify() call in
                            // add()/close() would have no effect since it
                            // happened before this sync block was entered
                            if (addList.size() < 1 && closeList.size() < 1) {
                                this.wait(10000);
                            }
                        }
                    } catch (InterruptedException e) {
                        // Ignore
                    } catch (Throwable t) {
                        ExceptionUtils.handleThrowable(t);
                        getLog().warn(sm.getString("endpoint.timeout.err"));
                    }
                }

                // Don't add or poll if the poller has been stopped
                if (!pollerRunning) {
                    break;
                }

                try {
                    // Duplicate the add and remove lists so that the syncs are
                    // minimised
                    synchronized (this) {
                        if (closeList.size() > 0) {
                            // Duplicate to another list, so that the syncing is
                            // minimal
                            closeList.duplicate(localCloseList);
                            closeList.clear();
                        } else {
                            localCloseList.clear();
                        }
                    }
                    synchronized (this) {
                        if (addList.size() > 0) {
                            // Duplicate to another list, so that the syncing is
                            // minimal
                            addList.duplicate(localAddList);
                            addList.clear();
                        } else {
                            localAddList.clear();
                        }
                    }

                    // Remove sockets
                    if (localCloseList.size() > 0) {
                        SocketInfo info = localCloseList.get();
                        while (info != null) {
                            localAddList.remove(info.socket);
                            removeFromPoller(info.socket);
                            destroySocket(info.socket);
                            info = localCloseList.get();
                        }
                    }

                    // Add sockets which are waiting to the poller
                    if (localAddList.size() > 0) {
                        SocketInfo info = localAddList.get();
                        while (info != null) {
                            if (log.isDebugEnabled()) {
                                log.debug(sm.getString(
                                        "endpoint.debug.pollerAddDo",
                                        Long.valueOf(info.socket)));
                            }
                            timeouts.remove(info.socket);
                            AprSocketWrapper wrapper = connections.get(
                                    Long.valueOf(info.socket));
                            if (wrapper == null) {
                                continue;
                            }
                            if (info.read() || info.write()) {
                                boolean comet = wrapper.isComet();
                                if (comet || wrapper.pollerFlags != 0) {
                                    removeFromPoller(info.socket);
                                }
                                wrapper.pollerFlags = wrapper.pollerFlags |
                                        (info.read() ? Poll.APR_POLLIN : 0) |
                                        (info.write() ? Poll.APR_POLLOUT : 0);
                                if (!addToPoller(info.socket, wrapper.pollerFlags)) {
                                    // Can't do anything: close the socket right
                                    // away
                                    if (!comet || !processSocket(info.socket, SocketStatus.ERROR)) {
                                        closeSocket(info.socket);
                                    }
                                } else {
                                    timeouts.add(info.socket,
                                            System.currentTimeMillis() +
                                                    info.timeout);
                                }
                            } else {
                                // Should never happen.
                                closeSocket(info.socket);
                                getLog().warn(sm.getString(
                                        "endpoint.apr.pollAddInvalid", info));
                            }
                            info = localAddList.get();
                        }
                    }

                    // Poll for the specified interval
                    for (int i = 0; i < pollers.length; i++) {

                        // Flags to ask to reallocate the pool
                        boolean reset = false;
                        //ArrayList<Long> skip = null;

                        int rv = 0;
                        // Iterate on each pollers, but no need to poll empty pollers
                        if (pollerSpace[i] < actualPollerSize) {
                            rv = Poll.poll(pollers[i], nextPollerTime, desc, true);
                            // Reset the nextPollerTime
                            nextPollerTime = pollerTime;
                        } else {
                            // Skipping an empty poll set means skipping a wait
                            // time of pollerTime microseconds. If most of the
                            // poll sets are skipped then this loop will be
                            // tighter than expected which could lead to higher
                            // than expected CPU usage. Extending the
                            // nextPollerTime ensures that this loop always
                            // takes about the same time to execute.
                            nextPollerTime += pollerTime;
                        }
                        if (rv > 0) {
                            pollerSpace[i] += rv;
                            connectionCount.addAndGet(-rv);
                            for (int n = 0; n < rv; n++) {
                                if (getLog().isDebugEnabled()) {
                                    log.debug(sm.getString(
                                            "endpoint.debug.pollerProcess",
                                            Long.valueOf(desc[n*2+1]),
                                            Long.valueOf(desc[n*2])));
                                }
                                long timeout = timeouts.remove(desc[n*2+1]);
                                AprSocketWrapper wrapper = connections.get(
                                        Long.valueOf(desc[n*2+1]));
                                if (wrapper == null) {
                                    // Socket was closed in another thread while still in
                                    // the Poller but wasn't removed from the Poller before
                                    // new data arrived.
                                    continue;
                                }
                                wrapper.pollerFlags = wrapper.pollerFlags & ~((int) desc[n*2]);
                                // Check for failed sockets and hand this socket off to a worker
                                if (wrapper.isComet()) {
                                    // Event processes either a read or a write depending on what the poller returns
                                    if (((desc[n*2] & Poll.APR_POLLHUP) == Poll.APR_POLLHUP)
                                            || ((desc[n*2] & Poll.APR_POLLERR) == Poll.APR_POLLERR)
                                            || ((desc[n*2] & Poll.APR_POLLNVAL) == Poll.APR_POLLNVAL)) {
                                        if (!processSocket(desc[n*2+1], SocketStatus.ERROR)) {
                                            // Close socket and clear pool
                                            closeSocket(desc[n*2+1]);
                                        }
                                    } else if ((desc[n*2] & Poll.APR_POLLIN) == Poll.APR_POLLIN) {
                                        if (wrapper.pollerFlags != 0) {
                                            add(desc[n*2+1], 1, wrapper.pollerFlags);
                                        }
                                        if (!processSocket(desc[n*2+1], SocketStatus.OPEN_READ)) {
                                            // Close socket and clear pool
                                            closeSocket(desc[n*2+1]);
                                        }
                                    } else if ((desc[n*2] & Poll.APR_POLLOUT) == Poll.APR_POLLOUT) {
                                        if (wrapper.pollerFlags != 0) {
                                            add(desc[n*2+1], 1, wrapper.pollerFlags);
                                        }
                                        if (!processSocket(desc[n*2+1], SocketStatus.OPEN_WRITE)) {
                                            // Close socket and clear pool
                                            closeSocket(desc[n*2+1]);
                                        }
                                    } else {
                                        // Unknown event
                                        getLog().warn(sm.getString(
                                                "endpoint.apr.pollUnknownEvent",
                                                Long.valueOf(desc[n*2])));
                                        if (!processSocket(desc[n*2+1], SocketStatus.ERROR)) {
                                            // Close socket and clear pool
                                            closeSocket(desc[n*2+1]);
                                        }
                                    }
                                } else if (((desc[n*2] & Poll.APR_POLLHUP) == Poll.APR_POLLHUP)
                                        || ((desc[n*2] & Poll.APR_POLLERR) == Poll.APR_POLLERR)
                                        || ((desc[n*2] & Poll.APR_POLLNVAL) == Poll.APR_POLLNVAL)) {
                                    if (wrapper.isUpgraded()) {
                                        // Using non-blocking IO. Need to trigger error handling.
                                        // Poller may return error codes plus the flags it was
                                        // waiting for or it may just return an error code. By
                                        // signalling read/write is possible, a read/write will be
                                        // attempted, fail and that will trigger an exception the
                                        // application will see.
                                        // Check the return flags first, followed by what the socket
                                        // was registered for
                                        if ((desc[n*2] & Poll.APR_POLLIN) == Poll.APR_POLLIN) {
                                            // Error probably occurred during a non-blocking read
                                            if (!processSocket(desc[n*2+1], SocketStatus.OPEN_READ)) {
                                                // Close socket and clear pool
                                                closeSocket(desc[n*2+1]);
                                            }
                                        } else if ((desc[n*2] & Poll.APR_POLLOUT) == Poll.APR_POLLOUT) {
                                            // Error probably occurred during a non-blocking write
                                            if (!processSocket(desc[n*2+1], SocketStatus.OPEN_WRITE)) {
                                                // Close socket and clear pool
                                                closeSocket(desc[n*2+1]);
                                            }
                                        } else if ((wrapper.pollerFlags & Poll.APR_POLLIN) == Poll.APR_POLLIN) {
                                            // Can't tell what was happening when the error occurred but the
                                            // socket is registered for non-blocking read so use that
                                            if (!processSocket(desc[n*2+1], SocketStatus.OPEN_READ)) {
                                                // Close socket and clear pool
                                                closeSocket(desc[n*2+1]);
                                            }
                                        } else if ((wrapper.pollerFlags & Poll.APR_POLLOUT) == Poll.APR_POLLOUT) {
                                            // Can't tell what was happening when the error occurred but the
                                            // socket is registered for non-blocking write so use that
                                            if (!processSocket(desc[n*2+1], SocketStatus.OPEN_WRITE)) {
                                                // Close socket and clear pool
                                                closeSocket(desc[n*2+1]);
                                            }
                                        } else {
                                            // Close socket and clear pool
                                            closeSocket(desc[n*2+1]);
                                        }
                                    } else {
                                        // Close socket and clear pool
                                        closeSocket(desc[n*2+1]);
                                    }
                                } else if (((desc[n*2] & Poll.APR_POLLIN) == Poll.APR_POLLIN)
                                        || ((desc[n*2] & Poll.APR_POLLOUT) == Poll.APR_POLLOUT)) {
                                    boolean error = false;
                                    if (((desc[n*2] & Poll.APR_POLLIN) == Poll.APR_POLLIN) &&
                                            !processSocket(desc[n*2+1], SocketStatus.OPEN_READ)) {
                                        error = true;
                                        // Close socket and clear pool
                                        closeSocket(desc[n*2+1]);
                                    }
                                    if (!error &&
                                            ((desc[n*2] & Poll.APR_POLLOUT) == Poll.APR_POLLOUT) &&
                                            !processSocket(desc[n*2+1], SocketStatus.OPEN_WRITE)) {
                                        // Close socket and clear pool
                                        error = true;
                                        closeSocket(desc[n*2+1]);
                                    }
                                    if (!error && wrapper.pollerFlags != 0) {
                                        // If socket was registered for multiple events but
                                        // only some of the occurred, re-register for the
                                        // remaining events.
                                        // timeout is the value of System.currentTimeMillis() that
                                        // was set as the point that the socket will timeout. When
                                        // adding to the poller, the timeout from now in
                                        // milliseconds is required.
                                        // So first, subtract the current timestamp
                                        if (timeout > 0) {
                                            timeout = timeout - System.currentTimeMillis();
                                        }
                                        // If the socket should have already expired by now,
                                        // re-add it with a very short timeout
                                        if (timeout <= 0) {
                                            timeout = 1;
                                        }
                                        // Should be impossible but just in case since timeout will
                                        // be cast to an int.
                                        if (timeout > Integer.MAX_VALUE) {
                                            timeout = Integer.MAX_VALUE;
                                        }
                                        add(desc[n*2+1], (int) timeout, wrapper.pollerFlags);
                                    }
                                } else {
                                    // Unknown event
                                    getLog().warn(sm.getString(
                                            "endpoint.apr.pollUnknownEvent",
                                            Long.valueOf(desc[n*2])));
                                    // Close socket and clear pool
                                    closeSocket(desc[n*2+1]);
                                }
                            }
                        } else if (rv < 0) {
                            int errn = -rv;
                            // Any non timeup or interrupted error is critical
                            if ((errn != Status.TIMEUP) && (errn != Status.EINTR)) {
                                if (errn >  Status.APR_OS_START_USERERR) {
                                    errn -=  Status.APR_OS_START_USERERR;
                                }
                                getLog().error(sm.getString(
                                        "endpoint.apr.pollError",
                                        Integer.valueOf(errn),
                                        Error.strerror(errn)));
                                // Destroy and reallocate the poller
                                reset = true;
                            }
                        }

                        if (reset && pollerRunning) {
                            // Reallocate the current poller
                            int count = Poll.pollset(pollers[i], desc);
                            long newPoller = allocatePoller(actualPollerSize, pool, -1);
                            // Don't restore connections for now, since I have not tested it
                            pollerSpace[i] = actualPollerSize;
                            connectionCount.addAndGet(-count);
                            Poll.destroy(pollers[i]);
                            pollers[i] = newPoller;
                        }

                    }
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    getLog().warn(sm.getString("endpoint.poll.error"), t);
                }
                try {
                    // Process socket timeouts
                    if (getSoTimeout() > 0 && pollerRunning) {
                        // This works and uses only one timeout mechanism for everything, but the
                        // non event poller might be a bit faster by using the old maintain.
                        maintain();
                    }
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    getLog().warn(sm.getString("endpoint.timeout.err"), t);
                }
            }

            synchronized (this) {
                this.notifyAll();
            }
        }
    }


    // ----------------------------------------------- SendfileData Inner Class


    /**
     * SendfileData class.
     */
    public static class SendfileData {
        // File
        public String fileName;
        public long fd;
        public long fdpool;
        // Range information
        public long start;
        public long end;
        // Socket and socket pool
        public long socket;
        // Position
        public long pos;
        // KeepAlive flag
        public SendfileKeepAliveState keepAliveState = SendfileKeepAliveState.NONE;
    }


    // --------------------------------------------------- Sendfile Inner Class


    public class Sendfile implements Runnable {

        protected long sendfilePollset = 0;
        protected long pool = 0;
        protected long[] desc;
        protected HashMap<Long, SendfileData> sendfileData;

        protected int sendfileCount;
        public int getSendfileCount() { return sendfileCount; }

        protected ArrayList<SendfileData> addS;

        private volatile boolean sendfileRunning = true;

        /**
         * Create the sendfile poller. With some versions of APR, the maximum
         * poller size will be 62 (recompiling APR is necessary to remove this
         * limitation).
         */
        protected void init() {
            pool = Pool.create(serverSockPool);
            int size = sendfileSize;
            if (size <= 0) {
                size = (OS.IS_WIN32 || OS.IS_WIN64) ? (1 * 1024) : (16 * 1024);
            }
            sendfilePollset = allocatePoller(size, pool, getSoTimeout());
            if (sendfilePollset == 0 && size > 1024) {
                size = 1024;
                sendfilePollset = allocatePoller(size, pool, getSoTimeout());
            }
            if (sendfilePollset == 0) {
                size = 62;
                sendfilePollset = allocatePoller(size, pool, getSoTimeout());
            }
            desc = new long[size * 2];
            sendfileData = new HashMap<Long, SendfileData>(size);
            addS = new ArrayList<SendfileData>();
        }

        /**
         * Destroy the poller.
         */
        protected void destroy() {
            sendfileRunning = false;
            // Wait for polltime before doing anything, so that the poller threads
            // exit, otherwise parallel destruction of sockets which are still
            // in the poller can cause problems
            try {
                synchronized (this) {
                    this.notify();
                    this.wait(pollTime / 1000);
                }
            } catch (InterruptedException e) {
                // Ignore
            }
            // Close any socket remaining in the add queue
            for (int i = (addS.size() - 1); i >= 0; i--) {
                SendfileData data = addS.get(i);
                closeSocket(data.socket);
            }
            // Close all sockets still in the poller
            int rv = Poll.pollset(sendfilePollset, desc);
            if (rv > 0) {
                for (int n = 0; n < rv; n++) {
                    closeSocket(desc[n*2+1]);
                }
            }
            Pool.destroy(pool);
            sendfileData.clear();
        }

        /**
         * Add the sendfile data to the sendfile poller. Note that in most cases,
         * the initial non blocking calls to sendfile will return right away, and
         * will be handled asynchronously inside the kernel. As a result,
         * the poller will never be used.
         *
         * @param data containing the reference to the data which should be snet
         * @return true if all the data has been sent right away, and false
         *              otherwise
         */
        public SendfileState add(SendfileData data) {
            // Initialize fd from data given
            try {
                data.fdpool = Socket.pool(data.socket);
                data.fd = File.open
                    (data.fileName, File.APR_FOPEN_READ
                     | File.APR_FOPEN_SENDFILE_ENABLED | File.APR_FOPEN_BINARY,
                     0, data.fdpool);
                data.pos = data.start;
                // Set the socket to nonblocking mode
                Socket.timeoutSet(data.socket, 0);
                while (sendfileRunning) {
                    long nw = Socket.sendfilen(data.socket, data.fd,
                                               data.pos, data.end - data.pos, 0);
                    if (nw < 0) {
                        if (!(-nw == Status.EAGAIN)) {
                            Pool.destroy(data.fdpool);
                            data.socket = 0;
                            return SendfileState.ERROR;
                        } else {
                            // Break the loop and add the socket to poller.
                            break;
                        }
                    } else {
                        data.pos = data.pos + nw;
                        if (data.pos >= data.end) {
                            // Entire file has been sent
                            Pool.destroy(data.fdpool);
                            // Set back socket to blocking mode
                            Socket.timeoutSet(
                                    data.socket, getSoTimeout() * 1000);
                            return SendfileState.DONE;
                        }
                    }
                }
            } catch (Exception e) {
                log.warn(sm.getString("endpoint.sendfile.error"), e);
                return SendfileState.ERROR;
            }
            // Add socket to the list. Newly added sockets will wait
            // at most for pollTime before being polled
            synchronized (this) {
                addS.add(data);
                this.notify();
            }
            return SendfileState.PENDING;
        }

        /**
         * Remove socket from the poller.
         *
         * @param data the sendfile data which should be removed
         */
        protected void remove(SendfileData data) {
            int rv = Poll.remove(sendfilePollset, data.socket);
            if (rv == Status.APR_SUCCESS) {
                sendfileCount--;
            }
            sendfileData.remove(Long.valueOf(data.socket));
        }

        /**
         * The background thread that listens for incoming TCP/IP connections
         * and hands them off to an appropriate processor.
         */
        @Override
        public void run() {

            long maintainTime = 0;
            // Loop until we receive a shutdown command
            while (sendfileRunning) {

                // Loop if endpoint is paused
                while (sendfileRunning && paused) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                }
                // Loop if poller is empty
                while (sendfileRunning && sendfileCount < 1 && addS.size() < 1) {
                    // Reset maintain time.
                    maintainTime = 0;
                    try {
                        synchronized (this) {
                            this.wait();
                        }
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                }

                // Don't add or poll if the poller has been stopped
                if (!sendfileRunning) {
                    break;
                }
                
                try {
                    // Add socket to the poller
                    if (addS.size() > 0) {
                        synchronized (this) {
                            for (int i = (addS.size() - 1); i >= 0; i--) {
                                SendfileData data = addS.get(i);
                                int rv = Poll.add(sendfilePollset, data.socket, Poll.APR_POLLOUT);
                                if (rv == Status.APR_SUCCESS) {
                                    sendfileData.put(Long.valueOf(data.socket), data);
                                    sendfileCount++;
                                } else {
                                    getLog().warn(sm.getString(
                                            "endpoint.sendfile.addfail",
                                            Integer.valueOf(rv),
                                            Error.strerror(rv)));
                                    // Can't do anything: close the socket right away
                                    closeSocket(data.socket);
                                }
                            }
                            addS.clear();
                        }
                    }

                    maintainTime += pollTime;
                    // Pool for the specified interval
                    int rv = Poll.poll(sendfilePollset, pollTime, desc, false);
                    if (rv > 0) {
                        for (int n = 0; n < rv; n++) {
                            // Get the sendfile state
                            SendfileData state =
                                sendfileData.get(Long.valueOf(desc[n*2+1]));
                            // Problem events
                            if (((desc[n*2] & Poll.APR_POLLHUP) == Poll.APR_POLLHUP)
                                    || ((desc[n*2] & Poll.APR_POLLERR) == Poll.APR_POLLERR)) {
                                // Close socket and clear pool
                                remove(state);
                                // Destroy file descriptor pool, which should close the file
                                // Close the socket, as the response would be incomplete
                                closeSocket(state.socket);
                                continue;
                            }
                            // Write some data using sendfile
                            long nw = Socket.sendfilen(state.socket, state.fd,
                                                       state.pos,
                                                       state.end - state.pos, 0);
                            if (nw < 0) {
                                // Close socket and clear pool
                                remove(state);
                                // Close the socket, as the response would be incomplete
                                // This will close the file too.
                                closeSocket(state.socket);
                                continue;
                            }

                            state.pos = state.pos + nw;
                            if (state.pos >= state.end) {
                                remove(state);
                                switch (state.keepAliveState) {
                                case NONE: {
                                    // Close the socket since this is
                                    // the end of the not keep-alive request.
                                    closeSocket(state.socket);
                                    break;
                                }
                                case PIPELINED: {
                                    // Destroy file descriptor pool, which should close the file
                                    Pool.destroy(state.fdpool);
                                    Socket.timeoutSet(state.socket, getSoTimeout() * 1000);
                                    // Process the pipelined request data
                                    if (!processSocket(state.socket, SocketStatus.OPEN_READ)) {
                                        closeSocket(state.socket);
                                    }
                                    break;
                                }
                                case OPEN: {
                                    // Destroy file descriptor pool, which should close the file
                                    Pool.destroy(state.fdpool);
                                    Socket.timeoutSet(state.socket, getSoTimeout() * 1000);
                                    // Put the socket back in the poller for
                                    // processing of further requests
                                    getPoller().add(state.socket, getKeepAliveTimeout(),
                                            true, false);
                                    break;
                                }
                                }
                            }
                        }
                    } else if (rv < 0) {
                        int errn = -rv;
                        /* Any non timeup or interrupted error is critical */
                        if ((errn != Status.TIMEUP) && (errn != Status.EINTR)) {
                            if (errn >  Status.APR_OS_START_USERERR) {
                                errn -=  Status.APR_OS_START_USERERR;
                            }
                            getLog().error(sm.getString(
                                    "endpoint.apr.pollError",
                                    Integer.valueOf(errn),
                                    Error.strerror(errn)));
                            // Handle poll critical failure
                            synchronized (this) {
                                destroy();
                                init();
                            }
                            continue;
                        }
                    }
                    // Call maintain for the sendfile poller
                    if (getSoTimeout() > 0 &&
                            maintainTime > 1000000L && sendfileRunning) {
                        rv = Poll.maintain(sendfilePollset, desc, false);
                        maintainTime = 0;
                        if (rv > 0) {
                            for (int n = 0; n < rv; n++) {
                                // Get the sendfile state
                                SendfileData state = sendfileData.get(Long.valueOf(desc[n]));
                                // Close socket and clear pool
                                remove(state);
                                // Destroy file descriptor pool, which should close the file
                                // Close the socket, as the response would be incomplete
                                closeSocket(state.socket);
                            }
                        }
                    }
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    getLog().error(sm.getString("endpoint.poll.error"), t);
                }
            }

            synchronized (this) {
                this.notifyAll();
            }

        }

    }

    // ------------------------------------------------ Handler Inner Interface


    /**
     * Bare bones interface used for socket processing. Per thread data is to be
     * stored in the ThreadWithAttributes extra folders, or alternately in
     * thread local fields.
     */
    public interface Handler extends AbstractEndpoint.Handler {
        public SocketState process(SocketWrapper<Long> socket,
                SocketStatus status);
    }


    // --------------------------------- SocketWithOptionsProcessor Inner Class

    /**
     * This class is the equivalent of the Worker, but will simply use in an
     * external Executor thread pool. This will also set the socket options
     * and do the handshake.
     *
     * This is called after an accept().
     */
    protected class SocketWithOptionsProcessor implements Runnable {

        protected SocketWrapper<Long> socket = null;


        public SocketWithOptionsProcessor(SocketWrapper<Long> socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            synchronized (socket) {
                if (!deferAccept) {
                    if (setSocketOptions(socket.getSocket().longValue())) {
                        getPoller().add(socket.getSocket().longValue(),
                                getSoTimeout(), true, false);
                    } else {
                        // Close socket and pool
                        closeSocket(socket.getSocket().longValue());
                        socket = null;
                    }
                } else {
                    // Process the request from this socket
                    if (!setSocketOptions(socket.getSocket().longValue())) {
                        // Close socket and pool
                        closeSocket(socket.getSocket().longValue());
                        socket = null;
                        return;
                    }
                    // Process the request from this socket
                    Handler.SocketState state = handler.process(socket,
                            SocketStatus.OPEN_READ);
                    if (state == Handler.SocketState.CLOSED) {
                        // Close socket and pool
                        closeSocket(socket.getSocket().longValue());
                        socket = null;
                    } else if (state == Handler.SocketState.LONG) {
                        socket.access();
                        if (socket.async) {
                            waitingRequests.add(socket);
                        }
                    }
                }
            }
        }
    }


    // -------------------------------------------- SocketProcessor Inner Class


    /**
     * This class is the equivalent of the Worker, but will simply use in an
     * external Executor thread pool.
     */
    protected class SocketProcessor implements Runnable {

        private final SocketWrapper<Long> socket;
        private final SocketStatus status;

        public SocketProcessor(SocketWrapper<Long> socket,
                SocketStatus status) {
            this.socket = socket;
            if (status == null) {
                // Should never happen
                throw new NullPointerException();
            }
            this.status = status;
        }

        @Override
        public void run() {

            // Upgraded connections need to allow multiple threads to access the
            // connection at the same time to enable blocking IO to be used when
            // Servlet 3.1 NIO has been configured
            if (socket.isUpgraded() && SocketStatus.OPEN_WRITE == status) {
                synchronized (socket.getWriteThreadLock()) {
                    doRun();
                }
            } else {
                synchronized (socket) {
                    doRun();
                }
            }
        }

        private void doRun() {
            // Process the request from this socket
            if (socket.getSocket() == null) {
                // Closed in another thread
                return;
            }
            SocketState state = handler.process(socket, status);
            if (state == Handler.SocketState.CLOSED) {
                // Close socket and pool
                closeSocket(socket.getSocket().longValue());
                socket.socket = null;
            } else if (state == Handler.SocketState.LONG) {
                socket.access();
                if (socket.async) {
                    waitingRequests.add(socket);
                }
            } else if (state == Handler.SocketState.ASYNC_END) {
                socket.access();
                SocketProcessor proc = new SocketProcessor(socket,
                        SocketStatus.OPEN_READ);
                getExecutor().execute(proc);
            }
        }
    }


    private static class AprSocketWrapper extends SocketWrapper<Long> {

        // This field should only be used by Poller#run()
        private int pollerFlags = 0;

        public AprSocketWrapper(Long socket) {
            super(socket);
        }
    }
}
