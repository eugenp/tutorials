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
package org.apache.catalina.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class TesterHttpServletRequest implements HttpServletRequest {

    private Map<String, Object> attributes = new HashMap<String,Object>();
    private Map<String, List<String>> headers =
            new HashMap<String,List<String>>();
    private String method;
    private String scheme;
    private String serverName;
    private int serverPort;
    private String contentType;

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attributes.keySet());
    }

    @Override
    public String getCharacterEncoding() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public void setCharacterEncoding(String env)
            throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getParameter(String name) {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public Enumeration<String> getParameterNames() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String[] getParameterValues(String name) {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public Map<String,String[]> getParameterMap() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getProtocol() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }


    @Override
    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public BufferedReader getReader() throws IOException {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getRemoteAddr() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getRemoteHost() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public void setAttribute(String name, Object o) {
        attributes.put(name, o);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    @Override
    public Locale getLocale() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public Enumeration<Locale> getLocales() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isSecure() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getRealPath(String path) {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public int getRemotePort() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getLocalName() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getLocalAddr() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public int getLocalPort() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getAuthType() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public Cookie[] getCookies() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public long getDateHeader(String name) {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getHeader(String name) {
        List<String> list = headers.get(name);
        if (list != null) {
            return list.get(0);
            // return CorsFilter.join(new HashSet<>(list), ",");
        }
        return null;
    }

    public void setHeader(String name, String value) {
        List<String> values = new ArrayList<String>();
        values.add(value);
        headers.put(name, values);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(headers.keySet());
    }

    @Override
    public int getIntHeader(String name) {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getPathInfo() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getPathTranslated() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getContextPath() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getQueryString() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getRemoteUser() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isUserInRole(String role) {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public Principal getUserPrincipal() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getRequestedSessionId() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getRequestURI() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public StringBuffer getRequestURL() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getServletPath() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public HttpSession getSession(boolean create) {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public HttpSession getSession() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isRequestedSessionIdValid() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {

        throw new RuntimeException("Not implemented");
    }

    @Override
    public ServletContext getServletContext() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest,
            ServletResponse servletResponse) throws IllegalStateException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isAsyncStarted() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isAsyncSupported() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public AsyncContext getAsyncContext() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public DispatcherType getDispatcherType() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean authenticate(HttpServletResponse response)
            throws IOException, ServletException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void login(String username, String password)
            throws ServletException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void logout() throws ServletException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        throw new RuntimeException("Not implemented");
    }
}
