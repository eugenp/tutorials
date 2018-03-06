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

package org.apache.catalina.core;

import javax.servlet.SessionCookieConfig;
import javax.servlet.http.Cookie;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.util.SessionConfig;
import org.apache.tomcat.util.res.StringManager;

public class ApplicationSessionCookieConfig implements SessionCookieConfig {

    /**
     * The string manager for this package.
     */
    private static final StringManager sm = StringManager
            .getManager(Constants.Package);

    private boolean httpOnly;
    private boolean secure;
    private int maxAge = -1;
    private String comment;
    private String domain;
    private String name;
    private String path;
    private StandardContext context;

    public ApplicationSessionCookieConfig(StandardContext context) {
        this.context = context;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public int getMaxAge() {
        return maxAge;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public boolean isHttpOnly() {
        return httpOnly;
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public void setComment(String comment) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(sm.getString(
                    "applicationSessionCookieConfig.ise", "comment",
                    context.getPath()));
        }
        this.comment = comment;
    }

    @Override
    public void setDomain(String domain) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(sm.getString(
                    "applicationSessionCookieConfig.ise", "domain name",
                    context.getPath()));
        }
        this.domain = domain;
    }

    @Override
    public void setHttpOnly(boolean httpOnly) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(sm.getString(
                    "applicationSessionCookieConfig.ise", "HttpOnly",
                    context.getPath()));
        }
        this.httpOnly = httpOnly;
    }

    @Override
    public void setMaxAge(int maxAge) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(sm.getString(
                    "applicationSessionCookieConfig.ise", "max age",
                    context.getPath()));
        }
        this.maxAge = maxAge;
    }

    @Override
    public void setName(String name) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(sm.getString(
                    "applicationSessionCookieConfig.ise", "name",
                    context.getPath()));
        }
        this.name = name;
    }

    @Override
    public void setPath(String path) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(sm.getString(
                    "applicationSessionCookieConfig.ise", "path",
                    context.getPath()));
        }
        this.path = path;
    }

    @Override
    public void setSecure(boolean secure) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(sm.getString(
                    "applicationSessionCookieConfig.ise", "secure",
                    context.getPath()));
        }
        this.secure = secure;
    }

    /**
     * Creates a new session cookie for the given session ID
     *
     * @param context     The Context for the web application
     * @param sessionId   The ID of the session for which the cookie will be
     *                    created
     * @param secure      Should session cookie be configured as secure
     */
    public static Cookie createSessionCookie(Context context,
            String sessionId, boolean secure) {

        SessionCookieConfig scc =
            context.getServletContext().getSessionCookieConfig();

        // NOTE: The priority order for session cookie configuration is:
        //       1. Context level configuration
        //       2. Values from SessionCookieConfig
        //       3. Defaults

        Cookie cookie = new Cookie(
                SessionConfig.getSessionCookieName(context), sessionId);
       
        // Just apply the defaults.
        cookie.setMaxAge(scc.getMaxAge());
        cookie.setComment(scc.getComment());
       
        if (context.getSessionCookieDomain() == null) {
            // Avoid possible NPE
            if (scc.getDomain() != null) {
                cookie.setDomain(scc.getDomain());
            }
        } else {
            cookie.setDomain(context.getSessionCookieDomain());
        }

        // Always set secure if the request is secure
        if (scc.isSecure() || secure) {
            cookie.setSecure(true);
        }

        // Always set httpOnly if the context is configured for that
        if (scc.isHttpOnly() || context.getUseHttpOnly()) {
            cookie.setHttpOnly(true);
        }
       
        String contextPath = context.getSessionCookiePath();
        if (contextPath == null || contextPath.length() == 0) {
            contextPath = scc.getPath();
        }
        if (contextPath == null || contextPath.length() == 0) {
            contextPath = context.getEncodedPath();
        }
        if (context.getSessionCookiePathUsesTrailingSlash()) {
            // Handle special case of ROOT context where cookies require a path of
            // '/' but the servlet spec uses an empty string
            // Also ensure the cookies for a context with a path of /foo don't get
            // sent for requests with a path of /foobar
            if (!contextPath.endsWith("/")) {
                contextPath = contextPath + "/";
            }
        } else {
            // Only handle special case of ROOT context where cookies require a
            // path of '/' but the servlet spec uses an empty string
            if (contextPath.length() == 0) {
                contextPath = "/";
            }
        }
        cookie.setPath(contextPath);

        return cookie;
    }


    /**
     * Determine the name to use for the session cookie for the provided
     * context.
     * @param context
     *
     * @deprecated  Replaced by
     *              {@link SessionConfig#getSessionCookieName(Context)}. This
     *              will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    public static String getSessionCookieName(Context context) {
        return SessionConfig.getSessionCookieName(context);
    }
    
    /**
     * Determine the name to use for the session cookie for the provided
     * context.
     * @param context
     *
     * @deprecated  Replaced by
     *              {@link SessionConfig#getSessionUriParamName(Context)}. This
     *              will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    public static String getSessionUriParamName(Context context) {
        return SessionConfig.getSessionUriParamName(context);
    }
}
