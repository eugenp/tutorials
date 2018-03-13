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
package org.apache.catalina.filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestRestCsrfPreventionFilter {

    private static final String NONCE = "nonce";

    private static final String INVALID_NONCE = "invalid-nonce";

    private static final String GET_METHOD = "GET";

    private static final String POST_METHOD = "POST";

    public static final String ACCEPTED_PATH1 = "/accepted/index1.jsp";

    public static final String ACCEPTED_PATH2 = "/accepted/index2.jsp";

    public static final String ACCEPTED_PATHS = ACCEPTED_PATH1 + "," + ACCEPTED_PATH2;

    private RestCsrfPreventionFilter filter;

    private TesterRequest request;

    private TesterResponse response;

    private TesterFilterChain filterChain;

    private HttpSession session;

    @Before
    public void setUp() {
        filter = new RestCsrfPreventionFilter() {
            @Override
            protected String generateNonce() {
                return NONCE;
            }
        };
        request = new TesterRequest();
        response = new TesterResponse();
        filterChain = new TesterFilterChain();
        session = new TesterSession();
    }

    @Test
    public void testGetRequestNoSessionNoNonce() throws Exception {
        setRequestExpectations(GET_METHOD, null, null);
        filter.doFilter(request, response, filterChain);
        verifyContinueChain();
    }

    @Test
    public void testPostRequestNoSessionNoNonce() throws Exception {
        setRequestExpectations(POST_METHOD, null, null);
        filter.doFilter(request, response, filterChain);
        verifyDenyResponse(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    public void testPostRequestSessionNoNonce1() throws Exception {
        setRequestExpectations(POST_METHOD, session, null);
        testPostRequestHeaderScenarios(null, true);
    }

    @Test
    public void testPostRequestSessionNoNonce2() throws Exception {
        setRequestExpectations(POST_METHOD, session, null);
        testPostRequestHeaderScenarios(NONCE, true);
    }

    @Test
    public void testPostRequestSessionInvalidNonce() throws Exception {
        setRequestExpectations(POST_METHOD, session, INVALID_NONCE);
        testPostRequestHeaderScenarios(NONCE, true);
    }

    @Test
    public void testPostRequestSessionValidNonce() throws Exception {
        setRequestExpectations(POST_METHOD, session, NONCE);
        testPostRequestHeaderScenarios(NONCE, false);
    }

    @Test
    public void testGetFetchRequestSessionNoNonce() throws Exception {
        setRequestExpectations(GET_METHOD, session, Constants.CSRF_REST_NONCE_HEADER_FETCH_VALUE);
        filter.doFilter(request, response, filterChain);
        verifyContinueChainNonceAvailable();
    }

    @Test
    public void testPostFetchRequestSessionNoNonce() throws Exception {
        setRequestExpectations(POST_METHOD, session, Constants.CSRF_REST_NONCE_HEADER_FETCH_VALUE);
        testPostRequestHeaderScenarios(null, true);
    }

    @Test
    public void testGetFetchRequestSessionNonce() throws Exception {
        setRequestExpectations(GET_METHOD, session, Constants.CSRF_REST_NONCE_HEADER_FETCH_VALUE);
        session.setAttribute(Constants.CSRF_REST_NONCE_SESSION_ATTR_NAME, NONCE);
        filter.doFilter(request, response, filterChain);
        verifyContinueChainNonceAvailable();
    }

    @Test
    public void testPostFetchRequestSessionNonce() throws Exception {
        setRequestExpectations(POST_METHOD, session, Constants.CSRF_REST_NONCE_HEADER_FETCH_VALUE);
        testPostRequestHeaderScenarios(NONCE, true);
    }

    @Test
    public void testPostRequestCustomDenyStatus() throws Exception {
        setRequestExpectations(POST_METHOD, null, null);
        filter.setDenyStatus(HttpServletResponse.SC_BAD_REQUEST);
        filter.doFilter(request, response, filterChain);
        verifyDenyResponse(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    public void testPostRequestValidNonceAsParameterValidPath1() throws Exception {
        setRequestExpectations(POST_METHOD, session, null, new String[] { NONCE }, ACCEPTED_PATH1);
        testPostRequestParamsScenarios(NONCE, false, true);
    }

    @Test
    public void testPostRequestValidNonceAsParameterValidPath2() throws Exception {
        setRequestExpectations(POST_METHOD, session, null, new String[] { NONCE }, ACCEPTED_PATH2);
        testPostRequestParamsScenarios(NONCE, false, true);
    }

    @Test
    public void testPostRequestInvalidNonceAsParameterValidPath() throws Exception {
        setRequestExpectations(POST_METHOD, session, null, new String[] { INVALID_NONCE },
                ACCEPTED_PATH1);
        testPostRequestParamsScenarios(NONCE, true, true);
    }

    @Test
    public void testPostRequestValidNonceAsParameterInvalidPath() throws Exception {
        setRequestExpectations(POST_METHOD, session, null, new String[] { NONCE }, ACCEPTED_PATH1
                + "blah");
        testPostRequestParamsScenarios(NONCE, true, true);
    }

    @Test
    public void testPostRequestValidNonceAsParameterNoPath() throws Exception {
        setRequestExpectations(POST_METHOD, session, null, new String[] { NONCE }, ACCEPTED_PATH1);
        testPostRequestParamsScenarios(NONCE, true, false);
    }

    @Test
    public void testPostRequestValidNonceAsParameterNoNonceInSession() throws Exception {
        setRequestExpectations(POST_METHOD, session, null, new String[] { NONCE }, ACCEPTED_PATH1);
        testPostRequestParamsScenarios(null, true, true);
    }

    @Test
    public void testPostRequestValidNonceAsParameterInvalidNonceAsHeader() throws Exception {
        setRequestExpectations(POST_METHOD, session, INVALID_NONCE, new String[] { NONCE },
                ACCEPTED_PATH1);
        testPostRequestParamsScenarios(NONCE, true, true);
    }

    @Test
    public void testPostRequestNoNonceAsParameterAndHeaderValidPath() throws Exception {
        setRequestExpectations(POST_METHOD, session, null, null, ACCEPTED_PATH1);
        testPostRequestParamsScenarios(NONCE, true, true);
    }

    @Test
    public void testPostRequestMultipleValidNoncesAsParameterValidPath() throws Exception {
        setRequestExpectations(POST_METHOD, session, null, new String[] { NONCE, NONCE },
                ACCEPTED_PATH1);
        testPostRequestParamsScenarios(NONCE, false, true);
    }

    @Test
    public void testPostRequestMultipleNoncesAsParameterValidPath() throws Exception {
        setRequestExpectations(POST_METHOD, session, null, new String[] { NONCE, INVALID_NONCE },
                ACCEPTED_PATH1);
        testPostRequestParamsScenarios(NONCE, true, true);
    }

    @Test
    public void testPostRequestMultipleInvalidNoncesAsParameterValidPath() throws Exception {
        setRequestExpectations(POST_METHOD, session, null, new String[] { INVALID_NONCE,
                INVALID_NONCE }, ACCEPTED_PATH1);
        testPostRequestParamsScenarios(NONCE, true, true);
    }

    @Test
    public void testGETRequestFetchNonceAsParameter() throws Exception {
        setRequestExpectations(GET_METHOD, null, null,
                new String[] { Constants.CSRF_REST_NONCE_HEADER_FETCH_VALUE }, ACCEPTED_PATH1);
        filter.setPathsAcceptingParams(ACCEPTED_PATHS);
        filter.doFilter(request, response, filterChain);
        verifyContinueChainNonceNotAvailable();
    }

    private void testPostRequestHeaderScenarios(String sessionAttr, boolean denyResponse)
            throws Exception {
        testPostRequestParamsScenarios(sessionAttr, denyResponse, false);
    }

    private void testPostRequestParamsScenarios(String sessionAttr, boolean denyResponse,
            boolean configurePaths) throws Exception {
        session.setAttribute(Constants.CSRF_REST_NONCE_SESSION_ATTR_NAME, sessionAttr);
        if (configurePaths) {
            filter.setPathsAcceptingParams(ACCEPTED_PATHS);
        }
        filter.doFilter(request, response, filterChain);
        if (denyResponse) {
            verifyDenyResponse(HttpServletResponse.SC_FORBIDDEN);
        } else {
            verifyContinueChain();
        }
    }

    private void setRequestExpectations(String method, HttpSession session, String headerValue) {
        setRequestExpectations(method, session, headerValue, null, null);
    }

    private void setRequestExpectations(String method, HttpSession session, String headerValue,
            String[] paramValues, String servletPath) {
        request.setMethod(method);
        request.setSession(session);
        request.setHeader(Constants.CSRF_REST_NONCE_HEADER_NAME, headerValue);
        request.setParameterValues(paramValues);
        request.setServletPath(servletPath);
    }

    private void verifyContinueChain() {
        assertTrue(filterChain.isVisited());
    }

    private void verifyContinueChainNonceAvailable() {
        assertTrue(NONCE.equals(response.getHeader(Constants.CSRF_REST_NONCE_HEADER_NAME)));
        verifyContinueChain();
    }

    private void verifyContinueChainNonceNotAvailable() {
        assertNull(response.getHeader(Constants.CSRF_REST_NONCE_HEADER_NAME));
        verifyContinueChain();
    }

    private void verifyDenyResponse(int statusCode) {
        assertTrue(Constants.CSRF_REST_NONCE_HEADER_REQUIRED_VALUE.equals(response
                .getHeader(Constants.CSRF_REST_NONCE_HEADER_NAME)));
        assertTrue(statusCode == response.getStatus());
        assertTrue(!filterChain.isVisited());
    }

    private static class TesterFilterChain implements FilterChain {
        private boolean visited = false;

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) throws IOException,
                ServletException {
            visited = true;
        }

        boolean isVisited() {
            return visited;
        }
    }

    private static class TesterRequest extends TesterHttpServletRequest {
        private HttpSession session;
        private String[] paramValues;
        private String servletPath;

        void setSession(HttpSession session) {
            this.session = session;
        }

        @Override
        public HttpSession getSession(boolean create) {
            return session;
        }

        void setParameterValues(String[] paramValues) {
            this.paramValues = paramValues;
        }

        @Override
        public String[] getParameterValues(String name) {
            return paramValues;
        }

        void setServletPath(String servletPath) {
            this.servletPath = servletPath;
        }

        @Override
        public String getServletPath() {
            return servletPath;
        }

        @Override
        public String getPathInfo() {
            return "";
        }
    }

    private static class TesterResponse extends TesterHttpServletResponse {
        @Override
        public void sendError(int status, String message) throws IOException {
            setStatus(status);
        }
    }

    private static class TesterSession implements HttpSession {
        Map<String,Object> attributes = new HashMap<String, Object>();
 
        @Override
        public long getCreationTime() {
            return 0;
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public long getLastAccessedTime() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public void setMaxInactiveInterval(int interval) {
        }

        @Override
        public int getMaxInactiveInterval() {
            return 0;
        }

        @Override
        @Deprecated
        public javax.servlet.http.HttpSessionContext getSessionContext() {
            return null;
        }

        @Override
        public Object getAttribute(String name) {
            return attributes.get(name);
        }

        @Override
        public Object getValue(String name) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String[] getValueNames() {
            return null;
        }

        @Override
        public void setAttribute(String name, Object value) {
            attributes.put(name, value);
        }

        @Override
        public void putValue(String name, Object value) {
        }

        @Override
        public void removeAttribute(String name) {
        }

        @Override
        public void removeValue(String name) {
        }

        @Override
        public void invalidate() {
        }

        @Override
        public boolean isNew() {
            return false;
        }
        
    }
}
