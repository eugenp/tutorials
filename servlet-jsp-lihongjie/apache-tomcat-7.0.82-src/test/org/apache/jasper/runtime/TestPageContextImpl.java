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
package org.apache.jasper.runtime;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.jasper.Constants;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestPageContextImpl extends TomcatBaseTest {

    @Test
    public void testDoForward() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = new ByteChunk();

        int rc = getUrl("http://localhost:" + getPort() +
                "/test/bug5nnnn/bug53545.jsp", res, null);

        Assert.assertEquals(HttpServletResponse.SC_OK, rc);

        String body = res.toString();
        Assert.assertTrue(body.contains("OK"));
        Assert.assertFalse(body.contains("FAIL"));
    }

    @Test
    public void testDefaultBufferSize() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        Context ctx = tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        // Add the Servlet
        Tomcat.addServlet(ctx, "bug56010", new Bug56010());
        ctx.addServletMapping("/bug56010", "bug56010");

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + "/test/bug56010");

        String result = res.toString();
        Assert.assertTrue(result.contains("OK"));
    }

    @Test
    public void testIncludeThrowsIOException() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = new ByteChunk();

        int rc = getUrl("http://localhost:" + getPort() + "/test/jsp/pageContext1.jsp", res, null);

        Assert.assertEquals(HttpServletResponse.SC_OK, rc);

        String body = res.toString();
        Assert.assertTrue(body.contains("OK"));
        Assert.assertFalse(body.contains("FAILED"));

        res = new ByteChunk();

        rc = getUrl("http://localhost:" + getPort() + "/test/jsp/pageContext1.jsp?flush=true", res,
                null);

        Assert.assertEquals(HttpServletResponse.SC_OK, rc);

        body = res.toString();
        Assert.assertTrue(body.contains("Flush"));
        Assert.assertTrue(body.contains("OK"));
        Assert.assertFalse(body.contains("FAILED"));
    }

    public static class Bug56010 extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(
                    this, req, resp, null, false, JspWriter.DEFAULT_BUFFER, true);
            JspWriter out = pageContext.getOut();
            if (Constants.DEFAULT_BUFFER_SIZE == out.getBufferSize()) {
                resp.getWriter().println("OK");
            } else {
                resp.getWriter().println("FAIL");
            }
        }

    }
}
