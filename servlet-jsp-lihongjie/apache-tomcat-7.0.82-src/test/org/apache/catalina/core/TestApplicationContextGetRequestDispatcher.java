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
package org.apache.catalina.core;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.catalina.util.URLEncoder;
import org.apache.tomcat.util.buf.ByteChunk;

@RunWith(value = Parameterized.class)
public class TestApplicationContextGetRequestDispatcher extends TomcatBaseTest {

    private final boolean useAsync;

    public TestApplicationContextGetRequestDispatcher(boolean useAsync) {
        this.useAsync = useAsync;
    }

    @Parameters(name = "{index}: useAsync[{0}]")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Boolean.TRUE},
                {Boolean.FALSE}
        });
    }

    @Test
    public void testGetRequestDispatcherNullPath01() throws Exception {
        doTestGetRequestDispatcher(true, "/start", null, null, "/target", DispatcherServlet.NULL);
    }


    @Test
    public void testGetRequestDispatcherNullPath02() throws Exception {
        doTestGetRequestDispatcher(false, "/start", null, null, "/target", DispatcherServlet.NULL);
    }


    @Test
    public void testGetRequestDispatcherOutsideContextRoot01() throws Exception {
        doTestGetRequestDispatcher(
                true, "/start", null, "../outside", "/target", DispatcherServlet.NULL);
    }


    @Test
    public void testGetRequestDispatcherOutsideContextRoot02() throws Exception {
        doTestGetRequestDispatcher(
                false, "/start", null, "../outside", "/target", DispatcherServlet.NULL);
    }


    @Test
    public void testGetRequestDispatcherEncodedTraversal() throws Exception {
        doTestGetRequestDispatcher(
                true, "/prefix/start", null, "%2E%2E/target", "/target", DispatcherServlet.NULL);
    }


    @Test
    public void testGetRequestDispatcherTraversal01() throws Exception {
        doTestGetRequestDispatcher(
                true, "/prefix/start", null, "../target", "/target", TargetServlet.OK);
    }


    @Test
    public void testGetRequestDispatcherTraversal02() throws Exception {
        doTestGetRequestDispatcher(
                false, "/prefix/start", null, "../target", "/target", TargetServlet.OK);
    }


    @Test
    public void testGetRequestDispatcherTraversal03() throws Exception {
        doTestGetRequestDispatcher(
                true, "/prefix/start", null, "../target?a=b", "/target", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcherTraversal04() throws Exception {
        doTestGetRequestDispatcher(
                false, "/prefix/start", null, "../target?a=b", "/target", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcherTraversal05() throws Exception {
        doTestGetRequestDispatcher(
                true, "/prefix/start", "a=b", "../target", "/target", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcherTraversal06() throws Exception {
        doTestGetRequestDispatcher(
                false, "/prefix/start", "a=b", "../target", "/target", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher01() throws Exception {
        doTestGetRequestDispatcher(
                true, "/prefix/start", null, "target", "/prefix/target", TargetServlet.OK);
    }


    @Test
    public void testGetRequestDispatcher02() throws Exception {
        doTestGetRequestDispatcher(
                false, "/prefix/start", null, "target", "/prefix/target", TargetServlet.OK);
    }


    @Test
    public void testGetRequestDispatcher03() throws Exception {
        doTestGetRequestDispatcher(true, "/prefix/start", null, "target?a=b", "/prefix/target",
                TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher04() throws Exception {
        doTestGetRequestDispatcher(false, "/prefix/start", null, "target?a=b", "/prefix/target",
                TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher05() throws Exception {
        doTestGetRequestDispatcher(true, "/prefix/start", "a=b", "target", "/prefix/target",
                TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher06() throws Exception {
        doTestGetRequestDispatcher(false, "/prefix/start", "a=b", "target", "/prefix/target",
                TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher11() throws Exception {
        doTestGetRequestDispatcher(true, "/aa%3Fbb%3Dcc/start", null, "target",
                "/aa%3Fbb%3Dcc/target", TargetServlet.OK);
    }


    @Test
    public void testGetRequestDispatcher12() throws Exception {
        // Expected to fail because when the RD processes this as unencoded it
        // sees /aa?bb=cc/target which it thinks is a query string. This is why
        // Tomcat encodes by default.
        doTestGetRequestDispatcher(false, "/aa%3Fbb%3Dcc/start", null, "target",
                "/aa%3Fbb%3Dcc/target", Default404Servlet.DEFAULT_404);
    }


    @Test
    public void testGetRequestDispatcher13() throws Exception {
        doTestGetRequestDispatcher(true, "/aa%3Fbb%3Dcc/start", null, "target?a=b",
                "/aa%3Fbb%3Dcc/target", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher14() throws Exception {
        // Expected to fail because when the RD processes this as unencoded it
        // sees /aa?bb=cc/target which it thinks is a query string. This is why
        // Tomcat encodes by default.
        doTestGetRequestDispatcher(false, "/aa%3Fbb%3Dcc/start", null, "target?a=b",
                "/aa%3Fbb%3Dcc/target", Default404Servlet.DEFAULT_404);
    }


    @Test
    public void testGetRequestDispatcher15() throws Exception {
        doTestGetRequestDispatcher(true, "/aa%3Fbb%3Dcc/start", "a=b", "target",
                "/aa%3Fbb%3Dcc/target", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher16() throws Exception {
        // Expected to fail because when the RD processes this as unencoded it
        // sees /aa?bb=cc/target which it thinks is a query string. This is why
        // Tomcat encodes by default.
        doTestGetRequestDispatcher(false, "/aa%3Fbb%3Dcc/start", "a=b", "target",
                "/aa%3Fbb%3Dcc/target", Default404Servlet.DEFAULT_404);
    }


    @Test
    public void testGetRequestDispatcher21() throws Exception {
        doTestGetRequestDispatcher(true, "/aa%3Dbb%3Dcc/start", null, "target",
                "/aa%3Dbb%3Dcc/target", TargetServlet.OK);
    }


    @Test
    public void testGetRequestDispatcher22() throws Exception {
        doTestGetRequestDispatcher(false, "/aa%3Dbb%3Dcc/start", null, "target",
                "/aa%3Dbb%3Dcc/target", TargetServlet.OK);
    }


    @Test
    public void testGetRequestDispatcher23() throws Exception {
        doTestGetRequestDispatcher(true, "/aa%3Dbb%3Dcc/start", null, "target?a=b",
                "/aa%3Dbb%3Dcc/target", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher24() throws Exception {
        doTestGetRequestDispatcher(false, "/aa%3Dbb%3Dcc/start", null, "target?a=b",
                "/aa%3Dbb%3Dcc/target", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher25() throws Exception {
        doTestGetRequestDispatcher(true, "/aa%3Dbb%3Dcc/start", "a=b", "target",
                "/aa%3Dbb%3Dcc/target", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher26() throws Exception {
        doTestGetRequestDispatcher(false, "/aa%3Dbb%3Dcc/start", "a=b", "target",
                "/aa%3Dbb%3Dcc/target", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher31() throws Exception {
        doTestGetRequestDispatcher(true, "/prefix/start", null, "aa%3Fbb%3Dcc",
                "/prefix/aa%3Fbb%3Dcc", TargetServlet.OK);
    }


    @Test
    public void testGetRequestDispatcher32() throws Exception {
        doTestGetRequestDispatcher(false, "/prefix/start", null, "aa%3Fbb%3Dcc",
                "/prefix/aa%3Fbb%3Dcc", Default404Servlet.DEFAULT_404);
    }


    @Test
    public void testGetRequestDispatcher33() throws Exception {
        doTestGetRequestDispatcher(true, "/prefix/start", null, "aa%3Fbb%3Dcc?a=b",
                "/prefix/aa%3Fbb%3Dcc", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher34() throws Exception {
        doTestGetRequestDispatcher(false, "/prefix/start", null, "aa%3Fbb%3Dcc?a=b",
                "/prefix/aa%3Fbb%3Dcc", Default404Servlet.DEFAULT_404);
    }


    @Test
    public void testGetRequestDispatcher35() throws Exception {
        doTestGetRequestDispatcher(true, "/prefix/start", "a=b", "aa%3Fbb%3Dcc",
                "/prefix/aa%3Fbb%3Dcc", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher36() throws Exception {
        doTestGetRequestDispatcher(false, "/prefix/start", "a=b", "aa%3Fbb%3Dcc",
                "/prefix/aa%3Fbb%3Dcc", Default404Servlet.DEFAULT_404);
    }


    @Test
    public void testGetRequestDispatcher41() throws Exception {
        doTestGetRequestDispatcher(true, "/prefix/start", null, "aa%3Fbb%3Dcc",
                "/prefix/aa%253Fbb%253Dcc", Default404Servlet.DEFAULT_404);
    }


    @Test
    public void testGetRequestDispatcher42() throws Exception {
        doTestGetRequestDispatcher(false, "/prefix/start", null, "aa%3Fbb%3Dcc",
                "/prefix/aa%253Fbb%253Dcc", TargetServlet.OK);
    }


    @Test
    public void testGetRequestDispatcher43() throws Exception {
        doTestGetRequestDispatcher(true, "/prefix/start", null, "aa%3Fbb%3Dcc?a=b",
                "/prefix/aa%253Fbb%253Dcc", Default404Servlet.DEFAULT_404);
    }


    @Test
    public void testGetRequestDispatcher44() throws Exception {
        doTestGetRequestDispatcher(false, "/prefix/start", null, "aa%3Fbb%3Dcc?a=b",
                "/prefix/aa%253Fbb%253Dcc", TargetServlet.OK + "a=b");
    }


    @Test
    public void testGetRequestDispatcher45() throws Exception {
        doTestGetRequestDispatcher(true, "/prefix/start", "a=b", "aa%3Fbb%3Dcc",
                "/prefix/aa%253Fbb%253Dcc", Default404Servlet.DEFAULT_404);
    }


    @Test
    public void testGetRequestDispatcher46() throws Exception {
        doTestGetRequestDispatcher(false, "/prefix/start", "a=b", "aa%3Fbb%3Dcc",
                "/prefix/aa%253Fbb%253Dcc", TargetServlet.OK + "a=b");
    }


    private void doTestGetRequestDispatcher(boolean useEncodedDispatchPaths, String startPath,
            String startQueryString, String dispatchPath, String targetPath, String expectedBody)
            throws Exception {

        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("/test", null);
        ctx.setDispatchersUseEncodedPaths(useEncodedDispatchPaths);

        // Add a default servlet to return 404 for not found resources
        Tomcat.addServlet(ctx, "Default", new Default404Servlet());
        ctx.addServletMapping("/*", "Default");

        // Add a target servlet to dispatch to
        Tomcat.addServlet(ctx, "target", new TargetServlet());
        // Note: This will decode the provided path
        ctx.addServletMapping(targetPath, "target");

        if (useAsync) {
            Wrapper w = Tomcat.addServlet(
                    ctx, "rd", new AsyncDispatcherServlet(dispatchPath, useEncodedDispatchPaths));
            w.setAsyncSupported(true);
        } else {
            Tomcat.addServlet(ctx, "rd", new DispatcherServlet(dispatchPath));
        }
        // Note: This will decode the provided path
        ctx.addServletMapping(startPath, "rd");

        tomcat.start();

        StringBuilder url = new StringBuilder("http://localhost:");
        url.append(getPort());
        url.append("/test");
        url.append(startPath);
        if (startQueryString != null) {
            url.append('?');
            url.append(startQueryString);
        }

        ByteChunk bc = getUrl(url.toString());
        String body = bc.toString();

        Assert.assertEquals(expectedBody, body);
    }


    private static class Default404Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        private static final String DEFAULT_404 = "DEFAULT-404";

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(DEFAULT_404);
            resp.setStatus(404);
        }
    }


    private static class DispatcherServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        private static final String NULL = "RD-NULL";

        private final String dispatchPath;

        public DispatcherServlet(String dispatchPath) {
            this.dispatchPath = dispatchPath;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            RequestDispatcher rd = req.getRequestDispatcher(dispatchPath);
            if (rd == null) {
                resp.setContentType("text/plain");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().print(NULL);
            } else {
                rd.forward(req, resp);
            }
        }
    }


    private static class TargetServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        private static final String OK = "OK";

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print(OK);
            String qs = req.getQueryString();
            if (qs != null) {
                resp.getWriter().print(qs);
            }
        }
    }


    private static class AsyncDispatcherServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        private static final String NULL = "RD-NULL";

        private final String dispatchPath;
        private final boolean encodePath;

        public AsyncDispatcherServlet(String dispatchPath, boolean encodePath) {
            this.dispatchPath = dispatchPath;
            this.encodePath = encodePath;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            AsyncContext ac = req.startAsync();
            // Quick and dirty. Sufficient for this test but ignores lots of
            // edge cases.
            String target = null;
            if (dispatchPath != null) {
                target = req.getServletPath();
                int lastSlash = target.lastIndexOf('/');
                target = target.substring(0, lastSlash + 1);
                if (encodePath) {
                    target = URLEncoder.DEFAULT.encode(target, "UTF-8");
                }
                target += dispatchPath;
            }
            try {
                ac.dispatch(target);
            } catch (UnsupportedOperationException uoe) {
                ac.complete();
                resp.setContentType("text/plain");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().print(NULL);
            }
        }
    }
}
