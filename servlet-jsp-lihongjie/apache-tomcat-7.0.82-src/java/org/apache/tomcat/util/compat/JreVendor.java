/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tomcat.util.compat;

import java.util.Locale;

public class JreVendor {

    static {
        /**
         * There are a few places where Tomcat either accesses JVM internals
         * (e.g. the memory leak protection) or where feature support varies
         * between JVMs (e.g. SPNEGO). These flags exist to enable Tomcat to
         * adjust its behaviour based on the vendor of the JVM. In an ideal
         * world this code would not exist.
         */
        String vendor = System.getProperty("java.vendor", "");
        vendor = vendor.toLowerCase(Locale.ENGLISH);

        if (vendor.startsWith("oracle") || vendor.startsWith("sun")) {
            IS_ORACLE_JVM = true;
            IS_IBM_JVM = false;
        } else if (vendor.contains("ibm")) {
            IS_ORACLE_JVM = false;
            IS_IBM_JVM = true;
        } else {
            IS_ORACLE_JVM = false;
            IS_IBM_JVM = false;
        }
    }

    public static final boolean IS_ORACLE_JVM;

    public static final boolean IS_IBM_JVM;
}
