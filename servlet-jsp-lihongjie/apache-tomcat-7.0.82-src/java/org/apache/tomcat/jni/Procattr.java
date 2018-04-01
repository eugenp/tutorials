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

/** Procattr
 *
 * @author Mladen Turk
 */
public class Procattr {

    /**
     * Create and initialize a new procattr variable
     * @param cont The pool to use
     * @return The newly created procattr.
     */
    public static native long create(long cont)
        throws Error;

    /**
     * Determine if any of stdin, stdout, or stderr should be linked to pipes
     * when starting a child process.
     * @param attr The procattr we care about.
     * @param in Should stdin be a pipe back to the parent?
     * @param out Should stdout be a pipe back to the parent?
     * @param err Should stderr be a pipe back to the parent?
     */
    public static native int ioSet(long attr, int in, int out, int err);
    /**
     * Set the child_in and/or parent_in values to existing apr_file_t values.
     * <br>
     * This is NOT a required initializer function. This is
     * useful if you have already opened a pipe (or multiple files)
     * that you wish to use, perhaps persistently across multiple
     * process invocations - such as a log file. You can save some
     * extra function calls by not creating your own pipe since this
     * creates one in the process space for you.
     * @param attr The procattr we care about.
     * @param in apr_file_t value to use as child_in. Must be a valid file.
     * @param parent apr_file_t value to use as parent_in. Must be a valid file.
     */
    public static native int childInSet(long attr, long in, long parent);

    /**
     * Set the child_out and parent_out values to existing apr_file_t values.
     * <br>
     * This is NOT a required initializer function. This is
     * useful if you have already opened a pipe (or multiple files)
     * that you wish to use, perhaps persistently across multiple
     * process invocations - such as a log file.
     * @param attr The procattr we care about.
     * @param out apr_file_t value to use as child_out. Must be a valid file.
     * @param parent apr_file_t value to use as parent_out. Must be a valid file.
     */
    public static native int childOutSet(long attr, long out, long parent);

    /**
     * Set the child_err and parent_err values to existing apr_file_t values.
     * <br>
     * This is NOT a required initializer function. This is
     * useful if you have already opened a pipe (or multiple files)
     * that you wish to use, perhaps persistently across multiple
     * process invocations - such as a log file.
     * @param attr The procattr we care about.
     * @param err apr_file_t value to use as child_err. Must be a valid file.
     * @param parent apr_file_t value to use as parent_err. Must be a valid file.
     */
    public static native int childErrSet(long attr, long err, long parent);

    /**
     * Set which directory the child process should start executing in.
     * @param attr The procattr we care about.
     * @param dir Which dir to start in.  By default, this is the same dir as
     *            the parent currently resides in, when the createprocess call
     *            is made.
     */
    public static native int dirSet(long attr, String dir);

    /**
     * Set what type of command the child process will call.
     * @param attr The procattr we care about.
     * @param cmd The type of command.  One of:
     * <PRE>
     * APR_SHELLCMD     --  Anything that the shell can handle
     * APR_PROGRAM      --  Executable program   (default)
     * APR_PROGRAM_ENV  --  Executable program, copy environment
     * APR_PROGRAM_PATH --  Executable program on PATH, copy env
     * </PRE>
     */
    public static native int cmdtypeSet(long attr, int cmd);

    /**
     * Determine if the child should start in detached state.
     * @param attr The procattr we care about.
     * @param detach Should the child start in detached state?  Default is no.
     */
    public static native int detachSet(long attr, int detach);

    /**
     * Specify that apr_proc_create() should do whatever it can to report
     * failures to the caller of apr_proc_create(), rather than find out in
     * the child.
     * @param attr The procattr describing the child process to be created.
     * @param chk Flag to indicate whether or not extra work should be done
     *            to try to report failures to the caller.
     * <br>
     * This flag only affects apr_proc_create() on platforms where
     * fork() is used.  This leads to extra overhead in the calling
     * process, but that may help the application handle such
     * errors more gracefully.
     */
    public static native int errorCheckSet(long attr, int chk);

    /**
     * Determine if the child should start in its own address space or using the
     * current one from its parent
     * @param attr The procattr we care about.
     * @param addrspace Should the child start in its own address space?  Default
     * is no on NetWare and yes on other platforms.
     */
    public static native int addrspaceSet(long attr, int addrspace);

    /**
     * Specify an error function to be called in the child process if APR
     * encounters an error in the child prior to running the specified program.
     * @param attr The procattr describing the child process to be created.
     * @param pool The the pool to use.
     * @param o The Object to call in the child process.
     * <br>
     * At the present time, it will only be called from apr_proc_create()
     * on platforms where fork() is used.  It will never be called on other
     * platforms, on those platforms apr_proc_create() will return the error
     * in the parent process rather than invoke the callback in the now-forked
     * child process.
     */
    public static native void errfnSet(long attr, long pool, Object o);

    /**
     * Set the username used for running process
     * @param attr The procattr we care about.
     * @param username The username used
     * @param password User password if needed. Password is needed on WIN32
     *                 or any other platform having
     *                 APR_PROCATTR_USER_SET_REQUIRES_PASSWORD set.
     */
    public static native int userSet(long attr, String username, String password);

    /**
     * Set the group used for running process
     * @param attr The procattr we care about.
     * @param groupname The group name  used
     */
    public static native int groupSet(long attr, String groupname);


}
