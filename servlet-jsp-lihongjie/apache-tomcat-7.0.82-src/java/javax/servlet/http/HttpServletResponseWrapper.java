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

import javax.servlet.ServletResponseWrapper;

/**
 * Provides a convenient implementation of the HttpServletResponse interface
 * that can be subclassed by developers wishing to adapt the response from a
 * Servlet. This class implements the Wrapper or Decorator pattern. Methods
 * default to calling through to the wrapped response object.
 * 
 * @author Various
 * @since v 2.3
 * @see javax.servlet.http.HttpServletResponse
 */
public class HttpServletResponseWrapper extends ServletResponseWrapper
        implements HttpServletResponse {

    /**
     * Constructs a response adaptor wrapping the given response.
     * 
     * @throws java.lang.IllegalArgumentException
     *             if the response is null
     */
    public HttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    private HttpServletResponse _getHttpServletResponse() {
        return (HttpServletResponse) super.getResponse();
    }

    /**
     * The default behavior of this method is to call addCookie(Cookie cookie)
     * on the wrapped response object.
     */
    @Override
    public void addCookie(Cookie cookie) {
        this._getHttpServletResponse().addCookie(cookie);
    }

    /**
     * The default behavior of this method is to call containsHeader(String
     * name) on the wrapped response object.
     */
    @Override
    public boolean containsHeader(String name) {
        return this._getHttpServletResponse().containsHeader(name);
    }

    /**
     * The default behavior of this method is to call encodeURL(String url) on
     * the wrapped response object.
     */
    @Override
    public String encodeURL(String url) {
        return this._getHttpServletResponse().encodeURL(url);
    }

    /**
     * The default behavior of this method is to return encodeRedirectURL(String
     * url) on the wrapped response object.
     */
    @Override
    public String encodeRedirectURL(String url) {
        return this._getHttpServletResponse().encodeRedirectURL(url);
    }

    /**
     * The default behavior of this method is to call encodeUrl(String url) on
     * the wrapped response object.
     * 
     * @deprecated As of Version 3.0 of the Java Servlet API
     */
    @Override
    @SuppressWarnings("dep-ann")
    // Spec API does not use @Deprecated
    public String encodeUrl(String url) {
        return this._getHttpServletResponse().encodeUrl(url);
    }

    /**
     * The default behavior of this method is to return encodeRedirectUrl(String
     * url) on the wrapped response object.
     * 
     * @deprecated As of Version 3.0 of the Java Servlet API
     */
    @Override
    @SuppressWarnings("dep-ann")
    // Spec API does not use @Deprecated
    public String encodeRedirectUrl(String url) {
        return this._getHttpServletResponse().encodeRedirectUrl(url);
    }

    /**
     * The default behavior of this method is to call sendError(int sc, String
     * msg) on the wrapped response object.
     */
    @Override
    public void sendError(int sc, String msg) throws IOException {
        this._getHttpServletResponse().sendError(sc, msg);
    }

    /**
     * The default behavior of this method is to call sendError(int sc) on the
     * wrapped response object.
     */
    @Override
    public void sendError(int sc) throws IOException {
        this._getHttpServletResponse().sendError(sc);
    }

    /**
     * The default behavior of this method is to return sendRedirect(String
     * location) on the wrapped response object.
     */
    @Override
    public void sendRedirect(String location) throws IOException {
        this._getHttpServletResponse().sendRedirect(location);
    }

    /**
     * The default behavior of this method is to call setDateHeader(String name,
     * long date) on the wrapped response object.
     */
    @Override
    public void setDateHeader(String name, long date) {
        this._getHttpServletResponse().setDateHeader(name, date);
    }

    /**
     * The default behavior of this method is to call addDateHeader(String name,
     * long date) on the wrapped response object.
     */
    @Override
    public void addDateHeader(String name, long date) {
        this._getHttpServletResponse().addDateHeader(name, date);
    }

    /**
     * The default behavior of this method is to return setHeader(String name,
     * String value) on the wrapped response object.
     */
    @Override
    public void setHeader(String name, String value) {
        this._getHttpServletResponse().setHeader(name, value);
    }

    /**
     * The default behavior of this method is to return addHeader(String name,
     * String value) on the wrapped response object.
     */
    @Override
    public void addHeader(String name, String value) {
        this._getHttpServletResponse().addHeader(name, value);
    }

    /**
     * The default behavior of this method is to call setIntHeader(String name,
     * int value) on the wrapped response object.
     */
    @Override
    public void setIntHeader(String name, int value) {
        this._getHttpServletResponse().setIntHeader(name, value);
    }

    /**
     * The default behavior of this method is to call addIntHeader(String name,
     * int value) on the wrapped response object.
     */
    @Override
    public void addIntHeader(String name, int value) {
        this._getHttpServletResponse().addIntHeader(name, value);
    }

    /**
     * The default behavior of this method is to call setStatus(int sc) on the
     * wrapped response object.
     */
    @Override
    public void setStatus(int sc) {
        this._getHttpServletResponse().setStatus(sc);
    }

    /**
     * The default behavior of this method is to call setStatus(int sc, String
     * sm) on the wrapped response object.
     * 
     * @deprecated As of Version 3.0 of the Java Servlet API
     */
    @Override
    @SuppressWarnings("dep-ann")
    // Spec API does not use @Deprecated
    public void setStatus(int sc, String sm) {
        this._getHttpServletResponse().setStatus(sc, sm);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default implementation is to call
     * {@link HttpServletResponse#getStatus()}
     * on the wrapper {@link HttpServletResponse}.
     *
     * @since Servlet 3.0
     */
    @Override
    public int getStatus() {
        return this._getHttpServletResponse().getStatus();
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default implementation is to call
     * {@link HttpServletResponse#getHeader(String)}
     * on the wrapper {@link HttpServletResponse}.
     *
     * @since Servlet 3.0
     */
    @Override
    public String getHeader(String name) {
        return this._getHttpServletResponse().getHeader(name);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default implementation is to call
     * {@link HttpServletResponse#getHeaders(String)}
     * on the wrapper {@link HttpServletResponse}.
     *
     * @since Servlet 3.0
     */
    @Override
    public Collection<String> getHeaders(String name) {
        return this._getHttpServletResponse().getHeaders(name);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The default implementation is to call
     * {@link HttpServletResponse#getHeaderNames()}
     * on the wrapper {@link HttpServletResponse}.
     *
     * @since Servlet 3.0
     */
    @Override
    public Collection<String> getHeaderNames() {
        return this._getHttpServletResponse().getHeaderNames();
    }
}
