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
package org.apache.tomcat.websocket.server;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import javax.websocket.SendHandler;
import javax.websocket.SendResult;

import org.apache.coyote.http11.upgrade.AbstractServletOutputStream;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.websocket.Transformation;
import org.apache.tomcat.websocket.WsRemoteEndpointImplBase;

/**
 * This is the server side {@link javax.websocket.RemoteEndpoint} implementation
 * - i.e. what the server uses to send data to the client. Communication is over
 * a {@link javax.servlet.ServletOutputStream}.
 */
public class WsRemoteEndpointImplServer extends WsRemoteEndpointImplBase {

    private static final StringManager sm =
            StringManager.getManager(Constants.PACKAGE_NAME);
    private static final Log log = LogFactory.getLog(WsRemoteEndpointImplServer.class);

    private static final Queue<OnResultRunnable> onResultRunnables =
            new ConcurrentLinkedQueue<OnResultRunnable>();

    private final AbstractServletOutputStream sos;
    private final WsWriteTimeout wsWriteTimeout;
    private final ExecutorService executorService;
    private volatile SendHandler handler = null;
    private volatile ByteBuffer[] buffers = null;

    private volatile long timeoutExpiry = -1;
    private volatile boolean close;


    public WsRemoteEndpointImplServer(AbstractServletOutputStream sos,
            WsServerContainer serverContainer) {
        this.sos = sos;
        this.wsWriteTimeout = serverContainer.getTimeout();
        this.executorService = serverContainer.getExecutorService();
    }


    @Override
    protected final boolean isMasked() {
        return false;
    }


    @Override
    protected void doWrite(SendHandler handler, ByteBuffer... buffers) {
        this.handler = handler;
        this.buffers = buffers;
        // This is definitely the same thread that triggered the write so a
        // dispatch will be required.
        onWritePossible(true);
    }


    public void onWritePossible(boolean useDispatch) {
        boolean complete = true;
        try {
            // If this is false there will be a call back when it is true
            while (sos.isReady()) {
                complete = true;
                for (ByteBuffer buffer : buffers) {
                    if (buffer.hasRemaining()) {
                        complete = false;
                        sos.write(buffer.array(), buffer.arrayOffset(),
                                buffer.limit());
                        buffer.position(buffer.limit());
                        break;
                    }
                }
                if (complete) {
                    wsWriteTimeout.unregister(this);
                    clearHandler(null, useDispatch);
                    if (close) {
                        close();
                    }
                    break;
                }
            }

        } catch (IOException ioe) {
            wsWriteTimeout.unregister(this);
            clearHandler(ioe, useDispatch);
            close();
        }
        if (!complete) {
            // Async write is in progress

            long timeout = getSendTimeout();
            if (timeout > 0) {
                // Register with timeout thread
                timeoutExpiry = timeout + System.currentTimeMillis();
                wsWriteTimeout.register(this);
            }
        }
    }


    @Override
    protected void doClose() {
        if (handler != null) {
            // close() can be triggered by a wide range of scenarios. It is far
            // simpler just to always use a dispatch than it is to try and track
            // whether or not this method was called by the same thread that
            // triggered the write
            clearHandler(new EOFException(), true);
        }
        try {
            sos.close();
        } catch (IOException e) {
            if (log.isInfoEnabled()) {
                log.info(sm.getString("wsRemoteEndpointServer.closeFailed"), e);
            }
        }
        wsWriteTimeout.unregister(this);
    }


    protected long getTimeoutExpiry() {
        return timeoutExpiry;
    }


    /*
     * Currently this is only called from the background thread so we could just
     * call clearHandler() with useDispatch == false but the method parameter
     * was added in case other callers started to use this method to make sure
     * that those callers think through what the correct value of useDispatch is
     * for them.
     */
    protected void onTimeout(boolean useDispatch) {
        if (handler != null) {
            clearHandler(new SocketTimeoutException(), useDispatch);
        }
        close();
    }


    @Override
    protected void setTransformation(Transformation transformation) {
        // Overridden purely so it is visible to other classes in this package
        super.setTransformation(transformation);
    }


    /**
     *
     * @param t             The throwable associated with any error that
     *                      occurred
     * @param useDispatch   Should {@link SendHandler#onResult(SendResult)} be
     *                      called from a new thread, keeping in mind the
     *                      requirements of
     *                      {@link javax.websocket.RemoteEndpoint.Async}
     */
    private void clearHandler(Throwable t, boolean useDispatch) {
        // Setting the result marks this (partial) message as
        // complete which means the next one may be sent which
        // could update the value of the handler. Therefore, keep a
        // local copy before signalling the end of the (partial)
        // message.
        SendHandler sh = handler;
        handler = null;
        buffers = null;
        if (sh != null) {
            if (useDispatch) {
                OnResultRunnable r = onResultRunnables.poll();
                if (r == null) {
                    r = new OnResultRunnable(onResultRunnables);
                }
                r.init(sh, t);
                if (executorService == null || executorService.isShutdown()) {
                    // Can't use the executor so call the runnable directly.
                    // This may not be strictly specification compliant in all
                    // cases but during shutdown only close messages are going
                    // to be sent so there should not be the issue of nested
                    // calls leading to stack overflow as described in bug
                    // 55715. The issues with nested calls was the reason for
                    // the separate thread requirement in the specification.
                    r.run();
                } else {
                    executorService.execute(r);
                }
            } else {
                if (t == null) {
                    sh.onResult(new SendResult());
                } else {
                    sh.onResult(new SendResult(t));
                }
            }
        }
    }


    private static class OnResultRunnable implements Runnable {

        private final Queue<OnResultRunnable> queue;

        private volatile SendHandler sh;
        private volatile Throwable t;

        private OnResultRunnable(Queue<OnResultRunnable> queue) {
            this.queue = queue;
        }

        private void init(SendHandler sh, Throwable t) {
            this.sh = sh;
            this.t = t;
        }

        @Override
        public void run() {
            if (t == null) {
                sh.onResult(new SendResult());
            } else {
                sh.onResult(new SendResult(t));
            }
            t = null;
            sh = null;
            // Return the Runnable to the queue when it has been finished with
            // Note if this method takes an age to finish there shouldn't be any
            // thread safety issues as the fields are cleared above.
            queue.add(this);
        }
    }
}
