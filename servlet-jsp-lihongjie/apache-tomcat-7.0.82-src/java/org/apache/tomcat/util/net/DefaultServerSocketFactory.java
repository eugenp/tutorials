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

package org.apache.tomcat.util.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Default server socket factory. Doesn't do much except give us
 * plain old server sockets.
 *
 * @author db@eng.sun.com
 * @author Harish Prabandham
 */
public class DefaultServerSocketFactory implements ServerSocketFactory {

    /**
     *
     * @param endpoint  Unused in this implementation.
     */
    public DefaultServerSocketFactory(AbstractEndpoint<?> endpoint) {
    }

    @Override
    public ServerSocket createSocket (int port) throws IOException {
        return  new ServerSocket (port);
    }

    @Override
    public ServerSocket createSocket (int port, int backlog)
            throws IOException {
        return new ServerSocket (port, backlog);
    }

    @Override
    public ServerSocket createSocket (int port, int backlog,
            InetAddress ifAddress) throws IOException {
        return new ServerSocket (port, backlog, ifAddress);
    }

    @Override
    public Socket acceptSocket(ServerSocket socket) throws IOException {
        return socket.accept();
    }

    @Override
    public void handshake(Socket sock) throws IOException {
        // NOOP
    }
}
