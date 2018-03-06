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


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.MultipartConfigElement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletResponse;
import javax.servlet.SessionTrackingMode;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.Manager;
import org.apache.catalina.Realm;
import org.apache.catalina.Session;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.ApplicationPart;
import org.apache.catalina.core.ApplicationSessionCookieConfig;
import org.apache.catalina.core.AsyncContextImpl;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.util.ParameterMap;
import org.apache.catalina.util.RequestUtil;
import org.apache.catalina.util.StringParser;
import org.apache.catalina.util.URLEncoder;
import org.apache.coyote.ActionCode;
import org.apache.coyote.http11.upgrade.servlet31.HttpUpgradeHandler;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.buf.UDecoder;
import org.apache.tomcat.util.compat.JreCompat;
import org.apache.tomcat.util.http.Cookies;
import org.apache.tomcat.util.http.FastHttpDateFormat;
import org.apache.tomcat.util.http.Parameters;
import org.apache.tomcat.util.http.Parameters.FailReason;
import org.apache.tomcat.util.http.ServerCookie;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.apache.tomcat.util.http.mapper.MappingData;
import org.apache.tomcat.util.net.SSLSupport;
import org.apache.tomcat.util.res.StringManager;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;


/**
 * Wrapper object for the Coyote request.
 *
 * @author Remy Maucherat
 * @author Craig R. McClanahan
 */
