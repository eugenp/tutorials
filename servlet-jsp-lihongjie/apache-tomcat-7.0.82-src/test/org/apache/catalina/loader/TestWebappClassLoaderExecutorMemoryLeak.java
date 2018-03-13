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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;

public class TestWebappClassLoaderExecutorMemoryLeak extends TomcatBaseTest {

    @Test
    public void testTimerThreadLeak() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        if (ctx instanceof StandardContext) {
            ((StandardContext) ctx).setClearReferencesStopThreads(true);
        }

        ExecutorServlet executorServlet = new ExecutorServlet();
        Tomcat.addServlet(ctx, "taskServlet", executorServlet);
        ctx.addServletMapping("/", "taskServlet");

        tomcat.start();

        // This will trigger the timer & thread creation
        getUrl("http://localhost:" + getPort() + "/");

        // Stop the context
        ctx.stop();

        // If the thread still exists, we have a thread/memory leak
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            // ignore
        }

        Assert.assertTrue(executorServlet.tpe.isShutdown());
        Assert.assertTrue(executorServlet.tpe.isTerminated());
    }

    static class ExecutorServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        int nTasks = 5;
        long n = 1000L;
        int tpSize = 10;

        public volatile ThreadPoolExecutor tpe;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            resp.getWriter().println(
                    "The current thread served " + this + " servlet");
            tpe = new ThreadPoolExecutor(tpSize, tpSize, 50000L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

            Task[] tasks = new Task[nTasks];
            for (int i = 0; i < nTasks; i++) {
                tasks[i] = new Task("Task " + i);
                tpe.execute(tasks[i]);
            }
            resp.getWriter().println("Started " + nTasks +
                    " never ending tasks using the ThreadPoolExecutor");
            resp.getWriter().flush();
        }

        class Task implements Runnable {

            String _id;

            public Task(String id) {
                this._id = id;
            }

            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(20000);
                        System.out.println(Thread.currentThread().getClass()
                                + " [" + Thread.currentThread().getName()
                                + "] executing " + this._id);
                    }
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getClass() + " ["
                            + Thread.currentThread().getName() + "] EXITING");
                }
            }
        }
    }
}
