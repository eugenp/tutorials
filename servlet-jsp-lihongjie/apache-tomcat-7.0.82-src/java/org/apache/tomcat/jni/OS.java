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

/** OS
 *
 * @author Mladen Turk
 */
public class OS {

    /* OS Enums */
    private static final int UNIX      = 1;
    private static final int NETWARE   = 2;
    private static final int WIN32     = 3;
    private static final int WIN64     = 4;
    private static final int LINUX     = 5;
    private static final int SOLARIS   = 6;
    private static final int BSD       = 7;
    private static final int MACOSX    = 8;

    public static final int LOG_EMERG  = 1;
    public static final int LOG_ERROR  = 2;
    public static final int LOG_NOTICE = 3;
    public static final int LOG_WARN   = 4;
    public static final int LOG_INFO   = 5;
    public static final int LOG_DEBUG  = 6;

    /**
     * Check for OS type.
     * @param type OS type to test.
     */
    private static native boolean is(int type);

    public static final boolean IS_UNIX    = is(UNIX);
    public static final boolean IS_NETWARE = is(NETWARE);
    public static final boolean IS_WIN32   = is(WIN32);
    public static final boolean IS_WIN64   = is(WIN64);
    public static final boolean IS_LINUX   = is(LINUX);
    public static final boolean IS_SOLARIS = is(SOLARIS);
    public static final boolean IS_BSD     = is(BSD);
    public static final boolean IS_MACOSX  = is(MACOSX);

    /**
     * Get the name of the system default character set.
     * @param pool the pool to allocate the name from, if needed
     */
    public static native String defaultEncoding(long pool);

    /**
     * Get the name of the current locale character set.
     * Defers to apr_os_default_encoding if the current locale's
     * data can't be retrieved on this system.
     * @param pool the pool to allocate the name from, if needed
     */
    public static native String localeEncoding(long pool);

    /**
     * Generate random bytes.
     * @param buf Buffer to fill with random bytes
     * @param len Length of buffer in bytes
     */
    public static native int random(byte [] buf, int len);

    /**
     * Gather system info.
     * <PRE>
     * On exit the inf array will be filled with:
     * inf[0]  - Total usable main memory size
     * inf[1]  - Available memory size
     * inf[2]  - Total page file/swap space size
     * inf[3]  - Page file/swap space still available
     * inf[4]  - Amount of shared memory
     * inf[5]  - Memory used by buffers
     * inf[6]  - Memory Load
     *
     * inf[7]  - Idle Time in microseconds
     * inf[8]  - Kernel Time in microseconds
     * inf[9]  - User Time in microseconds
     *
     * inf[10] - Process creation time (apr_time_t)
     * inf[11] - Process Kernel Time in microseconds
     * inf[12] - Process User Time in microseconds
     *
     * inf[13] - Current working set size.
     * inf[14] - Peak working set size.
     * inf[15] - Number of page faults.
     * </PRE>
     * @param inf array that will be filled with system information.
     *            Array length must be at least 16.
     */
    public static native int info(long [] inf);

    /**
     * Expand environment variables.
     * @param str String to expand
     * @return Expanded string with replaced environment variables.
     */
    public static native String expand(String str);

    /**
     * Initialize system logging.
     * @param domain String that will be prepended to every message
     */
    public static native void sysloginit(String domain);

    /**
     * Log message.
     * @param level Log message severity. See LOG_XXX enums.
     * @param message Message to log
     */
    public static native void syslog(int level, String message);

}
