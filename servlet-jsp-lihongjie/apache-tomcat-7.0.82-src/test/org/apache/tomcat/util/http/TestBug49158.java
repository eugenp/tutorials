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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.buf.ByteChunk;

/**
 * Test case for {@link Cookies}. <b>Note</b> because of the use of <code>final
 * static</code> constants in {@link Cookies}, each of these tests must be
 * executed in a new JVM instance. The tests have been place in separate classes
 * to facilitate this when running the unit tests via Ant.
 */
public class TestBug49158 extends CookiesBaseTest {
    public static final String path = "49158";

    @Override
    @Test
    public void testCookiesInstance() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        addServlets(tomcat);
        tomcat.start();
        Map<String,List<String>> headers = new HashMap<String,List<String>>();
        ByteChunk res = new ByteChunk();
        getUrl("http://localhost:" + getPort() + "/"+path, res, headers);
        List<String> cookieHeaders = headers.get("Set-Cookie");
        assertEquals("There should only be one Set-Cookie header in this test",
                1, cookieHeaders.size());
    }

    public static void addServlets(Tomcat tomcat) {
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, path, new TestBug49158Servlet());
        ctx.addServletMapping("/"+path, path);
    }

    public static class TestBug49158Servlet extends HttpServlet {

        private static final long serialVersionUID = 2725990508758127399L;

        @Override
        public void service(HttpServletRequest req, HttpServletResponse res)
                throws ServletException, IOException {
            HttpSession session = req.getSession();
            session.invalidate();
            session = req.getSession();
            session.invalidate();
            req.getSession();
        }

    }

}
