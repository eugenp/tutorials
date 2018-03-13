/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tomcat.websocket.pojo;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import javax.websocket.EncodeException;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.websocket.WrappedMessageHandler;

/**
 * Common implementation code for the POJO message handlers.
 *
 * @param <T>   The type of message to handle
 */
public abstract class PojoMessageHandlerBase<T>
        implements WrappedMessageHandler {

    protected final Object pojo;
    protected final Method method;
    protected final Session session;
    protected final Object[] params;
    protected final int indexPayload;
    protected final boolean convert;
    protected final int indexSession;
    protected final long maxMessageSize;

    public PojoMessageHandlerBase(Object pojo, Method method,
            Session session, Object[] params, int indexPayload, boolean convert,
            int indexSession, long maxMessageSize) {
        this.pojo = pojo;
        this.method = method;
        // TODO: The method should already be accessible here but the following
        // code seems to be necessary in some as yet not fully understood cases.
        try {
            this.method.setAccessible(true);
        } catch (Exception e) {
            // It is better to make sure the method is accessible, but
            // ignore exceptions and hope for the best
        }
        this.session = session;
        this.params = params;
        this.indexPayload = indexPayload;
        this.convert = convert;
        this.indexSession = indexSession;
        this.maxMessageSize = maxMessageSize;
    }


    protected final void processResult(Object result) {
        if (result == null) {
            return;
        }

        RemoteEndpoint.Basic remoteEndpoint = session.getBasicRemote();
        try {
            if (result instanceof String) {
                remoteEndpoint.sendText((String) result);
            } else if (result instanceof ByteBuffer) {
                remoteEndpoint.sendBinary((ByteBuffer) result);
            } else if (result instanceof byte[]) {
                remoteEndpoint.sendBinary(ByteBuffer.wrap((byte[]) result));
            } else {
                remoteEndpoint.sendObject(result);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        } catch (EncodeException ee) {
            throw new IllegalStateException(ee);
        }
    }


    /**
     * Expose the POJO if it is a message handler so the Session is able to
     * match requests to remove handlers if the original handler has been
     * wrapped.
     */
    @Override
    public final MessageHandler getWrappedHandler() {
        if (pojo instanceof MessageHandler) {
            return (MessageHandler) pojo;
        } else {
            return null;
        }
    }


    @Override
    public final long getMaxMessageSize() {
        return maxMessageSize;
    }


    protected final void handlePojoMethodException(Throwable t) {
        t = ExceptionUtils.unwrapInvocationTargetException(t);
        ExceptionUtils.handleThrowable(t);
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        } else {
            throw new RuntimeException(t.getMessage(), t);
        }
    }
}
