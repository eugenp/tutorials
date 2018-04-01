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

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletContextEvent;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
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
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.websocket.pojo.TesterUtil.ServerConfigListener;
import org.apache.tomcat.websocket.pojo.TesterUtil.SingletonConfigurator;
import org.apache.tomcat.websocket.server.WsContextListener;

public class TestEncodingDecoding extends TomcatBaseTest {

    private static final String MESSAGE_ONE = "message-one";
    private static final String PATH_PROGRAMMATIC_EP = "/echoProgrammaticEP";
    private static final String PATH_ANNOTATED_EP = "/echoAnnotatedEP";
    private static final String PATH_GENERICS_EP = "/echoGenericsEP";


    @Test
    public void testProgrammaticEndPoints() throws Exception{
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(
                ProgramaticServerEndpointConfig.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer = ContainerProvider.getWebSocketContainer();

        tomcat.start();

        Client client = new Client();
        URI uri = new URI("ws://localhost:" + getPort() + PATH_PROGRAMMATIC_EP);
        Session session = wsContainer.connectToServer(client, uri);

        MsgString msg1 = new MsgString();
        msg1.setData(MESSAGE_ONE);
        session.getBasicRemote().sendObject(msg1);
        // Should not take very long
        int i = 0;
        while (i < 20) {
            if (MsgStringMessageHandler.received.size() > 0 &&
                    client.received.size() > 0) {
                break;
            }
            Thread.sleep(100);
            i++;
        }

        // Check messages were received
        Assert.assertEquals(1, MsgStringMessageHandler.received.size());
        Assert.assertEquals(1, client.received.size());

        // Check correct messages were received
        Assert.assertEquals(MESSAGE_ONE,
                ((MsgString) MsgStringMessageHandler.received.peek()).getData());
        Assert.assertEquals(MESSAGE_ONE,
                new String(((MsgByte) client.received.peek()).getData()));
        session.close();
    }


    @Test
    public void testAnnotatedEndPoints() throws Exception {
        // Set up utility classes
        Server server = new Server();
        SingletonConfigurator.setInstance(server);
        ServerConfigListener.setPojoClazz(Server.class);

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
        URI uri = new URI("ws://localhost:" + getPort() + PATH_ANNOTATED_EP);
        Session session = wsContainer.connectToServer(client, uri);

        MsgString msg1 = new MsgString();
        msg1.setData(MESSAGE_ONE);
        session.getBasicRemote().sendObject(msg1);

        // Should not take very long
        int i = 0;
        while (i < 20) {
            if (server.received.size() > 0 && client.received.size() > 0) {
                break;
            }
            Thread.sleep(100);
        }

        // Check messages were received
        Assert.assertEquals(1, server.received.size());
        Assert.assertEquals(1, client.received.size());

        // Check correct messages were received
        Assert.assertEquals(MESSAGE_ONE,
                ((MsgString) server.received.peek()).getData());
        Assert.assertEquals(MESSAGE_ONE,
                ((MsgString) client.received.peek()).getData());
        session.close();

        // Should not take very long but some failures have been seen
        i = testEvent(MsgStringEncoder.class.getName()+":init", 0);
        i = testEvent(MsgStringDecoder.class.getName()+":init", i);
        i = testEvent(MsgByteEncoder.class.getName()+":init", i);
        i = testEvent(MsgByteDecoder.class.getName()+":init", i);
        i = testEvent(MsgStringEncoder.class.getName()+":destroy", i);
        i = testEvent(MsgStringDecoder.class.getName()+":destroy", i);
        i = testEvent(MsgByteEncoder.class.getName()+":destroy", i);
        i = testEvent(MsgByteDecoder.class.getName()+":destroy", i);
    }

    
    @Test
    public void testGenericsCoders() throws Exception {
        // Set up utility classes
        GenericsServer server = new GenericsServer();
        SingletonConfigurator.setInstance(server);
        ServerConfigListener.setPojoClazz(GenericsServer.class);

        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(ServerConfigListener.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer =
                ContainerProvider.getWebSocketContainer();

        tomcat.start();

        GenericsClient client = new GenericsClient();
        URI uri = new URI("ws://localhost:" + getPort() + PATH_GENERICS_EP);
        Session session = wsContainer.connectToServer(client, uri);

        ArrayList<String> list = new ArrayList<String>(2);
        list.add("str1");
        list.add("str2");
        session.getBasicRemote().sendObject(list);

        // Should not take very long
        int i = 0;
        while (i < 20) {
            if (server.received.size() > 0 && client.received.size() > 0) {
                break;
            }
            Thread.sleep(100);
        }

        // Check messages were received
        Assert.assertEquals(1, server.received.size());
        Assert.assertEquals(server.received.peek().toString(), "[str1, str2]");

        Assert.assertEquals(1, client.received.size());
        Assert.assertEquals(client.received.peek().toString(), "[str1, str2]");

        session.close();
    }


    private int testEvent(String name, int count) throws InterruptedException {
        int i = count;
        while (i < 50) {
            if (Server.isLifeCycleEventCalled(name)) {
                break;
            }
            i++;
            Thread.sleep(100);
        }
        Assert.assertTrue(Server.isLifeCycleEventCalled(name));
        return i;
    }


    @ClientEndpoint(decoders={ListStringDecoder.class},
            encoders={ListStringEncoder.class})
    public static class GenericsClient {
        private Queue<Object> received = new ConcurrentLinkedQueue<Object>();

        @OnMessage
        public void rx(List<String> in) {
            received.add(in);
        }
    }


    @ClientEndpoint(decoders={MsgStringDecoder.class, MsgByteDecoder.class},
            encoders={MsgStringEncoder.class, MsgByteEncoder.class})
    public static class Client {

        private Queue<Object> received = new ConcurrentLinkedQueue<Object>();

        @OnMessage
        public void rx(MsgString in) {
            received.add(in);
        }

        @OnMessage
        public void  rx(MsgByte in) {
            received.add(in);
        }
    }


    @ServerEndpoint(value=PATH_GENERICS_EP,
            decoders={ListStringDecoder.class},
            encoders={ListStringEncoder.class},
            configurator=SingletonConfigurator.class)
    public static class GenericsServer {

        private Queue<Object> received = new ConcurrentLinkedQueue<Object>();


        @OnMessage
        public List<String> rx(List<String> in) {
            received.add(in);
            // Echo the message back
            return in;
        }
    }


    @ServerEndpoint(value=PATH_ANNOTATED_EP,
            decoders={MsgStringDecoder.class, MsgByteDecoder.class},
            encoders={MsgStringEncoder.class, MsgByteEncoder.class},
            configurator=SingletonConfigurator.class)
    public static class Server {

        private Queue<Object> received = new ConcurrentLinkedQueue<Object>();
        static HashMap<String, Boolean> lifeCyclesCalled = new HashMap<String, Boolean>(8);

        @OnMessage
        public MsgString rx(MsgString in) {
            received.add(in);
            // Echo the message back
            return in;
        }

        @OnMessage
        public MsgByte rx(MsgByte in) {
            received.add(in);
            // Echo the message back
            return in;
        }

        public static void addLifeCycleEvent(String event){
            lifeCyclesCalled.put(event, Boolean.TRUE);
        }

        public static boolean isLifeCycleEventCalled(String event){
            Boolean called = lifeCyclesCalled.get(event);
            return called == null ? false : called.booleanValue();
        }
    }


    public static class MsgByteMessageHandler implements
            MessageHandler.Whole<MsgByte> {

        public static Queue<Object> received = new ConcurrentLinkedQueue<Object>();
        private final Session session;

        public MsgByteMessageHandler(Session session) {
            this.session = session;
        }

        @Override
        public void onMessage(MsgByte in) {
            System.out.println(getClass() + " received");
            received.add(in);
            try {
                MsgByte msg = new MsgByte();
                msg.setData("got it".getBytes());
                session.getBasicRemote().sendObject(msg);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            } catch (EncodeException e) {
                throw new IllegalStateException(e);
            }
        }
    }


    public static class MsgStringMessageHandler implements MessageHandler.Whole<MsgString> {

        public static Queue<Object> received = new ConcurrentLinkedQueue<Object>();
        private final Session session;

        public MsgStringMessageHandler(Session session) {
            this.session = session;
        }

        @Override
        public void onMessage(MsgString in) {
            received.add(in);
            try {
                MsgByte msg = new MsgByte();
                msg.setData(MESSAGE_ONE.getBytes());
                session.getBasicRemote().sendObject(msg);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        }
    }


    public static class ProgrammaticEndpoint extends Endpoint {
       @Override
       public void onOpen(Session session, EndpointConfig config) {
           session.addMessageHandler(new MsgStringMessageHandler(session));
       }
    }


    public static class MsgString {
        private String data;
        public String getData() { return data; }
        public void setData(String data) { this.data = data; }
    }


    public static class MsgStringEncoder implements Encoder.Text<MsgString> {

        @Override
        public void init(EndpointConfig endpointConfig) {
            Server.addLifeCycleEvent(getClass().getName() + ":init");
        }

        @Override
        public void destroy() {
            Server.addLifeCycleEvent(getClass().getName() + ":destroy");
        }

        @Override
        public String encode(MsgString msg) throws EncodeException {
            return "MsgString:" + msg.getData();
        }
    }


    public static class MsgStringDecoder implements Decoder.Text<MsgString> {

        @Override
        public void init(EndpointConfig endpointConfig) {
            Server.addLifeCycleEvent(getClass().getName() + ":init");
        }

        @Override
        public void destroy() {
            Server.addLifeCycleEvent(getClass().getName() + ":destroy");
        }

        @Override
        public MsgString decode(String s) throws DecodeException {
            MsgString result = new MsgString();
            result.setData(s.substring(10));
            return result;
        }

        @Override
        public boolean willDecode(String s) {
            return s.startsWith("MsgString:");
        }
    }


    public static class MsgByte {
        private byte[] data;
        public byte[] getData() { return data; }
        public void setData(byte[] data) { this.data = data; }
    }


    public static class MsgByteEncoder implements Encoder.Binary<MsgByte> {

        @Override
        public void init(EndpointConfig endpointConfig) {
            Server.addLifeCycleEvent(getClass().getName() + ":init");
        }

        @Override
        public void destroy() {
            Server.addLifeCycleEvent(getClass().getName() + ":destroy");
        }

        @Override
        public ByteBuffer encode(MsgByte msg) throws EncodeException {
            byte[] data = msg.getData();
            ByteBuffer reply = ByteBuffer.allocate(2 + data.length);
            reply.put((byte) 0x12);
            reply.put((byte) 0x34);
            reply.put(data);
            reply.flip();
            return reply;
        }
    }


    public static class MsgByteDecoder implements Decoder.Binary<MsgByte> {

        @Override
        public void init(EndpointConfig endpointConfig) {
             Server.addLifeCycleEvent(getClass().getName() + ":init");
        }

        @Override
        public void destroy() {
            Server.addLifeCycleEvent(getClass().getName() + ":destroy");
        }

        @Override
        public MsgByte decode(ByteBuffer bb) throws DecodeException {
            MsgByte result = new MsgByte();
            byte[] data = new byte[bb.limit() - bb.position()];
            bb.get(data);
            result.setData(data);
            return result;
        }

        @Override
        public boolean willDecode(ByteBuffer bb) {
            bb.mark();
            if (bb.get() == 0x12 && bb.get() == 0x34) {
                return true;
            }
            bb.reset();
            return false;
        }
    }


    public static class ListStringEncoder implements Encoder.Text<List<String>> {

        @Override
        public void init(EndpointConfig endpointConfig) {
            Server.addLifeCycleEvent(getClass().getName() + ":init");
        }

        @Override
        public void destroy() {
            Server.addLifeCycleEvent(getClass().getName() + ":destroy");
        }

        @Override
        public String encode(List<String> str) throws EncodeException {
            StringBuffer sbuf = new StringBuffer();
            sbuf.append("[");
            for (String s: str){
                sbuf.append(s).append(",");
            }
            sbuf.deleteCharAt(sbuf.lastIndexOf(",")).append("]");
            return sbuf.toString();
        }
    }


    public static class ListStringDecoder implements Decoder.Text<List<String>> {

        @Override
        public void init(EndpointConfig endpointConfig) {
             Server.addLifeCycleEvent(getClass().getName() + ":init");
        }

        @Override
        public void destroy() {
            Server.addLifeCycleEvent(getClass().getName() + ":destroy");
        }

        @Override
        public List<String> decode(String str) throws DecodeException {
            List<String> lst = new ArrayList<String>(1);
            str = str.substring(1,str.length()-1);
            String[] strings = str.split(",");
            for (String t : strings){
                lst.add(t);
            }
            return lst;
        }

        @Override
        public boolean willDecode(String str) {
            return str.startsWith("[") && str.endsWith("]");
        }
    }


    public static class ProgramaticServerEndpointConfig extends WsContextListener {

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            super.contextInitialized(sce);
            ServerContainer sc =
                    (ServerContainer) sce.getServletContext().getAttribute(
                            org.apache.tomcat.websocket.server.Constants.
                            SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE);
            try {
                sc.addEndpoint(new ServerEndpointConfig() {
                    @Override
                    public Map<String, Object> getUserProperties() {
                        return Collections.emptyMap();
                    }
                    @Override
                    public List<Class<? extends Encoder>> getEncoders() {
                        List<Class<? extends Encoder>> encoders = new ArrayList<Class<? extends Encoder>>(2);
                        encoders.add(MsgStringEncoder.class);
                        encoders.add(MsgByteEncoder.class);
                        return encoders;
                    }
                    @Override
                    public List<Class<? extends Decoder>> getDecoders() {
                        List<Class<? extends Decoder>> decoders = new ArrayList<Class<? extends Decoder>>(2);
                        decoders.add(MsgStringDecoder.class);
                        decoders.add(MsgByteDecoder.class);
                        return decoders;
                    }
                    @Override
                    public List<String> getSubprotocols() {
                        return Collections.emptyList();
                    }
                    @Override
                    public String getPath() {
                        return PATH_PROGRAMMATIC_EP;
                    }
                    @Override
                    public List<Extension> getExtensions() {
                        return Collections.emptyList();
                    }
                    @Override
                    public Class<?> getEndpointClass() {
                        return ProgrammaticEndpoint.class;
                    }
                    @Override
                    public Configurator getConfigurator() {
                        return new ServerEndpointConfig.Configurator() {
                        };
                    }
                });
            } catch (DeploymentException e) {
                throw new IllegalStateException(e);
            }
        }
    }


    @Test
    public void testUnsupportedObject() throws Exception{
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(ProgramaticServerEndpointConfig.class.getName());
        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        WebSocketContainer wsContainer = ContainerProvider.getWebSocketContainer();

        tomcat.start();

        Client client = new Client();
        URI uri = new URI("ws://localhost:" + getPort() + PATH_PROGRAMMATIC_EP);
        Session session = wsContainer.connectToServer(client, uri);

        // This should fail
        Object msg1 = new Object();
        try {
            session.getBasicRemote().sendObject(msg1);
            Assert.fail("No exception thrown ");
        } catch (EncodeException e) {
            // Expected
        } catch (Throwable t) {
            Assert.fail("Wrong exception type");
        } finally {
            session.close();
        }
    }
}
