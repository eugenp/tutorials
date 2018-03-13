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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.startup.TestTomcat.MapRealm;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestStandardWrapper extends TomcatBaseTest {

    @Test
    public void testSecurityAnnotationsSimple() throws Exception {
        doTest(DenyAllServlet.class.getName(), false, false, false);
    }

    @Test
    public void testSecurityAnnotationsSubclass1() throws Exception {
        doTest(SubclassDenyAllServlet.class.getName(), false, false, false);
    }

    @Test
    public void testSecurityAnnotationsSubclass2() throws Exception {
        doTest(SubclassAllowAllServlet.class.getName(), false, false, true);
    }

    @Test
    public void testSecurityAnnotationsMethods1() throws Exception {
        doTest(MethodConstraintServlet.class.getName(), false, false, false);
    }

    @Test
    public void testSecurityAnnotationsMethods2() throws Exception {
        doTest(MethodConstraintServlet.class.getName(), true, false, true);
    }

    @Test
    public void testSecurityAnnotationsRole1() throws Exception {
        doTest(RoleAllowServlet.class.getName(), false, true, true);
    }

    @Test
    public void testSecurityAnnotationsRole2() throws Exception {
        doTest(RoleDenyServlet.class.getName(), false, true, false);
    }

    @Test
    public void testSecurityAnnotationsWebXmlPriority() throws Exception {

        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0-fragments");
        tomcat.addWebapp(null, "", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk bc = new ByteChunk();
        int rc;
        rc = getUrl("http://localhost:" + getPort() +
                "/testStandardWrapper/securityAnnotationsWebXmlPriority",
                bc, null, null);

        assertTrue(bc.getLength() > 0);
        assertEquals(403, rc);
    }

    @Test
    public void testSecurityAnnotationsMetaDataPriority() throws Exception {

        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk bc = new ByteChunk();
        int rc;
        rc = getUrl("http://localhost:" + getPort() +
                "/testStandardWrapper/securityAnnotationsMetaDataPriority",
                bc, null, null);

        assertEquals("OK", bc.toString());
        assertEquals(200, rc);
    }

    @Test
    public void testSecurityAnnotationsAddServlet1() throws Exception {
        doTestSecurityAnnotationsAddServlet(false);
    }

    @Test
    public void testSecurityAnnotationsAddServlet2() throws Exception {
        doTestSecurityAnnotationsAddServlet(true);
    }

    @Test
    public void testSecurityAnnotationsNoWebXmlConstraints() throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0-servletsecurity");
        tomcat.addWebapp(null, "", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk bc = new ByteChunk();
        int rc;
        rc = getUrl("http://localhost:" + getPort() + "/",
                bc, null, null);

        assertTrue(bc.getLength() > 0);
        assertEquals(403, rc);
    }

    @Test
    public void testSecurityAnnotationsNoWebXmlLoginConfig() throws Exception {
        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0-servletsecurity2");
        tomcat.addWebapp(null, "", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk bc = new ByteChunk();
        int rc;
        rc = getUrl("http://localhost:" + getPort() + "/protected.jsp",
                bc, null, null);

        assertTrue(bc.getLength() > 0);
        assertEquals(403, rc);

        bc.recycle();

        rc = getUrl("http://localhost:" + getPort() + "/unprotected.jsp",
                bc, null, null);

        assertEquals(200, rc);
        assertTrue(bc.toString().contains("00-OK"));
    }

    private void doTestSecurityAnnotationsAddServlet(boolean useCreateServlet)
            throws Exception {

        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Servlet s = new DenyAllServlet();
        ServletContainerInitializer sci = new SCI(s, useCreateServlet);
        ctx.addServletContainerInitializer(sci, null);

        tomcat.start();

        ByteChunk bc = new ByteChunk();
        int rc;
        rc = getUrl("http://localhost:" + getPort() + "/", bc, null, null);

        if (useCreateServlet) {
            assertTrue(bc.getLength() > 0);
            assertEquals(403, rc);
        } else {
            assertEquals("OK", bc.toString());
            assertEquals(200, rc);
        }
    }

    private void doTest(String servletClassName, boolean usePost,
            boolean useRole, boolean expect200) throws Exception {

        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Wrapper wrapper = Tomcat.addServlet(ctx, "servlet", servletClassName);
        wrapper.setAsyncSupported(true);
        ctx.addServletMapping("/", "servlet");

        if (useRole) {
            MapRealm realm = new MapRealm();
            realm.addUser("testUser", "testPwd");
            realm.addUserRole("testUser", "testRole");
            ctx.setRealm(realm);

            ctx.setLoginConfig(new LoginConfig("BASIC", null, null, null));
            ctx.getPipeline().addValve(new BasicAuthenticator());
        }

        tomcat.start();

        ByteChunk bc = new ByteChunk();
        Map<String,List<String>> reqHeaders = null;
        if (useRole) {
            reqHeaders = new HashMap<String,List<String>>();
            List<String> authHeaders = new ArrayList<String>();
            // testUser, testPwd
            authHeaders.add("Basic dGVzdFVzZXI6dGVzdFB3ZA==");
            reqHeaders.put("Authorization", authHeaders);
        }

        int rc;
        if (usePost) {
            rc = postUrl(null, "http://localhost:" + getPort() + "/", bc,
                    reqHeaders, null);
        } else {
            rc = getUrl("http://localhost:" + getPort() + "/", bc, reqHeaders,
                    null);
        }

        if (expect200) {
            assertEquals("OK", bc.toString());
            assertEquals(200, rc);
        } else {
            assertTrue(bc.getLength() > 0);
            assertEquals(403, rc);
        }
    }

    public static class TestServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            resp.setContentType("text/plain");
            resp.getWriter().print("OK");
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            doGet(req, resp);
        }
    }

    @ServletSecurity(@HttpConstraint(EmptyRoleSemantic.DENY))
    public static class DenyAllServlet extends TestServlet {
        private static final long serialVersionUID = 1L;
    }

    public static class SubclassDenyAllServlet extends DenyAllServlet {
        private static final long serialVersionUID = 1L;
    }

    @ServletSecurity(@HttpConstraint(EmptyRoleSemantic.PERMIT))
    public static class SubclassAllowAllServlet extends DenyAllServlet {
        private static final long serialVersionUID = 1L;
    }

    @ServletSecurity(value= @HttpConstraint(EmptyRoleSemantic.PERMIT),
        httpMethodConstraints = {
            @HttpMethodConstraint(value="GET",
                    emptyRoleSemantic = EmptyRoleSemantic.DENY)
        }
    )
    public static class MethodConstraintServlet extends TestServlet {
        private static final long serialVersionUID = 1L;
    }

    @ServletSecurity(@HttpConstraint(rolesAllowed = "testRole"))
    public static class RoleAllowServlet extends TestServlet {
        private static final long serialVersionUID = 1L;
    }

    @ServletSecurity(@HttpConstraint(rolesAllowed = "otherRole"))
    public static class RoleDenyServlet extends TestServlet {
        private static final long serialVersionUID = 1L;
    }

    public static class SCI implements ServletContainerInitializer {

        private Servlet servlet;
        private boolean createServlet;

        public SCI(Servlet servlet, boolean createServlet) {
            this.servlet = servlet;
            this.createServlet = createServlet;
        }

        @Override
        public void onStartup(Set<Class<?>> c, ServletContext ctx)
                throws ServletException {
            Servlet s;

            if (createServlet) {
                s = ctx.createServlet(servlet.getClass());
            } else {
                s = servlet;
            }
            ServletRegistration.Dynamic r = ctx.addServlet("servlet", s);
            r.addMapping("/");
        }
    }


    public static final int BUG51445_THREAD_COUNT = 5;

    public static CountDownLatch latch = null;
    public static AtomicInteger counter = new AtomicInteger(0);

    public static void initLatch() {
        latch = new CountDownLatch(BUG51445_THREAD_COUNT);
    }

    @Test
    public void testBug51445AddServlet() throws Exception {

        initLatch();

        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "Bug51445", new Bug51445Servlet());
        ctx.addServletMapping("/", "Bug51445");

        tomcat.start();

        // Start the threads
        Bug51445Thread[] threads = new Bug51445Thread[5];
        for (int i = 0; i < BUG51445_THREAD_COUNT; i ++) {
            threads[i] = new Bug51445Thread(getPort());
            threads[i].start();
        }

        // Wait for threads to finish
        for (int i = 0; i < BUG51445_THREAD_COUNT; i ++) {
            threads[i].join();
        }

        Set<String> servlets = new HashSet<String>();
        // Output the result
        for (int i = 0; i < BUG51445_THREAD_COUNT; i ++) {
            System.out.println(threads[i].getResult());
        }

        // Check the result
        for (int i = 0; i < BUG51445_THREAD_COUNT; i ++) {
            String[] results = threads[i].getResult().split(",");
            assertEquals(2, results.length);
            assertEquals("10", results[0]);
            assertFalse(servlets.contains(results[1]));
            servlets.add(results[1]);
        }
    }

    @Test
    public void testBug51445AddChild() throws Exception {

        initLatch();

        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);

        StandardWrapper wrapper = new StandardWrapper();
        wrapper.setServletName("Bug51445");
        wrapper.setServletClass(Bug51445Servlet.class.getName());
        ctx.addChild(wrapper);
        ctx.addServletMapping("/", "Bug51445");

        tomcat.start();

        // Start the threads
        Bug51445Thread[] threads = new Bug51445Thread[5];
        for (int i = 0; i < BUG51445_THREAD_COUNT; i ++) {
            threads[i] = new Bug51445Thread(getPort());
            threads[i].start();
        }

        // Wait for threads to finish
        for (int i = 0; i < BUG51445_THREAD_COUNT; i ++) {
            threads[i].join();
        }

        Set<String> servlets = new HashSet<String>();
        // Output the result
        for (int i = 0; i < BUG51445_THREAD_COUNT; i ++) {
            System.out.println(threads[i].getResult());
        }
        // Check the result
        for (int i = 0; i < BUG51445_THREAD_COUNT; i ++) {
            String[] results = threads[i].getResult().split(",");
            assertEquals(2, results.length);
            assertEquals("10", results[0]);
            assertFalse(servlets.contains(results[1]));
            servlets.add(results[1]);
        }
    }

    private static class Bug51445Thread extends Thread {

        private int port;
        private String result;

        public Bug51445Thread(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try {
                ByteChunk res = getUrl("http://localhost:" + port + "/");
                result = res.toString();
            } catch (IOException ioe) {
                result = ioe.getMessage();
            }
        }

        public String getResult() {
            return result;
        }
    }

    /**
     * SingleThreadModel servlet that sets a value in the init() method.
     */
    @SuppressWarnings("deprecation")
    public static class Bug51445Servlet extends HttpServlet
            implements javax.servlet.SingleThreadModel {

        private static final long serialVersionUID = 1L;
        private static final long LATCH_TIMEOUT = 60;

        private int data = 0;
        private int counter;

        public Bug51445Servlet() {
            // Use this rather than hashCode since in some environments,
            // multiple instances of this object were created with the same
            // hashCode
            counter = TestStandardWrapper.counter.incrementAndGet();
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            boolean latchAwaitResult = false;

            // Ensure all threads have their own instance of the servlet
            latch.countDown();
            try {
                latchAwaitResult = latch.await(LATCH_TIMEOUT, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                // Ignore
            }

            resp.setContentType("text/plain");
            if (latchAwaitResult) {
                resp.getWriter().print(data);
            } else {
                resp.getWriter().print("Latch await failed");
            }
            resp.getWriter().print(",");
            resp.getWriter().print(counter);
        }

        @Override
        public void init(ServletConfig config) throws ServletException {
            super.init(config);
            data = 10;
        }
    }
}
