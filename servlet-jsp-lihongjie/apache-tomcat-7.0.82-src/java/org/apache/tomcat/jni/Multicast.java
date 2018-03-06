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

/** Multicast
 *
 * @author Mladen Turk
 */
public class Multicast {

    /**
     * Join a Multicast Group
     * @param sock The socket to join a multicast group
     * @param join The address of the multicast group to join
     * @param iface Address of the interface to use.  If NULL is passed, the
     *              default multicast interface will be used. (OS Dependent)
     * @param source Source Address to accept transmissions from (non-NULL
     *               implies Source-Specific Multicast)
     */
    public static native int join(long sock, long join,
                                  long iface, long source);

    /**
     * Leave a Multicast Group.  All arguments must be the same as
     * apr_mcast_join.
     * @param sock The socket to leave a multicast group
     * @param addr The address of the multicast group to leave
     * @param iface Address of the interface to use.  If NULL is passed, the
     *              default multicast interface will be used. (OS Dependent)
     * @param source Source Address to accept transmissions from (non-NULL
     *               implies Source-Specific Multicast)
     */
    public static native int leave(long sock, long addr,
                                   long iface, long source);

    /**
     * Set the Multicast Time to Live (ttl) for a multicast transmission.
     * @param sock The socket to set the multicast ttl
     * @param ttl Time to live to Assign. 0-255, default=1
     * <br><b>Remark :</b> If the TTL is 0, packets will only be seen
     * by sockets on the local machine,
     * and only when multicast loopback is enabled.
     */
    public static native int hops(long sock, int ttl);

    /**
     * Toggle IP Multicast Loopback
     * @param sock The socket to set multicast loopback
     * @param opt false=disable, true=enable
     */
    public static native int loopback(long sock, boolean opt);


    /**
     * Set the Interface to be used for outgoing Multicast Transmissions.
     * @param sock The socket to set the multicast interface on
     * @param iface Address of the interface to use for Multicast
     */
    public static native int ointerface(long sock, long iface);

}
