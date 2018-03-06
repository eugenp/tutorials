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
package org.apache.tomcat.websocket.pojo;

import java.io.InputStream;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.DeploymentException;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.websocket.DecoderEntry;
import org.apache.tomcat.websocket.Util;
import org.apache.tomcat.websocket.Util.DecoderMatch;

/**
 * For a POJO class annotated with
 * {@link javax.websocket.server.ServerEndpoint}, an instance of this class
 * creates and caches the method handler, method information and parameter
 * information for the onXXX calls.
 */
public class PojoMethodMapping {

    private static final StringManager sm =
            StringManager.getManager(Constants.PACKAGE_NAME);

    private final Method onOpen;
    private final Method onClose;
    private final Method onError;
    private final PojoPathParam[] onOpenParams;
    private final PojoPathParam[] onCloseParams;
    private final PojoPathParam[] onErrorParams;
    private final List<MessageHandlerInfo> onMessage = new ArrayList<MessageHandlerInfo>();
    private final String wsPath;


    public PojoMethodMapping(Class<?> clazzPojo,
            List<Class<? extends Decoder>> decoderClazzes, String wsPath)
                    throws DeploymentException {

        this.wsPath = wsPath;

        List<DecoderEntry> decoders = Util.getDecoders(decoderClazzes);
        Method open = null;
        Method close = null;
        Method error = null;
        Method[] clazzPojoMethods = null;
        Class<?> currentClazz = clazzPojo;
        while (!currentClazz.equals(Object.class)) {
            Method[] currentClazzMethods = currentClazz.getDeclaredMethods();
            if (currentClazz == clazzPojo) {
                clazzPojoMethods = currentClazzMethods;
            }
            for (Method method : currentClazzMethods) {
                if (method.getAnnotation(OnOpen.class) != null) {
                    checkPublic(method);
                    if (open == null) {
                        open = method;
                    } else {
                        if (currentClazz == clazzPojo ||
                                !isMethodOverride(open, method)) {
                            // Duplicate annotation
                            throw new DeploymentException(sm.getString(
                                    "pojoMethodMapping.duplicateAnnotation",
                                    OnOpen.class, currentClazz));
                        }
                    }
                } else if (method.getAnnotation(OnClose.class) != null) {
                    checkPublic(method);
                    if (close == null) {
                        close = method;
                    } else {
                        if (currentClazz == clazzPojo ||
                                !isMethodOverride(close, method)) {
                            // Duplicate annotation
                            throw new DeploymentException(sm.getString(
                                    "pojoMethodMapping.duplicateAnnotation",
                                    OnClose.class, currentClazz));
                        }
                    }
                } else if (method.getAnnotation(OnError.class) != null) {
                    checkPublic(method);
                    if (error == null) {
                        error = method;
                    } else {
                        if (currentClazz == clazzPojo ||
                                !isMethodOverride(error, method)) {
                            // Duplicate annotation
                            throw new DeploymentException(sm.getString(
                                    "pojoMethodMapping.duplicateAnnotation",
                                    OnError.class, currentClazz));
                        }
                    }
                } else if (method.getAnnotation(OnMessage.class) != null) {
                    checkPublic(method);
                    MessageHandlerInfo messageHandler = new MessageHandlerInfo(method, decoders);
                    boolean found = false;
                    for (MessageHandlerInfo otherMessageHandler : onMessage) {
                        if (messageHandler.targetsSameWebSocketMessageType(otherMessageHandler)) {
                            found = true;
                            if (currentClazz == clazzPojo ||
                                !isMethodOverride(messageHandler.m, otherMessageHandler.m)) {
                                // Duplicate annotation
                                throw new DeploymentException(sm.getString(
                                        "pojoMethodMapping.duplicateAnnotation",
                                        OnMessage.class, currentClazz));
                            }
                        }
                    }
                    if (!found) {
                        onMessage.add(messageHandler);
                    }
                } else {
                    // Method not annotated
                }
            }
            currentClazz = currentClazz.getSuperclass();
        }
        // If the methods are not on clazzPojo and they are overridden
        // by a non annotated method in clazzPojo, they should be ignored
        if (open != null && open.getDeclaringClass() != clazzPojo) {
            if (isOverridenWithoutAnnotation(clazzPojoMethods, open, OnOpen.class)) {
                open = null;
            }
        }
        if (close != null && close.getDeclaringClass() != clazzPojo) {
            if (isOverridenWithoutAnnotation(clazzPojoMethods, close, OnClose.class)) {
                close = null;
            }
        }
        if (error != null && error.getDeclaringClass() != clazzPojo) {
            if (isOverridenWithoutAnnotation(clazzPojoMethods, error, OnError.class)) {
                error = null;
            }
        }
        List<MessageHandlerInfo> overriddenOnMessage = new ArrayList<MessageHandlerInfo>();
        for (MessageHandlerInfo messageHandler : onMessage) {
            if (messageHandler.m.getDeclaringClass() != clazzPojo
                    && isOverridenWithoutAnnotation(clazzPojoMethods, messageHandler.m, OnMessage.class)) {
                overriddenOnMessage.add(messageHandler);
            }
        }
        for (MessageHandlerInfo messageHandler : overriddenOnMessage) {
            onMessage.remove(messageHandler);
        }
        this.onOpen = open;
        this.onClose = close;
        this.onError = error;
        onOpenParams = getPathParams(onOpen, MethodType.ON_OPEN);
        onCloseParams = getPathParams(onClose, MethodType.ON_CLOSE);
        onErrorParams = getPathParams(onError, MethodType.ON_ERROR);
    }


