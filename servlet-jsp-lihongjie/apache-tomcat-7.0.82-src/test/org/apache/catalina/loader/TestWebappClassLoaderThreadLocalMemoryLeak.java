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
package org.apache.catalina.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.core.JreMemoryLeakPreventionListener;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.unittest.TesterLogValidationFilter;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

/*
 * These unit tests are ignored by default as they are not reliable. They have
 * been failing regularly on Gump for some time and have recently started to
 * fail regularly on markt's laptop.
 *
 * The problem is that the ThreadLocal Maps are affected by GC. If GC occurs at
 * the wrong point, the leaking ThreadLocal will be cleaned up and the test will
 * fail. It is not possible to force the test to pass without effectively
 * changing the nature of the test so it no longer tests detection of leaks via
 * ThreadLocals.
 *
 * The test has been left in place since it will work reasonably reliably on
 * most systems (just not all and particularly some of the ASF's CI systems) and
 * still may be useful if a bug is reported in this area in the future.
 */
@Ignore
public class TestWebappClassLoaderThreadLocalMemoryLeak extends TomcatBaseTest {

    @Test
    public void testThreadLocalLeak1() throws Exception {

        Tomcat tomcat = getTomcatInstance();
        // Need to make sure we see a leak for the right reasons
        tomcat.getServer().addLifecycleListener(
                new JreMemoryLeakPreventionListener());

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "leakServlet1",
                "org.apache.tomcat.unittest.TesterLeakingServlet1");
        ctx.addServletMapping("/leak1", "leakServlet1");

        tomcat.start();

        Executor executor = tomcat.getConnector().getProtocolHandler().getExecutor();
        ((ThreadPoolExecutor) executor).setThreadRenewalDelay(-1);

        // Configure logging filter to check leak message appears
        TesterLogValidationFilter f = TesterLogValidationFilter.add(null,
                "The web application [] created a ThreadLocal with key of", null,
                "org.apache.catalina.loader.WebappClassLoaderBase");

        // Need to force loading of all web application classes via the web
        // application class loader
        loadClass("TesterCounter",
                (WebappClassLoaderBase) ctx.getLoader().getClassLoader());
        loadClass("TesterLeakingServlet1",
                (WebappClassLoaderBase) ctx.getLoader().getClassLoader());

        // This will trigger the ThreadLocal creation
        int rc = getUrl("http://localhost:" + getPort() + "/leak1",
                new ByteChunk(), null);

        // Make sure request is OK
        Assert.assertEquals(HttpServletResponse.SC_OK, rc);

        // Destroy the context
        ctx.stop();
        tomcat.getHost().removeChild(ctx);
        ctx = null;

        // Make sure we have a memory leak
        String[] leaks = ((StandardHost) tomcat.getHost())
                .findReloadedContextMemoryLeaks();
        Assert.assertNotNull(leaks);
        Assert.assertTrue(leaks.length > 0);

        // Make sure the message was logged
        Assert.assertEquals(1, f.getMessageCount());
    }


    @Test
    public void testThreadLocalLeak2() throws Exception {

        Tomcat tomcat = getTomcatInstance();
        // Need to make sure we see a leak for the right reasons
        tomcat.getServer().addLifecycleListener(
                new JreMemoryLeakPreventionListener());

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "leakServlet2",
                "org.apache.tomcat.unittest.TesterLeakingServlet2");
        ctx.addServletMapping("/leak2", "leakServlet2");

        tomcat.start();

        Executor executor = tomcat.getConnector().getProtocolHandler().getExecutor();
        ((ThreadPoolExecutor) executor).setThreadRenewalDelay(-1);

        // Configure logging filter to check leak message appears
        TesterLogValidationFilter f = TesterLogValidationFilter.add(null,
                "The web application [] created a ThreadLocal with key of", null,
                "org.apache.catalina.loader.WebappClassLoaderBase");

        // Need to force loading of all web application classes via the web
        // application class loader
        loadClass("TesterCounter",
                (WebappClassLoaderBase) ctx.getLoader().getClassLoader());
        loadClass("TesterThreadScopedHolder",
                (WebappClassLoaderBase) ctx.getLoader().getClassLoader());
        loadClass("TesterLeakingServlet2",
                (WebappClassLoaderBase) ctx.getLoader().getClassLoader());

        // This will trigger the ThreadLocal creation
        int rc = getUrl("http://localhost:" + getPort() + "/leak2",
                new ByteChunk(), null);

        // Make sure request is OK
        Assert.assertEquals(HttpServletResponse.SC_OK, rc);

        // Destroy the context
        ctx.stop();
        tomcat.getHost().removeChild(ctx);
        ctx = null;

        // Make sure we have a memory leak
        String[] leaks = ((StandardHost) tomcat.getHost())
                .findReloadedContextMemoryLeaks();
        Assert.assertNotNull(leaks);
        Assert.assertTrue(leaks.length > 0);

        // Make sure the message was logged
        Assert.assertEquals(1, f.getMessageCount());
    }


    /**
     * Utility method to ensure that classes are loaded by the
     * WebappClassLoader. We can't just create classes since they will be loaded
     * by the current class loader rather than the WebappClassLoader. This would
     * mean that no leak occurred making the test for a leak rather pointless
     * So, we load the bytes via the current class loader but define the class
     * with the WebappClassLoader.
     *
     * This method assumes that all classes are in the current package.
     */
    private void loadClass(String name, WebappClassLoaderBase cl) throws Exception {

        InputStream is = cl.getResourceAsStream(
                "org/apache/tomcat/unittest/" + name + ".class");
        // We know roughly how big the class will be (~ 1K) so allow 2k as a
        // starting point
        byte[] classBytes = new byte[2048];
        int offset = 0;
        try {
            int read = is.read(classBytes, offset, classBytes.length-offset);
            while (read > -1) {
                offset += read;
                if (offset == classBytes.length) {
                    // Buffer full - double size
                    byte[] tmp = new byte[classBytes.length * 2];
                    System.arraycopy(classBytes, 0, tmp, 0, classBytes.length);
                    classBytes = tmp;
                }
                read = is.read(classBytes, offset, classBytes.length-offset);
            }
            Class<?> lpClass = cl.doDefineClass(
                    "org.apache.tomcat.unittest." + name, classBytes, 0,
                    offset, cl.getClass().getProtectionDomain());
            // Make sure we can create an instance
            Object obj = lpClass.newInstance();
            obj.toString();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    // Ignore
                }
            }
        }
    }
}