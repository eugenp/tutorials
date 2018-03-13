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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;

import org.apache.coyote.http11.upgrade.UpgradeInbound;
import org.apache.coyote.http11.upgrade.UpgradeOutbound;
import org.apache.coyote.http11.upgrade.UpgradeProcessor;
import org.apache.tomcat.util.buf.Utf8Decoder;
import org.apache.tomcat.util.net.AbstractEndpoint.Handler.SocketState;

/**
 * Base implementation of the class used to process WebSocket connections based
 * on streams. Applications should extend this class to provide application
 * specific functionality. Applications that wish to operate on a message basis
 * rather than a stream basis should use {@link MessageInbound}.
 * 
 * @deprecated  Replaced by the JSR356 WebSocket 1.1 implementation and will be
 *              removed in Tomcat 8.0.x.  
 */
@Deprecated
public abstract class StreamInbound implements UpgradeInbound {

    private final ClassLoader applicationClassLoader;
    private UpgradeProcessor<?> processor = null;
    private WsOutbound outbound;
    private int outboundByteBufferSize = WsOutbound.DEFAULT_BUFFER_SIZE;
    private int outboundCharBufferSize = WsOutbound.DEFAULT_BUFFER_SIZE;


    public StreamInbound() {
        applicationClassLoader = Thread.currentThread().getContextClassLoader();
    }


    public int getOutboundByteBufferSize() {
        return outboundByteBufferSize;
    }


    /**
     * This only applies to the {@link WsOutbound} instance returned from
     * {@link #getWsOutbound()} created by a subsequent call to
     * {@link #setUpgradeOutbound(UpgradeOutbound)}. The current
     * {@link WsOutbound} instance, if any, is not affected.
     *
     * @param outboundByteBufferSize
     */
    public void setOutboundByteBufferSize(int outboundByteBufferSize) {
        this.outboundByteBufferSize = outboundByteBufferSize;
    }


    public int getOutboundCharBufferSize() {
        return outboundCharBufferSize;
    }


    /**
     * This only applies to the {@link WsOutbound} instance returned from
     * {@link #getWsOutbound()} created by a subsequent call to
     * {@link #setUpgradeOutbound(UpgradeOutbound)}. The current
     * {@link WsOutbound} instance, if any, is not affected.
     *
     * @param outboundCharBufferSize
     */
    public void setOutboundCharBufferSize(int outboundCharBufferSize) {
        this.outboundCharBufferSize = outboundCharBufferSize;
    }


    @Override
    public final void setUpgradeOutbound(UpgradeOutbound upgradeOutbound) {
        outbound = new WsOutbound(upgradeOutbound, this, outboundByteBufferSize,
                outboundCharBufferSize);
    }


    @Override
    public final void setUpgradeProcessor(UpgradeProcessor<?> processor) {
        this.processor = processor;
    }


    /**
     * Obtain the outbound side of this WebSocket connection used for writing
     * data to the client.
     */
    public final WsOutbound getWsOutbound() {
        return outbound;
    }


    @Override
    public final SocketState onData() throws IOException {
        // Must be start the start of a message (which may consist of multiple
        // frames)
        WsInputStream wsIs = new WsInputStream(processor, getWsOutbound());

        try {
            WsFrame frame = wsIs.nextFrame(false);

            while (frame != null) {
                // TODO User defined extensions may define values for rsv
                if (frame.getRsv() > 0) {
                    closeOutboundConnection(
                            Constants.STATUS_PROTOCOL_ERROR, null);
                    return SocketState.CLOSED;
                }

                byte opCode = frame.getOpCode();

                if (opCode == Constants.OPCODE_BINARY) {
                    doOnBinaryData(wsIs);
                } else if (opCode == Constants.OPCODE_TEXT) {
                    InputStreamReader r =
                            new InputStreamReader(wsIs, new Utf8Decoder());
                    doOnTextData(r);
                } else if (opCode == Constants.OPCODE_CLOSE){
                    closeOutboundConnection(frame);
                    return SocketState.CLOSED;
                } else if (opCode == Constants.OPCODE_PING) {
                    getWsOutbound().pong(frame.getPayLoad());
                } else if (opCode == Constants.OPCODE_PONG) {
                    doOnPong(frame.getPayLoad());
                } else {
                    // Unknown OpCode
                    closeOutboundConnection(
                            Constants.STATUS_PROTOCOL_ERROR, null);
                    return SocketState.CLOSED;
                }
                frame = wsIs.nextFrame(false);
            }
        } catch (MalformedInputException mie) {
            // Invalid UTF-8
            closeOutboundConnection(Constants.STATUS_BAD_DATA, null);
            return SocketState.CLOSED;
        } catch (UnmappableCharacterException uce) {
            // Invalid UTF-8
            closeOutboundConnection(Constants.STATUS_BAD_DATA, null);
            return SocketState.CLOSED;
        } catch (IOException ioe) {
            // Given something must have gone to reach this point, this
            // might not work but try it anyway.
            closeOutboundConnection(Constants.STATUS_PROTOCOL_ERROR, null);
            return SocketState.CLOSED;
        }
        return SocketState.UPGRADED;
    }

