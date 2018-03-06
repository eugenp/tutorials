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
package org.apache.tomcat.websocket.server;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import org.junit.Ignore;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.websocket.WebSocketBaseTest;
import org.apache.tomcat.websocket.pojo.TesterUtil.SimpleClient;

@Ignore // This test requires manual intervention to create breakpoints etc.
public class TestWsRemoteEndpointImplServer extends WebSocketBaseTest {

    /*
     * https://bz.apache.org/bugzilla/show_bug.cgi?id=58624
     *
     * This test requires three breakpoints to be set. Two in this file (marked
     * A & B with comments) and one (C) at the start of
     * WsRemoteEndpointImplServer.doWrite().
     *
     * With the breakpoints in place, run this test.
     * Once breakpoints A & B are reached, progress the thread at breakpoint A
     * one line to close the connection.
     * Once breakpoint C is reached, allow the thread at breakpoint B to
     * continue.
     * Then allow the thread at breakpoint C to continue.
     *
     * In the failure mode, the thread at breakpoint B will not progress past
     * the call to sendObject(). If the issue is fixed, the thread at breakpoint
     * B will continue past sendObject() and terminate with a TimeoutException.
     */
    @Test
    public void testClientDropsConnection() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(Bug58624Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();

        tomcat.start();

        SimpleClient client = new SimpleClient();
        URI uri = new URI("ws://localhost:" + getPort() + Bug58624Config.PATH);

        Session session = wsContainer.connectToServer(client, uri);
        // Break point A required on following line
        session.close();
    }

    public static class Bug58624Config extends WsContextListener {

        public static String PATH = "/bug58624";
        @Override
        public void contextInitialized(ServletContextEvent sce) {
            super.contextInitialized(sce);

            ServerContainer sc = (ServerContainer) sce.getServletContext().getAttribute(
                    Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE);

            List<Class<? extends Encoder>> encoders = new ArrayList<Class<? extends Encoder>>();
            encoders.add(Bug58624Encoder.class);
            ServerEndpointConfig sec = ServerEndpointConfig.Builder.create(
                    Bug58624Endpoint.class, PATH).encoders(encoders).build();

            try {
                sc.addEndpoint(sec);
            } catch (DeploymentException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class Bug58624Endpoint {

        private static final ExecutorService ex = Executors.newFixedThreadPool(1);

        @OnOpen
        public void onOpen(Session session) {
            ex.submit(new Bug58624SendMessage(session));
        }

        @OnMessage
        public void onMessage(String message) {
            System.out.println("OnMessage: " + message);
        }

        @OnError
        public void onError(Throwable t) {
            System.err.println("OnError:");
            t.printStackTrace();
        }

        @OnClose
        public void onClose(@SuppressWarnings("unused") Session session, CloseReason cr) {
            System.out.println("Closed " + cr);
        }
    }

    public static class Bug58624SendMessage implements Runnable {
        private Session session;

        public Bug58624SendMessage(Session session) {
            this.session = session;
        }

        @Override
        public void run() {
            try {
                // Breakpoint B required on following line
                session.getBasicRemote().sendObject("test");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class Bug58624Encoder implements Encoder.Text<Object> {

        @Override
        public void destroy() {
        }

        @Override
        public void init(EndpointConfig endpointConfig) {
        }

        @Override
        public String encode(Object object) throws EncodeException {
            return (String) object;
        }
    }
}
