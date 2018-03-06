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
package org.apache.catalina.connector;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.startup.SimpleHttpClient;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;

public class TestMaxConnections extends TomcatBaseTest {
    private static final int MAX_CONNECTIONS = 3;
    public static final int soTimeout = 5000;
    public static final int connectTimeout = 1000;

    @Test
    public void testConnector() throws Exception {
        init();
        ConnectThread[] t = new ConnectThread[10];
        for (int i=0; i<t.length; i++) {
            t[i] = new ConnectThread();
            t[i].setName("ConnectThread["+i+"]");
        }
        for (int i=0; i<t.length; i++) {
            t[i].start();
            Thread.sleep(50);
        }
        for (int i=0; i<t.length; i++) {
            t[i].join();
        }

        Assert.assertEquals(MAX_CONNECTIONS, SimpleServlet.getMaxConnections());
    }

    private class ConnectThread extends Thread {
        @Override
        public void run() {
            try {
                TestClient client = new TestClient();
                client.doHttp10Request();
            } catch (Exception x) {
                // NO-OP. Some connections are expected to fail.
            }
        }
    }


    private synchronized void init() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context root = tomcat.addContext("", null);
        Tomcat.addServlet(root, "Simple", new SimpleServlet());
        root.addServletMapping("/test", "Simple");
        tomcat.getConnector().setProperty("maxKeepAliveRequests", "1");
        tomcat.getConnector().setProperty("maxThreads", "10");
        tomcat.getConnector().setProperty("soTimeout", "20000");
        tomcat.getConnector().setProperty("keepAliveTimeout", "50000");
        tomcat.getConnector().setProperty(
                "maxConnections", Integer.toString(MAX_CONNECTIONS));
        tomcat.getConnector().setProperty("acceptCount", "1");
        tomcat.start();
    }

    private class TestClient extends SimpleHttpClient {

        private void doHttp10Request() throws Exception {
            setPort(getPort());

            long start = System.currentTimeMillis();
            // Open connection
            connect(connectTimeout,soTimeout);

            // Send request in two parts
            String[] request = new String[1];
            request[0] =
                "GET /test HTTP/1.0" + CRLF + CRLF;
            setRequest(request);
            boolean passed = false;
            processRequest(false); // blocks until response has been read
            long stop = System.currentTimeMillis();
            log.info(Thread.currentThread().getName()+" Request complete:"+(stop-start)+" ms.");
            passed = (this.readLine()==null);
            // Close the connection
            disconnect();
            reset();
            Assert.assertTrue(passed);
        }

        @Override
        public boolean isResponseBodyOK() {
            return true;
        }
    }


    private static class SimpleServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        private static int currentConnections = 0;
        private static int maxConnections = 0;

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            increment();

            try {
                Thread.sleep(TestMaxConnections.soTimeout*4/5);
            }catch (InterruptedException x) {

            }
            resp.setContentLength(0);
            resp.flushBuffer();

            decrement();
        }

        private static synchronized void increment() {
            currentConnections++;
            if (currentConnections > maxConnections) {
                maxConnections = currentConnections;
            }
        }

        private static synchronized void decrement() {
            currentConnections--;
        }


        public static synchronized int getMaxConnections() {
            return maxConnections;
        }
    }
}
