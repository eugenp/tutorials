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
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.session.ManagerBase;
import org.apache.catalina.startup.TesterServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Test BasicAuthenticator and NonLoginAuthenticator when a
 * SingleSignOn Valve is not active.
 *
 * <p>
 * In the absence of SSO support, these two authenticator classes
 * both have quite simple behaviour. By testing them together, we
 * can make sure they operate independently and confirm that no
 * SSO logic has been accidentally triggered.
 */
public class TestNonLoginAndBasicAuthenticator extends TomcatBaseTest {

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

    private static final int SHORT_SESSION_TIMEOUT_MINS = 1;
    private static final int LONG_SESSION_TIMEOUT_MINS = 2;
    private static final int MANAGER_SCAN_INTERVAL_SECS = 10;
    private static final int MANAGER_EXPIRE_SESSIONS_FAST = 1;
    private static final int EXTRA_DELAY_SECS = 5;
    private static final long TIMEOUT_DELAY_MSECS =
            (((SHORT_SESSION_TIMEOUT_MINS * 60)
            + (MANAGER_SCAN_INTERVAL_SECS * MANAGER_EXPIRE_SESSIONS_FAST)
            + EXTRA_DELAY_SECS) * 1000);

    private static final String CLIENT_AUTH_HEADER = "authorization";
    private static final String SERVER_AUTH_HEADER = "WWW-Authenticate";
    private static final String SERVER_COOKIE_HEADER = "Set-Cookie";
    private static final String CLIENT_COOKIE_HEADER = "Cookie";

    private static final BasicCredentials NO_CREDENTIALS = null;
    private static final BasicCredentials GOOD_CREDENTIALS =
                new BasicCredentials(NICE_METHOD, USER, PWD);
    private static final BasicCredentials STRANGE_CREDENTIALS =
                new BasicCredentials("bAsIc", USER, PWD);
    private static final BasicCredentials BAD_CREDENTIALS =
                new BasicCredentials(NICE_METHOD, USER, "wrong");
    private static final BasicCredentials BAD_METHOD =
                new BasicCredentials("BadMethod", USER, PWD);
    private static final BasicCredentials SPACED_BASE64 =
                new BasicCredentials(NICE_METHOD + " ", USER, PWD);
    private static final BasicCredentials SPACED_USERNAME =
                new BasicCredentials(NICE_METHOD, " " + USER + " ", PWD);
    private static final BasicCredentials SPACED_PASSWORD =
                new BasicCredentials(NICE_METHOD, USER, " " + PWD + " ");

    private Tomcat tomcat;
    private Context basicContext;
    private Context nonloginContext;
    private List<String> cookies;

