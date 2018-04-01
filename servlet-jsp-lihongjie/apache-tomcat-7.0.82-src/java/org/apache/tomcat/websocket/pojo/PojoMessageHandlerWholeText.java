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
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.Decoder.Text;
import javax.websocket.Decoder.TextStream;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.websocket.Util;


/**
 * Text specific concrete implementation for handling whole messages.
 */
public class PojoMessageHandlerWholeText
        extends PojoMessageHandlerWholeBase<String> {

    private static final StringManager sm =
            StringManager.getManager(Constants.PACKAGE_NAME);

    private final List<Decoder> decoders = new ArrayList<Decoder>();
    private final Class<?> primitiveType;

    public PojoMessageHandlerWholeText(Object pojo, Method method,
            Session session, EndpointConfig config,
            List<Class<? extends Decoder>> decoderClazzes, Object[] params,
            int indexPayload, boolean convert, int indexSession,
            long maxMessageSize) {
        super(pojo, method, session, params, indexPayload, convert,
                indexSession, maxMessageSize);

        // Update max text size handled by session
        if (maxMessageSize > -1 && maxMessageSize > session.getMaxTextMessageBufferSize()) {
            if (maxMessageSize > Integer.MAX_VALUE) {
                throw new IllegalArgumentException(sm.getString(
                        "pojoMessageHandlerWhole.maxBufferSize"));
            }
            session.setMaxTextMessageBufferSize((int) maxMessageSize);
        }

        // Check for primitives
        Class<?> type = method.getParameterTypes()[indexPayload];
        if (Util.isPrimitive(type)) {
            primitiveType = type;
            return;
        } else {
            primitiveType = null;
        }

        try {
            if (decoderClazzes != null) {
                for (Class<? extends Decoder> decoderClazz : decoderClazzes) {
                    if (Text.class.isAssignableFrom(decoderClazz)) {
                        Text<?> decoder = (Text<?>) decoderClazz.newInstance();
                        decoder.init(config);
                        decoders.add(decoder);
                    } else if (TextStream.class.isAssignableFrom(
                            decoderClazz)) {
                        TextStream<?> decoder =
                                (TextStream<?>) decoderClazz.newInstance();
                        decoder.init(config);
                        decoders.add(decoder);
                    } else {
                        // Binary decoder - ignore it
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(e);
        }
    }


    @Override
    protected Object decode(String message) throws DecodeException {
        // Handle primitives
        if (primitiveType != null) {
            return Util.coerceToType(primitiveType, message);
        }
        // Handle full decoders
        for (Decoder decoder : decoders) {
            if (decoder instanceof Text) {
                if (((Text<?>) decoder).willDecode(message)) {
                    return ((Text<?>) decoder).decode(message);
                }
            } else {
                StringReader r = new StringReader(message);
                try {
                    return ((TextStream<?>) decoder).decode(r);
                } catch (IOException ioe) {
                    throw new DecodeException(message, sm.getString(
                            "pojoMessageHandlerWhole.decodeIoFail"), ioe);
                }
            }
        }
        return null;
    }


    @Override
    protected Object convert(String message) {
        return new StringReader(message);
    }


    @Override
    protected void onClose() {
        for (Decoder decoder : decoders) {
            decoder.destroy();
        }
    }
}
