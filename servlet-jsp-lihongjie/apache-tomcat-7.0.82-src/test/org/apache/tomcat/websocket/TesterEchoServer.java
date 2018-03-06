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

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.servlet.ServletContextEvent;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;

import org.apache.tomcat.websocket.server.Constants;
import org.apache.tomcat.websocket.server.WsContextListener;

public class TesterEchoServer {

    public static class Config extends WsContextListener {

        public static final String PATH_ASYNC = "/echoAsync";
        public static final String PATH_BASIC = "/echoBasic";
        public static final String PATH_BASIC_LIMIT_LOW = "/echoBasicLimitLow";
        public static final String PATH_BASIC_LIMIT_HIGH = "/echoBasicLimitHigh";

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            super.contextInitialized(sce);
            ServerContainer sc =
                    (ServerContainer) sce.getServletContext().getAttribute(
                            Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE);
            try {
                sc.addEndpoint(Async.class);
                sc.addEndpoint(Basic.class);
                sc.addEndpoint(BasicLimitLow.class);
                sc.addEndpoint(BasicLimitHigh.class);
                sc.addEndpoint(RootEcho.class);
            } catch (DeploymentException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @ServerEndpoint("/echoAsync")
    public static class Async {

        @OnMessage
        public void echoTextMessage(Session session, String msg, boolean last) {
            try {
                session.getBasicRemote().sendText(msg, last);
            } catch (IOException e) {
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
            }
        }


        @OnMessage
        public void echoBinaryMessage(Session session, ByteBuffer msg,
                boolean last) {
            try {
                session.getBasicRemote().sendBinary(msg, last);
            } catch (IOException e) {
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
            }
        }
    }


    @ServerEndpoint("/echoBasic")
    public static class Basic {
        @OnMessage
        public void echoTextMessage(Session session, String msg) {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
            }
        }


        @OnMessage
        public void echoBinaryMessage(Session session, ByteBuffer msg) {
            try {
                session.getBasicRemote().sendBinary(msg);
            } catch (IOException e) {
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
            }
        }
    }


    @ServerEndpoint("/echoBasicLimitLow")
    public static class BasicLimitLow {

        public static final long MAX_SIZE = 10;

        @OnMessage(maxMessageSize = MAX_SIZE)
        public void echoTextMessage(Session session, String msg) {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
            }
        }


        @OnMessage(maxMessageSize = MAX_SIZE)
        public void echoBinaryMessage(Session session, ByteBuffer msg) {
            try {
                session.getBasicRemote().sendBinary(msg);
            } catch (IOException e) {
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
            }
        }
    }


    @ServerEndpoint("/echoBasicLimitHigh")
    public static class BasicLimitHigh {

        public static final long MAX_SIZE = 32 * 1024;

        @OnMessage(maxMessageSize = MAX_SIZE)
        public void echoTextMessage(Session session, String msg) {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
            }
        }


        @OnMessage(maxMessageSize = MAX_SIZE)
        public void echoBinaryMessage(Session session, ByteBuffer msg) {
            try {
                session.getBasicRemote().sendBinary(msg);
            } catch (IOException e) {
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
            }
        }
    }


    @ServerEndpoint("/")
    public static class RootEcho {

        @OnMessage
        public void echoTextMessage(Session session, String msg) {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
            }
        }
    }
}
