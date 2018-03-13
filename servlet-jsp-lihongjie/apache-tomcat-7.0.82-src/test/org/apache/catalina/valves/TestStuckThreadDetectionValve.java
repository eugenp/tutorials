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
package org.apache.catalina.valves;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestStuckThreadDetectionValve extends TomcatBaseTest {
    private StandardContext context;
    private Tomcat tomcat;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        tomcat = getTomcatInstance();
        File docBase = new File(System.getProperty("java.io.tmpdir"));
        context = (StandardContext) tomcat.addContext("",
                docBase.getAbsolutePath());
    }

    @Test
    public void testDetection() throws Exception {
        // second, we test the actual effect of the flag on the startup
        StuckingServlet stuckingServlet = new StuckingServlet(8000L);
        Wrapper servlet = Tomcat.addServlet(context, "myservlet",
                stuckingServlet);
        servlet.addMapping("/myservlet");

        StuckThreadDetectionValve valve = new StuckThreadDetectionValve();
        valve.setThreshold(2);
        context.addValve(valve);
        context.setBackgroundProcessorDelay(1);
        tomcat.start();

        Assert.assertEquals(0, valve.getStuckThreadIds().length);

        final ByteChunk result = new ByteChunk();
        Thread asyncThread = new Thread() {
            @Override
            public void run() {
                try {
                    getUrl("http://localhost:" + getPort() + "/myservlet",
                            result, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        asyncThread.start();
        try {
            Thread.sleep(500L);
            Assert.assertEquals(0, valve.getStuckThreadIds().length);

            Thread.sleep(5000L);
            Assert.assertEquals(1, valve.getStuckThreadIds().length);
        } finally {
            asyncThread.join(20000);
            // check that we did not reach the join timeout
            Assert.assertFalse(asyncThread.isAlive());
        }
        Assert.assertFalse(stuckingServlet.wasInterrupted);
        Assert.assertTrue(result.toString().startsWith("OK"));
    }

    @Test
    public void testInterruption() throws Exception {
        // second, we test the actual effect of the flag on the startup
        StuckingServlet stuckingServlet = new StuckingServlet(
                TimeUnit.SECONDS.toMillis(20L));
        Wrapper servlet = Tomcat.addServlet(context, "myservlet",
                stuckingServlet);
        servlet.addMapping("/myservlet");

        StuckThreadDetectionValve valve = new StuckThreadDetectionValve();
        valve.setThreshold(2);
        valve.setInterruptThreadThreshold(5);
        context.addValve(valve);
        context.setBackgroundProcessorDelay(1);
        tomcat.start();

        Assert.assertEquals(0, valve.getStuckThreadIds().length);

        final ByteChunk result = new ByteChunk();
        Thread asyncThread = new Thread() {
            @Override
            public void run() {
                try {
                    getUrl("http://localhost:" + getPort() + "/myservlet",
                            result, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        asyncThread.start();
        try {
            Thread.sleep(4000L);
            Assert.assertEquals(1, valve.getStuckThreadIds().length);

        } finally {
            asyncThread.join(20000);
            // check that we did not reach the join timeout
            Assert.assertFalse(asyncThread.isAlive());
        }
        Assert.assertTrue(stuckingServlet.wasInterrupted);
        Assert.assertEquals(0, valve.getStuckThreadIds().length);
        Assert.assertTrue(result.toString().startsWith("OK"));
    }

    private class StuckingServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;
        private final long delay;
        boolean wasInterrupted = false;

        StuckingServlet(long delay) {
            this.delay = delay;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                wasInterrupted = true;
            }
            resp.setContentType("text/plain");
            resp.getWriter().println("OK");
        }

    }
}
