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
package org.apache.catalina.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.ha.tcp.SimpleTcpCluster;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestStandardSessionIntegration extends TomcatBaseTest {

    /*
     * Test session.invalidate() in a clustered environment.
     */
    @Test
    public void testBug56578a() throws Exception {
        doTestInvalidate(true);
    }

    @Test
    public void testBug56578b() throws Exception {
        doTestInvalidate(false);
    }

    private void doTestInvalidate(boolean useClustering) throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "bug56578", new Bug56578Servlet());
        ctx.addServletMapping("/bug56578", "bug56578");

        if (useClustering) {
            tomcat.getEngine().setCluster(new SimpleTcpCluster());
            ctx.setDistributable(true);
            ctx.setManager(ctx.getCluster().createManager(""));
        }
        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + "/bug56578");
        Assert.assertEquals("PASS", res.toString());
    }

    private static class Bug56578Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter pw = resp.getWriter();

            HttpSession session = req.getSession(true);
            session.invalidate();

            // Ugly but the easiest way to test of the session is valid or not
            boolean result;
            try {
                session.getCreationTime();
                result = false;
            } catch (IllegalStateException ise) {
                result = true;
            }

            if (result) {
                pw.print("PASS");
            } else {
                pw.print("FAIL");
            }
        }
    }
}
