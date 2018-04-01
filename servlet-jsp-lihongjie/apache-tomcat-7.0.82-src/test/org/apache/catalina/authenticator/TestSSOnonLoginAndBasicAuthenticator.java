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
package org.apache.catalina.authenticator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.Session;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.session.ManagerBase;
import org.apache.catalina.startup.TesterServletEncodeUrl;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Test BasicAuthenticator and NonLoginAuthenticator when a
 * SingleSignOn Valve is active.
 *
 * <p>
 * In the absence of SSO support, a webapp using NonLoginAuthenticator
 * simply cannot access protected resources. These tests exercise the
 * the way successfully authenticating a different webapp under the
 * BasicAuthenticator triggers the additional SSO logic for both webapps.
 *
 * <p>
 * The two Authenticators are thoroughly exercised by two other unit test
 * classes: TestBasicAuthParser and TestNonLoginAndBasicAuthenticator.
 * This class mainly examines the way the Single SignOn Valve interacts with
 * two webapps when the second cannot be authenticated directly, but needs
 * to inherit its authentication via the other.
 *
 * <p>
 * When the server and client can both use cookies, the authentication
 * is preserved through the exchange of a JSSOSESSIONID cookie, which
 * is different to the individual and unique JSESSIONID cookies assigned
 * separately to the two webapp sessions.
 *
 * <p>
 * The other situation examined is where the server returns authentication
 * cookies, but the client is configured to ignore them. The Tomcat
 * documentation clearly states that SSO <i>requires</i> the client to
 * support cookies, so access to resources in other webapp containers
 * receives no SSO assistance.
 */
public class TestSSOnonLoginAndBasicAuthenticator extends TomcatBaseTest {

    protected static final boolean USE_COOKIES = true;
    protected static final boolean NO_COOKIES = !USE_COOKIES;

    private static final String USER = "user";
    private static final String PWD = "pwd";
    private static final String ROLE = "role";
    private static final String NICE_METHOD = "Basic";

    private static final String HTTP_PREFIX = "http://localhost:";
    private static final String CONTEXT_PATH_NOLOGIN = "/nologin";
    private static final String CONTEXT_PATH_LOGIN = "/login";
    private static final String URI_PROTECTED = "/protected";
    private static final String URI_PUBLIC = "/anyoneCanAccess";

    // session expiry in web.xml is defined in minutes
    private static final int SHORT_SESSION_TIMEOUT_MINS = 1;
    private static final int LONG_SESSION_TIMEOUT_MINS = 2;

    // we don't change the expiry scan interval - just the iteration count
    private static final int MANAGER_SCAN_INTERVAL_SECS = 10;
    private static final int MANAGER_EXPIRE_SESSIONS_FAST = 1;

    // now compute some delays - beware of the units!
    private static final int EXTRA_DELAY_SECS = 5;
    private static final long REASONABLE_MSECS_TO_EXPIRY =
            (((MANAGER_SCAN_INTERVAL_SECS * MANAGER_EXPIRE_SESSIONS_FAST)
                    + EXTRA_DELAY_SECS) * 1000);

    private static final String CLIENT_AUTH_HEADER = "authorization";
    private static final String SERVER_AUTH_HEADER = "WWW-Authenticate";
    private static final String SERVER_COOKIE_HEADER = "Set-Cookie";
    private static final String CLIENT_COOKIE_HEADER = "Cookie";
    private static final String ENCODE_SESSION_PARAM = "jsessionid";
    private static final String ENCODE_SSOSESSION_PARAM = "jssosessionid";

    private static final
            TestSSOnonLoginAndBasicAuthenticator.BasicCredentials
                    NO_CREDENTIALS = null;
    private static final
            TestSSOnonLoginAndBasicAuthenticator.BasicCredentials
                    GOOD_CREDENTIALS =
                new TestSSOnonLoginAndBasicAuthenticator.BasicCredentials(
                            NICE_METHOD, USER, PWD);

    private Tomcat tomcat;
    private Context basicContext;
    private Context nonloginContext;
    private List<String> cookies;
    private String encodedURL;

    /*
     * Run some sanity checks without an established SSO session
     * to make sure the test environment is correct.
     */
    @Test
    public void testEssentialEnvironment() throws Exception {

        // should be permitted to access an unprotected resource.
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PUBLIC,
                       USE_COOKIES, HttpServletResponse.SC_OK);

