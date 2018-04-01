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
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.HttpConstraintElement;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hamcrest.CoreMatchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Wrapper;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.SimpleHttpClient;
import org.apache.catalina.startup.TestTomcat.MapRealm;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;


public class TestStandardContext extends TomcatBaseTest {

    private static final String REQUEST =
        "GET / HTTP/1.1\r\n" +
        "Host: anything\r\n" +
        "Connection: close\r\n" +
        "\r\n";

    @Test
    public void testBug46243() throws Exception {
        // This tests that if a Filter init() fails then the web application
        // is not put into service. (BZ 46243)
        // This also tests that if the cause of the failure is gone,
        // the context can be started without a need to redeploy it.

        // Set up a container
        Tomcat tomcat = getTomcatInstance();

        File docBase = new File(tomcat.getHost().getAppBase(), "ROOT");
        if (!docBase.mkdirs() && !docBase.isDirectory()) {
            fail("Unable to create docBase");
        }

        Context root = tomcat.addContext("", "ROOT");
        configureTest46243Context(root, true);
        tomcat.start();

        // Configure the client
        Bug46243Client client =
                new Bug46243Client(tomcat.getConnector().getLocalPort());
        client.setRequest(new String[] { REQUEST });

        client.connect();
        client.processRequest();
        assertTrue(client.isResponse404());

        // Context failed to start. This checks that automatic transition
        // from FAILED to STOPPED state was successful.
        assertEquals(LifecycleState.STOPPED, root.getState());

        // Prepare context for the second attempt
        // Configuration was cleared on stop() thanks to
        // StandardContext.resetContext(), so we need to configure it again
        // from scratch.
        configureTest46243Context(root, false);
        root.start();
        // The same request is processed successfully
        client.connect();
        client.processRequest();
        assertTrue(client.isResponse200());
        assertEquals(Bug46243Filter.class.getName()
                + HelloWorldServlet.RESPONSE_TEXT, client.getResponseBody());
    }

    private static void configureTest46243Context(Context context, boolean fail) {
        // Add a test filter that fails
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterClass(Bug46243Filter.class.getName());
        filterDef.setFilterName("Bug46243");
        filterDef.addInitParameter("fail", Boolean.toString(fail));
        context.addFilterDef(filterDef);
        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName("Bug46243");
        filterMap.addURLPattern("*");
        context.addFilterMap(filterMap);

        // Add a test servlet so there is something to generate a response if
        // it works (although it shouldn't)
        Tomcat.addServlet(context, "Bug46243", new HelloWorldServlet());
        context.addServletMapping("/", "Bug46243");
    }

    private static final class Bug46243Client extends SimpleHttpClient {

        public Bug46243Client(int port) {
            setPort(port);
        }

        @Override
        public boolean isResponseBodyOK() {
            // Don't care about the body in this test
            return true;
        }
    }

    public static final class Bug46243Filter implements Filter {

        @Override
        public void destroy() {
            // NOOP
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response,
                FilterChain chain) throws IOException, ServletException {
            PrintWriter out = response.getWriter();
            out.print(getClass().getName());
            chain.doFilter(request, response);
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            boolean fail = filterConfig.getInitParameter("fail").equals("true");
            if (fail) {
                throw new ServletException("Init fail (test)",
                        new ClassNotFoundException());
            }
        }

    }

    @Test
    public void testWebappLoaderStartFail() throws Exception {
        // Test that if WebappLoader start() fails and if the cause of
        // the failure is gone, the context can be started without
        // a need to redeploy it.

        // Set up a container
        Tomcat tomcat = getTomcatInstance();
        tomcat.start();
        // To not start Context automatically, as we have to configure it first
        ((ContainerBase) tomcat.getHost()).setStartChildren(false);

        FailingWebappLoader loader = new FailingWebappLoader();
        File root = new File("test/webapp-3.0");
        Context context = tomcat.addWebapp("", root.getAbsolutePath());
        context.setLoader(loader);

        try {
            context.start();
            fail();
        } catch (LifecycleException ex) {
            // As expected
        }
        assertEquals(LifecycleState.FAILED, context.getState());

        // The second attempt
        loader.setFail(false);
        context.start();
        assertEquals(LifecycleState.STARTED, context.getState());

        // Using a test from testBug49922() to check that the webapp is running
        ByteChunk result = getUrl("http://localhost:" + getPort() +
                "/bug49922/target");
        assertEquals("Target", result.toString());
    }

