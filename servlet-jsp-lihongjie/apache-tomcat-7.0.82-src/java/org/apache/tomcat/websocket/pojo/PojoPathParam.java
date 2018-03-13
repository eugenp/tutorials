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

/**
 * Stores the parameter type and name for a parameter that needs to be passed to
 * an onXxx method of {@link javax.websocket.Endpoint}. The name is only present
 * for parameters annotated with
 * {@link javax.websocket.server.PathParam}. For the
 * {@link javax.websocket.Session} and {@link java.lang.Throwable} parameters,
 * {@link #getName()} will always return <code>null</code>.
 */
public class PojoPathParam {

    private final Class<?> type;
    private final String name;


    public PojoPathParam(Class<?> type, String name) {
        this.type = type;
        this.name = name;
    }


    public Class<?> getType() {
        return type;
    }


    public String getName() {
        return name;
    }
}
