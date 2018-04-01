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
 * The common interface through which the {@link JIoEndpoint} interacts with
 * both non-SSL and SSL sockets.
 */
public interface ServerSocketFactory {

    /**
     * Returns a server socket which uses all network interfaces on the host,
     * and is bound to a the specified port. The socket is configured with the
     * socket options (such as accept timeout) given to this factory.
     *
     * @param port
     *            the port to listen to
     * @exception IOException
     *                for networking errors
     * @exception InstantiationException
     *                for construction errors
     */
    ServerSocket createSocket(int port) throws IOException,
            InstantiationException;

    /**
     * Returns a server socket which uses all network interfaces on the host, is
     * bound to a the specified port, and uses the specified connection backlog.
     * The socket is configured with the socket options (such as accept timeout)
     * given to this factory.
     *
     * @param port
     *            the port to listen to
     * @param backlog
     *            how many connections are queued
     * @exception IOException
     *                for networking errors
     * @exception InstantiationException
     *                for construction errors
     */
    ServerSocket createSocket(int port, int backlog) throws IOException,
            InstantiationException;

    /**
     * Returns a server socket which uses only the specified network interface
     * on the local host, is bound to a the specified port, and uses the
     * specified connection backlog. The socket is configured with the socket
     * options (such as accept timeout) given to this factory.
     *
     * @param port
     *            the port to listen to
     * @param backlog
     *            how many connections are queued
     * @param ifAddress
     *            the network interface address to use
     * @exception IOException
     *                for networking errors
     * @exception InstantiationException
     *                for construction errors
     */
    ServerSocket createSocket(int port, int backlog, InetAddress ifAddress)
            throws IOException, InstantiationException;

    /**
     * Wrapper function for accept(). This allows us to trap and translate
     * exceptions if necessary.
     *
     * @exception IOException
     */
    Socket acceptSocket(ServerSocket socket) throws IOException;

    /**
     * Triggers the SSL handshake. This will be a no-op for non-SSL sockets.
     *
     * @exception IOException
     */
    void handshake(Socket sock) throws IOException;
}
