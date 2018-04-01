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

/** Status
 *
 * @author Mladen Turk
 */
public class Status {

    /**
     * APR_OS_START_ERROR is where the APR specific error values start.
     */
     public static final int APR_OS_START_ERROR   = 20000;
    /**
     * APR_OS_ERRSPACE_SIZE is the maximum number of errors you can fit
     *    into one of the error/status ranges below -- except for
     *    APR_OS_START_USERERR, which see.
     */
     public static final int APR_OS_ERRSPACE_SIZE = 50000;
    /**
     * APR_OS_START_STATUS is where the APR specific status codes start.
     */
     public static final int APR_OS_START_STATUS  = (APR_OS_START_ERROR + APR_OS_ERRSPACE_SIZE);

    /**
     * APR_OS_START_USERERR are reserved for applications that use APR that
     *     layer their own error codes along with APR's.  Note that the
     *     error immediately following this one is set ten times farther
     *     away than usual, so that users of apr have a lot of room in
     *     which to declare custom error codes.
     */
    public static final int APR_OS_START_USERERR  = (APR_OS_START_STATUS + APR_OS_ERRSPACE_SIZE);
    /**
     * APR_OS_START_USEERR is obsolete, defined for compatibility only.
     * Use APR_OS_START_USERERR instead.
     */
    public static final int APR_OS_START_USEERR    = APR_OS_START_USERERR;
    /**
     * APR_OS_START_CANONERR is where APR versions of errno values are defined
     *     on systems which don't have the corresponding errno.
     */
    public static final int APR_OS_START_CANONERR  = (APR_OS_START_USERERR + (APR_OS_ERRSPACE_SIZE * 10));

    /**
     * APR_OS_START_EAIERR folds EAI_ error codes from getaddrinfo() into
     *     apr_status_t values.
     */
    public static final int APR_OS_START_EAIERR  = (APR_OS_START_CANONERR + APR_OS_ERRSPACE_SIZE);
    /**
     * APR_OS_START_SYSERR folds platform-specific system error values into
     *     apr_status_t values.
     */
    public static final int APR_OS_START_SYSERR  = (APR_OS_START_EAIERR + APR_OS_ERRSPACE_SIZE);

    /** no error. */
    public static final int APR_SUCCESS = 0;

