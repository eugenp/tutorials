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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class DefaultServerEndpointConfigurator
        extends ServerEndpointConfig.Configurator {

    @Override
    public <T> T getEndpointInstance(Class<T> clazz)
            throws InstantiationException {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            InstantiationException ie = new InstantiationException();
            ie.initCause(e);
            throw ie;
        }
    }


    @Override
    public String getNegotiatedSubprotocol(List<String> supported,
            List<String> requested) {

        for (String request : requested) {
            if (supported.contains(request)) {
                return request;
            }
        }
        return "";
    }


    @Override
    public List<Extension> getNegotiatedExtensions(List<Extension> installed,
            List<Extension> requested) {
        Set<String> installedNames = new HashSet<String>();
        for (Extension e : installed) {
            installedNames.add(e.getName());
        }
        List<Extension> result = new ArrayList<Extension>();
        for (Extension request : requested) {
            if (installedNames.contains(request.getName())) {
                result.add(request);
            }
        }
        return result;
    }


    @Override
    public boolean checkOrigin(String originHeaderValue) {
        return true;
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec,
            HandshakeRequest request, HandshakeResponse response) {
        // NO-OP
    }

}
