/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.websocket.server;

import java.util.Set;

import javax.websocket.Endpoint;

/**
 * Applications may provide an implementation of this interface to filter the
 * discovered WebSocket endpoints that are deployed. Implementations of this
 * class will be discovered via an ServletContainerInitializer scan.
 */
public interface ServerApplicationConfig {

    /**
     * Enables applications to filter the discovered implementations of
     * {@link ServerEndpointConfig}.
     *
     * @param scanned   The {@link Endpoint} implementations found in the
     *                  application
     * @return  The set of configurations for the endpoint the application
     *              wishes to deploy
     */
    Set<ServerEndpointConfig> getEndpointConfigs(
            Set<Class<? extends Endpoint>> scanned);

    /**
     * Enables applications to filter the discovered classes annotated with
     * {@link ServerEndpoint}.
     *
     * @param scanned   The POJOs annotated with {@link ServerEndpoint} found in
     *                  the application
     * @return  The set of POJOs the application wishes to deploy
     */
    Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned);
}
