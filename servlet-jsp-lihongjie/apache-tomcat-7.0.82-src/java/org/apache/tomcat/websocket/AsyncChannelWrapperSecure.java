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
package org.apache.tomcat.websocket;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.res.StringManager;

/**
 * Wraps the {@link AsynchronousSocketChannel} with SSL/TLS. This needs a lot
 * more testing before it can be considered robust.
 */
public class AsyncChannelWrapperSecure implements AsyncChannelWrapper {

    private static final Log log =
            LogFactory.getLog(AsyncChannelWrapperSecure.class);
    private static final StringManager sm =
            StringManager.getManager(Constants.PACKAGE_NAME);

    private static final ByteBuffer DUMMY = ByteBuffer.allocate(16921);
    private final AsynchronousSocketChannel socketChannel;
    private final SSLEngine sslEngine;
    private final ByteBuffer socketReadBuffer;
    private final ByteBuffer socketWriteBuffer;
    // One thread for read, one for write
    private final ExecutorService executor =
            Executors.newFixedThreadPool(2, new SecureIOThreadFactory());
    private AtomicBoolean writing = new AtomicBoolean(false);
    private AtomicBoolean reading = new AtomicBoolean(false);

    public AsyncChannelWrapperSecure(AsynchronousSocketChannel socketChannel,
            SSLEngine sslEngine) {
        this.socketChannel = socketChannel;
        this.sslEngine = sslEngine;

        int socketBufferSize = sslEngine.getSession().getPacketBufferSize();
        socketReadBuffer = ByteBuffer.allocateDirect(socketBufferSize);
        socketWriteBuffer = ByteBuffer.allocateDirect(socketBufferSize);
    }

    @Override
    public Future<Integer> read(ByteBuffer dst) {
        WrapperFuture<Integer,Void> future = new WrapperFuture<Integer, Void>();

        if (!reading.compareAndSet(false, true)) {
            throw new IllegalStateException(sm.getString(
                    "asyncChannelWrapperSecure.concurrentRead"));
        }

        ReadTask readTask = new ReadTask(dst, future);

        executor.execute(readTask);

        return future;
    }

    @Override
    public <B,A extends B> void read(ByteBuffer dst, A attachment,
            CompletionHandler<Integer,B> handler) {

        WrapperFuture<Integer,B> future =
                new WrapperFuture<Integer, B>(handler, attachment);

        if (!reading.compareAndSet(false, true)) {
            throw new IllegalStateException(sm.getString(
                    "asyncChannelWrapperSecure.concurrentRead"));
        }

        ReadTask readTask = new ReadTask(dst, future);

        executor.execute(readTask);
    }

    @Override
    public Future<Integer> write(ByteBuffer src) {

        WrapperFuture<Long,Void> inner = new WrapperFuture<Long, Void>();

        if (!writing.compareAndSet(false, true)) {
            throw new IllegalStateException(sm.getString(
                    "asyncChannelWrapperSecure.concurrentWrite"));
        }

        WriteTask writeTask =
                new WriteTask(new ByteBuffer[] {src}, 0, 1, inner);

        executor.execute(writeTask);

        Future<Integer> future = new LongToIntegerFuture(inner);
        return future;
    }

    @Override
    public <B,A extends B> void write(ByteBuffer[] srcs, int offset, int length,
            long timeout, TimeUnit unit, A attachment,
            CompletionHandler<Long,B> handler) {

        WrapperFuture<Long,B> future =
                new WrapperFuture<Long, B>(handler, attachment);

        if (!writing.compareAndSet(false, true)) {
            throw new IllegalStateException(sm.getString(
                    "asyncChannelWrapperSecure.concurrentWrite"));
        }

        WriteTask writeTask = new WriteTask(srcs, offset, length, future);

        executor.execute(writeTask);
    }

