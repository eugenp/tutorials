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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Request;
import org.apache.catalina.session.StandardSession;

public class TesterRequest extends Request {

    private final TesterContext context;
    private final TesterServletContext servletContext;


    public TesterRequest() {
        this(false);
    }


    public TesterRequest(boolean withSession) {
        context = new TesterContext();
        servletContext = new TesterServletContext();
        context.setServletContext(servletContext);
        if (withSession) {
            Set<SessionTrackingMode> modes = new HashSet<SessionTrackingMode>();
            modes.add(SessionTrackingMode.URL);
            modes.add(SessionTrackingMode.COOKIE);
            servletContext.setSessionTrackingModes(modes);
            session = new StandardSession(null);
            session.setId("1234", false);
            session.setValid(true);
        }
    }


    @Override
    public String getScheme() {
        return "http";
    }

    @Override
    public String getServerName() {
        return "localhost";
    }

    @Override
    public int getServerPort() {
        return 8080;
    }


    @Override
    public String getRequestURI() {
        return "/level1/level2/foo.html";
    }

    @Override
    public String getDecodedRequestURI() {
        // Decoding not required
        return getRequestURI();
    }


    @Override
    public Context getContext() {
        return context;
    }


    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }


    private String method;
    public void setMethod(String method) {
        this.method = method;
    }
    @Override
    public String getMethod() {
        return method;
    }

    private final Map<String,List<String>> headers = new HashMap<String,List<String>>();
    public void addHeader(String name, String value) {
        List<String> values = headers.get(name);
        if (values == null) {
            values = new ArrayList<String>();
            headers.put(name, values);
        }
        values.add(value);
    }
    @Override
    public String getHeader(String name) {
        List<String> values = headers.get(name);
        if (values == null || values.size() == 0) {
            return null;
        }
        return values.get(0);
    }
    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> values = headers.get(name);
        if (values == null || values.size() == 0) {
            values = Collections.emptyList();
        }
        return Collections.enumeration(headers.get(name));
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(headers.keySet());
    }
}
