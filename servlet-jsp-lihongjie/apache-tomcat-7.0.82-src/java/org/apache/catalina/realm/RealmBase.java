/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.realm;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Realm;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.mbeans.MBeanUtils;
import org.apache.catalina.util.LifecycleMBeanBase;
import org.apache.catalina.util.SessionConfig;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.buf.HexUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.util.security.MD5Encoder;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSName;

/**
 * Simple implementation of <b>Realm</b> that reads an XML file to configure
 * the valid users, passwords, and roles.  The file format (and default file
 * location) are identical to those currently supported by Tomcat 3.X.
 *
 * @author Craig R. McClanahan
 */
public abstract class RealmBase extends LifecycleMBeanBase implements Realm {

    private static final Log log = LogFactory.getLog(RealmBase.class);

    // ----------------------------------------------------- Instance Variables


    /**
     * The Container with which this Realm is associated.
     */
    protected Container container = null;


    /**
     * Container log
     */
    protected Log containerLog = null;


    /**
     * Digest algorithm used in storing passwords in a non-plaintext format.
     * Valid values are those accepted for the algorithm name by the
     * MessageDigest class, or <code>null</code> if no digesting should
     * be performed.
     */
    protected String digest = null;

    /**
     * The encoding charset for the digest.
     */
    protected String digestEncoding = null;


    /**
     * Descriptive information about this Realm implementation.
     */
    protected static final String info =
        "org.apache.catalina.realm.RealmBase/1.0";


    /**
     * The MessageDigest object for digesting user credentials (passwords).
     */
    protected volatile MessageDigest md = null;


    /**
     * The MD5 helper object for this class.
     *
     * @deprecated  Unused - will be removed in Tomcat 8.0.x
     */
    @Deprecated
    protected static final MD5Encoder md5Encoder = new MD5Encoder();


    /**
     * MD5 message digest provider.
     */
    protected static volatile MessageDigest md5Helper;


    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    /**
     * The property change support for this component.
     */
    protected PropertyChangeSupport support = new PropertyChangeSupport(this);


    /**
     * Should we validate client certificate chains when they are presented?
     */
    protected boolean validate = true;

    /**
     * The name of the class to use for retrieving user names from X509
     * certificates.
     */
    protected String x509UsernameRetrieverClassName;

    /**
     * The object that will extract user names from X509 client certificates.
     */
    protected X509UsernameRetriever x509UsernameRetriever;

    /**
     * The all role mode.
     */
    protected AllRolesMode allRolesMode = AllRolesMode.STRICT_MODE;


    /**
     * When processing users authenticated via the GSS-API, should any
     * &quot;@...&quot; be stripped from the end of the user name?
     */
    protected boolean stripRealmForGss = true;


    private int transportGuaranteeRedirectStatus = HttpServletResponse.SC_FOUND;


    // ------------------------------------------------------------- Properties

    /**
     * @return The HTTP status code used when the container needs to issue an
     *         HTTP redirect to meet the requirements of a configured transport
     *         guarantee.
     */
    public int getTransportGuaranteeRedirectStatus() {
        return transportGuaranteeRedirectStatus;
    }


    /**
     * Set the HTTP status code used when the container needs to issue an HTTP
     * redirect to meet the requirements of a configured transport guarantee.
     *
     * @param transportGuaranteeRedirectStatus The status to use. This value is
     *                                         not validated
     */
    public void setTransportGuaranteeRedirectStatus(int transportGuaranteeRedirectStatus) {
        this.transportGuaranteeRedirectStatus = transportGuaranteeRedirectStatus;
    }


    /**
     * Return the Container with which this Realm has been associated.
     */
    @Override
    public Container getContainer() {

        return (container);

    }


    /**
     * Set the Container with which this Realm has been associated.
     *
     * @param container The associated Container
     */
    @Override
    public void setContainer(Container container) {

        Container oldContainer = this.container;
        this.container = container;
        support.firePropertyChange("container", oldContainer, this.container);

    }

    /**
     * Return the all roles mode.
     */
    public String getAllRolesMode() {

        return allRolesMode.toString();

    }


    /**
     * Set the all roles mode.
     */
    public void setAllRolesMode(String allRolesMode) {

        this.allRolesMode = AllRolesMode.toMode(allRolesMode);

    }

    /**
     * Return the digest algorithm  used for storing credentials.
     */
    public String getDigest() {

        return digest;

    }


    /**
     * Set the digest algorithm used for storing credentials.
     *
     * @param digest The new digest algorithm
     */
    public void setDigest(String digest) {

        this.digest = digest;

    }

    /**
     * Returns the digest encoding charset.
     *
     * @return The charset (may be null) for platform default
     */
    public String getDigestEncoding() {
        return digestEncoding;
    }

    /**
     * Sets the digest encoding charset.
     *
     * @param charset The charset (null for platform default)
     */
    public void setDigestEncoding(String charset) {
        digestEncoding = charset;
    }

