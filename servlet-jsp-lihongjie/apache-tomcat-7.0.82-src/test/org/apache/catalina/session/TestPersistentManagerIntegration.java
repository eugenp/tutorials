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
package org.apache.catalina.session;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Session;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.catalina.valves.PersistentValve;

public class TestPersistentManagerIntegration extends TomcatBaseTest {

    private final String ACTIVITY_CHECK = "org.apache.catalina.session.StandardSession.ACTIVITY_CHECK";

    private String oldActivityCheck;

    /**
     * As documented in config/manager.html, the "ACTIVITY_CHECK" property must
     * be set to "true" for PersistentManager to function correctly.
     */
    @Before
    public void setActivityCheck() {
        oldActivityCheck = System.setProperty(ACTIVITY_CHECK, "true");
    }

    @After
    public void resetActivityCheck() {
        if (oldActivityCheck != null) {
            System.setProperty(ACTIVITY_CHECK, oldActivityCheck);
        } else {
            System.clearProperty(ACTIVITY_CHECK);
        }
    }

    /**
     * Wait enough for the system clock to update its value. On some systems
     * (e.g. old Windows) the clock granularity is tens of milliseconds.
     */
    private void waitForClockUpdate() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int waitTime = 1;
        do {
            Thread.sleep(waitTime);
            waitTime *= 10;
        } while (System.currentTimeMillis() == startTime);
    }

    /**
     * Wait while session access counter has a positive value.
     */
    private void waitWhileSessionIsActive(StandardSession session)
            throws InterruptedException {
        long maxWaitTime = System.currentTimeMillis() + 60000;
        AtomicInteger accessCount = session.accessCount;
        while (accessCount.get() > 0) {
            // Wait until o.a.c.connector.Request.recycle() completes,
            // as it updates lastAccessedTime.
            Assert.assertTrue(System.currentTimeMillis() < maxWaitTime);
            Thread.sleep(200);
        }
    }

    @Test
    public void noSessionCreate_57637() throws IOException, LifecycleException {

        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);
        ctx.setDistributable(true);

        Tomcat.addServlet(ctx, "DummyServlet", new DummyServlet());
        ctx.addServletMapping("/dummy", "DummyServlet");

        PersistentManager manager = new PersistentManager();
        TesterStore store = new TesterStore();

        manager.setStore(store);
        manager.setMaxIdleBackup(0);
        ctx.setManager(manager);
        ctx.addValve(new PersistentValve());
        tomcat.start();
        Assert.assertEquals(manager.getActiveSessions(), 0);
        Assert.assertTrue("No sessions managed", manager.getSessionIdsFull().isEmpty());
        Assert.assertEquals(
                "NO_SESSION",
                getUrl(
                        "http://localhost:" + getPort()
                                + "/dummy?no_create_session=true").toString());
        Assert.assertEquals(manager.getActiveSessions(), 0);
        Assert.assertTrue("No sessions where created", manager.getSessionIdsFull().isEmpty());
    }

    @Test
    public void testCreateSessionAndPassivate() throws IOException, LifecycleException, ClassNotFoundException {

        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);
        ctx.setDistributable(true);

        Tomcat.addServlet(ctx, "DummyServlet", new DummyServlet());
        ctx.addServletMapping("/dummy", "DummyServlet");

        PersistentManager manager = new PersistentManager();
        TesterStore store = new TesterStore();

        manager.setStore(store);
        manager.setMaxIdleBackup(0);
        ctx.setManager(manager);
        ctx.addValve(new PersistentValve());
        tomcat.start();
        Assert.assertEquals("No active sessions", manager.getActiveSessions(), 0);
        Assert.assertTrue("No sessions managed", manager.getSessionIdsFull().isEmpty());
        String sessionId = getUrl(
                "http://localhost:" + getPort()
                        + "/dummy?no_create_session=false").toString();
        Assert.assertNotNull("Session is stored", store.load(sessionId));
        Assert.assertEquals("All sessions are passivated", manager.getActiveSessions(), 0);
        Assert.assertTrue("One session was created", !manager.getSessionIdsFull().isEmpty());
    }

    @Test
    public void backsUpOnce_56698() throws IOException, LifecycleException,
            InterruptedException {

        // Setup Tomcat instance
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.setDistributable(true);

        Tomcat.addServlet(ctx, "DummyServlet", new DummyServlet());
        ctx.addServletMapping("/dummy", "DummyServlet");

        PersistentManager manager = new PersistentManager();
        TesterStore store = new TesterStore();

        manager.setStore(store);
        manager.setMaxIdleBackup(0);
        ctx.setManager(manager);
        tomcat.start();
        String sessionId = getUrl("http://localhost:" + getPort() + "/dummy")
                .toString();

        // Note: PersistenceManager.findSession() silently updates
        // session.lastAccessedTime, so call it only once before other work.
        Session session = manager.findSession(sessionId);

        // Wait until request processing ends, as Request.recycle() updates
        // session.lastAccessedTime via session.endAccess().
        waitWhileSessionIsActive((StandardSession) session);

        long lastAccessedTime = session.getLastAccessedTimeInternal();

        // Session should be idle at least for 0 second (maxIdleBackup)
        // to be eligible for persistence, thus no need to wait.

        // Waiting a bit, to catch changes in last accessed time of a session
        waitForClockUpdate();

        manager.processPersistenceChecks();
        Assert.assertEquals(Arrays.asList(sessionId), store.getSavedIds());
        Assert.assertEquals(lastAccessedTime, session.getLastAccessedTimeInternal());

        // session was not accessed, so no save will be performed
        waitForClockUpdate();
        manager.processPersistenceChecks();
        Assert.assertEquals(Arrays.asList(sessionId), store.getSavedIds());
        Assert.assertEquals(lastAccessedTime, session.getLastAccessedTimeInternal());

        // access session
        session.access();
        session.endAccess();

        // session was accessed, so it will be saved once again
        manager.processPersistenceChecks();
        Assert.assertEquals(Arrays.asList(sessionId, sessionId),
                store.getSavedIds());

        // session was not accessed, so once again no save will happen
        manager.processPersistenceChecks();
        Assert.assertEquals(Arrays.asList(sessionId, sessionId),
                store.getSavedIds());
    }

    private static class DummyServlet extends HttpServlet {

        private static final long serialVersionUID = -3696433049266123995L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            boolean createSession = !Boolean.parseBoolean(req
                            .getParameter("no_create_session"));
            HttpSession session = req.getSession(createSession);
            if (session == null) {
                resp.getWriter().print("NO_SESSION");
            } else {
                String id = session.getId();
                resp.getWriter().print(id);
            }
        }

    }
}
