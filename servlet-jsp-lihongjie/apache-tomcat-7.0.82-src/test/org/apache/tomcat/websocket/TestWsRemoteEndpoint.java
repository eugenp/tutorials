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

import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpointConfig.Builder;
import javax.websocket.ContainerProvider;
import javax.websocket.Endpoint;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.websocket.TesterMessageCountClient.AsyncBinary;
import org.apache.tomcat.websocket.TesterMessageCountClient.AsyncHandler;
import org.apache.tomcat.websocket.TesterMessageCountClient.AsyncText;
import org.apache.tomcat.websocket.TesterMessageCountClient.TesterAnnotatedEndpoint;
import org.apache.tomcat.websocket.TesterMessageCountClient.TesterEndpoint;
import org.apache.tomcat.websocket.TesterMessageCountClient.TesterProgrammaticEndpoint;

public class TestWsRemoteEndpoint extends WebSocketBaseTest {

    private static final String SEQUENCE = "ABCDE";
    private static final int S_LEN = SEQUENCE.length();
    private static final String TEST_MESSAGE_5K;

    static {
        StringBuilder sb = new StringBuilder(S_LEN * 1024);
        for (int i = 0; i < 1024; i++) {
            sb.append(SEQUENCE);
        }
        TEST_MESSAGE_5K = sb.toString();
    }

    @Test
    public void testWriterAnnotation() throws Exception {
        doTestWriter(TesterAnnotatedEndpoint.class, true);
    }

    @Test
    public void testWriterProgrammatic() throws Exception {
        doTestWriter(TesterProgrammaticEndpoint.class, true);
    }

    @Test
    public void testStreamAnnotation() throws Exception {
        doTestWriter(TesterAnnotatedEndpoint.class, false);
    }

    @Test
    public void testStreamProgrammatic() throws Exception {
        doTestWriter(TesterProgrammaticEndpoint.class, false);
    }

    private void doTestWriter(Class<?> clazz, boolean useWriter) throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(TesterEchoServer.Config.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();

        tomcat.start();

        Session wsSession;
        URI uri = new URI("ws://localhost:" + getPort() +
                TesterEchoServer.Config.PATH_ASYNC);
        if (Endpoint.class.isAssignableFrom(clazz)) {
            @SuppressWarnings("unchecked")
            Class<? extends Endpoint> endpointClazz =
                    (Class<? extends Endpoint>) clazz;
            wsSession = wsContainer.connectToServer(endpointClazz,
                    Builder.create().build(), uri);
        } else {
            wsSession = wsContainer.connectToServer(clazz, uri);
        }

        CountDownLatch latch = new CountDownLatch(1);
        TesterEndpoint tep =
                (TesterEndpoint) wsSession.getUserProperties().get("endpoint");
        tep.setLatch(latch);
        AsyncHandler<?> handler;
        if (useWriter) {
            handler = new AsyncText(latch);
        } else {
            handler = new AsyncBinary(latch);
        }

        wsSession.addMessageHandler(handler);

        if (useWriter) {
            Writer w = wsSession.getBasicRemote().getSendWriter();

            for (int i = 0; i < 8; i++) {
                w.write(TEST_MESSAGE_5K);
            }

            w.close();
        } else {
            OutputStream s = wsSession.getBasicRemote().getSendStream();

            for (int i = 0; i < 8; i++) {
                s.write(TEST_MESSAGE_5K.getBytes(B2CConverter.UTF_8));
            }

            s.close();
        }

        boolean latchResult = handler.getLatch().await(10, TimeUnit.SECONDS);

        Assert.assertTrue(latchResult);

        List<String> results = new ArrayList<String>();
        if (useWriter) {
            @SuppressWarnings("unchecked")
            List<String> messages = (List<String>) handler.getMessages();
            results.addAll(messages);
        } else {
            // Take advantage of the fact that the message uses characters that
            // are represented as a single UTF-8 byte so won't be split across
            // binary messages
            @SuppressWarnings("unchecked")
            List<ByteBuffer> messages = (List<ByteBuffer>) handler.getMessages();
            for (ByteBuffer message : messages) {
                byte[] bytes = new byte[message.limit()];
                message.get(bytes);
                results.add(new String(bytes, B2CConverter.UTF_8));
            }
        }

        int offset = 0;
        int i = 0;
        for (String result : results) {
            // First may be a fragment
            Assert.assertEquals(SEQUENCE.substring(offset, S_LEN),
                    result.substring(0, S_LEN - offset));
            i = S_LEN - offset;
            while (i + S_LEN < result.length()) {
                if (!SEQUENCE.equals(result.substring(i, i + S_LEN))) {
                    Assert.fail();
                }
                i += S_LEN;
            }
            offset = result.length() - i;
            if (!SEQUENCE.substring(0, offset).equals(result.substring(i))) {
                Assert.fail();
            }
        }
    }
}
