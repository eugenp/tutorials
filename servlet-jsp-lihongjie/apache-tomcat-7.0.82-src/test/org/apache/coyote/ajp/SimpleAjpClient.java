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
package org.apache.coyote.ajp;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Locale;

import javax.net.SocketFactory;

/**
 * AJP client that is not (yet) a full AJP client implementation as it just
 * provides the functionality required for the unit tests. The client uses
 * blocking IO throughout.
 */
public class SimpleAjpClient {

    private static final int DEFAULT_AJP_PACKET_SIZE = 8192;
    private static final byte[] AJP_CPING;

    static {
        TesterAjpMessage ajpCping = new TesterAjpMessage(16);
        ajpCping.reset();
        ajpCping.appendByte(Constants.JK_AJP13_CPING_REQUEST);
        ajpCping.end();
        AJP_CPING = new byte[ajpCping.getLen()];
        System.arraycopy(ajpCping.getBuffer(), 0, AJP_CPING, 0,
                ajpCping.getLen());
    }

    private final int packetSize;
    private String host = "localhost";
    private int port = -1;
    /* GET == 2 */
    private int method = 2;
    private String protocol = "http";
    private String uri = "/";
    private String remoteAddr = "192.168.0.1";
    private String remoteHost = "client.example.com";
    private String serverName = "www.example.com";
    private int serverPort = 80;
    private boolean ssl = false;
    private Socket socket = null;

    public SimpleAjpClient() {
        this(DEFAULT_AJP_PACKET_SIZE);
    }

