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
package org.apache.coyote.http11.upgrade;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.tomcat.jni.OS;
import org.apache.tomcat.jni.Socket;
import org.apache.tomcat.jni.Status;
import org.apache.tomcat.util.net.AprEndpoint;
import org.apache.tomcat.util.net.SocketWrapper;

public class AprServletOutputStream extends AbstractServletOutputStream {

    private static final int SSL_OUTPUT_BUFFER_SIZE = 8192;

    private final AprEndpoint endpoint;
    private final SocketWrapper<Long> wrapper;
    private final long socket;
    private volatile boolean closed = false;
    private final ByteBuffer sslOutputBuffer;

    public AprServletOutputStream(SocketWrapper<Long> wrapper,
            int asyncWriteBufferSize, AprEndpoint endpoint) {
        super(asyncWriteBufferSize);
        this.endpoint = endpoint;
        this.wrapper = wrapper;
        this.socket = wrapper.getSocket().longValue();
        if (endpoint.isSSLEnabled()) {
            sslOutputBuffer = ByteBuffer.allocateDirect(SSL_OUTPUT_BUFFER_SIZE);
            sslOutputBuffer.position(SSL_OUTPUT_BUFFER_SIZE);
        } else {
            sslOutputBuffer = null;
        }
    }


    @Override
    protected int doWrite(boolean block, byte[] b, int off, int len)
            throws IOException {

        if (closed) {
            throw new IOException(sm.getString("apr.closed", Long.valueOf(socket)));
        }

        Lock readLock = wrapper.getBlockingStatusReadLock();
        WriteLock writeLock = wrapper.getBlockingStatusWriteLock();

        try {
            readLock.lock();
            if (wrapper.getBlockingStatus() == block) {
                return doWriteInternal(b, off, len);
            }
        } finally {
            readLock.unlock();
        }

        try {
            writeLock.lock();
            // Set the current settings for this socket
            wrapper.setBlockingStatus(block);
            if (block) {
                Socket.timeoutSet(socket, endpoint.getSoTimeout() * 1000);
            } else {
                Socket.timeoutSet(socket, 0);
            }

            // Downgrade the lock
            try {
                readLock.lock();
                writeLock.unlock();
                return doWriteInternal(b, off, len);
            } finally {
                readLock.unlock();
            }
        } finally {
            // Should have been released above but may not have been on some
            // exception paths
            if (writeLock.isHeldByCurrentThread()) {
                writeLock.unlock();
            }
        }
    }


    private int doWriteInternal(byte[] b, int off, int len) throws IOException {

        int start = off;
        int left = len;
        int written;

        do {
            if (endpoint.isSSLEnabled()) {
                if (sslOutputBuffer.remaining() == 0) {
                    // Buffer was fully written last time around
                    sslOutputBuffer.clear();
                    if (left < SSL_OUTPUT_BUFFER_SIZE) {
                        sslOutputBuffer.put(b, start, left);
                    } else {
                        sslOutputBuffer.put(b, start, SSL_OUTPUT_BUFFER_SIZE);
                    }
                    sslOutputBuffer.flip();
                } else {
                    // Buffer still has data from previous attempt to write
                    // APR + SSL requires that exactly the same parameters are
                    // passed when re-attempting the write
                }
                written = Socket.sendb(socket, sslOutputBuffer,
                        sslOutputBuffer.position(), sslOutputBuffer.limit());
                if (written > 0) {
                    sslOutputBuffer.position(
                            sslOutputBuffer.position() + written);
                }
            } else {
                written = Socket.send(socket, b, start, left);
            }
            if (Status.APR_STATUS_IS_EAGAIN(-written)) {
                written = 0;
            } else if (-written == Status.APR_EOF) {
                throw new EOFException(sm.getString("apr.clientAbort"));
            } else if ((OS.IS_WIN32 || OS.IS_WIN64) &&
                    (-written == Status.APR_OS_START_SYSERR + 10053)) {
                // 10053 on Windows is connection aborted
                throw new EOFException(sm.getString("apr.clientAbort"));
            } else if (written < 0) {
                throw new IOException(sm.getString("apr.write.error",
                        Integer.valueOf(-written), Long.valueOf(socket), wrapper));
            }
            start += written;
            left -= written;
        } while (written > 0 && left > 0);

        if (left > 0) {
            endpoint.getPoller().add(socket, -1, false, true);
        }
        return len - left;
    }


    @Override
    protected void doFlush() throws IOException {
        // TODO Auto-generated method stub
    }


    @Override
    protected void doClose() throws IOException {
        closed = true;
        // AbstractProcessor needs to trigger the close as multiple closes for
        // APR/native sockets will cause problems.
    }
}
