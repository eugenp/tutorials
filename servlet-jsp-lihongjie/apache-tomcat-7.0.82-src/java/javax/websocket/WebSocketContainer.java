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
import java.net.URI;
import java.util.Set;

public interface WebSocketContainer {

    /**
     * Get the default timeout for sending a message asynchronously.
     * @return The current default timeout in milliseconds. A non-positive value
     *         means an infinite timeout.
     */
    long getDefaultAsyncSendTimeout();

    /**
     * Set the default timeout for sending a message asynchronously.
     * @param timeout The new default timeout in milliseconds. A non-positive
     *                value means an infinite timeout.
     */
    void setAsyncSendTimeout(long timeout);

    Session connectToServer(Object endpoint, URI path)
            throws DeploymentException, IOException;

    Session connectToServer(Class<?> annotatedEndpointClass, URI path)
            throws DeploymentException, IOException;

    /**
     * Creates a new connection to the WebSocket.
     *
     * @param endpoint
     *            The endpoint instance that will handle responses from the
     *            server
     * @param clientEndpointConfiguration
     *            Used to configure the new connection
     * @param path
     *            The full URL of the WebSocket endpoint to connect to
     *
     * @return The WebSocket session for the connection
     *
     * @throws DeploymentException  If the connection can not be established
     * @throws IOException If an I/O occurred while trying to establish the
     *                     connection
     */
    Session connectToServer(Endpoint endpoint,
            ClientEndpointConfig clientEndpointConfiguration, URI path)
            throws DeploymentException, IOException;

    /**
     * Creates a new connection to the WebSocket.
     *
     * @param endpoint
     *            An instance of this class will be created to handle responses
     *            from the server
     * @param clientEndpointConfiguration
     *            Used to configure the new connection
     * @param path
     *            The full URL of the WebSocket endpoint to connect to
     *
     * @return The WebSocket session for the connection
     *
     * @throws DeploymentException  If the connection can not be established
     * @throws IOException If an I/O occurred while trying to establish the
     *                     connection
     */
    Session connectToServer(Class<? extends Endpoint> endpoint,
            ClientEndpointConfig clientEndpointConfiguration, URI path)
            throws DeploymentException, IOException;

    /**
     * Get the current default session idle timeout.
     * @return The current default session idle timeout in milliseconds. Zero or
     *         negative values indicate an infinite timeout.
     */
    long getDefaultMaxSessionIdleTimeout();

    /**
     * Set the default session idle timeout.
     * @param timeout The new default session idle timeout in milliseconds. Zero
     *                or negative values indicate an infinite timeout.
     */
    void setDefaultMaxSessionIdleTimeout(long timeout);

    /**
     * Get the default maximum buffer size for binary messages.
     * @return The current default maximum buffer size in bytes
     */
    int getDefaultMaxBinaryMessageBufferSize();

    /**
     * Set the default maximum buffer size for binary messages.
     * @param max The new default maximum buffer size in bytes
     */
    void setDefaultMaxBinaryMessageBufferSize(int max);

    /**
     * Get the default maximum buffer size for text messages.
     * @return The current default maximum buffer size in characters
     */
    int getDefaultMaxTextMessageBufferSize();

    /**
     * Set the default maximum buffer size for text messages.
     * @param max The new default maximum buffer size in characters
     */
    void setDefaultMaxTextMessageBufferSize(int max);

    /**
     * Get the installed extensions.
     * @return The set of extensions that are supported by this WebSocket
     *         implementation.
     */
    Set<Extension> getInstalledExtensions();
}
