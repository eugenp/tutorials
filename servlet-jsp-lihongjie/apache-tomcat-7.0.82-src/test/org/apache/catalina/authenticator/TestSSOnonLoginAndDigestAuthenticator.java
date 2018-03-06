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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.startup.TesterServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;

/**
 * Test DigestAuthenticator and NonLoginAuthenticator when a
 * SingleSignOn Valve is active.
 *
 * <p>
 * In the absence of SSO support, a webapp using NonLoginAuthenticator
 * simply cannot access protected resources. These tests exercise the
 * the way successfully authenticating a different webapp under the
 * DigestAuthenticator triggers the additional SSO logic for both webapps.
 *
 * <p>
 * Note: these tests are intended to exercise the SSO logic of the
 * Authenticator, but not to comprehensively test all of its logic paths.
 * That is the responsibility of the non-SSO test suite.
 */
public class TestSSOnonLoginAndDigestAuthenticator extends TomcatBaseTest {

    private static final String USER = "user";
    private static final String PWD = "pwd";
    private static final String ROLE = "role";

    private static final String HTTP_PREFIX = "http://localhost:";
    private static final String CONTEXT_PATH_NOLOGIN = "/nologin";
    private static final String CONTEXT_PATH_DIGEST = "/digest";
    private static final String URI_PROTECTED = "/protected";
    private static final String URI_PUBLIC = "/anyoneCanAccess";

    private static final int SHORT_TIMEOUT_SECS = 4;
    private static final long SHORT_TIMEOUT_DELAY_MSECS =
                                    ((SHORT_TIMEOUT_SECS + 3) * 1000);
    private static final int LONG_TIMEOUT_SECS = 10;
    private static final long LONG_TIMEOUT_DELAY_MSECS =
                                    ((LONG_TIMEOUT_SECS + 2) * 1000);

    private static final String CLIENT_AUTH_HEADER = "authorization";
    private static final String OPAQUE = "opaque";
    private static final String NONCE = "nonce";
    private static final String REALM = "realm";
    private static final String CNONCE = "cnonce";

    private static String NC1 = "00000001";
    private static String NC2 = "00000002";
    private static String QOP = "auth";

    private static String SERVER_COOKIES = "Set-Cookie";
    private static String BROWSER_COOKIES = "Cookie";

    private List<String> cookies;

