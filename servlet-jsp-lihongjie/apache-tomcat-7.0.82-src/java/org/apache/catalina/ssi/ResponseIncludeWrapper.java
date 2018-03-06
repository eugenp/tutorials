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

package org.apache.catalina.ssi;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.tomcat.util.ExceptionUtils;

/**
 * A HttpServletResponseWrapper, used from
 * <code>SSIServletExternalResolver</code>
 * 
 * @author Bip Thelin
 * @author David Becker
 */
public class ResponseIncludeWrapper extends HttpServletResponseWrapper {
    /**
     * The names of some headers we want to capture.
     */
    private static final String CONTENT_TYPE = "content-type";
    private static final String LAST_MODIFIED = "last-modified";
    private static final DateFormat RFC1123_FORMAT;
    private static final String RFC1123_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    protected long lastModified = -1;
    private String contentType = null;

    /**
     * Our ServletOutputStream
     */
    protected ServletOutputStream captureServletOutputStream;
    protected ServletOutputStream servletOutputStream;
    protected PrintWriter printWriter;
    
    private ServletContext context;
    private HttpServletRequest request;

    static {
        RFC1123_FORMAT = new SimpleDateFormat(RFC1123_PATTERN, Locale.US);
        RFC1123_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
    
    /**
     * Initialize our wrapper with the current HttpServletResponse and
     * ServletOutputStream.
     * 
     * @param context The servlet context
     * @param request The HttpServletResponse to use
     * @param response The response to use
     * @param captureServletOutputStream The ServletOutputStream to use
     */
    public ResponseIncludeWrapper(ServletContext context, 
            HttpServletRequest request, HttpServletResponse response,
            ServletOutputStream captureServletOutputStream) {
        super(response);
        this.context = context;
        this.request = request;
        this.captureServletOutputStream = captureServletOutputStream;
    }


    /**
     * Flush the servletOutputStream or printWriter ( only one will be non-null )
     * This must be called after a requestDispatcher.include, since we can't
     * assume that the included servlet flushed its stream.
     */
    public void flushOutputStreamOrWriter() throws IOException {
        if (servletOutputStream != null) {
            servletOutputStream.flush();
        }
        if (printWriter != null) {
            printWriter.flush();
        }
    }


    /**
     * Return a printwriter, throws and exception if a OutputStream already
     * been returned.
     * 
     * @return a PrintWriter object
     * @exception java.io.IOException
     *                if the outputstream already been called
     */
    @Override
    public PrintWriter getWriter() throws java.io.IOException {
        if (servletOutputStream == null) {
            if (printWriter == null) {
                setCharacterEncoding(getCharacterEncoding());
                printWriter = new PrintWriter(
                        new OutputStreamWriter(captureServletOutputStream,
                                               getCharacterEncoding()));
            }
            return printWriter;
        }
        throw new IllegalStateException();
    }


    /**
     * Return a OutputStream, throws and exception if a printwriter already
     * been returned.
     * 
     * @return a OutputStream object
     * @exception java.io.IOException
     *                if the printwriter already been called
     */
    @Override
    public ServletOutputStream getOutputStream() throws java.io.IOException {
        if (printWriter == null) {
            if (servletOutputStream == null) {
                servletOutputStream = captureServletOutputStream;
            }
            return servletOutputStream;
        }
        throw new IllegalStateException();
    }
    
    
    /**
     * Returns the value of the <code>last-modified</code> header field. The
     * result is the number of milliseconds since January 1, 1970 GMT.
     *
     * @return the date the resource referenced by this
     *   <code>ResponseIncludeWrapper</code> was last modified, or -1 if not
     *   known.                                                             
     */
    public long getLastModified() {                                                                                                                                                           
        if (lastModified == -1) {
            // javadocs say to return -1 if date not known, if you want another
            // default, put it here
            return -1;
        }
        return lastModified;
    }

    /**
     * Sets the value of the <code>last-modified</code> header field.
     *
     * @param lastModified The number of milliseconds since January 1, 1970 GMT.
     */
    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
        ((HttpServletResponse) getResponse()).setDateHeader(LAST_MODIFIED,
                lastModified);
    }

    /**
     * Returns the value of the <code>content-type</code> header field.
     *
     * @return the content type of the resource referenced by this
     *   <code>ResponseIncludeWrapper</code>, or <code>null</code> if not known.
     */
    @Override
    public String getContentType() {
        if (contentType == null) {
            String url = request.getRequestURI();
            String mime = context.getMimeType(url);
            if (mime != null) {
                setContentType(mime);
            } else {
                // return a safe value
                setContentType("application/x-octet-stream");
            }
        }
        return contentType;
    }
    
    /**
     * Sets the value of the <code>content-type</code> header field.
     *
     * @param mime a mime type
     */
    @Override
    public void setContentType(String mime) {
        contentType = mime;
        if (contentType != null) {
            getResponse().setContentType(contentType);
        }
    }


    @Override
    public void addDateHeader(String name, long value) {
        super.addDateHeader(name, value);
        String lname = name.toLowerCase(Locale.ENGLISH);
        if (lname.equals(LAST_MODIFIED)) {
            lastModified = value;
        }
    }

    @Override
    public void addHeader(String name, String value) {
        super.addHeader(name, value);
        String lname = name.toLowerCase(Locale.ENGLISH);
        if (lname.equals(LAST_MODIFIED)) {
            try {
                synchronized(RFC1123_FORMAT) {
                    lastModified = RFC1123_FORMAT.parse(value).getTime();
                }
            } catch (Throwable ignore) {
                ExceptionUtils.handleThrowable(ignore);
            }
        } else if (lname.equals(CONTENT_TYPE)) {
            contentType = value;
        }
    }

    @Override
    public void setDateHeader(String name, long value) {
        super.setDateHeader(name, value);
        String lname = name.toLowerCase(Locale.ENGLISH);
        if (lname.equals(LAST_MODIFIED)) {
            lastModified = value;
        }
    }

    @Override
    public void setHeader(String name, String value) {
        super.setHeader(name, value);
        String lname = name.toLowerCase(Locale.ENGLISH);
        if (lname.equals(LAST_MODIFIED)) {
            try {
                synchronized(RFC1123_FORMAT) {
                    lastModified = RFC1123_FORMAT.parse(value).getTime();
                }
            } catch (Throwable ignore) {
                ExceptionUtils.handleThrowable(ignore);
            }
        }
        else if (lname.equals(CONTENT_TYPE))
        {
            contentType = value;
        }
    }
}