    @Override
    public void close() {
        try {
            socketChannel.close();
        } catch (IOException e) {
            log.info(sm.getString("asyncChannelWrapperSecure.closeFail"));
        }
        executor.shutdownNow();
    }

    @Override
    public Future<Void> handshake() throws SSLException {

        WrapperFuture<Void,Void> wFuture = new WrapperFuture<Void, Void>();

        Thread t = new WebSocketSslHandshakeThread(wFuture);
        t.start();

        return wFuture;
    }


    private class WriteTask implements Runnable {

        private final ByteBuffer[] srcs;
        private final int offset;
        private final int length;
        private final WrapperFuture<Long,?> future;

        public WriteTask(ByteBuffer[] srcs, int offset, int length,
                WrapperFuture<Long,?> future) {
            this.srcs = srcs;
            this.future = future;
            this.offset = offset;
            this.length = length;
        }

        @Override
        public void run() {
            long written = 0;

            try {
                for (int i = offset; i < offset + length; i++) {
                    ByteBuffer src = srcs[i];
                    while (src.hasRemaining()) {
                        socketWriteBuffer.clear();

                        // Encrypt the data
                        SSLEngineResult r = sslEngine.wrap(src, socketWriteBuffer);
                        written += r.bytesConsumed();
                        Status s = r.getStatus();

                        if (s == Status.OK || s == Status.BUFFER_OVERFLOW) {
                            // Need to write out the bytes and may need to read from
                            // the source again to empty it
                        } else {
                            // Status.BUFFER_UNDERFLOW - only happens on unwrap
                            // Status.CLOSED - unexpected
                            throw new IllegalStateException(sm.getString(
                                    "asyncChannelWrapperSecure.statusWrap"));
                        }

                        // Check for tasks
                        if (r.getHandshakeStatus() == HandshakeStatus.NEED_TASK) {
                            Runnable runnable = sslEngine.getDelegatedTask();
                            while (runnable != null) {
                                runnable.run();
                                runnable = sslEngine.getDelegatedTask();
                            }
                        }

                        socketWriteBuffer.flip();

                        // Do the write
                        int toWrite = r.bytesProduced();
                        while (toWrite > 0) {
                            Future<Integer> f =
                                    socketChannel.write(socketWriteBuffer);
                            Integer socketWrite = f.get();
                            toWrite -= socketWrite.intValue();
                        }
                    }
                }


                if (writing.compareAndSet(true, false)) {
                    future.complete(Long.valueOf(written));
                } else {
                    future.fail(new IllegalStateException(sm.getString(
                            "asyncChannelWrapperSecure.wrongStateWrite")));
                }
            } catch (Exception e) {
                writing.set(false);
                future.fail(e);
            }
        }
    }


    private class ReadTask implements Runnable {

        private final ByteBuffer dest;
        private final WrapperFuture<Integer,?> future;

        public ReadTask(ByteBuffer dest, WrapperFuture<Integer,?> future) {
            this.dest = dest;
            this.future = future;
        }

