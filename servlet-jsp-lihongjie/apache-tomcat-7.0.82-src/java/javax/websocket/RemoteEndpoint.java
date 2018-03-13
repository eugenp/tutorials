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

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;


public interface RemoteEndpoint {

    interface Async extends RemoteEndpoint {

        /**
         * Obtain the timeout (in milliseconds) for sending a message
         * asynchronously. The default value is determined by
         * {@link WebSocketContainer#getDefaultAsyncSendTimeout()}.
         * @return  The current send timeout in milliseconds. A non-positive
         *          value means an infinite timeout.
         */
        long getSendTimeout();

        /**
         * Set the timeout (in milliseconds) for sending a message
         * asynchronously. The default value is determined by
         * {@link WebSocketContainer#getDefaultAsyncSendTimeout()}.
         * @param timeout   The new timeout for sending messages asynchronously
         *                  in milliseconds. A non-positive value means an
         *                  infinite timeout.
         */
        void setSendTimeout(long timeout);

        /**
         * Send the message asynchronously, using the SendHandler to signal to the
         * client when the message has been sent.
         * @param text          The text message to send
         * @param completion    Used to signal to the client when the message has
         *                      been sent
         */
        void sendText(String text, SendHandler completion);

        /**
         * Send the message asynchronously, using the Future to signal to the
         * client when the message has been sent.
         * @param text          The text message to send
         * @return A Future that signals when the message has been sent.
         */
        Future<Void> sendText(String text);

        /**
         * Send the message asynchronously, using the Future to signal to the client
         * when the message has been sent.
         * @param data          The text message to send
         * @return A Future that signals when the message has been sent.
         * @throws IllegalArgumentException if {@code data} is {@code null}.
         */
        Future<Void> sendBinary(ByteBuffer data);

        /**
         * Send the message asynchronously, using the SendHandler to signal to the
         * client when the message has been sent.
         * @param data          The text message to send
         * @param completion    Used to signal to the client when the message has
         *                      been sent
         * @throws IllegalArgumentException if {@code data} or {@code completion}
         *                      is {@code null}.
         */
        void sendBinary(ByteBuffer data, SendHandler completion);

        /**
         * Encodes object as a message and sends it asynchronously, using the
         * Future to signal to the client when the message has been sent.
         * @param obj           The object to be sent.
         * @return A Future that signals when the message has been sent.
         * @throws IllegalArgumentException if {@code obj} is {@code null}.
         */
        Future<Void> sendObject(Object obj);

        /**
         * Encodes object as a message and sends it asynchronously, using the
         * SendHandler to signal to the client when the message has been sent.
         * @param obj           The object to be sent.
         * @param completion    Used to signal to the client when the message has
         *                      been sent
         * @throws IllegalArgumentException if {@code obj} or
         *                      {@code completion} is {@code null}.
         */
        void sendObject(Object obj, SendHandler completion);

    }

    interface Basic extends RemoteEndpoint {

        /**
         * Send the message, blocking until the message is sent.
         * @param text  The text message to send.
         * @throws IllegalArgumentException if {@code text} is {@code null}.
         * @throws IOException if an I/O error occurs during the sending of the
         *                     message.
         */
        void sendText(String text) throws IOException;

        /**
         * Send the message, blocking until the message is sent.
         * @param data  The binary message to send
         * @throws IllegalArgumentException if {@code data} is {@code null}.
         * @throws IOException if an I/O error occurs during the sending of the
         *                     message.
         */
        void sendBinary(ByteBuffer data) throws IOException;

        /**
         * Sends part of a text message to the remote endpoint. Once the first part
         * of a message has been sent, no other text or binary messages may be sent
         * until all remaining parts of this message have been sent.
         *
         * @param fragment  The partial message to send
         * @param isLast    <code>true</code> if this is the last part of the
         *                  message, otherwise <code>false</code>
         * @throws IllegalArgumentException if {@code fragment} is {@code null}.
         * @throws IOException if an I/O error occurs during the sending of the
         *                     message.
         */
        void sendText(String fragment, boolean isLast) throws IOException;

        /**
         * Sends part of a binary message to the remote endpoint. Once the first
         * part of a message has been sent, no other text or binary messages may be
         * sent until all remaining parts of this message have been sent.
         *
         * @param partialByte   The partial message to send
         * @param isLast        <code>true</code> if this is the last part of the
         *                      message, otherwise <code>false</code>
         * @throws IllegalArgumentException if {@code partialByte} is
         *                     {@code null}.
         * @throws IOException if an I/O error occurs during the sending of the
         *                     message.
         */
        void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException;

        OutputStream getSendStream() throws IOException;

        Writer getSendWriter() throws IOException;

        /**
         * Encodes object as a message and sends it to the remote endpoint.
         * @param data  The object to be sent.
         * @throws EncodeException if there was a problem encoding the
         *                     {@code data} object as a websocket message.
         * @throws IllegalArgumentException if {@code data} is {@code null}.
         * @throws IOException if an I/O error occurs during the sending of the
         *                     message.
         */
        void sendObject(Object data) throws IOException, EncodeException;

    }
    /**
     * Enable or disable the batching of outgoing messages for this endpoint. If
     * batching is disabled when it was previously enabled then this method will
     * block until any currently batched messages have been written.
     *
     * @param batchingAllowed   New setting
     * @throws IOException      If changing the value resulted in a call to
     *                          {@link #flushBatch()} and that call threw an
     *                          {@link IOException}.
     */
    void setBatchingAllowed(boolean batchingAllowed) throws IOException;

    /**
     * Obtains the current batching status of the endpoint.
     *
     * @return <code>true</code> if batching is enabled, otherwise
     *         <code>false</code>.
     */
    boolean getBatchingAllowed();

    /**
     * Flush any currently batched messages to the remote endpoint. This method
     * will block until the flush completes.
     *
     * @throws IOException If an I/O error occurs while flushing
     */
    void flushBatch() throws IOException;

    /**
     * Send a ping message blocking until the message has been sent. Note that
     * if a message is in the process of being sent asynchronously, this method
     * will block until that message and this ping has been sent.
     *
     * @param applicationData   The payload for the ping message
     *
     * @throws IOException If an I/O error occurs while sending the ping
     * @throws IllegalArgumentException if the applicationData is too large for
     *         a control message (max 125 bytes)
     */
    void sendPing(ByteBuffer applicationData)
            throws IOException, IllegalArgumentException;

    /**
     * Send a pong message blocking until the message has been sent. Note that
     * if a message is in the process of being sent asynchronously, this method
     * will block until that message and this pong has been sent.
     *
     * @param applicationData   The payload for the pong message
     *
     * @throws IOException If an I/O error occurs while sending the pong
     * @throws IllegalArgumentException if the applicationData is too large for
     *         a control message (max 125 bytes)
     */
    void sendPong(ByteBuffer applicationData)
            throws IOException, IllegalArgumentException;
}

