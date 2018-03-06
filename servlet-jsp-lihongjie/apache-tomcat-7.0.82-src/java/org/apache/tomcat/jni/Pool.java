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

/** Pool
 *
 * @author Mladen Turk
 */
public class Pool {

    /**
     * Create a new pool.
     * @param parent The parent pool.  If this is 0, the new pool is a root
     * pool.  If it is non-zero, the new pool will inherit all
     * of its parent pool's attributes, except the apr_pool_t will
     * be a sub-pool.
     * @return The pool we have just created.
    */
    public static native long create(long parent);

    /**
     * Clear all memory in the pool and run all the cleanups. This also destroys all
     * subpools.
     * @param pool The pool to clear
     * This does not actually free the memory, it just allows the pool
     *         to re-use this memory for the next allocation.
     */
    public static native void clear(long pool);

    /**
     * Destroy the pool. This takes similar action as apr_pool_clear() and then
     * frees all the memory.
     * This will actually free the memory
     * @param pool The pool to destroy
     */
    public static native void destroy(long pool);

    /**
     * Get the parent pool of the specified pool.
     * @param pool The pool for retrieving the parent pool.
     * @return The parent of the given pool.
     */
    public static native long parentGet(long pool);

    /**
     * Determine if pool a is an ancestor of pool b
     * @param a The pool to search
     * @param b The pool to search for
     * @return True if a is an ancestor of b, NULL is considered an ancestor
     * of all pools.
     */
    public static native boolean isAncestor(long a, long b);


    /*
     * Cleanup
     *
     * Cleanups are performed in the reverse order they were registered.  That is:
     * Last In, First Out.  A cleanup function can safely allocate memory from
     * the pool that is being cleaned up. It can also safely register additional
     * cleanups which will be run LIFO, directly after the current cleanup
     * terminates.  Cleanups have to take caution in calling functions that
     * create subpools. Subpools, created during cleanup will NOT automatically
     * be cleaned up.  In other words, cleanups are to clean up after themselves.
     */

    /**
     * Register a function to be called when a pool is cleared or destroyed
     * @param pool The pool register the cleanup with
     * @param o The object to call when the pool is cleared
     *                      or destroyed
     * @return The cleanup handler.
     */
    public static native long cleanupRegister(long pool, Object o);

    /**
     * Remove a previously registered cleanup function
     * @param pool The pool remove the cleanup from
     * @param data The cleanup handler to remove from cleanup
     */
    public static native void cleanupKill(long pool, long data);

    /**
     * Register a process to be killed when a pool dies.
     * @param a The pool to use to define the processes lifetime
     * @param proc The process to register
     * @param how How to kill the process, one of:
     * <PRE>
     * APR_KILL_NEVER         -- process is never sent any signals
     * APR_KILL_ALWAYS        -- process is sent SIGKILL on apr_pool_t cleanup
     * APR_KILL_AFTER_TIMEOUT -- SIGTERM, wait 3 seconds, SIGKILL
     * APR_JUST_WAIT          -- wait forever for the process to complete
     * APR_KILL_ONLY_ONCE     -- send SIGTERM and then wait
     * </PRE>
     */
    public static native void noteSubprocess(long a, long proc, int how);

    /**
     * Allocate a block of memory from a pool
     * @param p The pool to allocate from
     * @param size The amount of memory to allocate
     * @return The ByteBuffer with allocated memory
     */
    public static native ByteBuffer alloc(long p, int size);

    /**
     * Allocate a block of memory from a pool and set all of the memory to 0
     * @param p The pool to allocate from
     * @param size The amount of memory to allocate
     * @return The ByteBuffer with allocated memory
     */
    public static native ByteBuffer calloc(long p, int size);

    /*
     * User data management
     */

    /**
     * Set the data associated with the current pool
     * @param data The user data associated with the pool.
     * @param key The key to use for association
     * @param pool The current pool
     * <br><b>Warning :</b>
     * The data to be attached to the pool should have a life span
     * at least as long as the pool it is being attached to.
     * Object attached to the pool will be globally referenced
     * until the pool is cleared or dataSet is called with the null data.
     * @return APR Status code.
     */
     public static native int dataSet(long pool, String key, Object data);

    /**
     * Return the data associated with the current pool.
     * @param key The key for the data to retrieve
     * @param pool The current pool.
     */
     public static native Object dataGet(long pool, String key);

    /**
     * Run all of the child_cleanups, so that any unnecessary files are
     * closed because we are about to exec a new program
     */
    public static native void cleanupForExec();

}
