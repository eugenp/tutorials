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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.ServiceUnavailableException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.StartTlsRequest;
import javax.naming.ldap.StartTlsResponse;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import org.apache.catalina.LifecycleException;
import org.ietf.jgss.GSSCredential;

/**
 * <p>Implementation of <strong>Realm</strong> that works with a directory
 * server accessed via the Java Naming and Directory Interface (JNDI) APIs.
 * The following constraints are imposed on the data structure in the
 * underlying directory server:</p>
 * <ul>
 *
 * <li>Each user that can be authenticated is represented by an individual
 *     element in the top level <code>DirContext</code> that is accessed
 *     via the <code>connectionURL</code> property.</li>
 *
 * <li>If a socket connection can not be made to the <code>connectURL</code>
 *     an attempt will be made to use the <code>alternateURL</code> if it
 *     exists.</li>
 *
 * <li>Each user element has a distinguished name that can be formed by
 *     substituting the presented username into a pattern configured by the
 *     <code>userPattern</code> property.</li>
 *
 * <li>Alternatively, if the <code>userPattern</code> property is not
 *     specified, a unique element can be located by searching the directory
 *     context. In this case:
 *     <ul>
 *     <li>The <code>userSearch</code> pattern specifies the search filter
 *         after substitution of the username.</li>
 *     <li>The <code>userBase</code> property can be set to the element that
 *         is the base of the subtree containing users.  If not specified,
 *         the search base is the top-level context.</li>
 *     <li>The <code>userSubtree</code> property can be set to
 *         <code>true</code> if you wish to search the entire subtree of the
 *         directory context.  The default value of <code>false</code>
 *         requests a search of only the current level.</li>
 *    </ul>
 * </li>
 *
 * <li>The user may be authenticated by binding to the directory with the
 *      username and password presented. This method is used when the
 *      <code>userPassword</code> property is not specified.</li>
 *
 * <li>The user may be authenticated by retrieving the value of an attribute
 *     from the directory and comparing it explicitly with the value presented
 *     by the user. This method is used when the <code>userPassword</code>
 *     property is specified, in which case:
 *     <ul>
 *     <li>The element for this user must contain an attribute named by the
 *         <code>userPassword</code> property.
 *     <li>The value of the user password attribute is either a cleartext
 *         String, or the result of passing a cleartext String through the
 *         <code>RealmBase.digest()</code> method (using the standard digest
 *         support included in <code>RealmBase</code>).
 *     <li>The user is considered to be authenticated if the presented
 *         credentials (after being passed through
 *         <code>RealmBase.digest()</code>) are equal to the retrieved value
 *         for the user password attribute.</li>
 *     </ul></li>
 *
 * <li>Each group of users that has been assigned a particular role may be
 *     represented by an individual element in the top level
 *     <code>DirContext</code> that is accessed via the
 *     <code>connectionURL</code> property.  This element has the following
 *     characteristics:
 *     <ul>
 *     <li>The set of all possible groups of interest can be selected by a
 *         search pattern configured by the <code>roleSearch</code>
 *         property.</li>
 *     <li>The <code>roleSearch</code> pattern optionally includes pattern
 *         replacements "{0}" for the distinguished name, and/or "{1}" for
 *         the username, and/or "{2}" the value of an attribute from the
 *         user's directory entry (the attribute is specified by the
 *         <code>userRoleAttribute</code> property), of the authenticated user
 *         for which roles will be retrieved.</li>
 *     <li>The <code>roleBase</code> property can be set to the element that
 *         is the base of the search for matching roles.  If not specified,
 *         the entire context will be searched.</li>
 *     <li>The <code>roleSubtree</code> property can be set to
 *         <code>true</code> if you wish to search the entire subtree of the
 *         directory context.  The default value of <code>false</code>
 *         requests a search of only the current level.</li>
 *     <li>The element includes an attribute (whose name is configured by
 *         the <code>roleName</code> property) containing the name of the
 *         role represented by this element.</li>
 *     </ul></li>
 *
 * <li>In addition, roles may be represented by the values of an attribute
 * in the user's element whose name is configured by the
 * <code>userRoleName</code> property.</li>
 *
 * <li>A default role can be assigned to each user that was successfully
 * authenticated by setting the <code>commonRole</code> property to the
 * name of this role. The role doesn't have to exist in the directory.</li>
 *
 * <li>If the directory server contains nested roles, you can search for them
 * by setting <code>roleNested</code> to <code>true</code>.
 * The default value is <code>false</code>, so role searches will not find
 * nested roles.</li>
 *
 * <li>Note that the standard <code>&lt;security-role-ref&gt;</code> element in
 *     the web application deployment descriptor allows applications to refer
 *     to roles programmatically by names other than those used in the
 *     directory server itself.</li>
 * </ul>
 *
 * <p><strong>TODO</strong> - Support connection pooling (including message
 * format objects) so that <code>authenticate()</code> does not have to be
 * synchronized.</p>
 *
 * <p><strong>WARNING</strong> - There is a reported bug against the Netscape
 * provider code (com.netscape.jndi.ldap.LdapContextFactory) with respect to
 * successfully authenticated a non-existing user. The
 * report is here: http://bz.apache.org/bugzilla/show_bug.cgi?id=11210 .
 * With luck, Netscape has updated their provider code and this is not an
 * issue. </p>
 *
 * @author John Holman
 * @author Craig R. McClanahan
 */
public class JNDIRealm extends RealmBase {


    // ----------------------------------------------------- Instance Variables

    /**
     *  The type of authentication to use
     */
    protected String authentication = null;

    /**
     * The connection username for the server we will contact.
     */
    protected String connectionName = null;


    /**
     * The connection password for the server we will contact.
     */
    protected String connectionPassword = null;


    /**
     * The connection URL for the server we will contact.
     */
    protected String connectionURL = null;


    /**
     * The directory context linking us to our directory server.
     */
    protected DirContext context = null;


    /**
     * The JNDI context factory used to acquire our InitialContext.  By
     * default, assumes use of an LDAP server using the standard JNDI LDAP
     * provider.
     */
    protected String contextFactory = "com.sun.jndi.ldap.LdapCtxFactory";


    /**
     * How aliases should be dereferenced during search operations.
     */
    protected String derefAliases = null;

    /**
     * Constant that holds the name of the environment property for specifying
     * the manner in which aliases should be dereferenced.
     */
    public static final String DEREF_ALIASES = "java.naming.ldap.derefAliases";

    /**
     * Descriptive information about this Realm implementation.
     */
    protected static final String info =
        "org.apache.catalina.realm.JNDIRealm/1.0";


    /**
     * Descriptive information about this Realm implementation.
     */
    protected static final String name = "JNDIRealm";


    /**
     * The protocol that will be used in the communication with the
     * directory server.
     */
    protected String protocol = null;


    /**
     * Should we ignore PartialResultExceptions when iterating over NamingEnumerations?
     * Microsoft Active Directory often returns referrals, which lead
     * to PartialResultExceptions. Unfortunately there's no stable way to detect,
     * if the Exceptions really come from an AD referral.
     * Set to true to ignore PartialResultExceptions.
     */
    protected boolean adCompat = false;


    /**
     * How should we handle referrals?  Microsoft Active Directory often returns
     * referrals. If you need to follow them set referrals to "follow".
     * Caution: if your DNS is not part of AD, the LDAP client lib might try
     * to resolve your domain name in DNS to find another LDAP server.
     */
    protected String referrals = null;


    /**
     * The base element for user searches.
     */
    protected String userBase = "";


    /**
     * The message format used to search for a user, with "{0}" marking
     * the spot where the username goes.
     */
    protected String userSearch = null;


    /**
     * When searching for users, should the search be performed as the user
     * currently being authenticated? If false, {@link #connectionName} and
     * {@link #connectionPassword} will be used if specified, else an anonymous
     * connection will be used.
     */
    private boolean userSearchAsUser = false;


    /**
     * The MessageFormat object associated with the current
     * <code>userSearch</code>.
     */
    protected MessageFormat userSearchFormat = null;


    /**
     * Should we search the entire subtree for matching users?
     */
    protected boolean userSubtree = false;


    /**
     * The attribute name used to retrieve the user password.
     */
    protected String userPassword = null;

    /**
     * The name of the attribute inside the users
     * directory entry where the value will be
     * taken to search for roles
     * This attribute is not used during a nested search
     */
    protected String userRoleAttribute = null;


    /**
     * A string of LDAP user patterns or paths, ":"-separated
     * These will be used to form the distinguished name of a
     * user, with "{0}" marking the spot where the specified username
     * goes.
     * This is similar to userPattern, but allows for multiple searches
     * for a user.
     */
    protected String[] userPatternArray = null;


