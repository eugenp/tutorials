/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tomcat.websocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.Extension;

/**
 * Internal implementation constants.
 */
public class Constants {

    protected static final String PACKAGE_NAME =
            Constants.class.getPackage().getName();
    // OP Codes
    public static final byte OPCODE_CONTINUATION = 0x00;
    public static final byte OPCODE_TEXT = 0x01;
    public static final byte OPCODE_BINARY = 0x02;
    public static final byte OPCODE_CLOSE = 0x08;
    public static final byte OPCODE_PING = 0x09;
    public static final byte OPCODE_PONG = 0x0A;

    // Internal OP Codes
    // RFC 6455 limits OP Codes to 4 bits so these should never clash
    // Always set bit 4 so these will be treated as control codes
    static final byte INTERNAL_OPCODE_FLUSH = 0x18;

    // Buffers
    static final int DEFAULT_BUFFER_SIZE = Integer.getInteger(
            "org.apache.tomcat.websocket.DEFAULT_BUFFER_SIZE", 8 * 1024)
            .intValue();

    // Client connection
    // RFC 2068 recommended a limit of 5
    // Most browsers have a default limit of 20
    public static final String MAX_REDIRECTIONS_PROPERTY =
            "org.apache.tomcat.websocket.MAX_REDIRECTIONS";
    public static final int MAX_REDIRECTIONS_DEFAULT = 20;

    // HTTP upgrade header names and values
    public static final String HOST_HEADER_NAME = "Host";
    public static final String UPGRADE_HEADER_NAME = "Upgrade";
    public static final String UPGRADE_HEADER_VALUE = "websocket";
    public static final String ORIGIN_HEADER_NAME = "Origin";
    public static final String CONNECTION_HEADER_NAME = "Connection";
    public static final String CONNECTION_HEADER_VALUE = "upgrade";
    public static final String LOCATION_HEADER_NAME = "Location";
    public static final String WS_VERSION_HEADER_NAME = "Sec-WebSocket-Version";
    public static final String WS_VERSION_HEADER_VALUE = "13";
    public static final String WS_KEY_HEADER_NAME = "Sec-WebSocket-Key";
    public static final String WS_PROTOCOL_HEADER_NAME =
            "Sec-WebSocket-Protocol";
    public static final String WS_EXTENSIONS_HEADER_NAME =
            "Sec-WebSocket-Extensions";

    /// HTTP redirection status codes
    public static final int MULTIPLE_CHOICES = 300;
    public static final int MOVED_PERMANENTLY = 301;
    public static final int FOUND = 302;
    public static final int SEE_OTHER = 303;
    public static final int USE_PROXY = 305;
    public static final int TEMPORARY_REDIRECT = 307;

    // Configuration for Origin header in client
    static final String DEFAULT_ORIGIN_HEADER_VALUE =
            System.getProperty("org.apache.tomcat.websocket.DEFAULT_ORIGIN_HEADER_VALUE");

    // Configuration for background processing checks intervals
    static final int DEFAULT_PROCESS_PERIOD = Integer.getInteger(
            "org.apache.tomcat.websocket.DEFAULT_PROCESS_PERIOD", 10)
            .intValue();

    /* Configuration for extensions
     * Note: These options are primarily present to enable this implementation
     *       to pass compliance tests. They are expected to be removed once
     *       the WebSocket API includes a mechanism for adding custom extensions
     *       and disabling built-in extensions.
     */
    static final boolean DISABLE_BUILTIN_EXTENSIONS =
            Boolean.getBoolean("org.apache.tomcat.websocket.DISABLE_BUILTIN_EXTENSIONS");
    static final boolean ALLOW_UNSUPPORTED_EXTENSIONS =
            Boolean.getBoolean("org.apache.tomcat.websocket.ALLOW_UNSUPPORTED_EXTENSIONS");

    // Configuration for stream behavior
    static final boolean STREAMS_DROP_EMPTY_MESSAGES =
            Boolean.getBoolean("org.apache.tomcat.websocket.STREAMS_DROP_EMPTY_MESSAGES");

    public static final boolean STRICT_SPEC_COMPLIANCE =
            Boolean.getBoolean("org.apache.tomcat.websocket.STRICT_SPEC_COMPLIANCE");

    public static final List<Extension> INSTALLED_EXTENSIONS;

    static {
        if (DISABLE_BUILTIN_EXTENSIONS) {
            INSTALLED_EXTENSIONS = Collections.unmodifiableList(new ArrayList<Extension>());
        } else {
            List<Extension> installed = new ArrayList<Extension>(1);
            installed.add(new WsExtension("permessage-deflate"));
            INSTALLED_EXTENSIONS = Collections.unmodifiableList(installed);
        }
    }

    private Constants() {
        // Hide default constructor
    }
}