    private void checkPublic(Method m) throws DeploymentException {
        if (!Modifier.isPublic(m.getModifiers())) {
            throw new DeploymentException(sm.getString(
                    "pojoMethodMapping.methodNotPublic", m.getName()));
        }
    }


    private boolean isMethodOverride(Method method1, Method method2) {
        return (method1.getName().equals(method2.getName())
                && method1.getReturnType().equals(method2.getReturnType())
                && Arrays.equals(method1.getParameterTypes(), method2.getParameterTypes()));
    }


    private boolean isOverridenWithoutAnnotation(Method[] methods,
            Method superclazzMethod, Class<? extends Annotation> annotation) {
        for (Method method : methods) {
            if (isMethodOverride(method, superclazzMethod)
                    && (method.getAnnotation(annotation) == null)) {
                return true;
            }
        }
        return false;
    }


    public String getWsPath() {
        return wsPath;
    }


    public Method getOnOpen() {
        return onOpen;
    }


    public Object[] getOnOpenArgs(Map<String,String> pathParameters,
            Session session, EndpointConfig config) throws DecodeException {
        return buildArgs(onOpenParams, pathParameters, session, config, null,
                null);
    }


    public Method getOnClose() {
        return onClose;
    }


    public Object[] getOnCloseArgs(Map<String,String> pathParameters,
            Session session, CloseReason closeReason) throws DecodeException {
        return buildArgs(onCloseParams, pathParameters, session, null, null,
                closeReason);
    }


    public Method getOnError() {
        return onError;
    }


    public Object[] getOnErrorArgs(Map<String,String> pathParameters,
            Session session, Throwable throwable) throws DecodeException {
        return buildArgs(onErrorParams, pathParameters, session, null,
                throwable, null);
    }


    public boolean hasMessageHandlers() {
        return !onMessage.isEmpty();
    }


    public Set<MessageHandler> getMessageHandlers(Object pojo,
            Map<String,String> pathParameters, Session session,
            EndpointConfig config) {
        Set<MessageHandler> result = new HashSet<MessageHandler>();
        for (MessageHandlerInfo messageMethod : onMessage) {
            result.addAll(messageMethod.getMessageHandlers(pojo, pathParameters,
                    session, config));
        }
        return result;
    }


