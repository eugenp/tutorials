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

/** User
 *
 * @author Mladen Turk
 */
public class User {

    /**
     * Get the userid (and groupid) of the calling process
     * This function is available only if APR_HAS_USER is defined.
     * @param p The pool from which to allocate working space
     * @return Returns the user id
     */
     public static native long uidCurrent(long p)
        throws Error;

    /**
     * Get the groupid of the calling process
     * This function is available only if APR_HAS_USER is defined.
     * @param p The pool from which to allocate working space
     * @return Returns the group id
     */
     public static native long gidCurrent(long p)
        throws Error;


    /**
     * Get the userid for the specified username
     * This function is available only if APR_HAS_USER is defined.
     * @param username The username to lookup
     * @param p The pool from which to allocate working space
     * @return Returns the user id
     */
     public static native long uid(String username, long p)
        throws Error;

    /**
     * Get the groupid for the specified username
     * This function is available only if APR_HAS_USER is defined.
     * @param username The username to lookup
     * @param p The pool from which to allocate working space
     * @return  Returns the user's group id
     */
     public static native long usergid(String username, long p)
        throws Error;

    /**
     * Get the groupid for a specified group name
     * This function is available only if APR_HAS_USER is defined.
     * @param groupname The group name to look up
     * @param p The pool from which to allocate working space
     * @return  Returns the user's group id
     */
     public static native long gid(String groupname, long p)
        throws Error;

    /**
     * Get the user name for a specified userid
     * This function is available only if APR_HAS_USER is defined.
     * @param userid The userid
     * @param p The pool from which to allocate the string
     * @return New string containing user name
     */
     public static native String username(long userid, long p)
        throws Error;

    /**
     * Get the group name for a specified groupid
     * This function is available only if APR_HAS_USER is defined.
     * @param groupid The groupid
     * @param p The pool from which to allocate the string
     * @return New string containing group name
     */
     public static native String groupname(long groupid, long p)
        throws Error;

    /**
     * Compare two user identifiers for equality.
     * This function is available only if APR_HAS_USER is defined.
     * @param left One uid to test
     * @param right Another uid to test
     * @return APR_SUCCESS if the apr_uid_t structures identify the same user,
     * APR_EMISMATCH if not, APR_BADARG if an apr_uid_t is invalid.
     */
     public static native int uidcompare(long left, long right);

    /**
     * Compare two group identifiers for equality.
     * This function is available only if APR_HAS_USER is defined.
     * @param left One gid to test
     * @param right Another gid to test
     * @return APR_SUCCESS if the apr_gid_t structures identify the same group,
     * APR_EMISMATCH if not, APR_BADARG if an apr_gid_t is invalid.
     */
     public static native int gidcompare(long left, long right);

    /**
     * Get the home directory for the named user
     * This function is available only if APR_HAS_USER is defined.
     * @param username The named user
     * @param p The pool from which to allocate the string
     * @return New string containing directory name
     */
     public static native String homepath(String username, long p)
        throws Error;

}