    /**
     * Try to access an unprotected resource without an
     * established SSO session.
     * This should be permitted.
     */
    @Test
    public void testAcceptPublicNonLogin() throws Exception {
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PUBLIC,
                       true, false, 200);
    }

    /*
     * Try to access a protected resource without an established
     * SSO session.
     * This should be rejected with SC_FORBIDDEN 403 status.
     */
    @Test
    public void testRejectProtectedNonLogin() throws Exception {
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                       false, true, 403);
    }

    /**
     * Logon to access a protected resource using DIGEST authentication,
     * which will establish an SSO session.
     * Wait until the SSO session times-out, then try to re-access
     * the resource.
     * This should be rejected with SC_FORBIDDEN 401 status, which
     * will then be followed by successful re-authentication.
     */
    @Test
    public void testDigestLoginSessionTimeout() throws Exception {
        doTestDigest(USER, PWD, CONTEXT_PATH_DIGEST + URI_PROTECTED,
                     true, 401, true, true, NC1, CNONCE, QOP, true);
        // wait long enough for my session to expire
        Thread.sleep(LONG_TIMEOUT_DELAY_MSECS);
        // must change the client nonce to succeed
        doTestDigest(USER, PWD, CONTEXT_PATH_DIGEST + URI_PROTECTED,
                     true, 401, true, true, NC2, CNONCE, QOP, true);
   }

    /*
     * Logon to access a protected resource using DIGEST authentication,
     * which will establish an SSO session.
     * Immediately try to access a protected resource in the NonLogin
     * webapp, but without sending the SSO session cookie.
     * This should be rejected with SC_FORBIDDEN 403 status.
     */
    @Test
    public void testDigestLoginRejectProtectedWithoutCookies() throws Exception {
        doTestDigest(USER, PWD, CONTEXT_PATH_DIGEST + URI_PROTECTED,
                     true, 401, true, true, NC1, CNONCE, QOP, true);
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                       false, true, 403);
    }

    /*
     * Logon to access a protected resource using DIGEST authentication,
     * which will establish an SSO session.
     * Immediately try to access a protected resource in the NonLogin
     * webapp while sending the SSO session cookie provided by the
     * first webapp.
     * This should be successful with SC_OK 200 status.
     */
    @Test
    public void testDigestLoginAcceptProtectedWithCookies() throws Exception {
        doTestDigest(USER, PWD, CONTEXT_PATH_DIGEST + URI_PROTECTED,
                true, 401, true, true, NC1, CNONCE, QOP, true);
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                        true, false, 200);
    }

    /*
     * Logon to access a protected resource using DIGEST authentication,
     * which will establish an SSO session.
     * Immediately try to access a protected resource in the NonLogin
     * webapp while sending the SSO session cookie provided by the
     * first webapp.
     * This should be successful with SC_OK 200 status.
     *
     * Then, wait long enough for the DIGEST session to expire. (The SSO
     * session should remain active because the NonLogin session has
     * not yet expired).
     *
     * Try to access the protected resource again, before the SSO session
     * has expired.
     * This should be successful with SC_OK 200 status.
     *
     * Finally, wait for the non-login session to expire and try again..
     * This should be rejected with SC_FORBIDDEN 403 status.
     *
     * (see bugfix https://bz.apache.org/bugzilla/show_bug.cgi?id=52303)
     */
    @Test
    public void testDigestExpiredAcceptProtectedWithCookies() throws Exception {
        doTestDigest(USER, PWD, CONTEXT_PATH_DIGEST + URI_PROTECTED,
                true, 401, true, true, NC1, CNONCE, QOP, true);
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                        true, false, 200);

        // wait long enough for the BASIC session to expire,
        // but not long enough for NonLogin session expiry
        Thread.sleep(SHORT_TIMEOUT_DELAY_MSECS);
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                        true, false, 200);

        // wait long enough for my NonLogin session to expire
        // and tear down the SSO session at the same time.
        Thread.sleep(LONG_TIMEOUT_DELAY_MSECS);
        doTestNonLogin(CONTEXT_PATH_NOLOGIN + URI_PROTECTED,
                        false, true, 403);
    }


    public void doTestNonLogin(String uri, boolean addCookies,
            boolean expectedReject, int expectedRC)
            throws Exception {

        Map<String,List<String>> reqHeaders =
                new HashMap<String,List<String>>();
        Map<String,List<String>> respHeaders =
                new HashMap<String,List<String>>();

        ByteChunk bc = new ByteChunk();
        if (addCookies) {
            addCookies(reqHeaders);
        }
        int rc = getUrl(HTTP_PREFIX + getPort() + uri, bc, reqHeaders,
                respHeaders);

        if (expectedReject) {
            assertEquals(expectedRC, rc);
            assertTrue(bc.getLength() > 0);
        }
        else {
            assertEquals(200, rc);
            assertEquals("OK", bc.toString());
            saveCookies(respHeaders);
        }
}

    public void doTestDigest(String user, String pwd, String uri,
            boolean expectedReject1, int expectedRC1,
            boolean useServerNonce, boolean useServerOpaque,
            String nc1, String cnonce,
            String qop, boolean req2expect200)
            throws Exception {

        String digestUri= uri;

        List<String> auth = new ArrayList<String>();
        Map<String,List<String>> reqHeaders1 =
                new HashMap<String,List<String>>();
        Map<String,List<String>> respHeaders1 =
                new HashMap<String,List<String>>();

        // the first access attempt should be challenged
        auth.add(buildDigestResponse(user, pwd, digestUri, REALM, "null",
                "null", nc1, cnonce, qop));
        reqHeaders1.put(CLIENT_AUTH_HEADER, auth);

        ByteChunk bc = new ByteChunk();
        int rc = getUrl(HTTP_PREFIX + getPort() + uri, bc, reqHeaders1,
                respHeaders1);

        if (expectedReject1) {
            assertEquals(expectedRC1, rc);
            assertTrue(bc.getLength() > 0);
        }
        else {
            assertEquals(200, rc);
            assertEquals("OK", bc.toString());
            saveCookies(respHeaders1);
            return;
        }

        // Second request should succeed (if we use the server nonce)
        Map<String,List<String>> reqHeaders2 =
                new HashMap<String,List<String>>();
        Map<String,List<String>> respHeaders2 =
                new HashMap<String,List<String>>();

        auth.clear();
        if (useServerNonce) {
            if (useServerOpaque) {
                auth.add(buildDigestResponse(user, pwd, digestUri,
                        getAuthToken(respHeaders1, REALM),
                        getAuthToken(respHeaders1, NONCE),
                        getAuthToken(respHeaders1, OPAQUE),
                        nc1, cnonce, qop));
            } else {
                auth.add(buildDigestResponse(user, pwd, digestUri,
                        getAuthToken(respHeaders1, REALM),
                        getAuthToken(respHeaders1, NONCE),
                        "null", nc1, cnonce, qop));
            }
        } else {
            auth.add(buildDigestResponse(user, pwd, digestUri,
                    getAuthToken(respHeaders2, REALM),
                    "null", getAuthToken(respHeaders1, OPAQUE),
                    nc1, cnonce, QOP));
        }
        reqHeaders2.put(CLIENT_AUTH_HEADER, auth);

        bc.recycle();
        rc = getUrl(HTTP_PREFIX + getPort() + uri, bc, reqHeaders2,
                respHeaders2);

        if (req2expect200) {
            assertEquals(200, rc);
            assertEquals("OK", bc.toString());
            saveCookies(respHeaders2);
        } else {
            assertEquals(401, rc);
            assertTrue((bc.getLength() > 0));
        }
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();

        // create a tomcat server using the default in-memory Realm
        Tomcat tomcat = getTomcatInstance();

        // associate the SingeSignOn Valve before the Contexts
        SingleSignOn sso = new SingleSignOn();
        tomcat.getHost().getPipeline().addValve(sso);

        // add the test user and role to the Realm
        tomcat.addUser(USER, PWD);
        tomcat.addRole(USER, ROLE);

        // setup both NonLogin, Login and digest webapps
        setUpNonLogin(tomcat);
        setUpDigest(tomcat);

        tomcat.start();
    }

    private void setUpNonLogin(Tomcat tomcat) throws Exception {

        // No file system docBase required
        Context ctxt = tomcat.addContext(CONTEXT_PATH_NOLOGIN, null);
        ctxt.setSessionTimeout(LONG_TIMEOUT_SECS);

        // Add protected servlet
        Tomcat.addServlet(ctxt, "TesterServlet1", new TesterServlet());
        ctxt.addServletMapping(URI_PROTECTED, "TesterServlet1");
        SecurityCollection collection1 = new SecurityCollection();
        collection1.addPattern(URI_PROTECTED);
        SecurityConstraint sc1 = new SecurityConstraint();
        sc1.addAuthRole(ROLE);
        sc1.addCollection(collection1);
        ctxt.addConstraint(sc1);

        // Add unprotected servlet
        Tomcat.addServlet(ctxt, "TesterServlet2", new TesterServlet());
        ctxt.addServletMapping(URI_PUBLIC, "TesterServlet2");
        SecurityCollection collection2 = new SecurityCollection();
        collection2.addPattern(URI_PUBLIC);
        SecurityConstraint sc2 = new SecurityConstraint();
        // do not add a role - which signals access permitted without one
        sc2.addCollection(collection2);
        ctxt.addConstraint(sc2);

        // Configure the appropriate authenticator
        LoginConfig lc = new LoginConfig();
        lc.setAuthMethod("NONE");
        ctxt.setLoginConfig(lc);
        ctxt.getPipeline().addValve(new NonLoginAuthenticator());
    }

    private void setUpDigest(Tomcat tomcat) throws Exception {

        // No file system docBase required
        Context ctxt = tomcat.addContext(CONTEXT_PATH_DIGEST, null);
        ctxt.setSessionTimeout(SHORT_TIMEOUT_SECS);

        // Add protected servlet
        Tomcat.addServlet(ctxt, "TesterServlet3", new TesterServlet());
        ctxt.addServletMapping(URI_PROTECTED, "TesterServlet3");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern(URI_PROTECTED);
        SecurityConstraint sc = new SecurityConstraint();
        sc.addAuthRole(ROLE);
        sc.addCollection(collection);
        ctxt.addConstraint(sc);

        // Configure the appropriate authenticator
        LoginConfig lc = new LoginConfig();
        lc.setAuthMethod("DIGEST");
        ctxt.setLoginConfig(lc);
        ctxt.getPipeline().addValve(new DigestAuthenticator());
    }

    protected static String getAuthToken(
            Map<String,List<String>> respHeaders, String token) {

        final String AUTH_PREFIX = "=\"";
        final String AUTH_SUFFIX = "\"";
        List<String> authHeaders =
            respHeaders.get(AuthenticatorBase.AUTH_HEADER_NAME);

        // Assume there is only one
        String authHeader = authHeaders.iterator().next();
        String searchFor = token + AUTH_PREFIX;
        int start = authHeader.indexOf(searchFor) + searchFor.length();
        int end = authHeader.indexOf(AUTH_SUFFIX, start);
        return authHeader.substring(start, end);
    }

    /*
     * Notes from RFC2617
     * H(data) = MD5(data)
     * KD(secret, data) = H(concat(secret, ":", data))
     * A1 = unq(username-value) ":" unq(realm-value) ":" passwd
     * A2 = Method ":" digest-uri-value
     * request-digest  = <"> < KD ( H(A1),     unq(nonce-value)
                                    ":" nc-value
                                    ":" unq(cnonce-value)
                                    ":" unq(qop-value)
                                    ":" H(A2)
                                   ) <">
     */
    private static String buildDigestResponse(String user, String pwd,
            String uri, String realm, String nonce, String opaque, String nc,
            String cnonce, String qop) {

        String a1 = user + ":" + realm + ":" + pwd;
        String a2 = "GET:" + uri;

        String md5a1 = digest(a1);
        String md5a2 = digest(a2);

        String response;
        if (qop == null) {
            response = md5a1 + ":" + nonce + ":" + md5a2;
        } else {
            response = md5a1 + ":" + nonce + ":" + nc + ":" + cnonce + ":" +
                    qop + ":" + md5a2;
        }

        String md5response = digest(response);

        StringBuilder auth = new StringBuilder();
        auth.append("Digest username=\"");
        auth.append(user);
        auth.append("\", realm=\"");
        auth.append(realm);
        auth.append("\", nonce=\"");
        auth.append(nonce);
        auth.append("\", uri=\"");
        auth.append(uri);
        auth.append("\", opaque=\"");
        auth.append(opaque);
        auth.append("\", response=\"");
        auth.append(md5response);
        auth.append("\"");
        if (qop != null) {
            auth.append(", qop=");
            auth.append(qop);
        }
        if (nc != null) {
            auth.append(", nc=");
            auth.append(nc);
        }
        if (cnonce != null) {
            auth.append(", cnonce=\"");
            auth.append(cnonce);
            auth.append("\"");
        }

        return auth.toString();
    }

    private static String digest(String input) {
        return MD5Encoder.encode(
                ConcurrentMessageDigest.digestMD5(input.getBytes()));
    }

    /*
     * extract and save the server cookies from the incoming response
     */
    protected void saveCookies(Map<String,List<String>> respHeaders) {

        // we only save the Cookie values, not header prefix
        cookies = respHeaders.get(SERVER_COOKIES);
    }

    /*
     * add all saved cookies to the outgoing request
     */
    protected void addCookies(Map<String,List<String>> reqHeaders) {

        if ((cookies != null) && (cookies.size() > 0)) {
            reqHeaders.put(BROWSER_COOKIES, cookies);
        }
    }
}
