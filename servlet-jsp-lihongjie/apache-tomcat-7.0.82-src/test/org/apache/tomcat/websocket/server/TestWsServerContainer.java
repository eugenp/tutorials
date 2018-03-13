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
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.unittest.TesterServletContext;
import org.apache.tomcat.websocket.TesterEchoServer;
import org.apache.tomcat.websocket.TesterMessageCountClient.BasicText;
import org.apache.tomcat.websocket.WebSocketBaseTest;
import org.apache.tomcat.websocket.pojo.TesterUtil.SimpleClient;


public class TestWsServerContainer extends WebSocketBaseTest {

    @Test
    public void testBug54807() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(Bug54807Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        Assert.assertEquals(LifecycleState.STARTED, ctx.getState());
    }


    @Test
    public void testBug58232() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(Bug54807Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();

        tomcat.start();

        Assert.assertEquals(LifecycleState.STARTED, ctx.getState());

        SimpleClient client = new SimpleClient();
        URI uri = new URI("ws://localhost:" + getPort() + "/echoBasic");

        Session session = null;
        try {
            session = wsContainer.connectToServer(client, uri);
            CountDownLatch latch = new CountDownLatch(1);
            BasicText handler = new BasicText(latch);
            session.addMessageHandler(handler);
            session.getBasicRemote().sendText("echoBasic");

            boolean latchResult = handler.getLatch().await(10, TimeUnit.SECONDS);
            Assert.assertTrue(latchResult);

            Queue<String> messages = handler.getMessages();
            Assert.assertEquals(1, messages.size());
            for (String message : messages) {
                Assert.assertEquals("echoBasic", message);
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    public static class Bug54807Config extends WsContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            super.contextInitialized(sce);

            ServerContainer sc =
                    (ServerContainer) sce.getServletContext().getAttribute(
                            Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE);

            ServerEndpointConfig sec = ServerEndpointConfig.Builder.create(
                    TesterEchoServer.Basic.class, "/{param}").build();

            try {
                sc.addEndpoint(sec);
            } catch (DeploymentException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Test
    public void testSpecExample3() throws Exception {
        WsServerContainer sc =
                new WsServerContainer(new TesterServletContext());

        ServerEndpointConfig configA = ServerEndpointConfig.Builder.create(
                Object.class, "/a/{var}/c").build();
        ServerEndpointConfig configB = ServerEndpointConfig.Builder.create(
                Object.class, "/a/b/c").build();
        ServerEndpointConfig configC = ServerEndpointConfig.Builder.create(
                Object.class, "/a/{var1}/{var2}").build();

        sc.addEndpoint(configA);
        sc.addEndpoint(configB);
        sc.addEndpoint(configC);

        Assert.assertEquals(configB, sc.findMapping("/a/b/c").getConfig());
        Assert.assertEquals(configA, sc.findMapping("/a/d/c").getConfig());
        Assert.assertEquals(configC, sc.findMapping("/a/x/y").getConfig());
    }


    @Test
    public void testSpecExample4() throws Exception {
        WsServerContainer sc =
                new WsServerContainer(new TesterServletContext());

        ServerEndpointConfig configA = ServerEndpointConfig.Builder.create(
                Object.class, "/{var1}/d").build();
        ServerEndpointConfig configB = ServerEndpointConfig.Builder.create(
                Object.class, "/b/{var2}").build();

        sc.addEndpoint(configA);
        sc.addEndpoint(configB);

        Assert.assertEquals(configB, sc.findMapping("/b/d").getConfig());
    }


    @Test(expected = javax.websocket.DeploymentException.class)
    public void testDuplicatePaths_01() throws Exception {
        WsServerContainer sc =
                new WsServerContainer(new TesterServletContext());

        ServerEndpointConfig configA = ServerEndpointConfig.Builder.create(
                Object.class, "/a/b/c").build();
        ServerEndpointConfig configB = ServerEndpointConfig.Builder.create(
                Object.class, "/a/b/c").build();

        sc.addEndpoint(configA);
        sc.addEndpoint(configB);
    }


    @Test(expected = javax.websocket.DeploymentException.class)
    public void testDuplicatePaths_02() throws Exception {
        WsServerContainer sc =
                new WsServerContainer(new TesterServletContext());

        ServerEndpointConfig configA = ServerEndpointConfig.Builder.create(
                Object.class, "/a/b/{var}").build();
        ServerEndpointConfig configB = ServerEndpointConfig.Builder.create(
                Object.class, "/a/b/{var}").build();

        sc.addEndpoint(configA);
        sc.addEndpoint(configB);
    }


    @Test(expected = javax.websocket.DeploymentException.class)
    public void testDuplicatePaths_03() throws Exception {
        WsServerContainer sc =
                new WsServerContainer(new TesterServletContext());

        ServerEndpointConfig configA = ServerEndpointConfig.Builder.create(
                Object.class, "/a/b/{var1}").build();
        ServerEndpointConfig configB = ServerEndpointConfig.Builder.create(
                Object.class, "/a/b/{var2}").build();

        sc.addEndpoint(configA);
        sc.addEndpoint(configB);
    }


    @Test
    public void testDuplicatePaths_04() throws Exception {
        WsServerContainer sc =
                new WsServerContainer(new TesterServletContext());

        ServerEndpointConfig configA = ServerEndpointConfig.Builder.create(
                Object.class, "/a/{var1}/{var2}").build();
        ServerEndpointConfig configB = ServerEndpointConfig.Builder.create(
                Object.class, "/a/b/{var2}").build();

        sc.addEndpoint(configA);
        sc.addEndpoint(configB);

        Assert.assertEquals(configA, sc.findMapping("/a/x/y").getConfig());
        Assert.assertEquals(configB, sc.findMapping("/a/b/y").getConfig());
    }
}
