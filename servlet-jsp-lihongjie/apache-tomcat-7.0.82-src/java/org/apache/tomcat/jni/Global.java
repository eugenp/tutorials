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

/** Global
 *
 * @author Mladen Turk
 */
public class Global {

    /**
     * Create and initialize a mutex that can be used to synchronize both
     * processes and threads. Note: There is considerable overhead in using
     * this API if only cross-process or cross-thread mutual exclusion is
     * required. See apr_proc_mutex.h and apr_thread_mutex.h for more
     * specialized lock routines.
     * <br><b>Warning :</b> Check APR_HAS_foo_SERIALIZE defines to see if the platform supports
     *          APR_LOCK_foo.  Only APR_LOCK_DEFAULT is portable.
     * @param fname A file name to use if the lock mechanism requires one.  This
     *        argument should always be provided.  The lock code itself will
     *        determine if it should be used.
     * @param mech The mechanism to use for the interprocess lock, if any; one of
     * <PRE>
     *            APR_LOCK_FCNTL
     *            APR_LOCK_FLOCK
     *            APR_LOCK_SYSVSEM
     *            APR_LOCK_POSIXSEM
     *            APR_LOCK_PROC_PTHREAD
     *            APR_LOCK_DEFAULT     pick the default mechanism for the platform
     * </PRE>
     * @param pool the pool from which to allocate the mutex.
     * @return Newly created mutex.
     */
    public static native long create(String fname, int mech, long pool)
        throws Error;

    /**
     * Re-open a mutex in a child process.
     * @param fname A file name to use if the mutex mechanism requires one.  This
     *              argument should always be provided.  The mutex code itself will
     *              determine if it should be used.  This filename should be the
     *              same one that was passed to apr_proc_mutex_create().
     * @param pool The pool to operate on.
     * This function must be called to maintain portability, even
     *         if the underlying lock mechanism does not require it.
     * @return Newly opened mutex.
     */
    public static native long childInit(String fname, long pool)
        throws Error;

    /**
     * Acquire the lock for the given mutex. If the mutex is already locked,
     * the current thread will be put to sleep until the lock becomes available.
     * @param mutex the mutex on which to acquire the lock.
     */
    public static native int lock(long mutex);

    /**
     * Attempt to acquire the lock for the given mutex. If the mutex has already
     * been acquired, the call returns immediately with APR_EBUSY. Note: it
     * is important that the APR_STATUS_IS_EBUSY(s) macro be used to determine
     * if the return value was APR_EBUSY, for portability reasons.
     * @param mutex the mutex on which to attempt the lock acquiring.
     */
    public static native int trylock(long mutex);

    /**
     * Release the lock for the given mutex.
     * @param mutex the mutex from which to release the lock.
     */
    public static native int unlock(long mutex);

    /**
     * Destroy the mutex and free the memory associated with the lock.
     * @param mutex the mutex to destroy.
     */
    public static native int destroy(long mutex);

}
