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
package org.apache.catalina.connector;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletOutputStream;
import javax.servlet.SessionTrackingMode;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.Session;
import org.apache.catalina.Wrapper;
import org.apache.catalina.security.SecurityUtil;
import org.apache.catalina.util.DateTool;
import org.apache.catalina.util.RequestUtil;
import org.apache.catalina.util.SessionConfig;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.buf.CharChunk;
import org.apache.tomcat.util.buf.UEncoder;
import org.apache.tomcat.util.buf.UEncoder.SafeCharsSet;
import org.apache.tomcat.util.http.FastHttpDateFormat;
import org.apache.tomcat.util.http.MimeHeaders;
import org.apache.tomcat.util.http.ResponseUtil;
import org.apache.tomcat.util.http.ServerCookie;
import org.apache.tomcat.util.http.parser.MediaTypeCache;
import org.apache.tomcat.util.net.URL;
import org.apache.tomcat.util.res.StringManager;

/**
 * Wrapper object for the Coyote response.
 *
 * @author Remy Maucherat
 * @author Craig R. McClanahan
 */
public class Response implements HttpServletResponse {

    private static final Log log = LogFactory.getLog(Response.class);
    protected static final StringManager sm = StringManager.getManager(Response.class);

    private static final MediaTypeCache MEDIA_TYPE_CACHE = new MediaTypeCache(100);

    /**
     * Compliance with SRV.15.2.22.1. A call to Response.getWriter() if no
     * character encoding has been specified will result in subsequent calls to
     * Response.getCharacterEncoding() returning ISO-8859-1 and the Content-Type
     * response header will include a charset=ISO-8859-1 component.
     */
    private static final boolean ENFORCE_ENCODING_IN_GET_WRITER;

    static {
        // Ensure that URL is loaded for SM
        URL.isSchemeChar('c');

        ENFORCE_ENCODING_IN_GET_WRITER = Boolean.parseBoolean(
                System.getProperty("org.apache.catalina.connector.Response.ENFORCE_ENCODING_IN_GET_WRITER",
                        "true"));
    }

    /**
     * Descriptive information about this Response implementation.
     */
    protected static final String info = "org.apache.coyote.catalina.CoyoteResponse/1.0";


    // ----------------------------------------------------- Instance Variables

    /**
     * The date format we will use for creating date headers.
     */
    protected SimpleDateFormat format = null;


    // ------------------------------------------------------------- Properties


    /**
     * Associated Catalina connector.
     * @deprecated  Unused
     */
    @Deprecated
    protected Connector connector;

    /**
     * Return the Connector through which this Request was received.
     */
    @Deprecated
    public Connector getConnector() {
        return (this.connector);
    }

    /**
     * Set the Connector through which this Request was received.
     *
     * @param connector The new connector
     */
    public void setConnector(Connector connector) {
        this.connector = connector;
        if("AJP/1.3".equals(connector.getProtocol())) {
            // default size to size of one ajp-packet
            outputBuffer = new OutputBuffer(8184);
        } else {
            outputBuffer = new OutputBuffer();
        }
        outputStream = new CoyoteOutputStream(outputBuffer);
        writer = new CoyoteWriter(outputBuffer);
    }


    /**
     * Coyote response.
     */
    protected org.apache.coyote.Response coyoteResponse;

    /**
     * Set the Coyote response.
     *
     * @param coyoteResponse The Coyote response
     */
    public void setCoyoteResponse(org.apache.coyote.Response coyoteResponse) {
        this.coyoteResponse = coyoteResponse;
        outputBuffer.setResponse(coyoteResponse);
    }

    /**
     * Get the Coyote response.
     */
    public org.apache.coyote.Response getCoyoteResponse() {
        return (coyoteResponse);
    }


    /**
     * Return the Context within which this Request is being processed.
     */
    public Context getContext() {
        return (request.getContext());
    }

    /**
     * Set the Context within which this Request is being processed.  This
     * must be called as soon as the appropriate Context is identified, because
     * it identifies the value to be returned by <code>getContextPath()</code>,
     * and thus enables parsing of the request URI.
     *
     * @param context The newly associated Context
     */
    @Deprecated
    public void setContext(Context context) {
        request.setContext(context);
    }


    /**
     * The associated output buffer.
     */
    protected OutputBuffer outputBuffer;


    /**
     * The associated output stream.
     */
    protected CoyoteOutputStream outputStream;


    /**
     * The associated writer.
     */
    protected CoyoteWriter writer;


    /**
     * The application commit flag.
     */
    protected boolean appCommitted = false;


    /**
     * The included flag.
     */
    protected boolean included = false;


    /**
     * The characterEncoding flag
     */
    private boolean isCharacterEncodingSet = false;

    /**
     * With the introduction of async processing and the possibility of
     * non-container threads calling sendError() tracking the current error
     * state and ensuring that the correct error page is called becomes more
     * complicated. This state attribute helps by tracking the current error
     * state and informing callers that attempt to change state if the change
     * was successful or if another thread got there first.
     *
     * <pre>
     * The state machine is very simple:
     *
     * 0 - NONE
     * 1 - NOT_REPORTED
     * 2 - REPORTED
     *
     *
     *   -->---->-- >NONE
     *   |   |        |
     *   |   |        | setError()
     *   ^   ^        |
     *   |   |       \|/
     *   |   |-<-NOT_REPORTED
     *   |            |
     *   ^            | report()
     *   |            |
     *   |           \|/
     *   |----<----REPORTED
     * </pre>
     */
    private final AtomicInteger errorState = new AtomicInteger(0);


