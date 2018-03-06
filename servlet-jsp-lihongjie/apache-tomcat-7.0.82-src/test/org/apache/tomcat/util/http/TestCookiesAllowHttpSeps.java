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

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.startup.SimpleHttpClient;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;

public class TestCookiesAllowHttpSeps extends TomcatBaseTest {

    private static final String COOKIE_WITH_SEPS = "name=val(ue";

    @Test
    public void testWithHttpSep() throws Exception {
        System.setProperty(
                "org.apache.tomcat.util.http.ServerCookie.ALLOW_HTTP_SEPARATORS_IN_V0",
                "true");

        TestCookieHttpSepClient client = new TestCookieHttpSepClient();
        client.doRequest();
    }

    private class TestCookieHttpSepClient extends SimpleHttpClient {


        private void doRequest() throws Exception {
            Tomcat tomcat = getTomcatInstance();
            Context root = tomcat.addContext("", TEMP_DIR);
            Tomcat.addServlet(root, "Simple", new SimpleServlet());
            root.addServletMapping("/test", "Simple");

            tomcat.start();
            // Open connection
            setPort(tomcat.getConnector().getLocalPort());
            connect();

            String[] request = new String[1];
            request[0] =
                "GET /test HTTP/1.0" + CRLF +
                "Cookie: " + COOKIE_WITH_SEPS + CRLF + CRLF;
            setRequest(request);
            processRequest(true); // blocks until response has been read
            String response = getResponseBody();

            // Close the connection
            disconnect();
            reset();
            tomcat.stop();
            assertEquals(COOKIE_WITH_SEPS, response);
        }

        @Override
        public boolean isResponseBodyOK() {
            return true;
        }

    }


    private static class SimpleServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
            Cookie cookies[] = req.getCookies();
            for (Cookie cookie : cookies) {
                resp.getWriter().write(cookie.getName() + "=" +
                        cookie.getValue() + "\n");
            }
            resp.flushBuffer();
        }

    }

}
