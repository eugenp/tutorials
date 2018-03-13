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
package javax.servlet.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestHttpServlet extends TomcatBaseTest {

    @Test
    public void testBug53454() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        StandardContext ctx = (StandardContext)
            tomcat.addContext("", null);

        // Map the test Servlet
        LargeBodyServlet largeBodyServlet = new LargeBodyServlet();
        Tomcat.addServlet(ctx, "largeBodyServlet", largeBodyServlet);
        ctx.addServletMapping("/", "largeBodyServlet");

        tomcat.start();

        Map<String,List<String>> resHeaders=
                new HashMap<String, List<String>>();
        int rc = headUrl("http://localhost:" + getPort() + "/", new ByteChunk(),
               resHeaders);

        Assert.assertEquals(HttpServletResponse.SC_OK, rc);
        Assert.assertEquals(LargeBodyServlet.RESPONSE_LENGTH,
                resHeaders.get("Content-Length").get(0));
    }


    private static class LargeBodyServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        private static final String RESPONSE_LENGTH = "12345678901";

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setHeader("content-length", RESPONSE_LENGTH);
        }
    }


    /**
     * Verifies that the same Content-Length is returned for both GET and HEAD
     * operations when a Servlet includes content from another Servlet
     */
    @Test
    public void testBug57602() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);

        Bug57602ServletOuter outer = new Bug57602ServletOuter();
        Tomcat.addServlet(ctx, "Bug57602ServletOuter", outer);
        ctx.addServletMapping("/outer", "Bug57602ServletOuter");

        Bug57602ServletInner inner = new Bug57602ServletInner();
        Tomcat.addServlet(ctx, "Bug57602ServletInner", inner);
        ctx.addServletMapping("/inner", "Bug57602ServletInner");

        tomcat.start();

        Map<String,List<String>> resHeaders= new HashMap<String,List<String>>();
        String path = "http://localhost:" + getPort() + "/outer";
        ByteChunk out = new ByteChunk();

        int rc = getUrl(path, out, resHeaders);
        Assert.assertEquals(HttpServletResponse.SC_OK, rc);
        String length = resHeaders.get("Content-Length").get(0);
        Assert.assertEquals(Long.parseLong(length), out.getLength());
        out.recycle();

        rc = headUrl(path, out, resHeaders);
        Assert.assertEquals(HttpServletResponse.SC_OK, rc);
        Assert.assertEquals(0, out.getLength());
        Assert.assertEquals(length, resHeaders.get("Content-Length").get(0));

        tomcat.stop();
    }


    private static class Bug57602ServletOuter extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter pw = resp.getWriter();
            pw.println("Header");
            req.getRequestDispatcher("/inner").include(req, resp);
            pw.println("Footer");
        }
    }


    private static class Bug57602ServletInner extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter pw = resp.getWriter();
            pw.println("Included");
        }
    }
}