    /**
     * Using output stream flag.
     */
    protected boolean usingOutputStream = false;


    /**
     * Using writer flag.
     */
    protected boolean usingWriter = false;


    /**
     * URL encoder.
     */
    protected final UEncoder urlEncoder = new UEncoder(SafeCharsSet.WITH_SLASH);


    /**
     * Recyclable buffer to hold the redirect URL.
     */
    protected CharChunk redirectURLCC = new CharChunk();


    // --------------------------------------------------------- Public Methods


    /**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     */
    public void recycle() {

        outputBuffer.recycle();
        usingOutputStream = false;
        usingWriter = false;
        appCommitted = false;
        included = false;
        errorState.set(0);
        isCharacterEncodingSet = false;

        if (Globals.IS_SECURITY_ENABLED || Connector.RECYCLE_FACADES) {
            if (facade != null) {
                facade.clear();
                facade = null;
            }
            if (outputStream != null) {
                outputStream.clear();
                outputStream = null;
            }
            if (writer != null) {
                writer.clear();
                writer = null;
            }
        } else {
            writer.recycle();
        }

    }


    /**
     * Clear cached encoders (to save memory for Comet requests).
     */
    public void clearEncoders() {
        outputBuffer.clearEncoders();
    }


    // ------------------------------------------------------- Response Methods


    /**
     * Return the number of bytes the application has actually written to the
     * output stream. This excludes chunking, compression, etc. as well as
     * headers.
     */
    public long getContentWritten() {
        return outputBuffer.getContentWritten();
    }


    /**
     * Return the number of bytes the actually written to the socket. This
     * includes chunking, compression, etc. but excludes headers.
     */
    public long getBytesWritten(boolean flush) {
        if (flush) {
            try {
                outputBuffer.flush();
            } catch (IOException ioe) {
                // Ignore - the client has probably closed the connection
            }
        }
        return getCoyoteResponse().getBytesWritten(flush);
    }

    /**
     * Set the application commit flag.
     *
     * @param appCommitted The new application committed flag value
     */
    public void setAppCommitted(boolean appCommitted) {
        this.appCommitted = appCommitted;
    }


    /**
     * Application commit flag accessor.
     */
    public boolean isAppCommitted() {
        return (this.appCommitted || isCommitted() || isSuspended()
                || ((getContentLength() > 0)
                    && (getContentWritten() >= getContentLength())));
    }


    /**
     * Return the "processing inside an include" flag.
     */
    @Deprecated
    public boolean getIncluded() {
        return included;
    }


    /**
     * Set the "processing inside an include" flag.
     *
     * @param included <code>true</code> if we are currently inside a
     *  RequestDispatcher.include(), else <code>false</code>
     */
    @Deprecated
    public void setIncluded(boolean included) {
        this.included = included;
    }


    /**
     * Return descriptive information about this Response implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {
        return (info);
    }


    /**
     * The request with which this response is associated.
     */
    protected Request request = null;

    /**
     * Return the Request with which this Response is associated.
     */
    public org.apache.catalina.connector.Request getRequest() {
        return (this.request);
    }

    /**
     * Set the Request with which this Response is associated.
     *
     * @param request The new associated request
     */
    public void setRequest(org.apache.catalina.connector.Request request) {
        this.request = request;
    }


    /**
     * The facade associated with this response.
     */
    protected ResponseFacade facade = null;

    /**
     * Return the <code>ServletResponse</code> for which this object
     * is the facade.
     */
    public HttpServletResponse getResponse() {
        if (facade == null) {
            facade = new ResponseFacade(this);
        }
        return (facade);
    }


    /**
     * Return the output stream associated with this Response.
     */
    @Deprecated
    public OutputStream getStream() {
        if (outputStream == null) {
            outputStream = new CoyoteOutputStream(outputBuffer);
        }
        return outputStream;
    }


    /**
     * Set the suspended flag.
     *
     * @param suspended The new suspended flag value
     */
    public void setSuspended(boolean suspended) {
        outputBuffer.setSuspended(suspended);
    }


    /**
     * Suspended flag accessor.
     */
    public boolean isSuspended() {
        return outputBuffer.isSuspended();
    }


    /**
     * Closed flag accessor.
     */
    public boolean isClosed() {
        return outputBuffer.isClosed();
    }


    /**
     * Set the error flag.
     */
    public boolean setError() {
        boolean result = errorState.compareAndSet(0, 1);
        if (result) {
            Wrapper wrapper = getRequest().getWrapper();
            if (wrapper != null) {
                wrapper.incrementErrorCount();
            }
        }
        return result;
    }


    /**
     * Error flag accessor.
     */
    public boolean isError() {
        return errorState.get() > 0;
    }


    public boolean isErrorReportRequired() {
        return errorState.get() == 1;
    }


    public boolean setErrorReported() {
        return errorState.compareAndSet(1, 2);
    }


