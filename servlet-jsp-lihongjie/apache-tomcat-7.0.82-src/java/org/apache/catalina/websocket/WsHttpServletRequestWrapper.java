/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
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

import org.apache.tomcat.util.res.StringManager;

/**
 * Wrapper for the HttpServletRequest object that allows the underlying request
 * object to be invalidated.
 * 
 * @deprecated  Replaced by the JSR356 WebSocket 1.1 implementation and will be
 *              removed in Tomcat 8.0.x.  
 */
@Deprecated
public class WsHttpServletRequestWrapper implements HttpServletRequest {

    private static final StringManager sm =
            StringManager.getManager(Constants.Package);

    private HttpServletRequest request;

    public WsHttpServletRequestWrapper(HttpServletRequest request) {
        this.request = request;
    }

    private HttpServletRequest getRequest() {
        if (request == null) {
            throw new IllegalStateException(sm.getString("wrapper.invalid"));
        }
        return request;
    }

    protected void invalidate() {
        request = null;
    }

    @Override
    public Object getAttribute(String name) {
        return getRequest().getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return getRequest().getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        return getRequest().getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String env)
            throws UnsupportedEncodingException {
        getRequest().setCharacterEncoding(env);
    }

    @Override
    public int getContentLength() {
        return getRequest().getContentLength();
    }

    @Override
    public String getContentType() {
        return getRequest().getContentType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return getRequest().getInputStream();
    }

    @Override
    public String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return getRequest().getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        return getRequest().getParameterValues(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return getRequest().getParameterMap();
    }

    @Override
    public String getProtocol() {
        return getRequest().getProtocol();
    }

    @Override
    public String getScheme() {
        return getRequest().getScheme();
    }

    @Override
    public String getServerName() {
        return getRequest().getServerName();
    }

    @Override
    public int getServerPort() {
        return getRequest().getServerPort();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return getRequest().getReader();
    }

    @Override
    public String getRemoteAddr() {
        return getRequest().getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return getRequest().getRemoteHost();
    }

    @Override
    public void setAttribute(String name, Object o) {
        getRequest().setAttribute(name, o);
    }

    @Override
    public void removeAttribute(String name) {
        getRequest().removeAttribute(name);
    }

    @Override
    public Locale getLocale() {
        return getRequest().getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return getRequest().getLocales();
    }

    @Override
    public boolean isSecure() {
        return getRequest().isSecure();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return getRequest().getRequestDispatcher(path);
    }

    @Override
    @Deprecated
    public String getRealPath(String path) {
        return getRequest().getRealPath(path);
    }

    @Override
    public int getRemotePort() {
        return getRequest().getRemotePort();
    }

    @Override
    public String getLocalName() {
        return getRequest().getLocalName();
    }

    @Override
    public String getLocalAddr() {
        return getRequest().getLocalAddr();
    }

    @Override
    public int getLocalPort() {
        return getRequest().getLocalPort();
    }

    @Override
    public ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return getRequest().startAsync();
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest,
            ServletResponse servletResponse) throws IllegalStateException {
        return getRequest().startAsync(servletRequest, servletResponse);
    }

    @Override
    public boolean isAsyncStarted() {
        return getRequest().isAsyncStarted();
    }

    @Override
    public boolean isAsyncSupported() {
        return getRequest().isAsyncSupported();
    }

    @Override
    public AsyncContext getAsyncContext() {
        return getRequest().getAsyncContext();
    }

    @Override
    public DispatcherType getDispatcherType() {
        return getRequest().getDispatcherType();
    }

    @Override
    public String getAuthType() {
        return getRequest().getAuthType();
    }

    @Override
    public Cookie[] getCookies() {
        return getRequest().getCookies();
    }

    @Override
    public long getDateHeader(String name) {
        return getRequest().getDateHeader(name);
    }

    @Override
    public String getHeader(String name) {
        return getRequest().getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return getRequest().getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return getRequest().getHeaderNames();
    }

    @Override
    public int getIntHeader(String name) {
        return getRequest().getIntHeader(name);
    }

    @Override
    public String getMethod() {
        return getRequest().getMethod();
    }

    @Override
    public String getPathInfo() {
        return getRequest().getPathInfo();
    }

    @Override
    public String getPathTranslated() {
        return getRequest().getPathTranslated();
    }

    @Override
    public String getContextPath() {
        return getRequest().getContextPath();
    }

    @Override
    public String getQueryString() {
        return getRequest().getQueryString();
    }

    @Override
    public String getRemoteUser() {
        return getRequest().getRemoteUser();
    }

    @Override
    public boolean isUserInRole(String role) {
        return getRequest().isUserInRole(role);
    }

    @Override
    public Principal getUserPrincipal() {
        return getRequest().getUserPrincipal();
    }

    @Override
    public String getRequestedSessionId() {
        return getRequest().getRequestedSessionId();
    }

    @Override
    public String getRequestURI() {
        return getRequest().getRequestURI();
    }

    @Override
    public StringBuffer getRequestURL() {
        return getRequest().getRequestURL();
    }

    @Override
    public String getServletPath() {
        return getRequest().getServletPath();
    }

    @Override
    public HttpSession getSession(boolean create) {
        return getRequest().getSession(create);
    }

    @Override
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return getRequest().isRequestedSessionIdValid();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return getRequest().isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return getRequest().isRequestedSessionIdFromURL();
    }

    @Override
    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        return getRequest().isRequestedSessionIdFromUrl();
    }

    @Override
    public boolean authenticate(HttpServletResponse response)
            throws IOException, ServletException {
        return getRequest().authenticate(response);
    }

    @Override
    public void login(String username, String password) throws ServletException {
        getRequest().login(username, password);
    }

    @Override
    public void logout() throws ServletException {
        getRequest().logout();
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return getRequest().getParts();
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return getRequest().getPart(name);
    }
}