        // should not be permitted to access a protected resource
        // with the two Authenticators used in the remaining tests.
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                USE_COOKIES, HttpServletResponse.SC_FORBIDDEN);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                NO_CREDENTIALS, USE_COOKIES,
                HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void testEssentialEnvironmentWithoutCookies() throws Exception {

        // should be permitted to access an unprotected resource.
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PUBLIC,
                       NO_COOKIES, HttpServletResponse.SC_OK);

        // should not be permitted to access a protected resource
        // with the two Authenticators used in the remaining tests.
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                NO_COOKIES, HttpServletResponse.SC_FORBIDDEN);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                NO_CREDENTIALS, NO_COOKIES,
                HttpServletResponse.SC_UNAUTHORIZED);
    }

    /*
     * Logon to access a protected resource using BASIC authentication,
     * which will establish an SSO session.
     * Wait until the SSO session times-out, then try to re-access
     * the resource. This should be rejected with SC_FORBIDDEN 401 status.
     *
     * Note: this test will run for slightly more than 1 minute.
     */
    @Test
    public void testBasicAccessAndSessionTimeout() throws Exception {

        setRapidSessionTimeoutDetection();

        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                NO_CREDENTIALS, USE_COOKIES,
                HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                GOOD_CREDENTIALS, USE_COOKIES,
                HttpServletResponse.SC_OK);

        // verify the SSOID exists as a cookie
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                GOOD_CREDENTIALS, USE_COOKIES,
                HttpServletResponse.SC_OK);

        // make the session time out and lose authentication
        doImminentSessionTimeout(basicContext);

        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                NO_CREDENTIALS, USE_COOKIES,
                HttpServletResponse.SC_UNAUTHORIZED);
    }


    /*
     * Logon to access a protected resource using BASIC authentication,
     * which will establish an SSO session.
     * Immediately try to access a protected resource in the NonLogin
     * webapp while providing the SSO session cookie received from the
     * first webapp. This should be successful with SC_OK 200 status.
     */
    @Test
    public void testBasicLoginThenAcceptWithCookies() throws Exception {

        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                NO_CREDENTIALS, NO_COOKIES,
                HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                GOOD_CREDENTIALS, USE_COOKIES, HttpServletResponse.SC_OK);

        // send the cookie which proves we have an authenticated SSO session
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                       USE_COOKIES, HttpServletResponse.SC_OK);
    }

    /*
     * Logon to access a protected resource using BASIC authentication,
     * which will establish an SSO session.
     * Immediately try to access a protected resource in the NonLogin
     * webapp, but without sending the SSO session cookie.
     * This should be rejected with SC_FORBIDDEN 403 status.
     */
    @Test
    public void testBasicLoginThenRejectWithoutCookie() throws Exception {

        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                NO_CREDENTIALS, USE_COOKIES,
                HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                GOOD_CREDENTIALS, USE_COOKIES,
                HttpServletResponse.SC_OK);

        // fail to send the authentication cookie to the other webapp.
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                NO_COOKIES, HttpServletResponse.SC_FORBIDDEN);
    }

    /*
     * Logon to access a protected resource using BASIC authentication,
     * which will establish an SSO session.
     * Then try to access a protected resource in the NonLogin
     * webapp by sending the JSESSIONID from the redirect header.
     * The access request should be rejected because the Basic webapp's
     * sessionID is not valid for any other container.
     */
    @Test
    public void testBasicAccessThenAcceptAuthWithUri() throws Exception {

        setAlwaysUseSession();

        // first, fail to access the protected resource without credentials
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                NO_CREDENTIALS, NO_COOKIES,
                HttpServletResponse.SC_UNAUTHORIZED);

        // now, access the protected resource with good credentials
        // to establish the session
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                GOOD_CREDENTIALS, NO_COOKIES,
                HttpServletResponse.SC_OK);

        // next, access it again to harvest the session id url parameter
        String forwardParam = "?nextUrl=" + CONTEXT_PATH_LOGIN + URI_PROTECTED;
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED + forwardParam,
                GOOD_CREDENTIALS, NO_COOKIES,
                HttpServletResponse.SC_OK);

        // verify the sessionID was encoded in the absolute URL
        String firstEncodedURL = encodedURL;
        assertTrue(firstEncodedURL.contains(ENCODE_SESSION_PARAM));

        // access the protected resource with the encoded url (with session id)
        doTestBasic(firstEncodedURL + forwardParam,
                NO_CREDENTIALS, NO_COOKIES,
                HttpServletResponse.SC_OK);

        // verify the sessionID has not changed
        // verify the SSO sessionID was not encoded
        String secondEncodedURL = encodedURL;
        assertEquals(firstEncodedURL, secondEncodedURL);
        assertFalse(firstEncodedURL.contains(ENCODE_SSOSESSION_PARAM));

        // extract the first container's session ID
        int ix = secondEncodedURL.indexOf(ENCODE_SESSION_PARAM);
        String sessionId = secondEncodedURL.substring(ix);

        // expect to fail using that sessionID in a different container
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED + ";" + sessionId,
                NO_COOKIES, HttpServletResponse.SC_FORBIDDEN);
    }

    /*
     * Logon to access a protected resource using BASIC authentication,
     * which will establish an SSO session.
     * Immediately try to access a protected resource in the NonLogin
     * webapp while providing the SSO session cookie received from the
     * first webapp. This should be successful with SC_OK 200 status.
     *
     * Then, wait long enough for the BASIC session to expire. (The SSO
     * session should remain active because the NonLogin session has
     * not yet expired).
     * Try to access the protected resource again, before the SSO session
     * has expired. This should be successful with SC_OK 200 status.
     *
     * Finally, wait for the non-login session to expire and try again..
     * This should be rejected with SC_FORBIDDEN 403 status.
     *
     * (see bugfix https://bz.apache.org/bugzilla/show_bug.cgi?id=52303)
     *
     * Note: this test will run for slightly more than 3 minutes.
     */
    @Test
    public void testBasicExpiredAcceptProtectedWithCookies() throws Exception {

        setRapidSessionTimeoutDetection();

        // begin with a repeat of testBasicLoginAcceptProtectedWithCookies
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                NO_CREDENTIALS, USE_COOKIES,
                HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                GOOD_CREDENTIALS, USE_COOKIES,
                HttpServletResponse.SC_OK);
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                       USE_COOKIES, HttpServletResponse.SC_OK);

        // wait long enough for the BASIC session to expire,
        // but not long enough for the NonLogin session expiry.
        doImminentSessionTimeout(basicContext);

        // this successful NonLogin access should replenish the
        // the individual session expiry time and keep the SSO session alive
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                       USE_COOKIES, HttpServletResponse.SC_OK);

        // wait long enough for the NonLogin session to expire,
        // which will also tear down the SSO session at the same time.
        doImminentSessionTimeout(nonloginContext);

        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED, USE_COOKIES,
                HttpServletResponse.SC_FORBIDDEN);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED,
                NO_CREDENTIALS, USE_COOKIES,
                HttpServletResponse.SC_UNAUTHORIZED);

    }


    public void doTestNonLogin(String uri, boolean useCookie,
            int expectedRC) throws Exception {

        Map<String,List<String>> reqHeaders =
                new HashMap<String,List<String>>();
        Map<String,List<String>> respHeaders =
                new HashMap<String,List<String>>();

        if (useCookie && (cookies != null)) {
            reqHeaders.put(CLIENT_COOKIE_HEADER, cookies);
        }

        ByteChunk bc = new ByteChunk();
        int rc = getUrl(HTTP_PREFIX + getPort() + uri, bc, reqHeaders,
                respHeaders);

        if (expectedRC != HttpServletResponse.SC_OK) {
            assertEquals(expectedRC, rc);
            assertTrue(bc.getLength() > 0);
        }
        else {
            assertEquals("OK", bc.toString());
        }
}

    private void doTestBasic(String uri,
            TestSSOnonLoginAndBasicAuthenticator.BasicCredentials credentials,
            boolean useCookie, int expectedRC) throws Exception {

        Map<String,List<String>> reqHeaders = new HashMap<String,List<String>>();
        Map<String,List<String>> respHeaders = new HashMap<String,List<String>>();

        if (useCookie && (cookies != null)) {
            reqHeaders.put(CLIENT_COOKIE_HEADER, cookies);
        }
        else {
            if (credentials != null) {
                List<String> auth = new ArrayList<String>();
                auth.add(credentials.getCredentials());
                reqHeaders.put(CLIENT_AUTH_HEADER, auth);
            }
        }

        ByteChunk bc = new ByteChunk();
        int rc = getUrl(HTTP_PREFIX + getPort() + uri, bc, reqHeaders,
                respHeaders);

        assertEquals("Unexpected Return Code", expectedRC, rc);
        if (expectedRC != HttpServletResponse.SC_OK) {
            assertTrue(bc.getLength() > 0);
            if (expectedRC == HttpServletResponse.SC_UNAUTHORIZED) {
                // The server should identify the acceptable method(s)
                boolean methodFound = false;
                List<String> authHeaders = respHeaders.get(SERVER_AUTH_HEADER);
                for (String authHeader : authHeaders) {
                    if (authHeader.indexOf(NICE_METHOD) > -1) {
                        methodFound = true;
                        break;
                    }
                }
                assertTrue(methodFound);
            }
        }
        else {
            String thePage = bc.toString();
            assertNotNull(thePage);
            assertTrue(thePage.startsWith("OK"));
            if (useCookie) {
                List<String> newCookies = respHeaders.get(SERVER_COOKIE_HEADER);
                if (newCookies != null) {
                    // harvest cookies whenever the server sends some new ones
                    cookies = newCookies;
                }
            }
            else {
                encodedURL = "";
                final String start = "<a href=\"";
                final String end = "\">";
                int iStart = thePage.indexOf(start);
                int iEnd = 0;
                if (iStart > -1) {
                    iStart += start.length();
                    iEnd = thePage.indexOf(end, iStart);
                    if (iEnd > -1) {
                        encodedURL = thePage.substring(iStart, iEnd);
                    }
                }
            }
        }
    }




    /*
     * setup two webapps for every test
     *
     * note: the super class tearDown method will stop tomcat
     */
    @Override
    public void setUp() throws Exception {

        super.setUp();

        // create a tomcat server using the default in-memory Realm
        tomcat = getTomcatInstance();

        // associate the SingeSignOn Valve before the Contexts
        SingleSignOn sso = new SingleSignOn();
        tomcat.getHost().getPipeline().addValve(sso);

        // add the test user and role to the Realm
        tomcat.addUser(USER, PWD);
        tomcat.addRole(USER, ROLE);

        // setup both NonLogin and Login webapps
        setUpNonLogin();
        setUpLogin();

        tomcat.start();
    }

    @Override
    public void tearDown() throws Exception {

        tomcat.stop();
    }

    private void setUpNonLogin() throws Exception {

        // No file system docBase required
        nonloginContext = tomcat.addContext(CONTEXT_PATH_NOLOGIN, null);
        nonloginContext.setSessionTimeout(LONG_SESSION_TIMEOUT_MINS);

        // Add protected servlet to the context
        Tomcat.addServlet(nonloginContext, "TesterServlet1",
                new TesterServletEncodeUrl());
        nonloginContext.addServletMapping(URI_PROTECTED, "TesterServlet1");

        SecurityCollection collection1 = new SecurityCollection();
        collection1.addPattern(URI_PROTECTED);
        SecurityConstraint sc1 = new SecurityConstraint();
        sc1.addAuthRole(ROLE);
        sc1.addCollection(collection1);
        nonloginContext.addConstraint(sc1);

        // Add unprotected servlet to the context
        Tomcat.addServlet(nonloginContext, "TesterServlet2",
                new TesterServletEncodeUrl());
        nonloginContext.addServletMapping(URI_PUBLIC, "TesterServlet2");

        SecurityCollection collection2 = new SecurityCollection();
        collection2.addPattern(URI_PUBLIC);
        SecurityConstraint sc2 = new SecurityConstraint();
        // do not add a role - which signals access permitted without one
        sc2.addCollection(collection2);
        nonloginContext.addConstraint(sc2);

        // Configure the authenticator and inherit the Realm from Engine
        LoginConfig lc = new LoginConfig();
        lc.setAuthMethod("NONE");
        nonloginContext.setLoginConfig(lc);
        AuthenticatorBase nonloginAuthenticator = new NonLoginAuthenticator();
        nonloginContext.getPipeline().addValve(nonloginAuthenticator);
    }

    private void setUpLogin() throws Exception {

        // No file system docBase required
        basicContext = tomcat.addContext(CONTEXT_PATH_LOGIN, null);
        basicContext.setSessionTimeout(SHORT_SESSION_TIMEOUT_MINS);

        // Add protected servlet to the context
        Tomcat.addServlet(basicContext, "TesterServlet3",
                new TesterServletEncodeUrl());
        basicContext.addServletMapping(URI_PROTECTED, "TesterServlet3");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern(URI_PROTECTED);
        SecurityConstraint sc = new SecurityConstraint();
        sc.addAuthRole(ROLE);
        sc.addCollection(collection);
        basicContext.addConstraint(sc);

        // Add unprotected servlet to the context
        Tomcat.addServlet(basicContext, "TesterServlet4",
                new TesterServletEncodeUrl());
        basicContext.addServletMapping(URI_PUBLIC, "TesterServlet4");
        SecurityCollection collection2 = new SecurityCollection();
        collection2.addPattern(URI_PUBLIC);
        SecurityConstraint sc2 = new SecurityConstraint();
        // do not add a role - which signals access permitted without one
        sc2.addCollection(collection2);
        basicContext.addConstraint(sc2);

        // Configure the authenticator and inherit the Realm from Engine
        LoginConfig lc = new LoginConfig();
        lc.setAuthMethod("BASIC");
        basicContext.setLoginConfig(lc);
        AuthenticatorBase basicAuthenticator = new BasicAuthenticator();
        basicContext.getPipeline().addValve(basicAuthenticator);
    }

    /*
     * extract and save the server cookies from the incoming response
     */
    protected void saveCookies(Map<String,List<String>> respHeaders) {

        // we only save the Cookie values, not header prefix
        cookies = respHeaders.get(SERVER_COOKIE_HEADER);
    }

    /*
     * add all saved cookies to the outgoing request
     */
    protected void addCookies(Map<String,List<String>> reqHeaders) {

        if ((cookies != null) && (cookies.size() > 0)) {
            reqHeaders.put(CLIENT_COOKIE_HEADER, cookies);
        }
    }

    /*
     * Force non-default behaviour for both Authenticators.
     * The session id will not be regenerated after authentication,
     * which is less secure but needed for browsers that will not
     * handle cookies.
     */
    private void setAlwaysUseSession() {

        ((AuthenticatorBase) basicContext.getAuthenticator())
                .setAlwaysUseSession(true);
        ((AuthenticatorBase) nonloginContext.getAuthenticator())
                .setAlwaysUseSession(true);
    }

    /*
     * Force faster timeout for an active Container than can
     * be defined in web.xml. By getting to the active Session we
     * can choose seconds instead of minutes.
     * Note: shamelessly cloned from ManagerBase - beware of synch issues
     *       on the underlying sessions.
     */
    private void doImminentSessionTimeout(Context activeContext) {

        ManagerBase manager = (ManagerBase) activeContext.getManager();
        Session[] sessions = manager.findSessions();
        for (int i = 0; i < sessions.length; i++) {
            if (sessions[i]!=null && sessions[i].isValid()) {
                sessions[i].setMaxInactiveInterval(EXTRA_DELAY_SECS);
                // leave it to be expired by the manager
            }
        }
        try {
            Thread.sleep(REASONABLE_MSECS_TO_EXPIRY);
        } catch (InterruptedException ie) {
            // ignored
        }

        // paranoid verification that active sessions have now gone
        sessions = manager.findSessions();
        assertTrue(sessions.length == 0);
    }

    /*
     * Force rapid timeout scanning for both webapps
     * The StandardManager default service cycle time is 10 seconds,
     * with a session expiry scan every 6 cycles.
     */
    private void setRapidSessionTimeoutDetection() {

        ((ManagerBase) basicContext.getManager())
                .setProcessExpiresFrequency(MANAGER_EXPIRE_SESSIONS_FAST);
        ((ManagerBase) nonloginContext.getManager())
                .setProcessExpiresFrequency(MANAGER_EXPIRE_SESSIONS_FAST);
    }

    /*
     * Encapsulate the logic to generate an HTTP header
     * for BASIC Authentication.
     * Note: only used internally, so no need to validate arguments.
     */
    private static final class BasicCredentials {

        private final String method;
        private final String username;
        private final String password;
        private final String credentials;

        private BasicCredentials(String aMethod,
                String aUsername, String aPassword) {
            method = aMethod;
            username = aUsername;
            password = aPassword;
            String userCredentials = username + ":" + password;
            byte[] credentialsBytes =
                    userCredentials.getBytes(B2CConverter.ISO_8859_1);
            String base64auth = Base64.encodeBase64String(credentialsBytes);
            credentials= method + " " + base64auth;
        }

        private String getCredentials() {
            return credentials;
        }
    }
}