    /**
     * Create and return a ServletOutputStream to write the content
     * associated with this Response.
     *
     * @exception IOException if an input/output error occurs
     */
    @Deprecated
    public ServletOutputStream createOutputStream()
        throws IOException {
        // Probably useless
        if (outputStream == null) {
            outputStream = new CoyoteOutputStream(outputBuffer);
        }
        return outputStream;
    }


    /**
     * Perform whatever actions are required to flush and close the output
     * stream or writer, in a single operation.
     *
     * @exception IOException if an input/output error occurs
     */
    public void finishResponse() throws IOException {
        // Writing leftover bytes
        outputBuffer.close();
    }


    /**
     * Return the content length that was set or calculated for this Response.
     */
    public int getContentLength() {
        return getCoyoteResponse().getContentLength();
    }


    /**
     * Return the content type that was set or calculated for this response,
     * or <code>null</code> if no content type was set.
     */
    @Override
    public String getContentType() {
        return getCoyoteResponse().getContentType();
    }


    /**
     * Return a PrintWriter that can be used to render error messages,
     * regardless of whether a stream or writer has already been acquired.
     *
     * @return Writer which can be used for error reports. If the response is
     * not an error report returned using sendError or triggered by an
     * unexpected exception thrown during the servlet processing
     * (and only in that case), null will be returned if the response stream
     * has already been used.
     *
     * @exception IOException if an input/output error occurs
     */
    public PrintWriter getReporter() throws IOException {
        if (outputBuffer.isNew()) {
            outputBuffer.checkConverter();
            if (writer == null) {
                writer = new CoyoteWriter(outputBuffer);
            }
            return writer;
        } else {
            return null;
        }
    }


    // ------------------------------------------------ ServletResponse Methods


    /**
     * Flush the buffer and commit this response.
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void flushBuffer() throws IOException {
        outputBuffer.flush();
    }


    /**
     * Return the actual buffer size used for this Response.
     */
    @Override
    public int getBufferSize() {
        return outputBuffer.getBufferSize();
    }


    /**
     * Return the character encoding used for this Response.
     */
    @Override
    public String getCharacterEncoding() {
        return (getCoyoteResponse().getCharacterEncoding());
    }


    /**
     * Return the servlet output stream associated with this Response.
     *
     * @exception IllegalStateException if <code>getWriter</code> has
     *  already been called for this response
     * @exception IOException if an input/output error occurs
     */
    @Override
    public ServletOutputStream getOutputStream()
        throws IOException {

        if (usingWriter) {
            throw new IllegalStateException
                (sm.getString("coyoteResponse.getOutputStream.ise"));
        }

        usingOutputStream = true;
        if (outputStream == null) {
            outputStream = new CoyoteOutputStream(outputBuffer);
        }
        return outputStream;

    }


    /**
     * Return the Locale assigned to this response.
     */
    @Override
    public Locale getLocale() {
        return (getCoyoteResponse().getLocale());
    }


    /**
     * Return the writer associated with this Response.
     *
     * @exception IllegalStateException if <code>getOutputStream</code> has
     *  already been called for this response
     * @exception IOException if an input/output error occurs
     */
    @Override
    public PrintWriter getWriter()
        throws IOException {

        if (usingOutputStream) {
            throw new IllegalStateException
                (sm.getString("coyoteResponse.getWriter.ise"));
        }

        if (ENFORCE_ENCODING_IN_GET_WRITER) {
            /*
             * If the response's character encoding has not been specified as
             * described in <code>getCharacterEncoding</code> (i.e., the method
             * just returns the default value <code>ISO-8859-1</code>),
             * <code>getWriter</code> updates it to <code>ISO-8859-1</code>
             * (with the effect that a subsequent call to getContentType() will
             * include a charset=ISO-8859-1 component which will also be
             * reflected in the Content-Type response header, thereby satisfying
             * the Servlet spec requirement that containers must communicate the
             * character encoding used for the servlet response's writer to the
             * client).
             */
            setCharacterEncoding(getCharacterEncoding());
        }

        usingWriter = true;
        outputBuffer.checkConverter();
        if (writer == null) {
            writer = new CoyoteWriter(outputBuffer);
        }
        return writer;
    }


    /**
     * Has the output of this response already been committed?
     */
    @Override
    public boolean isCommitted() {
        return getCoyoteResponse().isCommitted();
    }


    /**
     * Clear any content written to the buffer.
     *
     * @exception IllegalStateException if this response has already
     *  been committed
     */
    @Override
    public void reset() {
        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        getCoyoteResponse().reset();
        outputBuffer.reset();
        usingOutputStream = false;
        usingWriter = false;
        isCharacterEncodingSet = false;
    }


    /**
     * Reset the data buffer but not any status or header information.
     *
     * @exception IllegalStateException if the response has already
     *  been committed
     */
    @Override
    public void resetBuffer() {
        resetBuffer(false);
    }


    /**
     * Reset the data buffer and the using Writer/Stream flags but not any
     * status or header information.
     *
     * @param resetWriterStreamFlags <code>true</code> if the internal
     *        <code>usingWriter</code>, <code>usingOutputStream</code>,
     *        <code>isCharacterEncodingSet</code> flags should also be reset
     *
     * @exception IllegalStateException if the response has already
     *  been committed
     */
    public void resetBuffer(boolean resetWriterStreamFlags) {

        if (isCommitted()) {
            throw new IllegalStateException
                (sm.getString("coyoteResponse.resetBuffer.ise"));
        }

        outputBuffer.reset(resetWriterStreamFlags);

        if(resetWriterStreamFlags) {
            usingOutputStream = false;
            usingWriter = false;
            isCharacterEncodingSet = false;
        }

    }


