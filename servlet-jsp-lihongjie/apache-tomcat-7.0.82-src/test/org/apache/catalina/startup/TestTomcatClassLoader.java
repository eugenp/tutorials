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
package org.apache.catalina.startup;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.loader.WebappClassLoaderBase;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestTomcatClassLoader extends TomcatBaseTest {

    @Test
    public void testDefaultClassLoader() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "ClassLoaderReport", new ClassLoaderReport(null));
        ctx.addServletMapping("/", "ClassLoaderReport");

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + "/");
        assertEquals("WEBAPP,SYSTEM,OTHER,", res.toString());
    }

    @Test
    public void testNonDefaultClassLoader() throws Exception {

        ClassLoader cl = new URLClassLoader(new URL[0],
                Thread.currentThread().getContextClassLoader());

        Thread.currentThread().setContextClassLoader(cl);

        Tomcat tomcat = getTomcatInstance();
        tomcat.getServer().setParentClassLoader(cl);

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "ClassLoaderReport", new ClassLoaderReport(cl));
        ctx.addServletMapping("/", "ClassLoaderReport");

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + "/");
        assertEquals("WEBAPP,CUSTOM,SYSTEM,OTHER,", res.toString());
    }

    private static final class ClassLoaderReport extends HttpServlet {
        private static final long serialVersionUID = 1L;

        private transient ClassLoader custom;

        public ClassLoaderReport(ClassLoader custom) {
            this.custom = custom;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            PrintWriter out = resp.getWriter();

            ClassLoader system = ClassLoader.getSystemClassLoader();

            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            while (cl != null) {
                if (system == cl) {
                    out.print("SYSTEM,");
                } else if (custom == cl) {
                    out.print("CUSTOM,");
                } else if (cl instanceof WebappClassLoaderBase) {
                    out.print("WEBAPP,");
                } else {
                    out.print("OTHER,");
                }
                cl = cl.getParent();
            }
        }
    }
}