    /**
     * APR Error Values
     * <PRE>
     * <b>APR ERROR VALUES</b>
     * APR_ENOSTAT      APR was unable to perform a stat on the file
     * APR_ENOPOOL      APR was not provided a pool with which to allocate memory
     * APR_EBADDATE     APR was given an invalid date
     * APR_EINVALSOCK   APR was given an invalid socket
     * APR_ENOPROC      APR was not given a process structure
     * APR_ENOTIME      APR was not given a time structure
     * APR_ENODIR       APR was not given a directory structure
     * APR_ENOLOCK      APR was not given a lock structure
     * APR_ENOPOLL      APR was not given a poll structure
     * APR_ENOSOCKET    APR was not given a socket
     * APR_ENOTHREAD    APR was not given a thread structure
     * APR_ENOTHDKEY    APR was not given a thread key structure
     * APR_ENOSHMAVAIL  There is no more shared memory available
     * APR_EDSOOPEN     APR was unable to open the dso object.  For more
     *                  information call apr_dso_error().
     * APR_EGENERAL     General failure (specific information not available)
     * APR_EBADIP       The specified IP address is invalid
     * APR_EBADMASK     The specified netmask is invalid
     * APR_ESYMNOTFOUND Could not find the requested symbol
     * </PRE>
     *
     */
    public static final int APR_ENOSTAT       = (APR_OS_START_ERROR + 1);
    public static final int APR_ENOPOOL       = (APR_OS_START_ERROR + 2);
    public static final int APR_EBADDATE      = (APR_OS_START_ERROR + 4);
    public static final int APR_EINVALSOCK    = (APR_OS_START_ERROR + 5);
    public static final int APR_ENOPROC       = (APR_OS_START_ERROR + 6);
    public static final int APR_ENOTIME       = (APR_OS_START_ERROR + 7);
    public static final int APR_ENODIR        = (APR_OS_START_ERROR + 8);
    public static final int APR_ENOLOCK       = (APR_OS_START_ERROR + 9);
    public static final int APR_ENOPOLL       = (APR_OS_START_ERROR + 10);
    public static final int APR_ENOSOCKET     = (APR_OS_START_ERROR + 11);
    public static final int APR_ENOTHREAD     = (APR_OS_START_ERROR + 12);
    public static final int APR_ENOTHDKEY     = (APR_OS_START_ERROR + 13);
    public static final int APR_EGENERAL      = (APR_OS_START_ERROR + 14);
    public static final int APR_ENOSHMAVAIL   = (APR_OS_START_ERROR + 15);
    public static final int APR_EBADIP        = (APR_OS_START_ERROR + 16);
    public static final int APR_EBADMASK      = (APR_OS_START_ERROR + 17);
    public static final int APR_EDSOOPEN      = (APR_OS_START_ERROR + 19);
    public static final int APR_EABSOLUTE     = (APR_OS_START_ERROR + 20);
    public static final int APR_ERELATIVE     = (APR_OS_START_ERROR + 21);
    public static final int APR_EINCOMPLETE   = (APR_OS_START_ERROR + 22);
    public static final int APR_EABOVEROOT    = (APR_OS_START_ERROR + 23);
    public static final int APR_EBADPATH      = (APR_OS_START_ERROR + 24);
    public static final int APR_EPATHWILD     = (APR_OS_START_ERROR + 25);
    public static final int APR_ESYMNOTFOUND  = (APR_OS_START_ERROR + 26);
    public static final int APR_EPROC_UNKNOWN = (APR_OS_START_ERROR + 27);
    public static final int APR_ENOTENOUGHENTROPY = (APR_OS_START_ERROR + 28);

    /** APR Status Values
     * <PRE>
     * <b>APR STATUS VALUES</b>
     * APR_INCHILD        Program is currently executing in the child
     * APR_INPARENT       Program is currently executing in the parent
     * APR_DETACH         The thread is detached
     * APR_NOTDETACH      The thread is not detached
     * APR_CHILD_DONE     The child has finished executing
     * APR_CHILD_NOTDONE  The child has not finished executing
     * APR_TIMEUP         The operation did not finish before the timeout
     * APR_INCOMPLETE     The operation was incomplete although some processing
     *                    was performed and the results are partially valid
     * APR_BADCH          Getopt found an option not in the option string
     * APR_BADARG         Getopt found an option that is missing an argument
     *                    and an argument was specified in the option string
     * APR_EOF            APR has encountered the end of the file
     * APR_NOTFOUND       APR was unable to find the socket in the poll structure
     * APR_ANONYMOUS      APR is using anonymous shared memory
     * APR_FILEBASED      APR is using a file name as the key to the shared memory
     * APR_KEYBASED       APR is using a shared key as the key to the shared memory
     * APR_EINIT          Initializer value.  If no option has been found, but
     *                    the status variable requires a value, this should be used
     * APR_ENOTIMPL       The APR function has not been implemented on this
     *                    platform, either because nobody has gotten to it yet,
     *                    or the function is impossible on this platform.
     * APR_EMISMATCH      Two passwords do not match.
     * APR_EBUSY          The given lock was busy.
     * </PRE>
     *
     */
    public static final int APR_INCHILD       = (APR_OS_START_STATUS + 1);
    public static final int APR_INPARENT      = (APR_OS_START_STATUS + 2);
    public static final int APR_DETACH        = (APR_OS_START_STATUS + 3);
    public static final int APR_NOTDETACH     = (APR_OS_START_STATUS + 4);
    public static final int APR_CHILD_DONE    = (APR_OS_START_STATUS + 5);
    public static final int APR_CHILD_NOTDONE = (APR_OS_START_STATUS + 6);
    public static final int APR_TIMEUP        = (APR_OS_START_STATUS + 7);
    public static final int APR_INCOMPLETE    = (APR_OS_START_STATUS + 8);
    public static final int APR_BADCH         = (APR_OS_START_STATUS + 12);
    public static final int APR_BADARG        = (APR_OS_START_STATUS + 13);
    public static final int APR_EOF           = (APR_OS_START_STATUS + 14);
    public static final int APR_NOTFOUND      = (APR_OS_START_STATUS + 15);
    public static final int APR_ANONYMOUS     = (APR_OS_START_STATUS + 19);
    public static final int APR_FILEBASED     = (APR_OS_START_STATUS + 20);
    public static final int APR_KEYBASED      = (APR_OS_START_STATUS + 21);
    public static final int APR_EINIT         = (APR_OS_START_STATUS + 22);
    public static final int APR_ENOTIMPL      = (APR_OS_START_STATUS + 23);
    public static final int APR_EMISMATCH     = (APR_OS_START_STATUS + 24);
    public static final int APR_EBUSY         = (APR_OS_START_STATUS + 25);

