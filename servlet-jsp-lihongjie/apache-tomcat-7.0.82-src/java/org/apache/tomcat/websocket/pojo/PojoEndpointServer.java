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

import java.util.Map;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpointConfig;

import org.apache.tomcat.util.res.StringManager;

/**
 * Wrapper class for instances of POJOs annotated with
 * {@link javax.websocket.server.ServerEndpoint} so they appear as standard
 * {@link javax.websocket.Endpoint} instances.
 */
public class PojoEndpointServer extends PojoEndpointBase {

    private static final StringManager sm =
            StringManager.getManager(Constants.PACKAGE_NAME);

    public static final String POJO_PATH_PARAM_KEY =
            "org.apache.tomcat.websocket.pojo.PojoEndpoint.pathParams";
    public static final String POJO_METHOD_MAPPING_KEY =
            "org.apache.tomcat.websocket.pojo.PojoEndpoint.methodMapping";


    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

        ServerEndpointConfig sec = (ServerEndpointConfig) endpointConfig;

        Object pojo;
        try {
            pojo = sec.getConfigurator().getEndpointInstance(
                    sec.getEndpointClass());
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(sm.getString(
                    "pojoEndpointServer.getPojoInstanceFail",
                    sec.getEndpointClass().getName()), e);
        }
        setPojo(pojo);

        @SuppressWarnings("unchecked")
        Map<String,String> pathParameters =
                (Map<String, String>) sec.getUserProperties().get(
                        POJO_PATH_PARAM_KEY);
        setPathParameters(pathParameters);

        PojoMethodMapping methodMapping =
                (PojoMethodMapping) sec.getUserProperties().get(
                        POJO_METHOD_MAPPING_KEY);
        setMethodMapping(methodMapping);

        doOnOpen(session, endpointConfig);
    }
}
