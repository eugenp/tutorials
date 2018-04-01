/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tomcat.util.net;

import java.io.File;
import java.net.InetAddress;
import java.net.ServerSocket;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jni.Address;
import org.apache.tomcat.jni.Error;
import org.apache.tomcat.jni.Library;
import org.apache.tomcat.jni.OS;
import org.apache.tomcat.jni.Pool;
import org.apache.tomcat.jni.Socket;

/**
 * Test case for the Endpoint implementations. The testing framework will ensure
 * that each implementation is tested.
 */
public class TestXxxEndpoint extends TomcatBaseTest {

    private static Log log = LogFactory.getLog(TestXxxEndpoint.class);

    private long createAprPool() {

        // Create the pool for the server socket
        try {
            return Pool.create(0);
        } catch (UnsatisfiedLinkError e) {
            log.error("Could not create socket pool", e);
            return 0;
        }
    }

    private long createAprSocket(int port, long pool)
                 throws Exception {
        /**
         * Server socket "pointer".
         */
        long serverSock = 0;

        String address = InetAddress.getByName("localhost").getHostAddress();

        // Create the APR address that will be bound
        int family = Socket.APR_INET;
        if (Library.APR_HAVE_IPV6) {
            if (!OS.IS_BSD && !OS.IS_WIN32 && !OS.IS_WIN64)
                family = Socket.APR_UNSPEC;
         }

        long inetAddress = 0;
        try {
            inetAddress = Address.info(address, family,
                                       port, 0, pool);
            // Create the APR server socket
            serverSock = Socket.create(Address.getInfo(inetAddress).family,
                                       Socket.SOCK_STREAM,
                                       Socket.APR_PROTO_TCP, pool);
        } catch (Exception ex) {
            log.error("Could not create socket for address '" + address + "'");
            return 0;
        }

        if (OS.IS_UNIX) {
            Socket.optSet(serverSock, Socket.APR_SO_REUSEADDR, 1);
        }
        // Deal with the firewalls that tend to drop the inactive sockets
        Socket.optSet(serverSock, Socket.APR_SO_KEEPALIVE, 1);
        // Bind the server socket
        int ret = Socket.bind(serverSock, inetAddress);
        if (ret != 0) {
            log.error("Could not bind: " + Error.strerror(ret));
            throw (new Exception(Error.strerror(ret)));
        }
        return serverSock;
    }

    private void destroyAprSocket(long serverSock, long pool) {
        if (serverSock != 0) {
            Socket.shutdown(serverSock, Socket.APR_SHUTDOWN_READWRITE);
            Socket.close(serverSock);
            Socket.destroy(serverSock);
        }

        if (pool != 0) {
            Pool.destroy(pool);
            pool = 0;
        }
    }

    @Test
    public void testStartStopBindOnInit() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        File appDir = new File(getBuildDirectory(), "webapps/examples");
        tomcat.addWebapp(null, "/examples", appDir.getAbsolutePath());

        tomcat.start();

        int port = getPort();

        tomcat.getConnector().stop();
        Exception e = null;
        ServerSocket s = null;
        long pool = 0;
        long nativeSocket = 0;
        boolean isApr = tomcat.getConnector().getProtocolHandlerClassName().contains("Apr");
        try {
            // This should throw an Exception
            if (isApr) {
                pool = createAprPool();
                assertTrue(pool != 0);
                nativeSocket = createAprSocket(port, pool);
                assertTrue(nativeSocket != 0);
            } else {
                s = new ServerSocket(port, 100,
                        InetAddress.getByName("localhost"));
            }
        } catch (Exception e1) {
            e = e1;
        } finally {
            try {
                if (isApr) {
                    destroyAprSocket(nativeSocket, pool);
                } else if (s != null) {
                    s.close();
                }
            } catch (Exception e2) { /* Ignore */ }
        }
        if (e != null) {
            log.info("Exception was", e);
        }
        assertNotNull(e);
        tomcat.getConnector().start();
    }

    @Test
    public void testStartStopBindOnStart() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        Connector c = tomcat.getConnector();
        c.setProperty("bindOnInit", "false");

        File appDir = new File(getBuildDirectory(), "webapps/examples");
        tomcat.addWebapp(null, "/examples", appDir.getAbsolutePath());


        tomcat.start();
        int port = getPort();

        tomcat.getConnector().stop();
        Exception e = null;
        ServerSocket s = null;
        long pool = 0;
        long nativeSocket = 0;
        boolean isApr = tomcat.getConnector().getProtocolHandlerClassName().contains("Apr");
        try {
            // This should not throw an Exception
            if (isApr) {
                pool = createAprPool();
                assertTrue(pool != 0);
                nativeSocket = createAprSocket(port, pool);
                assertTrue(nativeSocket != 0);
            } else {
                s = new ServerSocket(port, 100,
                        InetAddress.getByName("localhost"));
            }
        } catch (Exception e1) {
            e = e1;
        } finally {
            try {
                if (isApr) {
                    destroyAprSocket(nativeSocket, pool);
                } else if (s != null) {
                    s.close();
                }
            } catch (Exception e2) { /* Ignore */ }
        }
        assertNull(e);
        tomcat.getConnector().start();
    }
}