    public static final int TIMEUP            = (APR_OS_START_USERERR + 1);
    public static final int EAGAIN            = (APR_OS_START_USERERR + 2);
    public static final int EINTR             = (APR_OS_START_USERERR + 3);
    public static final int EINPROGRESS       = (APR_OS_START_USERERR + 4);
    public static final int ETIMEDOUT         = (APR_OS_START_USERERR + 5);

    private static native boolean is(int err, int idx);
    /**
     * APR_STATUS_IS Status Value Tests
     * <br><b>Warning :</b> For any particular error condition, more than one of these tests
     *      may match. This is because platform-specific error codes may not
     *      always match the semantics of the POSIX codes these tests (and the
     *      corresponding APR error codes) are named after. A notable example
     *      are the APR_STATUS_IS_ENOENT and APR_STATUS_IS_ENOTDIR tests on
     *      Win32 platforms. The programmer should always be aware of this and
     *      adjust the order of the tests accordingly.
     *
     */
    public static final boolean APR_STATUS_IS_ENOSTAT(int s)    { return is(s, 1); }
    public static final boolean APR_STATUS_IS_ENOPOOL(int s)    { return is(s, 2); }
    /* empty slot: +3 */
    public static final boolean APR_STATUS_IS_EBADDATE(int s)   { return is(s, 4); }
    public static final boolean APR_STATUS_IS_EINVALSOCK(int s) { return is(s, 5); }
    public static final boolean APR_STATUS_IS_ENOPROC(int s)    { return is(s, 6); }
    public static final boolean APR_STATUS_IS_ENOTIME(int s)    { return is(s, 7); }
    public static final boolean APR_STATUS_IS_ENODIR(int s)     { return is(s, 8); }
    public static final boolean APR_STATUS_IS_ENOLOCK(int s)    { return is(s, 9); }
    public static final boolean APR_STATUS_IS_ENOPOLL(int s)    { return is(s, 10); }
    public static final boolean APR_STATUS_IS_ENOSOCKET(int s)  { return is(s, 11); }
    public static final boolean APR_STATUS_IS_ENOTHREAD(int s)  { return is(s, 12); }
    public static final boolean APR_STATUS_IS_ENOTHDKEY(int s)  { return is(s, 13); }
    public static final boolean APR_STATUS_IS_EGENERAL(int s)   { return is(s, 14); }
    public static final boolean APR_STATUS_IS_ENOSHMAVAIL(int s){ return is(s, 15); }
    public static final boolean APR_STATUS_IS_EBADIP(int s)     { return is(s, 16); }
    public static final boolean APR_STATUS_IS_EBADMASK(int s)   { return is(s, 17); }
    /* empty slot: +18 */
    public static final boolean APR_STATUS_IS_EDSOPEN(int s)    { return is(s, 19); }
    public static final boolean APR_STATUS_IS_EABSOLUTE(int s)  { return is(s, 20); }
    public static final boolean APR_STATUS_IS_ERELATIVE(int s)  { return is(s, 21); }
    public static final boolean APR_STATUS_IS_EINCOMPLETE(int s){ return is(s, 22); }
    public static final boolean APR_STATUS_IS_EABOVEROOT(int s) { return is(s, 23); }
    public static final boolean APR_STATUS_IS_EBADPATH(int s)   { return is(s, 24); }
    public static final boolean APR_STATUS_IS_EPATHWILD(int s)  { return is(s, 25); }
    public static final boolean APR_STATUS_IS_ESYMNOTFOUND(int s)      { return is(s, 26); }
    public static final boolean APR_STATUS_IS_EPROC_UNKNOWN(int s)     { return is(s, 27); }
    public static final boolean APR_STATUS_IS_ENOTENOUGHENTROPY(int s) { return is(s, 28); }