    /**
     * The message format used to form the distinguished name of a
     * user, with "{0}" marking the spot where the specified username
     * goes.
     */
    protected String userPattern = null;


    /**
     * An array of MessageFormat objects associated with the current
     * <code>userPatternArray</code>.
     */
    protected MessageFormat[] userPatternFormatArray = null;

    /**
     * The base element for role searches.
     */
    protected String roleBase = "";


    /**
     * The MessageFormat object associated with the current
     * <code>roleBase</code>.
     */
    protected MessageFormat roleBaseFormat = null;


    /**
     * The MessageFormat object associated with the current
     * <code>roleSearch</code>.
     */
    protected MessageFormat roleFormat = null;


    /**
     * The name of an attribute in the user's entry containing
     * roles for that user
     */
    protected String userRoleName = null;


    /**
     * The name of the attribute containing roles held elsewhere
     */
    protected String roleName = null;


    /**
     * The message format used to select roles for a user, with "{0}" marking
     * the spot where the distinguished name of the user goes. The "{1}"
     * and "{2}" are described in the Configuration Reference.
     */
    protected String roleSearch = null;


    /**
     * Should we search the entire subtree for matching memberships?
     */
    protected boolean roleSubtree = false;

    /**
     * Should we look for nested group in order to determine roles?
     */
    protected boolean roleNested = false;

    /**
     * When searching for user roles, should the search be performed as the user
     * currently being authenticated? If false, {@link #connectionName} and
     * {@link #connectionPassword} will be used if specified, else an anonymous
     * connection will be used.
     */
    protected boolean roleSearchAsUser = false;

    /**
     * An alternate URL, to which, we should connect if connectionURL fails.
     */
    protected String alternateURL;

    /**
     * The number of connection attempts.  If greater than zero we use the
     * alternate url.
     */
    protected int connectionAttempt = 0;

    /**
     *  Add this role to every authenticated user
     */
    protected String commonRole = null;


    /**
     * The timeout, in milliseconds, to use when trying to create a connection
     * to the directory. The default is 5000 (5 seconds).
     */
    protected String connectionTimeout = "5000";

    /**
     * The timeout, in milliseconds, to use when trying to read from a connection
     * to the directory. The default is 5000 (5 seconds).
     */
    protected String readTimeout = "5000";

    /**
     * The sizeLimit (also known as the countLimit) to use when the realm is
     * configured with {@link #userSearch}. Zero for no limit.
     */
    protected long sizeLimit = 0;

    /**
     * The timeLimit (in milliseconds) to use when the realm is configured with
     * {@link #userSearch}. Zero for no limit.
     */
    protected int timeLimit = 0;


    /**
     * Should delegated credentials from the SPNEGO authenticator be used if
     * available
     */
    protected boolean useDelegatedCredential = true;


    /**
     * The QOP that should be used for the connection to the LDAP server after
     * authentication. This value is used to set the
     * <code>javax.security.sasl.qop</code> environment property for the LDAP
     * connection.
     */
    protected String spnegoDelegationQop = "auth-conf";

    /**
     * Whether to use TLS for connections
     */
    private boolean useStartTls = false;

    private StartTlsResponse tls = null;

    /**
     * The list of enabled cipher suites used for establishing tls connections.
     * <code>null</code> means to use the default cipher suites.
     */
    private String[] cipherSuitesArray = null;

    /**
     * Verifier for hostnames in a StartTLS secured connection. <code>null</code>
     * means to use the default verifier.
     */
    private HostnameVerifier hostnameVerifier = null;

    /**
     * {@link SSLSocketFactory} to use when connection with StartTLS enabled.
     */
    private SSLSocketFactory sslSocketFactory = null;

    /**
     * Name of the class of the {@link SSLSocketFactory}. <code>null</code>
     * means to use the default factory.
     */
    private String sslSocketFactoryClassName;

    /**
     * Comma separated list of cipher suites to use for StartTLS. If empty, the
     * default suites are used.
     */
    private String cipherSuites;

    /**
     * Name of the class of the {@link HostnameVerifier}. <code>null</code>
     * means to use the default verifier.
     */
    private String hostNameVerifierClassName;

    /**
     * The ssl Protocol which will be used by StartTLS.
     */
    private String sslProtocol;

    // ------------------------------------------------------------- Properties

    /**
     * Return the type of authentication to use.
     */
    public String getAuthentication() {

        return authentication;

    }

    /**
     * Set the type of authentication to use.
     *
     * @param authentication The authentication
     */
    public void setAuthentication(String authentication) {

        this.authentication = authentication;

    }

    /**
     * Return the connection username for this Realm.
     */
    public String getConnectionName() {

        return this.connectionName;

    }


    /**
     * Set the connection username for this Realm.
     *
     * @param connectionName The new connection username
     */
    public void setConnectionName(String connectionName) {

        this.connectionName = connectionName;

    }


    /**
     * Return the connection password for this Realm.
     */
    public String getConnectionPassword() {

        return this.connectionPassword;

    }


    /**
     * Set the connection password for this Realm.
     *
     * @param connectionPassword The new connection password
     */
    public void setConnectionPassword(String connectionPassword) {

        this.connectionPassword = connectionPassword;

    }


    /**
     * Return the connection URL for this Realm.
     */
    public String getConnectionURL() {

        return this.connectionURL;

    }


    /**
     * Set the connection URL for this Realm.
     *
     * @param connectionURL The new connection URL
     */
    public void setConnectionURL(String connectionURL) {

        this.connectionURL = connectionURL;

    }


    /**
     * Return the JNDI context factory for this Realm.
     */
    public String getContextFactory() {

        return this.contextFactory;

    }


    /**
     * Set the JNDI context factory for this Realm.
     *
     * @param contextFactory The new context factory
     */
    public void setContextFactory(String contextFactory) {

        this.contextFactory = contextFactory;

    }

    /**
     * Return the derefAliases setting to be used.
     */
    public java.lang.String getDerefAliases() {
        return derefAliases;
    }

    /**
     * Set the value for derefAliases to be used when searching the directory.
     *
     * @param derefAliases New value of property derefAliases.
     */
    public void setDerefAliases(java.lang.String derefAliases) {
      this.derefAliases = derefAliases;
    }

    /**
     * Return the protocol to be used.
     */
    public String getProtocol() {

        return protocol;

    }

    /**
     * Set the protocol for this Realm.
     *
     * @param protocol The new protocol.
     */
    public void setProtocol(String protocol) {

        this.protocol = protocol;

    }


    /**
     * Returns the current settings for handling PartialResultExceptions
     */
    public boolean getAdCompat () {
        return adCompat;
    }


    /**
     * How do we handle PartialResultExceptions?
     * True: ignore all PartialResultExceptions.
     */
    public void setAdCompat (boolean adCompat) {
        this.adCompat = adCompat;
    }


    /**
     * Returns the current settings for handling JNDI referrals.
     */
    public String getReferrals () {
        return referrals;
    }


    /**
     * How do we handle JNDI referrals? ignore, follow, or throw
     * (see javax.naming.Context.REFERRAL for more information).
     */
    public void setReferrals (String referrals) {
        this.referrals = referrals;
    }


    /**
     * Return the base element for user searches.
     */
    public String getUserBase() {

        return this.userBase;

    }


    /**
     * Set the base element for user searches.
     *
     * @param userBase The new base element
     */
    public void setUserBase(String userBase) {

        this.userBase = userBase;

    }


    /**
     * Return the message format pattern for selecting users in this Realm.
     */
    public String getUserSearch() {

        return this.userSearch;

    }


    /**
     * Set the message format pattern for selecting users in this Realm.
     *
     * @param userSearch The new user search pattern
     */
    public void setUserSearch(String userSearch) {

        this.userSearch = userSearch;
        if (userSearch == null)
            userSearchFormat = null;
        else
            userSearchFormat = new MessageFormat(userSearch);

    }


    public boolean isUserSearchAsUser() {
        return userSearchAsUser;
    }


    public void setUserSearchAsUser(boolean userSearchAsUser) {
        this.userSearchAsUser = userSearchAsUser;
    }


    /**
     * Return the "search subtree for users" flag.
     */
    public boolean getUserSubtree() {

        return this.userSubtree;

    }


    /**
     * Set the "search subtree for users" flag.
     *
     * @param userSubtree The new search flag
     */
    public void setUserSubtree(boolean userSubtree) {

        this.userSubtree = userSubtree;

    }


    /**
     * Return the user role name attribute name for this Realm.
     */
    public String getUserRoleName() {

        return userRoleName;
    }


    /**
     * Set the user role name attribute name for this Realm.
     *
     * @param userRoleName The new userRole name attribute name
     */
    public void setUserRoleName(String userRoleName) {

        this.userRoleName = userRoleName;

    }


