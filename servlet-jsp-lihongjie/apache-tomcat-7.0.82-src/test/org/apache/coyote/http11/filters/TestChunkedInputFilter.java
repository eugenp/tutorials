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

package org.apache.coyote.http11.filters;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.startup.SimpleHttpClient;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;

public class TestChunkedInputFilter extends TomcatBaseTest {

    private static final String LF = "\n";
    private static final int EXT_SIZE_LIMIT = 10;

    @Test
    public void testChunkHeaderCRLF() throws Exception {
        doTestChunkingCRLF(true, true, true, true, true, true);
    }

    @Test
    public void testChunkHeaderLF() throws Exception {
        doTestChunkingCRLF(false, true, true, true, true, false);
    }

    @Test
    public void testChunkCRLF() throws Exception {
        doTestChunkingCRLF(true, true, true, true, true, true);
    }

    @Test
    public void testChunkLF() throws Exception {
        doTestChunkingCRLF(true, false, true, true, true, false);
    }

    @Test
    public void testFirstTrailingHeadersCRLF() throws Exception {
        doTestChunkingCRLF(true, true, true, true, true, true);
    }

    @Test
    public void testFirstTrailingHeadersLF() throws Exception {
        doTestChunkingCRLF(true, true, false, true, true, true);
    }

    @Test
    public void testSecondTrailingHeadersCRLF() throws Exception {
        doTestChunkingCRLF(true, true, true, true, true, true);
    }

    @Test
    public void testSecondTrailingHeadersLF() throws Exception {
        doTestChunkingCRLF(true, true, true, false, true, true);
    }

    @Test
    public void testEndCRLF() throws Exception {
        doTestChunkingCRLF(true, true, true, true, true, true);
    }

    @Test
    public void testEndLF() throws Exception {
        doTestChunkingCRLF(true, true, true, true, false, false);
    }

    private void doTestChunkingCRLF(boolean chunkHeaderUsesCRLF,
            boolean chunkUsesCRLF, boolean firstheaderUsesCRLF,
            boolean secondheaderUsesCRLF, boolean endUsesCRLF,
            boolean expectPass) throws Exception {

        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        // Configure allowed trailer headers
        tomcat.getConnector().setProperty("allowedTrailerHeaders", "X-Trailer1,X-Trailer2");

        EchoHeaderServlet servlet = new EchoHeaderServlet(expectPass);
        Tomcat.addServlet(ctx, "servlet", servlet);
        ctx.addServletMapping("/", "servlet");

        tomcat.start();

        String[] request = new String[]{
            "POST /echo-params.jsp HTTP/1.1" + SimpleHttpClient.CRLF +
            "Host: any" + SimpleHttpClient.CRLF +
            "Transfer-encoding: chunked" + SimpleHttpClient.CRLF +
            "Content-Type: application/x-www-form-urlencoded" +
                    SimpleHttpClient.CRLF +
            "Connection: close" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF +
            "3" + (chunkHeaderUsesCRLF ? SimpleHttpClient.CRLF : LF) +
            "a=0" + (chunkUsesCRLF ? SimpleHttpClient.CRLF : LF) +
            "4" + SimpleHttpClient.CRLF +
            "&b=1" + SimpleHttpClient.CRLF +
            "0" + SimpleHttpClient.CRLF +
            "x-trailer1: Test", "Value1" +
            (firstheaderUsesCRLF ? SimpleHttpClient.CRLF : LF) +
            "x-trailer2: TestValue2" +
            (secondheaderUsesCRLF ? SimpleHttpClient.CRLF : LF) +
            (endUsesCRLF ? SimpleHttpClient.CRLF : LF) };

        TrailerClient client =
                new TrailerClient(tomcat.getConnector().getLocalPort());
        client.setRequest(request);

        client.connect();
        Exception processException = null;
        try {
            client.processRequest();
        } catch (Exception e) {
            // Socket was probably closed before client had a chance to read
            // response
            processException = e;
        }

        if (expectPass) {
            assertTrue(client.isResponse200());
            assertEquals("nullnull7TestValue1TestValue2",
                    client.getResponseBody());
            assertNull(processException);
            assertFalse(servlet.getExceptionDuringRead());
        } else {
            if (processException == null) {
                assertTrue(client.getResponseLine(), client.isResponse500());
            } else {
                // Use fall-back for checking the error occurred
                assertTrue(servlet.getExceptionDuringRead());
            }
        }
    }