    private static PojoPathParam[] getPathParams(Method m,
            MethodType methodType) throws DeploymentException {
        if (m == null) {
            return new PojoPathParam[0];
        }
        boolean foundThrowable = false;
        Class<?>[] types = m.getParameterTypes();
        Annotation[][] paramsAnnotations = m.getParameterAnnotations();
        PojoPathParam[] result = new PojoPathParam[types.length];
        for (int i = 0; i < types.length; i++) {
            Class<?> type = types[i];
            if (type.equals(Session.class)) {
                result[i] = new PojoPathParam(type, null);
            } else if (methodType == MethodType.ON_OPEN &&
                    type.equals(EndpointConfig.class)) {
                result[i] = new PojoPathParam(type, null);
            } else if (methodType == MethodType.ON_ERROR
                    && type.equals(Throwable.class)) {
                foundThrowable = true;
                result[i] = new PojoPathParam(type, null);
            } else if (methodType == MethodType.ON_CLOSE &&
                    type.equals(CloseReason.class)) {
                result[i] = new PojoPathParam(type, null);
            } else {
                Annotation[] paramAnnotations = paramsAnnotations[i];
                for (Annotation paramAnnotation : paramAnnotations) {
                    if (paramAnnotation.annotationType().equals(
                            PathParam.class)) {
                        // Check that the type is valid. "0" coerces to every
                        // valid type
                        try {
                            Util.coerceToType(type, "0");
                        } catch (IllegalArgumentException iae) {
                            throw new DeploymentException(sm.getString(
                                    "pojoMethodMapping.invalidPathParamType"),
                                    iae);
                        }
                        result[i] = new PojoPathParam(type,
                                ((PathParam) paramAnnotation).value());
                        break;
                    }
                }
                // Parameters without annotations are not permitted
                if (result[i] == null) {
                    throw new DeploymentException(sm.getString(
                            "pojoMethodMapping.paramWithoutAnnotation",
                            type, m.getName(), m.getClass().getName()));
                }
            }
        }
        if (methodType == MethodType.ON_ERROR && !foundThrowable) {
            throw new DeploymentException(sm.getString(
                    "pojoMethodMapping.onErrorNoThrowable",
                    m.getName(), m.getDeclaringClass().getName()));
        }
        return result;
    }


    private static Object[] buildArgs(PojoPathParam[] pathParams,
            Map<String,String> pathParameters, Session session,
            EndpointConfig config, Throwable throwable, CloseReason closeReason)
            throws DecodeException {
        Object[] result = new Object[pathParams.length];
        for (int i = 0; i < pathParams.length; i++) {
            Class<?> type = pathParams[i].getType();
            if (type.equals(Session.class)) {
                result[i] = session;
            } else if (type.equals(EndpointConfig.class)) {
                result[i] = config;
            } else if (type.equals(Throwable.class)) {
                result[i] = throwable;
            } else if (type.equals(CloseReason.class)) {
                result[i] = closeReason;
            } else {
                String name = pathParams[i].getName();
                String value = pathParameters.get(name);
                try {
                    result[i] = Util.coerceToType(type, value);
                } catch (Exception e) {
                    throw new DecodeException(value, sm.getString(
                            "pojoMethodMapping.decodePathParamFail",
                            value, type), e);
                }
            }
        }
        return result;
    }


    private static class MessageHandlerInfo {

        private final Method m;
        private int indexString = -1;
        private int indexByteArray = -1;
        private int indexByteBuffer = -1;
        private int indexPong = -1;
        private int indexBoolean = -1;
        private int indexSession = -1;
        private int indexInputStream = -1;
        private int indexReader = -1;
        private int indexPrimitive = -1;
        private Class<?> primitiveType = null;
        private Map<Integer,PojoPathParam> indexPathParams = new HashMap<Integer,PojoPathParam>();
        private int indexPayload = -1;
        private DecoderMatch decoderMatch = null;
        private long maxMessageSize = -1;

