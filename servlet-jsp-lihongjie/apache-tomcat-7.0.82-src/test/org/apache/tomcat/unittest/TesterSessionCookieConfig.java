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
package org.apache.tomcat.unittest;

import javax.servlet.SessionCookieConfig;

public class TesterSessionCookieConfig implements SessionCookieConfig {

    private String name;
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setDomain(String domain) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getDomain() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPath(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPath() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setComment(String comment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getComment() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHttpOnly(boolean httpOnly) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isHttpOnly() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSecure(boolean secure) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSecure() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setMaxAge(int MaxAge) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getMaxAge() {
        throw new UnsupportedOperationException();
    }
}
