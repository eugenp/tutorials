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
package org.apache.catalina.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.SimpleHttpClient;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class TestSwallowAbortedUploads extends TomcatBaseTest {

    private static Log log = LogFactory.getLog(TestSwallowAbortedUploads.class);

    /**
     * Test whether size limited uploads correctly handle connection draining.
     */
    public Exception doAbortedUploadTest(AbortedUploadClient client, boolean limited,
                            boolean swallow) {
        Exception ex = client.doRequest(limited, swallow);
        if (log.isDebugEnabled()) {
            log.debug("Response line: " + client.getResponseLine());
            log.debug("Response headers: " + client.getResponseHeaders());
            log.debug("Response body: " + client.getResponseBody());
            if (ex != null) {
                log.debug("Exception in client: ", ex);
            }

        }
        return ex;
    }

    /**
     * Test whether aborted POST correctly handle connection draining.
     */
    public Exception doAbortedPOSTTest(AbortedPOSTClient client, int status,
                            boolean swallow) {
        Exception ex = client.doRequest(status, swallow);
        if (log.isDebugEnabled()) {
            log.debug("Response line: " + client.getResponseLine());
            log.debug("Response headers: " + client.getResponseHeaders());
            log.debug("Response body: " + client.getResponseBody());
            if (ex != null) {
                log.info("Exception in client: ", ex);
            }

        }
        return ex;
    }

    @Test
    public void testAbortedUploadUnlimitedSwallow() {
        log.info("Unlimited, swallow enabled");
        AbortedUploadClient client = new AbortedUploadClient();
        Exception ex = doAbortedUploadTest(client, false, true);
        assertNull("Unlimited upload with swallow enabled generates client exception",
                   ex);
        assertTrue("Unlimited upload with swallow enabled returns error status code",
                   client.isResponse200());
        client.reset();
    }

    @Test
    public void testAbortedUploadUnlimitedNoSwallow() {
        log.info("Unlimited, swallow disabled");
        AbortedUploadClient client = new AbortedUploadClient();
        Exception ex = doAbortedUploadTest(client, false, false);
        assertNull("Unlimited upload with swallow disabled generates client exception",
                   ex);
        assertTrue("Unlimited upload with swallow disabled returns error status code",
                   client.isResponse200());
        client.reset();
    }

    @Test
    public void testAbortedUploadLimitedSwallow() {
        log.info("Limited, swallow enabled");
        AbortedUploadClient client = new AbortedUploadClient();
        Exception ex = doAbortedUploadTest(client, true, true);
        assertNull("Limited upload with swallow enabled generates client exception",
                   ex);
        assertTrue("Limited upload with swallow enabled returns non-500 status code",
                   client.isResponse500());
        client.reset();
    }

    @Test
    public void testAbortedUploadLimitedNoSwallow() {
        log.info("Limited, swallow disabled");
        AbortedUploadClient client = new AbortedUploadClient();
        Exception ex = doAbortedUploadTest(client, true, false);
        assertTrue("Limited upload with swallow disabled does not generate client exception",
                   ex != null && ex instanceof java.net.SocketException);
        client.reset();
    }

    @Test
    public void testAbortedPOSTOKSwallow() {
        log.info("Aborted (OK), swallow enabled");
        AbortedPOSTClient client = new AbortedPOSTClient();
        Exception ex = doAbortedPOSTTest(client, HttpServletResponse.SC_OK, true);
        assertNull("Unlimited upload with swallow enabled generates client exception",
                   ex);
        assertTrue("Unlimited upload with swallow enabled returns error status code",
                   client.isResponse200());
        client.reset();
    }

    @Test
    public void testAbortedPOSTOKNoSwallow() {
        log.info("Aborted (OK), swallow disabled");
        AbortedPOSTClient client = new AbortedPOSTClient();
        Exception ex = doAbortedPOSTTest(client, HttpServletResponse.SC_OK, false);
        assertNull("Unlimited upload with swallow disabled generates client exception",
                   ex);
        assertTrue("Unlimited upload with swallow disabled returns error status code",
                   client.isResponse200());
        client.reset();
    }

    @Test
    public void testAbortedPOST413Swallow() {
        log.info("Aborted (413), swallow enabled");
        AbortedPOSTClient client = new AbortedPOSTClient();
        Exception ex = doAbortedPOSTTest(client, HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, true);
        assertNull("Limited upload with swallow enabled generates client exception",
                   ex);
        assertTrue("Limited upload with swallow enabled returns error status code",
                   client.isResponse413());
        client.reset();
    }

    @Test
    public void testAbortedPOST413NoSwallow() {
        log.info("Aborted (413), swallow disabled");
        AbortedPOSTClient client = new AbortedPOSTClient();
        Exception ex = doAbortedPOSTTest(client, HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, false);
        assertTrue("Limited upload with swallow disabled does not generate client exception",
                   ex != null && ex instanceof java.net.SocketException);
        client.reset();
    }

    @MultipartConfig
    private static class AbortedUploadServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            PrintWriter out = resp.getWriter();
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            StringBuilder sb = new StringBuilder();
            try {
                Collection<Part> c = req.getParts();
                if (c == null) {
                    log.debug("Count: -1");
                    sb.append("Count: -1\n");
                } else {
                    log.debug("Count: " + c.size());
                    sb.append("Count: " + c.size() + "\n");
                    for (Part p : c) {
                        log.debug("Name: " + p.getName() + ", Size: "
                                + p.getSize());
                        sb.append("Name: " + p.getName() + ", Size: "
                                + p.getSize() + "\n");
                    }
                }
            } catch (IllegalStateException ex) {
                log.debug("IllegalStateException during getParts()");
                sb.append("IllegalStateException during getParts()\n");
                resp.setStatus(500);
            } catch (Throwable ex) {
                log.error("Exception during getParts()", ex);
                sb.append(ex);
                resp.setStatus(500);
            }
            out.print(sb.toString());
            resp.flushBuffer();
        }

    }

    /**
     * Test no connection draining when upload too large
     */
    private class AbortedUploadClient extends SimpleHttpClient {

        private static final String URI = "/uploadAborted";
        private static final String servletName = "uploadAborted";
        private static final int limitSize = 100;
        private static final int hugeSize = 2000000;

        private boolean init;
        private Context context;

        private synchronized void init(boolean limited, boolean swallow)
                throws Exception {
            if (init)
                return;

            Tomcat tomcat = getTomcatInstance();
            context = tomcat.addContext("", TEMP_DIR);
            Wrapper w;
            w = Tomcat.addServlet(context, servletName,
                                  new AbortedUploadServlet());
            // Tomcat.addServlet does not respect annotations, so we have
            // to set our own MultipartConfigElement.
            // Choose upload file size limit.
            if (limited) {
                w.setMultipartConfigElement(new MultipartConfigElement("",
                        limitSize, -1, -1));
            } else {
                w.setMultipartConfigElement(new MultipartConfigElement(""));
            }
            context.addServletMapping(URI, servletName);
            context.setSwallowAbortedUploads(swallow);

            tomcat.start();
            setPort(tomcat.getConnector().getLocalPort());

            init = true;
        }

        private Exception doRequest(boolean limited, boolean swallow) {
            char body[] = new char[hugeSize];
            Arrays.fill(body, 'X');

            try {
                init(limited, swallow);

                // Open connection
                connect();

                // Send specified request body using method
                String[] request;

                String boundary = "--simpleboundary";
                StringBuilder sb = new StringBuilder();

                sb.append("--");
                sb.append(boundary);
                sb.append(CRLF);
                sb.append("Content-Disposition: form-data; name=\"part\"");
                sb.append(CRLF);
                sb.append(CRLF);
                sb.append(body);
                sb.append(CRLF);
                sb.append("--");
                sb.append(boundary);
                sb.append("--");
                sb.append(CRLF);

                // Re-encode the content so that bytes = characters
                String content = new String(sb.toString().getBytes("UTF-8"),
                        "ASCII");

                request = new String[] { "POST http://localhost:" + getPort() + URI + " HTTP/1.1" + CRLF
                        + "Host: localhost" + CRLF
                        + "Connection: close" + CRLF
                        + "Content-Type: multipart/form-data; boundary=" + boundary + CRLF
                        + "Content-Length: " + content.length() + CRLF
                        + CRLF
                        + content + CRLF };

                setRequest(request);
                processRequest(); // blocks until response has been read

                // Close the connection
                disconnect();
            } catch (Exception e) {
                return e;
            }
            return null;
        }

        @Override
        public boolean isResponseBodyOK() {
            return false; // Don't care
        }
    }

    private static class AbortedPOSTServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private int status = 200;

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(status);
            PrintWriter out = resp.getWriter();
            out.print("OK");
            resp.flushBuffer();
        }

    }

    /**
     * Test no connection draining when upload too large
     */
    private class AbortedPOSTClient extends SimpleHttpClient {

        private static final String URI = "/uploadAborted";
        private static final String servletName = "uploadAborted";
        private static final int hugeSize = 2000000;

        private boolean init;
        private Context context;

        private synchronized void init(int status, boolean swallow)
                throws Exception {
            if (init)
                return;

            Tomcat tomcat = getTomcatInstance();
            context = tomcat.addContext("", TEMP_DIR);
            AbortedPOSTServlet servlet = new AbortedPOSTServlet();
            servlet.setStatus(status);
            Tomcat.addServlet(context, servletName,
                              servlet);
            context.addServletMapping(URI, servletName);
            context.setSwallowAbortedUploads(swallow);

            tomcat.start();

            setPort(tomcat.getConnector().getLocalPort());

            init = true;
        }

        private Exception doRequest(int status, boolean swallow) {
            char body[] = new char[hugeSize];
            Arrays.fill(body, 'X');

            try {
                init(status, swallow);

                // Open connection
                connect();

                // Send specified request body using method
                String[] request;

                String content = new String(body);

                request = new String[] { "POST http://localhost:" + getPort() + URI + " HTTP/1.1" + CRLF
                        + "Host: localhost" + CRLF
                        + "Connection: close" + CRLF
                        + "Content-Length: " + content.length() + CRLF
                        + CRLF
                        + content + CRLF };

                setRequest(request);
                processRequest(); // blocks until response has been read

                // Close the connection
                disconnect();
            } catch (Exception e) {
                return e;
            }
            return null;
        }

        @Override
        public boolean isResponseBodyOK() {
            return false; // Don't care
        }
    }


    @Test
    public void testChunkedPUTLimit() throws Exception {
        doTestChunkedPUT(true);
    }


    @Test
    public void testChunkedPUTNoLimit() throws Exception {
        doTestChunkedPUT(false);
    }


    public void doTestChunkedPUT(boolean limit) throws Exception {

        Tomcat tomcat = getTomcatInstance();
        tomcat.addContext("", TEMP_DIR);
        // No need for target to exist.

        if (!limit) {
            tomcat.getConnector().setAttribute("maxSwallowSize", "-1");
        }

        tomcat.start();

        Exception writeEx = null;
        Exception readEx = null;
        String responseLine = null;
        Socket conn = null;
        
        try {
            conn = new Socket("localhost", getPort());
            Writer writer = new OutputStreamWriter(
                    conn.getOutputStream(), "US-ASCII");
            writer.write("PUT /does-not-exist HTTP/1.1\r\n");
            writer.write("Host: any\r\n");
            writer.write("Transfer-encoding: chunked\r\n");
            writer.write("\r\n");

            // Smarter than the typical client. Attempts to read the response
            // even if the request is not fully written.
            try {
                // Write (or try to write) 16MB
                for (int i = 0; i < 1024 * 1024; i++) {
                    writer.write("10\r\n");
                    writer.write("0123456789ABCDEF\r\n");
                }
            } catch (Exception e) {
                writeEx = e;
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "US-ASCII"));

                responseLine = reader.readLine();
            } catch (IOException e) {
                readEx = e;
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        if (limit) {
            Assert.assertNotNull(writeEx);
        } else {
            Assert.assertNull(writeEx);
            Assert.assertNull(readEx);
            Assert.assertNotNull(responseLine);
            Assert.assertTrue(responseLine.contains("404"));
        }
    }
}
