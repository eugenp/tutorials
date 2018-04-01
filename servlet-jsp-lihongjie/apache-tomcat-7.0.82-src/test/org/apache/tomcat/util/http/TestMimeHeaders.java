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
package org.apache.tomcat.util.http;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.SocketException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.startup.SimpleHttpClient;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.catalina.valves.TesterAccessLogValve;

public class TestMimeHeaders extends TomcatBaseTest {

    private HeaderCountLogValve alv;

    private void setupHeadersTest(Tomcat tomcat) {
        Context ctx = tomcat.addContext("", getTemporaryDirectory()
                .getAbsolutePath());
        Tomcat.addServlet(ctx, "servlet", new HttpServlet() {
            private static final long serialVersionUID = 1L;

            @Override
            public void service(ServletRequest req, ServletResponse res)
                    throws ServletException, IOException {
                res.setContentType("text/plain; charset=ISO-8859-1");
                res.getWriter().write("OK");
            }
        });
        ctx.addServletMapping("/", "servlet");

        alv = new HeaderCountLogValve();
        tomcat.getHost().getPipeline().addValve(alv);
    }

    private void runHeadersTest(final boolean successExpected,
            final Tomcat tomcat, final int count,
            final int expectedMaxHeaderCount) throws Exception {
        tomcat.start();

        String header = "A:B" + SimpleHttpClient.CRLF;
        StringBuilder request = new StringBuilder();
        request.append("GET / HTTP/1.0" + SimpleHttpClient.CRLF);
        for (int i = 0; i < count; i++) {
            request.append(header);
        }
        request.append(SimpleHttpClient.CRLF);

        Client client = new Client(tomcat);
        client.setRequest(new String[] { request.toString() });
        try {
            client.connect();
            client.processRequest();
            client.disconnect();
        } catch (SocketException ex) {
            // Connection was closed by Tomcat
            if (successExpected) {
                // unexpected
                log.error(ex.getMessage(), ex);
            } else {
                log.warn(ex.getMessage(), ex);
            }
        }
        if (successExpected) {
            alv.validateAccessLog(1, 200, 0, 3000);
            // Response 200
            assertTrue("Response line is: " + client.getResponseLine(),
                    client.getResponseLine() != null && client.isResponse200());
            assertEquals("OK", client.getResponseBody());
        } else {
            alv.validateAccessLog(1, 400, 0, 0);
            // Connection aborted or response 400
            assertTrue("Response line is: " + client.getResponseLine(),
                    client.getResponseLine() == null || client.isResponse400());
        }
        int maxHeaderCount = tomcat.getConnector().getMaxHeaderCount();
        assertEquals(expectedMaxHeaderCount, maxHeaderCount);
        if (maxHeaderCount > 0) {
            assertEquals(maxHeaderCount, alv.arraySize);
        } else if (maxHeaderCount < 0) {
            int maxHttpHeaderSize = ((Integer) tomcat.getConnector()
                    .getAttribute("maxHttpHeaderSize")).intValue();
            int headerCount = Math.min(count,
                    maxHttpHeaderSize / header.length() + 1);
            int arraySize = 1;
            while (arraySize < headerCount) {
                arraySize <<= 1;
            }
            assertEquals(arraySize, alv.arraySize);
        }
    }

    @Test
    public void testHeaderLimits1() throws Exception {
        // Bumping into maxHttpHeaderSize
        Tomcat tomcat = getTomcatInstance();
        setupHeadersTest(tomcat);
        tomcat.getConnector().setMaxHeaderCount(-1);
        runHeadersTest(false, tomcat, 8 * 1024, -1);
    }

    @Test
    public void testHeaderLimits2() throws Exception {
        // Can process 100 headers
        Tomcat tomcat = getTomcatInstance();
        setupHeadersTest(tomcat);
        runHeadersTest(true, tomcat, 100, 100);
    }

    @Test
    public void testHeaderLimits3() throws Exception {
        // Cannot process 101 header
        Tomcat tomcat = getTomcatInstance();
        setupHeadersTest(tomcat);
        runHeadersTest(false, tomcat, 101, 100);
    }

    @Test
    public void testHeaderLimits4() throws Exception {
        // Can change maxHeaderCount
        Tomcat tomcat = getTomcatInstance();
        setupHeadersTest(tomcat);
        tomcat.getConnector().setMaxHeaderCount(-1);
        runHeadersTest(true, tomcat, 300, -1);
    }

    private static final class HeaderCountLogValve extends TesterAccessLogValve {
        public volatile int arraySize = -1;

        @Override
        public void log(Request request, Response response, long time) {
            super.log(request, response, time);
            try {
                MimeHeaders mh = request.getCoyoteRequest().getMimeHeaders();
                Field headersArrayField = MimeHeaders.class
                        .getDeclaredField("headers");
                headersArrayField.setAccessible(true);
                arraySize = ((Object[]) headersArrayField.get(mh)).length;
            } catch (Exception ex) {
                assertNull(ex.getMessage(), ex);
            }
        }
    }

    private static final class Client extends SimpleHttpClient {
        public Client(Tomcat tomcat) {
            setPort(tomcat.getConnector().getLocalPort());
        }

        @Override
        public boolean isResponseBodyOK() {
            return true;
        }
    }
}
