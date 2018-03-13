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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Response;
import org.apache.catalina.deploy.ErrorPage;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestStandardContextValve extends TomcatBaseTest {

    @Test
    public void testBug51653a() throws Exception {
        // Set up a container
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        // Traces order of events across multiple components
        StringBuilder trace = new StringBuilder();

        //Add the error page
        Tomcat.addServlet(ctx, "errorPage", new Bug51653ErrorPage(trace));
        ctx.addServletMapping("/error", "errorPage");
        // And the handling for 404 responses
        ErrorPage errorPage = new ErrorPage();
        errorPage.setErrorCode(Response.SC_NOT_FOUND);
        errorPage.setLocation("/error");
        ctx.addErrorPage(errorPage);

        // Add the request listener
        Bug51653RequestListener reqListener =
            new Bug51653RequestListener(trace);
        ((StandardContext) ctx).addApplicationEventListener(reqListener);

        tomcat.start();

        // Request a page that does not exist
        int rc = getUrl("http://localhost:" + getPort() + "/invalid",
                new ByteChunk(), null);

        // Need to allow time (but not too long in case the test fails) for
        // ServletRequestListener to complete
        int i = 20;
        while (i > 0) {
            if (trace.toString().endsWith("Destroy")) {
                break;
            }
            Thread.sleep(250);
            i--;
        }

        assertEquals(Response.SC_NOT_FOUND, rc);
        assertEquals("InitErrorDestroy", trace.toString());
    }

    @Test
    public void testBug51653b() throws Exception {
        // Set up a container
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        // Traces order of events across multiple components
        StringBuilder trace = new StringBuilder();

        // Add the page that generates the error
        Tomcat.addServlet(ctx, "test", new Bug51653ErrorTrigger());
        ctx.addServletMapping("/test", "test");

        // Add the error page
        Tomcat.addServlet(ctx, "errorPage", new Bug51653ErrorPage(trace));
        ctx.addServletMapping("/error", "errorPage");
        // And the handling for 404 responses
        ErrorPage errorPage = new ErrorPage();
        errorPage.setErrorCode(Response.SC_NOT_FOUND);
        errorPage.setLocation("/error");
        ctx.addErrorPage(errorPage);

        // Add the request listener
        Bug51653RequestListener reqListener =
            new Bug51653RequestListener(trace);
        ((StandardContext) ctx).addApplicationEventListener(reqListener);

        tomcat.start();

        // Request a page that does not exist
        int rc = getUrl("http://localhost:" + getPort() + "/test",
                new ByteChunk(), null);

        // Need to allow time (but not too long in case the test fails) for
        // ServletRequestListener to complete
        int i = 20;
        while (i > 0) {
            if (trace.toString().endsWith("Destroy")) {
                break;
            }
            Thread.sleep(250);
            i--;
        }

        assertEquals(Response.SC_NOT_FOUND, rc);
        assertEquals("InitErrorDestroy", trace.toString());
    }

    private static class Bug51653ErrorTrigger extends HttpServlet {
        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.sendError(Response.SC_NOT_FOUND);
        }
    }

    private static class Bug51653ErrorPage extends HttpServlet {
        private static final long serialVersionUID = 1L;

        private StringBuilder sb;

        public Bug51653ErrorPage(StringBuilder sb) {
            this.sb = sb;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            sb.append("Error");

            resp.setContentType("text/plain");
            resp.getWriter().write("Error");
        }
    }

    private static class Bug51653RequestListener
            implements ServletRequestListener {

        private StringBuilder sb;

        public Bug51653RequestListener(StringBuilder sb) {
            this.sb = sb;
        }

        @Override
        public void requestInitialized(ServletRequestEvent sre) {
            sb.append("Init");
        }

        @Override
        public void requestDestroyed(ServletRequestEvent sre) {
            sb.append("Destroy");
        }

    }
}
