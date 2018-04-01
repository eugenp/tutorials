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
package org.apache.tomcat.websocket.server;

import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContextEvent;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.websocket.WebSocketBaseTest;

public class TestCloseBug58624 extends WebSocketBaseTest {

    @Test
    public void testOnErrorNotCalledWhenClosingConnection() throws Throwable {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(Bug58624ServerConfig.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer = ContainerProvider.getWebSocketContainer();

        tomcat.start();

        Bug58624ClientEndpoint client = new Bug58624ClientEndpoint();
        URI uri = new URI("ws://localhost:" + getPort() + Bug58624ServerConfig.PATH);

        Session session = wsContainer.connectToServer(client, uri);

        // Wait for session to open on the server
        int count = 0;
        while (count < 50 && Bug58624ServerEndpoint.getOpenSessionCount() == 0) {
            count++;
            Thread.sleep(100);
        }
        Assert.assertNotEquals(0,  Bug58624ServerEndpoint.getOpenSessionCount());

        // Now close the session
        session.close();

        // Wait for session to close on the server
        count = 0;
        while (count < 50 && Bug58624ServerEndpoint.getOpenSessionCount() > 0) {
            count++;
            Thread.sleep(100);
        }
        Assert.assertEquals(0,  Bug58624ServerEndpoint.getOpenSessionCount());

        // Ensure no errors were reported on the server
        Assert.assertEquals(0,  Bug58624ServerEndpoint.getErrorCount());

        if (client.getError() != null) {
            throw client.getError();
        }
    }

    @ClientEndpoint
    public class Bug58624ClientEndpoint {

        private volatile Throwable t;


        @OnError
        public void onError(Throwable t) {
            this.t = t;
        }


        public Throwable getError() {
            return this.t;
        }
    }

    public static class Bug58624ServerConfig extends WsContextListener {

        public static String PATH = "/bug58624";


        @Override
        public void contextInitialized(ServletContextEvent sce) {
            super.contextInitialized(sce);

            ServerContainer sc = (ServerContainer) sce.getServletContext().getAttribute(
                    Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE);

            ServerEndpointConfig sec = ServerEndpointConfig.Builder.create(
                    Bug58624ServerEndpoint.class, PATH).build();

            try {
                sc.addEndpoint(sec);
            } catch (DeploymentException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class Bug58624ServerEndpoint {

        private static AtomicInteger openSessionCount = new AtomicInteger(0);
        private static AtomicInteger errorCount = new AtomicInteger(0);

        public static int getOpenSessionCount() {
            return openSessionCount.get();
        }

        public static int getErrorCount() {
            return errorCount.get();
        }

        @OnOpen
        public void onOpen() {
            openSessionCount.incrementAndGet();
        }


        @OnMessage
        public void onMessage(@SuppressWarnings("unused") Session session, String message) {
            System.out.println("Received message " + message);
        }


        @OnError
        public void onError(Throwable t) {
            errorCount.incrementAndGet();
            t.printStackTrace();
        }


        @OnClose
        public void onClose(@SuppressWarnings("unused") CloseReason cr) {
            openSessionCount.decrementAndGet();
        }
    }
}