    private void doOnBinaryData(InputStream is) throws IOException {
        // Need to call onBinaryData using the web application's class loader
        Thread t = Thread.currentThread();
        ClassLoader cl = t.getContextClassLoader();
        t.setContextClassLoader(applicationClassLoader);
        try {
            onBinaryData(is);
        } finally {
            t.setContextClassLoader(cl);
        }
    }


    private void doOnTextData(Reader r) throws IOException {
        // Need to call onTextData using the web application's class loader
        Thread t = Thread.currentThread();
        ClassLoader cl = t.getContextClassLoader();
        t.setContextClassLoader(applicationClassLoader);
        try {
            onTextData(r);
        } finally {
            t.setContextClassLoader(cl);
        }
    }


    private void closeOutboundConnection(int status, ByteBuffer data) throws IOException {
        try {
            getWsOutbound().close(status, data);
        } finally {
            doOnClose(status);
        }
    }

    private void closeOutboundConnection(WsFrame frame) throws IOException {
        try {
            getWsOutbound().close(frame);
        } finally {
            doOnClose(Constants.STATUS_CLOSE_NORMAL);
        }
    }

    /**
     * Package private so the outbound connection can signal that the connection
     * has been closed - usually due to an error.
     *  
     * @param status    The WebSocket status code to report to the application
     */
    void doOnClose(int status) {
        // Need to call onClose using the web application's class loader
        Thread t = Thread.currentThread();
        ClassLoader cl = t.getContextClassLoader();
        t.setContextClassLoader(applicationClassLoader);
        try {
            onClose(status);
        } finally {
            t.setContextClassLoader(cl);
        }
    }

    private void doOnPong(ByteBuffer payload) {
        // Need to call onPong using the web application's class loader
        Thread t = Thread.currentThread();
        ClassLoader cl = t.getContextClassLoader();
        t.setContextClassLoader(applicationClassLoader);
        try {
            onPong(payload);
        } finally {
            t.setContextClassLoader(cl);
        }
    }
    
    @Override
    public final void onUpgradeComplete() {
        // Need to call onOpen using the web application's class loader
        Thread t = Thread.currentThread();
        ClassLoader cl = t.getContextClassLoader();
        t.setContextClassLoader(applicationClassLoader);
        try {
            onOpen(outbound);
        } finally {
            t.setContextClassLoader(cl);
        }
    }

    /**
     * Intended to be overridden by sub-classes that wish to be notified
     * when the outbound connection is established. The default implementation
     * is a NO-OP.
     *
     * @param outbound    The outbound WebSocket connection.
     */
    protected void onOpen(WsOutbound outbound) {
        // NO-OP
    }

    /**
     * Intended to be overridden by sub-classes that wish to be notified
     * when the outbound connection is closed. The default implementation
     * is a NO-OP.
     *
     * @param status    The status code of the close reason.
     */
    protected void onClose(int status) {
        // NO-OP
    }

    /**
     * Intended to be overridden by sub-classes that wish to be notified
     * when a pong is received. The default implementation is a NO-OP.
     *
     * @param payload   The payload included in the pong.
     */
    protected void onPong(ByteBuffer payload) {
        // NO-OP
    }

    /**
     * This method is called when there is a binary WebSocket message available
     * to process. The message is presented via a stream and may be formed from
     * one or more frames. The number of frames used to transmit the message is
     * not made visible to the application.
     *
     * @param is    The WebSocket message
     *
     * @throws IOException  If a problem occurs processing the message. Any
     *                      exception will trigger the closing of the WebSocket
     *                      connection.
     */
    protected abstract void onBinaryData(InputStream is) throws IOException;


    /**
     * This method is called when there is a textual WebSocket message available
     * to process. The message is presented via a reader and may be formed from
     * one or more frames. The number of frames used to transmit the message is
     * not made visible to the application.
     *
     * @param r     The WebSocket message
     *
     * @throws IOException  If a problem occurs processing the message. Any
     *                      exception will trigger the closing of the WebSocket
     *                      connection.
     */
    protected abstract void onTextData(Reader r) throws IOException;

    /**
     * This default implementation sets the read timeout to infinite and expects
     * the WebSocket application to close the connection when it is no longer
     * required. Applications wishing to set an explicit timeout may override
     * this method and return a value of their choice.
     *
     * @return  The read timeout in milliseconds or -1 for infinite
     */
    @Override
    public int getReadTimeout() {
        return -1;
    }
}
