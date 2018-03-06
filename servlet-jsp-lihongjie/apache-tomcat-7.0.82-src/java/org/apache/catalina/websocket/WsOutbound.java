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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

import org.apache.coyote.http11.upgrade.UpgradeOutbound;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.res.StringManager;

/**
 * Provides the means to write WebSocket messages to the client. All methods
 * that write to the client (or update a buffer that is later written to the
 * client) are synchronized to prevent multiple threads trying to write to the
 * client at the same time.
 * 
 * @deprecated  Replaced by the JSR356 WebSocket 1.1 implementation and will be
 *              removed in Tomcat 8.0.x.  
 */
@Deprecated
public class WsOutbound {

    private static final StringManager sm =
            StringManager.getManager(Constants.Package);
    public static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * This state lock is used rather than synchronized methods to allow error
     * handling to be managed outside of the synchronization else deadlocks may
     * occur such as https://bz.apache.org/bugzilla/show_bug.cgi?id=55524
     */
    private final Object stateLock = new Object();
    
    private UpgradeOutbound upgradeOutbound;
    private StreamInbound streamInbound;
    private ByteBuffer bb;
    private CharBuffer cb;
    private boolean closed = false;
    private Boolean text = null;
    private boolean firstFrame = true;


    public WsOutbound(UpgradeOutbound upgradeOutbound,
            StreamInbound streamInbound) {
        this(upgradeOutbound, streamInbound, DEFAULT_BUFFER_SIZE,
                DEFAULT_BUFFER_SIZE);
    }


    public WsOutbound(UpgradeOutbound upgradeOutbound, StreamInbound streamInbound,
            int byteBufferSize, int charBufferSize) {
        this.upgradeOutbound = upgradeOutbound;
        this.streamInbound = streamInbound;
        this.bb = ByteBuffer.allocate(byteBufferSize);
        this.cb = CharBuffer.allocate(charBufferSize);
    }


    /**
     * Adds the data to the buffer for binary data. If a textual message is
     * currently in progress that message will be completed and a new binary
     * message started. If the buffer for binary data is full, the buffer will
     * be flushed and a new binary continuation fragment started.
     *
     * @param b The byte (only the least significant byte is used) of data to
     *          send to the client.
     *
     * @throws IOException  If a flush is required and an error occurs writing
     *                      the WebSocket frame to the client
     */
    public void writeBinaryData(int b) throws IOException {
        try {
            synchronized (stateLock) {
                if (closed) {
                    throw new IOException(sm.getString("outbound.closed"));
                }
        
                if (bb.position() == bb.capacity()) {
                    doFlush(false);
                }
                if (text == null) {
                    text = Boolean.FALSE;
                } else if (Boolean.TRUE.equals(text)) {
                    // Flush the character data
                    flush();
                    text = Boolean.FALSE;
                }
                bb.put((byte) (b & 0xFF));
            }
        } catch (IOException ioe) {
            // Any IOException is terminal. Make sure the inbound side knows
            // that something went wrong.
            // The exception handling needs to be outside of the sync to avoid
            // possible deadlocks (e.g. BZ55524) when triggering the inbound
            // close as that will execute user code
            streamInbound.doOnClose(Constants.STATUS_CLOSED_UNEXPECTEDLY);
            throw ioe;
        }
    }


