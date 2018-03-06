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
package org.apache.catalina.valves;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestErrorReportValve extends TomcatBaseTest {

    @Test
    public void testBug53071() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "errorServlet", new ErrorServlet());
        ctx.addServletMapping("/", "errorServlet");

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort());

        Assert.assertTrue(res.toString().contains("<p><b>message</b> <u>" +
                ErrorServlet.ERROR_TEXT + "</u></p>"));
    }


    private static final class ErrorServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        private static final String ERROR_TEXT = "The wheels fell off.";
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            req.setAttribute(RequestDispatcher.ERROR_EXCEPTION,
                    new Throwable(ERROR_TEXT));
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    @Test
    public void testBug54220DoNotSetNotFound() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "bug54220", new Bug54220Servlet(false));
        ctx.addServletMapping("/", "bug54220");

        tomcat.start();

        ByteChunk res = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort(), res, null);

        Assert.assertNull(res.toString());
        Assert.assertEquals(HttpServletResponse.SC_OK, rc);
    }


    @Test
    public void testBug54220SetNotFound() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "bug54220", new Bug54220Servlet(true));
        ctx.addServletMapping("/", "bug54220");

        tomcat.start();

        ByteChunk res = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort(), res, null);

        Assert.assertNull(res.toString());
        Assert.assertEquals(HttpServletResponse.SC_NOT_FOUND, rc);
    }


    private static final class Bug54220Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private boolean setNotFound;

        private Bug54220Servlet(boolean setNotFound) {
            this.setNotFound = setNotFound;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            if (setNotFound) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }


    /**
     * Custom error/status codes should not result in a blank response.
     */
    @Test
    public void testBug54536() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "bug54536", new Bug54536Servlet());
        ctx.addServletMapping("/", "bug54536");

        tomcat.start();

        ByteChunk res = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort(), res, null);

        Assert.assertEquals(Bug54536Servlet.ERROR_STATUS, rc);
        String body = res.toString();
        Assert.assertNotNull(body);
        Assert.assertTrue(body, body.contains(Bug54536Servlet.ERROR_MESSAGE));
    }


    private static final class Bug54536Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        private static final int ERROR_STATUS = 999;
        private static final String ERROR_MESSAGE = "The sky is falling";

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.sendError(ERROR_STATUS, ERROR_MESSAGE);
        }
    }

    @Test
    public void testBug56042() throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Bug56042Servlet bug56042Servlet = new Bug56042Servlet();
        Wrapper wrapper =
            Tomcat.addServlet(ctx, "bug56042Servlet", bug56042Servlet);
        wrapper.setAsyncSupported(true);
        ctx.addServletMapping("/bug56042Servlet", "bug56042Servlet");

        tomcat.start();

        StringBuilder url = new StringBuilder(48);
        url.append("http://localhost:");
        url.append(getPort());
        url.append("/bug56042Servlet");

        ByteChunk res = new ByteChunk();
        int rc = getUrl(url.toString(), res, null);

        Assert.assertEquals(HttpServletResponse.SC_BAD_REQUEST, rc);
    }

    private static class Bug56042Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            // Only set the status on the first call (the dispatch will trigger
            // another call to this Servlet)
            if (resp.getStatus() != HttpServletResponse.SC_BAD_REQUEST) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                AsyncContext ac = req.startAsync();
                ac.dispatch();
            }
        }
    }
}