    /*
     * APR_Error
     */
    public static final boolean APR_STATUS_IS_INCHILD(int s)    { return is(s, 51); }
    public static final boolean APR_STATUS_IS_INPARENT(int s)   { return is(s, 52); }
    public static final boolean APR_STATUS_IS_DETACH(int s)     { return is(s, 53); }
    public static final boolean APR_STATUS_IS_NOTDETACH(int s)  { return is(s, 54); }
    public static final boolean APR_STATUS_IS_CHILD_DONE(int s) { return is(s, 55); }
    public static final boolean APR_STATUS_IS_CHILD_NOTDONE(int s)  { return is(s, 56); }
    public static final boolean APR_STATUS_IS_TIMEUP(int s)     { return is(s, 57); }
    public static final boolean APR_STATUS_IS_INCOMPLETE(int s) { return is(s, 58); }
    /* empty slot: +9 */
    /* empty slot: +10 */
    /* empty slot: +11 */
    public static final boolean APR_STATUS_IS_BADCH(int s)      { return is(s, 62); }
    public static final boolean APR_STATUS_IS_BADARG(int s)     { return is(s, 63); }
    public static final boolean APR_STATUS_IS_EOF(int s)        { return is(s, 64); }
    public static final boolean APR_STATUS_IS_NOTFOUND(int s)   { return is(s, 65); }
    /* empty slot: +16 */
    /* empty slot: +17 */
    /* empty slot: +18 */
    public static final boolean APR_STATUS_IS_ANONYMOUS(int s)  { return is(s, 69); }
    public static final boolean APR_STATUS_IS_FILEBASED(int s)  { return is(s, 70); }
    public static final boolean APR_STATUS_IS_KEYBASED(int s)   { return is(s, 71); }
    public static final boolean APR_STATUS_IS_EINIT(int s)      { return is(s, 72); }
    public static final boolean APR_STATUS_IS_ENOTIMPL(int s)   { return is(s, 73); }
    public static final boolean APR_STATUS_IS_EMISMATCH(int s)  { return is(s, 74); }
    public static final boolean APR_STATUS_IS_EBUSY(int s)      { return is(s, 75); }

    /* Socket errors */
    public static final boolean APR_STATUS_IS_EAGAIN(int s)     { return is(s, 90); }
    public static final boolean APR_STATUS_IS_ETIMEDOUT(int s)  { return is(s, 91); }
    public static final boolean APR_STATUS_IS_ECONNABORTED(int s) { return is(s, 92); }
    public static final boolean APR_STATUS_IS_ECONNRESET(int s)   { return is(s, 93); }
    public static final boolean APR_STATUS_IS_EINPROGRESS(int s)  { return is(s, 94); }
    public static final boolean APR_STATUS_IS_EINTR(int s)      { return is(s, 95); }
    public static final boolean APR_STATUS_IS_ENOTSOCK(int s)   { return is(s, 96); }
    public static final boolean APR_STATUS_IS_EINVAL(int s)     { return is(s, 97); }

}
