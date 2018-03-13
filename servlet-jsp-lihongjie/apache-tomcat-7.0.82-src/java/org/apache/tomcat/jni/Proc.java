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

/** Proc
 *
 * @author Mladen Turk
 */
public class Proc {

    /*
     * apr_cmdtype_e enum
     */
    public static final int APR_SHELLCM      = 0; /** use the shell to invoke the program */
    public static final int APR_PROGRAM      = 1; /** invoke the program directly, no copied env */
    public static final int APR_PROGRAM_ENV  = 2; /** invoke the program, replicating our environment */
    public static final int APR_PROGRAM_PATH = 3; /** find program on PATH, use our environment */
    public static final int APR_SHELLCMD_ENV = 4; /** use the shell to invoke the program,
                                                   *   replicating our environment
                                                   */

    /*
     * apr_wait_how_e enum
     */
    public static final int APR_WAIT   = 0; /** wait for the specified process to finish */
    public static final int APR_NOWAIT = 1; /** do not wait -- just see if it has finished */

    /*
     * apr_exit_why_e enum
     */
    public static final int APR_PROC_EXIT        = 1; /** process exited normally */
    public static final int APR_PROC_SIGNAL      = 2; /** process exited due to a signal */
    public static final int APR_PROC_SIGNAL_CORE = 4; /** process exited and dumped a core file */

    public static final int APR_NO_PIPE       = 0;
    public static final int APR_FULL_BLOCK    = 1;
    public static final int APR_FULL_NONBLOCK = 2;
    public static final int APR_PARENT_BLOCK  = 3;
    public static final int APR_CHILD_BLOCK   = 4;

    public static final int APR_LIMIT_CPU     = 0;
    public static final int APR_LIMIT_MEM     = 1;
    public static final int APR_LIMIT_NPROC   = 2;
    public static final int APR_LIMIT_NOFILE  = 3;


    /** child has died, caller must call unregister still */
    public static final int APR_OC_REASON_DEATH      = 0;
    /** write_fd is unwritable */
    public static final int APR_OC_REASON_UNWRITABLE = 1;
    /** a restart is occurring, perform any necessary cleanup (including
     * sending a special signal to child)
     */
    public static final int APR_OC_REASON_RESTART    = 2;
    /** unregister has been called, do whatever is necessary (including
     * kill the child)
     */
    public static final int APR_OC_REASON_UNREGISTER = 3;
    /** somehow the child exited without us knowing ... buggy os? */
    public static final int APR_OC_REASON_LOST       = 4;
    /** a health check is occurring, for most maintenance functions
     * this is a no-op.
     */
    public static final int APR_OC_REASON_RUNNING    = 5;

    /* apr_kill_conditions_e enumeration */
    /** process is never sent any signals */
    public static final int APR_KILL_NEVER         = 0;
    /** process is sent SIGKILL on apr_pool_t cleanup */
    public static final int APR_KILL_ALWAYS        = 1;
    /** SIGTERM, wait 3 seconds, SIGKILL */
    public static final int APR_KILL_AFTER_TIMEOUT = 2;
    /** wait forever for the process to complete */
    public static final int APR_JUST_WAIT          = 3;
    /** send SIGTERM and then wait */
    public static final int APR_KILL_ONLY_ONCE     = 4;

    public static final int APR_PROC_DETACH_FOREGROUND = 0; /** Do not detach */
    public static final int APR_PROC_DETACH_DAEMONIZE  = 1; /** Detach */

    /* Maximum number of arguments for create process call */
    public static final int MAX_ARGS_SIZE          = 1024;
    /* Maximum number of environment variables for create process call */
    public static final int MAX_ENV_SIZE           = 1024;

    /**
     * Allocate apr_proc_t structure from pool
     * This is not an apr function.
     * @param cont The pool to use.
     */
    public static native long alloc(long cont);

    /**
     * This is currently the only non-portable call in APR.  This executes
     * a standard unix fork.
     * @param proc The resulting process handle.
     * @param cont The pool to use.
     * @return APR_INCHILD for the child, and APR_INPARENT for the parent
     * or an error.
     */
    public static native int fork(long [] proc, long cont);

    /**
     * Create a new process and execute a new program within that process.
     * This function returns without waiting for the new process to terminate;
     * use apr_proc_wait for that.
     * @param progname The program to run
     * @param args The arguments to pass to the new program.  The first
     *             one should be the program name.
     * @param env The new environment table for the new process.  This
     *            should be a list of NULL-terminated strings. This argument
     *            is ignored for APR_PROGRAM_ENV, APR_PROGRAM_PATH, and
     *            APR_SHELLCMD_ENV types of commands.
     * @param attr The procattr we should use to determine how to create the new
     * process
     * @param pool The pool to use.
     * @return The resulting process handle.
     */
    public static native int create(long proc, String progname,
                                    String [] args, String [] env,
                                    long attr, long pool);

    /**
     * Wait for a child process to die
     * @param proc The process handle that corresponds to the desired child process
     * @param exit exit[0] The returned exit status of the child, if a child process
     *                dies, or the signal that caused the child to die.
     *                On platforms that don't support obtaining this information,
     *                the status parameter will be returned as APR_ENOTIMPL.
     * exit[1] Why the child died, the bitwise or of:
     * <PRE>
     * APR_PROC_EXIT         -- process terminated normally
     * APR_PROC_SIGNAL       -- process was killed by a signal
     * APR_PROC_SIGNAL_CORE  -- process was killed by a signal, and
     *                          generated a core dump.
     * </PRE>
     * @param waithow How should we wait.  One of:
     * <PRE>
     * APR_WAIT   -- block until the child process dies.
     * APR_NOWAIT -- return immediately regardless of if the
     *               child is dead or not.
     * </PRE>
     * @return The childs status is in the return code to this process.  It is one of:
     * <PRE>
     * APR_CHILD_DONE     -- child is no longer running.
     * APR_CHILD_NOTDONE  -- child is still running.
     * </PRE>
     */
    public static native int wait(long proc, int [] exit, int waithow);

    /**
     * Wait for any current child process to die and return information
     * about that child.
     * @param proc Pointer to NULL on entry, will be filled out with child's
     *             information
     * @param exit exit[0] The returned exit status of the child, if a child process
     *                dies, or the signal that caused the child to die.
     *                On platforms that don't support obtaining this information,
     *                the status parameter will be returned as APR_ENOTIMPL.
     * exit[1] Why the child died, the bitwise or of:
     * <PRE>
     * APR_PROC_EXIT         -- process terminated normally
     * APR_PROC_SIGNAL       -- process was killed by a signal
     * APR_PROC_SIGNAL_CORE  -- process was killed by a signal, and
     *                          generated a core dump.
     * </PRE>
     * @param waithow How should we wait.  One of:
     * <PRE>
     * APR_WAIT   -- block until the child process dies.
     * APR_NOWAIT -- return immediately regardless of if the
     *               child is dead or not.
     * </PRE>
     * @param pool Pool to allocate child information out of.
     */
    public static native int waitAllProcs(long proc, int [] exit,
                                          int waithow, long pool);

     /**
     * Detach the process from the controlling terminal.
     * @param daemonize set to non-zero if the process should daemonize
     *                  and become a background process, else it will
     *                  stay in the foreground.
     */
    public static native int detach(int daemonize);

    /**
     * Terminate a process.
     * @param proc The process to terminate.
     * @param sig How to kill the process.
     */
    public static native int kill(long proc, int sig);

}