        public MessageHandlerInfo(Method m, List<DecoderEntry> decoderEntries) {
            this.m = m;

            Class<?>[] types = m.getParameterTypes();
            Annotation[][] paramsAnnotations = m.getParameterAnnotations();

            for (int i = 0; i < types.length; i++) {
                boolean paramFound = false;
                Annotation[] paramAnnotations = paramsAnnotations[i];
                for (Annotation paramAnnotation : paramAnnotations) {
                    if (paramAnnotation.annotationType().equals(
                            PathParam.class)) {
                        indexPathParams.put(
                                Integer.valueOf(i), new PojoPathParam(types[i],
                                        ((PathParam) paramAnnotation).value()));
                        paramFound = true;
                        break;
                    }
                }
                if (paramFound) {
                    continue;
                }
                if (String.class.isAssignableFrom(types[i])) {
                    if (indexString == -1) {
                        indexString = i;
                    } else {
                        throw new IllegalArgumentException(sm.getString(
                                "pojoMethodMapping.duplicateMessageParam",
                                m.getName(), m.getDeclaringClass().getName()));
                    }
                } else if (Reader.class.isAssignableFrom(types[i])) {
                    if (indexReader == -1) {
                        indexReader = i;
                    } else {
                        throw new IllegalArgumentException(sm.getString(
                                "pojoMethodMapping.duplicateMessageParam",
                                m.getName(), m.getDeclaringClass().getName()));
                    }
                } else if (boolean.class == types[i]) {
                    if (indexBoolean == -1) {
                        indexBoolean = i;
                    } else {
                        throw new IllegalArgumentException(sm.getString(
                                "pojoMethodMapping.duplicateLastParam",
                                m.getName(), m.getDeclaringClass().getName()));
                    }
                } else if (ByteBuffer.class.isAssignableFrom(types[i])) {
                    if (indexByteBuffer == -1) {
                        indexByteBuffer = i;
                    } else {
                        throw new IllegalArgumentException(sm.getString(
                                "pojoMethodMapping.duplicateMessageParam",
                                m.getName(), m.getDeclaringClass().getName()));
                    }
                } else if (byte[].class == types[i]) {
                    if (indexByteArray == -1) {
                        indexByteArray = i;
                    } else {
                        throw new IllegalArgumentException(sm.getString(
                                "pojoMethodMapping.duplicateMessageParam",
                                m.getName(), m.getDeclaringClass().getName()));
                    }
                } else if (InputStream.class.isAssignableFrom(types[i])) {
                    if (indexInputStream == -1) {
                        indexInputStream = i;
                    } else {
                        throw new IllegalArgumentException(sm.getString(
                                "pojoMethodMapping.duplicateMessageParam",
                                m.getName(), m.getDeclaringClass().getName()));
                    }
                } else if (Util.isPrimitive(types[i])) {
                    if (indexPrimitive == -1) {
                        indexPrimitive = i;
                        primitiveType = types[i];
                    } else {
                        throw new IllegalArgumentException(sm.getString(
                                "pojoMethodMapping.duplicateMessageParam",
                                m.getName(), m.getDeclaringClass().getName()));
                    }
                } else if (Session.class.isAssignableFrom(types[i])) {
                    if (indexSession == -1) {
                        indexSession = i;
                    } else {
                        throw new IllegalArgumentException(sm.getString(
                                "pojoMethodMapping.duplicateSessionParam",
                                m.getName(), m.getDeclaringClass().getName()));
                    }
                } else if (PongMessage.class.isAssignableFrom(types[i])) {
                    if (indexPong == -1) {
                        indexPong = i;
                    } else {
                        throw new IllegalArgumentException(sm.getString(
                                "pojoMethodMapping.duplicatePongMessageParam",
                                m.getName(), m.getDeclaringClass().getName()));
                    }
                } else {
                    if (decoderMatch != null && decoderMatch.hasMatches()) {
                        throw new IllegalArgumentException(sm.getString(
                                "pojoMethodMapping.duplicateMessageParam",
                                m.getName(), m.getDeclaringClass().getName()));
                    }
                    decoderMatch = new DecoderMatch(types[i], decoderEntries);

                    if (decoderMatch.hasMatches()) {
                        indexPayload = i;
                    }
                }
            }

            // Additional checks required
            if (indexString != -1) {
                if (indexPayload != -1) {
                    throw new IllegalArgumentException(sm.getString(
                            "pojoMethodMapping.duplicateMessageParam",
                            m.getName(), m.getDeclaringClass().getName()));
                } else {
                    indexPayload = indexString;
                }
            }
            if (indexReader != -1) {
                if (indexPayload != -1) {
                    throw new IllegalArgumentException(sm.getString(
                            "pojoMethodMapping.duplicateMessageParam",
                            m.getName(), m.getDeclaringClass().getName()));
                } else {
                    indexPayload = indexReader;
                }
            }
            if (indexByteArray != -1) {
                if (indexPayload != -1) {
                    throw new IllegalArgumentException(sm.getString(
                            "pojoMethodMapping.duplicateMessageParam",
                            m.getName(), m.getDeclaringClass().getName()));
                } else {
                    indexPayload = indexByteArray;
                }
            }
            if (indexByteBuffer != -1) {
                if (indexPayload != -1) {
                    throw new IllegalArgumentException(sm.getString(
                            "pojoMethodMapping.duplicateMessageParam",
                            m.getName(), m.getDeclaringClass().getName()));
                } else {
                    indexPayload = indexByteBuffer;
                }
            }
            if (indexInputStream != -1) {
                if (indexPayload != -1) {
                    throw new IllegalArgumentException(sm.getString(
                            "pojoMethodMapping.duplicateMessageParam",
                            m.getName(), m.getDeclaringClass().getName()));
                } else {
                    indexPayload = indexInputStream;
                }
            }
            if (indexPrimitive != -1) {
                if (indexPayload != -1) {
                    throw new IllegalArgumentException(sm.getString(
                            "pojoMethodMapping.duplicateMessageParam",
                            m.getName(), m.getDeclaringClass().getName()));
                } else {
                    indexPayload = indexPrimitive;
                }
            }
            if (indexPong != -1) {
                if (indexPayload != -1) {
                    throw new IllegalArgumentException(sm.getString(
                            "pojoMethodMapping.pongWithPayload",
                            m.getName(), m.getDeclaringClass().getName()));
                } else {
                    indexPayload = indexPong;
                }
            }
            if (indexPayload == -1 && indexPrimitive == -1 &&
                    indexBoolean != -1) {
                // The boolean we found is a payload, not a last flag
                indexPayload = indexBoolean;
                indexPrimitive = indexBoolean;
                primitiveType = Boolean.TYPE;
                indexBoolean = -1;
            }
            if (indexPayload == -1) {
                throw new IllegalArgumentException(sm.getString(
                        "pojoMethodMapping.noPayload",
                        m.getName(), m.getDeclaringClass().getName()));
            }
            if (indexPong != -1 && indexBoolean != -1) {
                throw new IllegalArgumentException(sm.getString(
                        "pojoMethodMapping.partialPong",
                        m.getName(), m.getDeclaringClass().getName()));
            }
            if(indexReader != -1 && indexBoolean != -1) {
                throw new IllegalArgumentException(sm.getString(
                        "pojoMethodMapping.partialReader",
                        m.getName(), m.getDeclaringClass().getName()));
            }
            if(indexInputStream != -1 && indexBoolean != -1) {
                throw new IllegalArgumentException(sm.getString(
                        "pojoMethodMapping.partialInputStream",
                        m.getName(), m.getDeclaringClass().getName()));
            }
            if (decoderMatch != null && decoderMatch.hasMatches() &&
                    indexBoolean != -1) {
                throw new IllegalArgumentException(sm.getString(
                        "pojoMethodMapping.partialObject",
                        m.getName(), m.getDeclaringClass().getName()));
            }

            maxMessageSize = m.getAnnotation(OnMessage.class).maxMessageSize();
        }


