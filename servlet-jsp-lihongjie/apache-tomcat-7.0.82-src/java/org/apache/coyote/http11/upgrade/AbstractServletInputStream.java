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

import javax.servlet.ServletInputStream;

import org.apache.coyote.http11.upgrade.servlet31.ReadListener;
import org.apache.tomcat.util.res.StringManager;

/**
 * Implements the new Servlet 3.1 methods for {@link ServletInputStream}.
 */
public abstract class AbstractServletInputStream extends ServletInputStream {

    protected static final StringManager sm =
            StringManager.getManager(Constants.Package);

    private volatile boolean closeRequired = false;
    // Start in blocking-mode
    private volatile Boolean ready = Boolean.TRUE;
    private volatile ReadListener listener = null;
    private volatile ClassLoader applicationLoader = null;

    /**
     * New Servlet 3.1 method.
     */
    public final boolean isFinished() {
        if (listener == null) {
            throw new IllegalStateException(
                    sm.getString("upgrade.sis.isFinished.ise"));
        }
        // The only way to finish an HTTP Upgrade connection is to close the
        // socket.
        return false;
    }


    /**
     * New Servlet 3.1 method.
     */
    public final boolean isReady() {
        if (listener == null) {
            throw new IllegalStateException(
                    sm.getString("upgrade.sis.isReady.ise"));
        }

        // If we already know the current state, return it.
        if (ready != null) {
            return ready.booleanValue();
        }

        try {
            ready = Boolean.valueOf(doIsReady());
        } catch (IOException e) {
            onError(e);
        }
        return ready.booleanValue();
    }


    /**
     * New Servlet 3.1 method.
     */
    public final void setReadListener(ReadListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException(
                    sm.getString("upgrade.sis.readListener.null"));
        }
        if (this.listener != null) {
            throw new IllegalArgumentException(
                    sm.getString("upgrade.sis.readListener.set"));
        }
        this.listener = listener;
        this.applicationLoader = Thread.currentThread().getContextClassLoader();
        // Switching to non-blocking. Don't know if data is available.
        ready = null;
    }


    @Override
    public final int read() throws IOException {
        preReadChecks();

        return readInternal();
    }


    @Override
    public final int readLine(byte[] b, int off, int len) throws IOException {
        preReadChecks();

        if (len <= 0) {
            return 0;
        }
        int count = 0, c;

        while ((c = readInternal()) != -1) {
            b[off++] = (byte) c;
            count++;
            if (c == '\n' || count == len) {
                break;
            }
        }
        return count > 0 ? count : -1;
    }


    @Override
    public final int read(byte[] b, int off, int len) throws IOException {
        preReadChecks();

        try {
            return doRead(listener == null, b, off, len);
        } catch (IOException ioe) {
            closeRequired = true;
            throw ioe;
        }
    }



    @Override
    public void close() throws IOException {
        closeRequired = true;
        doClose();
    }


    private void preReadChecks() {
        if (listener != null && (ready == null || !ready.booleanValue())) {
            throw new IllegalStateException(
                    sm.getString("upgrade.sis.read.ise"));
        }
        // No longer know if data is available
        ready = null;
    }


    private int readInternal() throws IOException {
        // Single byte reads for non-blocking need special handling so all
        // single byte reads run through this method.
        byte[] b = new byte[1];
        int result;
        try {
            result = doRead(listener == null, b, 0, 1);
        } catch (IOException ioe) {
            closeRequired = true;
            throw ioe;
        }
        if (result == 0) {
            return -1;
        } else if (result == -1) {
            // Will never happen with a network socket. An IOException will be
            // thrown when the client closes the connection.
            // Echo back the -1 to be safe.
            return -1;
        } else {
            return b[0] & 0xFF;
        }
    }


    protected final void onAllDataRead() throws IOException {
        if (listener == null) {
            return;
        }
        Thread thread = Thread.currentThread();
        ClassLoader originalClassLoader = thread.getContextClassLoader();
        try {
            thread.setContextClassLoader(applicationLoader);
            listener.onAllDataRead();
        } finally {
            thread.setContextClassLoader(originalClassLoader);
        }
    }


    protected final void onDataAvailable() throws IOException {
        if (listener == null) {
            return;
        }
        ready = Boolean.TRUE;
        Thread thread = Thread.currentThread();
        ClassLoader originalClassLoader = thread.getContextClassLoader();
        try {
            thread.setContextClassLoader(applicationLoader);
            listener.onDataAvailable();
        } finally {
            thread.setContextClassLoader(originalClassLoader);
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
        ready = Boolean.FALSE;
    }


    protected final boolean isCloseRequired() {
        return closeRequired;
    }


    protected abstract boolean doIsReady() throws IOException;

    /**
     * Abstract method to be overridden by concrete implementations. The base
     * class will ensure that there are no concurrent calls to this method for
     * the same socket.
     */
    protected abstract int doRead(boolean block, byte[] b, int off, int len)
            throws IOException;

    protected abstract void doClose() throws IOException;
}