    /**
     * Adds the data to the buffer for textual data. If a binary message is
     * currently in progress that message will be completed and a new textual
     * message started. If the buffer for textual data is full, the buffer will
     * be flushed and a new textual continuation fragment started.
     *
     * @param c The character to send to the client.
     *
     * @throws IOException  If a flush is required and an error occurs writing
     *                      the WebSocket frame to the client
     */
    public void writeTextData(char c) throws IOException {
        try {
            synchronized (stateLock) {
                if (closed) {
                    throw new IOException(sm.getString("outbound.closed"));
                }
        
                if (cb.position() == cb.capacity()) {
                    doFlush(false);
                }
        
                if (text == null) {
                    text = Boolean.TRUE;
                } else if (Boolean.FALSE.equals(text)) {
                    // Flush the binary data
                    flush();
                    text = Boolean.TRUE;
                }
                cb.append(c);
            }
        } catch (IOException ioe) {
            // Any IOException is terminal. Make sure the Inbound side knows
            // that something went wrong.
            // The exception handling needs to be outside of the sync to avoid
            // possible deadlocks (e.g. BZ55524) when triggering the inbound
            // close as that will execute user code
            streamInbound.doOnClose(Constants.STATUS_CLOSED_UNEXPECTEDLY);
            throw ioe;
        }
    }


    /**
     * Flush any message (binary or textual) that may be buffered and then send
     * a WebSocket binary message as a single frame with the provided buffer as
     * the payload of the message.
     *
     * @param msgBb The buffer containing the payload
     *
     * @throws IOException  If an error occurs writing to the client
     */
    public void writeBinaryMessage(ByteBuffer msgBb) throws IOException {

        try {
            synchronized (stateLock) {
                if (closed) {
                    throw new IOException(sm.getString("outbound.closed"));
                }
        
                if (text != null) {
                    // Empty the buffer
                    flush();
                }
                text = Boolean.FALSE;
                doWriteBytes(msgBb, true);
            }
        } catch (IOException ioe) {
            // Any IOException is terminal. Make sure the Inbound side knows
            // that something went wrong.
            // The exception handling needs to be outside of the sync to avoid
            // possible deadlocks (e.g. BZ55524) when triggering the inbound
            // close as that will execute user code
            streamInbound.doOnClose(Constants.STATUS_CLOSED_UNEXPECTEDLY);
            throw ioe;
        }
    }


    /**
     * Flush any message (binary or textual) that may be buffered and then send
     * a WebSocket text message as a single frame with the provided buffer as
     * the payload of the message.
     *
     * @param msgCb The buffer containing the payload
     *
     * @throws IOException  If an error occurs writing to the client
     */
    public void writeTextMessage(CharBuffer msgCb) throws IOException {

        try {
            synchronized (stateLock) {
                if (closed) {
                    throw new IOException(sm.getString("outbound.closed"));
                }
        
                if (text != null) {
                    // Empty the buffer
                    flush();
                }
                text = Boolean.TRUE;
                doWriteText(msgCb, true);
            }
        } catch (IOException ioe) {
            // Any IOException is terminal. Make sure the Inbound side knows
            // that something went wrong.
            // The exception handling needs to be outside of the sync to avoid
            // possible deadlocks (e.g. BZ55524) when triggering the inbound
            // close as that will execute user code
            streamInbound.doOnClose(Constants.STATUS_CLOSED_UNEXPECTEDLY);
            throw ioe;
        }
    }


    /**
     * Flush any message (binary or textual) that may be buffered.
     *
     * @throws IOException  If an error occurs writing to the client
     */
    public void flush() throws IOException {
        try {
            synchronized (stateLock) {
                if (closed) {
                    throw new IOException(sm.getString("outbound.closed"));
                }
                doFlush(true);
            }
        } catch (IOException ioe) {
            // Any IOException is terminal. Make sure the Inbound side knows
            // that something went wrong.
            // The exception handling needs to be outside of the sync to avoid
            // possible deadlocks (e.g. BZ55524) when triggering the inbound
            // close as that will execute user code
            streamInbound.doOnClose(Constants.STATUS_CLOSED_UNEXPECTEDLY);
            throw ioe;
        }
    }


    private void doFlush(boolean finalFragment) throws IOException {
        if (text == null) {
            // No data
            return;
        }
        if (text.booleanValue()) {
            cb.flip();
            doWriteText(cb, finalFragment);
        } else {
            bb.flip();
            doWriteBytes(bb, finalFragment);
        }
    }


