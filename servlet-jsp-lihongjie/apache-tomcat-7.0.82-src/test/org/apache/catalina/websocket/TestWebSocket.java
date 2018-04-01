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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.ApplicationListener;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.catalina.util.Base64;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.C2BConverter;
import org.apache.tomcat.util.buf.CharChunk;
import org.apache.tomcat.websocket.TesterEchoServer;

/**
 * @deprecated  Will be removed in Tomcat 8.0.x.
 */
@Deprecated
public class TestWebSocket extends TomcatBaseTest {

    private static final String CRLF = "\r\n";
    private static final byte[] WS_ACCEPT =
        "258EAFA5-E914-47DA-95CA-C5AB0DC85B11".getBytes(
                B2CConverter.ISO_8859_1);

    @Test
    public void testSimple() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(new ApplicationListener(
                TesterEchoServer.Config.class.getName(), false));

        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        WebSocketClient client = new WebSocketClient(getPort());

        // Send the WebSocket handshake
        client.writer.write("GET " + TesterEchoServer.Config.PATH_BASIC + " HTTP/1.1" + CRLF);
        client.writer.write("Host: foo" + CRLF);
        client.writer.write("Upgrade: websocket" + CRLF);
        client.writer.write("Connection: keep-alive, upgrade" + CRLF);
        client.writer.write("Sec-WebSocket-Version: 13" + CRLF);
        client.writer.write("Sec-WebSocket-Key: TODO" + CRLF);
        client.writer.write(CRLF);
        client.writer.flush();

        // Make sure we got an upgrade response
        String responseLine = client.reader.readLine();
        assertTrue(responseLine.startsWith("HTTP/1.1 101"));

        // Swallow the headers
        String responseHeaderLine = client.reader.readLine();
        while (!responseHeaderLine.equals("")) {
            responseHeaderLine = client.reader.readLine();
        }

        // Now we can do WebSocket
        client.sendMessage("foo", false);
        client.sendMessage("foo", true);

        assertEquals("foofoo", client.readMessage());

