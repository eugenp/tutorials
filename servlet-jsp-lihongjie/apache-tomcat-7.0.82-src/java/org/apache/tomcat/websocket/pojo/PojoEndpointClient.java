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

import java.util.Collections;
import java.util.List;

import javax.websocket.Decoder;
import javax.websocket.DeploymentException;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;


/**
 * Wrapper class for instances of POJOs annotated with
 * {@link javax.websocket.ClientEndpoint} so they appear as standard
 * {@link javax.websocket.Endpoint} instances.
 */
public class PojoEndpointClient extends PojoEndpointBase {

    public PojoEndpointClient(Object pojo,
            List<Class<? extends Decoder>> decoders) throws DeploymentException {
        setPojo(pojo);
        setMethodMapping(
                new PojoMethodMapping(pojo.getClass(), decoders, null));
        setPathParameters(Collections.<String,String>emptyMap());
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        doOnOpen(session, config);
    }
}
