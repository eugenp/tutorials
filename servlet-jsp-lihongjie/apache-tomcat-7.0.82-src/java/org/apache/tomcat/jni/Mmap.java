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

/** Mmap
 *
 * @author Mladen Turk
 */
public class Mmap {
    /** MMap opened for reading */
    public static final int APR_MMAP_READ  = 1;
    /** MMap opened for writing */
    public static final int APR_MMAP_WRITE = 2;


    /**
     * Create a new mmap'ed file out of an existing APR file.
     * @param file The file turn into an mmap.
     * @param offset The offset into the file to start the data pointer at.
     * @param size The size of the file
     * @param flag bit-wise or of:
     * <PRE>
     * APR_MMAP_READ       MMap opened for reading
     * APR_MMAP_WRITE      MMap opened for writing
     * </PRE>
     * @param pool The pool to use when creating the mmap.
     * @return The newly created mmap'ed file.
     */
    public static native long create(long file, long offset, long size, int flag, long pool)
        throws Error;

    /**
     * Duplicate the specified MMAP.
     * @param mmap The mmap to duplicate.
     * @param pool The pool to use for new_mmap.
     * @return Duplicated mmap'ed file.
     */
    public static native long dup(long mmap, long pool)
        throws Error;

    /**
     * Remove a mmap'ed.
     * @param mm The mmap'ed file.
     */
    public static native int delete(long mm);

    /**
     * Move the pointer into the mmap'ed file to the specified offset.
     * @param mm The mmap'ed file.
     * @param offset The offset to move to.
     * @return The pointer to the offset specified.
     */
    public static native long offset(long mm, long offset)
        throws Error;

}
