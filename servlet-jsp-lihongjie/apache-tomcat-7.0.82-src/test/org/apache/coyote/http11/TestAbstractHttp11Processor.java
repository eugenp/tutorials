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
package org.apache.coyote.http11;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.startup.SimpleHttpClient;
import org.apache.catalina.startup.TesterServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestAbstractHttp11Processor extends TomcatBaseTest {

    @Test
    public void testResponseWithErrorChunked() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctxt = tomcat.addContext("", null);

        // Add protected servlet
        Tomcat.addServlet(ctxt, "ChunkedResponseWithErrorServlet",
                new ResponseWithErrorServlet(true));
        ctxt.addServletMapping("/*", "ChunkedResponseWithErrorServlet");

        tomcat.start();

        String request =
                "GET /anything HTTP/1.1" + SimpleHttpClient.CRLF +
                "Host: any" + SimpleHttpClient.CRLF +
                 SimpleHttpClient.CRLF;

        Client client = new Client(tomcat.getConnector().getLocalPort());
        client.setRequest(new String[] {request});

        client.connect();
        client.processRequest();

        // Expected response is a 200 response followed by an incomplete chunked
        // body.
        assertTrue(client.isResponse200());
        // There should not be an end chunk
        assertFalse(client.getResponseBody().endsWith("0"));
        // The last portion of text should be there
        assertTrue(client.getResponseBody().endsWith("line03"));
    }

    private static class ResponseWithErrorServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private final boolean useChunks;

        public ResponseWithErrorServlet(boolean useChunks) {
            this.useChunks = useChunks;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            if (!useChunks) {
                // Longer than it needs to be because response will fail before
                // it is complete
                resp.setContentLength(100);
            }
            PrintWriter pw = resp.getWriter();
            pw.print("line01");
            pw.flush();
            resp.flushBuffer();
            pw.print("line02");
            pw.flush();
            resp.flushBuffer();
            pw.print("line03");

            // Now throw a RuntimeException to end this request
            throw new ServletException("Deliberate failure");
        }
    }


    @Test
    public void testWithUnknownExpectation() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // Use the normal Tomcat ROOT context
        File root = new File("test/webapp-3.0");
        tomcat.addWebapp("", root.getAbsolutePath());

        tomcat.start();

        String request =
            "POST /echo-params.jsp HTTP/1.1" + SimpleHttpClient.CRLF +
            "Host: any" + SimpleHttpClient.CRLF +
            "Expect: unknoen" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF;

        Client client = new Client(tomcat.getConnector().getLocalPort());
        client.setRequest(new String[] {request});

        client.connect();
        client.processRequest();
        assertTrue(client.isResponse417());
    }


    @Test
    public void testWithTEVoid() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // Use the normal Tomcat ROOT context
        File root = new File("test/webapp-3.0");
        tomcat.addWebapp("", root.getAbsolutePath());

        tomcat.start();

        String request =
            "POST /echo-params.jsp HTTP/1.1" + SimpleHttpClient.CRLF +
            "Host: any" + SimpleHttpClient.CRLF +
            "Transfer-encoding: void" + SimpleHttpClient.CRLF +
            "Content-Length: 9" + SimpleHttpClient.CRLF +
            "Content-Type: application/x-www-form-urlencoded" +
                    SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF +
            "test=data";

        Client client = new Client(tomcat.getConnector().getLocalPort());
        client.setRequest(new String[] {request});

        client.connect();
        client.processRequest();
        assertTrue(client.isResponse501());
    }

    @Test
    public void testWithTEBuffered() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // Use the normal Tomcat ROOT context
        File root = new File("test/webapp-3.0");
        tomcat.addWebapp("", root.getAbsolutePath());

        tomcat.start();

        String request =
            "POST /echo-params.jsp HTTP/1.1" + SimpleHttpClient.CRLF +
            "Host: any" + SimpleHttpClient.CRLF +
            "Transfer-encoding: buffered" + SimpleHttpClient.CRLF +
            "Content-Length: 9" + SimpleHttpClient.CRLF +
            "Content-Type: application/x-www-form-urlencoded" +
                    SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF +
            "test=data";

        Client client = new Client(tomcat.getConnector().getLocalPort());
        client.setRequest(new String[] {request});

        client.connect();
        client.processRequest();
        assertTrue(client.isResponse501());
    }


    @Test
    public void testWithTEChunked() throws Exception {
        doTestWithTEChunked(false);
    }


    @Test
    public void testWithTEChunkedWithCL() throws Exception {
        // Should be ignored
        doTestWithTEChunked(true);
    }


    private void doTestWithTEChunked(boolean withCL)
            throws Exception {

        Tomcat tomcat = getTomcatInstance();

        // Use the normal Tomcat ROOT context
        File root = new File("test/webapp-3.0");
        tomcat.addWebapp("", root.getAbsolutePath());

        tomcat.start();

        String request =
            "POST /echo-params.jsp HTTP/1.1" + SimpleHttpClient.CRLF +
            "Host: any" + SimpleHttpClient.CRLF +
            (withCL ? "Content-length: 1" + SimpleHttpClient.CRLF : "") +
            "Transfer-encoding: chunked" + SimpleHttpClient.CRLF +
            "Content-Type: application/x-www-form-urlencoded" +
                    SimpleHttpClient.CRLF +
            "Connection: close" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF +
            "9" + SimpleHttpClient.CRLF +
            "test=data" + SimpleHttpClient.CRLF +
            "0" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF;

        Client client = new Client(tomcat.getConnector().getLocalPort());
        client.setRequest(new String[] {request});

        client.connect();
        client.processRequest();
        assertTrue(client.isResponse200());
        assertTrue(client.getResponseBody().contains("test - data"));
    }


    @Test
    public void testWithTEIdentity() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // Use the normal Tomcat ROOT context
        File root = new File("test/webapp-3.0");
        tomcat.addWebapp("", root.getAbsolutePath());

        tomcat.start();

        String request =
            "POST /echo-params.jsp HTTP/1.1" + SimpleHttpClient.CRLF +
            "Host: any" + SimpleHttpClient.CRLF +
            "Transfer-encoding: identity" + SimpleHttpClient.CRLF +
            "Content-Length: 9" + SimpleHttpClient.CRLF +
            "Content-Type: application/x-www-form-urlencoded" +
                    SimpleHttpClient.CRLF +
            "Connection: close" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF +
            "test=data";

        Client client = new Client(tomcat.getConnector().getLocalPort());
        client.setRequest(new String[] {request});

        client.connect();
        client.processRequest();
        assertTrue(client.isResponse200());
        assertTrue(client.getResponseBody().contains("test - data"));
    }


    @Test
    public void testWithTESavedRequest() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // Use the normal Tomcat ROOT context
        File root = new File("test/webapp-3.0");
        tomcat.addWebapp("", root.getAbsolutePath());

        tomcat.start();

        String request =
            "POST /echo-params.jsp HTTP/1.1" + SimpleHttpClient.CRLF +
            "Host: any" + SimpleHttpClient.CRLF +
            "Transfer-encoding: savedrequest" + SimpleHttpClient.CRLF +
            "Content-Length: 9" + SimpleHttpClient.CRLF +
            "Content-Type: application/x-www-form-urlencoded" +
                    SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF +
            "test=data";

        Client client = new Client(tomcat.getConnector().getLocalPort());
        client.setRequest(new String[] {request});

        client.connect();
        client.processRequest();
        assertTrue(client.isResponse501());
    }


    @Test
    public void testWithTEUnsupported() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // Use the normal Tomcat ROOT context
        File root = new File("test/webapp-3.0");
        tomcat.addWebapp("", root.getAbsolutePath());

        tomcat.start();

        String request =
            "POST /echo-params.jsp HTTP/1.1" + SimpleHttpClient.CRLF +
            "Host: any" + SimpleHttpClient.CRLF +
            "Transfer-encoding: unsupported" + SimpleHttpClient.CRLF +
            "Content-Length: 9" + SimpleHttpClient.CRLF +
            "Content-Type: application/x-www-form-urlencoded" +
                    SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF +
            "test=data";

        Client client = new Client(tomcat.getConnector().getLocalPort());
        client.setRequest(new String[] {request});

        client.connect();
        client.processRequest();
        assertTrue(client.isResponse501());
    }


    @Test
    public void testPipelining() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctxt = tomcat.addContext("", null);

        // Add protected servlet
        Tomcat.addServlet(ctxt, "TesterServlet", new TesterServlet());
        ctxt.addServletMapping("/foo", "TesterServlet");

        tomcat.start();

        String requestPart1 =
            "GET /foo HTTP/1.1" + SimpleHttpClient.CRLF;
        String requestPart2 =
            "Host: any" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF;

        final Client client = new Client(tomcat.getConnector().getLocalPort());
        client.setRequest(new String[] {requestPart1, requestPart2});
        client.setRequestPause(1000);
        client.setUseContentLength(true);
        client.connect();

        Runnable send = new Runnable() {
            @Override
            public void run() {
                try {
                    client.sendRequest();
                    client.sendRequest();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread t = new Thread(send);
        t.start();

        // Sleep for 1500 ms which should mean the all of request 1 has been
        // sent and half of request 2
        Thread.sleep(1500);

        // Now read the first response
        client.readResponse(true);
        assertFalse(client.isResponse50x());
        assertTrue(client.isResponse200());
        assertEquals("OK", client.getResponseBody());

        // Read the second response. No need to sleep, read will block until
        // there is data to process
        client.readResponse(true);
        assertFalse(client.isResponse50x());
        assertTrue(client.isResponse200());
        assertEquals("OK", client.getResponseBody());
    }


    @Test
    public void testChunking11NoContentLength() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctxt = tomcat.addContext("", null);

        Tomcat.addServlet(ctxt, "NoContentLengthFlushingServlet",
                new NoContentLengthFlushingServlet());
        ctxt.addServletMapping("/test", "NoContentLengthFlushingServlet");

        tomcat.start();

        ByteChunk responseBody = new ByteChunk();
        Map<String,List<String>> responseHeaders =
                new HashMap<String,List<String>>();
        int rc = getUrl("http://localhost:" + getPort() + "/test", responseBody,
                responseHeaders);

        assertEquals(HttpServletResponse.SC_OK, rc);
        assertTrue(responseHeaders.containsKey("Transfer-Encoding"));
        List<String> encodings = responseHeaders.get("Transfer-Encoding");
        assertEquals(1, encodings.size());
        assertEquals("chunked", encodings.get(0));
    }

    @Test
    public void testNoChunking11NoContentLengthConnectionClose()
            throws Exception {

        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctxt = tomcat.addContext("", null);

        Tomcat.addServlet(ctxt, "NoContentLengthConnectionCloseFlushingServlet",
                new NoContentLengthConnectionCloseFlushingServlet());
        ctxt.addServletMapping("/test",
                "NoContentLengthConnectionCloseFlushingServlet");

        tomcat.start();

        ByteChunk responseBody = new ByteChunk();
        Map<String,List<String>> responseHeaders =
                new HashMap<String,List<String>>();
        int rc = getUrl("http://localhost:" + getPort() + "/test", responseBody,
                responseHeaders);

        assertEquals(HttpServletResponse.SC_OK, rc);

        assertTrue(responseHeaders.containsKey("Connection"));
        List<String> connections = responseHeaders.get("Connection");
        assertEquals(1, connections.size());
        assertEquals("close", connections.get(0));

        assertFalse(responseHeaders.containsKey("Transfer-Encoding"));

        assertEquals("OK", responseBody.toString());
    }

    @Test
    public void testBug53677a() throws Exception {
        doTestBug53677(false);
    }

    @Test
    public void testBug53677b() throws Exception {
        doTestBug53677(true);
    }

    private void doTestBug53677(boolean flush) throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctxt = tomcat.addContext("", null);

        Tomcat.addServlet(ctxt, "LargeHeaderServlet",
                new LargeHeaderServlet(flush));
        ctxt.addServletMapping("/test", "LargeHeaderServlet");

        tomcat.start();

        ByteChunk responseBody = new ByteChunk();
        Map<String,List<String>> responseHeaders =
                new HashMap<String,List<String>>();
        int rc = getUrl("http://localhost:" + getPort() + "/test", responseBody,
                responseHeaders);

        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, rc);
        if (responseBody.getLength() > 0) {
            // It will be >0 if the standard error page handlign has been
            // triggered
            assertFalse(responseBody.toString().contains("FAIL"));
        }
    }


    private static CountDownLatch bug55772Latch1 = new CountDownLatch(1);
    private static CountDownLatch bug55772Latch2 = new CountDownLatch(1);
    private static CountDownLatch bug55772Latch3 = new CountDownLatch(1);
    private static boolean bug55772IsSecondRequest = false;
    private static boolean bug55772RequestStateLeaked = false;


    @Test
    public void testBug55772() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        tomcat.getConnector().setProperty("processorCache", "1");
        tomcat.getConnector().setProperty("maxThreads", "1");

        // No file system docBase required
        Context ctxt = tomcat.addContext("", null);

        Tomcat.addServlet(ctxt, "async", new Bug55772Servlet());
        ctxt.addServletMapping("/*", "async");

        tomcat.start();

        String request1 = "GET /async?1 HTTP/1.1\r\n" +
                "Host: localhost:" + getPort() + "\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                "User-Agent: Request1\r\n" +
                "Accept-Encoding: gzip,deflate,sdch\r\n" +
                "Accept-Language: en-US,en;q=0.8,fr;q=0.6,es;q=0.4\r\n" +
                "Cookie: something.that.should.not.leak=true\r\n" +
                "\r\n";

        String request2 = "GET /async?2 HTTP/1.1\r\n" +
                "Host: localhost:" + getPort() + "\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                "User-Agent: Request2\r\n" +
                "Accept-Encoding: gzip,deflate,sdch\r\n" +
                "Accept-Language: en-US,en;q=0.8,fr;q=0.6,es;q=0.4\r\n" +
                "\r\n";

        Socket connection = null;
        try {
            connection = new Socket("localhost", getPort());
            connection.setSoLinger(true, 0);
            Writer writer = new OutputStreamWriter(connection.getOutputStream(),
                    B2CConverter.getCharset("US-ASCII"));
            writer.write(request1);
            writer.flush();

            bug55772Latch1.await();
            connection.close();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException ioe) {
                    // Ignore
                }
            }
        }

        bug55772Latch2.await();
        bug55772IsSecondRequest = true;

        try {
            connection = new Socket("localhost", getPort());
            connection.setSoLinger(true, 0);
            Writer writer = new OutputStreamWriter(connection.getOutputStream(),
                    B2CConverter.getCharset("US-ASCII"));
            writer.write(request2);
            writer.flush();
            connection.getInputStream().read();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException ioe) {
                    // Ignore
                }
            }
        }

        bug55772Latch3.await();
        if (bug55772RequestStateLeaked) {
            Assert.fail("State leaked between requests!");
        }
    }


    // https://bz.apache.org/bugzilla/show_bug.cgi?id=57324
    @Test
    public void testNon2xxResponseWithExpectation() throws Exception {
        doTestNon2xxResponseAndExpectation(true);
    }

    @Test
    public void testNon2xxResponseWithoutExpectation() throws Exception {
        doTestNon2xxResponseAndExpectation(false);
    }

    private void doTestNon2xxResponseAndExpectation(boolean useExpectation) throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "echo", new EchoBodyServlet());
        ctx.addServletMapping("/echo", "echo");

        SecurityCollection collection = new SecurityCollection("All", "");
        collection.addPattern("/*");
        SecurityConstraint constraint = new SecurityConstraint();
        constraint.addAuthRole("Any");
        constraint.addCollection(collection);
        ctx.addConstraint(constraint);

        tomcat.start();

        Non2xxResponseClient client = new Non2xxResponseClient(useExpectation);
        client.setPort(getPort());
        client.doResourceRequest("GET http://localhost:" + getPort()
                + "/echo HTTP/1.1", "HelloWorld");
        Assert.assertTrue(client.isResponse403());
        Assert.assertTrue(client.checkConnectionHeader());
    }


    private static class Bug55772Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if (bug55772IsSecondRequest) {
                Cookie[] cookies = req.getCookies();
                if (cookies != null && cookies.length > 0) {
                    for (Cookie cookie : req.getCookies()) {
                        if (cookie.getName().equalsIgnoreCase("something.that.should.not.leak")) {
                            bug55772RequestStateLeaked = true;
                        }
                    }
                }
                bug55772Latch3.countDown();
            } else {
                req.getCookies(); // We have to do this so Tomcat will actually parse the cookies from the request
            }

            req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", Boolean.TRUE);
            AsyncContext asyncContext = req.startAsync();
            asyncContext.setTimeout(5000);

            bug55772Latch1.countDown();

            PrintWriter writer = asyncContext.getResponse().getWriter();
            writer.print('\n');
            writer.flush();

            bug55772Latch2.countDown();
        }
    }


    private static final class LargeHeaderServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        boolean flush = false;

        public LargeHeaderServlet(boolean flush) {
            this.flush = flush;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            String largeValue =
                    CharBuffer.allocate(10000).toString().replace('\0', 'x');
            resp.setHeader("x-Test", largeValue);
            if (flush) {
                resp.flushBuffer();
            }
            resp.setContentType("text/plain");
            resp.getWriter().print("FAIL");
        }

    }

    // flushes with no content-length set
    // should result in chunking on HTTP 1.1
    private static final class NoContentLengthFlushingServlet
            extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/plain");
            resp.getWriter().write("OK");
            resp.flushBuffer();
        }
    }

    // flushes with no content-length set but sets Connection: close header
    // should no result in chunking on HTTP 1.1
    private static final class NoContentLengthConnectionCloseFlushingServlet
            extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/event-stream");
            resp.addHeader("Connection", "close");
            resp.flushBuffer();
            resp.getWriter().write("OK");
            resp.flushBuffer();
        }
    }

    private static final class Client extends SimpleHttpClient {

        public Client(int port) {
            setPort(port);
        }

        @Override
        public boolean isResponseBodyOK() {
            return getResponseBody().contains("test - data");
        }
    }


    /*
     * Partially read chunked input is not swallowed when it is read during
     * async processing.
     */
    @Test
    public void testBug57621() throws Exception {

        Tomcat tomcat = getTomcatInstance();
        
        // Must have a real docBase - just use temp
        Context root = tomcat.addContext("", System.getProperty("java.io.tmpdir"));
        
        Wrapper w = Tomcat.addServlet(root, "Bug57621", new Bug57621Servlet());
        w.setAsyncSupported(true);
        root.addServletMapping("/test", "Bug57621");

        tomcat.start();

        Bug57621Client client = new Bug57621Client();
        client.setPort(tomcat.getConnector().getLocalPort());

        client.setUseContentLength(true);

        client.connect();

        client.doRequest();
        assertTrue(client.getResponseLine(), client.isResponse200());
        assertTrue(client.isResponseBodyOK());

        // Do the request again to ensure that the remaining body was swallowed
        client.resetResponse();
        client.processRequest();
        assertTrue(client.isResponse200());
        assertTrue(client.isResponseBodyOK());

        client.disconnect();
    }


    private static class Bug57621Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doPut(HttpServletRequest req, final HttpServletResponse resp)
                throws ServletException, IOException {
            final AsyncContext ac = req.startAsync();
            ac.start(new Runnable() {
                @Override
                public void run() {
                    resp.setContentType("text/plain");
                    resp.setCharacterEncoding("UTF-8");
                    try {
                        resp.getWriter().print("OK");
                    } catch (IOException e) {
                        // Should never happen. Test will fail if it does.
                    }
                    ac.complete();
                }
            });
        }
    }


    private class Bug57621Client extends SimpleHttpClient {

        private Exception doRequest() {
            try {
                String[] request = new String[2];
                request[0] =
                    "PUT http://localhost:8080/test HTTP/1.1" + CRLF +
                    "Transfer-encoding: chunked" + CRLF +
                    CRLF +
                    "2" + CRLF +
                    "OK";

                request[1] =
                    CRLF +
                    "0" + CRLF +
                    CRLF;

                setRequest(request);
                processRequest(); // blocks until response has been read
            } catch (Exception e) {
                return e;
            }
            return null;
        }

        @Override
        public boolean isResponseBodyOK() {
            if (getResponseBody() == null) {
                return false;
            }
            if (!getResponseBody().contains("OK")) {
                return false;
            }
            return true;
        }
    }


    private static class Non2xxResponseClient extends SimpleHttpClient {
        private static final String HEADER_EXPECT = "Expect: 100-continue";
        private static final String HEADER_CONNECTION = "Connection: close";
        private boolean useExpectation;

        Non2xxResponseClient(boolean useExpectation) {
            this.useExpectation = useExpectation;
        }

        void doResourceRequest(String resourceUri, String requestBody)
                throws Exception {
            StringBuilder requestHead = new StringBuilder();
            requestHead.append(resourceUri).append(CRLF);

            if (useExpectation) {
                requestHead.append(HEADER_EXPECT).append(CRLF);
            }

            requestHead.append(CRLF);
            requestHead.append(requestBody).append(CRLF);

            String request[] = new String[2];
            request[0] = requestHead.toString();
            request[1] = null;
            doRequest(request);
        }

        private void doRequest(String request[]) throws Exception {
            setRequest(request);
            connect();
            processRequest(false);
            disconnect();
        }

        @Override
        public boolean isResponseBodyOK() {
            return true;
        }

        boolean checkConnectionHeader() {
            List<String> responseHeaders = getResponseHeaders();
            boolean found = false;
            for (String header : responseHeaders) {
                if (HEADER_CONNECTION.equals(header)) {
                    found = true;
                    break;
                }
            }
            if (useExpectation) {
                if (found) {
                    return true;
                }
            } else if (!found) {
                return true;
            }
            return false;
        }

    }


    @Test
    public void testBug59310() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "Bug59310", new Bug59310Servlet());
        ctx.addServletMapping("/test", "Bug59310");

        tomcat.start();

        ByteChunk responseBody = new ByteChunk();
        Map<String,List<String>> responseHeaders = new HashMap<String,List<String>>();

        int rc = headUrl("http://localhost:" + getPort() + "/test", responseBody,
                responseHeaders);

        assertEquals(HttpServletResponse.SC_OK, rc);
        assertEquals(0, responseBody.getLength());
        assertFalse(responseHeaders.containsKey("Content-Length"));
    }


    private class Bug59310Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            // TODO Auto-generated method stub
            super.doGet(req, resp);
        }

        @Override
        protected void doHead(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            //resp.setContentLengthLong(-1);
            //resp.flushBuffer();
        }
    }


    /*
     * Tests what happens if a request is completed during a dispatch but the
     * request body has not been fully read.
     */
    @Test
    public void testRequestBodySwallowing() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        SempahoreServlet servlet = new SempahoreServlet();
        Wrapper w = Tomcat.addServlet(ctx, "Test", servlet);
        w.setAsyncSupported(true);
        ctx.addServletMapping("/test", "Test");

        tomcat.start();

        // Hand-craft the client so we have complete control over the timing
        SocketAddress addr = new InetSocketAddress("localhost", getPort());
        Socket socket = new Socket();
        socket.setSoTimeout(300000);
        socket.connect(addr,300000);
        OutputStream os = socket.getOutputStream();
        Writer writer = new OutputStreamWriter(os, "ISO-8859-1");
        InputStream is = socket.getInputStream();
        Reader r = new InputStreamReader(is, "ISO-8859-1");
        BufferedReader reader = new BufferedReader(r);

        // Write the headers
        writer.write("POST /test HTTP/1.1\r\n");
        writer.write("Host: localhost:8080\r\n");
        writer.write("Transfer-Encoding: chunked\r\n");
        writer.write("\r\n");
        writer.flush();

        validateResponse(reader);

        // Write the request body
        writer.write("2\r\n");
        writer.write("AB\r\n");
        writer.write("0\r\n");
        writer.write("\r\n");
        writer.flush();

        // Write the 2nd request
        writer.write("POST /test HTTP/1.1\r\n");
        writer.write("Host: localhost:8080\r\n");
        writer.write("Transfer-Encoding: chunked\r\n");
        writer.write("\r\n");
        writer.flush();

        // Read the 2nd response
        validateResponse(reader);

        // Write the 2nd request body
        writer.write("2\r\n");
        writer.write("AB\r\n");
        writer.write("0\r\n");
        writer.write("\r\n");
        writer.flush();

        // Done
        socket.close();
    }


    private void validateResponse(BufferedReader reader) throws IOException {
        // First line has the response code and should always be 200
        String line = reader.readLine();
        Assert.assertEquals("HTTP/1.1 200 OK", line);
        while (!"OK".equals(line)) {
            line = reader.readLine();
        }
    }


    private static class SempahoreServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            if (DispatcherType.ASYNC.equals(req.getDispatcherType())) {
                resp.setContentType("text/plain");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write("OK\n");
            } else {
                req.startAsync().dispatch();
            }
        }
    }

    @Test
    public void testBug61086() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Bug61086Servlet servlet = new Bug61086Servlet();
        Tomcat.addServlet(ctx, "Test", servlet);
        ctx.addServletMapping("/test", "Test");

        tomcat.start();

        ByteChunk responseBody = new ByteChunk();
        Map<String,List<String>> responseHeaders = new HashMap<String, List<String>>();
        int rc = getUrl("http://localhost:" + getPort() + "/test", responseBody, responseHeaders);

        assertEquals(HttpServletResponse.SC_RESET_CONTENT, rc);
        assertNotNull(responseHeaders.get("Content-Length"));
        assertTrue("0".equals(responseHeaders.get("Content-Length").get(0)));
        assertTrue(responseBody.getLength() == 0);
    }

    private static final class Bug61086Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setStatus(205);
        }
    }
}
