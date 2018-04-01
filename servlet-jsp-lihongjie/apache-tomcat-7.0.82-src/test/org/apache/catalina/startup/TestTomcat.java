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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextResourceLink;
import org.apache.catalina.ha.context.ReplicatedContext;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestTomcat extends TomcatBaseTest {

    /**
     * Simple servlet to test in-line registration.
     */
    public static class HelloWorld extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        public void doGet(HttpServletRequest req, HttpServletResponse res)
                throws IOException {
            res.getWriter().write("Hello world");
        }
    }

    /**
     * Simple servlet to test the default session manager.
     */
    public static class HelloWorldSession extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        public void doGet(HttpServletRequest req, HttpServletResponse res)
                throws IOException {
            HttpSession s = req.getSession(true);
            s.getId();
            res.getWriter().write("Hello world");
        }
    }

    /**
     * Simple servlet to test JNDI
     */
    public static class HelloWorldJndi extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private static final String JNDI_ENV_NAME = "test";

        @Override
        public void doGet(HttpServletRequest req, HttpServletResponse res)
                throws IOException {

            String name = null;

            try {
                javax.naming.Context initCtx = new InitialContext();
                javax.naming.Context envCtx =
                        (javax.naming.Context) initCtx.lookup("java:comp/env");
                name = (String) envCtx.lookup(JNDI_ENV_NAME);
            } catch (NamingException e) {
                throw new IOException(e);
            }

            res.getWriter().write("Hello, " + name);
        }
    }

    /**
     * Servlet that tries to obtain a URL for WEB-INF/web.xml
     */
    public static class GetResource extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException {
            URL url = req.getServletContext().getResource("/WEB-INF/web.xml");

            res.getWriter().write("The URL obtained for /WEB-INF/web.xml was ");
            if (url == null) {
                res.getWriter().write("null");
            } else {
                res.getWriter().write(url.toString() + "\n");
                res.getWriter().write("The first 20 characters of that resource are:\n");

                // Read some content from the resource
                URLConnection conn = url.openConnection();

                InputStream is = null;
                Reader reader = null;
                char cbuf[] = new char[20];
                int read = 0;
                try {
                    is = conn.getInputStream();
                    reader = new InputStreamReader(is);
                    while (read < 20) {
                        int len = reader.read(cbuf, read, cbuf.length - read);
                        res.getWriter().write(cbuf, read, len);
                        read = read + len;
                    }
                } finally {
                    if (reader != null) {
                        try { reader.close(); } catch(IOException ioe) {/*Ignore*/}
                    }
                    if (is != null) {
                        try { is.close(); } catch(IOException ioe) {/*Ignore*/}
                    }
                }


            }


        }
    }

    /**
     * Simple servlet to test initialization of servlet instances.
     */
    private static class InitCount extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private AtomicInteger callCount = new AtomicInteger(0);

        @Override
        public void init() throws ServletException {
            super.init();
            callCount.incrementAndGet();
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.getWriter().print("OK");
        }

        public int getCallCount() {
            return callCount.intValue();
        }
    }


    /**
     * Simple Realm that uses a configurable {@link Map} to link user names and
     * passwords.
     */
    public static final class MapRealm extends RealmBase {
        private Map<String,String> users = new HashMap<String,String>();
        private Map<String,List<String>> roles =
            new HashMap<String,List<String>>();

        public void addUser(String username, String password) {
            users.put(username, password);
        }

        public void addUserRole(String username, String role) {
            List<String> userRoles = roles.get(username);
            if (userRoles == null) {
                userRoles = new ArrayList<String>();
                roles.put(username, userRoles);
            }
            userRoles.add(role);
        }

        @Override
        protected String getName() {
            return "MapRealm";
        }

        @Override
        protected String getPassword(String username) {
            return users.get(username);
        }

        @Override
        protected Principal getPrincipal(String username) {
            return new GenericPrincipal(username, getPassword(username),
                    roles.get(username));
        }

    }

    /**
     * Start tomcat with a single context and one
     * servlet - all programmatic, no server.xml or
     * web.xml used.
     *
     * @throws Exception
     */
    @Test
    public void testProgrammatic() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        // You can customize the context by calling
        // its API

        Tomcat.addServlet(ctx, "myServlet", new HelloWorld());
        ctx.addServletMapping("/", "myServlet");

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + "/");
        assertEquals("Hello world", res.toString());
    }

    @Test
    public void testSingleWebapp() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File(getBuildDirectory(), "webapps/examples");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/examples", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/examples/servlets/servlet/HelloWorldExample");
        String text = res.toString();
        assertTrue(text, text.indexOf("<a href=\"../helloworld.html\">") > 0);
    }

    @Test
    public void testJsps() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File(getBuildDirectory(), "webapps/examples");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/examples", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/examples/jsp/jsp2/el/basic-arithmetic.jsp");
        String text = res.toString();
        assertTrue(text, text.indexOf("<td>${(1==2) ? 3 : 4}</td>") > 0);
    }

    @Test
    public void testSession() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        // You can customize the context by calling
        // its API

        Tomcat.addServlet(ctx, "myServlet", new HelloWorldSession());
        ctx.addServletMapping("/", "myServlet");

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + "/");
        assertEquals("Hello world", res.toString());
    }

    @Test
    public void testLaunchTime() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        long t0 = System.currentTimeMillis();
        tomcat.addContext(null, "", ".");
        tomcat.start();
        log.info("Tomcat started in [" + (System.currentTimeMillis() - t0)
                + "] ms");
     }


    /**
     * Test for enabling JNDI.
     */
    @Test
    public void testEnableNaming() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        // You can customise the context by calling its API

        // Enable JNDI - it is disabled by default
        tomcat.enableNaming();

        ContextEnvironment environment = new ContextEnvironment();
        environment.setType("java.lang.String");
        environment.setName(HelloWorldJndi.JNDI_ENV_NAME);
        environment.setValue("Tomcat User");
        ctx.getNamingResources().addEnvironment(environment);

        Tomcat.addServlet(ctx, "jndiServlet", new HelloWorldJndi());
        ctx.addServletMapping("/", "jndiServlet");

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + "/");
        assertEquals("Hello, Tomcat User", res.toString());
    }

    /**
     * Test for enabling JNDI and using global resources.
     */
    @Test
    public void testEnableNamingGlobal() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        // You can customise the context by calling its API

        // Enable JNDI - it is disabled by default
        tomcat.enableNaming();

        ContextEnvironment environment = new ContextEnvironment();
        environment.setType("java.lang.String");
        environment.setName("globalTest");
        environment.setValue("Tomcat User");
        tomcat.getServer().getGlobalNamingResources().addEnvironment(environment);

        ContextResourceLink link = new ContextResourceLink();
        link.setGlobal("globalTest");
        link.setName(HelloWorldJndi.JNDI_ENV_NAME);
        link.setType("java.lang.String");
        ctx.getNamingResources().addResourceLink(link);

        Tomcat.addServlet(ctx, "jndiServlet", new HelloWorldJndi());
        ctx.addServletMapping("/", "jndiServlet");

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + "/");
        assertEquals("Hello, Tomcat User", res.toString());
    }


    /**
     * Test for https://bz.apache.org/bugzilla/show_bug.cgi?id=47866
     */
    @Test
    public void testGetResource() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        String contextPath = "/examples";

        File appDir = new File(getBuildDirectory(), "webapps" + contextPath);
        // app dir is relative to server home
        Context ctx =
            tomcat.addWebapp(null, "/examples", appDir.getAbsolutePath());

        Tomcat.addServlet(ctx, "testGetResource", new GetResource());
        ctx.addServletMapping("/testGetResource", "testGetResource");

        tomcat.start();

        ByteChunk res = new ByteChunk();

        int rc =getUrl("http://localhost:" + getPort() + contextPath +
                "/testGetResource", res, null);
        assertEquals(HttpServletResponse.SC_OK, rc);
        assertTrue(res.toString().contains("<?xml version=\"1.0\" "));
    }

    @Test
    public void testBug50826() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        String contextPath = "/examples";

        File appDir = new File(getBuildDirectory(), "webapps" + contextPath);
        // app dir is relative to server home
        tomcat.addWebapp(null, "/examples", appDir.getAbsolutePath());

        Exception e = null;
        try {
            tomcat.destroy();
        } catch (Exception ex) {
            ex.printStackTrace();
            e = ex;
        }
        assertNull(e);
    }

    @Test
    public void testBug53301() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        InitCount initCount = new InitCount();
        Tomcat.addServlet(ctx, "initCount", initCount);
        ctx.addServletMapping("/", "initCount");

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + "/");
        assertEquals("OK", res.toString());

        assertEquals(1, initCount.getCallCount());
    }

    @Test
    public void testGetWebappConfigFileFromDirectory() {
        Tomcat tomcat = new Tomcat();
        assertNotNull(tomcat.getWebappConfigFile("test/deployment/dirContext", ""));
    }

    @Test
    public void testGetWebappConfigFileFromDirectoryNegative() {
        Tomcat tomcat = new Tomcat();
        assertNull(tomcat.getWebappConfigFile("test/deployment/dirNoContext", ""));
    }

    @Test
    public void testGetWebappConfigFileFromJar() {
        Tomcat tomcat = new Tomcat();
        assertNotNull(tomcat.getWebappConfigFile("test/deployment/context.war", ""));
    }

    @Test
    public void testGetWebappConfigFileFromJarNegative() {
        Tomcat tomcat = new Tomcat();
        assertNull(tomcat.getWebappConfigFile("test/deployment/noContext.war", ""));
    }

    @Test
    public void testBug51526() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appFile = new File("test/deployment/context.war");
        StandardContext context = (StandardContext) tomcat.addWebapp(null, "/test",
                appFile.getAbsolutePath());

        tomcat.start();

        assertEquals("WAR_CONTEXT", context.getSessionCookieName());
    }

    @Test
    public void testGetDefaultContextPerAddWebapp() {
        Tomcat tomcat = getTomcatInstance();

        File appFile = new File("test/deployment/context.war");
        Context context = tomcat.addWebapp(null,
                "/test", appFile.getAbsolutePath());

        assertEquals(StandardContext.class.getName(), context.getClass()
                .getName());
    }

    @Test
    public void testGetBrokenContextPerAddWepapp() {
        Tomcat tomcat = getTomcatInstance();
        Host host = tomcat.getHost();
        if (host instanceof StandardHost) {
            ((StandardHost) host).setContextClass("InvalidContextClassName");
        }

        try {
            File appFile = new File("test/deployment/context.war");
            tomcat.addWebapp(null, "/test", appFile.getAbsolutePath());
            fail();
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testGetCustomContextPerAddWebappWithNullHost() {
        Tomcat tomcat = getTomcatInstance();
        Host host = tomcat.getHost();
        if (host instanceof StandardHost) {
            ((StandardHost) host).setContextClass(ReplicatedContext.class
                    .getName());
        }

        File appFile = new File("test/deployment/context.war");
        Context context = tomcat.addWebapp(null, "/test",
                appFile.getAbsolutePath());

        assertEquals(ReplicatedContext.class.getName(), context.getClass()
                .getName());
    }

    @Test
    public void testGetCustomContextPerAddWebappWithHost() {
        Tomcat tomcat = getTomcatInstance();
        Host host = tomcat.getHost();
        if (host instanceof StandardHost) {
            ((StandardHost) host).setContextClass(ReplicatedContext.class
                    .getName());
        }

        File appFile = new File("test/deployment/context.war");
        Context context = tomcat.addWebapp(host, "/test",
                appFile.getAbsolutePath());

        assertEquals(ReplicatedContext.class.getName(), context.getClass()
                .getName());
    }

        @Test
    public void testGetDefaultContextPerAddContext() {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext(null, "", null);
        assertEquals(StandardContext.class.getName(), ctx.getClass().getName());
    }

    @Test
    public void testGetBrokenContextPerAddContext() {
        Tomcat tomcat = getTomcatInstance();
        Host host = tomcat.getHost();
        if (host instanceof StandardHost) {
            ((StandardHost) host).setContextClass("InvalidContextClassName");
        }

        // No file system docBase required
        try {
            tomcat.addContext(null, "", null);
            fail();
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void testGetCustomContextPerAddContextWithHost() {
        Tomcat tomcat = getTomcatInstance();
        Host host = tomcat.getHost();
        if (host instanceof StandardHost) {
            ((StandardHost) host).setContextClass(ReplicatedContext.class
                    .getName());
        }

        // No file system docBase required
        Context ctx = tomcat.addContext(host, "", null);
        assertEquals(ReplicatedContext.class.getName(), ctx.getClass()
                .getName());
    }

    @Test
    public void testCustomContextConfig() throws Exception {

        Tomcat tomcat = getTomcatInstance();

        tomcat.getHost().setConfigClass(CustomContextConfig.class.getName());

        File docBase = new File("test/webapp-3.0");
        tomcat.addWebapp("/test", docBase.getAbsolutePath());

        tomcat.start();

        Assert.assertTrue(CustomContextConfig.isUsed());
    }

    public static class CustomContextConfig implements LifecycleListener {

        private static volatile boolean used = false;

        public static boolean isUsed() {
            return used;
        }

        @Override
        public void lifecycleEvent(LifecycleEvent event) {
            // Hack via a static since we can't pass an instance in the test.
            used = true;
        }
    }
}
