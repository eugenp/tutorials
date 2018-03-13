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
package org.apache.catalina.filters;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;

/**
 * Mock HttpServletResponse
 */
public class TesterHttpServletResponse implements HttpServletResponse {

    private PrintWriter pw;
    private List<String> headerNames = new ArrayList<String>();
    private List<String> headerValues = new ArrayList<String>();
    private int status;

    public TesterHttpServletResponse() {
        // NOOP
    }


    @Override
    public PrintWriter getWriter() throws IOException {
        if (pw == null) {
            pw = new PrintWriter(new StringWriter());
        }
        return pw;
    }


    @Override
    public String getHeader(String name) {
        int index = headerNames.indexOf(name);
        if (index != -1) {
            return headerValues.get(index);
        }
        return null;
    }


    @Override
    public void setHeader(String name, String value) {
        int index = headerNames.indexOf(name);
        if (index != -1) {
            headerValues.set(index, value);
        } else {
            headerNames.add(name);
            headerValues.add(value);
        }
    }


    @Override
    public void addHeader(String name, String value) {
        headerNames.add(name);
        headerValues.add(value);
    }


    @Override
    public int getStatus() {
        return status;
    }


    @Override
    public void setStatus(int status) {
        this.status = status;
    }


    public void setAppCommitted(
            @SuppressWarnings("unused") boolean appCommitted) {/* NOOP */}
    public boolean isAppCommitted() { return false; }
    public Connector getConnector() { return null; }
    public void setConnector(@SuppressWarnings("unused") Connector connector) {
        // NOOP
    }
    public int getContentCount() { return -1; }
    public Context getContext() { return null; }
    public void setContext(@SuppressWarnings("unused") Context context) {
        // NOOP
    }
    public boolean getIncluded() { return false; }
    public void setIncluded(@SuppressWarnings("unused") boolean included) {
        // NOOP
    }
    public Request getRequest() { return null; }
    public void setRequest(@SuppressWarnings("unused") Request request) {
        // NOOP
    }
    public ServletResponse getResponse() { return null; }
    public OutputStream getStream() { return null; }
    public void setStream(@SuppressWarnings("unused") OutputStream stream) {
        // NOOP
    }
    public void setSuspended(@SuppressWarnings("unused") boolean suspended) {
        // NOOP
    }
    public boolean isSuspended() { return false; }
    public void setError() {/* NOOP */}
    public boolean isError() { return false; }
    /**
     * @throws IOException
     */
    public ServletOutputStream createOutputStream() throws IOException {
        return null;
    }
    /**
     * @throws IOException
     */
    public void finishResponse() throws IOException {/* NOOP */}
    public int getContentLength() { return -1; }
    @Override
    public String getContentType() { return null; }
    public PrintWriter getReporter() { return null; }
    public void recycle() {/* NOOP */}
    /**
     * @param b
     * @throws IOException
     */
    public void write(int b) throws IOException {
        // NOOP
    }
    /**
     * @param b
     * @throws IOException
     */
    public void write(byte b[]) throws IOException {
        // NOOP
    }
    /**
     * @param b
     * @param off
     * @param len
     * @throws IOException
     */
    public void write(byte b[], int off, int len) throws IOException {
        // NOOP
    }
    @Override
    public void flushBuffer() throws IOException {/* NOOP */}
    @Override
    public int getBufferSize() { return -1; }
    @Override
    public String getCharacterEncoding() { return null; }
    @Override
    public void setCharacterEncoding(String charEncoding) {/* NOOP */}
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }
    @Override
    public Locale getLocale() { return null; }
    @Override
    public boolean isCommitted() { return false; }
    @Override
    public void reset() {/* NOOP */}
    @Override
    public void resetBuffer() {/* NOOP */}
    @Override
    public void setBufferSize(int size) {/* NOOP */}
    @Override
    public void setContentLength(int length) {/* NOOP */}
    @Override
    public void setContentType(String type) {/* NOOP */}
    @Override
    public void setLocale(Locale locale) {/* NOOP */}
    @Override
    public Collection<String> getHeaderNames() { return null; }
    @Override
    public Collection<String> getHeaders(String name) { return null; }
    public String getMessage() { return null; }
    public void reset(@SuppressWarnings("unused") int status,
            @SuppressWarnings("unused") String message) {/* NOOP */}
    @Override
    public void addCookie(Cookie cookie) {/* NOOP */}
    @Override
    public void addDateHeader(String name, long value) {/* NOOP */}
    @Override
    public void addIntHeader(String name, int value) {/* NOOP */}
    @Override
    public boolean containsHeader(String name) { return false; }
    @Override
    public String encodeRedirectURL(String url) { return null; }
    /** @deprecated */
    @Override
    @Deprecated
    public String encodeRedirectUrl(String url) { return null; }
    @Override
    public String encodeURL(String url) { return null; }
    /** @deprecated */
    @Override
    @Deprecated
    public String encodeUrl(String url) { return null; }
    /**
     *
     * @throws IOException
     */
    public void sendAcknowledgement() throws IOException {/* NOOP */}
    @Override
    public void sendError(int status) throws IOException {/* NOOP */}
    @Override
    public void sendError(int status, String message) throws IOException {
        // NOOP
    }
    @Override
    public void sendRedirect(String location) throws IOException {/* NOOP */}
    @Override
    public void setDateHeader(String name, long value) {/* NOOP */}
    @Override
    public void setIntHeader(String name, int value) {/* NOOP */}
    /** @deprecated */
    @Override
    @Deprecated
    public void setStatus(int status, String message) {/* NOOP */}
}
