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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;

/**
 * Base Test case for {@link Cookies}. <b>Note</b> because of the use of
 * <code>static final</code> constants in {@link Cookies}, each of these tests
 * must be executed in a new JVM instance. The tests have been place in separate
 * classes to facilitate this when running the unit tests via Ant.
 */
public abstract class CookiesBaseTest extends TomcatBaseTest {

    /**
     * Servlet for cookie naming test.
     */
    public static class CookieServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private final String cookieName;
        private final String cookieValue;

        public CookieServlet(String cookieName, String cookieValue) {
            this.cookieName = cookieName;
            this.cookieValue = cookieValue;
        }

        @Override
        public void doGet(HttpServletRequest req, HttpServletResponse res)
                throws IOException {
            try {
                Cookie cookie = new Cookie(cookieName, cookieValue);
                res.addCookie(cookie);
                res.getWriter().write("Cookie name ok");
            } catch (IllegalArgumentException iae) {
                res.getWriter().write("Cookie name fail");
            }
        }

    }


    public static void addServlets(Tomcat tomcat) {
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "invalid", new CookieServlet("na;me", "value"));
        ctx.addServletMapping("/invalid", "invalid");
        Tomcat.addServlet(ctx, "null", new CookieServlet(null, "value"));
        ctx.addServletMapping("/null", "null");
        Tomcat.addServlet(ctx, "blank", new CookieServlet("", "value"));
        ctx.addServletMapping("/blank", "blank");
        Tomcat.addServlet(ctx, "invalidFwd",
                new CookieServlet("na/me", "value"));
        ctx.addServletMapping("/invalidFwd", "invalidFwd");
        Tomcat.addServlet(ctx, "invalidStrict",
                new CookieServlet("na?me", "value"));
        ctx.addServletMapping("/invalidStrict", "invalidStrict");
        Tomcat.addServlet(ctx, "valid", new CookieServlet("name", "value"));
        ctx.addServletMapping("/valid", "valid");
        Tomcat.addServlet(ctx, "switch", new CookieServlet("name", "val?ue"));
        ctx.addServletMapping("/switch", "switch");

    }

    public abstract void testCookiesInstance() throws Exception;

}
