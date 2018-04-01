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
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.tomcat.util.res.StringManager;

/**
 * Base implementation of the class used to process WebSocket connections based
 * on messages. Applications should extend this class to provide application
 * specific functionality. Applications that wish to operate on a stream basis
 * rather than a message basis should use {@link StreamInbound}.
 * 
 * @deprecated  Replaced by the JSR356 WebSocket 1.1 implementation and will be
 *              removed in Tomcat 8.0.x.  
 */
@Deprecated
public abstract class MessageInbound extends StreamInbound {

    private static final StringManager sm =
            StringManager.getManager(Constants.Package);


    // 2MB - like maxPostSize
    private int byteBufferMaxSize = 2097152;
    private int charBufferMaxSize = 2097152;

    private ByteBuffer bb = ByteBuffer.allocate(8192);
    private CharBuffer cb = CharBuffer.allocate(8192);


    @Override
    protected final void onBinaryData(InputStream is) throws IOException {
        int read = 0;
        while (read > -1) {
            bb.position(bb.position() + read);
            if (bb.remaining() == 0) {
                resizeByteBuffer();
            }
            read = is.read(bb.array(), bb.position(), bb.remaining());
        }
        bb.flip();
        onBinaryMessage(bb);
        bb.clear();
    }


    @Override
    protected final void onTextData(Reader r) throws IOException {
        int read = 0;
        while (read > -1) {
            cb.position(cb.position() + read);
            if (cb.remaining() == 0) {
                resizeCharBuffer();
            }
            read = r.read(cb.array(), cb.position(), cb.remaining());
        }
        cb.flip();
        onTextMessage(cb);
        cb.clear();
    }


    private void resizeByteBuffer() throws IOException {
        int maxSize = getByteBufferMaxSize();
        if (bb.limit() >= maxSize) {
            throw new IOException(sm.getString("message.bufferTooSmall"));
        }

        long newSize = bb.limit() * 2;
        if (newSize > maxSize) {
            newSize = maxSize;
        }

        // Cast is safe. newSize < maxSize and maxSize is an int
        ByteBuffer newBuffer = ByteBuffer.allocate((int) newSize);
        bb.rewind();
        newBuffer.put(bb);
        bb = newBuffer;
    }


    private void resizeCharBuffer() throws IOException {
        int maxSize = getCharBufferMaxSize();
        if (cb.limit() >= maxSize) {
            throw new IOException(sm.getString("message.bufferTooSmall"));
        }

        long newSize = cb.limit() * 2;
        if (newSize > maxSize) {
            newSize = maxSize;
        }

        // Cast is safe. newSize < maxSize and maxSize is an int
        CharBuffer newBuffer = CharBuffer.allocate((int) newSize);
        cb.rewind();
        newBuffer.put(cb);
        cb = newBuffer;
    }


    /**
     * Obtain the current maximum size (in bytes) of the buffer used for binary
     * messages.
     */
    public final int getByteBufferMaxSize() {
        return byteBufferMaxSize;
    }


    /**
     * Set the maximum size (in bytes) of the buffer used for binary messages.
     */
    public final void setByteBufferMaxSize(int byteBufferMaxSize) {
        this.byteBufferMaxSize = byteBufferMaxSize;
    }


    /**
     * Obtain the current maximum size (in characters) of the buffer used for
     * binary messages.
     */
    public final int getCharBufferMaxSize() {
        return charBufferMaxSize;
    }


    /**
     * Set the maximum size (in characters) of the buffer used for textual
     * messages.
     */
    public final void setCharBufferMaxSize(int charBufferMaxSize) {
        this.charBufferMaxSize = charBufferMaxSize;
    }


    /**
     * This method is called when there is a binary WebSocket message available
     * to process. The message is presented via a ByteBuffer and may have been
     * formed from one or more frames. The number of frames used to transmit the
     * message is not made visible to the application.
     *
     * @param message       The WebSocket message
     *
     * @throws IOException  If a problem occurs processing the message. Any
     *                      exception will trigger the closing of the WebSocket
     *                      connection.
     */
    protected abstract void onBinaryMessage(ByteBuffer message)
            throws IOException;


    /**
     * This method is called when there is a textual WebSocket message available
     * to process. The message is presented via a CharBuffer and may have been
     * formed from one or more frames. The number of frames used to transmit the
     * message is not made visible to the application.
     *
     * @param message       The WebSocket message
     *
     * @throws IOException  If a problem occurs processing the message. Any
     *                      exception will trigger the closing of the WebSocket
     *                      connection.
     */
    protected abstract void onTextMessage(CharBuffer message)
            throws IOException;
}
