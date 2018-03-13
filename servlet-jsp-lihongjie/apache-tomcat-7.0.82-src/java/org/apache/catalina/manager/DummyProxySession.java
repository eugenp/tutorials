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
package org.apache.catalina.manager;

import java.security.Principal;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.SessionListener;

public class DummyProxySession implements Session {

    private String sessionId;
    
    public DummyProxySession(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void access() {
        // NOOP
    }

    @Override
    public void addSessionListener(SessionListener listener) {
        // NOOP
    }

    @Override
    public void endAccess() {
        // NOOP
    }

    @Override
    public void expire() {
        // NOOP
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public long getCreationTime() {
        return 0;
    }

    @Override
    public long getCreationTimeInternal() {
        return 0;
    }

    @Override
    public String getId() {
        return sessionId;
    }

    @Override
    public String getIdInternal() {
        return sessionId;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public long getLastAccessedTimeInternal() {
        return 0;
    }

    @Override
    public Manager getManager() {
        return null;
    }

    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    public Object getNote(String name) {
        return null;
    }

    @Override
    public Iterator<String> getNoteNames() {
        return null;
    }

    @Override
    public Principal getPrincipal() {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public long getThisAccessedTime() {
        return 0;
    }

    @Override
    public long getThisAccessedTimeInternal() {
        return 0;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void recycle() {
        // NOOP
    }

    @Override
    public void removeNote(String name) {
        // NOOP
    }

    @Override
    public void removeSessionListener(SessionListener listener) {
        // NOOP
    }

    @Override
    public void setAuthType(String authType) {
        // NOOP
    }

    @Override
    public void setCreationTime(long time) {
        // NOOP
    }

    @Override
    public void setId(String id) {
        this.sessionId = id;
    }

    @Override
    public void setId(String id, boolean notify) {
        this.sessionId = id;
        // Ignore notify
    }

    @Override
    public void setManager(Manager manager) {
        // NOOP
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        // NOOP
    }

    @Override
    public void setNew(boolean isNew) {
        // NOOP
    }

    @Override
    public void setNote(String name, Object value) {
        // NOOP
    }

    @Override
    public void setPrincipal(Principal principal) {
        // NOOP
    }

    @Override
    public void setValid(boolean isValid) {
        // NOOP
    }

    @Override
    public boolean isAttributeDistributable(String name, Object value) {
        return false;
    }
}