    /**
     * Respond to a client close by sending a close that echoes the status code
     * and message.
     *
     * @param frame The close frame received from a client
     *
     * @throws IOException  If an error occurs writing to the client
     */
    protected void close(WsFrame frame) throws IOException {
        if (frame.getPayLoadLength() > 0) {
            // Must be status (2 bytes) plus optional message
            if (frame.getPayLoadLength() == 1) {
                throw new IOException();
            }
            int status = (frame.getPayLoad().get() & 0xFF) << 8;
            status += frame.getPayLoad().get() & 0xFF;

            if (validateCloseStatus(status)) {
                // Echo the status back to the client
                close(status, frame.getPayLoad());
            } else {
                // Invalid close code
                close(Constants.STATUS_PROTOCOL_ERROR, null);
            }
        } else {
            // No status
            close(0, null);
        }
    }


    private boolean validateCloseStatus(int status) {

        if (status == Constants.STATUS_CLOSE_NORMAL ||
                status == Constants.STATUS_SHUTDOWN ||
                status == Constants.STATUS_PROTOCOL_ERROR ||
                status == Constants.STATUS_UNEXPECTED_DATA_TYPE ||
                status == Constants.STATUS_BAD_DATA ||
                status == Constants.STATUS_POLICY_VIOLATION ||
                status == Constants.STATUS_MESSAGE_TOO_LARGE ||
                status == Constants.STATUS_REQUIRED_EXTENSION ||
                status == Constants.STATUS_UNEXPECTED_CONDITION ||
                (status > 2999 && status < 5000)) {
            // Other 1xxx reserved / not permitted
            // 2xxx reserved
            // 3xxx framework defined
            // 4xxx application defined
            return true;
        }
        // <1000 unused
        // >4999 undefined
        return false;
    }


    /**
     * Send a close message to the client
     *
     * @param status    Must be a valid status code or zero to send no code
     * @param data      Optional message. If message is defined, a valid status
     *                  code must be provided.
     *
     * @throws IOException  If an error occurs writing to the client
     */
    public void close(int status, ByteBuffer data) throws IOException {

        try {
            synchronized (stateLock) {
                if (closed) {
                    return;
                }
        
                // Send any partial data we have
                try {
                    doFlush(false);
                } finally {
                    closed = true;
                }
        
                upgradeOutbound.write(0x88);
                if (status == 0) {
                    upgradeOutbound.write(0);
                } else if (data == null || data.position() == data.limit()) {
                    upgradeOutbound.write(2);
                    upgradeOutbound.write(status >>> 8);
                    upgradeOutbound.write(status);
                } else {
                    upgradeOutbound.write(2 + data.limit() - data.position());
                    upgradeOutbound.write(status >>> 8);
                    upgradeOutbound.write(status);
                    upgradeOutbound.write(data.array(), data.position(),
                            data.limit() - data.position());
                }
                upgradeOutbound.flush();
        
                bb = null;
                cb = null;
                upgradeOutbound = null;
            }
        } catch (IOException ioe) {
            // Any IOException is terminal. Make sure the Inbound side knows
            // that something went wrong.
            // The exception handling needs to be outside of the sync to avoid
            // possible deadlocks (e.g. BZ55524) when triggering the inbound
            // close as that will execute user code
            streamInbound.doOnClose(Constants.STATUS_CLOSED_UNEXPECTEDLY);
            throw ioe;
        }
    }


    /**
     * Send a pong message to the client
     *
     * @param data      Optional message.
     *
     * @throws IOException  If an error occurs writing to the client
     */
    public void pong(ByteBuffer data) throws IOException {
        sendControlMessage(data, Constants.OPCODE_PONG);
    }

    /**
     * Send a ping message to the client
     *
     * @param data      Optional message.
     *
     * @throws IOException  If an error occurs writing to the client
     */
    public void ping(ByteBuffer data) throws IOException {
        sendControlMessage(data, Constants.OPCODE_PING);
    }