    @Test
    public void testWebappListenerConfigureFail() throws Exception {
        // Test that if LifecycleListener on webapp fails during
        // configure_start event and if the cause of the failure is gone,
        // the context can be started without a need to redeploy it.

        // Set up a container
        Tomcat tomcat = getTomcatInstance();
        tomcat.start();
        // To not start Context automatically, as we have to configure it first
        ((ContainerBase) tomcat.getHost()).setStartChildren(false);

        FailingLifecycleListener listener = new FailingLifecycleListener();
        File root = new File("test/webapp-3.0");
        Context context = tomcat.addWebapp("", root.getAbsolutePath());
        context.addLifecycleListener(listener);

        try {
            context.start();
            fail();
        } catch (LifecycleException ex) {
            // As expected
        }
        assertEquals(LifecycleState.FAILED, context.getState());

        // The second attempt
        listener.setFail(false);
        context.start();
        assertEquals(LifecycleState.STARTED, context.getState());

        // Using a test from testBug49922() to check that the webapp is running
        ByteChunk result = getUrl("http://localhost:" + getPort() +
                "/bug49922/target");
        assertEquals("Target", result.toString());
    }

    private static class FailingWebappLoader extends WebappLoader {
        private boolean fail = true;
        protected void setFail(boolean fail) {
            this.fail = fail;
        }
        @Override
        protected void startInternal() throws LifecycleException {
            if (fail) {
                throw new RuntimeException("Start fail (test)");
            }
            super.startInternal();
        }
    }

    private static class FailingLifecycleListener implements LifecycleListener {
        private final String failEvent = Lifecycle.CONFIGURE_START_EVENT;
        private boolean fail = true;
        protected void setFail(boolean fail) {
            this.fail = fail;
        }
        @Override
        public void lifecycleEvent(LifecycleEvent event) {
            if (fail && event.getType().equals(failEvent)) {
                throw new RuntimeException(failEvent + " fail (test)");
            }
        }
    }

    @Test
    public void testBug49922() throws Exception {
        // Test that filter mapping works. Test that the same filter is
        // called only once, even if is selected by several mapping
        // url-patterns or by both a url-pattern and a servlet-name.

        // Set up a container
        Tomcat tomcat = getTomcatInstance();

        File root = new File("test/webapp-3.0");
        tomcat.addWebapp("", root.getAbsolutePath());

        tomcat.start();
        ByteChunk result = new ByteChunk();

        // Check filter and servlet aren't called
        int rc = getUrl("http://localhost:" + getPort() +
                "/bug49922/foo", result, null);
        assertEquals(HttpServletResponse.SC_NOT_FOUND, rc);
        assertTrue(result.getLength() > 0);

        // Check extension mapping works
        result = getUrl("http://localhost:" + getPort() + "/foo.do");
        assertEquals("FilterServlet", result.toString());

        // Check path mapping works
        result = getUrl("http://localhost:" + getPort() + "/bug49922/servlet");
        assertEquals("FilterServlet", result.toString());

        // Check servlet name mapping works
        result = getUrl("http://localhost:" + getPort() + "/foo.od");
        assertEquals("FilterServlet", result.toString());

        // Check filter is only called once
        result = getUrl("http://localhost:" + getPort() +
                "/bug49922/servlet/foo.do");
        assertEquals("FilterServlet", result.toString());
        result = getUrl("http://localhost:" + getPort() +
                "/bug49922/servlet/foo.od");
        assertEquals("FilterServlet", result.toString());

        // Check dispatcher mapping
        result = getUrl("http://localhost:" + getPort() +
                "/bug49922/target");
        assertEquals("Target", result.toString());
        result = getUrl("http://localhost:" + getPort() +
                "/bug49922/forward");
        assertEquals("FilterTarget", result.toString());
        result = getUrl("http://localhost:" + getPort() +
                "/bug49922/include");
        assertEquals("IncludeFilterTarget", result.toString());
    }


    public static final class Bug49922Filter implements Filter {

        @Override
        public void destroy() {
            // NOOP
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response,
                FilterChain chain) throws IOException, ServletException {
            response.setContentType("text/plain");
            response.getWriter().print("Filter");
            chain.doFilter(request, response);
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            // NOOP
        }
    }

