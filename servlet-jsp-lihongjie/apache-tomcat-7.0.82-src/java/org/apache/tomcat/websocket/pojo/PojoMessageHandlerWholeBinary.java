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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.Decoder.Binary;
import javax.websocket.Decoder.BinaryStream;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.apache.tomcat.util.res.StringManager;

/**
 * ByteBuffer specific concrete implementation for handling whole messages.
 */
public class PojoMessageHandlerWholeBinary
        extends PojoMessageHandlerWholeBase<ByteBuffer> {

    private static final StringManager sm =
            StringManager.getManager(Constants.PACKAGE_NAME);

    private final List<Decoder> decoders = new ArrayList<Decoder>();

    private final boolean isForInputStream;

    public PojoMessageHandlerWholeBinary(Object pojo, Method method,
            Session session, EndpointConfig config,
            List<Class<? extends Decoder>> decoderClazzes, Object[] params,
            int indexPayload, boolean convert, int indexSession,
            boolean isForInputStream, long maxMessageSize) {
        super(pojo, method, session, params, indexPayload, convert,
                indexSession, maxMessageSize);

        // Update binary text size handled by session
        if (maxMessageSize > -1 && maxMessageSize > session.getMaxBinaryMessageBufferSize()) {
            if (maxMessageSize > Integer.MAX_VALUE) {
                throw new IllegalArgumentException(sm.getString(
                        "pojoMessageHandlerWhole.maxBufferSize"));
            }
            session.setMaxBinaryMessageBufferSize((int) maxMessageSize);
        }

        try {
            if (decoderClazzes != null) {
                for (Class<? extends Decoder> decoderClazz : decoderClazzes) {
                    if (Binary.class.isAssignableFrom(decoderClazz)) {
                        Binary<?> decoder =
                                (Binary<?>) decoderClazz.newInstance();
                        decoder.init(config);
                        decoders.add(decoder);
                    } else if (BinaryStream.class.isAssignableFrom(
                            decoderClazz)) {
                        BinaryStream<?> decoder =
                                (BinaryStream<?>) decoderClazz.newInstance();
                        decoder.init(config);
                        decoders.add(decoder);
                    } else {
                        // Text decoder - ignore it
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(e);
        }
        this.isForInputStream = isForInputStream;
    }


    @Override
    protected Object decode(ByteBuffer message) throws DecodeException {
        for (Decoder decoder : decoders) {
            if (decoder instanceof Binary) {
                if (((Binary<?>) decoder).willDecode(message)) {
                    return ((Binary<?>) decoder).decode(message);
                }
            } else {
                byte[] array = new byte[message.limit() - message.position()];
                message.get(array);
                ByteArrayInputStream bais = new ByteArrayInputStream(array);
                try {
                    return ((BinaryStream<?>) decoder).decode(bais);
                } catch (IOException ioe) {
                    throw new DecodeException(message, sm.getString(
                            "pojoMessageHandlerWhole.decodeIoFail"), ioe);
                }
            }
        }
        return null;
    }


    @Override
    protected Object convert(ByteBuffer message) {
        byte[] array = new byte[message.remaining()];
        message.get(array);
        if (isForInputStream) {
            return new ByteArrayInputStream(array);
        } else {
            return array;
        }
    }


    @Override
    protected void onClose() {
        for (Decoder decoder : decoders) {
            decoder.destroy();
        }
    }
}
