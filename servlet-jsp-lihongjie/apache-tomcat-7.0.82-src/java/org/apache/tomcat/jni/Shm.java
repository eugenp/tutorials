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

import java.nio.ByteBuffer;

/** Shm
 *
 * @author Mladen Turk
 */
public class Shm {

    /**
     * Create and make accessible a shared memory segment.
     * <br>
     * A note about Anonymous vs. Named shared memory segments:<br>
     *         Not all platforms support anonymous shared memory segments, but in
     *         some cases it is preferred over other types of shared memory
     *         implementations. Passing a NULL 'file' parameter to this function
     *         will cause the subsystem to use anonymous shared memory segments.
     *         If such a system is not available, APR_ENOTIMPL is returned.
     * <br>
     * A note about allocation sizes:<br>
     *         On some platforms it is necessary to store some metainformation
     *         about the segment within the actual segment. In order to supply
     *         the caller with the requested size it may be necessary for the
     *         implementation to request a slightly greater segment length
     *         from the subsystem. In all cases, the apr_shm_baseaddr_get()
     *         function will return the first usable byte of memory.
     * @param reqsize The desired size of the segment.
     * @param filename The file to use for shared memory on platforms that
     *        require it.
     * @param pool the pool from which to allocate the shared memory
     *        structure.
     * @return The created shared memory structure.
     *
     */
    public static native long create(long reqsize, String filename, long pool)
        throws Error;

    /**
     * Remove shared memory segment associated with a filename.
     * <br>
     * This function is only supported on platforms which support
     * name-based shared memory segments, and will return APR_ENOTIMPL on
     * platforms without such support.
     * @param filename The filename associated with shared-memory segment which
     *        needs to be removed
     * @param pool The pool used for file operations
     */
    public static native int remove(String filename, long pool);

    /**
     * Destroy a shared memory segment and associated memory.
     * @param m The shared memory segment structure to destroy.
     */
    public static native int destroy(long m);

    /**
     * Attach to a shared memory segment that was created
     * by another process.
     * @param filename The file used to create the original segment.
     *        (This MUST match the original filename.)
     * @param pool the pool from which to allocate the shared memory
     *        structure for this process.
     * @return The created shared memory structure.
     */
    public static native long attach(String filename, long pool)
        throws Error;

    /**
     * Detach from a shared memory segment without destroying it.
     * @param m The shared memory structure representing the segment
     *        to detach from.
     */
    public static native int detach(long m);

    /**
     * Retrieve the base address of the shared memory segment.
     * NOTE: This address is only usable within the callers address
     * space, since this API does not guarantee that other attaching
     * processes will maintain the same address mapping.
     * @param m The shared memory segment from which to retrieve
     *        the base address.
     * @return address, aligned by APR_ALIGN_DEFAULT.
     */
    public static native long baseaddr(long m);

    /**
     * Retrieve the length of a shared memory segment in bytes.
     * @param m The shared memory segment from which to retrieve
     *        the segment length.
     */
    public static native long size(long m);

    /**
     * Retrieve new ByteBuffer base address of the shared memory segment.
     * NOTE: This address is only usable within the callers address
     * space, since this API does not guarantee that other attaching
     * processes will maintain the same address mapping.
     * @param m The shared memory segment from which to retrieve
     *        the base address.
     * @return address, aligned by APR_ALIGN_DEFAULT.
     */
    public static native ByteBuffer buffer(long m);

}
