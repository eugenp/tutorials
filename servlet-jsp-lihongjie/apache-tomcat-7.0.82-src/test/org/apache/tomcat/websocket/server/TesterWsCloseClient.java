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
package org.apache.tomcat.websocket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.websocket.CloseReason.CloseCode;

import org.apache.tomcat.util.buf.B2CConverter;

/**
 * A client for testing Websocket behavior that differs from standard client
 * behavior.
 */
public class TesterWsCloseClient {

    private static final byte[] maskingKey = new byte[] { 0x12, 0x34, 0x56,
            0x78 };

    private final Socket socket;

    public TesterWsCloseClient(String host, int port) throws Exception {
        this.socket = new Socket(host, port);
        // Set read timeout in case of failure so test doesn't hang
        socket.setSoTimeout(2000);
        // Disable Nagle's algorithm to ensure packets sent immediately
        // TODO: Hoping this causes writes to wait for a TCP ACK for TCP RST
        // test cases but I'm not sure?
        socket.setTcpNoDelay(true);
    }

    public void httpUpgrade(String path) throws IOException {
        String req = createUpgradeRequest(path);
        write(req.getBytes(B2CConverter.UTF_8));
        readUpgradeResponse();
    }

    public void sendMessage(String text) throws IOException {
        write(createFrame(true, 1, text.getBytes(B2CConverter.UTF_8)));
    }

    public void sendCloseFrame(CloseCode closeCode) throws IOException {
        int code = closeCode.getCode();
        byte[] codeBytes = new byte[2];
        codeBytes[0] = (byte) (code >> 8);
        codeBytes[1] = (byte) code;
        write(createFrame(true, 8, codeBytes));
    }

    private void readUpgradeResponse() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        String line = in.readLine();
        while (line != null && !line.isEmpty()) {
            line = in.readLine();
        }
    }

    public void closeSocket() throws IOException {
        // Enable SO_LINGER to ensure close() only returns when TCP closing
        // handshake completes
        socket.setSoLinger(true, 65535);
        socket.close();
    }

    /*
     * Send a TCP RST instead of a TCP closing handshake
     */
    public void forceCloseSocket() throws IOException {
        // SO_LINGER sends a TCP RST when timeout expires
        socket.setSoLinger(true, 0);
        socket.close();
    }

    private void write(byte[] bytes) throws IOException {
        socket.getOutputStream().write(bytes);
        socket.getOutputStream().flush();
    }

    private static String createUpgradeRequest(String path) {
        String[] upgradeRequestLines = { "GET " + path + " HTTP/1.1",
                "Connection: Upgrade", "Host: localhost:8080",
                "Origin: localhost:8080",
                "Sec-WebSocket-Key: OEvAoAKn5jsuqv2/YJ1Wfg==",
                "Sec-WebSocket-Version: 13", "Upgrade: websocket" };
        StringBuffer sb = new StringBuffer();
        for (String line : upgradeRequestLines) {
            sb.append(line);
            sb.append("\r\n");
        }
        sb.append("\r\n");
        return sb.toString();
    }

    private static byte[] createFrame(boolean fin, int opCode, byte[] payload) {
        byte[] frame = new byte[6 + payload.length];
        frame[0] = (byte) (opCode | (fin ? 1 << 7 : 0));
        frame[1] = (byte) (0x80 | payload.length);

        frame[2] = maskingKey[0];
        frame[3] = maskingKey[1];
        frame[4] = maskingKey[2];
        frame[5] = maskingKey[3];

        for (int i = 0; i < payload.length; i++) {
            frame[i + 6] = (byte) (payload[i] ^ maskingKey[i % 4]);
        }

        return frame;
    }
}
