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
package javax.websocket;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Session extends Closeable {

    /**
     * Get the container that created this session.
     * @return the container that created this session.
     */
    WebSocketContainer getContainer();

    /**
     * Registers a {@link MessageHandler} for incoming messages. Only one
     * {@link MessageHandler} may be registered for each message type (text,
     * binary, pong). The message type will be derived at runtime from the
     * provided {@link MessageHandler} instance. It is not always possible to do
     * this so it is better to use
     * {@link #addMessageHandler(Class, javax.websocket.MessageHandler.Partial)}
     * or
     * {@link #addMessageHandler(Class, javax.websocket.MessageHandler.Whole)}.
     *
     * @param handler   The message handler for a incoming message
     *
     * @throws IllegalStateException  If a message handler has already been
     *                                registered for the associated message type
     */
    void addMessageHandler(MessageHandler handler) throws IllegalStateException;

    Set<MessageHandler> getMessageHandlers();

    void removeMessageHandler(MessageHandler listener);

    String getProtocolVersion();

    String getNegotiatedSubprotocol();

    List<Extension> getNegotiatedExtensions();

    boolean isSecure();

    boolean isOpen();

    /**
     * Get the idle timeout for this session.
     * @return The current idle timeout for this session in milliseconds. Zero
     *         or negative values indicate an infinite timeout.
     */
    long getMaxIdleTimeout();

    /**
     * Set the idle timeout for this session.
     * @param timeout The new idle timeout for this session in milliseconds.
     *                Zero or negative values indicate an infinite timeout.
     */
    void setMaxIdleTimeout(long timeout);

    /**
     * Set the current maximum buffer size for binary messages.
     * @param max The new maximum buffer size in bytes
     */
    void setMaxBinaryMessageBufferSize(int max);

    /**
     * Get the current maximum buffer size for binary messages.
     * @return The current maximum buffer size in bytes
     */
    int getMaxBinaryMessageBufferSize();

    /**
     * Set the maximum buffer size for text messages.
     * @param max The new maximum buffer size in characters.
     */
    void setMaxTextMessageBufferSize(int max);

    /**
     * Get the maximum buffer size for text messages.
     * @return The maximum buffer size in characters.
     */
    int getMaxTextMessageBufferSize();

    RemoteEndpoint.Async getAsyncRemote();

    RemoteEndpoint.Basic getBasicRemote();

    /**
     * Provides a unique identifier for the session. This identifier should not
     * be relied upon to be generated from a secure random source.
     * @return A unique identifier for the session.
     */
    String getId();

    /**
     * Close the connection to the remote end point using the code
     * {@link javax.websocket.CloseReason.CloseCodes#NORMAL_CLOSURE} and an
     * empty reason phrase.
     *
     * @throws IOException if an I/O error occurs while the WebSocket session is
     *                     being closed.
     */
    @Override
    void close() throws IOException;


    /**
     * Close the connection to the remote end point using the specified code
     * and reason phrase.
     * @param closeReason The reason the WebSocket session is being closed.
     *
     * @throws IOException if an I/O error occurs while the WebSocket session is
     *                     being closed.
     */
    void close(CloseReason closeReason) throws IOException;

    URI getRequestURI();

    Map<String, List<String>> getRequestParameterMap();

    String getQueryString();

    Map<String,String> getPathParameters();

    Map<String,Object> getUserProperties();

    Principal getUserPrincipal();

    /**
     * Obtain the set of open sessions associated with the same local endpoint
     * as this session.
     *
     * @return The set of currently open sessions for the local endpoint that
     * this session is associated with.
     */
    Set<Session> getOpenSessions();

    /**
     * Registers a {@link MessageHandler} for partial incoming messages. Only
     * one {@link MessageHandler} may be registered for each message type (text
     * or binary, pong messages are never presented as partial messages).
     *
     * @param <T>       The type of message that the given handler is intended
     *                  for
     * @param clazz     The Class that implements T
     * @param handler   The message handler for a incoming message
     *
     * @throws IllegalStateException  If a message handler has already been
     *                                registered for the associated message type
     *
     * @since WebSocket 1.1
     */
    <T> void addMessageHandler(Class<T> clazz, MessageHandler.Partial<T> handler)
            throws IllegalStateException;

    /**
     * Registers a {@link MessageHandler} for whole incoming messages. Only
     * one {@link MessageHandler} may be registered for each message type (text,
     * binary, pong).
     *
     * @param <T>       The type of message that the given handler is intended
     *                  for
     * @param clazz     The Class that implements T
     * @param handler   The message handler for a incoming message
     *
     * @throws IllegalStateException  If a message handler has already been
     *                                registered for the associated message type
     *
     * @since WebSocket 1.1
     */
    <T> void addMessageHandler(Class<T> clazz, MessageHandler.Whole<T> handler)
            throws IllegalStateException;
}
