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
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestContextConfig extends TomcatBaseTest {

    @Test
    public void testOverrideWithSCIDefaultName() throws Exception {
        doTestOverrideDefaultServletWithSCI("default");
    }

    @Test
    public void testOverrideWithSCIDefaultMapping() throws Exception {
        doTestOverrideDefaultServletWithSCI("anything");
    }

    private void doTestOverrideDefaultServletWithSCI(String servletName)
            throws Exception{

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        StandardContext ctxt = (StandardContext) tomcat.addContext(null,
                "/test", appDir.getAbsolutePath());
        ctxt.setDefaultWebXml(new File("conf/web.xml").getAbsolutePath());
        ctxt.addLifecycleListener(new ContextConfig());

        ctxt.addServletContainerInitializer(
                new CustomDefaultServletSCI(servletName), null);

        tomcat.start();

        assertPageContains("/test", "OK - Custom default Servlet");
    }

    @Test
    public void testBug51396() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =  new File("test/webapp-3.0-fragments");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        assertPageContains("/test/bug51396.jsp", "<p>OK</p>");
    }

    @Test
    public void testBug53574() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        assertPageContains("/test/bug53574", "OK");
    }

    @Test
    public void testBug54262() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0-fragments-empty-absolute-ordering");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        assertPageContains("/test/resourceA.jsp",
                "resourceA.jsp in resources.jar");
        assertPageContains("/test/resources/HelloWorldExample",
                null, HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void testBug54379() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0-fragments");
        Context context =
                tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        Tomcat.addServlet(context, "TestServlet",
                "org.apache.catalina.startup.TesterServletWithLifeCycleMethods");
        context.addServletMapping("/testServlet", "TestServlet");

        tomcat.enableNaming();

        tomcat.start();

        assertPageContains("/test/testServlet", "postConstruct1()");
    }

    @Test
    public void testBug54448and54450() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0-fragments");
        Context context = tomcat.addWebapp(null, "/test",
                appDir.getAbsolutePath());

        Tomcat.addServlet(context, "TestServlet",
                "org.apache.catalina.startup.TesterServletWithAnnotations");
        context.addServletMapping("/testServlet", "TestServlet");

        tomcat.enableNaming();

        tomcat.start();

        assertPageContains("/test/testServlet",
                "envEntry1: 1 envEntry2: 2 envEntry3: 33 envEntry4: 4 envEntry5: 55 envEntry6: 66");
    }

    @Test
    public void testBug55210() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0-fragments");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        assertPageContains("/test/TesterServlet1", "OK");
        assertPageContains("/test/TesterServlet2", "OK");
    }

    private static class CustomDefaultServletSCI
            implements ServletContainerInitializer {

        private String servletName;

        public CustomDefaultServletSCI(String servletName) {
            this.servletName = servletName;
        }

        @Override
        public void onStartup(Set<Class<?>> c, ServletContext ctx)
                throws ServletException {
            Servlet s = new CustomDefaultServlet();
            ServletRegistration.Dynamic r = ctx.addServlet(servletName, s);
            r.addMapping("/");
        }

    }

    private static class CustomDefaultServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            resp.setContentType("text/plain");
            resp.getWriter().print("OK - Custom default Servlet");
        }
    }

    private void assertPageContains(String pageUrl, String expectedBody)
            throws IOException {
        assertPageContains(pageUrl, expectedBody, HttpServletResponse.SC_OK);
    }

    private void assertPageContains(String pageUrl, String expectedBody,
            int expectedStatus) throws IOException {
        ByteChunk res = new ByteChunk();
        int sc = getUrl("http://localhost:" + getPort() + pageUrl, res, null);

        Assert.assertEquals(expectedStatus, sc);

        if (expectedStatus == HttpServletResponse.SC_OK) {
            String result = res.toString();
            Assert.assertTrue(result, result.indexOf(expectedBody) > -1);
        }
    }
}