    /**
     * Set the buffer size to be used for this Response.
     *
     * @param size The new buffer size
     *
     * @exception IllegalStateException if this method is called after
     *  output has been committed for this response
     */
    @Override
    public void setBufferSize(int size) {

        if (isCommitted() || !outputBuffer.isNew()) {
            throw new IllegalStateException
                (sm.getString("coyoteResponse.setBufferSize.ise"));
        }

        outputBuffer.setBufferSize(size);

    }


    /**
     * Set the content length (in bytes) for this Response.
     *
     * @param length The new content length
     */
    @Override
    public void setContentLength(int length) {

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        getCoyoteResponse().setContentLength(length);

    }


    /**
     * Set the content type for this Response.
     *
     * @param type The new content type
     */
    @Override
    public void setContentType(String type) {

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        if (type == null) {
            getCoyoteResponse().setContentType(null);
            return;
        }

        String[] m = MEDIA_TYPE_CACHE.parse(type);
        if (m == null) {
            // Invalid - Assume no charset and just pass through whatever
            // the user provided.
            getCoyoteResponse().setContentTypeNoCharset(type);
            return;
        }

        getCoyoteResponse().setContentTypeNoCharset(m[0]);

        if (m[1] != null) {
            // Ignore charset if getWriter() has already been called
            if (!usingWriter) {
                getCoyoteResponse().setCharacterEncoding(m[1]);
                isCharacterEncodingSet = true;
            }
        }
    }


    /*
     * Overrides the name of the character encoding used in the body
     * of the request. This method must be called prior to reading
     * request parameters or reading input using getReader().
     *
     * @param charset String containing the name of the character encoding.
     */
    @Override
    public void setCharacterEncoding(String charset) {

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        // Ignore any call made after the getWriter has been invoked
        // The default should be used
        if (usingWriter) {
            return;
        }

        getCoyoteResponse().setCharacterEncoding(charset);
        isCharacterEncodingSet = true;
    }


    /**
     * Set the Locale that is appropriate for this response, including
     * setting the appropriate character encoding.
     *
     * @param locale The new locale
     */
    @Override
    public void setLocale(Locale locale) {

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        getCoyoteResponse().setLocale(locale);

        // Ignore any call made after the getWriter has been invoked.
        // The default should be used
        if (usingWriter) {
            return;
        }

        if (isCharacterEncodingSet) {
            return;
        }

        String charset = getContext().getCharset(locale);
        if (charset != null) {
            getCoyoteResponse().setCharacterEncoding(charset);
        }
    }


    // --------------------------------------------------- HttpResponse Methods


    @Override
    public String getHeader(String name) {
        return getCoyoteResponse().getMimeHeaders().getHeader(name);
    }


    @Override
    public Collection<String> getHeaderNames() {

        MimeHeaders headers = getCoyoteResponse().getMimeHeaders();
        int n = headers.size();
        List<String> result = new ArrayList<String>(n);
        for (int i = 0; i < n; i++) {
            result.add(headers.getName(i).toString());
        }
        return result;

    }


    @Override
    public Collection<String> getHeaders(String name) {

        Enumeration<String> enumeration =
                getCoyoteResponse().getMimeHeaders().values(name);
        Vector<String> result = new Vector<String>();
        while (enumeration.hasMoreElements()) {
            result.addElement(enumeration.nextElement());
        }
        return result;
    }


    /**
     * Return the error message that was set with <code>sendError()</code>
     * for this Response.
     */
    public String getMessage() {
        return getCoyoteResponse().getMessage();
    }


    @Override
    public int getStatus() {
        return getCoyoteResponse().getStatus();
    }


    /**
     * Reset this response, and specify the values for the HTTP status code
     * and corresponding message.
     *
     * @exception IllegalStateException if this response has already been
     *  committed
     */
    @Deprecated
    public void reset(int status, String message) {
        reset();
        setStatus(status, message);
    }


    // -------------------------------------------- HttpServletResponse Methods


    /**
     * Add the specified Cookie to those that will be included with
     * this Response.
     *
     * @param cookie Cookie to be added
     */
    @Override
    public void addCookie(final Cookie cookie) {

        // Ignore any call from an included servlet
        if (included || isCommitted()) {
            return;
        }

        final StringBuffer sb = generateCookieString(cookie);
        //if we reached here, no exception, cookie is valid
        // the header name is Set-Cookie for both "old" and v.1 ( RFC2109 )
        // RFC2965 is not supported by browsers and the Servlet spec
        // asks for 2109.
        addHeader("Set-Cookie", sb.toString());
    }