    protected Charset getDigestCharset() throws UnsupportedEncodingException {
        if (digestEncoding == null) {
            return Charset.defaultCharset();
        } else {
            return B2CConverter.getCharset(getDigestEncoding());
        }
    }

    /**
     * Return descriptive information about this Realm implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    @Override
    public String getInfo() {

        return info;

    }


    /**
     * Return the "validate certificate chains" flag.
     */
    public boolean getValidate() {

        return (this.validate);

    }


    /**
     * Set the "validate certificate chains" flag.
     *
     * @param validate The new validate certificate chains flag
     */
    public void setValidate(boolean validate) {

        this.validate = validate;

    }

    /**
     * Gets the name of the class that will be used to extract user names
     * from X509 client certificates.
     * @return The name of the class that will be used to extract user names
     *         from X509 client certificates.
     */
    public String getX509UsernameRetrieverClassName() {
        return x509UsernameRetrieverClassName;
    }

    /**
     * Sets the name of the class that will be used to extract user names
     * from X509 client certificates. The class must implement
     * (@link X509UsernameRetriever}.
     *
     * @param className The name of the class that will be used to extract user names
     *                  from X509 client certificates.
     */
    public void setX509UsernameRetrieverClassName(String className) {
        this.x509UsernameRetrieverClassName = className;
    }

    public boolean isStripRealmForGss() {
        return stripRealmForGss;
    }


    public void setStripRealmForGss(boolean stripRealmForGss) {
        this.stripRealmForGss = stripRealmForGss;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Add a property change listener to this component.
     *
     * @param listener The listener to add
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

        support.addPropertyChangeListener(listener);

    }


    /**
     * Return the Principal associated with the specified username, if there
     * is one; otherwise return <code>null</code>.
     *
     * @param username Username of the Principal to look up
     */
    @Override
    public Principal authenticate(String username) {

        if (username == null) {
            return null;
        }

        if (containerLog.isTraceEnabled()) {
            containerLog.trace(sm.getString("realmBase.authenticateSuccess", username));
        }

        return getPrincipal(username);
    }


    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * @param username Username of the Principal to look up
     * @param credentials Password or other credentials to use in
     *  authenticating this username
     */
    @Override
    public Principal authenticate(String username, String credentials) {
        // No user or no credentials
        // Can't possibly authenticate, don't bother doing anything.
        if(username == null || credentials == null) {
            if (containerLog.isTraceEnabled()) {
                containerLog.trace(sm.getString("realmBase.authenticateFailure",
                                                username));
            }
            return null;
        }

        // Look up the user's credentials
        String serverCredentials = getPassword(username);

        if (serverCredentials == null) {
            // User was not found
            // Waste a bit of time as not to reveal that the user does not exist.
            compareCredentials(credentials, getClass().getName());

            if (containerLog.isTraceEnabled()) {
                containerLog.trace(sm.getString("realmBase.authenticateFailure",
                                                username));
            }
            return null;
        }

        boolean validated = compareCredentials(credentials, serverCredentials);

        if (validated) {
            if (containerLog.isTraceEnabled()) {
                containerLog.trace(sm.getString("realmBase.authenticateSuccess",
                                                username));
            }
            return getPrincipal(username);
        } else {
            if (containerLog.isTraceEnabled()) {
                containerLog.trace(sm.getString("realmBase.authenticateFailure",
                                                username));
            }
            return null;
        }
    }


    /**
     * Return the Principal associated with the specified username, which
     * matches the digest calculated using the given parameters using the
     * method described in RFC 2069; otherwise return <code>null</code>.
     *
     * @param username Username of the Principal to look up
     * @param clientDigest Digest which has been submitted by the client
     * @param nonce Unique (or supposedly unique) token which has been used
     * for this request
     * @param realm Realm name
     * @param md5a2 Second MD5 digest used to calculate the digest :
     * MD5(Method + ":" + uri)
     */
    @Override
    public Principal authenticate(String username, String clientDigest,
                                  String nonce, String nc, String cnonce,
                                  String qop, String realm,
                                  String md5a2) {

        // In digest auth, digests are always lower case
        String md5a1 = getDigest(username, realm);
        if (md5a1 == null)
            return null;
        md5a1 = md5a1.toLowerCase(Locale.ENGLISH);
        String serverDigestValue;
        if (qop == null) {
            serverDigestValue = md5a1 + ":" + nonce + ":" + md5a2;
        } else {
            serverDigestValue = md5a1 + ":" + nonce + ":" + nc + ":" +
                    cnonce + ":" + qop + ":" + md5a2;
        }

        byte[] valueBytes = null;
        try {
            valueBytes = serverDigestValue.getBytes(getDigestCharset());
        } catch (UnsupportedEncodingException uee) {
            log.error("Illegal digestEncoding: " + getDigestEncoding(), uee);
            throw new IllegalArgumentException(uee.getMessage());
        }

        String serverDigest = null;
        // Bugzilla 32137
        synchronized(md5Helper) {
            serverDigest = MD5Encoder.encode(md5Helper.digest(valueBytes));
        }

        if (log.isDebugEnabled()) {
            log.debug("Digest : " + clientDigest + " Username:" + username
                    + " ClientDigest:" + clientDigest + " nonce:" + nonce
                    + " nc:" + nc + " cnonce:" + cnonce + " qop:" + qop
                    + " realm:" + realm + "md5a2:" + md5a2
                    + " Server digest:" + serverDigest);
        }

        if (serverDigest.equals(clientDigest)) {
            return getPrincipal(username);
        }

        return null;
    }


