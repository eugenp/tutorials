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

import javax.servlet.ServletContextEvent;
import javax.websocket.ClientEndpoint;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.apache.tomcat.websocket.server.Constants;
import org.apache.tomcat.websocket.server.WsContextListener;

public class TesterUtil {

    public static class ServerConfigListener extends WsContextListener {

        private static Class<?> pojoClazz;

        public static void setPojoClazz(Class<?> pojoClazz) {
            ServerConfigListener.pojoClazz = pojoClazz;
        }

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            super.contextInitialized(sce);
            ServerContainer sc =
                    (ServerContainer) sce.getServletContext().getAttribute(
                            Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE);
            try {
                sc.addEndpoint(pojoClazz);
            } catch (DeploymentException e) {
                throw new IllegalStateException(e);
            }
        }
    }


    public static class SingletonConfigurator extends Configurator {

        private static Object instance;

        public static void setInstance(Object instance) {
            SingletonConfigurator.instance = instance;
        }

        @Override
        public <T> T getEndpointInstance(Class<T> clazz)
                throws InstantiationException {
            @SuppressWarnings("unchecked")
            T result = (T) instance;
            return result;
        }
    }


    @ClientEndpoint
    public static final class SimpleClient {
    }
}