        public boolean targetsSameWebSocketMessageType(MessageHandlerInfo otherHandler) {
            if (otherHandler == null) {
                return false;
            }
            if (indexByteArray >= 0 && otherHandler.indexByteArray >= 0) {
                return true;
            }
            if (indexByteBuffer >= 0 && otherHandler.indexByteBuffer >= 0) {
                return true;
            }
            if (indexInputStream >= 0 && otherHandler.indexInputStream >= 0) {
                return true;
            }
            if (indexPong >= 0 && otherHandler.indexPong >= 0) {
                return true;
            }
            if (indexPrimitive >= 0 && otherHandler.indexPrimitive >= 0
                    && primitiveType == otherHandler.primitiveType) {
                return true;
            }
            if (indexReader >= 0 && otherHandler.indexReader >= 0) {
                return true;
            }
            if (indexString >= 0 && otherHandler.indexString >= 0) {
                return true;
            }
            if (decoderMatch != null && otherHandler.decoderMatch != null
                    && decoderMatch.getTarget().equals(otherHandler.decoderMatch.getTarget())) {
                return true;
            }
            return false;
        }


        public Set<MessageHandler> getMessageHandlers(Object pojo,
                Map<String,String> pathParameters, Session session,
                EndpointConfig config) {
            Object[] params = new Object[m.getParameterTypes().length];

            for (Map.Entry<Integer,PojoPathParam> entry :
                    indexPathParams.entrySet()) {
                PojoPathParam pathParam = entry.getValue();
                String valueString = pathParameters.get(pathParam.getName());
                Object value = null;
                try {
                    value = Util.coerceToType(pathParam.getType(), valueString);
                } catch (Exception e) {
                    DecodeException de =  new DecodeException(valueString,
                            sm.getString(
                                    "pojoMethodMapping.decodePathParamFail",
                                    valueString, pathParam.getType()), e);
                    params = new Object[] { de };
                }
                params[entry.getKey().intValue()] = value;
            }

            Set<MessageHandler> results = new HashSet<MessageHandler>(2);
            if (indexBoolean == -1) {
                // Basic
                if (indexString != -1 || indexPrimitive != -1) {
                    MessageHandler mh = new PojoMessageHandlerWholeText(pojo, m,
                            session, config, null, params, indexPayload, false,
                            indexSession, maxMessageSize);
                    results.add(mh);
                } else if (indexReader != -1) {
                    MessageHandler mh = new PojoMessageHandlerWholeText(pojo, m,
                            session, config, null, params, indexReader, true,
                            indexSession, maxMessageSize);
                    results.add(mh);
                } else if (indexByteArray != -1) {
                    MessageHandler mh = new PojoMessageHandlerWholeBinary(pojo,
                            m, session, config, null, params, indexByteArray,
                            true, indexSession, false, maxMessageSize);
                    results.add(mh);
                } else if (indexByteBuffer != -1) {
                    MessageHandler mh = new PojoMessageHandlerWholeBinary(pojo,
                            m, session, config, null, params, indexByteBuffer,
                            false, indexSession, false, maxMessageSize);
                    results.add(mh);
                } else if (indexInputStream != -1) {
                    MessageHandler mh = new PojoMessageHandlerWholeBinary(pojo,
                            m, session, config, null, params, indexInputStream,
                            true, indexSession, true, maxMessageSize);
                    results.add(mh);
                } else if (decoderMatch != null && decoderMatch.hasMatches()) {
                    if (decoderMatch.getBinaryDecoders().size() > 0) {
                        MessageHandler mh = new PojoMessageHandlerWholeBinary(
                                pojo, m, session, config,
                                decoderMatch.getBinaryDecoders(), params,
                                indexPayload, true, indexSession, true,
                                maxMessageSize);
                        results.add(mh);
                    }
                    if (decoderMatch.getTextDecoders().size() > 0) {
                        MessageHandler mh = new PojoMessageHandlerWholeText(
                                pojo, m, session, config,
                                decoderMatch.getTextDecoders(), params,
                                indexPayload, true, indexSession, maxMessageSize);
                        results.add(mh);
                    }
                } else {
                    MessageHandler mh = new PojoMessageHandlerWholePong(pojo, m,
                            session, params, indexPong, false, indexSession);
                    results.add(mh);
                }
            } else {
                // ASync
                if (indexString != -1) {
                    MessageHandler mh = new PojoMessageHandlerPartialText(pojo,
                            m, session, params, indexString, false,
                            indexBoolean, indexSession, maxMessageSize);
                    results.add(mh);
                } else if (indexByteArray != -1) {
                    MessageHandler mh = new PojoMessageHandlerPartialBinary(
                            pojo, m, session, params, indexByteArray, true,
                            indexBoolean, indexSession, maxMessageSize);
                    results.add(mh);
                } else {
                    MessageHandler mh = new PojoMessageHandlerPartialBinary(
                            pojo, m, session, params, indexByteBuffer, false,
                            indexBoolean, indexSession, maxMessageSize);
                    results.add(mh);
                }
            }
            return results;
        }
    }


    private static enum MethodType {
        ON_OPEN,
        ON_CLOSE,
        ON_ERROR
    }
}