    /**
     * Return the base element for role searches.
     */
    public String getRoleBase() {

        return this.roleBase;

    }


    /**
     * Set the base element for role searches.
     *
     * @param roleBase The new base element
     */
    public void setRoleBase(String roleBase) {

        this.roleBase = roleBase;
        if (roleBase == null)
            roleBaseFormat = null;
        else
            roleBaseFormat = new MessageFormat(roleBase);

    }


    /**
     * Return the role name attribute name for this Realm.
     */
    public String getRoleName() {

        return this.roleName;

    }


    /**
     * Set the role name attribute name for this Realm.
     *
     * @param roleName The new role name attribute name
     */
    public void setRoleName(String roleName) {

        this.roleName = roleName;

    }


    /**
     * Return the message format pattern for selecting roles in this Realm.
     */
    public String getRoleSearch() {

        return this.roleSearch;

    }


    /**
     * Set the message format pattern for selecting roles in this Realm.
     *
     * @param roleSearch The new role search pattern
     */
    public void setRoleSearch(String roleSearch) {

        this.roleSearch = roleSearch;
        if (roleSearch == null)
            roleFormat = null;
        else
            roleFormat = new MessageFormat(roleSearch);

    }


    public boolean isRoleSearchAsUser() {
        return roleSearchAsUser;
    }


    public void setRoleSearchAsUser(boolean roleSearchAsUser) {
        this.roleSearchAsUser = roleSearchAsUser;
    }


    /**
     * Return the "search subtree for roles" flag.
     */
    public boolean getRoleSubtree() {

        return this.roleSubtree;

    }


    /**
     * Set the "search subtree for roles" flag.
     *
     * @param roleSubtree The new search flag
     */
    public void setRoleSubtree(boolean roleSubtree) {

        this.roleSubtree = roleSubtree;

    }

    /**
     * Return the "The nested group search flag" flag.
     */
    public boolean getRoleNested() {

        return this.roleNested;

    }


    /**
     * Set the "search subtree for roles" flag.
     *
     * @param roleNested The nested group search flag
     */
    public void setRoleNested(boolean roleNested) {

        this.roleNested = roleNested;

    }


    /**
     * Return the password attribute used to retrieve the user password.
     */
    public String getUserPassword() {

        return this.userPassword;

    }


    /**
     * Set the password attribute used to retrieve the user password.
     *
     * @param userPassword The new password attribute
     */
    public void setUserPassword(String userPassword) {

        this.userPassword = userPassword;

    }


    public String getUserRoleAttribute() {
        return userRoleAttribute;
    }

    public void setUserRoleAttribute(String userRoleAttribute) {
        this.userRoleAttribute = userRoleAttribute;
    }

    /**
     * Return the message format pattern for selecting users in this Realm.
     */
    public String getUserPattern() {

        return this.userPattern;

    }




    /**
     * Set the message format pattern for selecting users in this Realm.
     * This may be one simple pattern, or multiple patterns to be tried,
     * separated by parentheses. (for example, either "cn={0}", or
     * "(cn={0})(cn={0},o=myorg)" Full LDAP search strings are also supported,
     * but only the "OR", "|" syntax, so "(|(cn={0})(cn={0},o=myorg))" is
     * also valid. Complex search strings with &, etc are NOT supported.
     *
     * @param userPattern The new user pattern
     */
    public void setUserPattern(String userPattern) {

        this.userPattern = userPattern;
        if (userPattern == null)
            userPatternArray = null;
        else {
            userPatternArray = parseUserPatternString(userPattern);
            int len = this.userPatternArray.length;
            userPatternFormatArray = new MessageFormat[len];
            for (int i=0; i < len; i++) {
                userPatternFormatArray[i] =
                    new MessageFormat(userPatternArray[i]);
            }
        }
    }


    /**
     * Getter for property alternateURL.
     *
     * @return Value of property alternateURL.
     */
    public String getAlternateURL() {

        return this.alternateURL;

    }


    /**
     * Setter for property alternateURL.
     *
     * @param alternateURL New value of property alternateURL.
     */
    public void setAlternateURL(String alternateURL) {

        this.alternateURL = alternateURL;

    }


    /**
     * Return the common role
     */
    public String getCommonRole() {

        return commonRole;

    }


    /**
     * Set the common role
     *
     * @param commonRole The common role
     */
    public void setCommonRole(String commonRole) {

        this.commonRole = commonRole;

    }


    /**
     * Return the connection timeout.
     */
    public String getConnectionTimeout() {

        return connectionTimeout;

    }


    /**
     * Set the connection timeout.
     *
     * @param timeout The new connection timeout
     */
    public void setConnectionTimeout(String timeout) {

        this.connectionTimeout = timeout;

    }

    /**
     * @return the read timeout.
     */
    public String getReadTimeout() {

        return readTimeout;

    }


    /**
     * Set the read timeout.
     *
     * @param timeout The new read timeout
     */
    public void setReadTimeout(String timeout) {

        this.readTimeout = timeout;

    }


    public long getSizeLimit() {
        return sizeLimit;
    }


    public void setSizeLimit(long sizeLimit) {
        this.sizeLimit = sizeLimit;
    }


