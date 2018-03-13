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
package javax.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Provides a convenient implementation of the ServletResponse interface that
 * can be subclassed by developers wishing to adapt the response from a Servlet.
 * This class implements the Wrapper or Decorator pattern. Methods default to
 * calling through to the wrapped response object.
 * 
 * @author Various
 * @since v 2.3
 * @see javax.servlet.ServletResponse
 */
public class ServletResponseWrapper implements ServletResponse {
    private ServletResponse response;

    /**
     * Creates a ServletResponse adaptor wrapping the given response object.
     * 
     * @throws java.lang.IllegalArgumentException
     *             if the response is null.
     */
    public ServletResponseWrapper(ServletResponse response) {
        if (response == null) {
            throw new IllegalArgumentException("Response cannot be null");
        }
        this.response = response;
    }

    /**
     * Return the wrapped ServletResponse object.
     */
    public ServletResponse getResponse() {
        return this.response;
    }

    /**
     * Sets the response being wrapped.
     * 
     * @throws java.lang.IllegalArgumentException
     *             if the response is null.
     */
    public void setResponse(ServletResponse response) {
        if (response == null) {
            throw new IllegalArgumentException("Response cannot be null");
        }
        this.response = response;
    }

    /**
     * The default behavior of this method is to call
     * setCharacterEncoding(String charset) on the wrapped response object.
     * 
     * @since 2.4
     */
    @Override
    public void setCharacterEncoding(String charset) {
        this.response.setCharacterEncoding(charset);
    }

    /**
     * The default behavior of this method is to return getCharacterEncoding()
     * on the wrapped response object.
     */
    @Override
    public String getCharacterEncoding() {
        return this.response.getCharacterEncoding();
    }

    /**
     * The default behavior of this method is to return getOutputStream() on the
     * wrapped response object.
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return this.response.getOutputStream();
    }

    /**
     * The default behavior of this method is to return getWriter() on the
     * wrapped response object.
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        return this.response.getWriter();
    }

    /**
     * The default behavior of this method is to call setContentLength(int len)
     * on the wrapped response object.
     */
    @Override
    public void setContentLength(int len) {
        this.response.setContentLength(len);
    }

    /**
     * The default behavior of this method is to call setContentType(String
     * type) on the wrapped response object.
     */
    @Override
    public void setContentType(String type) {
        this.response.setContentType(type);
    }

    /**
     * The default behavior of this method is to return getContentType() on the
     * wrapped response object.
     * 
     * @since 2.4
     */
    @Override
    public String getContentType() {
        return this.response.getContentType();
    }

    /**
     * The default behavior of this method is to call setBufferSize(int size) on
     * the wrapped response object.
     */
    @Override
    public void setBufferSize(int size) {
        this.response.setBufferSize(size);
    }

    /**
     * The default behavior of this method is to return getBufferSize() on the
     * wrapped response object.
     */
    @Override
    public int getBufferSize() {
        return this.response.getBufferSize();
    }

    /**
     * The default behavior of this method is to call flushBuffer() on the
     * wrapped response object.
     */
    @Override
    public void flushBuffer() throws IOException {
        this.response.flushBuffer();
    }

    /**
     * The default behavior of this method is to return isCommitted() on the
     * wrapped response object.
     */
    @Override
    public boolean isCommitted() {
        return this.response.isCommitted();
    }

    /**
     * The default behavior of this method is to call reset() on the wrapped
     * response object.
     */
    @Override
    public void reset() {
        this.response.reset();
    }

    /**
     * The default behavior of this method is to call resetBuffer() on the
     * wrapped response object.
     */
    @Override
    public void resetBuffer() {
        this.response.resetBuffer();
    }

    /**
     * The default behavior of this method is to call setLocale(Locale loc) on
     * the wrapped response object.
     */
    @Override
    public void setLocale(Locale loc) {
        this.response.setLocale(loc);
    }

    /**
     * The default behavior of this method is to return getLocale() on the
     * wrapped response object.
     */
    @Override
    public Locale getLocale() {
        return this.response.getLocale();
    }

    /**
     * @param wrapped
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    public boolean isWrapperFor(ServletResponse wrapped) {
        if (response == wrapped) {
            return true;
        }
        if (response instanceof ServletResponseWrapper) {
            return ((ServletResponseWrapper) response).isWrapperFor(wrapped);
        }
        return false;
    }

    /**
     * @param wrappedType
     * @since Servlet 3.0 TODO SERVLET3 - Add comments
     */
    @SuppressWarnings("unchecked")
    // Spec API does not use generics
    public boolean isWrapperFor(@SuppressWarnings("rawtypes") Class wrappedType) {
        if (wrappedType.isAssignableFrom(response.getClass())) {
            return true;
        }
        if (response instanceof ServletResponseWrapper) {
            return ((ServletResponseWrapper) response).isWrapperFor(wrappedType);
        }
        return false;
    }

}