    public static final class Bug49922ForwardServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            req.getRequestDispatcher("/bug49922/target").forward(req, resp);
        }

    }

    public static final class Bug49922IncludeServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.getWriter().print("Include");
            req.getRequestDispatcher("/bug49922/target").include(req, resp);
        }

    }

    public static final class Bug49922TargetServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.getWriter().print("Target");
        }

    }

    public static final class Bug49922Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.getWriter().print("Servlet");
        }

    }

    @Test
    public void testBug50015() throws Exception {
        // Test that configuring servlet security constraints programmatically
        // does work.

        // Set up a container
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        // Setup realm
        MapRealm realm = new MapRealm();
        realm.addUser("tomcat", "tomcat");
        realm.addUserRole("tomcat", "tomcat");
        ctx.setRealm(realm);

        // Configure app for BASIC auth
        LoginConfig lc = new LoginConfig();
        lc.setAuthMethod("BASIC");
        ctx.setLoginConfig(lc);
        ctx.getPipeline().addValve(new BasicAuthenticator());

        // Add ServletContainerInitializer
        ServletContainerInitializer sci = new Bug50015SCI();
        ctx.addServletContainerInitializer(sci, null);

        // Start the context
        tomcat.start();

        // Request the first servlet
        ByteChunk bc = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort() + "/bug50015",
                bc, null);

        // Check for a 401
        assertNotSame("OK", bc.toString());
        assertEquals(401, rc);
    }

    public static final class Bug50015SCI
            implements ServletContainerInitializer {

        @Override
        public void onStartup(Set<Class<?>> c, ServletContext ctx)
                throws ServletException {
            // Register and map servlet
            Servlet s = new Bug50015Servlet();
            ServletRegistration.Dynamic sr = ctx.addServlet("bug50015", s);
            sr.addMapping("/bug50015");

            // Limit access to users in the Tomcat role
            HttpConstraintElement hce = new HttpConstraintElement(
                    TransportGuarantee.NONE, "tomcat");
            ServletSecurityElement sse = new ServletSecurityElement(hce);
            sr.setServletSecurity(sse);
        }
    }

    public static final class Bug50015Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.getWriter().write("OK");
        }

    }

    @Test
    public void testBug51376a() throws Exception {
        doTestBug51376(false);
    }

    @Test
    public void testBug51376b() throws Exception {
        doTestBug51376(true);
    }

    private void doTestBug51376(boolean loadOnStartUp) throws Exception {
        // Test that for a servlet that was added programmatically its
        // loadOnStartup property is honored and its init() and destroy()
        // methods are called.

        // Set up a container
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        // Add ServletContainerInitializer
        Bug51376SCI sci = new Bug51376SCI(loadOnStartUp);
        ctx.addServletContainerInitializer(sci, null);

        // Start the context
        tomcat.start();

        // Stop the context
        ctx.stop();

        // Make sure that init() and destroy() were called correctly
        assertTrue(sci.getServlet().isOk());
        assertTrue(loadOnStartUp == sci.getServlet().isInitCalled());
    }

    public static final class Bug51376SCI
            implements ServletContainerInitializer {

        private Bug51376Servlet s = null;
        private boolean loadOnStartUp;

        public Bug51376SCI(boolean loadOnStartUp) {
            this.loadOnStartUp = loadOnStartUp;
        }

        private Bug51376Servlet getServlet() {
            return s;
        }

        @Override
        public void onStartup(Set<Class<?>> c, ServletContext ctx)
                throws ServletException {
            // Register and map servlet
            s = new Bug51376Servlet();
            ServletRegistration.Dynamic sr = ctx.addServlet("bug51376", s);
            sr.addMapping("/bug51376");
            if (loadOnStartUp) {
                sr.setLoadOnStartup(1);
            }
        }
    }

    public static final class Bug51376Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private Boolean initOk = null;
        private Boolean destroyOk = null;

        @Override
        public void init() {
            if (initOk == null && destroyOk == null) {
                initOk = Boolean.TRUE;
            } else {
                initOk = Boolean.FALSE;
            }
        }

        @Override
        public void destroy() {
            if (initOk.booleanValue() && destroyOk == null) {
                destroyOk = Boolean.TRUE;
            } else {
                destroyOk = Boolean.FALSE;
            }
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.getWriter().write("OK");
        }

        protected boolean isOk() {
            if (initOk != null && initOk.booleanValue() && destroyOk != null &&
                    destroyOk.booleanValue()) {
                return true;
            } else if (initOk == null && destroyOk == null) {
                return true;
            } else {
                return false;
            }
        }

        protected boolean isInitCalled() {
            return initOk != null && initOk.booleanValue();
        }
    }

    /**
     * Test case for bug 49711: HttpServletRequest.getParts does not work
     * in a filter.
     */
    @Test
    public void testBug49711() {
        Bug49711Client client = new Bug49711Client();

        // Make sure non-multipart works properly
        client.doRequest("/regular", false, false);

        assertEquals("Incorrect response for GET request",
                     "parts=0",
                     client.getResponseBody());

        client.reset();

        // Make sure regular multipart works properly
        client.doRequest("/multipart", false, true); // send multipart request

        assertEquals("Regular multipart doesn't work",
                     "parts=1",
                     client.getResponseBody());

        client.reset();

        // Make casual multipart request to "regular" servlet w/o config
        // We expect that no parts will be available
        client.doRequest("/regular", false, true); // send multipart request

        assertEquals("Incorrect response for non-configured casual multipart request",
                     "parts=0", // multipart request should be ignored
                     client.getResponseBody());

        client.reset();

        // Make casual multipart request to "regular" servlet w/config
        // We expect that the server /will/ parse the parts, even though
        // there is no @MultipartConfig
        client.doRequest("/regular", true, true); // send multipart request

        assertEquals("Incorrect response for configured casual multipart request",
                     "parts=1",
                     client.getResponseBody());

        client.reset();
    }

    private static class Bug49711Servlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
            // Just echo the parameters and values back as plain text
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");

            PrintWriter out = resp.getWriter();

            out.println("parts=" + (null == req.getParts()
                                    ? "null"
                                    : Integer.valueOf(req.getParts().size())));
        }
    }

    @MultipartConfig
    private static class Bug49711Servlet_multipart extends Bug49711Servlet {
        private static final long serialVersionUID = 1L;
    }

    /**
     * Bug 49711 test client: test for casual getParts calls.
     */
    private class Bug49711Client extends SimpleHttpClient {

        private boolean init;
        private Context context;

        private synchronized void init() throws Exception {
            if (init) return;

            Tomcat tomcat = getTomcatInstance();
            context = tomcat.addContext("", TEMP_DIR);
            Tomcat.addServlet(context, "regular", new Bug49711Servlet());
            Wrapper w = Tomcat.addServlet(context, "multipart", new Bug49711Servlet_multipart());

            // Tomcat.addServlet does not respect annotations, so we have
            // to set our own MultipartConfigElement.
            w.setMultipartConfigElement(new MultipartConfigElement(""));

            context.addServletMapping("/regular", "regular");
            context.addServletMapping("/multipart", "multipart");
            tomcat.start();

            setPort(tomcat.getConnector().getLocalPort());

            init = true;
        }

        private Exception doRequest(String uri,
                                    boolean allowCasualMultipart,
                                    boolean makeMultipartRequest) {
            try {
                init();

                context.setAllowCasualMultipartParsing(allowCasualMultipart);

                // Open connection
                connect();

                // Send specified request body using method
                String[] request;

                if(makeMultipartRequest) {
                    String boundary = "--simpleboundary";

                    String content = "--" + boundary + CRLF
                        + "Content-Disposition: form-data; name=\"name\"" + CRLF + CRLF
                        + "value" + CRLF
                        + "--" + boundary + "--" + CRLF;

                    // Re-encode the content so that bytes = characters
                    content = new String(content.getBytes("UTF-8"), "ASCII");

                    request = new String[] {
                        "POST http://localhost:" + getPort() + uri + " HTTP/1.1" + CRLF
                        + "Host: localhost" + CRLF
                        + "Connection: close" + CRLF
                        + "Content-Type: multipart/form-data; boundary=" + boundary + CRLF
                        + "Content-Length: " + content.length() + CRLF
                        + CRLF
                        + content
                        + CRLF
                    };
                }
                else
                {
                    request = new String[] {
                        "GET http://localhost:" + getPort() + uri + " HTTP/1.1" + CRLF
                        + "Host: localhost" + CRLF
                        + "Connection: close" + CRLF
                        + CRLF
                    };
                }

                setRequest(request);
                processRequest(); // blocks until response has been read

                // Close the connection
                disconnect();
            } catch (Exception e) {
                return e;
            }

            return null;
        }

        @Override
        public boolean isResponseBodyOK() {
            return false; // Don't care
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddPostConstructMethodNullClassName() {
        new StandardContext().addPostConstructMethod(null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddPostConstructMethodNullMethodName() {
        new StandardContext().addPostConstructMethod("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddPostConstructMethodConflicts() {
        StandardContext standardContext = new StandardContext();
        standardContext.addPostConstructMethod("a", "a");
        standardContext.addPostConstructMethod("a", "b");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddPreDestroyMethodNullClassName() {
        new StandardContext().addPreDestroyMethod(null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddPreDestroyMethodNullMethodName() {
        new StandardContext().addPreDestroyMethod("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddPreDestroyMethodConflicts() {
        StandardContext standardContext = new StandardContext();
        standardContext.addPreDestroyMethod("a", "a");
        standardContext.addPreDestroyMethod("a", "b");
    }

    @Test
    public void testTldListener() throws Exception {
        // Set up a container
        Tomcat tomcat = getTomcatInstance();

        File docBase = new File("test/webapp-3.0");
        Context ctx = tomcat.addContext("", docBase.getAbsolutePath());

        // Start the context
        tomcat.start();

        // Stop the context
        ctx.stop();

        String log = TesterTldListener.getLog();
        Assert.assertTrue(log, log.contains("PASS-01"));
        Assert.assertTrue(log, log.contains("PASS-02"));
        Assert.assertFalse(log, log.contains("FAIL"));
    }

    @Test
    public void testFlagFailCtxIfServletStartFails() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        File docBase = new File(System.getProperty("java.io.tmpdir"));
        StandardContext context = (StandardContext) tomcat.addContext("",
                docBase.getAbsolutePath());

        // first we test the flag itself, which can be set on the Host and
        // Context
        assertFalse(context.getComputedFailCtxIfServletStartFails());

        StandardHost host = (StandardHost) tomcat.getHost();
        host.setFailCtxIfServletStartFails(true);
        assertTrue(context.getComputedFailCtxIfServletStartFails());
        context.setFailCtxIfServletStartFails(Boolean.FALSE);
        assertFalse("flag on Context should override Host config",
                context.getComputedFailCtxIfServletStartFails());

        // second, we test the actual effect of the flag on the startup
        Wrapper servlet = Tomcat.addServlet(context, "myservlet",
                new FailingStartupServlet());
        servlet.setLoadOnStartup(1);

        tomcat.start();
        assertTrue("flag false should not fail deployment", context.getState()
                .isAvailable());

        tomcat.stop();
        assertFalse(context.getState().isAvailable());

        host.removeChild(context);
        context = (StandardContext) tomcat.addContext("",
                docBase.getAbsolutePath());
        servlet = Tomcat.addServlet(context, "myservlet",
                new FailingStartupServlet());
        servlet.setLoadOnStartup(1);
        tomcat.start();
        assertFalse("flag true should fail deployment", context.getState()
                .isAvailable());
    }

    private class FailingStartupServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        public void init() throws ServletException {
            throw new ServletException("failing on purpose");
        }

    }

    @Test
    public void testBug56903() {
        Context context = new StandardContext();

        context.setResourceOnlyServlets("a,b,c");
        Assert.assertThat(Arrays.asList(context.getResourceOnlyServlets().split(",")),
                CoreMatchers.hasItems("a", "b", "c"));
    }

    @Test
    public void testSetPath() {
        testSetPath("", "");
        testSetPath("/foo", "/foo");
        testSetPath("/foo/bar", "/foo/bar");
        testSetPath(null, "");
        testSetPath("/", "");
        testSetPath("foo", "/foo");
        testSetPath("/foo/bar/", "/foo/bar");
        testSetPath("foo/bar/", "/foo/bar");
    }

    private void testSetPath(String value, String expectedValue) {
        StandardContext context = new StandardContext();
        context.setPath(value);
        Assert.assertEquals(expectedValue, context.getPath());
    }
}
