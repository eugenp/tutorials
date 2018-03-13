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
package org.apache.tomcat.websocket;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.net.TesterSupport;
import org.apache.tomcat.websocket.TesterMessageCountClient.BasicText;
import org.apache.tomcat.websocket.TesterMessageCountClient.SleepingText;
import org.apache.tomcat.websocket.TesterMessageCountClient.TesterProgrammaticEndpoint;

public class TestWebSocketFrameClientSSL extends WebSocketBaseTest {


    @Test
    public void testConnectToServerEndpoint() throws Exception {

        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterFirehoseServer.Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");


        TesterSupport.initSsl(tomcat);

        tomcat.start();

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();
        ClientEndpointConfig clientEndpointConfig =
                ClientEndpointConfig.Builder.create().build();
        URL truststoreUrl = this.getClass().getClassLoader().getResource(
                TesterSupport.CA_JKS);
        File truststoreFile = new File(truststoreUrl.toURI());
        clientEndpointConfig.getUserProperties().put(
                WsWebSocketContainer.SSL_TRUSTSTORE_PROPERTY,
                truststoreFile.getAbsolutePath());
        Session wsSession = wsContainer.connectToServer(
                TesterProgrammaticEndpoint.class,
                clientEndpointConfig,
                new URI("wss://localhost:" + getPort() +
                        TesterFirehoseServer.Config.PATH));
        CountDownLatch latch =
                new CountDownLatch(TesterFirehoseServer.MESSAGE_COUNT);
        BasicText handler = new BasicText(latch);
        wsSession.addMessageHandler(handler);
        wsSession.getBasicRemote().sendText("Hello");

        System.out.println("Sent Hello message, waiting for data");

        // Ignore the latch result as the message count test below will tell us
        // if the right number of messages arrived
        handler.getLatch().await(TesterFirehoseServer.WAIT_TIME_MILLIS,
                TimeUnit.MILLISECONDS);

        Queue<String> messages = handler.getMessages();
        Assert.assertEquals(
                TesterFirehoseServer.MESSAGE_COUNT, messages.size());
        for (String message : messages) {
            Assert.assertEquals(TesterFirehoseServer.MESSAGE, message);
        }
    }


    @Test
    public void testBug56032() throws Exception {
        // TODO Investigate options to get this test to pass with the HTTP BIO
        //      connector.
        Assume.assumeFalse(
                "Skip this test on BIO. TODO: investigate options to make it pass with HTTP BIO connector",
                getTomcatInstance().getConnector().getProtocolHandlerClassName().equals(
                        "org.apache.coyote.http11.Http11Protocol"));

        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterFirehoseServer.Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        TesterSupport.initSsl(tomcat);

        tomcat.start();

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();
        ClientEndpointConfig clientEndpointConfig =
                ClientEndpointConfig.Builder.create().build();
        clientEndpointConfig.getUserProperties().put(
                WsWebSocketContainer.SSL_TRUSTSTORE_PROPERTY,
                "test/" + TesterSupport.CA_JKS);
        Session wsSession = wsContainer.connectToServer(
                TesterProgrammaticEndpoint.class,
                clientEndpointConfig,
                new URI("wss://localhost:" + getPort() +
                        TesterFirehoseServer.Config.PATH));

        // Process incoming messages very slowly
        MessageHandler handler = new SleepingText(5000);
        wsSession.addMessageHandler(handler);
        wsSession.getBasicRemote().sendText("Hello");

        // Wait long enough for the buffers to fill and the send to timeout
        int count = 0;
        int limit = TesterFirehoseServer.WAIT_TIME_MILLIS / 100;

        System.err.println("Waiting for server to report an error");
        while (TesterFirehoseServer.Endpoint.getErrorCount() == 0 && count < limit) {
            Thread.sleep(100);
            count ++;
        }

        if (TesterFirehoseServer.Endpoint.getErrorCount() == 0) {
            Assert.fail("No error reported by Endpoint when timeout was expected");
        }

        // Wait up to another 20 seconds for the connection to be closed
        System.err.println("Waiting for connection to be closed");
        count = 0;
        limit = (TesterFirehoseServer.SEND_TIME_OUT_MILLIS * 4) / 100;
        while (TesterFirehoseServer.Endpoint.getOpenConnectionCount() != 0 && count < limit) {
            Thread.sleep(100);
            count ++;
        }

        int openConnectionCount = TesterFirehoseServer.Endpoint.getOpenConnectionCount();
        if (openConnectionCount != 0) {
            Assert.fail("There are [" + openConnectionCount + "] connections still open");
        }

        // Close the client session.
        wsSession.close();
    }

}
