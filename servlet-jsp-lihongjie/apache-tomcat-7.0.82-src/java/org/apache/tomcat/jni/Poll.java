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

/** Poll
 *
 * @author Mladen Turk
 */

public class Poll {

    /**
     * Poll return values
     */
    /** Can read without blocking */
    public static final int APR_POLLIN   = 0x001;
    /** Priority data available */
    public static final int APR_POLLPRI  = 0x002;
    /** Can write without blocking */
    public static final int APR_POLLOUT  = 0x004;
    /** Pending error */
    public static final int APR_POLLERR  = 0x010;
    /** Hangup occurred */
    public static final int APR_POLLHUP  = 0x020;
    /** Descriptor invalid */
    public static final int APR_POLLNVAL = 0x040;

    /**
     * Pollset Flags
     */
    /** Adding or Removing a Descriptor is thread safe */
    public static final int APR_POLLSET_THREADSAFE = 0x001;


    /** Used in apr_pollfd_t to determine what the apr_descriptor is
     * apr_datatype_e enum
     */
    public static final int APR_NO_DESC       = 0; /** nothing here */
    public static final int APR_POLL_SOCKET   = 1; /** descriptor refers to a socket */
    public static final int APR_POLL_FILE     = 2; /** descriptor refers to a file */
    public static final int APR_POLL_LASTDESC = 3; /** descriptor is the last one in the list */

    /**
     * Setup a pollset object.
     * If flags equals APR_POLLSET_THREADSAFE, then a pollset is
     * created on which it is safe to make concurrent calls to
     * apr_pollset_add(), apr_pollset_remove() and apr_pollset_poll() from
     * separate threads.  This feature is only supported on some
     * platforms; the apr_pollset_create() call will fail with
     * APR_ENOTIMPL on platforms where it is not supported.
     * @param size The maximum number of descriptors that this pollset can hold
     * @param p The pool from which to allocate the pollset
     * @param flags Optional flags to modify the operation of the pollset.
     * @param ttl Maximum time to live for a particular socket.
     * @return  The pointer in which to return the newly created object
     */
    public static native long create(int size, long p, int flags, long ttl)
        throws Error;
    /**
     * Destroy a pollset object
     * @param pollset The pollset to destroy
     */
    public static native int destroy(long pollset);

    /**
     * Add a socket to a pollset with the default timeout.
     * @param pollset The pollset to which to add the socket
     * @param sock The sockets to add
     * @param reqevents requested events
     */
    public static native int add(long pollset, long sock,
                                 int reqevents);

    /**
     * Add a socket to a pollset with a specific timeout.
     * @param pollset The pollset to which to add the socket
     * @param sock The sockets to add
     * @param reqevents requested events
     * @param timeout requested timeout in microseconds (-1 for infinite)
     */
    public static native int addWithTimeout(long pollset, long sock,
                                            int reqevents, long timeout);

    /**
     * Remove a descriptor from a pollset
     * @param pollset The pollset from which to remove the descriptor
     * @param sock The socket to remove
     */
    public static native int remove(long pollset, long sock);

    /**
     * Block for activity on the descriptor(s) in a pollset
     * @param pollset The pollset to use
     * @param timeout Timeout in microseconds
     * @param descriptors Array of signaled descriptors (output parameter)
     *        The descriptor array must be two times the size of pollset.
     *        and are populated as follows:
     * <PRE>
     * descriptors[2n + 0] -&gt; returned events
     * descriptors[2n + 1] -&gt; socket
     * </PRE>
     * @param remove Remove signaled descriptors from pollset
     * @return Number of signaled descriptors (output parameter)
     *         or negative APR error code.
     */
    public static native int poll(long pollset, long timeout,
                                  long [] descriptors, boolean remove);

    /**
     * Maintain on the descriptor(s) in a pollset
     * @param pollset The pollset to use
     * @param descriptors Array of signaled descriptors (output parameter)
     *        The descriptor array must be the size of pollset.
     *        and are populated as follows:
     * <PRE>
     * descriptors[n] -&gt; socket
     * </PRE>
     * @param remove Remove signaled descriptors from pollset
     * @return Number of signaled descriptors (output parameter)
     *         or negative APR error code.
     */
    public static native int maintain(long pollset, long [] descriptors,
                                      boolean remove);

    /**
     * Set the socket time to live.
     * @param pollset The pollset to use
     * @param ttl Timeout in microseconds
     */
    public static native void setTtl(long pollset, long ttl);

    /**
     * Get the socket time to live.
     * @param pollset The pollset to use
     * @return Timeout in microseconds
     */
    public static native long getTtl(long pollset);

    /**
     * Return all descriptor(s) in a pollset
     * @param pollset The pollset to use
     * @param descriptors Array of descriptors (output parameter)
     *        The descriptor array must be two times the size of pollset.
     *        and are populated as follows:
     * <PRE>
     * descriptors[2n + 0] -&gt; returned events
     * descriptors[2n + 1] -&gt; socket
     * </PRE>
     * @return Number of descriptors (output parameter) in the Poll
     *         or negative APR error code.
     */
    public static native int pollset(long pollset, long [] descriptors);
}