    /**
     * Special method for adding a session cookie as we should be overriding
     * any previous
     * @param cookie
     */
    public void addSessionCookieInternal(final Cookie cookie) {
        if (isCommitted()) {
            return;
        }

        String name = cookie.getName();
        final String headername = "Set-Cookie";
        final String startsWith = name + "=";
        final StringBuffer sb = generateCookieString(cookie);
        boolean set = false;
        MimeHeaders headers = getCoyoteResponse().getMimeHeaders();
        int n = headers.size();
        for (int i = 0; i < n; i++) {
            if (headers.getName(i).toString().equals(headername)) {
                if (headers.getValue(i).toString().startsWith(startsWith)) {
                    headers.getValue(i).setString(sb.toString());
                    set = true;
                }
            }
        }
        if (!set) {
            addHeader(headername, sb.toString());
        }


    }

    public StringBuffer generateCookieString(final Cookie cookie) {
        final StringBuffer sb = new StringBuffer();
        //web application code can receive a IllegalArgumentException
        //from the appendCookieValue invocation
        if (SecurityUtil.isPackageProtectionEnabled()) {
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                @Override
                public Void run(){
                    ServerCookie.appendCookieValue
                        (sb, cookie.getVersion(), cookie.getName(),
                         cookie.getValue(), cookie.getPath(),
                         cookie.getDomain(), cookie.getComment(),
                         cookie.getMaxAge(), cookie.getSecure(),
                         cookie.isHttpOnly());
                    return null;
                }
            });
        } else {
            ServerCookie.appendCookieValue
                (sb, cookie.getVersion(), cookie.getName(), cookie.getValue(),
                     cookie.getPath(), cookie.getDomain(), cookie.getComment(),
                     cookie.getMaxAge(), cookie.getSecure(),
                     cookie.isHttpOnly());
        }
        return sb;
    }


    /**
     * Add the specified date header to the specified value.
     *
     * @param name Name of the header to set
     * @param value Date value to be set
     */
    @Override
    public void addDateHeader(String name, long value) {

        if (name == null || name.length() == 0) {
            return;
        }

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        if (format == null) {
            format = new SimpleDateFormat(DateTool.HTTP_RESPONSE_DATE_HEADER,
                                          Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
        }

        addHeader(name, FastHttpDateFormat.formatDate(value, format));

    }


    /**
     * Add the specified header to the specified value.
     *
     * @param name Name of the header to set
     * @param value Value to be set
     */
    @Override
    public void addHeader(String name, String value) {

        if (name == null || name.length() == 0 || value == null) {
            return;
        }

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        char cc=name.charAt(0);
        if (cc=='C' || cc=='c') {
            if (checkSpecialHeader(name, value))
            return;
        }

        getCoyoteResponse().addHeader(name, value);
    }


    /**
     * An extended version of this exists in {@link org.apache.coyote.Response}.
     * This check is required here to ensure that the usingWriter checks in
     * {@link #setContentType(String)} and {@link #setLocale(Locale) are applied
     * since usingWriter is not visible to {@link org.apache.coyote.Response}
     *
     * Called from set/addHeader.
     * Return true if the header is special, no need to set the header.
     */
    private boolean checkSpecialHeader(String name, String value) {
        if (name.equalsIgnoreCase("Content-Type")) {
            setContentType(value);
            return true;
        }
        if (name.equalsIgnoreCase("Content-Language")) {
            Locale locale = ResponseUtil.getLocaleFromLanguageHeader(value);
            if (locale == null) {
                return false;
            } else {
                setLocale(locale);
                return true;
            }
        }
        return false;
    }


    /**
     * Add the specified integer header to the specified value.
     *
     * @param name Name of the header to set
     * @param value Integer value to be set
     */
    @Override
    public void addIntHeader(String name, int value) {

        if (name == null || name.length() == 0) {
            return;
        }

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        addHeader(name, "" + value);

    }


    /**
     * Has the specified header been set already in this response?
     *
     * @param name Name of the header to check
     */
    @Override
    public boolean containsHeader(String name) {
        // Need special handling for Content-Type and Content-Length due to
        // special handling of these in coyoteResponse
        char cc=name.charAt(0);
        if(cc=='C' || cc=='c') {
            if(name.equalsIgnoreCase("Content-Type")) {
                // Will return null if this has not been set
                return (getCoyoteResponse().getContentType() != null);
            }
            if(name.equalsIgnoreCase("Content-Length")) {
                // -1 means not known and is not sent to client
                return (getCoyoteResponse().getContentLengthLong() != -1);
            }
        }

        return getCoyoteResponse().containsHeader(name);
    }


    /**
     * Encode the session identifier associated with this response
     * into the specified redirect URL, if necessary.
     *
     * @param url URL to be encoded
     */
    @Override
    public String encodeRedirectURL(String url) {

        if (isEncodeable(toAbsolute(url))) {
            return (toEncoded(url, request.getSessionInternal().getIdInternal()));
        } else {
            return (url);
        }

    }


    /**
     * Encode the session identifier associated with this response
     * into the specified redirect URL, if necessary.
     *
     * @param url URL to be encoded
     *
     * @deprecated As of Version 2.1 of the Java Servlet API, use
     *  <code>encodeRedirectURL()</code> instead.
     */
    @Override
    @Deprecated
    public String encodeRedirectUrl(String url) {
        return (encodeRedirectURL(url));
    }


    /**
     * Encode the session identifier associated with this response
     * into the specified URL, if necessary.
     *
     * @param url URL to be encoded
     */
    @Override
    public String encodeURL(String url) {

        String absolute;
        try {
            absolute = toAbsolute(url);
        } catch (IllegalArgumentException iae) {
            // Relative URL
            return url;
        }

        if (isEncodeable(absolute)) {
            // W3c spec clearly said
            if (url.equalsIgnoreCase("")) {
                url = absolute;
            } else if (url.equals(absolute) && !hasPath(url)) {
                url += '/';
            }
            return (toEncoded(url, request.getSessionInternal().getIdInternal()));
        } else {
            return (url);
        }

    }


    /**
     * Encode the session identifier associated with this response
     * into the specified URL, if necessary.
     *
     * @param url URL to be encoded
     *
     * @deprecated As of Version 2.1 of the Java Servlet API, use
     *  <code>encodeURL()</code> instead.
     */
    @Override
    @Deprecated
    public String encodeUrl(String url) {
        return (encodeURL(url));
    }


    /**
     * Send an acknowledgment of a request.
     *
     * @exception IOException if an input/output error occurs
     */
    public void sendAcknowledgement()
        throws IOException {

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        getCoyoteResponse().acknowledge();
    }


    /**
     * Send an error response with the specified status and a
     * default message.
     *
     * @param status HTTP status code to send
     *
     * @exception IllegalStateException if this response has
     *  already been committed
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void sendError(int status) throws IOException {
        sendError(status, null);
    }


    /**
     * Send an error response with the specified status and message.
     *
     * @param status HTTP status code to send
     * @param message Corresponding message to send
     *
     * @exception IllegalStateException if this response has
     *  already been committed
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void sendError(int status, String message) throws IOException {

        if (isCommitted()) {
            throw new IllegalStateException
                (sm.getString("coyoteResponse.sendError.ise"));
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        setError();

        getCoyoteResponse().setStatus(status);
        getCoyoteResponse().setMessage(message);

        // Clear any data content that has been buffered
        resetBuffer();

        // Cause the response to be finished (from the application perspective)
        setSuspended(true);
    }


    /**
     * Send a temporary redirect to the specified redirect location URL.
     *
     * @param location Location URL to redirect to
     *
     * @exception IllegalStateException if this response has
     *  already been committed
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void sendRedirect(String location) throws IOException {
        sendRedirect(location, SC_FOUND);
    }


    /**
     * Internal method that allows a redirect to be sent with a status other
     * than {@link HttpServletResponse#SC_FOUND} (302). No attempt is made to
     * validate the status code.
     */
    public void sendRedirect(String location, int status) throws IOException {
        if (isCommitted()) {
            throw new IllegalStateException(sm.getString("coyoteResponse.sendRedirect.ise"));
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        // Clear any data content that has been buffered
        resetBuffer(true);

        // Generate a temporary redirect to the specified location
        try {
            String locationUri;
            // Relative redirects require HTTP/1.1
            if (getRequest().getCoyoteRequest().getSupportsRelativeRedirects() &&
                    getContext().getUseRelativeRedirects()) {
                locationUri = location;
            } else {
                locationUri = toAbsolute(location);
            }
            setStatus(status);
            setHeader("Location", locationUri);
            if (getContext().getSendRedirectBody()) {
                PrintWriter writer = getWriter();
                writer.print(sm.getString("coyoteResponse.sendRedirect.note",
                        RequestUtil.filter(locationUri)));
                flushBuffer();
            }
        } catch (IllegalArgumentException e) {
            log.warn(sm.getString("response.sendRedirectFail", location), e);
            setStatus(SC_NOT_FOUND);
        }

        // Cause the response to be finished (from the application perspective)
        setSuspended(true);
    }


    /**
     * Set the specified date header to the specified value.
     *
     * @param name Name of the header to set
     * @param value Date value to be set
     */
    @Override
    public void setDateHeader(String name, long value) {

        if (name == null || name.length() == 0) {
            return;
        }

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        if (format == null) {
            format = new SimpleDateFormat(DateTool.HTTP_RESPONSE_DATE_HEADER,
                                          Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
        }

        setHeader(name, FastHttpDateFormat.formatDate(value, format));
    }


    /**
     * Set the specified header to the specified value.
     *
     * @param name Name of the header to set
     * @param value Value to be set
     */
    @Override
    public void setHeader(String name, String value) {

        if (name == null || name.length() == 0 || value == null) {
            return;
        }

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        char cc=name.charAt(0);
        if (cc=='C' || cc=='c') {
            if (checkSpecialHeader(name, value))
            return;
        }

        getCoyoteResponse().setHeader(name, value);
    }


    /**
     * Set the specified integer header to the specified value.
     *
     * @param name Name of the header to set
     * @param value Integer value to be set
     */
    @Override
    public void setIntHeader(String name, int value) {

        if (name == null || name.length() == 0) {
            return;
        }

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        setHeader(name, "" + value);

    }


    /**
     * Set the HTTP status to be returned with this response.
     *
     * @param status The new HTTP status
     */
    @Override
    public void setStatus(int status) {
        setStatus(status, null);
    }


    /**
     * Set the HTTP status and message to be returned with this response.
     *
     * @param status The new HTTP status
     * @param message The associated text message
     *
     * @deprecated As of Version 2.1 of the Java Servlet API, this method
     *  has been deprecated due to the ambiguous meaning of the message
     *  parameter.
     */
    @Override
    @Deprecated
    public void setStatus(int status, String message) {

        if (isCommitted()) {
            return;
        }

        // Ignore any call from an included servlet
        if (included) {
            return;
        }

        getCoyoteResponse().setStatus(status);
        getCoyoteResponse().setMessage(message);

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Return <code>true</code> if the specified URL should be encoded with
     * a session identifier.  This will be true if all of the following
     * conditions are met:
     * <ul>
     * <li>The request we are responding to asked for a valid session
     * <li>The requested session ID was not received via a cookie
     * <li>The specified URL points back to somewhere within the web
     *     application that is responding to this request
     * </ul>
     *
     * @param location Absolute URL to be validated
     */
    protected boolean isEncodeable(final String location) {

        if (location == null) {
            return (false);
        }

        // Is this an intra-document reference?
        if (location.startsWith("#")) {
            return (false);
        }

        // Are we in a valid session that is not using cookies?
        final Request hreq = request;
        final Session session = hreq.getSessionInternal(false);
        if (session == null) {
            return (false);
        }
        if (hreq.isRequestedSessionIdFromCookie()) {
            return (false);
        }

        // Is URL encoding permitted
        if (!hreq.getServletContext().getEffectiveSessionTrackingModes().
                contains(SessionTrackingMode.URL)) {
            return false;
        }

        if (SecurityUtil.isPackageProtectionEnabled()) {
            return (
                AccessController.doPrivileged(new PrivilegedAction<Boolean>() {

                @Override
                public Boolean run(){
                    return Boolean.valueOf(doIsEncodeable(hreq, session, location));
                }
            })).booleanValue();
        } else {
            return doIsEncodeable(hreq, session, location);
        }
    }

    private boolean doIsEncodeable(Request hreq, Session session,
                                   String location) {
        // Is this a valid absolute URL?
        URL url = null;
        try {
            url = new URL(location);
        } catch (MalformedURLException e) {
            return (false);
        }

        // Does this URL match down to (and including) the context path?
        if (!hreq.getScheme().equalsIgnoreCase(url.getProtocol())) {
            return (false);
        }
        if (!hreq.getServerName().equalsIgnoreCase(url.getHost())) {
            return (false);
        }
        int serverPort = hreq.getServerPort();
        if (serverPort == -1) {
            if ("https".equals(hreq.getScheme())) {
                serverPort = 443;
            } else {
                serverPort = 80;
            }
        }
        int urlPort = url.getPort();
        if (urlPort == -1) {
            if ("https".equals(url.getProtocol())) {
                urlPort = 443;
            } else {
                urlPort = 80;
            }
        }
        if (serverPort != urlPort) {
            return (false);
        }

        String contextPath = getContext().getPath();
        if (contextPath != null) {
            String file = url.getFile();
            if (!file.startsWith(contextPath)) {
                return (false);
            }
            String tok = ";" +
                    SessionConfig.getSessionUriParamName(request.getContext()) +
                    "=" + session.getIdInternal();
            if( file.indexOf(tok, contextPath.length()) >= 0 ) {
                return (false);
            }
        }

        // This URL belongs to our web application, so it is encodeable
        return (true);

    }


    /**
     * Convert (if necessary) and return the absolute URL that represents the
     * resource referenced by this possibly relative URL.  If this URL is
     * already absolute, return it unchanged.
     *
     * @param location URL to be (possibly) converted and then returned
     *
     * @exception IllegalArgumentException if a MalformedURLException is
     *  thrown when converting the relative URL to an absolute one
     */
    protected String toAbsolute(String location) {

        if (location == null) {
            return (location);
        }

        boolean leadingSlash = location.startsWith("/");

        if (location.startsWith("//")) {
            // Scheme relative
            redirectURLCC.recycle();
            // Add the scheme
            String scheme = request.getScheme();
            try {
                redirectURLCC.append(scheme, 0, scheme.length());
                redirectURLCC.append(':');
                redirectURLCC.append(location, 0, location.length());
                return redirectURLCC.toString();
            } catch (IOException e) {
                IllegalArgumentException iae =
                    new IllegalArgumentException(location);
                iae.initCause(e);
                throw iae;
            }

        } else if (leadingSlash || !hasScheme(location)) {

            redirectURLCC.recycle();

            String scheme = request.getScheme();
            String name = request.getServerName();
            int port = request.getServerPort();

            try {
                redirectURLCC.append(scheme, 0, scheme.length());
                redirectURLCC.append("://", 0, 3);
                redirectURLCC.append(name, 0, name.length());
                if ((scheme.equals("http") && port != 80)
                    || (scheme.equals("https") && port != 443)) {
                    redirectURLCC.append(':');
                    String portS = port + "";
                    redirectURLCC.append(portS, 0, portS.length());
                }
                if (!leadingSlash) {
                    String relativePath = request.getDecodedRequestURI();
                    int pos = relativePath.lastIndexOf('/');
                    CharChunk encodedURI = null;
                    final String frelativePath = relativePath;
                    final int fend = pos;
                    if (SecurityUtil.isPackageProtectionEnabled() ){
                        try{
                            encodedURI = AccessController.doPrivileged(
                                new PrivilegedExceptionAction<CharChunk>(){
                                    @Override
                                    public CharChunk run() throws IOException{
                                        return urlEncoder.encodeURL(frelativePath, 0, fend);
                                    }
                           });
                        } catch (PrivilegedActionException pae){
                            IllegalArgumentException iae =
                                new IllegalArgumentException(location);
                            iae.initCause(pae.getException());
                            throw iae;
                        }
                    } else {
                        encodedURI = urlEncoder.encodeURL(relativePath, 0, pos);
                    }
                    redirectURLCC.append(encodedURI);
                    encodedURI.recycle();
                    redirectURLCC.append('/');
                }
                redirectURLCC.append(location, 0, location.length());

                normalize(redirectURLCC);
            } catch (IOException e) {
                IllegalArgumentException iae =
                    new IllegalArgumentException(location);
                iae.initCause(e);
                throw iae;
            }

            return redirectURLCC.toString();

        } else {

            return (location);

        }

    }

    /*
     * Removes /./ and /../ sequences from absolute URLs.
     * Code borrowed heavily from CoyoteAdapter.normalize()
     */
    private void normalize(CharChunk cc) {
        // Strip query string and/or fragment first as doing it this way makes
        // the normalization logic a lot simpler
        int truncate = cc.indexOf('?');
        if (truncate == -1) {
            truncate = cc.indexOf('#');
        }
        char[] truncateCC = null;
        if (truncate > -1) {
            truncateCC = Arrays.copyOfRange(cc.getBuffer(),
                    cc.getStart() + truncate, cc.getEnd());
            cc.setEnd(cc.getStart() + truncate);
        }

        if (cc.endsWith("/.") || cc.endsWith("/..")) {
            try {
                cc.append('/');
            } catch (IOException e) {
                throw new IllegalArgumentException(cc.toString(), e);
            }
        }

        char[] c = cc.getChars();
        int start = cc.getStart();
        int end = cc.getEnd();
        int index = 0;
        int startIndex = 0;

        // Advance past the first three / characters (should place index just
        // scheme://host[:port]

        for (int i = 0; i < 3; i++) {
            startIndex = cc.indexOf('/', startIndex + 1);
        }

        // Remove /./
        index = startIndex;
        while (true) {
            index = cc.indexOf("/./", 0, 3, index);
            if (index < 0) {
                break;
            }
            copyChars(c, start + index, start + index + 2,
                      end - start - index - 2);
            end = end - 2;
            cc.setEnd(end);
        }

        // Remove /../
        index = startIndex;
        int pos;
        while (true) {
            index = cc.indexOf("/../", 0, 4, index);
            if (index < 0) {
                break;
            }
            // Can't go above the server root
            if (index == startIndex) {
                throw new IllegalArgumentException();
            }
            int index2 = -1;
            for (pos = start + index - 1; (pos >= 0) && (index2 < 0); pos --) {
                if (c[pos] == (byte) '/') {
                    index2 = pos;
                }
            }
            copyChars(c, start + index2, start + index + 3,
                      end - start - index - 3);
            end = end + index2 - index - 3;
            cc.setEnd(end);
            index = index2;
        }

        // Add the query string and/or fragment (if present) back in
        if (truncateCC != null) {
            try {
                cc.append(truncateCC, 0, truncateCC.length);
            } catch (IOException ioe) {
                throw new IllegalArgumentException(ioe);
            }
        }
    }

    private void copyChars(char[] c, int dest, int src, int len) {
        for (int pos = 0; pos < len; pos++) {
            c[pos + dest] = c[pos + src];
        }
    }


    /**
     * Determine if an absolute URL has a path component
     */
    private boolean hasPath(String uri) {
        int pos = uri.indexOf("://");
        if (pos < 0) {
            return false;
        }
        pos = uri.indexOf('/', pos + 3);
        if (pos < 0) {
            return false;
        }
        return true;
    }

    /**
     * Determine if a URI string has a <code>scheme</code> component.
     */
    private boolean hasScheme(String uri) {
        int len = uri.length();
        for(int i=0; i < len ; i++) {
            char c = uri.charAt(i);
            if(c == ':') {
                return i > 0;
            } else if(!URL.isSchemeChar(c)) {
                return false;
            }
        }
        return false;
    }

    /**
     * Return the specified URL with the specified session identifier
     * suitably encoded.
     *
     * @param url URL to be encoded with the session id
     * @param sessionId Session id to be included in the encoded URL
     */
    protected String toEncoded(String url, String sessionId) {

        if ((url == null) || (sessionId == null)) {
            return (url);
        }

        String path = url;
        String query = "";
        String anchor = "";
        int question = url.indexOf('?');
        if (question >= 0) {
            path = url.substring(0, question);
            query = url.substring(question);
        }
        int pound = path.indexOf('#');
        if (pound >= 0) {
            anchor = path.substring(pound);
            path = path.substring(0, pound);
        }
        StringBuilder sb = new StringBuilder(path);
        if( sb.length() > 0 ) { // jsessionid can't be first.
            sb.append(";");
            sb.append(SessionConfig.getSessionUriParamName(
                    request.getContext()));
            sb.append("=");
            sb.append(sessionId);
        }
        sb.append(anchor);
        sb.append(query);
        return (sb.toString());

    }
}
