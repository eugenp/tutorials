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
package org.apache.catalina.valves;


/**
 * Manifest constants for the <code>org.apache.catalina.valves</code>
 * package.
 *
 * @author Craig R. McClanahan
 */

public final class Constants {

    public static final String Package = "org.apache.catalina.valves";

    // Constants for the AccessLogValve class
    public static final class AccessLog {
        public static final String COMMON_ALIAS = "common";
        public static final String COMMON_PATTERN = "%h %l %u %t \"%r\" %s %b";
        public static final String COMBINED_ALIAS = "combined";
        public static final String COMBINED_PATTERN = "%h %l %u %t \"%r\" %s %b \"%{Referer}i\" \"%{User-Agent}i\"";
    }

}
