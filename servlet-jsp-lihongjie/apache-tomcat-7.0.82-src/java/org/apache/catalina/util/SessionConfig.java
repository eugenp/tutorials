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
package org.apache.catalina.util;

import javax.servlet.SessionCookieConfig;

import org.apache.catalina.Context;

public class SessionConfig {

    private static final String DEFAULT_SESSION_COOKIE_NAME = "JSESSIONID";
    private static final String DEFAULT_SESSION_PARAMETER_NAME = "jsessionid";

    /**
     * Determine the name to use for the session cookie for the provided
     * context.
     * @param context
     */
    public static String getSessionCookieName(Context context) {

        String result = getConfiguredSessionCookieName(context);

        if (result == null) {
            result = DEFAULT_SESSION_COOKIE_NAME;
        }

        return result;
    }

    /**
     * Determine the name to use for the session cookie for the provided
     * context.
     * @param context
     */
    public static String getSessionUriParamName(Context context) {

        String result = getConfiguredSessionCookieName(context);

        if (result == null) {
            result = DEFAULT_SESSION_PARAMETER_NAME;
        }

        return result;
    }


    private static String getConfiguredSessionCookieName(Context context) {

        // Priority is:
        // 1. Cookie name defined in context
        // 2. Cookie name configured for app
        // 3. Default defined by spec
        if (context != null) {
            String cookieName = context.getSessionCookieName();
            if (cookieName != null && cookieName.length() > 0) {
                return cookieName;
            }

            SessionCookieConfig scc =
                context.getServletContext().getSessionCookieConfig();
            cookieName = scc.getName();
            if (cookieName != null && cookieName.length() > 0) {
                return cookieName;
            }
        }

        return null;
    }


    private SessionConfig() {
        // Utility class. Hide default constructor.
    }
}
