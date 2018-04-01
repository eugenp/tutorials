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

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Represents the HTTP request that asked to be upgraded to WebSocket.
 */
public interface HandshakeRequest {

    static final String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
    static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
    static final String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
    static final String SEC_WEBSOCKET_EXTENSIONS= "Sec-WebSocket-Extensions";

    Map<String,List<String>> getHeaders();

    Principal getUserPrincipal();

    URI getRequestURI();

    boolean isUserInRole(String role);

    /**
     * Get the HTTP Session object associated with this request. Object is used
     * to avoid a direct dependency on the Servlet API.
     * @return The javax.servlet.http.HttpSession object associated with this
     *         request, if any.
     */
    Object getHttpSession();

    Map<String, List<String>> getParameterMap();

    String getQueryString();
}
