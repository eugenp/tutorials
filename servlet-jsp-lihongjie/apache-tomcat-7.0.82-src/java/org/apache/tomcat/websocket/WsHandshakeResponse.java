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
package org.apache.tomcat.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.HandshakeResponse;

/**
 * Represents the response to a WebSocket handshake.
 */
public class WsHandshakeResponse implements HandshakeResponse {

    private final Map<String,List<String>> headers = new CaseInsensitiveKeyMap<List<String>>();


    public WsHandshakeResponse() {
    }


    public WsHandshakeResponse(Map<String,List<String>> headers) {
        for (Entry<String,List<String>> entry : headers.entrySet()) {
            if (this.headers.containsKey(entry.getKey())) {
                this.headers.get(entry.getKey()).addAll(entry.getValue());
            } else {
                List<String> values = new ArrayList<String>(entry.getValue());
                this.headers.put(entry.getKey(), values);
            }
        }
    }


    @Override
    public Map<String,List<String>> getHeaders() {
        return headers;
    }
}
