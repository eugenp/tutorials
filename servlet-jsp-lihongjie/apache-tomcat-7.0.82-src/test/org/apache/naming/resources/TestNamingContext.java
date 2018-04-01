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
package org.apache.naming.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.startup.ExpandWar;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestNamingContext extends TomcatBaseTest {

    @Test
    public void testLookupSingletonResource() throws Exception {
        doTestLookup(true);
    }

    @Test
    public void testLookupNonSingletonResource() throws Exception {
        doTestLookup(false);
    }
    
    public void doTestLookup(boolean useSingletonResource) throws Exception {
        Tomcat tomcat = getTomcatInstance();
        tomcat.enableNaming();

        // No file system docBase required
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);
        
        // Create the resource
        ContextResource cr = new ContextResource();
        cr.setName("list/foo");
        cr.setType("org.apache.naming.resources.TesterObject");
        cr.setProperty("factory", "org.apache.naming.resources.TesterFactory");
        cr.setSingleton(useSingletonResource);
        ctx.getNamingResources().addResource(cr);
        
        // Map the test Servlet
        Bug49994Servlet bug49994Servlet = new Bug49994Servlet();
        Tomcat.addServlet(ctx, "bug49994Servlet", bug49994Servlet);
        ctx.addServletMapping("/", "bug49994Servlet");

        tomcat.start();

        ByteChunk bc = getUrl("http://localhost:" + getPort() + "/");
        
        String expected;
        if (useSingletonResource) {
            expected = "EQUAL";
        } else {
            expected = "NOTEQUAL";
        }
        assertEquals(expected, bc.toString());

    }

    @Test
    public void testAliases() throws Exception {
        // Some sample text
        String foxText = "The quick brown fox jumps over the lazy dog";
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

        // Set up a temporary docBase and some alternates that we can
        // set up as aliases.
        File tmpDir = new File(getTemporaryDirectory(),
                               "tomcat-unit-test." + TestNamingContext.class.getName());

        // Make sure we've got a clean slate
        ExpandWar.delete(tmpDir);

        File docBase = new File(tmpDir, "docBase");
        File alternate1 = new File(tmpDir, "alternate1");
        File alternate2 = new File(tmpDir, "alternate2");

        // Register for clean-up
        addDeleteOnTearDown(tmpDir);

        if(!tmpDir.mkdirs())
            throw new IOException("Could not create temp directory " + tmpDir);
        if(!docBase.mkdir())
            throw new IOException("Could not create temp directory " + docBase);
        if(!alternate1.mkdir())
            throw new IOException("Could not create temp directory " + alternate1);
        if(!alternate2.mkdir())
            throw new IOException("Could not create temp directory " + alternate2);

        // Create a file in each alternate directory that we can attempt to access
        FileOutputStream fos = new FileOutputStream(new File(alternate1, "test1.txt"));
        try {
            fos.write(foxText.getBytes("UTF-8"));
            fos.flush();
        } finally {
            fos.close();
        }

        fos = new FileOutputStream(new File(alternate2, "test2.txt"));
        try {
            fos.write(loremIpsum.getBytes("UTF-8"));
            fos.flush();
        } finally {
            fos.close();
        }

        // Finally, create the Context
        FileDirContext ctx = new FileDirContext();
        ctx.setDocBase(docBase.getCanonicalPath());
        ctx.setAliases("/a1=" + alternate1.getCanonicalPath()
                       +",/a2=" + alternate2.getCanonicalPath());

        // Check first alias
        Object file = ctx.lookup("/a1/test1.txt");

        Assert.assertNotNull(file);
        Assert.assertTrue(file instanceof Resource);

        byte[] buffer = new byte[4096];
        Resource res = (Resource)file;

        InputStream is = res.streamContent();
        int len;
        try {
            len = is.read(buffer);
        } finally {
            is.close();
        }
        String contents = new String(buffer, 0, len, "UTF-8");

        assertEquals(foxText, contents);

        // Check second alias
        file = ctx.lookup("/a2/test2.txt");

        Assert.assertNotNull(file);
        Assert.assertTrue(file instanceof Resource);

        res = (Resource)file;
        is = res.streamContent();
        try {
            len = is.read(buffer);
        } finally {
            is.close();
        }
        contents = new String(buffer, 0, len, "UTF-8");

        assertEquals(loremIpsum, contents);

        // Test aliases with spaces around the separators
        ctx.setAliases("   /a1= " + alternate1.getCanonicalPath()
                       + "\n\n"
                       +", /a2 =\n" + alternate2.getCanonicalPath()
                       + ",");

        // Check first alias
        file = ctx.lookup("/a1/test1.txt");

        Assert.assertNotNull(file);
        Assert.assertTrue(file instanceof Resource);

        res = (Resource)file;
        is = res.streamContent();
        try {
            len = is.read(buffer);
        } finally {
            is.close();
        }
        contents = new String(buffer, 0, len, "UTF-8");

        assertEquals(foxText, contents);

        // Check second alias
        file = ctx.lookup("/a2/test2.txt");

        Assert.assertNotNull(file);
        Assert.assertTrue(file instanceof Resource);

        res = (Resource)file;
        is = res.streamContent();
        try {
            len = is.read(buffer);
        } finally {
            is.close();
        }
        contents = new String(buffer, 0, len, "UTF-8");

        assertEquals(loremIpsum, contents);
    }

    public static final class Bug49994Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            resp.setContentType("text/plain;UTF-8");
            PrintWriter out = resp.getWriter();

            try {
                Context ctx = new InitialContext();
                Object obj1 = ctx.lookup("java:comp/env/list/foo");
                Object obj2 = ctx.lookup("java:comp/env/list/foo");
                if (obj1 == obj2) {
                    out.print("EQUAL");
                } else {
                    out.print("NOTEQUAL");
                }
            } catch (NamingException ne) {
                ne.printStackTrace(out);
            }
        }
    }

    @Test
    public void testListBindings() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        tomcat.enableNaming();

        // No file system docBase required
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);
        
        // Create the resource
        ContextResource cr = new ContextResource();
        cr.setName("list/foo");
        cr.setType("org.apache.naming.resources.TesterObject");
        cr.setProperty("factory", "org.apache.naming.resources.TesterFactory");
        ctx.getNamingResources().addResource(cr);
        
        // Map the test Servlet
        Bug23950Servlet bug23950Servlet = new Bug23950Servlet();
        Tomcat.addServlet(ctx, "bug23950Servlet", bug23950Servlet);
        ctx.addServletMapping("/", "bug23950Servlet");

        tomcat.start();

        ByteChunk bc = getUrl("http://localhost:" + getPort() + "/");
        assertEquals("org.apache.naming.resources.TesterObject", bc.toString());
    }
    
    public static final class Bug23950Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            resp.setContentType("text/plain;UTF-8");
            PrintWriter out = resp.getWriter();

            try {
                Context ctx = new InitialContext();
                NamingEnumeration<Binding> enm =
                    ctx.listBindings("java:comp/env/list");
                while (enm.hasMore()) {
                    Binding b = enm.next();
                    out.print(b.getObject().getClass().getName());
                }
            } catch (NamingException ne) {
                ne.printStackTrace(out);
            }
        }
    }

    @Test
    public void testBeanFactory() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        tomcat.enableNaming();

        // No file system docBase required
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);
        
        // Create the resource
        ContextResource cr = new ContextResource();
        cr.setName("bug50351");
        cr.setType("org.apache.naming.resources.TesterObject");
        cr.setProperty("factory", "org.apache.naming.factory.BeanFactory");
        cr.setProperty("foo", "value");
        ctx.getNamingResources().addResource(cr);
        
        // Map the test Servlet
        Bug50351Servlet bug50351Servlet = new Bug50351Servlet();
        Tomcat.addServlet(ctx, "bug50351Servlet", bug50351Servlet);
        ctx.addServletMapping("/", "bug50351Servlet");

        tomcat.start();

        ByteChunk bc = getUrl("http://localhost:" + getPort() + "/");
        assertEquals("value", bc.toString());
    }

    public static final class Bug50351Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            resp.setContentType("text/plain;UTF-8");
            PrintWriter out = resp.getWriter();

            try {
                Context ctx = new InitialContext();
                Object obj = ctx.lookup("java:comp/env/bug50351");
                TesterObject to = (TesterObject) obj;
                out.print(to.getFoo());
            } catch (NamingException ne) {
                ne.printStackTrace(out);
            }
        }
    }
    
    @Test
    public void testBug51744a() throws Exception {
        doTestBug51744(true);
    }

    @Test
    public void testBug51744b() throws Exception {
        doTestBug51744(false);
    }

    private void doTestBug51744(boolean exceptionOnFailedWrite)
            throws Exception {
        Tomcat tomcat = getTomcatInstance();
        tomcat.enableNaming();

        // No file system docBase required
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);
        
        ctx.setJndiExceptionOnFailedWrite(exceptionOnFailedWrite);

        // Map the test Servlet
        Bug51744Servlet bug51744Servlet = new Bug51744Servlet();
        Tomcat.addServlet(ctx, "bug51744Servlet", bug51744Servlet);
        ctx.addServletMapping("/", "bug51744Servlet");

        tomcat.start();

        ByteChunk bc = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort() + "/", bc, null);
        assertEquals(200, rc);
        assertTrue(bc.toString().contains(Bug51744Servlet.EXPECTED));
        if (exceptionOnFailedWrite) {
            assertTrue(bc.toString().contains(Bug51744Servlet.ERROR_MESSAGE));
        }
    }

    public static final class Bug51744Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        public static final String EXPECTED = "TestValue";
        public static final String ERROR_MESSAGE = "Error";

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            resp.setContentType("text/plain;UTF-8");
            PrintWriter out = resp.getWriter();

            try {
                Context ctx1 = new InitialContext();
                Context env1 = (Context) ctx1.lookup("java:comp/env");
                env1.addToEnvironment("TestName", EXPECTED);

                out.print(env1.getEnvironment().get("TestName"));

                try {
                    env1.close();
                } catch (NamingException ne) {
                    out.print(ERROR_MESSAGE);
                }
            } catch (NamingException ne) {
                ne.printStackTrace(out);
            }
        }
    }

    @Test
    public void testBug52830() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        tomcat.enableNaming();

        // No file system docBase required
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);

        // Create the resource
        ContextEnvironment env = new ContextEnvironment();
        env.setName("boolean");
        env.setType(Boolean.class.getName());
        env.setValue("true");
        ctx.getNamingResources().addEnvironment(env);

        // Map the test Servlet
        Bug52830Servlet bug52830Servlet = new Bug52830Servlet();
        Tomcat.addServlet(ctx, "bug52830Servlet", bug52830Servlet);
        ctx.addServletMapping("/", "bug52830Servlet");

        tomcat.start();

        ByteChunk bc = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort() + "/", bc, null);
        assertEquals(200, rc);
        assertTrue(bc.toString().contains("truetrue"));
    }

    public static final class Bug52830Servlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        public static final String JNDI_NAME = "java:comp/env/boolean";

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            resp.setContentType("text/plain;UTF-8");
            PrintWriter out = resp.getWriter();

            try {
                Context initCtx = new InitialContext();

                Boolean b1 = (Boolean) initCtx.lookup(JNDI_NAME);
                Boolean b2 = (Boolean) initCtx.lookup(
                        new CompositeName(JNDI_NAME));

                out.print(b1);
                out.print(b2);

            } catch (NamingException ne) {
                throw new ServletException(ne);
            }
        }
    }

    @Test
    public void testBug53465() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        tomcat.enableNaming();

        File appDir =
            new File("test/webapp-3.0");
        // app dir is relative to server home
        org.apache.catalina.Context ctxt =
                tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk bc = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort() +
                "/test/bug5nnnn/bug53465.jsp", bc, null);

        Assert.assertEquals(HttpServletResponse.SC_OK, rc);
        Assert.assertTrue(bc.toString().contains("<p>10</p>"));

        ContextEnvironment ce =
                ctxt.getNamingResources().findEnvironment("bug53465");
        Assert.assertEquals("Bug53465MappedName", ce.getProperty("mappedName"));
    }
}