        @Override
        public void run() {
            int read = 0;

            boolean forceRead = false;

            try {
                while (read == 0) {
                    socketReadBuffer.compact();

                    if (forceRead) {
                        forceRead = false;
                        Future<Integer> f = socketChannel.read(socketReadBuffer);
                        Integer socketRead = f.get();
                        if (socketRead.intValue() == -1) {
                            throw new EOFException(sm.getString(
                                    "asyncChannelWrapperSecure.eof"));
                        }
                    }

                    socketReadBuffer.flip();

                    if (socketReadBuffer.hasRemaining()) {
                        // Decrypt the data in the buffer
                        SSLEngineResult r =
                                sslEngine.unwrap(socketReadBuffer, dest);
                        read += r.bytesProduced();
                        Status s = r.getStatus();

                        if (s == Status.OK) {
                            // Bytes available for reading and there may be
                            // sufficient data in the socketReadBuffer to
                            // support further reads without reading from the
                            // socket
                        } else if (s == Status.BUFFER_UNDERFLOW) {
                            // There is partial data in the socketReadBuffer
                            if (read == 0) {
                                // Need more data before the partial data can be
                                // processed and some output generated
                                forceRead = true;
                            }
                            // else return the data we have and deal with the
                            // partial data on the next read
                        } else if (s == Status.BUFFER_OVERFLOW) {
                            // Not enough space in the destination buffer to
                            // store all of the data. We could use a bytes read
                            // value of -bufferSizeRequired to signal the new
                            // buffer size required but an explicit exception is
                            // clearer.
                            if (reading.compareAndSet(true, false)) {
                                throw new ReadBufferOverflowException(sslEngine.
                                        getSession().getApplicationBufferSize());
                            } else {
                                future.fail(new IllegalStateException(sm.getString(
                                        "asyncChannelWrapperSecure.wrongStateRead")));
                            }
                        } else {
                            // Status.CLOSED - unexpected
                            throw new IllegalStateException(sm.getString(
                                    "asyncChannelWrapperSecure.statusUnwrap"));
                        }

                        // Check for tasks
                        if (r.getHandshakeStatus() == HandshakeStatus.NEED_TASK) {
                            Runnable runnable = sslEngine.getDelegatedTask();
                            while (runnable != null) {
                                runnable.run();
                                runnable = sslEngine.getDelegatedTask();
                            }
                        }
                    } else {
                        forceRead = true;
                    }
                }


                if (reading.compareAndSet(true, false)) {
                    future.complete(Integer.valueOf(read));
                } else {
                    future.fail(new IllegalStateException(sm.getString(
                            "asyncChannelWrapperSecure.wrongStateRead")));
                }
            } catch (Exception e) {
                reading.set(false);
                future.fail(e);
            }
        }
    }


    private class WebSocketSslHandshakeThread extends Thread {

        private final WrapperFuture<Void,Void> hFuture;

        private HandshakeStatus handshakeStatus;
        private Status resultStatus;

        public WebSocketSslHandshakeThread(WrapperFuture<Void,Void> hFuture) {
            this.hFuture = hFuture;
        }

        @Override
        public void run() {
            try {
                sslEngine.beginHandshake();
                // So the first compact does the right thing
                socketReadBuffer.position(socketReadBuffer.limit());

                handshakeStatus = sslEngine.getHandshakeStatus();
                resultStatus = Status.OK;

                boolean handshaking = true;

                while(handshaking) {
                    switch (handshakeStatus) {
                        case NEED_WRAP: {
                            socketWriteBuffer.clear();
                            SSLEngineResult r =
                                    sslEngine.wrap(DUMMY, socketWriteBuffer);
                            checkResult(r, true);
                            socketWriteBuffer.flip();
                            Future<Integer> fWrite =
                                    socketChannel.write(socketWriteBuffer);
                            fWrite.get();
                            break;
                        }
                        case NEED_UNWRAP: {
                            socketReadBuffer.compact();
                            if (socketReadBuffer.position() == 0 ||
                                    resultStatus == Status.BUFFER_UNDERFLOW) {
                                Future<Integer> fRead =
                                        socketChannel.read(socketReadBuffer);
                                fRead.get();
                            }
                            socketReadBuffer.flip();
                            SSLEngineResult r =
                                    sslEngine.unwrap(socketReadBuffer, DUMMY);
                            checkResult(r, false);
                            break;
                        }
                        case NEED_TASK: {
                            Runnable r = null;
                            while ((r = sslEngine.getDelegatedTask()) != null) {
                                r.run();
                            }
                            handshakeStatus = sslEngine.getHandshakeStatus();
                            break;
                        }
                        case FINISHED: {
                            handshaking = false;
                            break;
                        }
                        default: {
                            throw new SSLException("TODO");
                        }
                    }
                }
            } catch (SSLException e) {
                hFuture.fail(e);
            } catch (InterruptedException e) {
                hFuture.fail(e);
            } catch (ExecutionException e) {
                hFuture.fail(e);
            }

            hFuture.complete(null);
        }

