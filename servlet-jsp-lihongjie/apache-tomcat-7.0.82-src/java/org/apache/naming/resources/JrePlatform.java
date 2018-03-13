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
package org.apache.naming.resources;

import java.security.AccessController;
import java.security.PrivilegedAction;

public class JrePlatform {

    private static final String OS_NAME_PROPERTY = "os.name";
    private static final String OS_NAME_WINDOWS_PREFIX = "Windows";

    static {
        /*
         * There are a few places where a) the behaviour of the Java API depends
         * on the underlying platform and b) those behavioural differences have
         * an impact on Tomcat.
         *
         * Tomcat therefore needs to be able to determine the platform it is
         * running on to account for those differences.
         *
         * In an ideal world this code would not exist.
         */

        // This check is derived from the check in Apache Commons Lang
        String osName;
        if (System.getSecurityManager() == null) {
            osName = System.getProperty(OS_NAME_PROPERTY);
        } else {
            osName = AccessController.doPrivileged(
                    new PrivilegedAction<String>() {

                    @Override
                    public String run() {
                        return System.getProperty(OS_NAME_PROPERTY);
                    }
                });
        }

        IS_WINDOWS = osName.startsWith(OS_NAME_WINDOWS_PREFIX);
    }


    public static final boolean IS_WINDOWS;
}
