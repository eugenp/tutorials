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
import java.util.ArrayList;
import java.util.List;

import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.websocket.pojo.TesterUtil.ServerConfigListener;
import org.apache.tomcat.websocket.pojo.TesterUtil.SimpleClient;
import org.apache.tomcat.websocket.pojo.TesterUtil.SingletonConfigurator;

public class TestPojoMethodMapping extends TomcatBaseTest {

    private static final String PARAM_ONE = "abcde";
    private static final String PARAM_TWO = "12345";
    private static final String PARAM_THREE = "true";

    @Test
    public void test() throws Exception {

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

        SimpleClient client = new SimpleClient();
        URI uri = new URI("ws://localhost:" + getPort() + "/" + PARAM_ONE +
                "/" + PARAM_TWO + "/" + PARAM_THREE);

        Session session = wsContainer.connectToServer(client, uri);
        session.getBasicRemote().sendText("NO-OP");
        session.close();

        // Give server 20s to close. 5s should be plenty but the Gump VM is slow
        int count = 0;
        while (count < 200) {
            if (server.isClosed()) {
                break;
            }
            count++;
            Thread.sleep(100);
        }
        if (count == 50) {
            Assert.fail("Server did not process an onClose event within 5 " +
                    "seconds of the client sending a close message");
        }

        // Check no errors
        List<String> errors = server.getErrors();
        for (String error : errors) {
            System.err.println(error);
        }
        Assert.assertEquals("Found errors", 0, errors.size());
    }


    @ServerEndpoint(value="/{one}/{two}/{three}",
            configurator=SingletonConfigurator.class)
    public static final class Server {

        private final List<String> errors = new ArrayList<String>();
        private volatile boolean closed;

        @OnOpen
        public void onOpen(@PathParam("one") String p1, @PathParam("two")int p2,
                @PathParam("three")boolean p3) {
            checkParams("onOpen", p1, p2, p3);
        }

        @OnMessage
        public void onMessage(@SuppressWarnings("unused") String msg,
                @PathParam("one") String p1, @PathParam("two")int p2,
                @PathParam("three")boolean p3) {
            checkParams("onMessage", p1, p2, p3);
        }

        @OnClose
        public void onClose(@PathParam("one") String p1,
                @PathParam("two")int p2, @PathParam("three")boolean p3) {
            checkParams("onClose", p1, p2, p3);
            closed = true;
        }

        public List<String> getErrors() {
            return errors;
        }

        public boolean isClosed() {
            return closed;
        }

        private void checkParams(String method, String p1, int p2, boolean p3) {
            checkParam(method, PARAM_ONE, p1);
            checkParam(method, PARAM_TWO, Integer.toString(p2));
            checkParam(method, PARAM_THREE, Boolean.toString(p3));
        }

        private void checkParam(String method, String expected, String actual) {
            if (!expected.equals(actual)) {
                errors.add("Method [" + method + "]. Expected [" + expected +
                        "] was + [" + actual + "]");
            }
        }
    }
}
