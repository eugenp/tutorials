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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Request;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.startup.TestTomcat.MapRealm;
import org.apache.catalina.startup.TesterServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.unittest.TesterContext;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;

public class TestDigestAuthenticator extends TomcatBaseTest {

    private static String USER = "user";
    private static String PWD = "pwd";
    private static String ROLE = "role";
    private static String URI = "/protected";
    private static String QUERY = "?foo=bar";
    private static String CONTEXT_PATH = "/foo";
    private static String CLIENT_AUTH_HEADER = "authorization";
    private static String REALM = "TestRealm";
    private static String CNONCE = "cnonce";
    private static String NC1 = "00000001";
    private static String NC2 = "00000002";
    private static String QOP = "auth";


    @Test
    public void bug54521() throws LifecycleException {
        DigestAuthenticator digestAuthenticator = new DigestAuthenticator();
        digestAuthenticator.setContainer(new TesterContext());
        digestAuthenticator.start();
        Request request = new TesterRequest();
        final int count = 1000;

        Set<String> nonces = new HashSet<String>();

        for (int i = 0; i < count; i++) {
            nonces.add(digestAuthenticator.generateNonce(request));
        }

        Assert.assertEquals(count,  nonces.size());
    }


    @Test
    public void testAllValid() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, true,
                NC1, NC2, CNONCE, QOP, true, true);
    }

    @Test
    public void testValidNoQop() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, true,
                null, null, null, null, true, true);
    }

    @Test
    public void testValidQuery() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI + QUERY, false, true, REALM, true,
                true, NC1, NC2, CNONCE, QOP, true, true);
    }

    @Test
    public void testInvalidUriFail() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, true, true, REALM, true, true,
                NC1, NC2, CNONCE, QOP, false, false);
    }

    @Test
    public void testInvalidUriPass() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, true, false, REALM, true, true,
                NC1, NC2, CNONCE, QOP, true, true);
    }

    @Test
    public void testInvalidRealm() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, "null", true, true,
                NC1, NC2, CNONCE, QOP, false, false);
    }

    @Test
    public void testInvalidNonce() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, false, true,
                NC1, NC2, CNONCE, QOP, false, true);
    }

    @Test
    public void testInvalidOpaque() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, false,
                NC1, NC2, CNONCE, QOP, false, true);
    }

    @Test
    public void testInvalidNc1() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, true,
                "null", null, CNONCE, QOP, false, false);
    }

    @Test
    public void testInvalidQop() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, true,
                NC1, NC2, CNONCE, "null", false, false);
    }

    @Test
    public void testInvalidQopCombo1() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, true,
                NC1, NC2, CNONCE, null, false, false);
    }

    @Test
    public void testInvalidQopCombo2() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, true,
                NC1, NC2, null, QOP, false, false);
    }

    @Test
    public void testInvalidQopCombo3() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, true,
                NC1, NC2, null, null, false, false);
    }

    @Test
    public void testInvalidQopCombo4() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, true,
                null, null, CNONCE, QOP, false, false);
    }

    @Test
    public void testInvalidQopCombo5() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, true,
                null, null, CNONCE, null, false, false);
    }

    @Test
    public void testInvalidQopCombo6() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, true,
                null, null, null, QOP, false, false);
    }

    @Test
    public void testReplay() throws Exception {
        doTest(USER, PWD, CONTEXT_PATH + URI, false, true, REALM, true, true,
                NC1, NC1, CNONCE, QOP, true, false);
    }

    public void doTest(String user, String pwd, String uri, boolean breakUri,
            boolean validateUri, String realm, boolean useServerNonce,
            boolean useServerOpaque, String nc1, String nc2, String cnonce,
            String qop, boolean req2expect200, boolean req3expect200)
            throws Exception {

        if (!validateUri) {
            DigestAuthenticator auth =
                (DigestAuthenticator) getTomcatInstance().getHost().findChild(
                        CONTEXT_PATH).getPipeline().getFirst();
            auth.setValidateUri(false);
        }
        getTomcatInstance().start();

        String digestUri;
        if (breakUri) {
            digestUri = "/broken" + uri;
        } else {
            digestUri = uri;
        }
        List<String> auth = new ArrayList<String>();
        auth.add(buildDigestResponse(user, pwd, digestUri, realm, "null",
                "null", nc1, cnonce, qop));
        Map<String,List<String>> reqHeaders = new HashMap<String,List<String>>();
        reqHeaders.put(CLIENT_AUTH_HEADER, auth);

        Map<String,List<String>> respHeaders =
            new HashMap<String,List<String>>();

        // The first request will fail - but we need to extract the nonce
        ByteChunk bc = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort() + uri, bc, reqHeaders,
                respHeaders);
        assertEquals(401, rc);
        assertTrue(bc.getLength() > 0);
        bc.recycle();

        // Second request should succeed (if we use the server nonce)
        auth.clear();
        if (useServerNonce) {
            if (useServerOpaque) {
                auth.add(buildDigestResponse(user, pwd, digestUri, realm,
                        getNonce(respHeaders), getOpaque(respHeaders), nc1,
                        cnonce, qop));
            } else {
                auth.add(buildDigestResponse(user, pwd, digestUri, realm,
                        getNonce(respHeaders), "null", nc1, cnonce, qop));
            }
        } else {
            auth.add(buildDigestResponse(user, pwd, digestUri, realm,
                    "null", getOpaque(respHeaders), nc1, cnonce, QOP));
        }
        rc = getUrl("http://localhost:" + getPort() + uri, bc, reqHeaders,
                null);

        if (req2expect200) {
            assertEquals(200, rc);
            assertEquals("OK", bc.toString());
        } else {
            assertEquals(401, rc);
            assertTrue(bc.getLength() > 0);
        }

        // Third request should succeed if we increment nc
        auth.clear();
        bc.recycle();
        bc.reset();
        auth.add(buildDigestResponse(user, pwd, digestUri, realm,
                getNonce(respHeaders), getOpaque(respHeaders), nc2, cnonce,
                qop));
        rc = getUrl("http://localhost:" + getPort() + uri, bc, reqHeaders,
                null);

        if (req3expect200) {
            assertEquals(200, rc);
            assertEquals("OK", bc.toString());
        } else {
            assertEquals(401, rc);
            assertTrue(bc.getLength() > 0);
        }
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        // Configure a context with digest auth and a single protected resource
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctxt = tomcat.addContext(CONTEXT_PATH, null);

        // Add protected servlet
        Tomcat.addServlet(ctxt, "TesterServlet", new TesterServlet());
        ctxt.addServletMapping(URI, "TesterServlet");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern(URI);
        SecurityConstraint sc = new SecurityConstraint();
        sc.addAuthRole(ROLE);
        sc.addCollection(collection);
        ctxt.addConstraint(sc);

        // Configure the Realm
        MapRealm realm = new MapRealm();
        realm.addUser(USER, PWD);
        realm.addUserRole(USER, ROLE);
        ctxt.setRealm(realm);

        // Configure the authenticator
        LoginConfig lc = new LoginConfig();
        lc.setAuthMethod("DIGEST");
        lc.setRealmName(REALM);
        ctxt.setLoginConfig(lc);
        ctxt.getPipeline().addValve(new DigestAuthenticator());
    }

    protected static String getNonce(Map<String,List<String>> respHeaders) {
        List<String> authHeaders =
            respHeaders.get(AuthenticatorBase.AUTH_HEADER_NAME);
        // Assume there is only one
        String authHeader = authHeaders.iterator().next();

        int start = authHeader.indexOf("nonce=\"") + 7;
        int end = authHeader.indexOf('\"', start);
        return authHeader.substring(start, end);
    }

    protected static String getOpaque(Map<String,List<String>> respHeaders) {
        List<String> authHeaders =
            respHeaders.get(AuthenticatorBase.AUTH_HEADER_NAME);
        // Assume there is only one
        String authHeader = authHeaders.iterator().next();

        int start = authHeader.indexOf("opaque=\"") + 8;
        int end = authHeader.indexOf('\"', start);
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
            auth.append("");
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


    private static class TesterRequest extends Request {

        @Override
        public String getRemoteAddr() {
            return "127.0.0.1";
        }
    }
}
