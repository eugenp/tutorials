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

/** Windows Registry support
 *
 * @author Mladen Turk
 */
public class Registry {

    /* Registry Enums */
    public static final int HKEY_CLASSES_ROOT       = 1;
    public static final int HKEY_CURRENT_CONFIG     = 2;
    public static final int HKEY_CURRENT_USER       = 3;
    public static final int HKEY_LOCAL_MACHINE      = 4;
    public static final int HKEY_USERS              = 5;

    public static final int KEY_ALL_ACCESS          = 0x0001;
    public static final int KEY_CREATE_LINK         = 0x0002;
    public static final int KEY_CREATE_SUB_KEY      = 0x0004;
    public static final int KEY_ENUMERATE_SUB_KEYS  = 0x0008;
    public static final int KEY_EXECUTE             = 0x0010;
    public static final int KEY_NOTIFY              = 0x0020;
    public static final int KEY_QUERY_VALUE         = 0x0040;
    public static final int KEY_READ                = 0x0080;
    public static final int KEY_SET_VALUE           = 0x0100;
    public static final int KEY_WOW64_64KEY         = 0x0200;
    public static final int KEY_WOW64_32KEY         = 0x0400;
    public static final int KEY_WRITE               = 0x0800;

    public static final int REG_BINARY              = 1;
    public static final int REG_DWORD               = 2;
    public static final int REG_EXPAND_SZ           = 3;
    public static final int REG_MULTI_SZ            = 4;
    public static final int REG_QWORD               = 5;
    public static final int REG_SZ                  = 6;

     /**
     * Create or open a Registry Key.
     * @param name Registry Subkey to open
     * @param root Root key, one of HKEY_*
     * @param sam Access mask that specifies the access rights for the key.
     * @param pool Pool used for native memory allocation
     * @return Opened Registry key
     */
    public static native long create(int root, String name, int sam, long pool)
        throws Error;

     /**
     * Opens the specified Registry Key.
     * @param name Registry Subkey to open
     * @param root Root key, one of HKEY_*
     * @param sam Access mask that specifies the access rights for the key.
     * @param pool Pool used for native memory allocation
     * @return Opened Registry key
     */
    public static native long open(int root, String name, int sam, long pool)
        throws Error;

    /**
     * Close the specified Registry key.
     * @param key The Registry key descriptor to close.
     */
    public static native int close(long key);

    /**
     * Get the Registry key type.
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to query
     * @return Value type or negative error value
     */
    public static native int getType(long key, String name);

    /**
     * Get the Registry value for REG_DWORD
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to query
     * @return Registry key value
     */
    public static native int getValueI(long key, String name)
        throws Error;

    /**
     * Get the Registry value for REG_QWORD or REG_DWORD
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to query
     * @return Registry key value
     */
    public static native long getValueJ(long key, String name)
        throws Error;

    /**
     * Get the Registry key length.
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to query
     * @return Value size or negative error value
     */
    public static native int getSize(long key, String name);

    /**
     * Get the Registry value for REG_SZ or REG_EXPAND_SZ
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to query
     * @return Registry key value
     */
    public static native String getValueS(long key, String name)
        throws Error;

    /**
     * Get the Registry value for REG_MULTI_SZ
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to query
     * @return Registry key value
     */
    public static native String[] getValueA(long key, String name)
        throws Error;

    /**
     * Get the Registry value for REG_BINARY
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to query
     * @return Registry key value
     */
    public static native byte[] getValueB(long key, String name)
        throws Error;


    /**
     * Set the Registry value for REG_DWORD
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to set
     * @param val The the value to set
     * @return If the function succeeds, the return value is 0
     */
    public static native int setValueI(long key, String name, int val);

    /**
     * Set the Registry value for REG_QWORD
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to set
     * @param val The the value to set
     * @return If the function succeeds, the return value is 0
     */
    public static native int setValueJ(long key, String name, long val);

    /**
     * Set the Registry value for REG_SZ
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to set
     * @param val The the value to set
     * @return If the function succeeds, the return value is 0
     */
    public static native int setValueS(long key, String name, String val);

    /**
     * Set the Registry value for REG_EXPAND_SZ
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to set
     * @param val The the value to set
     * @return If the function succeeds, the return value is 0
     */
    public static native int setValueE(long key, String name, String val);

     /**
     * Set the Registry value for REG_MULTI_SZ
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to set
     * @param val The the value to set
     * @return If the function succeeds, the return value is 0
     */
    public static native int setValueA(long key, String name, String[] val);

     /**
     * Set the Registry value for REG_BINARY
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to set
     * @param val The the value to set
     * @return If the function succeeds, the return value is 0
     */
    public static native int setValueB(long key, String name, byte[] val);

    /**
     * Enumerate the Registry subkeys
     * @param key The Registry key descriptor to use.
     * @return Array of all subkey names
     */
    public static native String[] enumKeys(long key)
        throws Error;

    /**
     * Enumerate the Registry values
     * @param key The Registry key descriptor to use.
     * @return Array of all value names
     */
    public static native String[] enumValues(long key)
        throws Error;

     /**
     * Delete the Registry value
     * @param key The Registry key descriptor to use.
     * @param name The name of the value to delete
     * @return If the function succeeds, the return value is 0
     */
    public static native int deleteValue(long key, String name);

     /**
     * Delete the Registry subkey
     * @param root Root key, one of HKEY_*
     * @param name Subkey to delete
     * @param onlyIfEmpty If true will not delete a key if
     *                    it contains any subkeys or values
     * @return If the function succeeds, the return value is 0
     */
    public static native int deleteKey(int root, String name,
                                       boolean onlyIfEmpty);


}