        // Finished with the socket
        client.close();
    }

    @Test
    public void testDetectWrongVersion() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(new ApplicationListener(
                TesterEchoServer.Config.class.getName(), false));

        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        WebSocketClient client= new WebSocketClient(getPort());

        // Send the WebSocket handshake
        client.writer.write("GET " + TesterEchoServer.Config.PATH_BASIC + " HTTP/1.1" + CRLF);
        client.writer.write("Host: foo" + CRLF);
        client.writer.write("Upgrade: websocket" + CRLF);
        client.writer.write("Connection: upgrade" + CRLF);
        client.writer.write("Sec-WebSocket-Version: 8" + CRLF);
        client.writer.write("Sec-WebSocket-Key: TODO" + CRLF);
        client.writer.write(CRLF);
        client.writer.flush();

        // Make sure we got an upgrade response
        String responseLine = client.reader.readLine();
        assertTrue(responseLine.startsWith("HTTP/1.1 426"));

        List<String> headerlines = new ArrayList<String>();

        String responseHeaderLine = client.reader.readLine();
        while (!responseHeaderLine.equals("")) {
            headerlines.add(responseHeaderLine);
            responseHeaderLine = client.reader.readLine();
        }

        assertTrue(headerlines.contains("Sec-WebSocket-Version: 13"));
        // Finished with the socket
        client.close();
    }

    @Test
    public void testNoConnection() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(new ApplicationListener(
                TesterEchoServer.Config.class.getName(), false));

        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        WebSocketClient client= new WebSocketClient(getPort());

        // Send the WebSocket handshake
        client.writer.write("GET " + TesterEchoServer.Config.PATH_BASIC + " HTTP/1.1" + CRLF);
        client.writer.write("Host: foo" + CRLF);
        client.writer.write("Upgrade: websocket" + CRLF);
        client.writer.write("Sec-WebSocket-Version: 13" + CRLF);
        client.writer.write("Sec-WebSocket-Key: TODO" + CRLF);
        client.writer.write(CRLF);
        client.writer.flush();

        // Make sure we got an error response
        String responseLine = client.reader.readLine();
        assertTrue(responseLine.startsWith("HTTP/1.1 400"));

        // Finished with the socket
        client.close();
    }


    @Test
    public void testNoUpgrade() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(new ApplicationListener(
                TesterEchoServer.Config.class.getName(), false));

        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        WebSocketClient client= new WebSocketClient(getPort());

        // Send the WebSocket handshake
        client.writer.write("GET " + TesterEchoServer.Config.PATH_BASIC + " HTTP/1.1" + CRLF);
        client.writer.write("Host: foo" + CRLF);
        client.writer.write("Connection: upgrade" + CRLF);
        client.writer.write("Sec-WebSocket-Version: 13" + CRLF);
        client.writer.write("Sec-WebSocket-Key: TODO" + CRLF);
        client.writer.write(CRLF);
        client.writer.flush();

        // Make sure we got an error response
        // No upgrade means it is not treated an as upgrade request so a 404 is
        // generated when the request reaches the Default Servlet.s
        String responseLine = client.reader.readLine();
        assertTrue(responseLine.startsWith("HTTP/1.1 404"));

        // Finished with the socket
        client.close();
    }

    @Test
    public void testKey() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        // No file system docBase required
        Context ctx = tomcat.addContext("", null);
        ctx.addApplicationListener(new ApplicationListener(
                TesterEchoServer.Config.class.getName(), false));

        Tomcat.addServlet(ctx, "default", new DefaultServlet());
        ctx.addServletMapping("/", "default");

        tomcat.start();

        WebSocketClient client= new WebSocketClient(getPort());

        // Send the WebSocket handshake
        client.writer.write("GET " + TesterEchoServer.Config.PATH_BASIC + " HTTP/1.1" + CRLF);
        client.writer.write("Host: foo" + CRLF);
        client.writer.write("Upgrade: websocket" + CRLF);
        client.writer.write("Connection: upgrade" + CRLF);
        client.writer.write("Sec-WebSocket-Version: 13" + CRLF);
        client.writer.write("Sec-WebSocket-Key: TODO" + CRLF);
        client.writer.write(CRLF);
        client.writer.flush();

        // Make sure we got an upgrade response
        String responseLine = client.reader.readLine();
        assertTrue(responseLine.startsWith("HTTP/1.1 101"));

        String accept = null;
        String responseHeaderLine = client.reader.readLine();
        while (!responseHeaderLine.equals("")) {
            if(responseHeaderLine.startsWith("Sec-WebSocket-Accept: ")) {
                accept = responseHeaderLine.substring(responseHeaderLine.indexOf(':')+2);
                break;
            }
            responseHeaderLine = client.reader.readLine();
        }
        assertTrue(accept != null);
        MessageDigest sha1Helper = MessageDigest.getInstance("SHA1");
        sha1Helper.reset();
        sha1Helper.update("TODO".getBytes(B2CConverter.ISO_8859_1));
        String source = Base64.encode(sha1Helper.digest(WS_ACCEPT));
        assertEquals(source,accept);

        sha1Helper.reset();
        sha1Helper.update("TOD".getBytes(B2CConverter.ISO_8859_1));
        source = Base64.encode(sha1Helper.digest(WS_ACCEPT));
        assertFalse(source.equals(accept));
        // Finished with the socket
        client.close();
    }


    @Test
    public void testBug53339() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        tomcat.enableNaming();

        // No file system docBase required
        Context ctx = tomcat.addContext("", null);

        Tomcat.addServlet(ctx, "Bug53339", new Bug53339Servlet());
        ctx.addServletMapping("/*", "Bug53339");

        // Create the resource
        ContextEnvironment env = new ContextEnvironment();
        env.setName(Bug53339WsInbound.JNDI_NAME);
        env.setType(String.class.getName());
        env.setValue(Bug53339WsInbound.TEST_MESSAGE);
        ctx.getNamingResources().addEnvironment(env);

        tomcat.start();

        WebSocketClient client= new WebSocketClient(getPort());

        // Send the WebSocket handshake
        client.writer.write("GET / HTTP/1.1" + CRLF);
        client.writer.write("Host: foo" + CRLF);
        client.writer.write("Upgrade: websocket" + CRLF);
        client.writer.write("Connection: upgrade" + CRLF);
        client.writer.write("Sec-WebSocket-Version: 13" + CRLF);
        client.writer.write("Sec-WebSocket-Key: TODO" + CRLF);
        client.writer.write(CRLF);
        client.writer.flush();

        // Make sure we got an upgrade response
        String responseLine = client.reader.readLine();
        assertTrue(responseLine.startsWith("HTTP/1.1 101"));

        // Swallow the headers
        String responseHeaderLine = client.reader.readLine();
        while (!responseHeaderLine.equals("")) {
            responseHeaderLine = client.reader.readLine();
        }

        // Now we can do WebSocket
        String msg = client.readMessage();
        assertEquals(Bug53339WsInbound.TEST_MESSAGE, msg);

        // Finished with the socket
        client.close();
    }


    private static class Bug53339Servlet extends WebSocketServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected StreamInbound createWebSocketInbound(String subProtocol,
                HttpServletRequest request) {
            return new Bug53339WsInbound();
        }
    }


    private static class Bug53339WsInbound extends MessageInbound {

        public static final String TEST_MESSAGE = "Test Message";
        public static final String JNDI_NAME = "Bug53339Message";

        @Override
        protected void onOpen(WsOutbound outbound) {
            String msg = "Error";
            try {
                javax.naming.Context initCtx = new InitialContext();
                msg = (String) initCtx.lookup(
                        "java:comp/env/" + JNDI_NAME);
            } catch (NamingException e) {
                // Ignore - the test checks if the message is sent
                e.printStackTrace(); // for debug purposes if the test fails
            }
            CharBuffer cb = CharBuffer.wrap("" + msg);
            try {
                outbound.writeTextMessage(cb);
            } catch (IOException e) {
                // Ignore - the test checks if the message is sent
            }

        }

        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            // Ignore
        }

        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
            // Ignore
        }
    }

    private static class WebSocketClient {
        private OutputStream os;
        private boolean isContinuation = false;
        final String encoding = "ISO-8859-1";
        private Socket socket;
        private Writer writer;
        private CustomReader reader;

        public WebSocketClient(int port) {
            SocketAddress addr = new InetSocketAddress("localhost", port);
            socket = new Socket();
            try {
                socket.setSoTimeout(10000);
                socket.connect(addr, 10000);
                os = socket.getOutputStream();
                writer = new OutputStreamWriter(os, encoding);
                reader = new CustomReader(socket.getInputStream(), encoding);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        void close() throws IOException {
            socket.close();
        }

        private void sendMessage(String message, boolean finalFragment)
                throws IOException {
            ByteChunk bc = new ByteChunk(8192);
            CharChunk cc = new CharChunk(8192);
            C2BConverter c2b = new C2BConverter("UTF-8");
            cc.append(message);
            c2b.convert(cc, bc);

            int len = bc.getLength();
            assertTrue(len < 126);

            byte first;
            if (isContinuation) {
                first = Constants.OPCODE_CONTINUATION;
            } else {
                first = Constants.OPCODE_TEXT;
            }
            if (finalFragment) {
                first = (byte) (0x80 | first);
            }
            os.write(first);

            os.write(0x80 | len);

            // Zero mask
            os.write(0);
            os.write(0);
            os.write(0);
            os.write(0);

            // Payload
            os.write(bc.getBytes(), bc.getStart(), len);

            os.flush();

            // Will the next frame be a continuation frame
            isContinuation = !finalFragment;
        }

        private String readMessage() throws IOException {

            // Skip first byte
            reader.read();

            // Get payload length
            int len = reader.read() & 0x7F;
            assertTrue(len < 126);

            // Read payload
            reader.setEncoding("UTF-8");
            return reader.read(len);
        }
        /*
         * This is not a real reader but just a thin wrapper around
         * an input stream that allows to switch encoding during
         * reading.
         * An example is reading headers using ISO-8859-1 and a body
         * using UTF-8.
         * The class is not thread-safe and not well-performing.
         */
        private class CustomReader {
            private InputStream is;
            private boolean markSupported;
            private B2CConverter b2c;

            public CustomReader(InputStream is, String encoding) throws IOException {
                this.is = new BufferedInputStream(is);
                markSupported = is.markSupported();
                b2c = new B2CConverter(encoding);
            }

            public void setEncoding(String encoding) throws IOException {
                b2c = new B2CConverter(encoding);
            }

            public int read() throws IOException {
                return is.read();
            }

            public String read(int len) throws IOException {
                ByteChunk bc = new ByteChunk(125);
                CharChunk cc = new CharChunk(125);
                int read = 0;
                while (read < len) {
                    read = read + is.read(bc.getBytes(), read, len - read);
                }

                bc.setEnd(len);
                b2c.convert(bc, cc, true);
                return cc.toString();
            }

            public String readLine() throws IOException {
                ByteChunk bc = new ByteChunk(125);
                CharChunk cc = new CharChunk(125);
                int i = is.read();
                int read = 0;
                while (i != -1) {
                    if (i != '\r' && i != '\n') {
                        bc.append((byte)i);
                        read++;
                    } else if (i == '\n') {
                        break;
                    } else if (i == '\r') {
                        if (markSupported) {
                            is.mark(2);
                        }
                        i = read();
                        if (i == -1) {
                            break;
                        } else {
                            if (i == '\n') {
                                break;
                            } else {
                                if (markSupported) {
                                    is.reset();
                                }
                            }
                        }
                    }
                    i = is.read();
                }
                bc.setEnd(read);
                b2c.convert(bc, cc, true);
                return cc.toString();
            }
        }
    }
}