    /**
     * Generic function to send either a ping or a pong.
     *
     * @param data      Optional message.
     * @param opcode    The byte to include as the opcode.
     *
     * @throws IOException  If an error occurs writing to the client
     */
    private void sendControlMessage(ByteBuffer data, byte opcode) throws IOException {

        try {
            synchronized (stateLock) {
                if (closed) {
                    throw new IOException(sm.getString("outbound.closed"));
                }
        
                doFlush(false);
        
                upgradeOutbound.write(0x80 | opcode);
                if (data == null) {
                    upgradeOutbound.write(0);
                } else {
                    upgradeOutbound.write(data.limit() - data.position());
                    upgradeOutbound.write(data.array(), data.position(),
                            data.limit() - data.position());
                }
        
                upgradeOutbound.flush();
            }
        } catch (IOException ioe) {
            // Any IOException is terminal. Make sure the Inbound side knows
            // that something went wrong.
            // The exception handling needs to be outside of the sync to avoid
            // possible deadlocks (e.g. BZ55524) when triggering the inbound
            // close as that will execute user code
            streamInbound.doOnClose(Constants.STATUS_CLOSED_UNEXPECTEDLY);
            throw ioe;
        }
    }


    /**
     * Writes the provided bytes as the payload in a new WebSocket frame.
     *
     * @param buffer        The bytes to include in the payload.
     * @param finalFragment Do these bytes represent the final fragment of a
     *                      WebSocket message?
     * @throws IOException
     */
    private void doWriteBytes(ByteBuffer buffer, boolean finalFragment)
            throws IOException {

        if (closed) {
            throw new IOException(sm.getString("outbound.closed"));
        }

        // Work out the first byte
        int first = 0x00;
        if (finalFragment) {
            first = first + 0x80;
        }
        if (firstFrame) {
            if (text.booleanValue()) {
                first = first + 0x1;
            } else {
                first = first + 0x2;
            }
        }
        // Continuation frame is OpCode 0
        upgradeOutbound.write(first);

        if (buffer.limit() < 126) {
            upgradeOutbound.write(buffer.limit());
        } else if (buffer.limit() < 65536) {
            upgradeOutbound.write(126);
            upgradeOutbound.write(buffer.limit() >>> 8);
            upgradeOutbound.write(buffer.limit() & 0xFF);
        } else {
            // Will never be more than 2^31-1
            upgradeOutbound.write(127);
            upgradeOutbound.write(0);
            upgradeOutbound.write(0);
            upgradeOutbound.write(0);
            upgradeOutbound.write(0);
            upgradeOutbound.write(buffer.limit() >>> 24);
            upgradeOutbound.write(buffer.limit() >>> 16);
            upgradeOutbound.write(buffer.limit() >>> 8);
            upgradeOutbound.write(buffer.limit() & 0xFF);
        }

        // Write the content
        upgradeOutbound.write(buffer.array(), buffer.arrayOffset(),
                buffer.limit());
        upgradeOutbound.flush();

        // Reset
        if (finalFragment) {
            text = null;
            firstFrame = true;
        } else {
            firstFrame = false;
        }
        bb.clear();
    }


    /*
     * Convert the textual message to bytes and then output it.
     */
    private void doWriteText(CharBuffer buffer, boolean finalFragment)
            throws IOException {
        CharsetEncoder encoder = B2CConverter.UTF_8.newEncoder();
        do {
            CoderResult cr = encoder.encode(buffer, bb, true);
            if (cr.isError()) {
                cr.throwException();
            }
            bb.flip();
            if (buffer.hasRemaining()) {
                doWriteBytes(bb, false);
            } else {
                doWriteBytes(bb, finalFragment);
            }
        } while (buffer.hasRemaining());

        // Reset - bb will be cleared in doWriteBytes()
        cb.clear();
    }
}
