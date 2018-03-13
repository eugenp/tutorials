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

/** Directory
 *
 * @author Mladen Turk
 */
public class Directory {

    /**
     * Create a new directory on the file system.
     * @param path the path for the directory to be created. (use / on all systems)
     * @param perm Permissions for the new directory.
     * @param pool the pool to use.
     */
    public static native int make(String path, int perm, long pool);

    /** Creates a new directory on the file system, but behaves like
     * 'mkdir -p'. Creates intermediate directories as required. No error
     * will be reported if PATH already exists.
     * @param path the path for the directory to be created. (use / on all systems)
     * @param perm Permissions for the new directory.
     * @param pool the pool to use.
     */
    public static native int makeRecursive(String path, int perm, long pool);

    /**
     * Remove directory from the file system.
     * @param path the path for the directory to be removed. (use / on all systems)
     * @param pool the pool to use.
     */
    public static native int remove(String path, long pool);

    /**
     * Find an existing directory suitable as a temporary storage location.
     * @param pool The pool to use for any necessary allocations.
     * @return The temp directory.
     *
     * This function uses an algorithm to search for a directory that an
     * an application can use for temporary storage.  Once such a
     * directory is found, that location is cached by the library.  Thus,
     * callers only pay the cost of this algorithm once if that one time
     * is successful.
     *
     */
    public static native String tempGet(long pool);

    /**
     * Open the specified directory.
     * @param dirname The full path to the directory (use / on all systems)
     * @param pool The pool to use.
     * @return The opened directory descriptor.
     */
    public static native long open(String dirname, long pool)
        throws Error;

    /**
     * close the specified directory.
     * @param thedir the directory descriptor to close.
     */
    public static native int close(long thedir);

    /**
     * Rewind the directory to the first entry.
     * @param thedir the directory descriptor to rewind.
     */
    public static native int rewind(long thedir);


    /**
     * Read the next entry from the specified directory.
     * @param finfo the file info structure and filled in by apr_dir_read
     * @param wanted The desired apr_finfo_t fields, as a bit flag of APR_FINFO_ values
     * @param thedir the directory descriptor returned from apr_dir_open
     * No ordering is guaranteed for the entries read.
     */
    public static native int read(FileInfo finfo, int wanted, long thedir);

}
