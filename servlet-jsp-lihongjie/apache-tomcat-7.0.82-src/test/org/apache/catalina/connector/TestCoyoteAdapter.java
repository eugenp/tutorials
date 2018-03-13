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
package org.apache.catalina.connector;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.SimpleHttpClient;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestCoyoteAdapter extends TomcatBaseTest {

    public static final String TEXT_8K;
    public static final byte[] BYTES_8K;

    static {
        StringBuilder sb = new StringBuilder(8192);
        for (int i = 0; i < 512; i++) {
            sb.append("0123456789ABCDEF");
        }
        TEXT_8K = sb.toString();
        BYTES_8K = TEXT_8K.getBytes(B2CConverter.UTF_8);
    }
    @Test
    public void testPathParmsRootNone() throws Exception {
        pathParamTest("/", "none");
    }

    @Test
    public void testPathParmsFooNone() throws Exception {
        pathParamTest("/foo", "none");
    }

    @Test
    public void testPathParmsRootSessionOnly() throws Exception {
        pathParamTest("/;jsessionid=1234", "1234");
    }

    @Test
    public void testPathParmsFooSessionOnly() throws Exception {
        pathParamTest("/foo;jsessionid=1234", "1234");
    }

    @Test
    public void testPathParmsFooSessionDummy() throws Exception {
        pathParamTest("/foo;jsessionid=1234;dummy", "1234");
    }

    @Test
    public void testPathParmsFooSessionDummyValue() throws Exception {
        pathParamTest("/foo;jsessionid=1234;dummy=5678", "1234");
    }

    @Test
    public void testPathParmsFooSessionValue() throws Exception {
        pathParamTest("/foo;jsessionid=1234;=5678", "1234");
    }

    @Test
    public void testPathParmsFooSessionBar() throws Exception {
        pathParamTest("/foo;jsessionid=1234/bar", "1234");
    }

    @Test
    public void testPathParamsRedirect() throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // Must have a real docBase. Don't use java.io.tmpdir as it may not be
        // writable.
        File docBase = new File(getTemporaryDirectory(), "testCoyoteAdapter");
        addDeleteOnTearDown(docBase);
        if (!docBase.mkdirs() && !docBase.isDirectory()) {
            Assert.fail("Failed to create: [" + docBase.toString() + "]");
        }

        // Create the folder that will trigger the redirect
        File foo = new File(docBase, "foo");
        addDeleteOnTearDown(foo);
        if (!foo.mkdirs() && !foo.isDirectory()) {
            Assert.fail("Unable to create foo directory in docBase");
        }

        Context ctx = tomcat.addContext("", docBase.getAbsolutePath());

        Tomcat.addServlet(ctx, "servlet", new PathParamServlet());
        ctx.addServletMapping("/", "servlet");

        tomcat.start();

        testPath("/", "none");
        testPath("/;jsessionid=1234", "1234");
        testPath("/foo;jsessionid=1234", "1234");
        testPath("/foo;jsessionid=1234;dummy", "1234");
        testPath("/foo;jsessionid=1234;dummy=5678", "1234");
        testPath("/foo;jsessionid=1234;=5678", "1234");
        testPath("/foo;jsessionid=1234/bar", "1234");
    }

    private void pathParamTest(String path, String expected) throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "servlet", new PathParamServlet());
        ctx.addServletMapping("/", "servlet");

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + path);
        Assert.assertEquals(expected, res.toString());
    }

    private void testPath(String path, String expected) throws Exception {
        ByteChunk res = getUrl("http://localhost:" + getPort() + path);
        Assert.assertEquals(expected, res.toString());
    }

    private static class PathParamServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            PrintWriter pw = resp.getWriter();
            String sessionId = req.getRequestedSessionId();
            if (sessionId == null) {
                sessionId = "none";
            }
            pw.write(sessionId);
        }
    }

    @Test
    public void testPathParamExtRootNoParam() throws Exception {
        pathParamExtensionTest("/testapp/blah.txt", "none");
    }

    @Test
    public void testPathParamExtLevel1NoParam() throws Exception {
        pathParamExtensionTest("/testapp/blah/blah.txt", "none");
    }

    @Test
    public void testPathParamExtLevel1WithParam() throws Exception {
        pathParamExtensionTest("/testapp/blah;x=y/blah.txt", "none");
    }

    private void pathParamExtensionTest(String path, String expected)
            throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("/testapp", null);

        Tomcat.addServlet(ctx, "servlet", new PathParamServlet());
        ctx.addServletMapping("*.txt", "servlet");

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + path);
        Assert.assertEquals(expected, res.toString());
    }

    @Test
    public void testBug54602a() throws Exception {
        // No UTF-8
        doTestUriDecoding("/foo", "UTF-8", "/foo");
    }

    @Test
    public void testBug54602b() throws Exception {
        // Valid UTF-8
        doTestUriDecoding("/foo%c4%87", "UTF-8", "/foo\u0107");
    }

    @Test
    public void testBug54602c() throws Exception {
        // Partial UTF-8
        doTestUriDecoding("/foo%c4", "UTF-8", "/foo\uFFFD");
    }

    @Test
    public void testBug54602d() throws Exception {
        // Invalid UTF-8
        doTestUriDecoding("/foo%ff", "UTF-8", "/foo\uFFFD");
    }

    @Test
    public void testBug54602e() throws Exception {
        // Invalid UTF-8
        doTestUriDecoding("/foo%ed%a0%80", "UTF-8", "/foo\uFFFD\uFFFD\uFFFD");
    }

    private void doTestUriDecoding(String path, String encoding,
            String expectedPathInfo) throws Exception{

        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        tomcat.getConnector().setURIEncoding(encoding);

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        PathInfoServlet servlet = new PathInfoServlet();
        Tomcat.addServlet(ctx, "servlet", servlet);
        ctx.addServletMapping("/*", "servlet");

        tomcat.start();

        int rc = getUrl("http://localhost:" + getPort() + path,
                new ByteChunk(), null);
        Assert.assertEquals(HttpServletResponse.SC_OK, rc);

        Assert.assertEquals(expectedPathInfo, servlet.getPathInfo());
    }

    private static class PathInfoServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private String pathInfo = null;

        public String getPathInfo() {
            return pathInfo;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            // Not thread safe
            pathInfo = req.getPathInfo();
        }
    }


    @Test
    public void testBug54928() throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        AsyncServlet servlet = new AsyncServlet();
        Wrapper w = Tomcat.addServlet(ctx, "async", servlet);
        w.setAsyncSupported(true);
        ctx.addServletMapping("/async", "async");

        tomcat.start();

        SimpleHttpClient client = new SimpleHttpClient() {
            @Override
            public boolean isResponseBodyOK() {
                return true;
            }
        };

        String request = "GET /async HTTP/1.1" + SimpleHttpClient.CRLF +
                "Host: a" + SimpleHttpClient.CRLF + SimpleHttpClient.CRLF;

        client.setPort(getPort());
        client.setRequest(new String[] {request});

        client.connect();
        client.sendRequest();

        for (int i = 0; i < 10; i++) {
            String line = client.readLine();
            if (line != null && line.length() > 20) {
                log.info(line.subSequence(0, 20) + "...");
            }
        }

        client.disconnect();

        // Wait for server thread to stop
        Thread t = servlet.getThread();
        long startTime = System.nanoTime();
        t.join(5000);
        long endTime = System.nanoTime();
        log.info("Waited for servlet thread to stop for "
                + (endTime - startTime) / 1000000 + " ms");

        Assert.assertTrue(servlet.isCompleted());
    }

    private class AsyncServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        // This is a hack that won't work generally as servlets are expected to
        // handle more than one request.
        private Thread t;
        private volatile boolean completed = false;

        public Thread getThread() {
            return t;
        }

        public boolean isCompleted() {
            return completed;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");

            final OutputStream os = resp.getOutputStream();

            final AsyncContext asyncCtxt = req.startAsync();
            asyncCtxt.setTimeout(3000);

            t = new Thread(new Runnable() {

                @Override
                public void run() {
                    for (int i = 0; i < 20; i++) {
                        try {
                            // Some tests depend on this write failing (e.g.
                            // because the client has gone away). In some cases
                            // there may be a large (ish) buffer to fill before
                            // the write fails.
                            for (int j = 0 ; j < 8; j++) {
                                os.write(BYTES_8K);
                            }
                            os.flush();
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            log.info("Exception caught " + e);
                            try {
                                // Note if request times out before this
                                // exception is thrown and the complete call
                                // below is made, the complete call below will
                                // fail since the timeout will have completed
                                // the request.
                                asyncCtxt.complete();
                                break;
                            } finally {
                                completed = true;
                            }
                        }
                    }
                }
            });
            t.setName("testBug54928");
            t.start();
        }
    }
}
