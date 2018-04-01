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

package org.apache.tomcat.jni;

/** Address
 *
 * @author Mladen Turk
 */
public class Address {

    public static final String APR_ANYADDR = "0.0.0.0";
    /**
     * Fill the Sockaddr class from apr_sockaddr_t
     * @param info Sockaddr class to fill
     * @param sa Structure pointer
     */
    public static native boolean fill(Sockaddr info, long sa);

    /**
     * Create the Sockaddr object from apr_sockaddr_t
     * @param sa Structure pointer
     */
    public static native Sockaddr getInfo(long sa);

    /**
     * Create apr_sockaddr_t from hostname, address family, and port.
     * @param hostname The hostname or numeric address string to resolve/parse, or
     *               NULL to build an address that corresponds to 0.0.0.0 or ::
     * @param family The address family to use, or APR_UNSPEC if the system should
     *               decide.
     * @param port The port number.
     * @param flags Special processing flags:
     * <PRE>
     *       APR_IPV4_ADDR_OK          first query for IPv4 addresses; only look
     *                                 for IPv6 addresses if the first query failed;
     *                                 only valid if family is APR_UNSPEC and hostname
     *                                 isn't NULL; mutually exclusive with
     *                                 APR_IPV6_ADDR_OK
     *       APR_IPV6_ADDR_OK          first query for IPv6 addresses; only look
     *                                 for IPv4 addresses if the first query failed;
     *                                 only valid if family is APR_UNSPEC and hostname
     *                                 isn't NULL and APR_HAVE_IPV6; mutually exclusive
     *                                 with APR_IPV4_ADDR_OK
     * </PRE>
     * @param p The pool for the apr_sockaddr_t and associated storage.
     * @return The new apr_sockaddr_t.
     */
    public static native long info(String hostname, int family,
                                   int port, int flags, long p)
        throws Exception;
    /**
     * Look up the host name from an apr_sockaddr_t.
     * @param sa The apr_sockaddr_t.
     * @param flags Special processing flags.
     * @return The hostname.
     */
    public static native String getnameinfo(long sa, int flags);

    /**
     * Return the IP address (in numeric address string format) in
     * an APR socket address.  APR will allocate storage for the IP address
     * string from the pool of the apr_sockaddr_t.
     * @param sa The socket address to reference.
     * @return The IP address.
     */
    public static native String getip(long sa);

    /**
     * Given an apr_sockaddr_t and a service name, set the port for the service
     * @param sockaddr The apr_sockaddr_t that will have its port set
     * @param servname The name of the service you wish to use
     * @return APR status code.
     */
    public static native int getservbyname(long sockaddr, String servname);

    /**
     * Return an apr_sockaddr_t from an apr_socket_t
     * @param which Which interface do we want the apr_sockaddr_t for?
     * @param sock The socket to use
     * @return The returned apr_sockaddr_t.
     */
    public static native long get(int which, long sock)
        throws Exception;

    /**
     * See if the IP addresses in two APR socket addresses are
     * equivalent.  Appropriate logic is present for comparing
     * IPv4-mapped IPv6 addresses with IPv4 addresses.
     *
     * @param a One of the APR socket addresses.
     * @param b The other APR socket address.
     * The return value will be True if the addresses
     * are equivalent.
     */
    public static native boolean equal(long a, long b);

}
