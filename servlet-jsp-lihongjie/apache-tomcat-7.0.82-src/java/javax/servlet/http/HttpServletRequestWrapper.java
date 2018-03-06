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
package javax.servlet.http;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletRequestWrapper;

/**
 * Provides a convenient implementation of the HttpServletRequest interface that
 * can be subclassed by developers wishing to adapt the request to a Servlet.
 * This class implements the Wrapper or Decorator pattern. Methods default to
 * calling through to the wrapped request object.
 * 
 * @see javax.servlet.http.HttpServletRequest
 * @since v 2.3
 */
public class HttpServletRequestWrapper extends ServletRequestWrapper implements
        HttpServletRequest {

    /**
     * Constructs a request object wrapping the given request.
     * 
     * @throws java.lang.IllegalArgumentException
     *             if the request is null
     */
    public HttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    private HttpServletRequest _getHttpServletRequest() {
        return (HttpServletRequest) super.getRequest();
    }

    /**
     * The default behavior of this method is to return getAuthType() on the
     * wrapped request object.
     */
    @Override
    public String getAuthType() {
        return this._getHttpServletRequest().getAuthType();
    }

    /**
     * The default behavior of this method is to return getCookies() on the
     * wrapped request object.
     */
    @Override
    public Cookie[] getCookies() {
        return this._getHttpServletRequest().getCookies();
    }

    /**
     * The default behavior of this method is to return getDateHeader(String
     * name) on the wrapped request object.
     */
    @Override
    public long getDateHeader(String name) {
        return this._getHttpServletRequest().getDateHeader(name);
    }

    /**
     * The default behavior of this method is to return getHeader(String name)
     * on the wrapped request object.
     */
    @Override
    public String getHeader(String name) {
        return this._getHttpServletRequest().getHeader(name);
    }

    /**
     * The default behavior of this method is to return getHeaders(String name)
     * on the wrapped request object.
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        return this._getHttpServletRequest().getHeaders(name);
    }

    /**
     * The default behavior of this method is to return getHeaderNames() on the
     * wrapped request object.
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return this._getHttpServletRequest().getHeaderNames();
    }

    /**
     * The default behavior of this method is to return getIntHeader(String
     * name) on the wrapped request object.
     */
    @Override
    public int getIntHeader(String name) {
        return this._getHttpServletRequest().getIntHeader(name);
    }

    /**
     * The default behavior of this method is to return getMethod() on the
     * wrapped request object.
     */
    @Override
    public String getMethod() {
        return this._getHttpServletRequest().getMethod();
    }

    /**
     * The default behavior of this method is to return getPathInfo() on the
     * wrapped request object.
     */
    @Override
    public String getPathInfo() {
        return this._getHttpServletRequest().getPathInfo();
    }

    /**
     * The default behavior of this method is to return getPathTranslated() on
     * the wrapped request object.
     */
    @Override
    public String getPathTranslated() {
        return this._getHttpServletRequest().getPathTranslated();
    }

    /**
     * The default behavior of this method is to return getContextPath() on the
     * wrapped request object.
     */
    @Override
    public String getContextPath() {
        return this._getHttpServletRequest().getContextPath();
    }

    /**
     * The default behavior of this method is to return getQueryString() on the
     * wrapped request object.
     */
    @Override
    public String getQueryString() {
        return this._getHttpServletRequest().getQueryString();
    }

    /**
     * The default behavior of this method is to return getRemoteUser() on the
     * wrapped request object.
     */
    @Override
    public String getRemoteUser() {
        return this._getHttpServletRequest().getRemoteUser();
    }

    /**
     * The default behavior of this method is to return isUserInRole(String
     * role) on the wrapped request object.
     */
    @Override
    public boolean isUserInRole(String role) {
        return this._getHttpServletRequest().isUserInRole(role);
    }

    /**
     * The default behavior of this method is to return getUserPrincipal() on
     * the wrapped request object.
     */
    @Override
    public java.security.Principal getUserPrincipal() {
        return this._getHttpServletRequest().getUserPrincipal();
    }

    /**
     * The default behavior of this method is to return getRequestedSessionId()
     * on the wrapped request object.
     */
    @Override
    public String getRequestedSessionId() {
        return this._getHttpServletRequest().getRequestedSessionId();
    }

    /**
     * The default behavior of this method is to return getRequestURI() on the
     * wrapped request object.
     */
    @Override
    public String getRequestURI() {
        return this._getHttpServletRequest().getRequestURI();
    }

    /**
     * The default behavior of this method is to return getRequestURL() on the
     * wrapped request object.
     */
    @Override
    public StringBuffer getRequestURL() {
        return this._getHttpServletRequest().getRequestURL();
    }

    /**
     * The default behavior of this method is to return getServletPath() on the
     * wrapped request object.
     */
    @Override
    public String getServletPath() {
        return this._getHttpServletRequest().getServletPath();
    }

    /**
     * The default behavior of this method is to return getSession(boolean
     * create) on the wrapped request object.
     */
    @Override
    public HttpSession getSession(boolean create) {
        return this._getHttpServletRequest().getSession(create);
    }

    /**
     * The default behavior of this method is to return getSession() on the
     * wrapped request object.
     */
    @Override
    public HttpSession getSession() {
        return this._getHttpServletRequest().getSession();
    }

    /**
     * The default behavior of this method is to return
     * isRequestedSessionIdValid() on the wrapped request object.
     */
    @Override
    public boolean isRequestedSessionIdValid() {
        return this._getHttpServletRequest().isRequestedSessionIdValid();
    }

    /**
     * The default behavior of this method is to return
     * isRequestedSessionIdFromCookie() on the wrapped request object.
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return this._getHttpServletRequest().isRequestedSessionIdFromCookie();
    }

    /**
     * The default behavior of this method is to return
     * isRequestedSessionIdFromURL() on the wrapped request object.
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {
        return this._getHttpServletRequest().isRequestedSessionIdFromURL();
    }

    /**
     * The default behavior of this method is to return
     * isRequestedSessionIdFromUrl() on the wrapped request object.
     * 
     * @deprecated As of Version 3.0 of the Java Servlet API
     */
    @Override
    @SuppressWarnings("dep-ann")
    // Spec API does not use @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        return this._getHttpServletRequest().isRequestedSessionIdFromUrl();
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default behavior of this method is to return
     * {@link HttpServletRequest#authenticate(HttpServletResponse)}
     * on the wrapped request object.
     *
     * @since Servlet 3.0
     */
    @Override
    public boolean authenticate(HttpServletResponse response)
            throws IOException, ServletException {
        return this._getHttpServletRequest().authenticate(response);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default behavior of this method is to return
     * {@link HttpServletRequest#login(String, String)}
     * on the wrapped request object.
     *
     * @since Servlet 3.0
     */
    @Override
    public void login(String username, String password) throws ServletException {
        this._getHttpServletRequest().login(username, password);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default behavior of this method is to return
     * {@link HttpServletRequest#logout()}
     * on the wrapped request object.
     *
     * @since Servlet 3.0
     */
    @Override
    public void logout() throws ServletException {
        this._getHttpServletRequest().logout();
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default behavior of this method is to return
     * {@link HttpServletRequest#getParts()}
     * on the wrapped request object.
     *
     * @since Servlet 3.0
     */
    @Override
    public Collection<Part> getParts() throws IllegalStateException,
            IOException, ServletException {
        return this._getHttpServletRequest().getParts();
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default behavior of this method is to return
     * {@link HttpServletRequest#getPart(String)}
     * on the wrapped request object.
     *
     * @since Servlet 3.0
     */
    @Override
    public Part getPart(String name) throws IllegalStateException, IOException,
            ServletException {
        return this._getHttpServletRequest().getPart(name);
    }
}
