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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Response;
import org.apache.catalina.deploy.ErrorPage;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestStandardHostValve extends TomcatBaseTest {

    @Test
    public void testErrorPageHandling() throws Exception {
        // Set up a container
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        // Add the error page
        Tomcat.addServlet(ctx, "error", new ErrorServlet());
        ctx.addServletMapping("/error", "error");

        // Add the error handling page
        Tomcat.addServlet(ctx, "report", new ReportServlet());
        ctx.addServletMapping("/report/*", "report");

        // And the handling for 500 responses
        ErrorPage errorPage500 = new ErrorPage();
        errorPage500.setErrorCode(Response.SC_INTERNAL_SERVER_ERROR);
        errorPage500.setLocation("/report/500");
        ctx.addErrorPage(errorPage500);

        // And the default error handling
        ErrorPage errorPageDefault = new ErrorPage();
        errorPageDefault.setLocation("/report/default");
        ctx.addErrorPage(errorPageDefault);

        tomcat.start();

        doTestErrorPageHandling(500, "/500");
        doTestErrorPageHandling(501, "/default");
    }


    @Test(expected=IllegalArgumentException.class)
    public void testInvalidErrorPage() throws Exception {
        // Set up a container
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        // Add a broken error page configuration
        ErrorPage errorPage500 = new ErrorPage();
        errorPage500.setErrorCode("java.lang.Exception");
        errorPage500.setLocation("/report/500");
        ctx.addErrorPage(errorPage500);
    }


    @Test
    public void testSRLAfterError() throws Exception {
        // Set up a container
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        File docBase = new File(System.getProperty("java.io.tmpdir"));
        Context ctx = tomcat.addContext("", docBase.getAbsolutePath());

        // Add the error page
        Tomcat.addServlet(ctx, "error", new ErrorServlet());
        ctx.addServletMapping("/error", "error");

        final List<String> result = new ArrayList<String>();

        // Add the request listener
        ServletRequestListener servletRequestListener = new ServletRequestListener() {

            @Override
            public void requestDestroyed(ServletRequestEvent sre) {
                result.add("Visit requestDestroyed");
            }

            @Override
            public void requestInitialized(ServletRequestEvent sre) {
                result.add("Visit requestInitialized");
            }

        };
        ((StandardContext) ctx).addApplicationEventListener(servletRequestListener);

        tomcat.start();

        // Request a page that triggers an error
        ByteChunk bc = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort() + "/error?errorCode=400", bc, null);

        Assert.assertEquals(400, rc);
        Assert.assertTrue(result.contains("Visit requestInitialized"));
        Assert.assertTrue(result.contains("Visit requestDestroyed"));
    }


    private void doTestErrorPageHandling(int error, String report)
            throws Exception {

        // Request a page that triggers an error
        ByteChunk bc = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort() +
                "/error?errorCode=" + error, bc, null);

        Assert.assertEquals(error, rc);
        Assert.assertEquals(report, bc.toString());
    }

    private static class ErrorServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            int error = Integer.parseInt(req.getParameter("errorCode"));
            resp.sendError(error);
        }
    }

    private static class ReportServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            String pathInfo = req.getPathInfo();
            resp.setContentType("text/plain");
            resp.getWriter().print(pathInfo);
        }
    }
}