        private void checkResult(SSLEngineResult result, boolean wrap)
                throws SSLException {

            handshakeStatus = result.getHandshakeStatus();
            resultStatus = result.getStatus();

            if (resultStatus != Status.OK &&
                    (wrap || resultStatus != Status.BUFFER_UNDERFLOW)) {
                throw new SSLException("TODO");
            }
            if (wrap && result.bytesConsumed() != 0) {
                throw new SSLException("TODO");
            }
            if (!wrap && result.bytesProduced() != 0) {
                throw new SSLException("TODO");
            }
        }
    }


    private static class WrapperFuture<T,A> implements Future<T> {

        private final CompletionHandler<T,A> handler;
        private final A attachment;

        private volatile T result = null;
        private volatile Throwable throwable = null;
        private CountDownLatch completionLatch = new CountDownLatch(1);

        public WrapperFuture() {
            this(null, null);
        }

        public WrapperFuture(CompletionHandler<T,A> handler, A attachment) {
            this.handler = handler;
            this.attachment = attachment;
        }

        public void complete(T result) {
            this.result = result;
            completionLatch.countDown();
            if (handler != null) {
                handler.completed(result, attachment);
            }
        }

        public void fail(Throwable t) {
            throwable = t;
            completionLatch.countDown();
            if (handler != null) {
                handler.failed(throwable, attachment);
            }
        }

        @Override
        public final boolean cancel(boolean mayInterruptIfRunning) {
            // Could support cancellation by closing the connection
            return false;
        }

        @Override
        public final boolean isCancelled() {
            // Could support cancellation by closing the connection
            return false;
        }

        @Override
        public final boolean isDone() {
            return completionLatch.getCount() > 0;
        }

        @Override
        public T get() throws InterruptedException, ExecutionException {
            completionLatch.await();
            if (throwable != null) {
                throw new ExecutionException(throwable);
            }
            return result;
        }

        @Override
        public T get(long timeout, TimeUnit unit)
                throws InterruptedException, ExecutionException,
                TimeoutException {
            boolean latchResult = completionLatch.await(timeout, unit);
            if (latchResult == false) {
                throw new TimeoutException();
            }
            if (throwable != null) {
                throw new ExecutionException(throwable);
            }
            return result;
        }
    }

    private static final class LongToIntegerFuture implements Future<Integer> {

        private final Future<Long> wrapped;

        public LongToIntegerFuture(Future<Long> wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return wrapped.cancel(mayInterruptIfRunning);
        }

        @Override
        public boolean isCancelled() {
            return wrapped.isCancelled();
        }

        @Override
        public boolean isDone() {
            return wrapped.isDone();
        }

        @Override
        public Integer get() throws InterruptedException, ExecutionException {
            Long result = wrapped.get();
            if (result.longValue() > Integer.MAX_VALUE) {
                throw new ExecutionException(sm.getString(
                        "asyncChannelWrapperSecure.tooBig", result), null);
            }
            return Integer.valueOf(result.intValue());
        }

        @Override
        public Integer get(long timeout, TimeUnit unit)
                throws InterruptedException, ExecutionException,
                TimeoutException {
            Long result = wrapped.get(timeout, unit);
            if (result.longValue() > Integer.MAX_VALUE) {
                throw new ExecutionException(sm.getString(
                        "asyncChannelWrapperSecure.tooBig", result), null);
            }
            return Integer.valueOf(result.intValue());
        }
    }


    private static class SecureIOThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("WebSocketClient-SecureIO-" + count.incrementAndGet());
            // No need to set the context class loader. The threads will be
            // cleaned up when the connection is closed.
            t.setDaemon(true);
            return t;
        }
    }
}
