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

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.res.StringManager;

/**
 * Registers an interest in any class that is annotated with
 * {@link ServerEndpoint} so that Endpoint can be published via the WebSocket
 * server.
 */
@HandlesTypes({ServerEndpoint.class, ServerApplicationConfig.class,
        Endpoint.class})
public class WsSci implements ServletContainerInitializer {

    private static boolean logMessageWritten = false;

    private static final Log log =
            LogFactory.getLog(WsSci.class);
    private static final StringManager sm =
            StringManager.getManager(Constants.PACKAGE_NAME);

    @Override
    public void onStartup(Set<Class<?>> clazzes, ServletContext ctx)
            throws ServletException {

        if (!isJava7OrLater()) {
            // The WebSocket implementation requires Java 7 so don't initialise
            // it if Java 7 is not available.
            if (!logMessageWritten) {
                logMessageWritten = true;
                log.info(sm.getString("sci.noWebSocketSupport"));
            }
            return;
        }

        WsServerContainer sc = init(ctx, true);

        if (clazzes == null || clazzes.size() == 0) {
            return;
        }

        // Group the discovered classes by type
        Set<ServerApplicationConfig> serverApplicationConfigs = new HashSet<ServerApplicationConfig>();
        Set<Class<? extends Endpoint>> scannedEndpointClazzes = new HashSet<Class<? extends Endpoint>>();
        Set<Class<?>> scannedPojoEndpoints = new HashSet<Class<?>>();

        try {
            // wsPackage is "javax.websocket."
            String wsPackage = ContainerProvider.class.getName();
            wsPackage = wsPackage.substring(0, wsPackage.lastIndexOf('.') + 1);
            for (Class<?> clazz : clazzes) {
                int modifiers = clazz.getModifiers();
                if (!Modifier.isPublic(modifiers) ||
                        Modifier.isAbstract(modifiers)) {
                    // Non-public or abstract - skip it.
                    continue;
                }
                // Protect against scanning the WebSocket API JARs
                if (clazz.getName().startsWith(wsPackage)) {
                    continue;
                }
                if (ServerApplicationConfig.class.isAssignableFrom(clazz)) {
                    serverApplicationConfigs.add(
                            (ServerApplicationConfig) clazz.newInstance());
                }
                if (Endpoint.class.isAssignableFrom(clazz)) {
                    @SuppressWarnings("unchecked")
                    Class<? extends Endpoint> endpoint =
                            (Class<? extends Endpoint>) clazz;
                    scannedEndpointClazzes.add(endpoint);
                }
                if (clazz.isAnnotationPresent(ServerEndpoint.class)) {
                    scannedPojoEndpoints.add(clazz);
                }
            }
        } catch (InstantiationException e) {
            throw new ServletException(e);
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        }

        // Filter the results
        Set<ServerEndpointConfig> filteredEndpointConfigs = new HashSet<ServerEndpointConfig>();
        Set<Class<?>> filteredPojoEndpoints = new HashSet<Class<?>>();

        if (serverApplicationConfigs.isEmpty()) {
            filteredPojoEndpoints.addAll(scannedPojoEndpoints);
        } else {
            for (ServerApplicationConfig config : serverApplicationConfigs) {
                Set<ServerEndpointConfig> configFilteredEndpoints =
                        config.getEndpointConfigs(scannedEndpointClazzes);
                if (configFilteredEndpoints != null) {
                    filteredEndpointConfigs.addAll(configFilteredEndpoints);
                }
                Set<Class<?>> configFilteredPojos =
                        config.getAnnotatedEndpointClasses(
                                scannedPojoEndpoints);
                if (configFilteredPojos != null) {
                    filteredPojoEndpoints.addAll(configFilteredPojos);
                }
            }
        }

        try {
            // Deploy endpoints
            for (ServerEndpointConfig config : filteredEndpointConfigs) {
                sc.addEndpoint(config);
            }
            // Deploy POJOs
            for (Class<?> clazz : filteredPojoEndpoints) {
                sc.addEndpoint(clazz);
            }
        } catch (DeploymentException e) {
            throw new ServletException(e);
        }
    }


    static WsServerContainer init(ServletContext servletContext,
            boolean initBySciMechanism) {

        WsServerContainer sc = new WsServerContainer(servletContext);

        servletContext.setAttribute(
                Constants.SERVER_CONTAINER_SERVLET_CONTEXT_ATTRIBUTE, sc);

        servletContext.addListener(new WsSessionListener(sc));
        // Can't register the ContextListener again if the ContextListener is
        // calling this method
        if (initBySciMechanism) {
            servletContext.addListener(new WsContextListener());
        }

        return sc;
    }


    private static boolean isJava7OrLater() {
        try {
            Class.forName("java.nio.channels.AsynchronousSocketChannel");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
