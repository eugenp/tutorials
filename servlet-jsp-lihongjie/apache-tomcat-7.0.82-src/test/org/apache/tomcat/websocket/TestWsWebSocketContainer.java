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
package org.apache.tomcat.websocket;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Extension;
import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11Protocol;
import org.apache.tomcat.util.net.TesterSupport;
import org.apache.tomcat.websocket.TesterMessageCountClient.BasicBinary;
import org.apache.tomcat.websocket.TesterMessageCountClient.BasicHandler;
import org.apache.tomcat.websocket.TesterMessageCountClient.BasicText;
import org.apache.tomcat.websocket.TesterMessageCountClient.TesterEndpoint;
import org.apache.tomcat.websocket.TesterMessageCountClient.TesterProgrammaticEndpoint;
import org.apache.tomcat.websocket.server.Constants;
import org.apache.tomcat.websocket.server.WsContextListener;

public class TestWsWebSocketContainer extends WebSocketBaseTest {

    private static final String MESSAGE_EMPTY = "";
    private static final String MESSAGE_STRING_1 = "qwerty";
    private static final String MESSAGE_TEXT_4K;
    private static final byte[] MESSAGE_BINARY_4K = new byte[4096];

    private static final long TIMEOUT_MS = 5 * 1000;
    private static final long MARGIN = 500;

    // 5s should be plenty but Gump can be a lot slower
    private static final long START_STOP_WAIT = 60 * 1000;

    static {
        StringBuilder sb = new StringBuilder(4096);
        for (int i = 0; i < 4096; i++) {
            sb.append('*');
        }
        MESSAGE_TEXT_4K = sb.toString();
    }


    @Test
    public void testConnectToServerEndpoint() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterEchoServer.Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();
        // Set this artificially small to trigger
        // https://bz.apache.org/bugzilla/show_bug.cgi?id=57054
        wsContainer.setDefaultMaxBinaryMessageBufferSize(64);
        Session wsSession = wsContainer.connectToServer(
                TesterProgrammaticEndpoint.class,
                ClientEndpointConfig.Builder.create().build(),
                new URI("ws://" + getHostName() + ":" + getPort() +
                        TesterEchoServer.Config.PATH_ASYNC));
        CountDownLatch latch = new CountDownLatch(1);
        BasicText handler = new BasicText(latch);
        wsSession.addMessageHandler(handler);
        wsSession.getBasicRemote().sendText(MESSAGE_STRING_1);

        boolean latchResult = handler.getLatch().await(10, TimeUnit.SECONDS);

        Assert.assertTrue(latchResult);

        Queue<String> messages = handler.getMessages();
        Assert.assertEquals(1, messages.size());
        Assert.assertEquals(MESSAGE_STRING_1, messages.peek());

