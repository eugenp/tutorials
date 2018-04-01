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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestApplicationHttpRequest extends TomcatBaseTest {

    /*
     * https://bz.apache.org/bugzilla/show_bug.cgi?id=58836
     */
    @Test
    public void testForwardQueryString01() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b" });
        doQueryStringTest(null, "a=b", expected);
    }


    @Test
    public void testForwardQueryString02() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b", "c" });
        doQueryStringTest(null, "a=b&a=c", expected);
    }


    @Test
    public void testForwardQueryString03() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b" });
        expected.put("c", new String[] { "d" });
        doQueryStringTest(null, "a=b&c=d", expected);
    }


    @Test
    public void testForwardQueryString04() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b", "e" });
        expected.put("c", new String[] { "d" });
        doQueryStringTest(null, "a=b&c=d&a=e", expected);
    }


    @Test
    public void testForwardQueryString05() throws Exception {
        // Parameters with no value are assigned a value of the empty string
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b", "e" });
        expected.put("c", new String[] { "" });
        doQueryStringTest(null, "a=b&c&a=e", expected);
    }


    @Test
    public void testOriginalQueryString01() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b" });
        doQueryStringTest("a=b", null, expected);
    }


    @Test
    public void testOriginalQueryString02() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b", "c" });
        doQueryStringTest("a=b&a=c", null, expected);
    }


    @Test
    public void testOriginalQueryString03() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b" });
        expected.put("c", new String[] { "d" });
        doQueryStringTest("a=b&c=d", null, expected);
    }


    @Test
    public void testOriginalQueryString04() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b", "e" });
        expected.put("c", new String[] { "d" });
        doQueryStringTest("a=b&c=d&a=e", null, expected);
    }


    @Test
    public void testOriginalQueryString05() throws Exception {
        // Parameters with no value are assigned a value of the empty string
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b", "e" });
        expected.put("c", new String[] { "" });
        doQueryStringTest("a=b&c&a=e", null, expected);
    }


    @Test
    public void testMergeQueryString01() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "z", "b" });
        doQueryStringTest("a=b", "a=z", expected);
    }


    @Test
    public void testMergeQueryString02() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "z", "b", "e" });
        expected.put("c", new String[] { "" });
        doQueryStringTest("a=b&c&a=e", "a=z", expected);
    }


    @Test
    public void testMergeQueryString03() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b", "e" });
        expected.put("c", new String[] { "z", "" });
        doQueryStringTest("a=b&c&a=e", "c=z", expected);
    }


    @Test
    public void testMergeQueryString04() throws Exception {
        Map<String,String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "", "b", "e" });
        expected.put("c", new String[] { "" });
        doQueryStringTest("a=b&c&a=e", "a", expected);
    }

    @Test
    public void testMergeQueryString05() throws Exception {
        // https://ru.wikipedia.org/wiki/%D0%A2%D0%B5%D1%81%D1%82
        // "Test" = "Test"
        String test = "\u0422\u0435\u0441\u0442";
        String query = test + "=%D0%A2%D0%B5%D1%81%D1%82";

        Map<String, String[]> expected = new HashMap<String, String[]>();
        expected.put("a", new String[] { "b" });
        expected.put(test, new String[] { test });
        doQueryStringTest("a=b", query, expected);
    }


    private void doQueryStringTest(String originalQueryString, String forwardQueryString,
            Map<String,String[]> expected) throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        if (forwardQueryString == null) {
            Tomcat.addServlet(ctx, "forward", new ForwardServlet("/display"));
        } else {
            Tomcat.addServlet(ctx, "forward", new ForwardServlet("/display?" + forwardQueryString));
        }
        ctx.addServletMapping("/forward", "forward");

        Tomcat.addServlet(ctx, "display", new DisplayParameterServlet(expected));
        ctx.addServletMapping("/display", "display");

        tomcat.start();

        ByteChunk response = new ByteChunk();
        StringBuilder target = new StringBuilder("http://localhost:");
        target.append(getPort());
        target.append("/forward");
        if (originalQueryString != null) {
            target.append('?');
            target.append(originalQueryString);
        }
        int rc = getUrl(target.toString(), response, null);

        Assert.assertEquals(200, rc);
        Assert.assertEquals("OK", response.toString());
    }


    @Test
    public void testParameterImmutability() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "forward", new ForwardServlet("/modify"));
        ctx.addServletMapping("/forward", "forward");

        Tomcat.addServlet(ctx, "modify", new ModifyParameterServlet());
        ctx.addServletMapping("/modify", "modify");

        tomcat.start();

        ByteChunk response = new ByteChunk();
        StringBuilder target = new StringBuilder("http://localhost:");
        target.append(getPort());
        target.append("/forward");
        int rc = getUrl(target.toString(), response, null);

        Assert.assertEquals(200, rc);
        Assert.assertEquals("OK", response.toString());
    }


    private static class ForwardServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private final String target;

        public ForwardServlet(String target) {
            this.target = target;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            req.setCharacterEncoding("UTF-8");
            req.getRequestDispatcher(target).forward(req, resp);
        }
    }


    private static class DisplayParameterServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private Map<String,String[]> expected;

        public DisplayParameterServlet(Map<String,String[]> expected) {
            this.expected = expected;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter w = resp.getWriter();
            Map<String,String[]> actual = req.getParameterMap();

            boolean ok = true;
            for (Entry<String,String[]> entry : actual.entrySet()) {
                String[] expectedValue = expected.get(entry.getKey());
                if (expectedValue == null ||
                        expectedValue.length != entry.getValue().length) {
                    ok = false;
                    break;
                }
                for (int i = 0; i < expectedValue.length; i++) {
                    if (!expectedValue[i].equals(entry.getValue()[i])) {
                        ok = false;
                        break;
                    }
                }
                if (!ok) {
                    break;
                }
            }

            if (ok) {
                w.print("OK");
                return;
            }
            boolean firstParam = true;
            for (Entry<String,String[]> param : actual.entrySet()) {
                if (firstParam) {
                    firstParam = false;
                } else {
                    w.print(';');
                }
                w.print(param.getKey());
                w.print(':');
                boolean firstValue = true;
                for (String value : param.getValue()) {
                    if (firstValue) {
                        firstValue = false;
                    } else {
                        w.print(',');
                    }
                    w.print('(');
                    w.print(value);
                    w.print(')');
                }
            }
        }
    }


    private static class ModifyParameterServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        // Suppress warnings generated because the code is trying to put the
        // wrong type of values into the Map
        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            Map map = req.getParameterMap();

            boolean insertWorks;
            try {
                map.put("test", new Integer[] { Integer.valueOf(0) });
                insertWorks = true;
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                insertWorks = false;
            }

            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter pw = resp.getWriter();
            if (insertWorks) {
                pw.print("FAIL");
            } else {
                pw.print("OK");
            }
        }
    }
}