public class Request
    implements HttpServletRequest {

    private static final Log log = LogFactory.getLog(Request.class);

    // ----------------------------------------------------------- Constructors


    public Request() {

        formats[0].setTimeZone(GMT_ZONE);
        formats[1].setTimeZone(GMT_ZONE);
        formats[2].setTimeZone(GMT_ZONE);

    }


    // ------------------------------------------------------------- Properties


    /**
     * Coyote request.
     */
    protected org.apache.coyote.Request coyoteRequest;

    /**
     * Set the Coyote request.
     *
     * @param coyoteRequest The Coyote request
     */
    public void setCoyoteRequest(org.apache.coyote.Request coyoteRequest) {
        this.coyoteRequest = coyoteRequest;
        inputBuffer.setRequest(coyoteRequest);
    }

    /**
     * Get the Coyote request.
     */
    public org.apache.coyote.Request getCoyoteRequest() {
        return (this.coyoteRequest);
    }


    // ----------------------------------------------------- Variables


    protected static final TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT");


    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    /**
     * The set of cookies associated with this Request.
     */
    protected Cookie[] cookies = null;


    /**
     * The set of SimpleDateFormat formats to use in getDateHeader().
     *
     * Notice that because SimpleDateFormat is not thread-safe, we can't
     * declare formats[] as a static variable.
     */
    protected SimpleDateFormat formats[] = {
        new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
        new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
        new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)
    };


    /**
     * The default Locale if none are specified.
     */
    protected static Locale defaultLocale = Locale.getDefault();


    /**
     * The attributes associated with this Request, keyed by attribute name.
     */
    private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();


    /**
     * Flag that indicates if SSL attributes have been parsed to improve
     * performance for applications (usually frameworks) that make multiple
     * calls to {@link Request#getAttributeNames()}.
     */
    protected boolean sslAttributesParsed = false;


    /**
     * The preferred Locales associated with this Request.
     */
    protected ArrayList<Locale> locales = new ArrayList<Locale>();


    /**
     * Internal notes associated with this request by Catalina components
     * and event listeners.
     */
    private transient HashMap<String, Object> notes = new HashMap<String, Object>();


    /**
     * Authentication type.
     */
    protected String authType = null;


    /**
     * Associated event.
     */
    protected CometEventImpl event = null;


    /**
     * Comet state
     */
    protected boolean comet = false;


    /**
     * The current dispatcher type.
     */
    protected DispatcherType internalDispatcherType = null;


    /**
     * The associated input buffer.
     */
    protected InputBuffer inputBuffer = new InputBuffer();


    /**
     * ServletInputStream.
     */
    protected CoyoteInputStream inputStream =
        new CoyoteInputStream(inputBuffer);


    /**
     * Reader.
     */
    protected CoyoteReader reader = new CoyoteReader(inputBuffer);


    /**
     * Using stream flag.
     */
    protected boolean usingInputStream = false;


    /**
     * Using writer flag.
     */
    protected boolean usingReader = false;


    /**
     * User principal.
     */
    protected Principal userPrincipal = null;


    /**
     * Session parsed flag.
     */
    @Deprecated
    protected boolean sessionParsed = false;


    /**
     * Request parameters parsed flag.
     */
    protected boolean parametersParsed = false;


    /**
     * Cookies parsed flag.
     */
    protected boolean cookiesParsed = false;


    /**
     * Secure flag.
     */
    protected boolean secure = false;


    /**
     * The Subject associated with the current AccessControllerContext
     */
    protected transient Subject subject = null;


    /**
     * Post data buffer.
     */
    protected static int CACHED_POST_LEN = 8192;
    protected byte[] postData = null;


    /**
     * Hash map used in the getParametersMap method.
     */
    protected ParameterMap<String, String[]> parameterMap = new ParameterMap<String, String[]>();


    /**
     * The parts, if any, uploaded with this request.
     */
    protected Collection<Part> parts = null;


    /**
     * The exception thrown, if any when parsing the parts.
     */
    protected Exception partsParseException = null;


    /**
     * The currently active session for this request.
     */
    protected Session session = null;


    /**
     * The current request dispatcher path.
     */
    protected Object requestDispatcherPath = null;


    /**
     * Was the requested session ID received in a cookie?
     */
    protected boolean requestedSessionCookie = false;


    /**
     * The requested session ID (if any) for this request.
     */
    protected String requestedSessionId = null;


    /**
     * Was the requested session ID received in a URL?
     */
    protected boolean requestedSessionURL = false;


    /**
     * Was the requested session ID obtained from the SSL session?
     */
    protected boolean requestedSessionSSL = false;


    /**
     * Parse locales.
     */
    protected boolean localesParsed = false;


    /**
     * The string parser we will use for parsing request lines.
     */
    private final StringParser parser = new StringParser();


    /**
     * Local port
     */
    protected int localPort = -1;

    /**
     * Remote address.
     */
    protected String remoteAddr = null;


    /**
     * Remote host.
     */
    protected String remoteHost = null;


    /**
     * Remote port
     */
    protected int remotePort = -1;

    /**
     * Local address
     */
    protected String localAddr = null;


    /**
     * Local address
     */
    protected String localName = null;

    /**
     * AsyncContext
     */
    private volatile AsyncContextImpl asyncContext = null;

    protected Boolean asyncSupported = null;


    /**
     * Path parameters
     */
    protected Map<String,String> pathParameters = new HashMap<String, String>();

    // --------------------------------------------------------- Public Methods


    protected void addPathParameter(String name, String value) {
        pathParameters.put(name, value);
    }

    protected String getPathParameter(String name) {
        return pathParameters.get(name);
    }

    public void setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = Boolean.valueOf(asyncSupported);
    }

    /**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     */
    public void recycle() {

        context = null;
        wrapper = null;

        internalDispatcherType = null;
        requestDispatcherPath = null;

        comet = false;
        if (event != null) {
            event.clear();
            event = null;
        }

        authType = null;
        inputBuffer.recycle();
        usingInputStream = false;
        usingReader = false;
        userPrincipal = null;
        subject = null;
        sessionParsed = false;
        parametersParsed = false;
        if (parts != null) {
            for (Part part: parts) {
                try {
                    part.delete();
                } catch (IOException ignored) {
                    // ApplicationPart.delete() never throws an IOEx
                }
            }
            parts = null;
        }
        partsParseException = null;
        cookiesParsed = false;
        locales.clear();
        localesParsed = false;
        secure = false;
        remoteAddr = null;
        remoteHost = null;
        remotePort = -1;
        localPort = -1;
        localAddr = null;
        localName = null;

        attributes.clear();
        sslAttributesParsed = false;
        notes.clear();
        cookies = null;

        recycleSessionInfo();

        if (Globals.IS_SECURITY_ENABLED || Connector.RECYCLE_FACADES) {
            parameterMap = new ParameterMap<String, String[]>();
        } else {
            parameterMap.setLocked(false);
            parameterMap.clear();
        }

        mappingData.recycle();

        if (Globals.IS_SECURITY_ENABLED || Connector.RECYCLE_FACADES) {
            if (facade != null) {
                facade.clear();
                facade = null;
            }
            if (inputStream != null) {
                inputStream.clear();
                inputStream = null;
            }
            if (reader != null) {
                reader.clear();
                reader = null;
            }
        }

        asyncSupported = null;
        if (asyncContext!=null) {
            asyncContext.recycle();
        }
        asyncContext = null;

        pathParameters.clear();
    }

    @Deprecated
    protected boolean isProcessing() {
        return coyoteRequest.isProcessing();
    }

    /**
     * Clear cached encoders (to save memory for Comet requests).
     */
    public void clearEncoders() {
        inputBuffer.clearEncoders();
    }


    protected void recycleSessionInfo() {
        if (session != null) {
            try {
                session.endAccess();
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                log.warn(sm.getString("coyoteRequest.sessionEndAccessFail"), t);
            }
        }
        session = null;
        requestedSessionCookie = false;
        requestedSessionId = null;
        requestedSessionURL = false;
        requestedSessionSSL = false;
    }


    public boolean read() throws IOException {
        return (inputBuffer.realReadBytes(null, 0, 0) > 0);
    }


    // -------------------------------------------------------- Request Methods


    /**
     * Associated Catalina connector.
     */
    protected Connector connector;

    /**
     * Return the Connector through which this Request was received.
     */
    public Connector getConnector() {
        return this.connector;
    }

    /**
     * Set the Connector through which this Request was received.
     *
     * @param connector The new connector
     */
    public void setConnector(Connector connector) {
        this.connector = connector;
    }


    /**
     * Associated context.
     */
    protected Context context = null;

    /**
     * Return the Context within which this Request is being processed.
     */
    public Context getContext() {
        return this.context;
    }


    /**
     * Set the Context within which this Request is being processed.  This
     * must be called as soon as the appropriate Context is identified, because
     * it identifies the value to be returned by <code>getContextPath()</code>,
     * and thus enables parsing of the request URI.
     *
     * @param context The newly associated Context
     */
    public void setContext(Context context) {
        this.context = context;
    }


    /**
     * Filter chain associated with the request.
     */
    protected FilterChain filterChain = null;

    /**
     * Get filter chain associated with the request.
     */
    public FilterChain getFilterChain() {
        return this.filterChain;
    }

    /**
     * Set filter chain associated with the request.
     *
     * @param filterChain new filter chain
     */
    public void setFilterChain(FilterChain filterChain) {
        this.filterChain = filterChain;
    }


    /**
     * Return the Host within which this Request is being processed.
     */
    public Host getHost() {
        return ((Host) mappingData.host);
    }


    /**
     * Set the Host within which this Request is being processed.  This
     * must be called as soon as the appropriate Host is identified, and
     * before the Request is passed to a context.
     *
     * @param host The newly associated Host
     */
    @Deprecated
    public void setHost(Host host) {
        mappingData.host = host;
    }


    /**
     * Descriptive information about this Request implementation.
     */
    protected static final String info =
        "org.apache.coyote.catalina.CoyoteRequest/1.0";

    /**
     * Return descriptive information about this Request implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {
        return info;
    }


    /**
     * Mapping data.
     */
    protected MappingData mappingData = new MappingData();

    /**
     * Return mapping data.
     */
    public MappingData getMappingData() {
        return mappingData;
    }


    /**
     * The facade associated with this request.
     */
    protected RequestFacade facade = null;

    /**
     * Return the <code>ServletRequest</code> for which this object
     * is the facade.  This method must be implemented by a subclass.
     */
    public HttpServletRequest getRequest() {
        if (facade == null) {
            facade = new RequestFacade(this);
        }
        return facade;
    }


    /**
     * The response with which this request is associated.
     */
    protected org.apache.catalina.connector.Response response = null;

    /**
     * Return the Response with which this Request is associated.
     */
    public org.apache.catalina.connector.Response getResponse() {
        return this.response;
    }

    /**
     * Set the Response with which this Request is associated.
     *
     * @param response The new associated response
     */
    public void setResponse(org.apache.catalina.connector.Response response) {
        this.response = response;
    }

    /**
     * Return the input stream associated with this Request.
     */
    public InputStream getStream() {
        if (inputStream == null) {
            inputStream = new CoyoteInputStream(inputBuffer);
        }
        return inputStream;
    }

    /**
     * URI byte to char converter.
     */
    protected B2CConverter URIConverter = null;

    /**
     * Return the URI converter.
     */
    protected B2CConverter getURIConverter() {
        return URIConverter;
    }

    /**
     * Set the URI converter.
     *
     * @param URIConverter the new URI converter
     */
    protected void setURIConverter(B2CConverter URIConverter) {
        this.URIConverter = URIConverter;
    }


    /**
     * Associated wrapper.
     */
    protected Wrapper wrapper = null;

    /**
     * Return the Wrapper within which this Request is being processed.
     */
    public Wrapper getWrapper() {
        return this.wrapper;
    }


    /**
     * Set the Wrapper within which this Request is being processed.  This
     * must be called as soon as the appropriate Wrapper is identified, and
     * before the Request is ultimately passed to an application servlet.
     * @param wrapper The newly associated Wrapper
     */
    public void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }


    // ------------------------------------------------- Request Public Methods


    /**
     * Create and return a ServletInputStream to read the content
     * associated with this Request.
     *
     * @exception IOException if an input/output error occurs
     */
    public ServletInputStream createInputStream()
        throws IOException {
        if (inputStream == null) {
            inputStream = new CoyoteInputStream(inputBuffer);
        }
        return inputStream;
    }


    /**
     * Perform whatever actions are required to flush and close the input
     * stream or reader, in a single operation.
     *
     * @exception IOException if an input/output error occurs
     */
    public void finishRequest() throws IOException {
        // Optionally disable swallowing of additional request data.
        Context context = getContext();
        if (context != null &&
                response.getStatus() == HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE &&
                !context.getSwallowAbortedUploads()) {
            coyoteRequest.action(ActionCode.DISABLE_SWALLOW_INPUT, null);
        }
    }


    /**
     * Return the object bound with the specified name to the internal notes
     * for this request, or <code>null</code> if no such binding exists.
     *
     * @param name Name of the note to be returned
     */
    public Object getNote(String name) {
        return notes.get(name);
    }


    /**
     * Return an Iterator containing the String names of all notes bindings
     * that exist for this request.
     */
    @Deprecated
    public Iterator<String> getNoteNames() {
        return notes.keySet().iterator();
    }


    /**
     * Remove any object bound to the specified name in the internal notes
     * for this request.
     *
     * @param name Name of the note to be removed
     */
    public void removeNote(String name) {
        notes.remove(name);
    }


    /**
     * Set the port number of the server to process this request.
     *
     * @param port The server port
     */
    public void setLocalPort(int port) {
        localPort = port;
    }

    /**
     * Bind an object to a specified name in the internal notes associated
     * with this request, replacing any existing binding for this name.
     *
     * @param name Name to which the object should be bound
     * @param value Object to be bound to the specified name
     */
    public void setNote(String name, Object value) {
        notes.put(name, value);
    }


    /**
     * Set the IP address of the remote client associated with this Request.
     *
     * @param remoteAddr The remote IP address
     */
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }


    /**
     * Set the fully qualified name of the remote client associated with this
     * Request.
     *
     * @param remoteHost The remote host name
     */
    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }


    /**
     * Set the value to be returned by <code>isSecure()</code>
     * for this Request.
     *
     * @param secure The new isSecure value
     */
    public void setSecure(boolean secure) {
        this.secure = secure;
    }


    /**
     * Set the name of the server (virtual host) to process this request.
     *
     * @param name The server name
     */
    @Deprecated
    public void setServerName(String name) {
        coyoteRequest.serverName().setString(name);
    }


    /**
     * Set the port number of the server to process this request.
     *
     * @param port The server port
     */
    public void setServerPort(int port) {
        coyoteRequest.setServerPort(port);
    }


    // ------------------------------------------------- ServletRequest Methods


    /**
     * Return the specified request attribute if it exists; otherwise, return
     * <code>null</code>.
     *
     * @param name Name of the request attribute to return
     */
    @Override
    public Object getAttribute(String name) {

        // Special attributes
        SpecialAttributeAdapter adapter = specialAttributes.get(name);
        if (adapter != null) {
            return adapter.get(this, name);
        }

        Object attr=attributes.get(name);

        if(attr!=null) {
            return(attr);
        }

        attr =  coyoteRequest.getAttribute(name);
        if(attr != null) {
            return attr;
        }
        if( isSSLAttribute(name) || name.equals(SSLSupport.PROTOCOL_VERSION_KEY)) {
            coyoteRequest.action(ActionCode.REQ_SSL_ATTRIBUTE,
                                 coyoteRequest);
            attr = coyoteRequest.getAttribute(Globals.CERTIFICATES_ATTR);
            if( attr != null) {
                attributes.put(Globals.CERTIFICATES_ATTR, attr);
            }
            attr = coyoteRequest.getAttribute(Globals.CIPHER_SUITE_ATTR);
            if(attr != null) {
                attributes.put(Globals.CIPHER_SUITE_ATTR, attr);
            }
            attr = coyoteRequest.getAttribute(Globals.KEY_SIZE_ATTR);
            if(attr != null) {
                attributes.put(Globals.KEY_SIZE_ATTR, attr);
            }
            attr = coyoteRequest.getAttribute(Globals.SSL_SESSION_ID_ATTR);
            if(attr != null) {
                attributes.put(Globals.SSL_SESSION_ID_ATTR, attr);
                attributes.put(Globals.SSL_SESSION_ID_TOMCAT_ATTR, attr);
            }
            attr = coyoteRequest.getAttribute(Globals.SSL_SESSION_MGR_ATTR);
            if(attr != null) {
                attributes.put(Globals.SSL_SESSION_MGR_ATTR, attr);
            }
            attr = coyoteRequest.getAttribute(SSLSupport.PROTOCOL_VERSION_KEY);
            if(attr != null) {
                attributes.put(SSLSupport.PROTOCOL_VERSION_KEY, attr);
            }
            attr = attributes.get(name);
            sslAttributesParsed = true;
        }
        return attr;
    }


    /**
     * Test if a given name is one of the special Servlet-spec SSL attributes.
     */
    static boolean isSSLAttribute(String name) {
        return Globals.CERTIFICATES_ATTR.equals(name) ||
            Globals.CIPHER_SUITE_ATTR.equals(name) ||
            Globals.KEY_SIZE_ATTR.equals(name)  ||
            Globals.SSL_SESSION_ID_ATTR.equals(name) ||
            Globals.SSL_SESSION_ID_TOMCAT_ATTR.equals(name) ||
            Globals.SSL_SESSION_MGR_ATTR.equals(name);
    }

    /**
     * Return the names of all request attributes for this Request, or an
     * empty <code>Enumeration</code> if there are none. Note that the attribute
     * names returned will only be those for the attributes set via
     * {@link #setAttribute(String, Object)}. Tomcat internal attributes will
     * not be included although they are accessible via
     * {@link #getAttribute(String)}. The Tomcat internal attributes include:
     * <ul>
     * <li>{@link Globals#DISPATCHER_TYPE_ATTR}</li>
     * <li>{@link Globals#DISPATCHER_REQUEST_PATH_ATTR}</li>
     * <li>{@link Globals#ASYNC_SUPPORTED_ATTR}</li>
     * <li>{@link Globals#CERTIFICATES_ATTR} (SSL connections only)</li>
     * <li>{@link Globals#CIPHER_SUITE_ATTR} (SSL connections only)</li>
     * <li>{@link Globals#KEY_SIZE_ATTR} (SSL connections only)</li>
     * <li>{@link Globals#SSL_SESSION_ID_ATTR} (SSL connections only)</li>
     * <li>{@link Globals#SSL_SESSION_ID_TOMCAT_ATTR} (SSL connections only)
     * </li>
     * <li>{@link Globals#SSL_SESSION_MGR_ATTR} (SSL connections only)</li>
     * <li>{@link Globals#PARAMETER_PARSE_FAILED_ATTR}</li>
     * </ul>
     * The underlying connector may also expose request attributes. These all
     * have names starting with "org.apache.tomcat" and include:
     * <ul>
     * <li>{@link Globals#SENDFILE_SUPPORTED_ATTR}</li>
     * <li>{@link Globals#COMET_SUPPORTED_ATTR}</li>
     * <li>{@link Globals#COMET_TIMEOUT_SUPPORTED_ATTR}</li>
     * </ul>
     * Connector implementations may return some, all or none of these
     * attributes and may also support additional attributes.
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        if (isSecure() && !sslAttributesParsed) {
            getAttribute(Globals.CERTIFICATES_ATTR);
        }
        // Take a copy to prevent ConcurrentModificationExceptions if used to
        // remove attributes
        Set<String> names = new HashSet<String>();
        names.addAll(attributes.keySet());
        return Collections.enumeration(names);
    }


    /**
     * Return the character encoding for this Request.
     */
    @Override
    public String getCharacterEncoding() {
      return coyoteRequest.getCharacterEncoding();
    }


    /**
     * Return the content length for this Request.
     */
    @Override
    public int getContentLength() {
        return coyoteRequest.getContentLength();
    }


    /**
     * Return the content type for this Request.
     */
    @Override
    public String getContentType() {
        return coyoteRequest.getContentType();
    }


    /**
     * Return the servlet input stream for this Request.  The default
     * implementation returns a servlet input stream created by
     * <code>createInputStream()</code>.
     *
     * @exception IllegalStateException if <code>getReader()</code> has
     *  already been called for this request
     * @exception IOException if an input/output error occurs
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {

        if (usingReader) {
            throw new IllegalStateException
                (sm.getString("coyoteRequest.getInputStream.ise"));
        }

        usingInputStream = true;
        if (inputStream == null) {
            inputStream = new CoyoteInputStream(inputBuffer);
        }
        return inputStream;

    }


    /**
     * Return the preferred Locale that the client will accept content in,
     * based on the value for the first <code>Accept-Language</code> header
     * that was encountered.  If the request did not specify a preferred
     * language, the server's default Locale is returned.
     */
    @Override
    public Locale getLocale() {

        if (!localesParsed) {
            parseLocales();
        }

        if (locales.size() > 0) {
            return locales.get(0);
        }

        return defaultLocale;
    }


    /**
     * Return the set of preferred Locales that the client will accept
     * content in, based on the values for any <code>Accept-Language</code>
     * headers that were encountered.  If the request did not specify a
     * preferred language, the server's default Locale is returned.
     */
    @Override
    public Enumeration<Locale> getLocales() {

        if (!localesParsed) {
            parseLocales();
        }

        if (locales.size() > 0) {
            return Collections.enumeration(locales);
        }
        ArrayList<Locale> results = new ArrayList<Locale>();
        results.add(defaultLocale);
        return Collections.enumeration(results);

    }


    /**
     * Return the value of the specified request parameter, if any; otherwise,
     * return <code>null</code>.  If there is more than one value defined,
     * return only the first one.
     *
     * @param name Name of the desired request parameter
     */
    @Override
    public String getParameter(String name) {

        if (!parametersParsed) {
            parseParameters();
        }

        return coyoteRequest.getParameters().getParameter(name);

    }



    /**
     * Returns a <code>Map</code> of the parameters of this request.
     * Request parameters are extra information sent with the request.
     * For HTTP servlets, parameters are contained in the query string
     * or posted form data.
     *
     * @return A <code>Map</code> containing parameter names as keys
     *  and parameter values as map values.
     */
    @Override
    public Map<String, String[]> getParameterMap() {

        if (parameterMap.isLocked()) {
            return parameterMap;
        }

        Enumeration<String> enumeration = getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String[] values = getParameterValues(name);
            parameterMap.put(name, values);
        }

        parameterMap.setLocked(true);

        return parameterMap;

    }


    /**
     * Return the names of all defined request parameters for this request.
     */
    @Override
    public Enumeration<String> getParameterNames() {

        if (!parametersParsed) {
            parseParameters();
        }

        return coyoteRequest.getParameters().getParameterNames();

    }


    /**
     * Return the defined values for the specified request parameter, if any;
     * otherwise, return <code>null</code>.
     *
     * @param name Name of the desired request parameter
     */
    @Override
    public String[] getParameterValues(String name) {

        if (!parametersParsed) {
            parseParameters();
        }

        return coyoteRequest.getParameters().getParameterValues(name);

    }


    /**
     * Return the protocol and version used to make this Request.
     */
    @Override
    public String getProtocol() {
        return coyoteRequest.protocol().toString();
    }


    /**
     * Read the Reader wrapping the input stream for this Request.  The
     * default implementation wraps a <code>BufferedReader</code> around the
     * servlet input stream returned by <code>createInputStream()</code>.
     *
     * @exception IllegalStateException if <code>getInputStream()</code>
     *  has already been called for this request
     * @exception IOException if an input/output error occurs
     */
    @Override
    public BufferedReader getReader() throws IOException {

        if (usingInputStream) {
            throw new IllegalStateException
                (sm.getString("coyoteRequest.getReader.ise"));
        }

        usingReader = true;
        inputBuffer.checkConverter();
        if (reader == null) {
            reader = new CoyoteReader(inputBuffer);
        }
        return reader;

    }


    /**
     * Return the real path of the specified virtual path.
     *
     * @param path Path to be translated
     *
     * @deprecated As of version 2.1 of the Java Servlet API, use
     *  <code>ServletContext.getRealPath()</code>.
     */
    @Override
    @Deprecated
    public String getRealPath(String path) {

        if (context == null) {
            return null;
        }
        ServletContext servletContext = context.getServletContext();
        if (servletContext == null) {
            return null;
        }

        try {
            return (servletContext.getRealPath(path));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    /**
     * Return the remote IP address making this Request.
     */
    @Override
    public String getRemoteAddr() {
        if (remoteAddr == null) {
            coyoteRequest.action
                (ActionCode.REQ_HOST_ADDR_ATTRIBUTE, coyoteRequest);
            remoteAddr = coyoteRequest.remoteAddr().toString();
        }
        return remoteAddr;
    }


    /**
     * Return the remote host name making this Request.
     */
    @Override
    public String getRemoteHost() {
        if (remoteHost == null) {
            if (!connector.getEnableLookups()) {
                remoteHost = getRemoteAddr();
            } else {
                coyoteRequest.action
                    (ActionCode.REQ_HOST_ATTRIBUTE, coyoteRequest);
                remoteHost = coyoteRequest.remoteHost().toString();
            }
        }
        return remoteHost;
    }

    /**
     * Returns the Internet Protocol (IP) source port of the client
     * or last proxy that sent the request.
     */
    @Override
    public int getRemotePort(){
        if (remotePort == -1) {
            coyoteRequest.action
                (ActionCode.REQ_REMOTEPORT_ATTRIBUTE, coyoteRequest);
            remotePort = coyoteRequest.getRemotePort();
        }
        return remotePort;
    }

    /**
     * Returns the host name of the Internet Protocol (IP) interface on
     * which the request was received.
     */
    @Override
    public String getLocalName(){
        if (localName == null) {
            coyoteRequest.action
                (ActionCode.REQ_LOCAL_NAME_ATTRIBUTE, coyoteRequest);
            localName = coyoteRequest.localName().toString();
        }
        return localName;
    }

    /**
     * Returns the Internet Protocol (IP) address of the interface on
     * which the request  was received.
     */
    @Override
    public String getLocalAddr(){
        if (localAddr == null) {
            coyoteRequest.action
                (ActionCode.REQ_LOCAL_ADDR_ATTRIBUTE, coyoteRequest);
            localAddr = coyoteRequest.localAddr().toString();
        }
        return localAddr;
    }


    /**
     * Returns the Internet Protocol (IP) port number of the interface
     * on which the request was received.
     */
    @Override
    public int getLocalPort(){
        if (localPort == -1){
            coyoteRequest.action
                (ActionCode.REQ_LOCALPORT_ATTRIBUTE, coyoteRequest);
            localPort = coyoteRequest.getLocalPort();
        }
        return localPort;
    }

    /**
     * Return a RequestDispatcher that wraps the resource at the specified
     * path, which may be interpreted as relative to the current request path.
     *
     * @param path Path of the resource to be wrapped
     */
    @Override
    public RequestDispatcher getRequestDispatcher(String path) {

        if (context == null) {
            return null;
        }

        // If the path is already context-relative, just pass it through
        if (path == null) {
            return null;
        } else if (path.startsWith("/")) {
            return (context.getServletContext().getRequestDispatcher(path));
        }

        // Convert a request-relative path to a context-relative one
        String servletPath = (String) getAttribute(
                RequestDispatcher.INCLUDE_SERVLET_PATH);
        if (servletPath == null) {
            servletPath = getServletPath();
        }

        // Add the path info, if there is any
        String pathInfo = getPathInfo();
        String requestPath = null;

        if (pathInfo == null) {
            requestPath = servletPath;
        } else {
            requestPath = servletPath + pathInfo;
        }

        int pos = requestPath.lastIndexOf('/');
        String relative = null;
        if (context.getDispatchersUseEncodedPaths()) {
            if (pos >= 0) {
                relative = URLEncoder.DEFAULT.encode(
                        requestPath.substring(0, pos + 1), "UTF-8") + path;
            } else {
                relative = URLEncoder.DEFAULT.encode(requestPath, "UTF-8") + path;
            }
        } else {
            if (pos >= 0) {
                relative = requestPath.substring(0, pos + 1) + path;
            } else {
                relative = requestPath + path;
            }
        }

        return context.getServletContext().getRequestDispatcher(relative);
    }


    /**
     * Return the scheme used to make this Request.
     */
    @Override
    public String getScheme() {
        return coyoteRequest.scheme().toString();
    }


    /**
     * Return the server name responding to this Request.
     */
    @Override
    public String getServerName() {
        return coyoteRequest.serverName().toString();
    }


    /**
     * Return the server port responding to this Request.
     */
    @Override
    public int getServerPort() {
        return coyoteRequest.getServerPort();
    }


    /**
     * Was this request received on a secure connection?
     */
    @Override
    public boolean isSecure() {
        return secure;
    }


    /**
     * Remove the specified request attribute if it exists.
     *
     * @param name Name of the request attribute to remove
     */
    @Override
    public void removeAttribute(String name) {
        // Remove the specified attribute
        // Pass special attributes to the native layer
        if (name.startsWith("org.apache.tomcat.")) {
            coyoteRequest.getAttributes().remove(name);
        }

        boolean found = attributes.containsKey(name);
        if (found) {
            Object value = attributes.get(name);
            attributes.remove(name);

            // Notify interested application event listeners
            notifyAttributeRemoved(name, value);
        } else {
            return;
        }
    }


    /**
     * Set the specified request attribute to the specified value.
     *
     * @param name Name of the request attribute to set
     * @param value The associated value
     */
    @Override
    public void setAttribute(String name, Object value) {

        // Name cannot be null
        if (name == null) {
            throw new IllegalArgumentException
                (sm.getString("coyoteRequest.setAttribute.namenull"));
        }

        // Null value is the same as removeAttribute()
        if (value == null) {
            removeAttribute(name);
            return;
        }

        // Special attributes
        SpecialAttributeAdapter adapter = specialAttributes.get(name);
        if (adapter != null) {
            adapter.set(this, name, value);
            return;
        }

        // Add or replace the specified attribute
        // Do the security check before any updates are made
        if (Globals.IS_SECURITY_ENABLED &&
                name.equals(Globals.SENDFILE_FILENAME_ATTR)) {
            // Use the canonical file name to avoid any possible symlink and
            // relative path issues
            String canonicalPath;
            try {
                canonicalPath = new File(value.toString()).getCanonicalPath();
            } catch (IOException e) {
                throw new SecurityException(sm.getString(
                        "coyoteRequest.sendfileNotCanonical", value), e);
            }
            // Sendfile is performed in Tomcat's security context so need to
            // check if the web app is permitted to access the file while still
            // in the web app's security context
            System.getSecurityManager().checkRead(canonicalPath);
            // Update the value so the canonical path is used
            value = canonicalPath;
        }

        Object oldValue = attributes.put(name, value);

        // Pass special attributes to the native layer
        if (name.startsWith("org.apache.tomcat.")) {
            coyoteRequest.setAttribute(name, value);
        }

        // Notify interested application event listeners
        notifyAttributeAssigned(name, value, oldValue);
    }


    /**
     * Notify interested listeners that attribute has been assigned a value.
     */
    private void notifyAttributeAssigned(String name, Object value,
            Object oldValue) {
        Object listeners[] = context.getApplicationEventListeners();
        if ((listeners == null) || (listeners.length == 0)) {
            return;
        }
        boolean replaced = (oldValue != null);
        ServletRequestAttributeEvent event = null;
        if (replaced) {
            event = new ServletRequestAttributeEvent(
                    context.getServletContext(), getRequest(), name, oldValue);
        } else {
            event = new ServletRequestAttributeEvent(
                    context.getServletContext(), getRequest(), name, value);
        }

        for (int i = 0; i < listeners.length; i++) {
            if (!(listeners[i] instanceof ServletRequestAttributeListener)) {
                continue;
            }
            ServletRequestAttributeListener listener =
                (ServletRequestAttributeListener) listeners[i];
            try {
                if (replaced) {
                    listener.attributeReplaced(event);
                } else {
                    listener.attributeAdded(event);
                }
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                context.getLogger().error(sm.getString("coyoteRequest.attributeEvent"), t);
                // Error valve will pick this exception up and display it to user
                attributes.put(RequestDispatcher.ERROR_EXCEPTION, t);
            }
        }
    }


    /**
     * Notify interested listeners that attribute has been removed.
     */
    private void notifyAttributeRemoved(String name, Object value) {
        Object listeners[] = context.getApplicationEventListeners();
        if ((listeners == null) || (listeners.length == 0)) {
            return;
        }
        ServletRequestAttributeEvent event =
          new ServletRequestAttributeEvent(context.getServletContext(),
                                           getRequest(), name, value);
        for (int i = 0; i < listeners.length; i++) {
            if (!(listeners[i] instanceof ServletRequestAttributeListener)) {
                continue;
            }
            ServletRequestAttributeListener listener =
                (ServletRequestAttributeListener) listeners[i];
            try {
                listener.attributeRemoved(event);
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                context.getLogger().error(sm.getString("coyoteRequest.attributeEvent"), t);
                // Error valve will pick this exception up and display it to user
                attributes.put(RequestDispatcher.ERROR_EXCEPTION, t);
            }
        }
    }


    /**
     * Overrides the name of the character encoding used in the body of
     * this request.  This method must be called prior to reading request
     * parameters or reading input using <code>getReader()</code>.
     *
     * @param enc The character encoding to be used
     *
     * @exception UnsupportedEncodingException if the specified encoding
     *  is not supported
     *
     * @since Servlet 2.3
     */
    @Override
    public void setCharacterEncoding(String enc)
        throws UnsupportedEncodingException {

        if (usingReader) {
            return;
        }

        // Confirm that the encoding name is valid
        B2CConverter.getCharset(enc);

        // Save the validated encoding
        coyoteRequest.setCharacterEncoding(enc);
    }


    @Override
    public ServletContext getServletContext() {
        return context.getServletContext();
     }

    @Override
    public AsyncContext startAsync() {
        return startAsync(getRequest(),response.getResponse());
    }

    @Override
    public AsyncContext startAsync(ServletRequest request,
            ServletResponse response) {
        if (!isAsyncSupported()) {
            throw new IllegalStateException(sm.getString("request.asyncNotSupported"));
        }

        if (asyncContext == null) {
            asyncContext = new AsyncContextImpl(this);
        }

        asyncContext.setStarted(getContext(), request, response,
                request==getRequest() && response==getResponse().getResponse());
        asyncContext.setTimeout(getConnector().getAsyncTimeout());

        return asyncContext;
    }

    @Override
    public boolean isAsyncStarted() {
        if (asyncContext == null) {
            return false;
        }

        return asyncContext.isStarted();
    }

    public boolean isAsyncDispatching() {
        if (asyncContext == null) {
            return false;
        }

        AtomicBoolean result = new AtomicBoolean(false);
        coyoteRequest.action(ActionCode.ASYNC_IS_DISPATCHING, result);
        return result.get();
    }

    public boolean isAsyncCompleting() {
        if (asyncContext == null) {
            return false;
        }

        AtomicBoolean result = new AtomicBoolean(false);
        coyoteRequest.action(ActionCode.ASYNC_IS_COMPLETING, result);
        return result.get();
    }

    public boolean isAsync() {
        if (asyncContext == null) {
            return false;
        }

        AtomicBoolean result = new AtomicBoolean(false);
        coyoteRequest.action(ActionCode.ASYNC_IS_ASYNC, result);
        return result.get();
    }

    @Override
    public boolean isAsyncSupported() {
        if (this.asyncSupported == null) {
            return true;
        }

        return asyncSupported.booleanValue();
    }

    @Override
    public AsyncContext getAsyncContext() {
        if (!isAsyncStarted()) {
            throw new IllegalStateException(sm.getString("request.notAsync"));
        }
        return asyncContext;
    }

    public AsyncContextImpl getAsyncContextInternal() {
        return asyncContext;
    }

    @Override
    public DispatcherType getDispatcherType() {
        if (internalDispatcherType == null) {
            return DispatcherType.REQUEST;
        }

        return this.internalDispatcherType;
    }

    // ---------------------------------------------------- HttpRequest Methods


    /**
     * Add a Cookie to the set of Cookies associated with this Request.
     *
     * @param cookie The new cookie
     */
    public void addCookie(Cookie cookie) {

        if (!cookiesParsed) {
            parseCookies();
        }

        int size = 0;
        if (cookies != null) {
            size = cookies.length;
        }

        Cookie[] newCookies = new Cookie[size + 1];
        for (int i = 0; i < size; i++) {
            newCookies[i] = cookies[i];
        }
        newCookies[size] = cookie;

        cookies = newCookies;

    }


    /**
     * Add a Locale to the set of preferred Locales for this Request.  The
     * first added Locale will be the first one returned by getLocales().
     *
     * @param locale The new preferred Locale
     */
    public void addLocale(Locale locale) {
        locales.add(locale);
    }


    /**
     * Add a parameter name and corresponding set of values to this Request.
     * (This is used when restoring the original request on a form based
     * login).
     *
     * @param name Name of this request parameter
     * @param values Corresponding values for this request parameter
     */
    @Deprecated
    public void addParameter(String name, String values[]) {
        coyoteRequest.getParameters().addParameterValues(name, values);
    }


    /**
     * Clear the collection of Cookies associated with this Request.
     */
    public void clearCookies() {
        cookiesParsed = true;
        cookies = null;
    }


    /**
     * Clear the collection of Headers associated with this Request.
     */
    @Deprecated
    public void clearHeaders() {
        // Not used
    }


    /**
     * Clear the collection of Locales associated with this Request.
     */
    public void clearLocales() {
        locales.clear();
    }


    /**
     * Clear the collection of parameters associated with this Request.
     */
    @Deprecated
    public void clearParameters() {
        // Not used
    }


    /**
     * Set the authentication type used for this request, if any; otherwise
     * set the type to <code>null</code>.  Typical values are "BASIC",
     * "DIGEST", or "SSL".
     *
     * @param type The authentication type used
     */
    public void setAuthType(String type) {
        this.authType = type;
    }


    /**
     * Set the context path for this Request.  This will normally be called
     * when the associated Context is mapping the Request to a particular
     * Wrapper.
     *
     * @param path The context path
     */
    @Deprecated
    public void setContextPath(String path) {

        if (path == null) {
            mappingData.contextPath.setString("");
        } else {
            mappingData.contextPath.setString(path);
        }

    }


    /**
     * Set the path information for this Request.  This will normally be called
     * when the associated Context is mapping the Request to a particular
     * Wrapper.
     *
     * @param path The path information
     */
    public void setPathInfo(String path) {
        mappingData.pathInfo.setString(path);
    }


    /**
     * Set a flag indicating whether or not the requested session ID for this
     * request came in through a cookie.  This is normally called by the
     * HTTP Connector, when it parses the request headers.
     *
     * @param flag The new flag
     */
    public void setRequestedSessionCookie(boolean flag) {

        this.requestedSessionCookie = flag;

    }


    /**
     * Set the requested session ID for this request.  This is normally called
     * by the HTTP Connector, when it parses the request headers.
     *
     * @param id The new session id
     */
    public void setRequestedSessionId(String id) {

        this.requestedSessionId = id;

    }


    /**
     * Set a flag indicating whether or not the requested session ID for this
     * request came in through a URL.  This is normally called by the
     * HTTP Connector, when it parses the request headers.
     *
     * @param flag The new flag
     */
    public void setRequestedSessionURL(boolean flag) {

        this.requestedSessionURL = flag;

    }


    /**
     * Set a flag indicating whether or not the requested session ID for this
     * request came in through SSL.  This is normally called by the
     * HTTP Connector, when it parses the request headers.
     *
     * @param flag The new flag
     */
    public void setRequestedSessionSSL(boolean flag) {

        this.requestedSessionSSL = flag;

    }


    /**
     * Get the decoded request URI.
     *
     * @return the URL decoded request URI
     */
    public String getDecodedRequestURI() {
        return coyoteRequest.decodedURI().toString();
    }


    /**
     * Get the decoded request URI.
     *
     * @return the URL decoded request URI
     */
    public MessageBytes getDecodedRequestURIMB() {
        return coyoteRequest.decodedURI();
    }


    /**
     * Set the servlet path for this Request.  This will normally be called
     * when the associated Context is mapping the Request to a particular
     * Wrapper.
     *
     * @param path The servlet path
     */
    @Deprecated
    public void setServletPath(String path) {
        if (path != null) {
            mappingData.wrapperPath.setString(path);
        }
    }


    /**
     * Set the Principal who has been authenticated for this Request.  This
     * value is also used to calculate the value to be returned by the
     * <code>getRemoteUser()</code> method.
     *
     * @param principal The user Principal
     */
    public void setUserPrincipal(final Principal principal) {
        if (Globals.IS_SECURITY_ENABLED) {
            if (subject == null) {
                final HttpSession session = getSession(false);
                if (session == null) {
                    // Cache the subject in the request
                    subject = newSubject(principal);
                } else {
                    // Cache the subject in the request and the session
                    subject = (Subject) session.getAttribute(Globals.SUBJECT_ATTR);
                    if (subject == null) {
                        subject = newSubject(principal);
                        session.setAttribute(Globals.SUBJECT_ATTR, subject);
                    } else {
                        subject.getPrincipals().add(principal);
                    }
                }
            } else {
                subject.getPrincipals().add(principal);
            }
        }
        userPrincipal = principal;
    }


    private Subject newSubject(final Principal principal) {
        final Subject result = new Subject();
        result.getPrincipals().add(principal);
        return result;
    }


    // --------------------------------------------- HttpServletRequest Methods


    /**
     * Return the authentication type used for this Request.
     */
    @Override
    public String getAuthType() {
        return authType;
    }


    /**
     * Return the portion of the request URI used to select the Context
     * of the Request. The value returned is not decoded which also implies it
     * is not normalised.
     */
    @Override
    public String getContextPath() {
        String canonicalContextPath = getServletContext().getContextPath();
        String uri = getRequestURI();
        char[] uriChars = uri.toCharArray();
        int lastSlash = mappingData.contextSlashCount;
        // Special case handling for the root context
        if (lastSlash == 0) {
            return "";
        }
        int pos = 0;
        // Need at least the number of slashes in the context path
        while (lastSlash > 0) {
            pos = nextSlash(uriChars, pos + 1);
            if (pos == -1) {
                break;
            }
            lastSlash--;
        }
        // Now allow for path parameters, normalization and/or encoding.
        // Essentially, keep extending the candidate path up to the next slash
        // until the decoded and normalized candidate path (with the path
        // parameters removed) is the same as the canonical path.
        String candidate;
        if (pos == -1) {
            candidate = uri;
        } else {
            candidate = uri.substring(0, pos);
        }
        candidate = removePathParameters(candidate);
        candidate = RequestUtil.URLDecode(candidate, connector.getURIEncoding());
        candidate = org.apache.tomcat.util.http.RequestUtil.normalize(candidate);
        boolean match = canonicalContextPath.equals(candidate);
        while (!match && pos != -1) {
            pos = nextSlash(uriChars, pos + 1);
            if (pos == -1) {
                candidate = uri;
            } else {
                candidate = uri.substring(0, pos);
            }
            candidate = removePathParameters(candidate);
            candidate = RequestUtil.URLDecode(candidate, connector.getURIEncoding());
            candidate = org.apache.tomcat.util.http.RequestUtil.normalize(candidate);
            match = canonicalContextPath.equals(candidate);
        }
        if (match) {
            if (pos == -1) {
                return uri;
            } else {
                return uri.substring(0, pos);
            }
        } else {
            // Should never happen
            throw new IllegalStateException(sm.getString(
                    "coyoteRequest.getContextPath.ise", canonicalContextPath, uri));
        }
    }


    private String removePathParameters(String input) {
        int nextSemiColon = input.indexOf(';');
        // Shortcut
        if (nextSemiColon == -1) {
            return input;
        }
        StringBuilder result = new StringBuilder(input.length());
        result.append(input.substring(0, nextSemiColon));
        while (true) {
            int nextSlash = input.indexOf('/', nextSemiColon);
            if (nextSlash == -1) {
                break;
            }
            nextSemiColon = input.indexOf(';', nextSlash);
            if (nextSemiColon == -1) {
                result.append(input.substring(nextSlash));
                break;
            } else {
                result.append(input.substring(nextSlash, nextSemiColon));
            }
        }

        return result.toString();
    }


    private int nextSlash(char[] uri, int startPos) {
        int len = uri.length;
        int pos = startPos;
        while (pos < len) {
            if (uri[pos] == '/') {
                return pos;
            } else if (UDecoder.ALLOW_ENCODED_SLASH && uri[pos] == '%' && pos + 2 < len &&
                    uri[pos+1] == '2' && (uri[pos + 2] == 'f' || uri[pos + 2] == 'F')) {
                return pos;
            }
            pos++;
        }
        return -1;
    }


    /**
     * Get the context path.
     *
     * @return the context path
     */
    @Deprecated
    public MessageBytes getContextPathMB() {
        return mappingData.contextPath;
    }


    /**
     * Return the set of Cookies received with this Request.
     */
    @Override
    public Cookie[] getCookies() {

        if (!cookiesParsed) {
            parseCookies();
        }

        return cookies;

    }


    /**
     * Set the set of cookies received with this Request.
     */
    @Deprecated
    public void setCookies(Cookie[] cookies) {

        this.cookies = cookies;

    }


    /**
     * Return the value of the specified date header, if any; otherwise
     * return -1.
     *
     * @param name Name of the requested date header
     *
     * @exception IllegalArgumentException if the specified header value
     *  cannot be converted to a date
     */
    @Override
    public long getDateHeader(String name) {

        String value = getHeader(name);
        if (value == null) {
            return (-1L);
        }

        // Attempt to convert the date header in a variety of formats
        long result = FastHttpDateFormat.parseDate(value, formats);
        if (result != (-1L)) {
            return result;
        }
        throw new IllegalArgumentException(value);

    }


    /**
     * Return the first value of the specified header, if any; otherwise,
     * return <code>null</code>
     *
     * @param name Name of the requested header
     */
    @Override
    public String getHeader(String name) {
        return coyoteRequest.getHeader(name);
    }


    /**
     * Return all of the values of the specified header, if any; otherwise,
     * return an empty enumeration.
     *
     * @param name Name of the requested header
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        return coyoteRequest.getMimeHeaders().values(name);
    }


    /**
     * Return the names of all headers received with this request.
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return coyoteRequest.getMimeHeaders().names();
    }


    /**
     * Return the value of the specified header as an integer, or -1 if there
     * is no such header for this request.
     *
     * @param name Name of the requested header
     *
     * @exception IllegalArgumentException if the specified header value
     *  cannot be converted to an integer
     */
    @Override
    public int getIntHeader(String name) {

        String value = getHeader(name);
        if (value == null) {
            return (-1);
        }

        return Integer.parseInt(value);
    }


    /**
     * Return the HTTP request method used in this Request.
     */
    @Override
    public String getMethod() {
        return coyoteRequest.method().toString();
    }


    /**
     * Return the path information associated with this Request.
     */
    @Override
    public String getPathInfo() {
        return mappingData.pathInfo.toString();
    }


    /**
     * Get the path info.
     *
     * @return the path info
     */
    @Deprecated
    public MessageBytes getPathInfoMB() {
        return mappingData.pathInfo;
    }


    /**
     * Return the extra path information for this request, translated
     * to a real path.
     */
    @Override
    public String getPathTranslated() {

        if (context == null) {
            return null;
        }

        if (getPathInfo() == null) {
            return null;
        }

        return context.getServletContext().getRealPath(getPathInfo());
    }


    /**
     * Return the query string associated with this request.
     */
    @Override
    public String getQueryString() {
        return coyoteRequest.queryString().toString();
    }


    /**
     * Return the name of the remote user that has been authenticated
     * for this Request.
     */
    @Override
    public String getRemoteUser() {

        if (userPrincipal == null) {
            return null;
        }

        return userPrincipal.getName();
    }


    /**
     * Get the request path.
     *
     * @return the request path
     */
    public MessageBytes getRequestPathMB() {
        return mappingData.requestPath;
    }


    /**
     * Return the session identifier included in this request, if any.
     */
    @Override
    public String getRequestedSessionId() {
        return requestedSessionId;
    }


    /**
     * Return the request URI for this request.
     */
    @Override
    public String getRequestURI() {
        return coyoteRequest.requestURI().toString();
    }


    /**
     * Reconstructs the URL the client used to make the request.
     * The returned URL contains a protocol, server name, port
     * number, and server path, but it does not include query
     * string parameters.
     * <p>
     * Because this method returns a <code>StringBuffer</code>,
     * not a <code>String</code>, you can modify the URL easily,
     * for example, to append query parameters.
     * <p>
     * This method is useful for creating redirect messages and
     * for reporting errors.
     *
     * @return A <code>StringBuffer</code> object containing the
     *  reconstructed URL
     */
    @Override
    public StringBuffer getRequestURL() {

        StringBuffer url = new StringBuffer();
        String scheme = getScheme();
        int port = getServerPort();
        if (port < 0)
         {
            port = 80; // Work around java.net.URL bug
        }

        url.append(scheme);
        url.append("://");
        url.append(getServerName());
        if ((scheme.equals("http") && (port != 80))
            || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(getRequestURI());

        return url;
    }


    /**
     * Return the portion of the request URI used to select the servlet
     * that will process this request.
     */
    @Override
    public String getServletPath() {
        return (mappingData.wrapperPath.toString());
    }


    /**
     * Get the servlet path.
     *
     * @return the servlet path
     */
    @Deprecated
    public MessageBytes getServletPathMB() {
        return mappingData.wrapperPath;
    }


    /**
     * Return the session associated with this Request, creating one
     * if necessary.
     */
    @Override
    public HttpSession getSession() {
        Session session = doGetSession(true);
        if (session == null) {
            return null;
        }

        return session.getSession();
    }


    /**
     * Return the session associated with this Request, creating one
     * if necessary and requested.
     *
     * @param create Create a new session if one does not exist
     */
    @Override
    public HttpSession getSession(boolean create) {
        Session session = doGetSession(create);
        if (session == null) {
            return null;
        }

        return session.getSession();
    }


    /**
     * Return <code>true</code> if the session identifier included in this
     * request came from a cookie.
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {

        if (requestedSessionId == null) {
            return false;
        }

        return requestedSessionCookie;
    }


    /**
     * Return <code>true</code> if the session identifier included in this
     * request came from the request URI.
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {

        if (requestedSessionId == null) {
            return false;
        }

        return requestedSessionURL;
    }


    /**
     * Return <code>true</code> if the session identifier included in this
     * request came from the request URI.
     *
     * @deprecated As of Version 2.1 of the Java Servlet API, use
     *  <code>isRequestedSessionIdFromURL()</code> instead.
     */
    @Override
    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        return (isRequestedSessionIdFromURL());
    }


    /**
     * Return <code>true</code> if the session identifier included in this
     * request identifies a valid session.
     */
    @Override
    public boolean isRequestedSessionIdValid() {

        if (requestedSessionId == null) {
            return false;
        }

        if (context == null) {
            return false;
        }

        Manager manager = context.getManager();
        if (manager == null) {
            return false;
        }

        Session session = null;
        try {
            session = manager.findSession(requestedSessionId);
        } catch (IOException e) {
            // Can't find the session
        }

        if ((session == null) || !session.isValid()) {
            // Check for parallel deployment contexts
            if (getMappingData().contexts == null) {
                return false;
            } else {
                for (int i = (getMappingData().contexts.length); i > 0; i--) {
                    Context ctxt = (Context) getMappingData().contexts[i - 1];
                    try {
                        if (ctxt.getManager().findSession(requestedSessionId) !=
                                null) {
                            return true;
                        }
                    } catch (IOException e) {
                        // Ignore
                    }
                }
                return false;
            }
        }

        return true;
    }


    /**
     * Return <code>true</code> if the authenticated user principal
     * possesses the specified role name.
     *
     * @param role Role name to be validated
     */
    @Override
    public boolean isUserInRole(String role) {

        // Have we got an authenticated principal at all?
        if (userPrincipal == null) {
            return false;
        }

        // Identify the Realm we will use for checking role assignments
        if (context == null) {
            return false;
        }

        Realm realm = context.getRealm();
        if (realm == null) {
            return false;
        }

        // Check for a role defined directly as a <security-role>
        return (realm.hasRole(wrapper, userPrincipal, role));
    }


    /**
     * Return the principal that has been authenticated for this Request.
     */
    public Principal getPrincipal() {
        return userPrincipal;
    }


    /**
     * Return the principal that has been authenticated for this Request.
     */
    @Override
    public Principal getUserPrincipal() {
        if (userPrincipal instanceof GenericPrincipal) {
            GSSCredential gssCredential =
                    ((GenericPrincipal) userPrincipal).getGssCredential();
            if (gssCredential != null) {
                int left = -1;
                try {
                    left = gssCredential.getRemainingLifetime();
                } catch (GSSException e) {
                    log.warn(sm.getString("coyoteRequest.gssLifetimeFail",
                            userPrincipal.getName()), e);
                }
                if (left == 0) {
                    // GSS credential has expired. Need to re-authenticate.
                    try {
                        logout();
                    } catch (ServletException e) {
                        // Should never happen (no code called by logout()
                        // throws a ServletException
                    }
                    return null;
                }
            }
            return ((GenericPrincipal) userPrincipal).getUserPrincipal();
        }

        return userPrincipal;
    }


    /**
     * Return the session associated with this Request, creating one
     * if necessary.
     */
    public Session getSessionInternal() {
        return doGetSession(true);
    }


    /**
     * Change the ID of the session that this request is associated with. There
     * are several things that may trigger an ID change. These include moving
     * between nodes in a cluster and session fixation prevention during the
     * authentication process.
     *
     * @param newSessionId   The session to change the session ID for
     */
    public void changeSessionId(String newSessionId) {
        // This should only ever be called if there was an old session ID but
        // double check to be sure
        if (requestedSessionId != null && requestedSessionId.length() > 0) {
            requestedSessionId = newSessionId;
        }

        if (context != null && !context.getServletContext()
                .getEffectiveSessionTrackingModes().contains(
                        SessionTrackingMode.COOKIE)) {
            return;
        }

        if (response != null) {
            Cookie newCookie =
                ApplicationSessionCookieConfig.createSessionCookie(context,
                        newSessionId, secure);
            response.addSessionCookieInternal(newCookie);
        }
    }


    /**
     * Return the session associated with this Request, creating one
     * if necessary and requested.
     *
     * @param create Create a new session if one does not exist
     */
    public Session getSessionInternal(boolean create) {
        return doGetSession(create);
    }


    /**
     * Get the event associated with the request.
     * @return the event
     */
    public CometEventImpl getEvent() {
        if (event == null) {
            event = new CometEventImpl(this, response);
        }
        return event;
    }


    /**
     * Return true if the current request is handling Comet traffic.
     */
    public boolean isComet() {
        return comet;
    }


    /**
     * Set comet state.
     */
    public void setComet(boolean comet) {
        this.comet = comet;
    }

    /**
     * return true if we have parsed parameters
     */
    public boolean isParametersParsed() {
        return parametersParsed;
    }

    /**
     * Return true if bytes are available.
     */
    public boolean getAvailable() {
        return (inputBuffer.available() > 0);
    }

    /**
     * Disable swallowing of remaining input if configured
     */
    protected void checkSwallowInput() {
        Context context = getContext();
        if (context != null && !context.getSwallowAbortedUploads()) {
            coyoteRequest.action(ActionCode.DISABLE_SWALLOW_INPUT, null);
        }
    }

    public void cometClose() {
        coyoteRequest.action(ActionCode.COMET_CLOSE,getEvent());
        setComet(false);
    }

    public void setCometTimeout(long timeout) {
        coyoteRequest.action(ActionCode.COMET_SETTIMEOUT, Long.valueOf(timeout));
    }

    /**
     * Not part of Servlet 3 spec but probably should be.
     * @return true if the requested session ID was obtained from the SSL session
     */
    @Deprecated
    public boolean isRequestedSessionIdFromSSL() {
        return requestedSessionSSL;
    }

    /**
     * @throws IOException If an I/O error occurs
     * @throws IllegalStateException If the response has been committed
     * @throws ServletException If the caller is responsible for handling the
     *         error and the container has NOT set the HTTP response code etc.
     */
    @Override
    public boolean authenticate(HttpServletResponse response)
    throws IOException, ServletException {
        if (response.isCommitted()) {
            throw new IllegalStateException(
                    sm.getString("coyoteRequest.authenticate.ise"));
        }

        return context.getAuthenticator().authenticate(this, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(String username, String password)
    throws ServletException {
        if (getAuthType() != null || getRemoteUser() != null ||
                getUserPrincipal() != null) {
            throw new ServletException(
                    sm.getString("coyoteRequest.alreadyAuthenticated"));
        }

        if (context.getAuthenticator() == null) {
            throw new ServletException("no authenticator");
        }

        context.getAuthenticator().login(username, password, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout() throws ServletException {
        context.getAuthenticator().logout(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Part> getParts() throws IOException, IllegalStateException,
            ServletException {

        parseParts();

        if (partsParseException != null) {
            if (partsParseException instanceof IOException) {
                throw (IOException) partsParseException;
            } else if (partsParseException instanceof IllegalStateException) {
                throw (IllegalStateException) partsParseException;
            } else if (partsParseException instanceof ServletException) {
                throw (ServletException) partsParseException;
            }
        }

        return parts;
    }

    private void parseParts() {

        // Return immediately if the parts have already been parsed
        if (parts != null || partsParseException != null) {
            return;
        }

        MultipartConfigElement mce = getWrapper().getMultipartConfigElement();

        if (mce == null) {
            if(getContext().getAllowCasualMultipartParsing()) {
                mce = new MultipartConfigElement(null,
                                                 connector.getMaxPostSize(),
                                                 connector.getMaxPostSize(),
                                                 connector.getMaxPostSize());
            } else {
                parts = Collections.emptyList();
                return;
            }
        }

        Parameters parameters = coyoteRequest.getParameters();
        parameters.setLimit(getConnector().getMaxParameterCount());

        boolean success = false;
        try {
            File location;
            String locationStr = mce.getLocation();
            if (locationStr == null || locationStr.length() == 0) {
                location = ((File) context.getServletContext().getAttribute(
                        ServletContext.TEMPDIR));
            } else {
                // If relative, it is relative to TEMPDIR
                location = new File(locationStr);
                if (!location.isAbsolute()) {
                    location = new File(
                            (File) context.getServletContext().getAttribute(
                                        ServletContext.TEMPDIR),
                                        locationStr).getAbsoluteFile();
                }
            }

            if (!location.isDirectory()) {
                parameters.setParseFailedReason(FailReason.MULTIPART_CONFIG_INVALID);
                partsParseException = new IOException(
                        sm.getString("coyoteRequest.uploadLocationInvalid",
                                location));
                return;
            }


            // Create a new file upload handler
            DiskFileItemFactory factory = new DiskFileItemFactory();
            try {
                factory.setRepository(location.getCanonicalFile());
            } catch (IOException ioe) {
                parameters.setParseFailedReason(FailReason.IO_ERROR);
                partsParseException = ioe;
                return;
            }
            factory.setSizeThreshold(mce.getFileSizeThreshold());

            ServletFileUpload upload = new ServletFileUpload();
            upload.setFileItemFactory(factory);
            upload.setFileSizeMax(mce.getMaxFileSize());
            upload.setSizeMax(mce.getMaxRequestSize());

            parts = new ArrayList<Part>();
            try {
                List<FileItem> items =
                        upload.parseRequest(new ServletRequestContext(this));
                int maxPostSize = getConnector().getMaxPostSize();
                int postSize = 0;
                String enc = getCharacterEncoding();
                Charset charset = null;
                if (enc != null) {
                    try {
                        charset = B2CConverter.getCharset(enc);
                    } catch (UnsupportedEncodingException e) {
                        // Ignore
                    }
                }
                for (FileItem item : items) {
                    ApplicationPart part = new ApplicationPart(item, location);
                    parts.add(part);
                    if (part.getSubmittedFileName() == null) {
                        String name = part.getName();
                        String value = null;
                        try {
                            String encoding = parameters.getEncoding();
                            if (encoding == null) {
                                if (enc == null) {
                                    encoding = Parameters.DEFAULT_ENCODING;
                                } else {
                                    encoding = enc;
                                }
                            }
                            value = part.getString(encoding);
                        } catch (UnsupportedEncodingException uee) {
                            try {
                                value = part.getString(Parameters.DEFAULT_ENCODING);
                            } catch (UnsupportedEncodingException e) {
                                // Should not be possible
                            }
                        }
                        if (maxPostSize >= 0) {
                            // Have to calculate equivalent size. Not completely
                            // accurate but close enough.
                            if (charset == null) {
                                // Name length
                                postSize += name.getBytes().length;
                            } else {
                                postSize += name.getBytes(charset).length;
                            }
                            if (value != null) {
                                // Equals sign
                                postSize++;
                                // Value length
                                postSize += part.getSize();
                            }
                            // Value separator
                            postSize++;
                            if (postSize > maxPostSize) {
                                parameters.setParseFailedReason(FailReason.POST_TOO_LARGE);
                                throw new IllegalStateException(sm.getString(
                                        "coyoteRequest.maxPostSizeExceeded"));
                            }
                        }
                        parameters.addParameter(name, value);
                    }
                }

                success = true;
            } catch (InvalidContentTypeException e) {
                parameters.setParseFailedReason(FailReason.INVALID_CONTENT_TYPE);
                partsParseException = new ServletException(e);
            } catch (FileUploadBase.SizeException e) {
                parameters.setParseFailedReason(FailReason.POST_TOO_LARGE);
                checkSwallowInput();
                partsParseException = new IllegalStateException(e);
            } catch (FileUploadException e) {
                parameters.setParseFailedReason(FailReason.IO_ERROR);
                partsParseException = new IOException(e);
            } catch (IllegalStateException e) {
                // addParameters() will set parseFailedReason
                checkSwallowInput();
                partsParseException = e;
            }
        } finally {
            if (partsParseException != null || !success) {
                parameters.setParseFailedReason(FailReason.UNKNOWN);
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Part getPart(String name) throws IOException, IllegalStateException,
            ServletException {
        Collection<Part> c = getParts();
        Iterator<Part> iterator = c.iterator();
        while (iterator.hasNext()) {
            Part part = iterator.next();
            if (name.equals(part.getName())) {
                return part;
            }
        }
        return null;
    }


    // --------------------------------- Tomcat proprietary HTTP upgrade methods

    /**
     * @deprecated  Will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    public void doUpgrade(org.apache.coyote.http11.upgrade.UpgradeInbound inbound)
            throws IOException {

        coyoteRequest.action(ActionCode.UPGRADE_TOMCAT, inbound);

        // Output required by RFC2616. Protocol specific headers should have
        // already been set.
        response.setStatus(HttpServletResponse.SC_SWITCHING_PROTOCOLS);
        response.flushBuffer();
    }


    // ---------------------------------- Servlet 3.1 based HTTP upgrade methods

    @SuppressWarnings("unchecked")
    public <T extends HttpUpgradeHandler> T upgrade(
            Class<T> httpUpgradeHandlerClass) throws ServletException {
            
        T handler;
        try {
            handler = (T) context.getInstanceManager().newInstance(httpUpgradeHandlerClass);
        } catch (InstantiationException e) {
            throw new ServletException(e);
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        } catch (InvocationTargetException e) {
            throw new ServletException(e);
        } catch (NamingException e) {
            throw new ServletException(e);
        }

        coyoteRequest.action(ActionCode.UPGRADE, handler);

        // Output required by RFC2616. Protocol specific headers should have
        // already been set.
        response.setStatus(HttpServletResponse.SC_SWITCHING_PROTOCOLS);

        return handler;
    }


    // ------------------------------------------------------ Protected Methods


    protected Session doGetSession(boolean create) {

        // There cannot be a session if no context has been assigned yet
        Context context = getContext();
        if (context == null) {
            return (null);
        }

        // Return the current session if it exists and is valid
        if ((session != null) && !session.isValid()) {
            session = null;
        }
        if (session != null) {
            return (session);
        }

        // Return the requested session if it exists and is valid
        Manager manager = context.getManager();
        if (manager == null) {
            return null;        // Sessions are not supported
        }
        if (requestedSessionId != null) {
            try {
                session = manager.findSession(requestedSessionId);
            } catch (IOException e) {
                session = null;
            }
            if ((session != null) && !session.isValid()) {
                session = null;
            }
            if (session != null) {
                session.access();
                return (session);
            }
        }

        // Create a new session if requested and the response is not committed
        if (!create) {
            return (null);
        }
        if ((response != null) &&
            context.getServletContext().getEffectiveSessionTrackingModes().
                    contains(SessionTrackingMode.COOKIE) &&
            response.getResponse().isCommitted()) {
            throw new IllegalStateException
              (sm.getString("coyoteRequest.sessionCreateCommitted"));
        }

        // Re-use session IDs provided by the client in very limited
        // circumstances.
        String sessionId = getRequestedSessionId();
        if (requestedSessionSSL) {
            // If the session ID has been obtained from the SSL handshake then
            // use it.
        } else if (("/".equals(context.getSessionCookiePath())
                && isRequestedSessionIdFromCookie())) {
            /* This is the common(ish) use case: using the same session ID with
             * multiple web applications on the same host. Typically this is
             * used by Portlet implementations. It only works if sessions are
             * tracked via cookies. The cookie must have a path of "/" else it
             * won't be provided for requests to all web applications.
             *
             * Any session ID provided by the client should be for a session
             * that already exists somewhere on the host. Check if the context
             * is configured for this to be confirmed.
             */
            if (context.getValidateClientProvidedNewSessionId()) {
                boolean found = false;
                for (Container container : getHost().findChildren()) {
                    Manager m = ((Context) container).getManager();
                    if (m != null) {
                        try {
                            if (m.findSession(sessionId) != null) {
                                found = true;
                                break;
                            }
                        } catch (IOException e) {
                            // Ignore. Problems with this manager will be
                            // handled elsewhere.
                        }
                    }
                }
                if (!found) {
                    sessionId = null;
                }
            }
        } else {
            sessionId = null;
        }
        session = manager.createSession(sessionId);

        // Creating a new session cookie based on that session
        if ((session != null) && (getContext() != null)
               && getContext().getServletContext().
                       getEffectiveSessionTrackingModes().contains(
                               SessionTrackingMode.COOKIE)) {
            Cookie cookie =
                ApplicationSessionCookieConfig.createSessionCookie(
                        context, session.getIdInternal(), isSecure());

            response.addSessionCookieInternal(cookie);
        }

        if (session == null) {
            return null;
        }

        session.access();
        return session;
    }

    protected String unescape(String s) {
        if (s==null) {
            return null;
        }
        if (s.indexOf('\\') == -1) {
            return s;
        }
        StringBuilder buf = new StringBuilder();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c!='\\') {
                buf.append(c);
            } else {
                if (++i >= s.length())
                 {
                    throw new IllegalArgumentException();//invalid escape, hence invalid cookie
                }
                c = s.charAt(i);
                buf.append(c);
            }
        }
        return buf.toString();
    }

    /**
     * Parse cookies.
     */
    protected void parseCookies() {

        cookiesParsed = true;

        Cookies serverCookies = coyoteRequest.getCookies();
        int count = serverCookies.getCookieCount();
        if (count <= 0) {
            return;
        }

        cookies = new Cookie[count];

        int idx=0;
        for (int i = 0; i < count; i++) {
            ServerCookie scookie = serverCookies.getCookie(i);
            try {
                /*
                we must unescape the '\\' escape character
                */
                Cookie cookie = new Cookie(scookie.getName().toString(),null);
                int version = scookie.getVersion();
                cookie.setVersion(version);
                cookie.setValue(unescape(scookie.getValue().toString()));
                cookie.setPath(unescape(scookie.getPath().toString()));
                String domain = scookie.getDomain().toString();
                if (domain!=null)
                 {
                    cookie.setDomain(unescape(domain));//avoid NPE
                }
                String comment = scookie.getComment().toString();
                cookie.setComment(version==1?unescape(comment):null);
                cookies[idx++] = cookie;
            } catch(IllegalArgumentException e) {
                // Ignore bad cookie
            }
        }
        if( idx < count ) {
            Cookie [] ncookies = new Cookie[idx];
            System.arraycopy(cookies, 0, ncookies, 0, idx);
            cookies = ncookies;
        }

    }

    /**
     * Parse request parameters.
     */
    protected void parseParameters() {

        parametersParsed = true;

        Parameters parameters = coyoteRequest.getParameters();
        boolean success = false;
        try {
            // Set this every time in case limit has been changed via JMX
            parameters.setLimit(getConnector().getMaxParameterCount());

            // getCharacterEncoding() may have been overridden to search for
            // hidden form field containing request encoding
            String enc = getCharacterEncoding();

            boolean useBodyEncodingForURI = connector.getUseBodyEncodingForURI();
            if (enc != null) {
                parameters.setEncoding(enc);
                if (useBodyEncodingForURI) {
                    parameters.setQueryStringEncoding(enc);
                }
            } else {
                parameters.setEncoding
                    (org.apache.coyote.Constants.DEFAULT_CHARACTER_ENCODING);
                if (useBodyEncodingForURI) {
                    parameters.setQueryStringEncoding
                        (org.apache.coyote.Constants.DEFAULT_CHARACTER_ENCODING);
                }
            }

            parameters.handleQueryParameters();

            if (usingInputStream || usingReader) {
                success = true;
                return;
            }

            if( !getConnector().isParseBodyMethod(getMethod()) ) {
                success = true;
                return;
            }

            String contentType = getContentType();
            if (contentType == null) {
                contentType = "";
            }
            int semicolon = contentType.indexOf(';');
            if (semicolon >= 0) {
                contentType = contentType.substring(0, semicolon).trim();
            } else {
                contentType = contentType.trim();
            }

            if ("multipart/form-data".equals(contentType)) {
                parseParts();
                success = true;
                return;
            }

            if (!("application/x-www-form-urlencoded".equals(contentType))) {
                success = true;
                return;
            }

            int len = getContentLength();

            if (len > 0) {
                int maxPostSize = connector.getMaxPostSize();
                if ((maxPostSize >= 0) && (len > maxPostSize)) {
                    if (context.getLogger().isDebugEnabled()) {
                        context.getLogger().debug(
                                sm.getString("coyoteRequest.postTooLarge"));
                    }
                    checkSwallowInput();
                    parameters.setParseFailedReason(FailReason.POST_TOO_LARGE);
                    return;
                }
                byte[] formData = null;
                if (len < CACHED_POST_LEN) {
                    if (postData == null) {
                        postData = new byte[CACHED_POST_LEN];
                    }
                    formData = postData;
                } else {
                    formData = new byte[len];
                }
                try {
                    if (readPostBody(formData, len) != len) {
                        parameters.setParseFailedReason(FailReason.REQUEST_BODY_INCOMPLETE);
                        return;
                    }
                } catch (IOException e) {
                    // Client disconnect
                    if (context.getLogger().isDebugEnabled()) {
                        context.getLogger().debug(
                                sm.getString("coyoteRequest.parseParameters"), e);
                    }
                    parameters.setParseFailedReason(FailReason.CLIENT_DISCONNECT);
                    return;
                }
                parameters.processParameters(formData, 0, len);
            } else if ("chunked".equalsIgnoreCase(
                    coyoteRequest.getHeader("transfer-encoding"))) {
                byte[] formData = null;
                try {
                    formData = readChunkedPostBody();
                } catch (IllegalStateException ise) {
                    // chunkedPostTooLarge error
                    parameters.setParseFailedReason(FailReason.POST_TOO_LARGE);
                    Context context = getContext();
                    if (context != null && context.getLogger().isDebugEnabled()) {
                        context.getLogger().debug(
                                sm.getString("coyoteRequest.parseParameters"),
                                ise);
                    }
                    return;
                } catch (IOException e) {
                    // Client disconnect
                    parameters.setParseFailedReason(FailReason.CLIENT_DISCONNECT);
                    Context context = getContext();
                    if (context != null && context.getLogger().isDebugEnabled()) {
                        context.getLogger().debug(
                                sm.getString("coyoteRequest.parseParameters"), e);
                    }
                    return;
                }
                if (formData != null) {
                    parameters.processParameters(formData, 0, formData.length);
                }
            }
            success = true;
        } finally {
            if (!success) {
                parameters.setParseFailedReason(FailReason.UNKNOWN);
            }
        }

    }


    /**
     * Read post body in an array.
     */
    protected int readPostBody(byte body[], int len)
        throws IOException {

        int offset = 0;
        do {
            int inputLen = getStream().read(body, offset, len - offset);
            if (inputLen <= 0) {
                return offset;
            }
            offset += inputLen;
        } while ((len - offset) > 0);
        return len;

    }


    /**
     * Read chunked post body.
     */
    protected byte[] readChunkedPostBody() throws IOException {
        ByteChunk body = new ByteChunk();

        byte[] buffer = new byte[CACHED_POST_LEN];

        int len = 0;
        while (len > -1) {
            len = getStream().read(buffer, 0, CACHED_POST_LEN);
            if (connector.getMaxPostSize() >= 0 &&
                    (body.getLength() + len) > connector.getMaxPostSize()) {
                // Too much data
                checkSwallowInput();
                throw new IllegalStateException(
                        sm.getString("coyoteRequest.chunkedPostTooLarge"));
            }
            if (len > 0) {
                body.append(buffer, 0, len);
            }
        }
        if (body.getLength() == 0) {
            return null;
        }
        if (body.getLength() < body.getBuffer().length) {
            int length = body.getLength();
            byte[] result = new byte[length];
            System.arraycopy(body.getBuffer(), 0, result, 0, length);
            return result;
        }

        return body.getBuffer();
    }


    /**
     * Parse request locales.
     */
    protected void parseLocales() {

        localesParsed = true;

        // Store the accumulated languages that have been requested in
        // a local collection, sorted by the quality value (so we can
        // add Locales in descending order).  The values will be ArrayLists
        // containing the corresponding Locales to be added
        TreeMap<Double, ArrayList<Locale>> locales = new TreeMap<Double, ArrayList<Locale>>();

        Enumeration<String> values = getHeaders("accept-language");

        while (values.hasMoreElements()) {
            String value = values.nextElement();
            parseLocalesHeader(value, locales);
        }

        // Process the quality values in highest->lowest order (due to
        // negating the Double value when creating the key)
        for (ArrayList<Locale> list : locales.values()) {
            for (Locale locale : list) {
                addLocale(locale);
            }
        }
    }


    /**
     * Parse accept-language header value.
     */
    protected void parseLocalesHeader(String value, TreeMap<Double, ArrayList<Locale>> locales) {

        // Preprocess the value to remove all whitespace
        int white = value.indexOf(' ');
        if (white < 0) {
            white = value.indexOf('\t');
        }
        if (white >= 0) {
            StringBuilder sb = new StringBuilder();
            int len = value.length();
            for (int i = 0; i < len; i++) {
                char ch = value.charAt(i);
                if ((ch != ' ') && (ch != '\t')) {
                    sb.append(ch);
                }
            }
            parser.setString(sb.toString());
        } else {
            parser.setString(value);
        }

        JreCompat jreCompat = JreCompat.getInstance();
        
        // Process each comma-delimited language specification
        int length = parser.getLength();
        while (true) {

            // Extract the next comma-delimited entry
            int start = parser.getIndex();
            if (start >= length) {
                break;
            }
            int end = parser.findChar(',');
            String entry = parser.extract(start, end).trim();
            parser.advance();   // For the following entry

            // Extract the quality factor for this entry
            double quality = 1.0;
            int semi = entry.indexOf(";q=");
            if (semi >= 0) {
                try {
                    String strQuality = entry.substring(semi + 3);
                    if (strQuality.length() <= 5) {
                        quality = Double.parseDouble(strQuality);
                    } else {
                        quality = 0.0;
                    }
                } catch (NumberFormatException e) {
                    quality = 0.0;
                }
                entry = entry.substring(0, semi);
            }

            // Skip entries we are not going to keep track of
            if (quality < 0.00005)
             {
                continue;       // Zero (or effectively zero) quality factors
            }
            if ("*".equals(entry))
             {
                continue;       // FIXME - "*" entries are not handled
            }

            Locale locale = jreCompat.forLanguageTag(entry);
            if (locale == null) {
                continue;
            }

            Double key = Double.valueOf(-quality);  // Reverse the order
            ArrayList<Locale> values = locales.get(key);
            if (values == null) {
                values = new ArrayList<Locale>();
                locales.put(key, values);
            }
            values.add(locale);
        }
    }


    // ----------------------------------------------------- Special attributes handling

    private static interface SpecialAttributeAdapter {
        Object get(Request request, String name);

        void set(Request request, String name, Object value);

        // None of special attributes support removal
        // void remove(Request request, String name);
    }

    private static final Map<String, SpecialAttributeAdapter> specialAttributes
        = new HashMap<String, SpecialAttributeAdapter>();

    static {
        specialAttributes.put(Globals.DISPATCHER_TYPE_ATTR,
                new SpecialAttributeAdapter() {
                    @Override
                    public Object get(Request request, String name) {
                        return (request.internalDispatcherType == null) ? DispatcherType.REQUEST
                                : request.internalDispatcherType;
                    }

                    @Override
                    public void set(Request request, String name, Object value) {
                        request.internalDispatcherType = (DispatcherType) value;
                    }
                });
        specialAttributes.put(Globals.DISPATCHER_REQUEST_PATH_ATTR,
                new SpecialAttributeAdapter() {
                    @Override
                    public Object get(Request request, String name) {
                        return (request.requestDispatcherPath == null) ? request
                                .getRequestPathMB().toString()
                                : request.requestDispatcherPath.toString();
                    }

                    @Override
                    public void set(Request request, String name, Object value) {
                        request.requestDispatcherPath = value;
                    }
                });
        specialAttributes.put(Globals.ASYNC_SUPPORTED_ATTR,
                new SpecialAttributeAdapter() {
                    @Override
                    public Object get(Request request, String name) {
                        return request.asyncSupported;
                    }

                    @Override
                    public void set(Request request, String name, Object value) {
                        Boolean oldValue = request.asyncSupported;
                        request.asyncSupported = (Boolean)value;
                        request.notifyAttributeAssigned(name, value, oldValue);
                    }
                });
        specialAttributes.put(Globals.GSS_CREDENTIAL_ATTR,
                new SpecialAttributeAdapter() {
                    @Override
                    public Object get(Request request, String name) {
                        if (request.userPrincipal instanceof GenericPrincipal) {
                            return ((GenericPrincipal) request.userPrincipal)
                                    .getGssCredential();
                        }
                        return null;
                    }

                    @Override
                    public void set(Request request, String name, Object value) {
                        // NO-OP
                    }
                });
        specialAttributes.put(Globals.PARAMETER_PARSE_FAILED_ATTR,
                new SpecialAttributeAdapter() {
                    @Override
                    public Object get(Request request, String name) {
                        if (request.getCoyoteRequest().getParameters()
                                .isParseFailed()) {
                            return Boolean.TRUE;
                        }
                        return null;
                    }

                    @Override
                    public void set(Request request, String name, Object value) {
                        // NO-OP
                    }
                });
        specialAttributes.put(Globals.PARAMETER_PARSE_FAILED_REASON_ATTR,
                new SpecialAttributeAdapter() {
                    @Override
                    public Object get(Request request, String name) {
                        return request.getCoyoteRequest().getParameters().getParseFailedReason();
                    }

                    @Override
                    public void set(Request request, String name, Object value) {
                        // NO-OP
                    }
                });
    }
}
