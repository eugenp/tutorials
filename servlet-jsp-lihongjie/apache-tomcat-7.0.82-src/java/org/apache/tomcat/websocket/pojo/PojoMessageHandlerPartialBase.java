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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import javax.websocket.DecodeException;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.apache.tomcat.websocket.WsSession;

/**
 * Common implementation code for the POJO partial message handlers. All
 * the real work is done in this class and in the superclass.
 *
 * @param <T>   The type of message to handle
 */
public abstract class PojoMessageHandlerPartialBase<T>
        extends PojoMessageHandlerBase<T> implements MessageHandler.Partial<T> {

    private final int indexBoolean;

    public PojoMessageHandlerPartialBase(Object pojo, Method method,
            Session session, Object[] params, int indexPayload,
            boolean convert, int indexBoolean, int indexSession,
            long maxMessageSize) {
        super(pojo, method, session, params, indexPayload, convert,
                indexSession, maxMessageSize);
        this.indexBoolean = indexBoolean;
    }


    @Override
    public final void onMessage(T message, boolean last) {
        if (params.length == 1 && params[0] instanceof DecodeException) {
            ((WsSession) session).getLocal().onError(session,
                    (DecodeException) params[0]);
            return;
        }
        Object[] parameters = params.clone();
        if (indexBoolean != -1) {
            parameters[indexBoolean] = Boolean.valueOf(last);
        }
        if (indexSession != -1) {
            parameters[indexSession] = session;
        }
        if (convert) {
            parameters[indexPayload] = ((ByteBuffer) message).array();
        } else {
            parameters[indexPayload] = message;
        }
        Object result = null;
        try {
            result = method.invoke(pojo, parameters);
        } catch (IllegalAccessException e) {
            handlePojoMethodException(e);
        } catch (InvocationTargetException e) {
            handlePojoMethodException(e);
        }
        processResult(result);
    }
}
