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

package org.apache.catalina.deploy;

import java.util.EnumSet;

import javax.servlet.SessionTrackingMode;

/**
 * Representation of a session configuration element for a web application,
 * as represented in a <code>&lt;session-config&gt;</code> element in the
 * deployment descriptor.
 */
public class SessionConfig {
    private Integer sessionTimeout;
    private String cookieName;
    private String cookieDomain;
    private String cookiePath;
    private String cookieComment;
    private Boolean cookieHttpOnly;
    private Boolean cookieSecure;
    private Integer cookieMaxAge;
    private EnumSet<SessionTrackingMode> sessionTrackingModes =
        EnumSet.noneOf(SessionTrackingMode.class);
    
    public Integer getSessionTimeout() {
        return sessionTimeout;
    }
    public void setSessionTimeout(String sessionTimeout) {
        this.sessionTimeout = Integer.valueOf(sessionTimeout);
    }
    
    public String getCookieName() {
        return cookieName;
    }
    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }
    
    public String getCookieDomain() {
        return cookieDomain;
    }
    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }
    
    public String getCookiePath() {
        return cookiePath;
    }
    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }
    
    public String getCookieComment() {
        return cookieComment;
    }
    public void setCookieComment(String cookieComment) {
        this.cookieComment = cookieComment;
    }
    
    public Boolean getCookieHttpOnly() {
        return cookieHttpOnly;
    }
    public void setCookieHttpOnly(String cookieHttpOnly) {
        this.cookieHttpOnly = Boolean.valueOf(cookieHttpOnly);
    }
    
    public Boolean getCookieSecure() {
        return cookieSecure;
    }
    public void setCookieSecure(String cookieSecure) {
        this.cookieSecure = Boolean.valueOf(cookieSecure);
    }
    
    public Integer getCookieMaxAge() {
        return cookieMaxAge;
    }
    public void setCookieMaxAge(String cookieMaxAge) {
        this.cookieMaxAge = Integer.valueOf(cookieMaxAge);
    }
    
    public EnumSet<SessionTrackingMode> getSessionTrackingModes() {
        return sessionTrackingModes;
    }
    public void addSessionTrackingMode(String sessionTrackingMode) {
        sessionTrackingModes.add(
                SessionTrackingMode.valueOf(sessionTrackingMode));
    }
    
}