    @Test
    public void testTrailingHeadersSizeLimit() throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "servlet", new EchoHeaderServlet(false));
        ctx.addServletMapping("/", "servlet");

        // Limit the size of the trailing header
        tomcat.getConnector().setProperty("maxTrailerSize", "10");
        tomcat.start();

        String[] request = new String[]{
            "POST /echo-params.jsp HTTP/1.1" + SimpleHttpClient.CRLF +
            "Host: any" + SimpleHttpClient.CRLF +
            "Transfer-encoding: chunked" + SimpleHttpClient.CRLF +
            "Content-Type: application/x-www-form-urlencoded" +
                    SimpleHttpClient.CRLF +
            "Connection: close" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF +
            "3" + SimpleHttpClient.CRLF +
            "a=0" + SimpleHttpClient.CRLF +
            "4" + SimpleHttpClient.CRLF +
            "&b=1" + SimpleHttpClient.CRLF +
            "0" + SimpleHttpClient.CRLF +
            "x-trailer: Test" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF };

        TrailerClient client =
                new TrailerClient(tomcat.getConnector().getLocalPort());
        client.setRequest(request);

        client.connect();
        client.processRequest();
        // Expected to fail because the trailers are longer
        // than the set limit of 10 bytes
        assertTrue(client.isResponse500());
    }


    @Test
    public void testExtensionSizeLimitOneBelow() throws Exception {
        doTestExtensionSizeLimit(EXT_SIZE_LIMIT - 1, true);
    }


    @Test
    public void testExtensionSizeLimitExact() throws Exception {
        doTestExtensionSizeLimit(EXT_SIZE_LIMIT, true);
    }


    @Test
    public void testExtensionSizeLimitOneOver() throws Exception {
        doTestExtensionSizeLimit(EXT_SIZE_LIMIT + 1, false);
    }


    private void doTestExtensionSizeLimit(int len, boolean ok) throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        tomcat.getConnector().setProperty(
                "maxExtensionSize", Integer.toString(EXT_SIZE_LIMIT));

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "servlet", new EchoHeaderServlet(ok));
        ctx.addServletMapping("/", "servlet");

        tomcat.start();

        String extName = ";foo=";
        StringBuilder extValue = new StringBuilder(len);
        for (int i = 0; i < (len - extName.length()); i++) {
            extValue.append("x");
        }

        String[] request = new String[]{
            "POST /echo-params.jsp HTTP/1.1" + SimpleHttpClient.CRLF +
            "Host: any" + SimpleHttpClient.CRLF +
            "Transfer-encoding: chunked" + SimpleHttpClient.CRLF +
            "Content-Type: application/x-www-form-urlencoded" +
                    SimpleHttpClient.CRLF +
            "Connection: close" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF +
            "3" + extName + extValue.toString() + SimpleHttpClient.CRLF +
            "a=0" + SimpleHttpClient.CRLF +
            "4" + SimpleHttpClient.CRLF +
            "&b=1" + SimpleHttpClient.CRLF +
            "0" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF };

        TrailerClient client =
                new TrailerClient(tomcat.getConnector().getLocalPort());
        client.setRequest(request);

        client.connect();
        client.processRequest();

        if (ok) {
            assertTrue(client.isResponse200());
        } else {
            assertTrue(client.isResponse500());
        }
    }

    @Test
    public void testNoTrailingHeaders() throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "servlet", new EchoHeaderServlet(true));
        ctx.addServletMapping("/", "servlet");

        tomcat.start();

        String request =
            "POST /echo-params.jsp HTTP/1.1" + SimpleHttpClient.CRLF +
            "Host: any" + SimpleHttpClient.CRLF +
            "Transfer-encoding: chunked" + SimpleHttpClient.CRLF +
            "Content-Type: application/x-www-form-urlencoded" +
                    SimpleHttpClient.CRLF +
            "Connection: close" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF +
            "3" + SimpleHttpClient.CRLF +
            "a=0" + SimpleHttpClient.CRLF +
            "4" + SimpleHttpClient.CRLF +
            "&b=1" + SimpleHttpClient.CRLF +
            "0" + SimpleHttpClient.CRLF +
            SimpleHttpClient.CRLF;

        TrailerClient client =
                new TrailerClient(tomcat.getConnector().getLocalPort());
        client.setRequest(new String[] {request});

        client.connect();
        client.processRequest();
        assertEquals("nullnull7nullnull", client.getResponseBody());
    }

    @Test
    public void testChunkSizeZero() throws Exception {
        doTestChunkSize(true, true, "", 10, 0);
    }

    @Test
    public void testChunkSizeAbsent() throws Exception {
        doTestChunkSize(false, false, SimpleHttpClient.CRLF, 10, 0);
    }

    @Test
    public void testChunkSizeTwentyFive() throws Exception {
        doTestChunkSize(true, true, "19" + SimpleHttpClient.CRLF
                + "Hello World!Hello World!!" + SimpleHttpClient.CRLF, 40, 25);
    }

    @Test
    public void testChunkSizeEightDigit() throws Exception {
        doTestChunkSize(true, true, "0000000C" + SimpleHttpClient.CRLF
                + "Hello World!" + SimpleHttpClient.CRLF, 20, 12);
    }

    @Test
    public void testChunkSizeNineDigit() throws Exception {
        doTestChunkSize(false, false, "00000000C" + SimpleHttpClient.CRLF
                + "Hello World!" + SimpleHttpClient.CRLF, 20, 12);
    }

    @Test
    public void testChunkSizeLong() throws Exception {
        doTestChunkSize(true, false, "7fFFffFF" + SimpleHttpClient.CRLF
                + "Hello World!" + SimpleHttpClient.CRLF, 10, 10);
    }

    @Test
    public void testChunkSizeIntegerMinValue() throws Exception {
        doTestChunkSize(false, false, "80000000" + SimpleHttpClient.CRLF
                + "Hello World!" + SimpleHttpClient.CRLF, 10, 10);
    }

    @Test
    public void testChunkSizeMinusOne() throws Exception {
        doTestChunkSize(false, false, "ffffffff" + SimpleHttpClient.CRLF
                + "Hello World!" + SimpleHttpClient.CRLF, 10, 10);
    }

    /**
     * @param expectPass
     *            If the servlet is expected to process the request
     * @param expectReadWholeBody
     *            If the servlet is expected to fully read the body and reliably
     *            deliver a response
     * @param chunks
     *            Text of chunks
     * @param readLimit
     *            Do not read more than this many bytes
     * @param expectReadCount
     *            Expected count of read bytes
     * @throws Exception
     *             Unexpected
     */
    private void doTestChunkSize(boolean expectPass,
            boolean expectReadWholeBody, String chunks, int readLimit,
            int expectReadCount) throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        BodyReadServlet servlet = new BodyReadServlet(expectPass, readLimit);
        Tomcat.addServlet(ctx, "servlet", servlet);
        ctx.addServletMapping("/", "servlet");

        tomcat.start();

        String request = "POST /echo-params.jsp HTTP/1.1"
                + SimpleHttpClient.CRLF + "Host: any" + SimpleHttpClient.CRLF
                + "Transfer-encoding: chunked" + SimpleHttpClient.CRLF
                + "Content-Type: text/plain" + SimpleHttpClient.CRLF;
        if (expectPass) {
            request += "Connection: close" + SimpleHttpClient.CRLF;
        }
        request += SimpleHttpClient.CRLF + chunks + "0" + SimpleHttpClient.CRLF
                + SimpleHttpClient.CRLF;

        TrailerClient client = new TrailerClient(tomcat.getConnector()
                .getLocalPort());
        client.setRequest(new String[] { request });

        Exception processException = null;
        client.connect();
        try {
            client.processRequest();
        } catch (Exception e) {
            // Socket was probably closed before client had a chance to read
            // response
            processException = e;
        }
        if (expectPass) {
            if (expectReadWholeBody) {
                assertNull(processException);
            }
            if (processException == null) {
                assertTrue(client.getResponseLine(), client.isResponse200());
                assertEquals(String.valueOf(expectReadCount),
                        client.getResponseBody());
            }
            assertEquals(expectReadCount, servlet.getCountRead());
        } else {
            if (processException == null) {
                assertTrue(client.getResponseLine(), client.isResponse500());
            }
            assertEquals(0, servlet.getCountRead());
            assertTrue(servlet.getExceptionDuringRead());
        }
    }

    private static class EchoHeaderServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        private boolean exceptionDuringRead = false;

        private final boolean expectPass;

        public EchoHeaderServlet(boolean expectPass) {
            this.expectPass = expectPass;
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            PrintWriter pw = resp.getWriter();
            // Headers not visible yet, body not processed
            dumpHeader("x-trailer1", req, pw);
            dumpHeader("x-trailer2", req, pw);

            // Read the body - quick and dirty
            InputStream is = req.getInputStream();
            int count = 0;
            try {
                while (is.read() > -1) {
                    count++;
                }
            } catch (IOException ioe) {
                exceptionDuringRead = true;
                if (!expectPass) { // as expected
                    log(ioe.toString());
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }
                throw ioe;
            }

            pw.write(Integer.valueOf(count).toString());

            // Headers should be visible now
            dumpHeader("x-trailer1", req, pw);
            dumpHeader("x-trailer2", req, pw);
        }

        public boolean getExceptionDuringRead() {
            return exceptionDuringRead;
        }

        private void dumpHeader(String headerName, HttpServletRequest req,
                PrintWriter pw) {
            String value = req.getHeader(headerName);
            if (value == null) {
                value = "null";
            }
            pw.write(value);
        }
    }

    private static class BodyReadServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        private boolean exceptionDuringRead = false;
        private int countRead = 0;
        private final boolean expectPass;
        private final int readLimit;

        public BodyReadServlet(boolean expectPass, int readLimit) {
            this.expectPass = expectPass;
            this.readLimit = readLimit;
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            PrintWriter pw = resp.getWriter();

            // Read the body - quick and dirty
            InputStream is = req.getInputStream();
            try {
                while (is.read() > -1 && countRead < readLimit) {
                    countRead++;
                }
            } catch (IOException ioe) {
                exceptionDuringRead = true;
                if (!expectPass) { // as expected
                    log(ioe.toString());
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }
                throw ioe;
            }

            pw.write(Integer.valueOf(countRead).toString());
        }

        public boolean getExceptionDuringRead() {
            return exceptionDuringRead;
        }

        public int getCountRead() {
            return countRead;
        }
    }

    private static class TrailerClient extends SimpleHttpClient {

        public TrailerClient(int port) {
            setPort(port);
        }

        @Override
        public boolean isResponseBodyOK() {
            return getResponseBody().contains("TestTestTest");
        }
    }
}
