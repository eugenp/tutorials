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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import javax.websocket.SendHandler;
import javax.websocket.SendResult;

import org.apache.tomcat.util.res.StringManager;


/**
 * Converts a Future to a SendHandler.
 */
class FutureToSendHandler implements Future<Void>, SendHandler {

    private static final StringManager sm = StringManager.getManager(Constants.PACKAGE_NAME);

    private final CountDownLatch latch = new CountDownLatch(1);
    private final WsSession wsSession;
    private final boolean closeMessage;
    private volatile AtomicReference<SendResult> result = new AtomicReference<SendResult>(null);

    public FutureToSendHandler(WsSession wsSession) {
        this(wsSession, false);
    }


    public FutureToSendHandler(WsSession wsSession, boolean closeMessage) {
        this.wsSession = wsSession;
        this.closeMessage = closeMessage;
    }


    public boolean isCloseMessage() {
        return closeMessage;
    }


    // --------------------------------------------------------- SendHandler

    @Override
    public void onResult(SendResult result) {
        this.result.compareAndSet(null, result);
        latch.countDown();
    }


    // -------------------------------------------------------------- Future

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        // Cancelling the task is not supported
        return false;
    }

    @Override
    public boolean isCancelled() {
        // Cancelling the task is not supported
        return false;
    }

    @Override
    public boolean isDone() {
        return latch.getCount() == 0;
    }

    @Override
    public Void get() throws InterruptedException,
            ExecutionException {
        try {
            wsSession.registerFuture(this);
            latch.await();
        } finally {
            wsSession.unregisterFuture(this);
        }
        if (result.get().getException() != null) {
            throw new ExecutionException(result.get().getException());
        }
        return null;
    }

    @Override
    public Void get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException,
            TimeoutException {
        boolean retval = false;
        try {
            wsSession.registerFuture(this);
            retval = latch.await(timeout, unit);
        } finally {
            wsSession.unregisterFuture(this);

        }
        if (retval == false) {
            throw new TimeoutException(sm.getString("futureToSendHandler.timeout",
                    Long.valueOf(timeout), unit.toString().toLowerCase()));
        }
        if (result.get().getException() != null) {
            throw new ExecutionException(result.get().getException());
        }
        return null;
    }
}