    /*
     * Try to access an unprotected resource in a webapp that
     * does not have a login method defined.
     * This should be permitted.
     */
    @Test
    public void testAcceptPublicNonLogin() throws Exception {
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PUBLIC, NO_COOKIES,
                HttpServletResponse.SC_OK);
    }

    /*
     * Try to access a protected resource in a webapp that
     * does not have a login method defined.
     * This should be rejected with SC_FORBIDDEN 403 status.
     */
    @Test
    public void testRejectProtectedNonLogin() throws Exception {
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED, NO_COOKIES,
                HttpServletResponse.SC_FORBIDDEN);
    }

    /*
     * Try to access an unprotected resource in a webapp that
     * has a BASIC login method defined.
     * This should be permitted without a challenge.
     */
    @Test
    public void testAcceptPublicBasic() throws Exception {
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PUBLIC, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_OK);
    }

    /*
     * Try to access a protected resource in a webapp that
     * has a BASIC login method defined. The access will be
     * challenged with 401 SC_UNAUTHORIZED, and then be permitted
     * once authenticated.
     */
    @Test
    public void testAcceptProtectedBasic() throws Exception {
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, GOOD_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_OK);
    }

    /*
     * This is the same as testAcceptProtectedBasic (above), except
     * using an invalid password.
     */
    @Test
    public void testAuthMethodBadCredentials() throws Exception {
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, BAD_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
    }

    /*
     * This is the same as testAcceptProtectedBasic (above), except
     * to verify the server follows RFC2617 by treating the auth-scheme
     * token as case-insensitive.
     */
    @Test
    public void testAuthMethodCaseBasic() throws Exception {
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, STRANGE_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_OK);
    }

    /*
     * This is the same as testAcceptProtectedBasic (above), except
     * using an invalid authentication method.
     *
     * Note: the container ensures the Basic login method is called.
     *       BasicAuthenticator does not find the expected authentication
     *       header method, and so does not extract any credentials.
     *
     * The request is rejected with 401 SC_UNAUTHORIZED status. RFC2616
     * says the response body should identify the auth-schemes that are
     * acceptable for the container.
     */
    @Test
    public void testAuthMethodBadMethod() throws Exception {
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, BAD_METHOD,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
    }

    /*
     * This is the same as testAcceptProtectedBasic (above), except
     * using excess white space after the authentication method.
     *
     * The access will be challenged with 401 SC_UNAUTHORIZED, and then be
     * permitted once authenticated.
     *
     * RFC2617 does not define the separation syntax between the auth-scheme and
     * basic-credentials tokens. Tomcat tolerates any amount of white space
     * (within the limits of HTTP header sizes) and returns SC_OK.
     */
    @Test
    public void testAuthMethodExtraSpace() throws Exception {
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, SPACED_BASE64,
                NO_COOKIES, HttpServletResponse.SC_OK);

    }

    /*
     * This is the same as testAcceptProtectedBasic (above), except
     * using white space around the username credential.
     *
     * The request is rejected with 401 SC_UNAUTHORIZED status.
     *
     * TODO: RFC2617 does not define the separation syntax between the
     *       auth-scheme and basic-credentials tokens. Tomcat should tolerate
     *       any reasonable amount of white space and return SC_OK.
     */
    @Test
    public void testUserExtraSpace() throws Exception {
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, SPACED_USERNAME,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
    }

    /*
     * This is the same as testAcceptProtectedBasic (above), except
     * using white space around the password credential.
     *
     * The request is rejected with 401 SC_UNAUTHORIZED status.
     *
     * TODO: RFC2617 does not define the separation syntax between the
     *       auth-scheme and basic-credentials tokens. Tomcat should tolerate
     *       any reasonable amount of white space and return SC_OK.
     */
    @Test
    public void testPasswordExtraSpace() throws Exception {
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, SPACED_PASSWORD,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
    }

    /*
     * The default behaviour of BASIC authentication does NOT create
     * a session on the server. Verify that the client is required to
     * send a valid authenticate header with every request to access
     * protected resources.
     */
    @Test
    public void testBasicLoginWithoutSession() throws Exception {

        // this section is identical to testAuthMethodCaseBasic
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, GOOD_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_OK);

        // next, try to access the protected resource while not providing
        // credentials. This confirms the server has not retained any state
        // data which might allow it to authenticate the client. Expect
        // to be challenged with 401 SC_UNAUTHORIZED.
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);

        // finally, provide credentials to confirm the resource
        // can still be accessed with an authentication header.
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, GOOD_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_OK);
    }

    /*
     * Test the optional behaviour of BASIC authentication to create
     * a session on the server. The server will return a session cookie.
     *
     * 1. try to access a protected resource without credentials, so
     *    get Unauthorized status.
     * 2. try to access a protected resource when providing credentials,
     *    so get OK status and a server session cookie.
     * 3. access the protected resource once more using a session cookie.
     * 4. repeat using the session cookie.
     *
     * Note: The FormAuthenticator is a two-step process and is protected
     *       from session fixation attacks by the default AuthenticatorBase
     *       changeSessionIdOnAuthentication setting of true. However,
     *       BasicAuthenticator is a one-step process and so the
     *       AuthenticatorBase does not reissue the sessionId.
     */
   @Test
    public void testBasicLoginSessionPersistence() throws Exception {

        setAlwaysUseSession();

        // this section is identical to testAuthMethodCaseBasic
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, GOOD_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_OK);

        // confirm the session is not recognised by the server alone
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);

        // now provide the harvested session cookie for authentication
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                USE_COOKIES, HttpServletResponse.SC_OK);

        // finally, do it again with the cookie to be sure
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                USE_COOKIES, HttpServletResponse.SC_OK);
    }

    /*
     * Verify the timeout mechanism works for BASIC sessions. This test
     * follows the flow of testBasicLoginSessionPersistence (above).
     */
   @Test
    public void testBasicLoginSessionTimeout() throws Exception {

       setAlwaysUseSession();
       setRapidSessionTimeout();

       // this section is identical to testAuthMethodCaseBasic
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, GOOD_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_OK);

        // now provide the harvested session cookie for authentication
        List<String> originalCookies = cookies;
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                USE_COOKIES, HttpServletResponse.SC_OK);

        // allow the session to time out and lose authentication
        Thread.sleep(TIMEOUT_DELAY_MSECS);

        // provide the harvested session cookie for authentication
        // to confirm it has expired
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                USE_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);

        // finally, do BASIC reauthentication and get another session
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, GOOD_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_OK);

        // slightly paranoid verification
        boolean sameCookies = originalCookies.equals(cookies);
        assertTrue(!sameCookies);
    }

    /*
     * Logon to access a protected resource in a webapp that uses
     * BASIC authentication. Then try to access a protected resource
     * in a different webapp which does not have a login method.
     * This should be rejected with SC_FORBIDDEN 403 status, confirming
     * there has been no cross-authentication between the webapps.
     */
    @Test
    public void testBasicLoginRejectProtected() throws Exception {
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, GOOD_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_OK);

        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                NO_COOKIES, HttpServletResponse.SC_FORBIDDEN);
    }

    /*
     * Try to use the session cookie from the BASIC webapp to request
     * access to the webapp that does not have a login method. (This
     * is equivalent to Single Signon, but without the Valve.)
     *
     * Verify there is no cross-authentication when using similar logic
     * to testBasicLoginRejectProtected (above).
     *
     * This should be rejected with SC_FORBIDDEN 403 status.
     */
    @Test
    public void testBasicLoginRejectProtectedWithSession() throws Exception {

        setAlwaysUseSession();

        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, NO_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_UNAUTHORIZED);
        doTestBasic(CONTEXT_PATH_LOGIN + URI_PROTECTED, GOOD_CREDENTIALS,
                NO_COOKIES, HttpServletResponse.SC_OK);

        // use the session cookie harvested with the other webapp
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                USE_COOKIES, HttpServletResponse.SC_FORBIDDEN);
    }


    private void doTestNonLogin(String uri, boolean useCookie,
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

    private void doTestBasic(String uri, BasicCredentials credentials,
            boolean useCookie, int expectedRC) throws Exception {

        Map<String,List<String>> reqHeaders =
                new HashMap<String, List<String>>();
        Map<String,List<String>> respHeaders =
                new HashMap<String, List<String>>();

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

        if (expectedRC != HttpServletResponse.SC_OK) {
            assertEquals(expectedRC, rc);
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
            assertEquals("OK", bc.toString());
            List<String> newCookies = respHeaders.get(SERVER_COOKIE_HEADER);
            if (newCookies != null) {
                // harvest cookies whenever the server sends some new ones
                cookies = newCookies;
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

        // add the test user and role to the Realm
        tomcat.addUser(USER, PWD);
        tomcat.addRole(USER, ROLE);

        // setup both NonLogin and Login webapps
        setUpNonLogin();
        setUpLogin();

        tomcat.start();
    }


    private void setUpNonLogin() throws Exception {

        // No file system docBase required
        nonloginContext = tomcat.addContext(CONTEXT_PATH_NOLOGIN, null);
        nonloginContext.setSessionTimeout(LONG_SESSION_TIMEOUT_MINS);

        // Add protected servlet to the context
        Tomcat.addServlet(nonloginContext, "TesterServlet1", new TesterServlet());
        nonloginContext.addServletMapping(URI_PROTECTED, "TesterServlet1");

        SecurityCollection collection1 = new SecurityCollection();
        collection1.addPattern(URI_PROTECTED);
        SecurityConstraint sc1 = new SecurityConstraint();
        sc1.addAuthRole(ROLE);
        sc1.addCollection(collection1);
        nonloginContext.addConstraint(sc1);

        // Add unprotected servlet to the context
        Tomcat.addServlet(nonloginContext, "TesterServlet2", new TesterServlet());
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
        Tomcat.addServlet(basicContext, "TesterServlet3", new TesterServlet());
        basicContext.addServletMapping(URI_PROTECTED, "TesterServlet3");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern(URI_PROTECTED);
        SecurityConstraint sc = new SecurityConstraint();
        sc.addAuthRole(ROLE);
        sc.addCollection(collection);
        basicContext.addConstraint(sc);

        // Add unprotected servlet to the context
        Tomcat.addServlet(basicContext, "TesterServlet4", new TesterServlet());
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
     * Force non-default behaviour for both Authenticators
     */
    private void setAlwaysUseSession() {

        ((AuthenticatorBase)basicContext.getAuthenticator())
                .setAlwaysUseSession(true);
        ((AuthenticatorBase)nonloginContext.getAuthenticator())
                .setAlwaysUseSession(true);
    }

    /*
     * Force rapid timeout scanning for the Basic Authentication webapp
     * The StandardManager default service cycle time is 10 seconds,
     * with a session expiry scan every 6 cycles.
     */
    private void setRapidSessionTimeout() {

        ((ManagerBase) basicContext.getManager())
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
