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

package org.apache.catalina.filters;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestAddCharSetFilter extends TomcatBaseTest {

    @Test
    public void testNoneSpecifiedMode1() throws Exception {
        doTest(null, "ISO-8859-1");
    }

    @Test
    public void testNoneSpecifiedMode2() throws Exception {
        doTest(null, "ISO-8859-2", 2);
    }

    @Test
    public void testNoneSpecifiedMode3() throws Exception {
        doTest(null, "ISO-8859-3", 3);
    }

    @Test
    public void testDefault() throws Exception {
        doTest("default", "ISO-8859-1");
    }

    @Test
    public void testDefaultMixedCase() throws Exception {
        doTest("dEfAuLt", "ISO-8859-1");
    }

    @Test
    public void testSystem() throws Exception {
        doTest("system", Charset.defaultCharset().name());
    }

    @Test
    public void testSystemMixedCase() throws Exception {
        doTest("SyStEm", Charset.defaultCharset().name());
    }

    @Test
    public void testUTF8() throws Exception {
        doTest("utf-8", "utf-8");
    }


    private void doTest(String encoding, String expected) throws Exception {
        doTest(encoding, expected, 1);
    }

    private void doTest(String encoding, String expected, int mode)
            throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        // Add the Servlet
        CharsetServlet servlet = new CharsetServlet(mode);
        Tomcat.addServlet(ctx, "servlet", servlet);
        ctx.addServletMapping("/", "servlet");

        // Add the Filter
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterClass(AddDefaultCharsetFilter.class.getName());
        filterDef.setFilterName("filter");
        if (encoding != null) {
            filterDef.addInitParameter("encoding", encoding);
        }
        ctx.addFilterDef(filterDef);
        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName("filter");
        filterMap.addServletName("servlet");
        ctx.addFilterMap(filterMap);

        tomcat.start();

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        getUrl("http://localhost:" + getPort() + "/", new ByteChunk(), headers);

        List<String> ctHeaders = headers.get("Content-Type");
        assertEquals(1, ctHeaders.size());
        String ct = ctHeaders.get(0);
        assertEquals("text/plain;charset=" + expected, ct);
    }

    private static class CharsetServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;
        private static final String OUTPUT = "OK";

        private final int mode;

        public CharsetServlet(int mode) {
            this.mode = mode;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            switch (mode) {
                case 1:
                    resp.setContentType("text/plain");
                    break;
                case 2:
                    resp.setContentType("text/plain;charset=ISO-8859-2");
                    break;
                case 3:
                    resp.setContentType("text/plain");
                    resp.setCharacterEncoding("ISO-8859-3");
                    break;
                default:
                    resp.setContentType("text/plain;charset=ISO-8859-4");
                    break;
            }
            resp.getWriter().print(OUTPUT);
        }
    }
}
