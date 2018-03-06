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

/** Fileinfo
 *
 * @author Mladen Turk
 */
public class FileInfo {

    /** Allocates memory and closes lingering handles in the specified pool */
    public long pool;
    /** The bitmask describing valid fields of this apr_finfo_t structure
     *  including all available 'wanted' fields and potentially more */
    public int valid;
    /** The access permissions of the file.  Mimics Unix access rights. */
    public int protection;
    /** The type of file.  One of APR_REG, APR_DIR, APR_CHR, APR_BLK, APR_PIPE,
     * APR_LNK or APR_SOCK.  If the type is undetermined, the value is APR_NOFILE.
     * If the type cannot be determined, the value is APR_UNKFILE.
     */
    public int filetype;
    /** The user id that owns the file */
    public int user;
    /** The group id that owns the file */
    public int group;
    /** The inode of the file. */
    public int inode;
    /** The id of the device the file is on. */
    public int device;
    /** The number of hard links to the file. */
    public int nlink;
    /** The size of the file */
    public long size;
    /** The storage size consumed by the file */
    public long csize;
    /** The time the file was last accessed */
    public long atime;
    /** The time the file was last modified */
    public long mtime;
    /** The time the file was created, or the inode was last changed */
    public long ctime;
    /** The pathname of the file (possibly unrooted) */
    public String fname;
    /** The file's name (no path) in filesystem case */
    public String name;
    /** The file's handle, if accessed (can be submitted to apr_duphandle) */
    public long filehand;

}
