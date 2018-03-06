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
package org.apache.tomcat.util.http;

import java.util.Locale;

public class ResponseUtil {

    private ResponseUtil() {
        // Hide default constructor as this is a utility class
    }

    public static Locale getLocaleFromLanguageHeader(String header) {
        if (header == null) {
            return null;
        }

        if (header.indexOf(',') > -1) {
            // Multiple values. RFC 2616 does not define a priority.
            // No way to select a Locale
            return null;
        }

        String tags[] = header.split("-");
        String primaryTag = tags[0];

        if (primaryTag.length() != 2) {
            // Not an ISO-639 language abbreviation. No way to determine Locale
            return null;
        }

        String firstSubTag = null;
        if (tags.length > 1) {
            if (tags[1].length() == 2) {
                // Hopefully an ISO-3166 country code
                firstSubTag = tags[1];
            }
        }

        if (firstSubTag == null) {
            return new Locale(primaryTag);
        } else {
            return new Locale(primaryTag, firstSubTag);
        }
    }
}