    public SimpleAjpClient(int packetSize) {
        this.packetSize = packetSize;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setMethod(String method) {
        method = method.toUpperCase(Locale.ENGLISH);
        if (method.equals("OPTIONS")) {
            this.method = 1;
        } else if (method.equals("GET")) {
            this.method = 2;
        } else if (method.equals("HEAD")) {
            this.method = 3;
        } else if (method.equals("POST")) {
            this.method = 4;
        } else if (method.equals("PUT")) {
            this.method = 5;
        } else if (method.equals("DELETE")) {
            this.method = 6;
        } else if (method.equals("TRACE")) {
            this.method = 7;
        } else if (method.equals("PROPFIND")) {
            this.method = 8;
        } else if (method.equals("PROPPATCH")) {
            this.method = 9;
        } else if (method.equals("MKCOL")) {
            this.method = 10;
        } else if (method.equals("COPY")) {
            this.method = 11;
        } else if (method.equals("MOVE")) {
            this.method = 12;
        } else if (method.equals("LOCK")) {
            this.method = 13;
        } else if (method.equals("UNLOCK")) {
            this.method = 14;
        } else if (method.equals("ACL")) {
            this.method = 15;
        } else if (method.equals("REPORT")) {
            this.method = 16;
        } else if (method.equals("VERSION-CONTROL")) {
            this.method = 17;
        } else if (method.equals("CHECKIN")) {
            this.method = 18;
        } else if (method.equals("CHECKOUT")) {
            this.method = 19;
        } else if (method.equals("UNCHECKOUT")) {
            this.method = 20;
        } else if (method.equals("SEARCH")) {
            this.method = 21;
        } else if (method.equals("MKWORKSPACE")) {
            this.method = 22;
        } else if (method.equals("UPDATE")) {
            this.method = 23;
        } else if (method.equals("LABEL")) {
            this.method = 24;
        } else if (method.equals("MERGE")) {
            this.method = 25;
        } else if (method.equals("BASELINE-CONTROL")) {
            this.method = 26;
        } else if (method.equals("MKACTIVITY")) {
            this.method = 27;
        } else {
            this.method = 99;
        }
    }

    public String getMethod() {
        switch (method) {
            case 1:
                return "OPTIONS";
            case 2:
                return "GET";
            case 3:
                return "HEAD";
            case 4:
                return "POST";
            case 5:
                return "PUT";
            case 6:
                return "DELETE";
            case 7:
                return "TRACE";
            case 8:
                return "PROPFIND";
            case 9:
                return "PROPPATCH";
            case 10:
                return "MKCOL";
            case 11:
                return "COPY";
            case 12:
                return "MOVE";
            case 13:
                return "LOCK";
            case 14:
                return "UNLOCK";
            case 15:
                return "ACL";
            case 16:
                return "REPORT";
            case 17:
                return "VERSION-CONTROL";
            case 18:
                return "CHECKIN";
            case 19:
                return "CHECKOUT";
            case 20:
                return "UNCHECKOUT";
            case 21:
                return "SEARCH";
            case 22:
                return "MKWORKSPACE";
            case 23:
                return "UPDATE";
            case 24:
                return "LABEL";
            case 25:
                return "MERGE";
            case 26:
                return "BASELINE-CONTROL";
            case 27:
                return "MKACTIVITY";
            default:
                return "UNKNOWN";
        }
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void connect() throws IOException {
        socket = SocketFactory.getDefault().createSocket(host, port);
    }

    public void disconnect() throws IOException {
        socket.close();
        socket = null;
    }

    /**
     * Create a message to request the given URL.
     */
    public TesterAjpMessage createForwardMessage() {

        TesterAjpMessage message = new TesterAjpMessage(packetSize);
        message.reset();

        // Set the header bytes
        message.getBuffer()[0] = 0x12;
        message.getBuffer()[1] = 0x34;

        // Code 2 for forward request
        message.appendByte(Constants.JK_AJP13_FORWARD_REQUEST);

        // HTTP method, GET = 2
        message.appendByte(method);

        // Protocol
        message.appendString(protocol);

        // Request URI
        message.appendString(uri);

        // Client address
        message.appendString(remoteAddr);

        // Client host
        message.appendString(remoteHost);

        // Server name
        message.appendString(serverName);

        // Server port
        message.appendInt(serverPort);

        // Is ssl
        message.appendByte(ssl ? 0x01 : 0x00);

        return message;
    }

    public TesterAjpMessage createBodyMessage(byte[] data) {

        TesterAjpMessage message = new TesterAjpMessage(packetSize);
        message.reset();

        // Set the header bytes
        message.getBuffer()[0] = 0x12;
        message.getBuffer()[1] = 0x34;

        message.appendBytes(data, 0, data.length);
        message.end();

        return message;
    }


    /**
     * Sends an TesterAjpMessage to the server and returns the response message.
     */
    public TesterAjpMessage sendMessage(TesterAjpMessage headers)
            throws IOException {
        return sendMessage(headers, null);
    }

    public TesterAjpMessage sendMessage(TesterAjpMessage headers,
            TesterAjpMessage body) throws IOException {
        // Send the headers
        socket.getOutputStream().write(
                headers.getBuffer(), 0, headers.getLen());
        if (body != null) {
            // Send the body of present
            socket.getOutputStream().write(
                    body.getBuffer(), 0, body.getLen());
        }
        // Read the response
        return readMessage();
    }

    /**
     * Tests the connection to the server and returns the CPONG response.
     */
    public TesterAjpMessage cping() throws IOException {
        // Send the ping message
        socket.getOutputStream().write(AJP_CPING);
        // Read the response
        return readMessage();
    }

    /**
     * Reads a message from the server.
     */
    public TesterAjpMessage readMessage() throws IOException {

        InputStream is = socket.getInputStream();

        TesterAjpMessage message = new TesterAjpMessage(packetSize);

        byte[] buf = message.getBuffer();
        int headerLength = message.getHeaderLength();

        read(is, buf, 0, headerLength);

        int messageLength = message.processHeader(false);
        if (messageLength < 0) {
            throw new IOException("Invalid AJP message length");
        } else if (messageLength == 0) {
            return message;
        } else {
            if (messageLength > buf.length) {
                throw new IllegalArgumentException("Message too long [" +
                        Integer.valueOf(messageLength) +
                        "] for buffer length [" +
                        Integer.valueOf(buf.length) + "]");
            }
            read(is, buf, headerLength, messageLength);
            return message;
        }
    }

    protected boolean read(InputStream is, byte[] buf, int pos, int n)
        throws IOException {

        int read = 0;
        int res = 0;
        while (read < n) {
            res = is.read(buf, read + pos, n - read);
            if (res > 0) {
                read += res;
            } else {
                throw new IOException("Read failed");
            }
        }
        return true;
    }
}
