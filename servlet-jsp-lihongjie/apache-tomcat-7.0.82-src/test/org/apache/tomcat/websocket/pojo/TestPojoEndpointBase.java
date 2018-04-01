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
package org.apache.tomcat.websocket.pojo;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerEndpoint;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.websocket.TestUtil;
import org.apache.tomcat.websocket.pojo.TesterUtil.ServerConfigListener;
import org.apache.tomcat.websocket.pojo.TesterUtil.SingletonConfigurator;

public class TestPojoEndpointBase extends TomcatBaseTest {

    @Test
    public void testBug54716() throws Exception {
        TestUtil.generateMask();
        // Set up utility classes
        Bug54716 server = new Bug54716();
        SingletonConfigurator.setInstance(server);
        ServerConfigListener.setPojoClazz(Bug54716.class);

        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(ServerConfigListener.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();


        tomcat.start();

        Client client = new Client();
        URI uri = new URI("ws://localhost:" + getPort() + "/");

        wsContainer.connectToServer(client, uri);

        // Server should close the connection after the exception on open.
        boolean closed = client.waitForClose(5);
        Assert.assertTrue("Server failed to close connection", closed);
    }


    @Test
    public void testOnOpenPojoMethod() throws Exception {
        // Set up utility classes
        OnOpenServerEndpoint server = new OnOpenServerEndpoint();
        SingletonConfigurator.setInstance(server);
        ServerConfigListener.setPojoClazz(OnOpenServerEndpoint.class);

        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(ServerConfigListener.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();


        tomcat.start();

        Client client = new Client();
        URI uri = new URI("ws://localhost:" + getPort() + "/");

        Session session = wsContainer.connectToServer(client, uri);

        client.waitForClose(5);
        Assert.assertTrue(session.isOpen());
    }


    @ServerEndpoint("/")
    public static class OnOpenServerEndpoint {

        @OnOpen
        public void onOpen(@SuppressWarnings("unused") Session session,
                EndpointConfig config) {
            if (config == null) {
                throw new RuntimeException();
            }
        }

        @OnError
        public void onError(@SuppressWarnings("unused") Throwable t){
            throw new RuntimeException();
        }
    }


    @ServerEndpoint("/")
    public static class Bug54716 {

        @OnOpen
        public void onOpen() {
            throw new RuntimeException();
        }
    }


    @ClientEndpoint
    public static final class Client {

        private final CountDownLatch closeLatch = new CountDownLatch(1);

        @OnClose
        public void onClose() {
            closeLatch.countDown();
        }

        public boolean waitForClose(int seconds) throws InterruptedException {
            return closeLatch.await(seconds, TimeUnit.SECONDS);
        }
    }
}
