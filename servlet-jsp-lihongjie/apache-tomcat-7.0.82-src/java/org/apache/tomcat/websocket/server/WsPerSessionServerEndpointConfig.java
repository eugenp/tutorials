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
package org.apache.tomcat.websocket.server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Extension;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Wraps the provided {@link ServerEndpointConfig} and provides a per session
 * view - the difference being that the map returned by {@link
 * #getUserProperties()} is unique to this instance rather than shared with the
 * wrapped {@link ServerEndpointConfig}.
 */
class WsPerSessionServerEndpointConfig implements ServerEndpointConfig {

    private final ServerEndpointConfig perEndpointConfig;
    private final Map<String,Object> perSessionUserProperties =
            new ConcurrentHashMap<String,Object>();

    WsPerSessionServerEndpointConfig(ServerEndpointConfig perEndpointConfig) {
        this.perEndpointConfig = perEndpointConfig;
        perSessionUserProperties.putAll(perEndpointConfig.getUserProperties());
    }

    @Override
    public List<Class<? extends Encoder>> getEncoders() {
        return perEndpointConfig.getEncoders();
    }

    @Override
    public List<Class<? extends Decoder>> getDecoders() {
        return perEndpointConfig.getDecoders();
    }

    @Override
    public Map<String,Object> getUserProperties() {
        return perSessionUserProperties;
    }

    @Override
    public Class<?> getEndpointClass() {
        return perEndpointConfig.getEndpointClass();
    }

    @Override
    public String getPath() {
        return perEndpointConfig.getPath();
    }

    @Override
    public List<String> getSubprotocols() {
        return perEndpointConfig.getSubprotocols();
    }

    @Override
    public List<Extension> getExtensions() {
        return perEndpointConfig.getExtensions();
    }

    @Override
    public Configurator getConfigurator() {
        return perEndpointConfig.getConfigurator();
    }
}
