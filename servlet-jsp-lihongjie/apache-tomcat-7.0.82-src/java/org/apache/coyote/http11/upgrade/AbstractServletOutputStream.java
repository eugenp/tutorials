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

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import org.apache.coyote.http11.upgrade.servlet31.WriteListener;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.res.StringManager;

/**
 * Implements the new Servlet 3.1 methods for {@link ServletOutputStream}.
 */
public abstract class AbstractServletOutputStream extends ServletOutputStream {

    protected static final StringManager sm =
            StringManager.getManager(Constants.Package);

    // Used to ensure that isReady() and onWritePossible() have a consistent
    // view of buffer and fireListener when determining if the listener should
    // fire.
    private final Object fireListenerLock = new Object();

    // Used to ensure that only one thread writes to the socket at a time and
    // that buffer is consistently updated with any unwritten data after the
    // write. Note it is not necessary to hold this lock when checking if buffer
    // contains data but, depending on how the result is used, some form of
    // synchronization may be required (see fireListenerLock for an example).
    private final Object writeLock = new Object();

    private volatile boolean closeRequired = false;

    // Start in blocking-mode
    private volatile WriteListener listener = null;

    // Guarded by fireListenerLock
    private volatile boolean fireListener = false;

    private volatile ClassLoader applicationLoader = null;

    // Writes guarded by writeLock
    private volatile byte[] buffer;
    private volatile int bufferPos;
    private volatile int bufferLimit;
    private final int asyncWriteBufferSize;


    public AbstractServletOutputStream(int asyncWriteBufferSize) {
        this.asyncWriteBufferSize = asyncWriteBufferSize;
        buffer = new byte[asyncWriteBufferSize];
    }


    /**
     * New Servlet 3.1 method.
     */
    public final boolean isReady() {
        if (listener == null) {
            throw new IllegalStateException(
                    sm.getString("upgrade.sos.canWrite.ise"));
        }

        // Make sure isReady() and onWritePossible() have a consistent view of
        // buffer and fireListener when determining if the listener should fire
        synchronized (fireListenerLock) {
            boolean result = (bufferLimit == 0);
            fireListener = !result;
            return result;
        }
    }

    /**
     * New Servlet 3.1 method.
     */
    public final void setWriteListener(WriteListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException(
                    sm.getString("upgrade.sos.writeListener.null"));
        }
        if (this.listener != null) {
            throw new IllegalArgumentException(
                    sm.getString("upgrade.sos.writeListener.set"));
        }
        this.listener = listener;
        this.applicationLoader = Thread.currentThread().getContextClassLoader();
    }

    protected final boolean isCloseRequired() {
        return closeRequired;
    }

    @Override
    public void write(int b) throws IOException {
        synchronized (writeLock) {
            preWriteChecks();
            writeInternal(new byte[] { (byte) b }, 0, 1);
        }
    }


    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        synchronized (writeLock) {
            preWriteChecks();
            writeInternal(b, off, len);
        }
    }


    @Override
    public void close() throws IOException {
        closeRequired = true;
        doClose();
    }

    private void preWriteChecks() {
        if (bufferLimit != 0) {
            throw new IllegalStateException(sm.getString("upgrade.sis.write.ise"));
        }
    }


    /**
     * Must hold writeLock to call this method.
     */
    private void writeInternal(byte[] b, int off, int len) throws IOException {
        if (listener == null) {
            // Simple case - blocking IO
            doWrite(true, b, off, len);
        } else {
            // Non-blocking IO
            // If the non-blocking read does not complete, doWrite() will add
            // the socket back into the poller. The poller may trigger a new
            // write event before this method has finished updating buffer. The
            // writeLock sync makes sure that buffer is updated before the next
            // write executes.
            int written = doWrite(false, b, off, len);
            if (written < len) {
                if (b == buffer) {
                    // This is a partial write of the existing buffer. Just
                    // increment the current position
                    bufferPos += written;
                } else {
                    // This is a new partial write
                    int bytesLeft = len - written;
                    if (bytesLeft > buffer.length) {
                        buffer = new byte[bytesLeft];
                    } else if (bytesLeft < asyncWriteBufferSize &&
                            buffer.length > asyncWriteBufferSize) {
                        buffer = new byte[asyncWriteBufferSize];
                    }
                    bufferPos = 0;
                    bufferLimit = bytesLeft;
                    System.arraycopy(b, off + written, buffer, bufferPos, bufferLimit);
                }
            } else {
                bufferLimit = 0;
            }
        }
    }


    protected final void onWritePossible() throws IOException {
        try {
            synchronized (writeLock) {
                if (bufferLimit > 0) {
                    writeInternal(buffer, bufferPos, bufferLimit - bufferPos);
                }
            }
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            onError(t);
            if (t instanceof IOException) {
                throw (IOException) t;
            } else {
                throw new IOException(t);
            }
        }

        // Make sure isReady() and onWritePossible() have a consistent view of
        // buffer and fireListener when determining if the listener should fire
        boolean fire = false;

        synchronized (fireListenerLock) {
            if (bufferLimit == 0 && fireListener) {
                fireListener = false;
                fire = true;
            }
        }
        if (fire) {
            Thread thread = Thread.currentThread();
            ClassLoader originalClassLoader = thread.getContextClassLoader();
            try {
                thread.setContextClassLoader(applicationLoader);
                listener.onWritePossible();
            } finally {
                thread.setContextClassLoader(originalClassLoader);
            }
        }
    }

    protected final void onError(Throwable t) {
        if (listener == null) {
            return;
        }
        Thread thread = Thread.currentThread();
        ClassLoader originalClassLoader = thread.getContextClassLoader();
        try {
            thread.setContextClassLoader(applicationLoader);
            listener.onError(t);
        } finally {
            thread.setContextClassLoader(originalClassLoader);
        }
    }


    /**
     * Abstract method to be overridden by concrete implementations. The base
     * class will ensure that there are no concurrent calls to this method for
     * the same socket by ensuring that the writeLock is held when making any
     * calls to this method.
     */
    protected abstract int doWrite(boolean block, byte[] b, int off, int len)
            throws IOException;

    protected abstract void doFlush() throws IOException;

    protected abstract void doClose() throws IOException;
}
