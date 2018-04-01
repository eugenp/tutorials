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
package org.apache.catalina.websocket;

/**
 * Constants for this Java package.
 * 
 * @deprecated  Replaced by the JSR356 WebSocket 1.1 implementation and will be
 *              removed in Tomcat 8.0.x.  
 */
@Deprecated
public class Constants {
    public static final String Package = "org.apache.catalina.websocket";

    // OP Codes
    public static final byte OPCODE_CONTINUATION = 0x00;
    public static final byte OPCODE_TEXT = 0x01;
    public static final byte OPCODE_BINARY = 0x02;
    public static final byte OPCODE_CLOSE = 0x08;
    public static final byte OPCODE_PING = 0x09;
    public static final byte OPCODE_PONG = 0x0A;

    // Status Codes
    // Definitions as per RFC 6455 (http://tools.ietf.org/html/rfc6455)
    /**
     * 1000 indicates a normal closure, meaning whatever purpose the
     * connection was established for has been fulfilled.
     */
    public static final int STATUS_CLOSE_NORMAL = 1000;

    /**
     * 1001 indicates that an endpoint is "going away", such as a server
     * going down, or a browser having navigated away from a page.
     */
    public static final int STATUS_SHUTDOWN = 1001;

    /**
     * 1002 indicates that an endpoint is terminating the connection due
     * to a protocol error.
     */
    public static final int STATUS_PROTOCOL_ERROR = 1002;

    /**
     * 1003 indicates that an endpoint is terminating the connection
     * because it has received a type of data it cannot accept (e.g. an
     * endpoint that understands only text data MAY send this if it
     * receives a binary message).
     */
    public static final int STATUS_UNEXPECTED_DATA_TYPE = 1003;

    // 1004 is reserved. The specific meaning might be defined in the future.

    /**
     * 1005 is a reserved value and MUST NOT be set as a status code in a
     * Close control frame by an endpoint.  It is designated for use in
     * applications expecting a status code to indicate that no status
     * code was actually present.
     */
    public static final int STATUS_CODE_MISSING = 1005;

    /**
     * 1006 is a reserved value and MUST NOT be set as a status code in a
     * Close control frame by an endpoint.  It is designated for use in
     * applications expecting a status code to indicate that the
     * connection was closed abnormally, e.g. without sending or
     * receiving a Close control frame.
     */
    public static final int STATUS_CLOSED_UNEXPECTEDLY = 1006;

    /**
     * 1007 indicates that an endpoint is terminating the connection
     * because it has received data within a message that was not
     * consistent with the type of the message (e.g., non-UTF-8 [RFC3629]
     * data within a text message).
     */
    public static final int STATUS_BAD_DATA = 1007;

    /**
     * 1008 indicates that an endpoint is terminating the connection
     * because it has received a message that violates its policy.  This
     * is a generic status code that can be returned when there is no
     * other more suitable status code (e.g. 1003 or 1009), or if there
     * is a need to hide specific details about the policy.
     */
    public static final int STATUS_POLICY_VIOLATION = 1008;

    /**
     * 1009 indicates that an endpoint is terminating the connection
     * because it has received a message which is too big for it to
     * process.
     */
    public static final int STATUS_MESSAGE_TOO_LARGE = 1009;

    /**
     * 1010 indicates that an endpoint (client) is terminating the
     * connection because it has expected the server to negotiate one or
     * more extension, but the server didn't return them in the response
     * message of the WebSocket handshake.  The list of extensions which
     * are needed SHOULD appear in the /reason/ part of the Close frame.
     * Note that this status code is not used by the server, because it
     * can fail the WebSocket handshake instead.
     */
    public static final int STATUS_REQUIRED_EXTENSION = 1010;

    /**
     * 1011 indicates that a server is terminating the connection because it
     * encountered an unexpected condition that prevented it from fulfilling the
     * request.
     */
    public static final int STATUS_UNEXPECTED_CONDITION = 1011;
}