    public int getTimeLimit() {
        return timeLimit;
    }


    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }


    public boolean isUseDelegatedCredential() {
        return useDelegatedCredential;
    }

    public void setUseDelegatedCredential(boolean useDelegatedCredential) {
        this.useDelegatedCredential = useDelegatedCredential;
    }


    public String getSpnegoDelegationQop() {
        return spnegoDelegationQop;
    }

    public void setSpnegoDelegationQop(String spnegoDelegationQop) {
        this.spnegoDelegationQop = spnegoDelegationQop;
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
     * @return flag whether to use StartTLS for connections to the ldap server
     */
    public boolean getUseStartTls() {
        return useStartTls;
    }

    /**
     * Flag whether StartTLS should be used when connecting to the ldap server
     *
     * @param useStartTls
     *            {@code true} when StartTLS should be used. Default is
     *            {@code false}.
     */
    public void setUseStartTls(boolean useStartTls) {
        this.useStartTls = useStartTls;
    }

    /**
     * @return list of the allowed cipher suites when connections are made using
     *         StartTLS
     */
    private String[] getCipherSuitesArray() {
        if (cipherSuites == null || cipherSuitesArray != null) {
            return cipherSuitesArray;
        }
        if (this.cipherSuites.trim().isEmpty()) {
            containerLog.warn(sm.getString("jndiRealm.emptyCipherSuites"));
            this.cipherSuitesArray = null;
        } else {
            this.cipherSuitesArray = cipherSuites.trim().split("\\s*,\\s*");
            containerLog.debug(sm.getString("jndiRealm.cipherSuites",
                    Arrays.asList(this.cipherSuitesArray)));
        }
        return this.cipherSuitesArray;
    }

    /**
     * Set the allowed cipher suites when opening a connection using StartTLS.
     * The cipher suites are expected as a comma separated list.
     *
     * @param suites
     *            comma separated list of allowed cipher suites
     */
    public void setCipherSuites(String suites) {
        this.cipherSuites = suites;
    }

    /**
     * @return name of the {@link HostnameVerifier} class used for connections
     *         using StartTLS, or the empty string, if the default verifier
     *         should be used.
     */
    public String getHostnameVerifierClassName() {
        if (this.hostnameVerifier == null) {
            return "";
        }
        return this.hostnameVerifier.getClass().getCanonicalName();
    }

    /**
     * Set the {@link HostnameVerifier} to be used when opening connections
     * using StartTLS. An instance of the given class name will be constructed
     * using the default constructor.
     *
     * @param verifierClassName
     *            class name of the {@link HostnameVerifier} to be constructed
     */
    public void setHostnameVerifierClassName(String verifierClassName) {
        if (verifierClassName != null) {
            this.hostNameVerifierClassName = verifierClassName.trim();
        } else {
            this.hostNameVerifierClassName = null;
        }
    }

    /**
     * @return the {@link HostnameVerifier} to use for peer certificate
     *         verification when opening connections using StartTLS.
     */
    public HostnameVerifier getHostnameVerifier() {
        if (this.hostnameVerifier != null) {
            return this.hostnameVerifier;
        }
        if (this.hostNameVerifierClassName == null
                || hostNameVerifierClassName.equals("")) {
            return null;
        }
        try {
            Object o = constructInstance(hostNameVerifierClassName);
            if (o instanceof HostnameVerifier) {
                this.hostnameVerifier = (HostnameVerifier) o;
                return this.hostnameVerifier;
            } else {
                throw new IllegalArgumentException(sm.getString(
                        "jndiRealm.invalidHostnameVerifier",
                        hostNameVerifierClassName));
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(sm.getString(
                    "jndiRealm.invalidHostnameVerifier",
                    hostNameVerifierClassName), e);
        } catch (SecurityException e) {
            throw new IllegalArgumentException(sm.getString(
                    "jndiRealm.invalidHostnameVerifier",
                    hostNameVerifierClassName), e);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(sm.getString(
                    "jndiRealm.invalidHostnameVerifier",
                    hostNameVerifierClassName), e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(sm.getString(
                    "jndiRealm.invalidHostnameVerifier",
                    hostNameVerifierClassName), e);
        }
    }

    /**
     * Set the {@link SSLSocketFactory} to be used when opening connections
     * using StartTLS. An instance of the factory with the given name will be
     * created using the default constructor. The SSLSocketFactory can also be
     * set using {@link JNDIRealm#setSslProtocol(String) setSslProtocol(String)}.
     *
     * @param factoryClassName
     *            class name of the factory to be constructed
     */
    public void setSslSocketFactoryClassName(String factoryClassName) {
        this.sslSocketFactoryClassName = factoryClassName;
    }

    /**
     * Set the ssl protocol to be used for connections using StartTLS.
     *
     * @param protocol
     *            one of the allowed ssl protocol names
     */
    public void setSslProtocol(String protocol) {
        this.sslProtocol = protocol;
    }

    /**
     * @return the list of supported ssl protocols by the default
     *         {@link SSLContext}
     */
    private String[] getSupportedSslProtocols() {
        try {
            SSLContext sslContext = SSLContext.getDefault();
            return sslContext.getSupportedSSLParameters().getProtocols();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(sm.getString("jndiRealm.exception"), e);
        }
    }

    private Object constructInstance(String className)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        Class<?> clazz = Class.forName(className);
        return clazz.newInstance();
    }

    // ---------------------------------------------------------- Realm Methods

    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * If there are any errors with the JDBC connection, executing
     * the query or anything we return null (don't authenticate). This
     * event is also logged, and the connection will be closed so that
     * a subsequent request will automatically re-open it.
     *
     * @param username Username of the Principal to look up
     * @param credentials Password or other credentials to use in
     *  authenticating this username
     */
    @Override
    public Principal authenticate(String username, String credentials) {

        DirContext context = null;
        Principal principal = null;

        try {

            // Ensure that we have a directory context available
            context = open();

            // Occassionally the directory context will timeout.  Try one more
            // time before giving up.
            try {

                // Authenticate the specified username if possible
                principal = authenticate(context, username, credentials);

            } catch (NullPointerException e) {
                /*
                 * BZ 42449
                 * Catch NPE - Kludge Sun's LDAP provider with broken SSL.
                 */

                // log the exception so we know it's there.
                containerLog.info(sm.getString("jndiRealm.exception.retry"), e);

                // close the connection so we know it will be reopened.
                if (context != null)
                    close(context);

                // open a new directory context.
                context = open();

                // Try the authentication again.
                principal = authenticate(context, username, credentials);

            } catch (NamingException e) {
                /*
                 * BZ 61313
                 * NamingException may or may not indicate an error that is
                 * recoverable via fail over. Therefore a decision needs to be
                 * made whether to fail over or not. Generally, attempting to
                 * fail over when it is not appropriate is better than not
                 * failing over when it is appropriate so the code always
                 * attempts to fail over for NamingExceptions.
                 */

                // log the exception so we know it's there.
                containerLog.info(sm.getString("jndiRealm.exception.retry"), e);

                // close the connection so we know it will be reopened.
                if (context != null)
                    close(context);

                // open a new directory context.
                context = open();

                // Try the authentication again.
                principal = authenticate(context, username, credentials);
            }


            // Release this context
            release(context);

            // Return the authenticated Principal (if any)
            return principal;

        } catch (NamingException e) {

            // Log the problem for posterity
            containerLog.error(sm.getString("jndiRealm.exception"), e);

            // Close the connection so that it gets reopened next time
            if (context != null)
                close(context);

            // Return "not authenticated" for this request
            if (containerLog.isDebugEnabled())
                containerLog.debug("Returning null principal.");
            return null;

        }

    }


    // -------------------------------------------------------- Package Methods


    // ------------------------------------------------------ Protected Methods


    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * @param context The directory context
     * @param username Username of the Principal to look up
     * @param credentials Password or other credentials to use in
     *  authenticating this username
     *
     * @exception NamingException if a directory server error occurs
     */
    public synchronized Principal authenticate(DirContext context,
                                               String username,
                                               String credentials)
        throws NamingException {

        if (username == null || username.equals("")
            || credentials == null || credentials.equals("")) {
            if (containerLog.isDebugEnabled())
                containerLog.debug("username null or empty: returning null principal.");
            return null;
        }

        if (userPatternArray != null) {
            for (int curUserPattern = 0;
                 curUserPattern < userPatternFormatArray.length;
                 curUserPattern++) {
                // Retrieve user information
                User user = getUser(context, username, credentials, curUserPattern);
                if (user != null) {
                    try {
                        // Check the user's credentials
                        if (checkCredentials(context, user, credentials)) {
                            // Search for additional roles
                            List<String> roles = getRoles(context, user);
                            if (containerLog.isDebugEnabled()) {
                                Iterator<String> it = roles.iterator();
                                // TODO: Use a single log message
                                while (it.hasNext()) {
                                    containerLog.debug("Found role: " + it.next());
                                }
                            }
                            return (new GenericPrincipal(username,
                                                         credentials,
                                                         roles));
                        }
                    } catch (InvalidNameException ine) {
                        // Log the problem for posterity
                        containerLog.warn(sm.getString("jndiRealm.exception"), ine);
                        // ignore; this is probably due to a name not fitting
                        // the search path format exactly, as in a fully-
                        // qualified name being munged into a search path
                        // that already contains cn= or vice-versa
                    }
                }
            }
            return null;
        } else {
            // Retrieve user information
            User user = getUser(context, username, credentials);
            if (user == null)
                return null;

            // Check the user's credentials
            if (!checkCredentials(context, user, credentials))
                return null;

            // Search for additional roles
            List<String> roles = getRoles(context, user);
            if (containerLog.isDebugEnabled()) {
                Iterator<String> it = roles.iterator();
                // TODO: Use a single log message
                while (it.hasNext()) {
                    containerLog.debug("Found role: " + it.next());
                }
            }

            // Create and return a suitable Principal for this user
            return new GenericPrincipal(username, credentials, roles);
        }
    }


    /**
     * Return a User object containing information about the user
     * with the specified username, if found in the directory;
     * otherwise return <code>null</code>.
     *
     * @param context The directory context
     * @param username Username to be looked up
     *
     * @exception NamingException if a directory server error occurs
     *
     * @see #getUser(DirContext, String, String, int)
     */
    protected User getUser(DirContext context, String username)
        throws NamingException {

        return getUser(context, username, null, -1);
    }


    /**
     * Return a User object containing information about the user
     * with the specified username, if found in the directory;
     * otherwise return <code>null</code>.
     *
     * @param context The directory context
     * @param username Username to be looked up
     * @param credentials User credentials (optional)
     *
     * @exception NamingException if a directory server error occurs
     *
     * @see #getUser(DirContext, String, String, int)
     */
    protected User getUser(DirContext context, String username, String credentials)
        throws NamingException {

        return getUser(context, username, credentials, -1);
    }


    /**
     * Return a User object containing information about the user
     * with the specified username, if found in the directory;
     * otherwise return <code>null</code>.
     *
     * If the <code>userPassword</code> configuration attribute is
     * specified, the value of that attribute is retrieved from the
     * user's directory entry. If the <code>userRoleName</code>
     * configuration attribute is specified, all values of that
     * attribute are retrieved from the directory entry.
     *
     * @param context The directory context
     * @param username Username to be looked up
     * @param credentials User credentials (optional)
     * @param curUserPattern Index into userPatternFormatArray
     *
     * @exception NamingException if a directory server error occurs
     */
    protected User getUser(DirContext context, String username,
                           String credentials, int curUserPattern)
        throws NamingException {

        User user = null;

        // Get attributes to retrieve from user entry
        ArrayList<String> list = new ArrayList<String>();
        if (userPassword != null)
            list.add(userPassword);
        if (userRoleName != null)
            list.add(userRoleName);
        if (userRoleAttribute != null) {
            list.add(userRoleAttribute);
        }
        String[] attrIds = new String[list.size()];
        list.toArray(attrIds);

        // Use pattern or search for user entry
        if (userPatternFormatArray != null && curUserPattern >= 0) {
            user = getUserByPattern(context, username, credentials, attrIds, curUserPattern);
            if (containerLog.isDebugEnabled()) {
                containerLog.debug("Found user by pattern [" + user + "]");
            }
        } else {
            boolean thisUserSearchAsUser = isUserSearchAsUser();
            try {
                if (thisUserSearchAsUser) {
                    userCredentialsAdd(context, username, credentials);
                }
                user = getUserBySearch(context, username, attrIds);
            } finally {
                if (thisUserSearchAsUser) {
                    userCredentialsRemove(context);
                }
            }
            if (containerLog.isDebugEnabled()) {
                containerLog.debug("Found user by search [" + user + "]");
            }
        }

        if (userPassword == null && credentials != null && user != null) {
            // The password is available. Insert it since it may be required for
            // role searches.
            return new User(user.getUserName(), user.getDN(), credentials,
                    user.getRoles(), user.getUserRoleId());
        }

        return user;
    }


    /**
     * Use the distinguished name to locate the directory
     * entry for the user with the specified username and
     * return a User object; otherwise return <code>null</code>.
     *
     * @param context The directory context
     * @param username The username
     * @param attrIds String[]containing names of attributes to
     * @param dn Distinguished name of the user
     * retrieve.
     *
     * @exception NamingException if a directory server error occurs
     */
    protected User getUserByPattern(DirContext context,
                                    String username,
                                    String[] attrIds,
                                    String dn)
        throws NamingException {

        // If no attributes are requested, no need to look for them
        if (attrIds == null || attrIds.length == 0) {
            return new User(username, dn, null, null,null);
        }

        // Get required attributes from user entry
        Attributes attrs = null;
        try {
            attrs = context.getAttributes(dn, attrIds);
        } catch (NameNotFoundException e) {
            return null;
        }
        if (attrs == null)
            return null;

        // Retrieve value of userPassword
        String password = null;
        if (userPassword != null)
            password = getAttributeValue(userPassword, attrs);

        String userRoleAttrValue = null;
        if (userRoleAttribute != null) {
            userRoleAttrValue = getAttributeValue(userRoleAttribute, attrs);
        }

        // Retrieve values of userRoleName attribute
        ArrayList<String> roles = null;
        if (userRoleName != null)
            roles = addAttributeValues(userRoleName, attrs, roles);

        return new User(username, dn, password, roles, userRoleAttrValue);
    }


    /**
     * Use the <code>UserPattern</code> configuration attribute to
     * locate the directory entry for the user with the specified
     * username and return a User object; otherwise return
     * <code>null</code>.
     *
     * @param context The directory context
     * @param username The username
     * @param credentials User credentials (optional)
     * @param attrIds String[]containing names of attributes to
     * @param curUserPattern Index into userPatternFormatArray
     *
     * @exception NamingException if a directory server error occurs
     * @see #getUserByPattern(DirContext, String, String[], String)
     */
    protected User getUserByPattern(DirContext context,
                                    String username,
                                    String credentials,
                                    String[] attrIds,
                                    int curUserPattern)
        throws NamingException {

        User user = null;

        if (username == null || userPatternFormatArray[curUserPattern] == null)
            return null;

        // Form the dn from the user pattern
        String dn = userPatternFormatArray[curUserPattern].format(new String[] { username });

        try {
            user = getUserByPattern(context, username, attrIds, dn);
        } catch (NameNotFoundException e) {
            return null;
        } catch (NamingException e) {
            // If the getUserByPattern() call fails, try it again with the
            // credentials of the user that we're searching for
            try {
                userCredentialsAdd(context, dn, credentials);

                user = getUserByPattern(context, username, attrIds, dn);
            } finally {
                userCredentialsRemove(context);
            }
        }
        return user;
    }


    /**
     * Search the directory to return a User object containing
     * information about the user with the specified username, if
     * found in the directory; otherwise return <code>null</code>.
     *
     * @param context The directory context
     * @param username The username
     * @param attrIds String[]containing names of attributes to retrieve.
     *
     * @exception NamingException if a directory server error occurs
     */
    protected User getUserBySearch(DirContext context,
                                   String username,
                                   String[] attrIds)
        throws NamingException {

        if (username == null || userSearchFormat == null)
            return null;

        // Form the search filter
        String filter = userSearchFormat.format(new String[] { username });

        // Set up the search controls
        SearchControls constraints = new SearchControls();

        if (userSubtree) {
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        }
        else {
            constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        }

        constraints.setCountLimit(sizeLimit);
        constraints.setTimeLimit(timeLimit);

        // Specify the attributes to be retrieved
        if (attrIds == null)
            attrIds = new String[0];
        constraints.setReturningAttributes(attrIds);

        NamingEnumeration<SearchResult> results =
            context.search(userBase, filter, constraints);

        try {
            // Fail if no entries found
            try {
                if (results == null || !results.hasMore()) {
                    return null;
                }
            } catch (PartialResultException ex) {
                if (!adCompat)
                    throw ex;
                else
                    return null;
            }

            // Get result for the first entry found
            SearchResult result = results.next();

            // Check no further entries were found
            try {
                if (results.hasMore()) {
                    if(containerLog.isInfoEnabled())
                        containerLog.info("username " + username + " has multiple entries");
                    return null;
                }
            } catch (PartialResultException ex) {
                if (!adCompat)
                    throw ex;
            }

            String dn = getDistinguishedName(context, userBase, result);

            if (containerLog.isTraceEnabled())
                containerLog.trace("  entry found for " + username + " with dn " + dn);

            // Get the entry's attributes
            Attributes attrs = result.getAttributes();
            if (attrs == null)
                return null;

            // Retrieve value of userPassword
            String password = null;
            if (userPassword != null)
                password = getAttributeValue(userPassword, attrs);

            String userRoleAttrValue = null;
            if (userRoleAttribute != null) {
                userRoleAttrValue = getAttributeValue(userRoleAttribute, attrs);
            }

            // Retrieve values of userRoleName attribute
            ArrayList<String> roles = null;
            if (userRoleName != null)
                roles = addAttributeValues(userRoleName, attrs, roles);

            return new User(username, dn, password, roles, userRoleAttrValue);
        } finally {
            if (results != null) {
                results.close();
            }
        }
    }


    /**
     * Check whether the given User can be authenticated with the
     * given credentials. If the <code>userPassword</code>
     * configuration attribute is specified, the credentials
     * previously retrieved from the directory are compared explicitly
     * with those presented by the user. Otherwise the presented
     * credentials are checked by binding to the directory as the
     * user.
     *
     * @param context The directory context
     * @param user The User to be authenticated
     * @param credentials The credentials presented by the user
     *
     * @exception NamingException if a directory server error occurs
     */
    protected boolean checkCredentials(DirContext context,
                                     User user,
                                     String credentials)
         throws NamingException {

         boolean validated = false;

         if (userPassword == null) {
             validated = bindAsUser(context, user, credentials);
         } else {
             validated = compareCredentials(context, user, credentials);
         }

         if (containerLog.isTraceEnabled()) {
             if (validated) {
                 containerLog.trace(sm.getString("jndiRealm.authenticateSuccess",
                                  user.getUserName()));
             } else {
                 containerLog.trace(sm.getString("jndiRealm.authenticateFailure",
                                  user.getUserName()));
             }
         }
         return validated;
     }


    /**
     * Check whether the credentials presented by the user match those
     * retrieved from the directory.
     *
     * @param context The directory context
     * @param info The User to be authenticated
     * @param credentials Authentication credentials
     *
     * @exception NamingException if a directory server error occurs
     */
    protected boolean compareCredentials(DirContext context,
                                         User info,
                                         String credentials)
        throws NamingException {

        // Validate the credentials specified by the user
        if (containerLog.isTraceEnabled())
            containerLog.trace("  validating credentials");

        if (info == null || credentials == null)
            return (false);

        String password = info.getPassword();

        return compareCredentials(credentials, password);
    }


    /**
     * Check credentials by binding to the directory as the user
     *
     * @param context The directory context
     * @param user The User to be authenticated
     * @param credentials Authentication credentials
     *
     * @exception NamingException if a directory server error occurs
     */
     protected boolean bindAsUser(DirContext context,
                                  User user,
                                  String credentials)
         throws NamingException {

         if (credentials == null || user == null)
             return (false);

         String dn = user.getDN();
         if (dn == null)
             return (false);

         // Validate the credentials specified by the user
         if (containerLog.isTraceEnabled()) {
             containerLog.trace("  validating credentials by binding as the user");
        }

        userCredentialsAdd(context, dn, credentials);

        // Elicit an LDAP bind operation
        boolean validated = false;
        try {
            if (containerLog.isTraceEnabled()) {
                containerLog.trace("  binding as "  + dn);
            }
            context.getAttributes("", null);
            validated = true;
        }
        catch (AuthenticationException e) {
            if (containerLog.isTraceEnabled()) {
                containerLog.trace("  bind attempt failed");
            }
        }

        userCredentialsRemove(context);

        return validated;
    }

     /**
      * Configure the context to use the provided credentials for
      * authentication.
      *
      * @param context      DirContext to configure
      * @param dn           Distinguished name of user
      * @param credentials  Credentials of user
      */
    private void userCredentialsAdd(DirContext context, String dn,
            String credentials) throws NamingException {
        // Set up security environment to bind as the user
        context.addToEnvironment(Context.SECURITY_PRINCIPAL, dn);
        context.addToEnvironment(Context.SECURITY_CREDENTIALS, credentials);
    }

    /**
     * Configure the context to use {@link #connectionName} and
     * {@link #connectionPassword} if specified or an anonymous connection if
     * those attributes are not specified.
     *
      * @param context      DirContext to configure
     */
    private void userCredentialsRemove(DirContext context)
            throws NamingException {
        // Restore the original security environment
        if (connectionName != null) {
            context.addToEnvironment(Context.SECURITY_PRINCIPAL,
                                     connectionName);
        } else {
            context.removeFromEnvironment(Context.SECURITY_PRINCIPAL);
        }

        if (connectionPassword != null) {
            context.addToEnvironment(Context.SECURITY_CREDENTIALS,
                                     connectionPassword);
        }
        else {
            context.removeFromEnvironment(Context.SECURITY_CREDENTIALS);
        }
    }

    /**
     * Return a List of roles associated with the given User.  Any
     * roles present in the user's directory entry are supplemented by
     * a directory search. If no roles are associated with this user,
     * a zero-length List is returned.
     *
     * @param context The directory context we are searching
     * @param user The User to be checked
     *
     * @exception NamingException if a directory server error occurs
     */
    protected List<String> getRoles(DirContext context, User user)
        throws NamingException {

        if (user == null)
            return null;

        String dn = user.getDN();
        String username = user.getUserName();
        String userRoleId = user.getUserRoleId();

        if (dn == null || username == null)
            return null;

        if (containerLog.isTraceEnabled())
            containerLog.trace("  getRoles(" + dn + ")");

        // Start with roles retrieved from the user entry
        List<String> list = new ArrayList<String>();
        List<String> userRoles = user.getRoles();
        if (userRoles != null) {
            list.addAll(userRoles);
        }
        if (commonRole != null)
            list.add(commonRole);

        if (containerLog.isTraceEnabled()) {
            containerLog.trace("  Found " + list.size() + " user internal roles");
            for (int i=0; i<list.size(); i++)
                containerLog.trace(  "  Found user internal role " + list.get(i));
        }

        // Are we configured to do role searches?
        if ((roleFormat == null) || (roleName == null))
            return list;

        // Set up parameters for an appropriate search
        String filter = roleFormat.format(new String[] { doRFC2254Encoding(dn), username, userRoleId });
        SearchControls controls = new SearchControls();
        if (roleSubtree)
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        else
            controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        controls.setReturningAttributes(new String[] {roleName});

        String base = null;
        if (roleBaseFormat != null) {
            NameParser np = context.getNameParser("");
            Name name = np.parse(dn);
            String nameParts[] = new String[name.size()];
            for (int i = 0; i < name.size(); i++) {
                nameParts[i] = name.get(i);
            }
            base = roleBaseFormat.format(nameParts);
        } else {
            base = "";
        }

        // Perform the configured search and process the results
        NamingEnumeration<SearchResult> results = searchAsUser(context, user, base, filter, controls,
                isRoleSearchAsUser());

        if (results == null)
            return list;  // Should never happen, but just in case ...

        HashMap<String, String> groupMap = new HashMap<String, String>();
        try {
            while (results.hasMore()) {
                SearchResult result = results.next();
                Attributes attrs = result.getAttributes();
                if (attrs == null)
                    continue;
                String dname = getDistinguishedName(context, roleBase, result);
                String name = getAttributeValue(roleName, attrs);
                if (name != null && dname != null) {
                    groupMap.put(dname, name);
                }
            }
        } catch (PartialResultException ex) {
            if (!adCompat)
                throw ex;
        } finally {
            results.close();
        }

        if (containerLog.isTraceEnabled()) {
            Set<Entry<String, String>> entries = groupMap.entrySet();
            containerLog.trace("  Found " + entries.size() + " direct roles");
            for (Entry<String, String> entry : entries) {
                containerLog.trace(  "  Found direct role " + entry.getKey() + " -> " + entry.getValue());
            }
        }

        // if nested group search is enabled, perform searches for nested groups until no new group is found
        if (getRoleNested()) {

            // The following efficient algorithm is known as memberOf Algorithm, as described in "Practices in
            // Directory Groups". It avoids group slurping and handles cyclic group memberships as well.
            // See http://middleware.internet2.edu/dir/ for details

            Map<String, String> newGroups = new HashMap<String,String>(groupMap);
            while (!newGroups.isEmpty()) {
                Map<String, String> newThisRound = new HashMap<String, String>(); // Stores the groups we find in this iteration

                for (Entry<String, String> group : newGroups.entrySet()) {
                    filter = roleFormat.format(new String[] { group.getKey(), group.getValue(), group.getValue() });

                    if (containerLog.isTraceEnabled()) {
                        containerLog.trace("Perform a nested group search with base "+ roleBase + " and filter " + filter);
                    }

                    results = searchAsUser(context, user, roleBase, filter, controls,
                            isRoleSearchAsUser());

                    try {
                        while (results.hasMore()) {
                            SearchResult result = results.next();
                            Attributes attrs = result.getAttributes();
                            if (attrs == null)
                                continue;
                            String dname = getDistinguishedName(context, roleBase, result);
                            String name = getAttributeValue(roleName, attrs);
                            if (name != null && dname != null && !groupMap.keySet().contains(dname)) {
                                groupMap.put(dname, name);
                                newThisRound.put(dname, name);

                                if (containerLog.isTraceEnabled()) {
                                    containerLog.trace("  Found nested role " + dname + " -> " + name);
                                }

                            }
                         }
                    } catch (PartialResultException ex) {
                        if (!adCompat)
                            throw ex;
                    } finally {
                        results.close();
                    }
                }

                newGroups = newThisRound;
            }
        }

        list.addAll(groupMap.values());
        return list;
    }

    /**
     * Perform the search on the context as the {@code dn}, when
     * {@code searchAsUser} is {@code true}, otherwise search the context with
     * the default credentials.
     *
     * @param context
     *            context to search on
     * @param user
     *            user to bind on
     * @param base
     *            base to start the search from
     * @param filter
     *            filter to use for the search
     * @param controls
     *            controls to use for the search
     * @param searchAsUser
     *            {@code true} when the search should be done as user, or
     *            {@code false} for using the default credentials
     * @return enumeration with all found entries
     * @throws NamingException
     *             if a directory server error occurs
     */
    private NamingEnumeration<SearchResult> searchAsUser(DirContext context,
            User user, String base, String filter,
            SearchControls controls, boolean searchAsUser) throws NamingException {
        NamingEnumeration<SearchResult> results;
        try {
            if (searchAsUser) {
                userCredentialsAdd(context, user.getDN(), user.getPassword());
            }
            results = context.search(base, filter, controls);
        } finally {
            if (searchAsUser) {
                userCredentialsRemove(context);
            }
        }
        return results;
    }


    /**
     * Return a String representing the value of the specified attribute.
     *
     * @param attrId Attribute name
     * @param attrs Attributes containing the required value
     *
     * @exception NamingException if a directory server error occurs
     */
    private String getAttributeValue(String attrId, Attributes attrs)
        throws NamingException {

        if (containerLog.isTraceEnabled())
            containerLog.trace("  retrieving attribute " + attrId);

        if (attrId == null || attrs == null)
            return null;

        Attribute attr = attrs.get(attrId);
        if (attr == null)
            return null;
        Object value = attr.get();
        if (value == null)
            return null;
        String valueString = null;
        if (value instanceof byte[])
            valueString = new String((byte[]) value);
        else
            valueString = value.toString();

        return valueString;
    }


    /**
     * Add values of a specified attribute to a list
     *
     * @param attrId Attribute name
     * @param attrs Attributes containing the new values
     * @param values ArrayList containing values found so far
     *
     * @exception NamingException if a directory server error occurs
     */
    private ArrayList<String> addAttributeValues(String attrId,
                                         Attributes attrs,
                                         ArrayList<String> values)
        throws NamingException{

        if (containerLog.isTraceEnabled())
            containerLog.trace("  retrieving values for attribute " + attrId);
        if (attrId == null || attrs == null)
            return values;
        if (values == null)
            values = new ArrayList<String>();
        Attribute attr = attrs.get(attrId);
        if (attr == null)
            return values;
        NamingEnumeration<?> e = attr.getAll();
        try {
            while(e.hasMore()) {
                String value = (String)e.next();
                values.add(value);
            }
        } catch (PartialResultException ex) {
            if (!adCompat)
                throw ex;
        } finally {
            e.close();
        }
        return values;
    }


    /**
     * Close any open connection to the directory server for this Realm.
     *
     * @param context The directory context to be closed
     */
    protected void close(DirContext context) {

        // Do nothing if there is no opened connection
        if (context == null)
            return;

        // Close tls startResponse if used
        if (tls != null) {
            try {
                tls.close();
            } catch (IOException e) {
                containerLog.error(sm.getString("jndiRealm.tlsClose"), e);
            }
        }
        // Close our opened connection
        try {
            if (containerLog.isDebugEnabled())
                containerLog.debug("Closing directory context");
            context.close();
        } catch (NamingException e) {
            containerLog.error(sm.getString("jndiRealm.close"), e);
        }
        this.context = null;

    }


    /**
     * Return a short name for this Realm implementation.
     */
    @Override
    protected String getName() {

        return name;

    }


    /**
     * Return the password associated with the given principal's user name.
     */
    @Override
    protected String getPassword(String username) {
        String userPassword = getUserPassword();
        if (userPassword == null || userPassword.isEmpty()) {
            return null;
        }

        try {
            User user = getUser(open(), username, null);
             if (user == null) {
                // User should be found...
                return null;
            } else {
                // ... and have a password
                return user.getPassword();
            }
        } catch (NamingException e) {
            return null;
        }

    }

    /**
     * Return the Principal associated with the given user name.
     */
    @Override
    protected Principal getPrincipal(String username) {
        return getPrincipal(username, null);
    }

    @Override
    protected Principal getPrincipal(String username,
            GSSCredential gssCredential) {

        DirContext context = null;
        Principal principal = null;

        try {

            // Ensure that we have a directory context available
            context = open();

            // Occasionally the directory context will timeout.  Try one more
            // time before giving up.
            try {

                // Authenticate the specified username if possible
                principal = getPrincipal(context, username, gssCredential);

            } catch (CommunicationException e) {

                // log the exception so we know it's there.
                containerLog.info(sm.getString("jndiRealm.exception.retry"), e);

                // close the connection so we know it will be reopened.
                if (context != null)
                    close(context);

                // open a new directory context.
                context = open();

                // Try the authentication again.
                principal = getPrincipal(context, username, gssCredential);

            } catch (ServiceUnavailableException e) {

                // log the exception so we know it's there.
                containerLog.info(sm.getString("jndiRealm.exception.retry"), e);

                // close the connection so we know it will be reopened.
                if (context != null)
                    close(context);

                // open a new directory context.
                context = open();

                // Try the authentication again.
                principal = getPrincipal(context, username, gssCredential);

            }


            // Release this context
            release(context);

            // Return the authenticated Principal (if any)
            return principal;

        } catch (NamingException e) {

            // Log the problem for posterity
            containerLog.error(sm.getString("jndiRealm.exception"), e);

            // Close the connection so that it gets reopened next time
            if (context != null)
                close(context);

            // Return "not authenticated" for this request
            return null;

        }


    }


    /**
     * Return the Principal associated with the given user name.
     */
    protected synchronized Principal getPrincipal(DirContext context,
            String username, GSSCredential gssCredential)
        throws NamingException {

        User user = null;
        List<String> roles = null;
        Hashtable<?, ?> preservedEnvironment = null;

        try {
            if (gssCredential != null && isUseDelegatedCredential()) {
                // Preserve the current context environment parameters
                preservedEnvironment = context.getEnvironment();
                // Set up context
                context.addToEnvironment(
                        Context.SECURITY_AUTHENTICATION, "GSSAPI");
                context.addToEnvironment(
                        "javax.security.sasl.server.authentication", "true");
                context.addToEnvironment(
                        "javax.security.sasl.qop", spnegoDelegationQop);
                // Note: Subject already set in SPNEGO authenticator so no need
                //       for Subject.doAs() here
            }
            user = getUser(context, username);
            if (user != null) {
                roles = getRoles(context, user);
            }
        } finally {
            restoreEnvironmentParameter(context,
                    Context.SECURITY_AUTHENTICATION, preservedEnvironment);
            restoreEnvironmentParameter(context,
                    "javax.security.sasl.server.authentication", preservedEnvironment);
            restoreEnvironmentParameter(context, "javax.security.sasl.qop",
                    preservedEnvironment);
        }

        if (user != null) {
            return new GenericPrincipal(user.getUserName(), user.getPassword(),
                    roles, null, null, gssCredential);
        }

        return null;
    }

    private void restoreEnvironmentParameter(DirContext context,
            String parameterName, Hashtable<?, ?> preservedEnvironment) {
        try {
            context.removeFromEnvironment(parameterName);
            if (preservedEnvironment != null && preservedEnvironment.containsKey(parameterName)) {
                context.addToEnvironment(parameterName,
                        preservedEnvironment.get(parameterName));
            }
        } catch (NamingException e) {
            // Ignore
        }
    }

    /**
     * Open (if necessary) and return a connection to the configured
     * directory server for this Realm.
     *
     * @exception NamingException if a directory server error occurs
     */
    protected DirContext open() throws NamingException {

        // Do nothing if there is a directory server connection already open
        if (context != null)
            return context;

        try {

            // Ensure that we have a directory context available
            context = createDirContext(getDirectoryContextEnvironment());

        } catch (Exception e) {

            connectionAttempt = 1;

            // log the first exception.
            containerLog.info(sm.getString("jndiRealm.exception.retry"), e);

            // Try connecting to the alternate url.
            context = createDirContext(getDirectoryContextEnvironment());

        } finally {

            // reset it in case the connection times out.
            // the primary may come back.
            connectionAttempt = 0;

        }

        return context;

    }

    private DirContext createDirContext(Hashtable<String, String> env) throws NamingException {
        if (useStartTls) {
            return createTlsDirContext(env);
        } else {
            return new InitialDirContext(env);
        }
    }

    private SSLSocketFactory getSSLSocketFactory() {
        if (sslSocketFactory != null) {
            return sslSocketFactory;
        }
        final SSLSocketFactory result;
        if (this.sslSocketFactoryClassName != null
                && !sslSocketFactoryClassName.trim().equals("")) {
            result = createSSLSocketFactoryFromClassName(this.sslSocketFactoryClassName);
        } else {
            result = createSSLContextFactoryFromProtocol(sslProtocol);
        }
        this.sslSocketFactory = result;
        return result;
    }

    private SSLSocketFactory createSSLSocketFactoryFromClassName(String className) {
        try {
            Object o = constructInstance(className);
            if (o instanceof SSLSocketFactory) {
                return sslSocketFactory;
            } else {
                throw new IllegalArgumentException(sm.getString(
                        "jndiRealm.invalidSslSocketFactory",
                        className));
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(sm.getString(
                    "jndiRealm.invalidSslSocketFactory",
                    className), e);
        } catch (SecurityException e) {
            throw new IllegalArgumentException(sm.getString(
                    "jndiRealm.invalidSslSocketFactory",
                    className), e);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(sm.getString(
                    "jndiRealm.invalidSslSocketFactory",
                    className), e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(sm.getString(
                    "jndiRealm.invalidSslSocketFactory",
                    className), e);
        }
    }

    private SSLSocketFactory createSSLContextFactoryFromProtocol(String protocol) {
        try {
            SSLContext sslContext;
            if (protocol != null) {
                sslContext = SSLContext.getInstance(protocol);
                sslContext.init(null, null, null);
            } else {
                sslContext = SSLContext.getDefault();
            }
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            List<String> allowedProtocols = Arrays
                    .asList(getSupportedSslProtocols());
            throw new IllegalArgumentException(
                    sm.getString("jndiRealm.invalidSslProtocol", protocol,
                            allowedProtocols), e);
        } catch (KeyManagementException e) {
            List<String> allowedProtocols = Arrays
                    .asList(getSupportedSslProtocols());
            throw new IllegalArgumentException(
                    sm.getString("jndiRealm.invalidSslProtocol", protocol,
                            allowedProtocols), e);
        }
    }

    /**
     * Create a tls enabled LdapContext and set the StartTlsResponse tls
     * instance variable.
     *
     * @param env
     *            Environment to use for context creation
     * @return configured {@link LdapContext}
     * @throws NamingException
     *             when something goes wrong while negotiating the connection
     */
    private DirContext createTlsDirContext(
            Hashtable<String, String> env) throws NamingException {
        Map<String, Object> savedEnv = new HashMap<String, Object>();
        for (String key : Arrays.asList(Context.SECURITY_AUTHENTICATION,
                Context.SECURITY_CREDENTIALS, Context.SECURITY_PRINCIPAL,
                Context.SECURITY_PROTOCOL)) {
            Object entry = env.remove(key);
            if (entry != null) {
                savedEnv.put(key, entry);
            }
        }
        LdapContext result = null;
        try {
            result = new InitialLdapContext(env, null);
            tls = (StartTlsResponse) result
                    .extendedOperation(new StartTlsRequest());
            if (getHostnameVerifier() != null) {
                tls.setHostnameVerifier(getHostnameVerifier());
            }
            if (getCipherSuitesArray() != null) {
                tls.setEnabledCipherSuites(getCipherSuitesArray());
            }
            try {
                SSLSession negotiate = tls.negotiate(getSSLSocketFactory());
                containerLog.debug(sm.getString("jndiRealm.negotiatedTls",
                        negotiate.getProtocol()));
            } catch (IOException e) {
                throw new NamingException(e.getMessage());
            }
        } finally {
            if (result != null) {
                for (Map.Entry<String, Object> savedEntry : savedEnv.entrySet()) {
                    result.addToEnvironment(savedEntry.getKey(),
                            savedEntry.getValue());
                }
            }
        }
        return result;
    }

    /**
     * Create our directory context configuration.
     *
     * @return java.util.Hashtable the configuration for the directory context.
     */
    protected Hashtable<String,String> getDirectoryContextEnvironment() {

        Hashtable<String,String> env = new Hashtable<String,String>();

        // Configure our directory context environment.
        if (containerLog.isDebugEnabled() && connectionAttempt == 0)
            containerLog.debug("Connecting to URL " + connectionURL);
        else if (containerLog.isDebugEnabled() && connectionAttempt > 0)
            containerLog.debug("Connecting to URL " + alternateURL);
        env.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
        if (connectionName != null)
            env.put(Context.SECURITY_PRINCIPAL, connectionName);
        if (connectionPassword != null)
            env.put(Context.SECURITY_CREDENTIALS, connectionPassword);
        if (connectionURL != null && connectionAttempt == 0)
            env.put(Context.PROVIDER_URL, connectionURL);
        else if (alternateURL != null && connectionAttempt > 0)
            env.put(Context.PROVIDER_URL, alternateURL);
        if (authentication != null)
            env.put(Context.SECURITY_AUTHENTICATION, authentication);
        if (protocol != null)
            env.put(Context.SECURITY_PROTOCOL, protocol);
        if (referrals != null)
            env.put(Context.REFERRAL, referrals);
        if (derefAliases != null)
            env.put(JNDIRealm.DEREF_ALIASES, derefAliases);
        if (connectionTimeout != null)
            env.put("com.sun.jndi.ldap.connect.timeout", connectionTimeout);
        if (readTimeout != null)
            env.put("com.sun.jndi.ldap.read.timeout", readTimeout);

        return env;

    }


    /**
     * Release our use of this connection so that it can be recycled.
     *
     * @param context The directory context to release
     */
    protected void release(DirContext context) {

        // NO-OP since we are not pooling anything

    }


    // ------------------------------------------------------ Lifecycle Methods


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

        // Check to see if the connection to the directory can be opened
        try {
            open();
        } catch (NamingException e) {
            // A failure here is not fatal as the directory may be unavailable
            // now but available later. Unavailability of the directory is not
            // fatal once the Realm has started so there is no reason for it to
            // be fatal when the Realm starts.
            containerLog.error(sm.getString("jndiRealm.open"), e);
        }

        super.startInternal();
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

        super.stopInternal();

        // Close any open directory server connection
        close(this.context);

    }

    /**
     * Given a string containing LDAP patterns for user locations (separated by
     * parentheses in a pseudo-LDAP search string format -
     * "(location1)(location2)", returns an array of those paths.  Real LDAP
     * search strings are supported as well (though only the "|" "OR" type).
     *
     * @param userPatternString - a string LDAP search paths surrounded by
     * parentheses
     */
    protected String[] parseUserPatternString(String userPatternString) {

        if (userPatternString != null) {
            ArrayList<String> pathList = new ArrayList<String>();
            int startParenLoc = userPatternString.indexOf('(');
            if (startParenLoc == -1) {
                // no parens here; return whole thing
                return new String[] {userPatternString};
            }
            int startingPoint = 0;
            while (startParenLoc > -1) {
                int endParenLoc = 0;
                // weed out escaped open parens and parens enclosing the
                // whole statement (in the case of valid LDAP search
                // strings: (|(something)(somethingelse))
                while ( (userPatternString.charAt(startParenLoc + 1) == '|') ||
                        (startParenLoc != 0 && userPatternString.charAt(startParenLoc - 1) == '\\') ) {
                    startParenLoc = userPatternString.indexOf('(', startParenLoc+1);
                }
                endParenLoc = userPatternString.indexOf(')', startParenLoc+1);
                // weed out escaped end-parens
                while (userPatternString.charAt(endParenLoc - 1) == '\\') {
                    endParenLoc = userPatternString.indexOf(')', endParenLoc+1);
                }
                String nextPathPart = userPatternString.substring
                    (startParenLoc+1, endParenLoc);
                pathList.add(nextPathPart);
                startingPoint = endParenLoc+1;
                startParenLoc = userPatternString.indexOf('(', startingPoint);
            }
            return pathList.toArray(new String[] {});
        }
        return null;

    }


    /**
     * Given an LDAP search string, returns the string with certain characters
     * escaped according to RFC 2254 guidelines.
     * The character mapping is as follows:
     *     char ->  Replacement
     *    ---------------------------
     *     *  -> \2a
     *     (  -> \28
     *     )  -> \29
     *     \  -> \5c
     *     \0 -> \00
     * @param inString string to escape according to RFC 2254 guidelines
     * @return String the escaped/encoded result
     */
    protected String doRFC2254Encoding(String inString) {
        StringBuilder buf = new StringBuilder(inString.length());
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            switch (c) {
                case '\\':
                    buf.append("\\5c");
                    break;
                case '*':
                    buf.append("\\2a");
                    break;
                case '(':
                    buf.append("\\28");
                    break;
                case ')':
                    buf.append("\\29");
                    break;
                case '\0':
                    buf.append("\\00");
                    break;
                default:
                    buf.append(c);
                    break;
            }
        }
        return buf.toString();
    }


    /**
     * Returns the distinguished name of a search result.
     *
     * @param context Our DirContext
     * @param base The base DN
     * @param result The search result
     * @return String containing the distinguished name
     */
    protected String getDistinguishedName(DirContext context, String base,
            SearchResult result) throws NamingException {
        // Get the entry's distinguished name.  For relative results, this means
        // we need to composite a name with the base name, the context name, and
        // the result name.  For non-relative names, use the returned name.
        if (result.isRelative()) {
           if (containerLog.isTraceEnabled()) {
               containerLog.trace("  search returned relative name: " +
                       result.getName());
           }
           NameParser parser = context.getNameParser("");
           Name contextName = parser.parse(context.getNameInNamespace());
           Name baseName = parser.parse(base);

           // Bugzilla 32269
           Name entryName =
               parser.parse(new CompositeName(result.getName()).get(0));

           Name name = contextName.addAll(baseName);
           name = name.addAll(entryName);
           return name.toString();
        } else {
           String absoluteName = result.getName();
           if (containerLog.isTraceEnabled())
               containerLog.trace("  search returned absolute name: " +
                       result.getName());
           try {
               // Normalize the name by running it through the name parser.
               NameParser parser = context.getNameParser("");
               URI userNameUri = new URI(absoluteName);
               String pathComponent = userNameUri.getPath();
               // Should not ever have an empty path component, since that is /{DN}
               if (pathComponent.length() < 1 ) {
                   throw new InvalidNameException(
                           "Search returned unparseable absolute name: " +
                           absoluteName );
               }
               Name name = parser.parse(pathComponent.substring(1));
               return name.toString();
           } catch ( URISyntaxException e ) {
               throw new InvalidNameException(
                       "Search returned unparseable absolute name: " +
                       absoluteName );
           }
        }
    }


    // ------------------------------------------------------ Private Classes

    /**
     * A protected class representing a User
     */
    protected static class User {

        private final String username;
        private final String dn;
        private final String password;
        private final List<String> roles;
        private final String userRoleId;


        public User(String username, String dn, String password,
                List<String> roles, String userRoleId) {
            this.username = username;
            this.dn = dn;
            this.password = password;
            if (roles == null) {
                this.roles = Collections.emptyList();
            } else {
                this.roles = Collections.unmodifiableList(roles);
            }
            this.userRoleId = userRoleId;
        }

        public String getUserName() {
            return username;
        }

        public String getDN() {
            return dn;
        }

        public String getPassword() {
            return password;
        }

        public List<String> getRoles() {
            return roles;
        }

        public String getUserRoleId() {
            return userRoleId;
    }


}
}