    /**
     * Return the Principal associated with the specified chain of X509
     * client certificates.  If there is none, return <code>null</code>.
     *
     * @param certs Array of client certificates, with the first one in
     *  the array being the certificate of the client itself.
     */
    @Override
    public Principal authenticate(X509Certificate certs[]) {

        if ((certs == null) || (certs.length < 1))
            return (null);

        // Check the validity of each certificate in the chain
        if (log.isDebugEnabled())
            log.debug("Authenticating client certificate chain");
        if (validate) {
            for (int i = 0; i < certs.length; i++) {
                if (log.isDebugEnabled())
                    log.debug(" Checking validity for '" +
                        certs[i].getSubjectDN().getName() + "'");
                try {
                    certs[i].checkValidity();
                } catch (Exception e) {
                    if (log.isDebugEnabled())
                        log.debug("  Validity exception", e);
                    return (null);
                }
            }
        }

        // Check the existence of the client Principal in our database
        return (getPrincipal(certs[0]));

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Principal authenticate(GSSContext gssContext, boolean storeCreds) {
        if (gssContext.isEstablished()) {
            GSSName gssName = null;
            try {
                gssName = gssContext.getSrcName();
            } catch (GSSException e) {
                log.warn(sm.getString("realmBase.gssNameFail"), e);
            }

            if (gssName!= null) {
                String name = gssName.toString();

                if (isStripRealmForGss()) {
                    int i = name.indexOf('@');
                    if (i > 0) {
                        // Zero so we don;t leave a zero length name
                        name = name.substring(0, i);
                    }
                }
                GSSCredential gssCredential = null;
                if (storeCreds && gssContext.getCredDelegState()) {
                    try {
                        gssCredential = gssContext.getDelegCred();
                    } catch (GSSException e) {
                        if (log.isDebugEnabled()) {
                            log.debug(sm.getString(
                                    "realmBase.delegatedCredentialFail", name),
                                    e);
                        }
                    }
                }
                return getPrincipal(name, gssCredential);
            }
        } else {
            log.error(sm.getString("realmBase.gssContextNotEstablished"));
        }

        // Fail in all other cases
        return null;
    }


    protected boolean compareCredentials(String userCredentials,
            String serverCredentials) {

        if (serverCredentials == null) {
            return false;
        }

        if (hasMessageDigest()) {
            // Some directories and databases prefix the password with the hash
            // type. The string is in a format compatible with Base64.encode not
            // the normal hex encoding of the digest
            if (serverCredentials.startsWith("{MD5}") ||
                    serverCredentials.startsWith("{SHA}")) {
                // Server is storing digested passwords with a prefix indicating
                // the digest type
                String serverDigest = serverCredentials.substring(5);
                String userDigest;
                synchronized (this) {
                    md.reset();
                    md.update(userCredentials.getBytes(B2CConverter.ISO_8859_1));
                    userDigest = Base64.encodeBase64String(md.digest());
                }
                return userDigest.equals(serverDigest);

            } else if (serverCredentials.startsWith("{SSHA}")) {
                // Server is storing digested passwords with a prefix indicating
                // the digest type and the salt used when creating that digest

                String serverDigestPlusSalt = serverCredentials.substring(6);

                // Need to convert the salt to bytes to apply it to the user's
                // digested password.
                byte[] serverDigestPlusSaltBytes =
                        Base64.decodeBase64(serverDigestPlusSalt);
                final int saltPos = 20;
                byte[] serverDigestBytes = new byte[saltPos];
                System.arraycopy(serverDigestPlusSaltBytes, 0,
                        serverDigestBytes, 0, saltPos);

                // Generate the digested form of the user provided password
                // using the salt
                byte[] userDigestBytes;
                synchronized (this) {
                    md.reset();
                    // User provided password
                    md.update(userCredentials.getBytes(B2CConverter.ISO_8859_1));
                    // Add the salt
                    md.update(serverDigestPlusSaltBytes, saltPos,
                            serverDigestPlusSaltBytes.length - saltPos);
                    userDigestBytes = md.digest();
                }

                return Arrays.equals(userDigestBytes, serverDigestBytes);

            } else {
                // Hex hashes should be compared case-insensitively
                String userDigest = digest(userCredentials);
                return serverCredentials.equalsIgnoreCase(userDigest);
            }
        } else {
            // No digests, compare directly
            return serverCredentials.equals(userCredentials);
        }
    }


    /**
     * Execute a periodic task, such as reloading, etc. This method will be
     * invoked inside the classloading context of this container. Unexpected
     * throwables will be caught and logged.
     */
    @Override
    public void backgroundProcess() {
        // NOOP in base class
    }


    /**
     * Return the SecurityConstraints configured to guard the request URI for
     * this request, or <code>null</code> if there is no such constraint.
     *
     * @param request Request we are processing
     * @param context Context the Request is mapped to
     */
    @Override
    public SecurityConstraint [] findSecurityConstraints(Request request,
                                                         Context context) {

        ArrayList<SecurityConstraint> results = null;
        // Are there any defined security constraints?
        SecurityConstraint constraints[] = context.findConstraints();
        if ((constraints == null) || (constraints.length == 0)) {
            if (log.isDebugEnabled())
                log.debug("  No applicable constraints defined");
            return (null);
        }

        // Check each defined security constraint
        String uri = request.getRequestPathMB().toString();
        // Bug47080 - in rare cases this may be null
        // Mapper treats as '/' do the same to prevent NPE
        if (uri == null) {
            uri = "/";
        }

        String method = request.getMethod();
        int i;
        boolean found = false;
        for (i = 0; i < constraints.length; i++) {
            SecurityCollection [] collection = constraints[i].findCollections();

            // If collection is null, continue to avoid an NPE
            // See Bugzilla 30624
            if ( collection == null) {
                continue;
            }

            if (log.isDebugEnabled()) {
                log.debug("  Checking constraint '" + constraints[i] +
                    "' against " + method + " " + uri + " --> " +
                    constraints[i].included(uri, method));
            }

            for(int j=0; j < collection.length; j++){
                String [] patterns = collection[j].findPatterns();

                // If patterns is null, continue to avoid an NPE
                // See Bugzilla 30624
                if ( patterns == null) {
                    continue;
                }

                for(int k=0; k < patterns.length; k++) {
                    if(uri.equals(patterns[k])) {
                        found = true;
                        if(collection[j].findMethod(method)) {
                            if(results == null) {
                                results = new ArrayList<SecurityConstraint>();
                            }
                            results.add(constraints[i]);
                        }
                    }
                }
            }
        }

        if(found) {
            return resultsToArray(results);
        }

        int longest = -1;

        for (i = 0; i < constraints.length; i++) {
            SecurityCollection [] collection = constraints[i].findCollections();

            // If collection is null, continue to avoid an NPE
            // See Bugzilla 30624
            if ( collection == null) {
                continue;
            }

            if (log.isDebugEnabled()) {
                log.debug("  Checking constraint '" + constraints[i] +
                    "' against " + method + " " + uri + " --> " +
                    constraints[i].included(uri, method));
            }

            for(int j=0; j < collection.length; j++){
                String [] patterns = collection[j].findPatterns();

                // If patterns is null, continue to avoid an NPE
                // See Bugzilla 30624
                if ( patterns == null) {
                    continue;
                }

                boolean matched = false;
                int length = -1;
                for(int k=0; k < patterns.length; k++) {
                    String pattern = patterns[k];
                    if(pattern.startsWith("/") && pattern.endsWith("/*") &&
                       pattern.length() >= longest) {

                        if(pattern.length() == 2) {
                            matched = true;
                            length = pattern.length();
                        } else if(pattern.regionMatches(0,uri,0,
                                                        pattern.length()-1) ||
                                  (pattern.length()-2 == uri.length() &&
                                   pattern.regionMatches(0,uri,0,
                                                        pattern.length()-2))) {
                            matched = true;
                            length = pattern.length();
                        }
                    }
                }
                if(matched) {
                    if(length > longest) {
                        found = false;
                        if(results != null) {
                            results.clear();
                        }
                        longest = length;
                    }
                    if(collection[j].findMethod(method)) {
                        found = true;
                        if(results == null) {
                            results = new ArrayList<SecurityConstraint>();
                        }
                        results.add(constraints[i]);
                    }
                }
            }
        }

        if(found) {
            return  resultsToArray(results);
        }

        for (i = 0; i < constraints.length; i++) {
            SecurityCollection [] collection = constraints[i].findCollections();

            // If collection is null, continue to avoid an NPE
            // See Bugzilla 30624
            if ( collection == null) {
                continue;
            }

            if (log.isDebugEnabled()) {
                log.debug("  Checking constraint '" + constraints[i] +
                    "' against " + method + " " + uri + " --> " +
                    constraints[i].included(uri, method));
            }

            boolean matched = false;
            int pos = -1;
            for(int j=0; j < collection.length; j++){
                String [] patterns = collection[j].findPatterns();

                // If patterns is null, continue to avoid an NPE
                // See Bugzilla 30624
                if ( patterns == null) {
                    continue;
                }

                for(int k=0; k < patterns.length && !matched; k++) {
                    String pattern = patterns[k];
                    if(pattern.startsWith("*.")){
                        int slash = uri.lastIndexOf('/');
                        int dot = uri.lastIndexOf('.');
                        if(slash >= 0 && dot > slash &&
                           dot != uri.length()-1 &&
                           uri.length()-dot == pattern.length()-1) {
                            if(pattern.regionMatches(1,uri,dot,uri.length()-dot)) {
                                matched = true;
                                pos = j;
                            }
                        }
                    }
                }
            }
            if(matched) {
                found = true;
                if(collection[pos].findMethod(method)) {
                    if(results == null) {
                        results = new ArrayList<SecurityConstraint>();
                    }
                    results.add(constraints[i]);
                }
            }
        }

        if(found) {
            return resultsToArray(results);
        }

        for (i = 0; i < constraints.length; i++) {
            SecurityCollection [] collection = constraints[i].findCollections();

            // If collection is null, continue to avoid an NPE
            // See Bugzilla 30624
            if ( collection == null) {
                continue;
            }

            if (log.isDebugEnabled()) {
                log.debug("  Checking constraint '" + constraints[i] +
                    "' against " + method + " " + uri + " --> " +
                    constraints[i].included(uri, method));
            }

            for(int j=0; j < collection.length; j++){
                String [] patterns = collection[j].findPatterns();

                // If patterns is null, continue to avoid an NPE
                // See Bugzilla 30624
                if ( patterns == null) {
                    continue;
                }

                boolean matched = false;
                for(int k=0; k < patterns.length && !matched; k++) {
                    String pattern = patterns[k];
                    if(pattern.equals("/")){
                        matched = true;
                    }
                }
                if(matched) {
                    if(results == null) {
                        results = new ArrayList<SecurityConstraint>();
                    }
                    results.add(constraints[i]);
                }
            }
        }

        if(results == null) {
            // No applicable security constraint was found
            if (log.isDebugEnabled())
                log.debug("  No applicable constraint located");
        }
        return resultsToArray(results);
    }

    /**
     * Convert an ArrayList to a SecurityConstraint [].
     */
    private SecurityConstraint [] resultsToArray(
            ArrayList<SecurityConstraint> results) {
        if(results == null || results.size() == 0) {
            return null;
        }
        SecurityConstraint [] array = new SecurityConstraint[results.size()];
        results.toArray(array);
        return array;
    }


    /**
     * Perform access control based on the specified authorization constraint.
     * Return <code>true</code> if this constraint is satisfied and processing
     * should continue, or <code>false</code> otherwise.
     *
     * @param request Request we are processing
     * @param response Response we are creating
     * @param constraints Security constraint we are enforcing
     * @param context The Context to which client of this class is attached.
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public boolean hasResourcePermission(Request request,
                                         Response response,
                                         SecurityConstraint []constraints,
                                         Context context)
        throws IOException {

        if (constraints == null || constraints.length == 0)
            return (true);

        // Which user principal have we already authenticated?
        Principal principal = request.getPrincipal();
        boolean status = false;
        boolean denyfromall = false;
        for(int i=0; i < constraints.length; i++) {
            SecurityConstraint constraint = constraints[i];

            String roles[];
            if (constraint.getAllRoles()) {
                // * means all roles defined in web.xml
                roles = request.getContext().findSecurityRoles();
            } else {
                roles = constraint.findAuthRoles();
            }

            if (roles == null)
                roles = new String[0];

            if (log.isDebugEnabled())
                log.debug("  Checking roles " + principal);

            if (roles.length == 0 && !constraint.getAllRoles()) {
                if(constraint.getAuthConstraint()) {
                    if( log.isDebugEnabled() )
                        log.debug("No roles");
                    status = false; // No listed roles means no access at all
                    denyfromall = true;
                    break;
                }

                if(log.isDebugEnabled())
                    log.debug("Passing all access");
                status = true;
            } else if (principal == null) {
                if (log.isDebugEnabled())
                    log.debug("  No user authenticated, cannot grant access");
            } else {
                for (int j = 0; j < roles.length; j++) {
                    if (hasRole(null, principal, roles[j])) {
                        status = true;
                        if( log.isDebugEnabled() )
                            log.debug( "Role found:  " + roles[j]);
                    }
                    else if( log.isDebugEnabled() )
                        log.debug( "No role found:  " + roles[j]);
                }
            }
        }

        if (!denyfromall && allRolesMode != AllRolesMode.STRICT_MODE &&
                !status && principal != null) {
            if (log.isDebugEnabled()) {
                log.debug("Checking for all roles mode: " + allRolesMode);
            }
            // Check for an all roles(role-name="*")
            for (int i = 0; i < constraints.length; i++) {
                SecurityConstraint constraint = constraints[i];
                String roles[];
                // If the all roles mode exists, sets
                if (constraint.getAllRoles()) {
                    if (allRolesMode == AllRolesMode.AUTH_ONLY_MODE) {
                        if (log.isDebugEnabled()) {
                            log.debug("Granting access for role-name=*, auth-only");
                        }
                        status = true;
                        break;
                    }

                    // For AllRolesMode.STRICT_AUTH_ONLY_MODE there must be zero roles
                    roles = request.getContext().findSecurityRoles();
                    if (roles.length == 0 && allRolesMode == AllRolesMode.STRICT_AUTH_ONLY_MODE) {
                        if (log.isDebugEnabled()) {
                            log.debug("Granting access for role-name=*, strict auth-only");
                        }
                        status = true;
                        break;
                    }
                }
            }
        }

        // Return a "Forbidden" message denying access to this resource
        if(!status) {
            response.sendError
                (HttpServletResponse.SC_FORBIDDEN,
                 sm.getString("realmBase.forbidden"));
        }
        return status;

    }


    /**
     * Return <code>true</code> if the specified Principal has the specified
     * security role, within the context of this Realm; otherwise return
     * <code>false</code>.  This method can be overridden by Realm
     * implementations, but the default is adequate when an instance of
     * <code>GenericPrincipal</code> is used to represent authenticated
     * Principals from this Realm.
     *
     * @param principal Principal for whom the role is to be checked
     * @param role Security role to be checked
     */
    @Override
    public boolean hasRole(Wrapper wrapper, Principal principal, String role) {
        // Check for a role alias defined in a <security-role-ref> element
        if (wrapper != null) {
            String realRole = wrapper.findSecurityReference(role);
            if (realRole != null)
                role = realRole;
        }

        // Should be overridden in JAASRealm - to avoid pretty inefficient conversions
        if ((principal == null) || (role == null) ||
            !(principal instanceof GenericPrincipal))
            return (false);

        GenericPrincipal gp = (GenericPrincipal) principal;
        boolean result = gp.hasRole(role);
        if (log.isDebugEnabled()) {
            String name = principal.getName();
            if (result)
                log.debug(sm.getString("realmBase.hasRoleSuccess", name, role));
            else
                log.debug(sm.getString("realmBase.hasRoleFailure", name, role));
        }
        return (result);

    }


    /**
     * Enforce any user data constraint required by the security constraint
     * guarding this request URI.  Return <code>true</code> if this constraint
     * was not violated and processing should continue, or <code>false</code>
     * if we have created a response already.
     *
     * @param request Request we are processing
     * @param response Response we are creating
     * @param constraints Security constraint being checked
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public boolean hasUserDataPermission(Request request,
                                         Response response,
                                         SecurityConstraint []constraints)
        throws IOException {

        // Is there a relevant user data constraint?
        if (constraints == null || constraints.length == 0) {
            if (log.isDebugEnabled())
                log.debug("  No applicable security constraint defined");
            return (true);
        }
        for(int i=0; i < constraints.length; i++) {
            SecurityConstraint constraint = constraints[i];
            String userConstraint = constraint.getUserConstraint();
            if (userConstraint == null) {
                if (log.isDebugEnabled())
                    log.debug("  No applicable user data constraint defined");
                return (true);
            }
            if (userConstraint.equals(Constants.NONE_TRANSPORT)) {
                if (log.isDebugEnabled())
                    log.debug("  User data constraint has no restrictions");
                return (true);
            }

        }
        // Validate the request against the user data constraint
        if (request.getRequest().isSecure()) {
            if (log.isDebugEnabled())
                log.debug("  User data constraint already satisfied");
            return (true);
        }
        // Initialize variables we need to determine the appropriate action
        int redirectPort = request.getConnector().getRedirectPort();

        // Is redirecting disabled?
        if (redirectPort <= 0) {
            if (log.isDebugEnabled())
                log.debug("  SSL redirect is disabled");
            response.sendError
                (HttpServletResponse.SC_FORBIDDEN,
                 request.getRequestURI());
            return (false);
        }

        // Redirect to the corresponding SSL port
        StringBuilder file = new StringBuilder();
        String protocol = "https";
        String host = request.getServerName();
        // Protocol
        file.append(protocol).append("://").append(host);
        // Host with port
        if(redirectPort != 443) {
            file.append(":").append(redirectPort);
        }
        // URI
        file.append(request.getRequestURI());
        String requestedSessionId = request.getRequestedSessionId();
        if ((requestedSessionId != null) &&
            request.isRequestedSessionIdFromURL()) {
            file.append(";");
            file.append(SessionConfig.getSessionUriParamName(
                    request.getContext()));
            file.append("=");
            file.append(requestedSessionId);
        }
        String queryString = request.getQueryString();
        if (queryString != null) {
            file.append('?');
            file.append(queryString);
        }
        if (log.isDebugEnabled())
            log.debug("  Redirecting to " + file.toString());
        response.sendRedirect(file.toString(), transportGuaranteeRedirectStatus);
        return (false);

    }


    /**
     * Remove a property change listener from this component.
     *
     * @param listener The listener to remove
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

        support.removePropertyChangeListener(listener);

    }


    @Override
    protected void initInternal() throws LifecycleException {

        super.initInternal();

        // We want logger as soon as possible
        if (container != null) {
            this.containerLog = container.getLogger();
        }

        x509UsernameRetriever = createUsernameRetriever(x509UsernameRetrieverClassName);
    }

    /**
     * Prepare for the beginning of active use of the public methods of this
     * component and implement the requirements of
     * {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected void startInternal() throws LifecycleException {

        // Create a MessageDigest instance for credentials, if desired
        if (digest != null) {
            try {
                md = MessageDigest.getInstance(digest);
            } catch (NoSuchAlgorithmException e) {
                throw new LifecycleException
                    (sm.getString("realmBase.algorithm", digest), e);
            }
        }

        setState(LifecycleState.STARTING);
    }


    /**
     * Gracefully terminate the active use of the public methods of this
     * component and implement the requirements of
     * {@link org.apache.catalina.util.LifecycleBase#stopInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that needs to be reported
     */
    @Override
    protected void stopInternal() throws LifecycleException {

        setState(LifecycleState.STOPPING);

        // Clean up allocated resources
        md = null;
    }


    /**
     * Return a String representation of this component.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Realm[");
        sb.append(getName());
        sb.append(']');
        return sb.toString();
    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Digest the password using the specified algorithm and
     * convert the result to a corresponding hexadecimal string.
     * If exception, the plain credentials string is returned.
     *
     * @param credentials Password or other credentials to use in
     *  authenticating this username
     */
    protected String digest(String credentials)  {

        // If no MessageDigest instance is specified, return unchanged
        if (hasMessageDigest() == false)
            return (credentials);

        // Digest the user credentials and return as hexadecimal
        synchronized (this) {
            try {
                md.reset();

                byte[] bytes = null;
                try {
                    bytes = credentials.getBytes(getDigestCharset());
                } catch (UnsupportedEncodingException uee) {
                    log.error("Illegal digestEncoding: " + getDigestEncoding(), uee);
                    throw new IllegalArgumentException(uee.getMessage());
                }
                md.update(bytes);

                return (HexUtils.toHexString(md.digest()));
            } catch (Exception e) {
                log.error(sm.getString("realmBase.digest"), e);
                return (credentials);
            }
        }

    }

    protected boolean hasMessageDigest() {
        return !(md == null);
    }

    /**
     * Return the digest associated with given principal's user name.
     */
    protected String getDigest(String username, String realmName) {
        if (md5Helper == null) {
            try {
                md5Helper = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                log.error("Couldn't get MD5 digest: ", e);
                throw new IllegalStateException(e.getMessage());
            }
        }

        if (hasMessageDigest()) {
            // Use pre-generated digest
            return getPassword(username);
        }

        String digestValue = username + ":" + realmName + ":"
            + getPassword(username);

        byte[] valueBytes = null;
        try {
            valueBytes = digestValue.getBytes(getDigestCharset());
        } catch (UnsupportedEncodingException uee) {
            log.error("Illegal digestEncoding: " + getDigestEncoding(), uee);
            throw new IllegalArgumentException(uee.getMessage());
        }

        byte[] digest = null;
        // Bugzilla 32137
        synchronized(md5Helper) {
            digest = md5Helper.digest(valueBytes);
        }

        return MD5Encoder.encode(digest);
    }


    /**
     * Return a short name for this Realm implementation, for use in
     * log messages.
     */
    protected abstract String getName();


    /**
     * Return the password associated with the given principal's user name.
     */
    protected abstract String getPassword(String username);


    /**
     * Return the Principal associated with the given certificate.
     */
    protected Principal getPrincipal(X509Certificate usercert) {
        String username = x509UsernameRetriever.getUsername(usercert);

        if(log.isDebugEnabled())
            log.debug(sm.getString("realmBase.gotX509Username", username));

        return(getPrincipal(username));
    }


    /**
     * Return the Principal associated with the given user name.
     */
    protected abstract Principal getPrincipal(String username);


    protected Principal getPrincipal(String username,
            GSSCredential gssCredential) {
        Principal p = getPrincipal(username);

        if (p instanceof GenericPrincipal) {
            ((GenericPrincipal) p).setGssCredential(gssCredential);
        }

        return p;
    }

    /**
     * Return the Server object that is the ultimate parent for the container
     * with which this Realm is associated. If the server cannot be found (eg
     * because the container hierarchy is not complete), <code>null</code> is
     * returned.
     */
    protected Server getServer() {
        Container c = container;
        if (c instanceof Context) {
            c = c.getParent();
        }
        if (c instanceof Host) {
            c = c.getParent();
        }
        if (c instanceof Engine) {
            Service s = ((Engine)c).getService();
            if (s != null) {
                return s.getServer();
            }
        }
        return null;
    }


    // --------------------------------------------------------- Static Methods


    /**
     * Digest password using the algorithm specified and
     * convert the result to a corresponding hex string.
     * If exception, the plain credentials string is returned
     *
     * @param credentials Password or other credentials to use in
     *  authenticating this username
     * @param algorithm Algorithm used to do the digest
     * @param encoding Character encoding of the string to digest
     */
    public static final String Digest(String credentials, String algorithm,
                                      String encoding) {

        try {
            // Obtain a new message digest with "digest" encryption
            MessageDigest md =
                (MessageDigest) MessageDigest.getInstance(algorithm).clone();

            // encode the credentials
            // Should use the digestEncoding, but that's not a static field
            if (encoding == null) {
                md.update(credentials.getBytes());
            } else {
                md.update(credentials.getBytes(encoding));
            }

            // Digest the credentials and return as hexadecimal
            return (HexUtils.toHexString(md.digest()));
        } catch(Exception ex) {
            log.error(ex);
            return credentials;
        }

    }


    /**
     * Digest password using the algorithm specified and
     * convert the result to a corresponding hex string.
     * If exception, the plain credentials string is returned
     */
    public static void main(String args[]) {

        String encoding = null;
        int firstCredentialArg = 2;

        if (args.length > 4 && args[2].equalsIgnoreCase("-e")) {
            encoding = args[3];
            firstCredentialArg = 4;
        }

        if(args.length > firstCredentialArg && args[0].equalsIgnoreCase("-a")) {
            for(int i=firstCredentialArg; i < args.length ; i++){
                System.out.print(args[i]+":");
                System.out.println(Digest(args[i], args[1], encoding));
            }
        } else {
            System.out.println
                ("Usage: RealmBase -a <algorithm> [-e <encoding>] <credentials>");
        }

    }


    // -------------------- JMX and Registration  --------------------

    @Override
    public String getObjectNameKeyProperties() {

        StringBuilder keyProperties = new StringBuilder("type=Realm");
        keyProperties.append(getRealmSuffix());
        keyProperties.append(MBeanUtils.getContainerKeyProperties(container));

        return keyProperties.toString();
    }

    @Override
    public String getDomainInternal() {
        return MBeanUtils.getDomain(container);
    }

    protected String realmPath = "/realm0";

    public String getRealmPath() {
        return realmPath;
    }

    public void setRealmPath(String theRealmPath) {
        realmPath = theRealmPath;
    }

    protected String getRealmSuffix() {
        return ",realmPath=" + getRealmPath();
    }


    protected static class AllRolesMode {

        private String name;
        /** Use the strict servlet spec interpretation which requires that the user
         * have one of the web-app/security-role/role-name
         */
        public static final AllRolesMode STRICT_MODE = new AllRolesMode("strict");
        /** Allow any authenticated user
         */
        public static final AllRolesMode AUTH_ONLY_MODE = new AllRolesMode("authOnly");
        /** Allow any authenticated user only if there are no web-app/security-roles
         */
        public static final AllRolesMode STRICT_AUTH_ONLY_MODE = new AllRolesMode("strictAuthOnly");

        static AllRolesMode toMode(String name)
        {
            AllRolesMode mode;
            if( name.equalsIgnoreCase(STRICT_MODE.name) )
                mode = STRICT_MODE;
            else if( name.equalsIgnoreCase(AUTH_ONLY_MODE.name) )
                mode = AUTH_ONLY_MODE;
            else if( name.equalsIgnoreCase(STRICT_AUTH_ONLY_MODE.name) )
                mode = STRICT_AUTH_ONLY_MODE;
            else
                throw new IllegalStateException("Unknown mode, must be one of: strict, authOnly, strictAuthOnly");
            return mode;
        }

        private AllRolesMode(String name)
        {
            this.name = name;
        }

        @Override
        public boolean equals(Object o)
        {
            boolean equals = false;
            if( o instanceof AllRolesMode )
            {
                AllRolesMode mode = (AllRolesMode) o;
                equals = name.equals(mode.name);
            }
            return equals;
        }
        @Override
        public int hashCode()
        {
            return name.hashCode();
        }
        @Override
        public String toString()
        {
            return name;
        }
    }

    private static X509UsernameRetriever createUsernameRetriever(String className)
        throws LifecycleException {
        if(null == className || "".equals(className.trim()))
            return new X509SubjectDnRetriever();

        try {
            @SuppressWarnings("unchecked")
            Class<? extends X509UsernameRetriever> clazz = (Class<? extends X509UsernameRetriever>)Class.forName(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new LifecycleException(sm.getString("realmBase.createUsernameRetriever.ClassNotFoundException", className), e);
        } catch (InstantiationException e) {
            throw new LifecycleException(sm.getString("realmBase.createUsernameRetriever.InstantiationException", className), e);
        } catch (IllegalAccessException e) {
            throw new LifecycleException(sm.getString("realmBase.createUsernameRetriever.IllegalAccessException", className), e);
        } catch (ClassCastException e) {
            throw new LifecycleException(sm.getString("realmBase.createUsernameRetriever.ClassCastException", className), e);
        }
    }
}