        ((WsWebSocketContainer) wsContainer).destroy();
    }


    @Test(expected=javax.websocket.DeploymentException.class)
    public void testConnectToServerEndpointInvalidScheme() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterEchoServer.Config.class.getName());

        tomcat.start();

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();
        wsContainer.connectToServer(TesterProgrammaticEndpoint.class,
                ClientEndpointConfig.Builder.create().build(),
                new URI("ftp://" + getHostName() + ":" + getPort() +
                        TesterEchoServer.Config.PATH_ASYNC));
    }


    @Test(expected=javax.websocket.DeploymentException.class)
    public void testConnectToServerEndpointNoHost() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterEchoServer.Config.class.getName());

        tomcat.start();

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();
        wsContainer.connectToServer(TesterProgrammaticEndpoint.class,
                ClientEndpointConfig.Builder.create().build(),
                new URI("ws://" + TesterEchoServer.Config.PATH_ASYNC));
    }


    @Test
    public void testSmallTextBufferClientTextMessage() throws Exception {
        doBufferTest(true, false, true, false);
    }


    @Test
    public void testSmallTextBufferClientBinaryMessage() throws Exception {
        doBufferTest(true, false, false, true);
    }


    @Test
    public void testSmallTextBufferServerTextMessage() throws Exception {
        doBufferTest(true, true, true, false);
    }


    @Test
    public void testSmallTextBufferServerBinaryMessage() throws Exception {
        doBufferTest(true, true, false, true);
    }


    @Test
    public void testSmallBinaryBufferClientTextMessage() throws Exception {
        doBufferTest(false, false, true, true);
    }


    @Test
    public void testSmallBinaryBufferClientBinaryMessage() throws Exception {
        doBufferTest(false, false, false, false);
    }


    @Test
    public void testSmallBinaryBufferServerTextMessage() throws Exception {
        doBufferTest(false, true, true, true);
    }


    @Test
    public void testSmallBinaryBufferServerBinaryMessage() throws Exception {
        doBufferTest(false, true, false, false);
    }


    private void doBufferTest(boolean isTextBuffer, boolean isServerBuffer,
            boolean isTextMessage, boolean pass) throws Exception {

        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterEchoServer.Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();

        if (isServerBuffer) {
            if (isTextBuffer) {
                ctx.addParameter(
                        org.apache.tomcat.websocket.server.Constants.
                                TEXT_BUFFER_SIZE_SERVLET_CONTEXT_INIT_PARAM,
                        "1024");
            } else {
                ctx.addParameter(
                        org.apache.tomcat.websocket.server.Constants.
                                BINARY_BUFFER_SIZE_SERVLET_CONTEXT_INIT_PARAM,
                        "1024");
            }
        } else {
            if (isTextBuffer) {
                wsContainer.setDefaultMaxTextMessageBufferSize(1024);
            } else {
                wsContainer.setDefaultMaxBinaryMessageBufferSize(1024);
            }
        }

        tomcat.start();

        Session wsSession = wsContainer.connectToServer(
                TesterProgrammaticEndpoint.class,
                ClientEndpointConfig.Builder.create().build(),
                        new URI("ws://" + getHostName() + ":" + getPort() +
                                TesterEchoServer.Config.PATH_BASIC));
        BasicHandler<?> handler;
        CountDownLatch latch = new CountDownLatch(1);
        TesterEndpoint tep =
                (TesterEndpoint) wsSession.getUserProperties().get("endpoint");
        tep.setLatch(latch);
        if (isTextMessage) {
            handler = new BasicText(latch);
        } else {
            handler = new BasicBinary(latch);
        }

        wsSession.addMessageHandler(handler);
        try {
            if (isTextMessage) {
                wsSession.getBasicRemote().sendText(MESSAGE_TEXT_4K);
            } else {
                wsSession.getBasicRemote().sendBinary(
                        ByteBuffer.wrap(MESSAGE_BINARY_4K));
            }
        } catch (IOException ioe) {
            // Some messages sends are expected to fail. Assertions further on
            // in this method will check for the correct behaviour so ignore any
            // exception here.
        }

        boolean latchResult = handler.getLatch().await(10, TimeUnit.SECONDS);

        Assert.assertTrue(latchResult);

        Queue<?> messages = handler.getMessages();
        if (pass) {
            Assert.assertEquals(1, messages.size());
            if (isTextMessage) {
                Assert.assertEquals(MESSAGE_TEXT_4K, messages.peek());
            } else {
                Assert.assertEquals(ByteBuffer.wrap(MESSAGE_BINARY_4K),
                        messages.peek());
            }
        } else {
            // When the message exceeds the buffer size, the WebSocket is
            // closed. The endpoint ensures that the latch is cleared when the
            // WebSocket closes. However, the session isn't marked as closed
            // until after the onClose() method completes so there is a small
            // window where this test could fail. Therefore, wait briefly to
            // give the session a chance to complete the close process.
            for (int i = 0; i < 500; i++) {
                if (!wsSession.isOpen()) {
                    break;
                }
                Thread.sleep(10);
            }
            Assert.assertFalse(wsSession.isOpen());
        }
    }


    @Test
    public void testWriteTimeoutClientContainer() throws Exception {
        doTestWriteTimeoutClient(true);
    }


    @Test
    public void testWriteTimeoutClientEndpoint() throws Exception {
        doTestWriteTimeoutClient(false);
    }


    private void doTestWriteTimeoutClient(boolean setTimeoutOnContainer)
            throws Exception {

        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(BlockingConfig.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();

        // Set the async timeout
        if (setTimeoutOnContainer) {
            wsContainer.setAsyncSendTimeout(TIMEOUT_MS);
        }

        tomcat.start();

        Session wsSession = wsContainer.connectToServer(
                TesterProgrammaticEndpoint.class,
                ClientEndpointConfig.Builder.create().build(),
                new URI("ws://" + getHostName() + ":" + getPort() + BlockingConfig.PATH));

        if (!setTimeoutOnContainer) {
            wsSession.getAsyncRemote().setSendTimeout(TIMEOUT_MS);
        }

        long lastSend = 0;

        // Should send quickly until the network buffers fill up and then block
        // until the timeout kicks in
        Exception exception = null;
        try {
            while (true) {
                lastSend = System.currentTimeMillis();
                Future<Void> f = wsSession.getAsyncRemote().sendBinary(
                        ByteBuffer.wrap(MESSAGE_BINARY_4K));
                f.get();
            }
        } catch (Exception e) {
            exception = e;
        }

        long timeout = System.currentTimeMillis() - lastSend;

        // Clear the server side block and prevent further blocks to allow the
        // server to shutdown cleanly
        BlockingPojo.clearBlock();

        // Close the client session, primarily to allow the
        // BackgroundProcessManager to shut down.
        wsSession.close();

        String msg = "Time out was [" + timeout + "] ms";

        // Check correct time passed
        Assert.assertTrue(msg, timeout >= TIMEOUT_MS - MARGIN );

        // Check the timeout wasn't too long
        Assert.assertTrue(msg, timeout < TIMEOUT_MS * 2);

        Assert.assertNotNull(exception);
    }


    @Test
    public void testWriteTimeoutServerContainer() throws Exception {
        doTestWriteTimeoutServer(true);
    }


    @Test
    public void testWriteTimeoutServerEndpoint() throws Exception {
        doTestWriteTimeoutServer(false);
    }


    private static volatile boolean timeoutOnContainer = false;

    private void doTestWriteTimeoutServer(boolean setTimeoutOnContainer)
            throws Exception {

        // This will never work for BIO
        Assume.assumeFalse(
                "Skipping test. This feature will never work for BIO connector.",
                getProtocol().equals(Http11Protocol.class.getName()));

        /*
         * Note: There are all sorts of horrible uses of statics in this test
         *       because the API uses classes and the tests really need access
         *       to the instances which simply isn't possible.
         */
        timeoutOnContainer = setTimeoutOnContainer;

        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(ConstantTxConfig.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();

        tomcat.start();

        Session wsSession = wsContainer.connectToServer(
                TesterProgrammaticEndpoint.class,
                ClientEndpointConfig.Builder.create().build(),
                new URI("ws://" + getHostName() + ":" + getPort() +
                        ConstantTxConfig.PATH));

        wsSession.addMessageHandler(new BlockingBinaryHandler());

        int loops = 0;
        while (loops < 15) {
            Thread.sleep(1000);
            if (!ConstantTxEndpoint.getRunning()) {
                break;
            }
            loops++;
        }

        // Close the client session, primarily to allow the
        // BackgroundProcessManager to shut down.
        wsSession.close();

        // Check the right exception was thrown
        Assert.assertNotNull(ConstantTxEndpoint.getException());
        Assert.assertEquals(ExecutionException.class,
                ConstantTxEndpoint.getException().getClass());
        Assert.assertNotNull(ConstantTxEndpoint.getException().getCause());
        Assert.assertEquals(SocketTimeoutException.class,
                ConstantTxEndpoint.getException().getCause().getClass());

        // Check correct time passed
        Assert.assertTrue(ConstantTxEndpoint.getTimeout() >= TIMEOUT_MS);

        // Check the timeout wasn't too long
        Assert.assertTrue(ConstantTxEndpoint.getTimeout() < TIMEOUT_MS*2);
    }


    public static class BlockingConfig extends WsContextListener {

        public static final String PATH = "/block";

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            super.contextInitialized(sce);
            ServerContainer sc =
                    (ServerContainer) sce.getServletContext().getAttribute(
                            Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE);
            try {
                // Reset blocking state
                BlockingPojo.resetBlock();
                sc.addEndpoint(BlockingPojo.class);
            } catch (DeploymentException e) {
                throw new IllegalStateException(e);
            }
        }
    }


    @ServerEndpoint("/block")
    public static class BlockingPojo {

        private static Object monitor = new Object();
        // Enable blocking by default
        private static boolean block = true;

        /**
         * Clear any current block.
         */
        public static void clearBlock() {
            synchronized (monitor) {
                BlockingPojo.block = false;
                monitor.notifyAll();
            }
        }

        public static void resetBlock() {
            synchronized (monitor) {
                block = true;
            }
        }
        @SuppressWarnings("unused")
        @OnMessage
        public void echoTextMessage(Session session, String msg, boolean last) {
            try {
                synchronized (monitor) {
                    while (block) {
                        monitor.wait();
                    }
                }
            } catch (InterruptedException e) {
                // Ignore
            }
        }


        @SuppressWarnings("unused")
        @OnMessage
        public void echoBinaryMessage(Session session, ByteBuffer msg,
                boolean last) {
            try {
                synchronized (monitor) {
                    while (block) {
                        monitor.wait();
                    }
                }
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }


    public static class BlockingBinaryHandler
            implements MessageHandler.Partial<ByteBuffer> {

        @Override
        public void onMessage(ByteBuffer messagePart, boolean last) {
            try {
                Thread.sleep(TIMEOUT_MS * 10);
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }


    public static class ConstantTxEndpoint extends Endpoint {

        // Have to be static to be able to retrieve results from test case
        private static volatile long timeout = -1;
        private static volatile Exception exception = null;
        private static volatile boolean running = true;


        @Override
        public void onOpen(Session session, EndpointConfig config) {

            // Reset everything
            timeout = -1;
            exception = null;
            running = true;

            if (!TestWsWebSocketContainer.timeoutOnContainer) {
                session.getAsyncRemote().setSendTimeout(TIMEOUT_MS);
            }

            // The close message is written with a blocking write. This is going
            // to fail so reduce the timeout from the default so the test
            // completes faster
            session.getUserProperties().put(
                    WsRemoteEndpointImplBase.BLOCKING_SEND_TIMEOUT_PROPERTY, Long.valueOf(5000));

            // Should send quickly until the network buffers fill up and then
            // block until the timeout kicks in
            long lastSend = 0;
            try {
                while (true) {
                    lastSend = System.currentTimeMillis();
                    Future<Void> f = session.getAsyncRemote().sendBinary(
                            ByteBuffer.wrap(MESSAGE_BINARY_4K));
                    f.get();
                }
            } catch (ExecutionException e) {
                exception = e;
            } catch (InterruptedException e) {
                exception = e;
            }
            timeout = System.currentTimeMillis() - lastSend;
            running = false;
        }

        public static long getTimeout() {
            return timeout;
        }

        public static Exception getException() {
            return exception;
        }

        public static boolean getRunning() {
            return running;
        }
    }


    public static class ConstantTxConfig extends WsContextListener {

        private static final String PATH = "/test";

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            super.contextInitialized(sce);
            ServerContainer sc =
                    (ServerContainer) sce.getServletContext().getAttribute(
                            Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE);
            try {
                sc.addEndpoint(ServerEndpointConfig.Builder.create(
                        ConstantTxEndpoint.class, PATH).build());
                if (TestWsWebSocketContainer.timeoutOnContainer) {
                    sc.setAsyncSendTimeout(TIMEOUT_MS);
                }
            } catch (DeploymentException e) {
                throw new IllegalStateException(e);
            }
        }
    }


    @Test
    public void testGetOpenSessions() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterEchoServer.Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();

        EndpointA endpointA = new EndpointA();
        Session s1a = connectToEchoServer(wsContainer, endpointA,
                TesterEchoServer.Config.PATH_BASIC);
        Session s2a = connectToEchoServer(wsContainer, endpointA,
                TesterEchoServer.Config.PATH_BASIC);
        Session s3a = connectToEchoServer(wsContainer, endpointA,
                TesterEchoServer.Config.PATH_BASIC);

        EndpointB endpointB = new EndpointB();
        Session s1b = connectToEchoServer(wsContainer, endpointB,
                TesterEchoServer.Config.PATH_BASIC);
        Session s2b = connectToEchoServer(wsContainer, endpointB,
                TesterEchoServer.Config.PATH_BASIC);

        Set<Session> setA = s3a.getOpenSessions();
        Assert.assertEquals(3, setA.size());
        Assert.assertTrue(setA.remove(s1a));
        Assert.assertTrue(setA.remove(s2a));
        Assert.assertTrue(setA.remove(s3a));

        s1a.close();

        setA = s3a.getOpenSessions();
        Assert.assertEquals(2, setA.size());
        Assert.assertFalse(setA.remove(s1a));
        Assert.assertTrue(setA.remove(s2a));
        Assert.assertTrue(setA.remove(s3a));

        Set<Session> setB = s1b.getOpenSessions();
        Assert.assertEquals(2, setB.size());
        Assert.assertTrue(setB.remove(s1b));
        Assert.assertTrue(setB.remove(s2b));

        // Close sessions explicitly as Gump reports a session remains open at
        // the end of this test
        s2a.close();
        s3a.close();
        s1b.close();
        s2b.close();
    }


    @Test
    public void testSessionExpiryContainer() throws Exception {

        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterEchoServer.Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        // Need access to implementation methods for configuring unit tests
        WsWebSocketContainer wsContainer = (WsWebSocketContainer)
                ContainerProvider.getWebSocketContainer();

        // 5 second timeout
        wsContainer.setDefaultMaxSessionIdleTimeout(5000);
        wsContainer.setProcessPeriod(1);

        EndpointA endpointA = new EndpointA();
        connectToEchoServer(wsContainer, endpointA,
                TesterEchoServer.Config.PATH_BASIC);
        connectToEchoServer(wsContainer, endpointA,
                TesterEchoServer.Config.PATH_BASIC);
        Session s3a = connectToEchoServer(wsContainer, endpointA,
                TesterEchoServer.Config.PATH_BASIC);

        // Check all three sessions are open
        Set<Session> setA = s3a.getOpenSessions();
        Assert.assertEquals(3, setA.size());

        int count = 0;
        boolean isOpen = true;
        while (isOpen && count < 8) {
            count ++;
            Thread.sleep(1000);
            isOpen = false;
            for (Session session : setA) {
                if (session.isOpen()) {
                    isOpen = true;
                    break;
                }
            }
        }

        if (isOpen) {
            for (Session session : setA) {
                if (session.isOpen()) {
                    System.err.println("Session with ID [" + session.getId() +
                            "] is open");
                }
            }
            Assert.fail("There were open sessions");
        }
    }


    @Test
    public void testSessionExpirySession() throws Exception {

        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterEchoServer.Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        // Need access to implementation methods for configuring unit tests
        WsWebSocketContainer wsContainer = (WsWebSocketContainer)
                ContainerProvider.getWebSocketContainer();

        // 5 second timeout
        wsContainer.setDefaultMaxSessionIdleTimeout(5000);
        wsContainer.setProcessPeriod(1);

        EndpointA endpointA = new EndpointA();
        Session s1a = connectToEchoServer(wsContainer, endpointA,
                TesterEchoServer.Config.PATH_BASIC);
        s1a.setMaxIdleTimeout(3000);
        Session s2a = connectToEchoServer(wsContainer, endpointA,
                TesterEchoServer.Config.PATH_BASIC);
        s2a.setMaxIdleTimeout(6000);
        Session s3a = connectToEchoServer(wsContainer, endpointA,
                TesterEchoServer.Config.PATH_BASIC);
        s3a.setMaxIdleTimeout(9000);

        // Check all three sessions are open
        Set<Session> setA = s3a.getOpenSessions();

        int expected = 3;
        while (expected > 0) {
            Assert.assertEquals(expected, getOpenCount(setA));

            int count = 0;
            while (getOpenCount(setA) == expected && count < 50) {
                count ++;
                Thread.sleep(100);
            }

            expected--;
        }

        Assert.assertEquals(0, getOpenCount(setA));
    }


    private int getOpenCount(Set<Session> sessions) {
        int result = 0;
        for (Session session : sessions) {
            if (session.isOpen()) {
                result++;
            }
        }
        return result;
    }

    private Session connectToEchoServer(WebSocketContainer wsContainer,
            Endpoint endpoint, String path) throws Exception {
        return wsContainer.connectToServer(endpoint,
                ClientEndpointConfig.Builder.create().build(),
                new URI("ws://" + getHostName() + ":" + getPort() + path));
    }

    public static final class EndpointA extends Endpoint {

        @Override
        public void onOpen(Session session, EndpointConfig config) {
            // NO-OP
        }
    }


    public static final class EndpointB extends Endpoint {

        @Override
        public void onOpen(Session session, EndpointConfig config) {
            // NO-OP
        }
    }


    @Test
    public void testConnectToServerEndpointSSL() throws Exception {

        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterEchoServer.Config.class.getName());
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
                new URI("wss://" + getHostName() + ":" + getPort() +
                        TesterEchoServer.Config.PATH_ASYNC));
        CountDownLatch latch = new CountDownLatch(1);
        BasicText handler = new BasicText(latch);
        wsSession.addMessageHandler(handler);
        wsSession.getBasicRemote().sendText(MESSAGE_STRING_1);

        boolean latchResult = handler.getLatch().await(10, TimeUnit.SECONDS);

        Assert.assertTrue(latchResult);

        Queue<String> messages = handler.getMessages();
        Assert.assertEquals(1, messages.size());
        Assert.assertEquals(MESSAGE_STRING_1, messages.peek());
    }


    @Test
    public void testMaxMessageSize01() throws Exception {
        doMaxMessageSize(TesterEchoServer.Config.PATH_BASIC_LIMIT_LOW,
                TesterEchoServer.BasicLimitLow.MAX_SIZE - 1, true);
    }


    @Test
    public void testMaxMessageSize02() throws Exception {
        doMaxMessageSize(TesterEchoServer.Config.PATH_BASIC_LIMIT_LOW,
                TesterEchoServer.BasicLimitLow.MAX_SIZE, true);
    }


    @Test
    public void testMaxMessageSize03() throws Exception {
        doMaxMessageSize(TesterEchoServer.Config.PATH_BASIC_LIMIT_LOW,
                TesterEchoServer.BasicLimitLow.MAX_SIZE + 1, false);
    }


    @Test
    public void testMaxMessageSize04() throws Exception {
        doMaxMessageSize(TesterEchoServer.Config.PATH_BASIC_LIMIT_HIGH,
                TesterEchoServer.BasicLimitHigh.MAX_SIZE - 1, true);
    }


    @Test
    public void testMaxMessageSize05() throws Exception {
        doMaxMessageSize(TesterEchoServer.Config.PATH_BASIC_LIMIT_HIGH,
                TesterEchoServer.BasicLimitHigh.MAX_SIZE, true);
    }


    @Test
    public void testMaxMessageSize06() throws Exception {
        doMaxMessageSize(TesterEchoServer.Config.PATH_BASIC_LIMIT_HIGH,
                TesterEchoServer.BasicLimitHigh.MAX_SIZE + 1, false);
    }


    private void doMaxMessageSize(String path, long size, boolean expectOpen)
            throws Exception {

        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterEchoServer.Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();

        Session s = connectToEchoServer(wsContainer, new EndpointA(), path);

        // One for the client, one for the server
        validateBackgroundProcessCount(2);

        StringBuilder msg = new StringBuilder();
        for (long i = 0; i < size; i++) {
            msg.append('x');
        }

        s.getBasicRemote().sendText(msg.toString());

        // Wait for up to 5 seconds for the client session to open
        boolean open = s.isOpen();
        int count = 0;
        while (open != expectOpen && count < 50) {
            Thread.sleep(100);
            count++;
            open = s.isOpen();
        }

        Assert.assertEquals(Boolean.valueOf(expectOpen),
                Boolean.valueOf(s.isOpen()));

        // Close the session if it is expected to be open
        if (expectOpen) {
            s.close();
        }

        // Ensure both server and client have shutdown
        validateBackgroundProcessCount(0);
    }


    private void validateBackgroundProcessCount(int expected) throws Exception {
        int count = 0;
        while (count < (START_STOP_WAIT / 100)) {
            if (BackgroundProcessManager.getInstance().getProcessCount() == expected) {
                break;
            }
            Thread.sleep(100);
            count++;
    }
        Assert.assertEquals(expected, BackgroundProcessManager.getInstance().getProcessCount());

    }

    @Test
    public void testPerMessageDeflateClient01() throws Exception {
        doTestPerMessageDeflateClient(MESSAGE_STRING_1, 1);
    }


    @Test
    public void testPerMessageDeflateClient02() throws Exception {
        doTestPerMessageDeflateClient(MESSAGE_EMPTY, 1);
    }


    @Test
    public void testPerMessageDeflateClient03() throws Exception {
        doTestPerMessageDeflateClient(MESSAGE_STRING_1, 2);
    }


    @Test
    public void testPerMessageDeflateClient04() throws Exception {
        doTestPerMessageDeflateClient(MESSAGE_EMPTY, 2);
    }


    private void doTestPerMessageDeflateClient(String msg, int count) throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // Must have a real docBase - just use temp
        Context ctx =
            tomcat.addContext("", System.getProperty("java.io.tmpdir"));
        ctx.addApplicationListener(TesterEchoServer.Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        Extension perMessageDeflate = new WsExtension(PerMessageDeflate.NAME);
        List<Extension> extensions = new ArrayList<Extension>(1);
        extensions.add(perMessageDeflate);

        ClientEndpointConfig clientConfig =
                ClientEndpointConfig.Builder.create().extensions(extensions).build();

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();
        Session wsSession = wsContainer.connectToServer(
                TesterProgrammaticEndpoint.class,
                clientConfig,
                new URI("ws://" + getHostName() + ":" + getPort() +
                        TesterEchoServer.Config.PATH_ASYNC));
        CountDownLatch latch = new CountDownLatch(count);
        BasicText handler = new BasicText(latch, msg);
        wsSession.addMessageHandler(handler);
        for (int i = 0; i < count; i++) {
            wsSession.getBasicRemote().sendText(msg);
        }

        boolean latchResult = handler.getLatch().await(10, TimeUnit.SECONDS);

        Assert.assertTrue(latchResult);

        ((WsWebSocketContainer) wsContainer).destroy();
    }


    /*
     * Make this possible to override so sub-class can more easily test proxy
     */
    protected String getHostName() {
        return "localhost";
    }
}
