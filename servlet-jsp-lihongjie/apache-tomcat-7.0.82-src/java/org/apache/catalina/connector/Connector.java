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

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javax.management.ObjectName;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Service;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.mbeans.MBeanUtils;
import org.apache.catalina.util.LifecycleMBeanBase;
import org.apache.coyote.Adapter;
import org.apache.coyote.ProtocolHandler;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.IntrospectionUtils;
import org.apache.tomcat.util.http.mapper.Mapper;
import org.apache.tomcat.util.res.StringManager;


/**
 * Implementation of a Coyote connector.
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 */
public class Connector extends LifecycleMBeanBase  {

    private static final Log log = LogFactory.getLog(Connector.class);


    /**
     * Alternate flag to enable recycling of facades.
     */
    public static final boolean RECYCLE_FACADES =
        Boolean.parseBoolean(System.getProperty("org.apache.catalina.connector.RECYCLE_FACADES", "false"));


    // ------------------------------------------------------------ Constructor

    public Connector() {
        this(null);
    }

    public Connector(String protocol) {
        setProtocol(protocol);
        // Instantiate protocol handler
        try {
            Class<?> clazz = Class.forName(protocolHandlerClassName);
            this.protocolHandler = (ProtocolHandler) clazz.newInstance();
        } catch (Exception e) {
            log.error(sm.getString(
                    "coyoteConnector.protocolHandlerInstantiationFailed"), e);
        }
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The <code>Service</code> we are associated with (if any).
     */
    protected Service service = null;


    /**
     * Do we allow TRACE ?
     */
    protected boolean allowTrace = false;


    /**
     * Default timeout for asynchronous requests (ms).
     */
    protected  long asyncTimeout = 10000;


    /**
     * The "enable DNS lookups" flag for this Connector.
     */
    protected boolean enableLookups = false;


    /*
     * Is generation of X-Powered-By response header enabled/disabled?
     */
    protected boolean xpoweredBy = false;


    /**
     * Descriptive information about this Connector implementation.
     */
    protected static final String info =
        "org.apache.catalina.connector.Connector/2.1";


    /**
     * The port number on which we listen for requests.
     */
    protected int port = -1;


    /**
     * The server name to which we should pretend requests to this Connector
     * were directed.  This is useful when operating Tomcat behind a proxy
     * server, so that redirects get constructed accurately.  If not specified,
     * the server name included in the <code>Host</code> header is used.
     */
    protected String proxyName = null;


    /**
     * The server port to which we should pretend requests to this Connector
     * were directed.  This is useful when operating Tomcat behind a proxy
     * server, so that redirects get constructed accurately.  If not specified,
     * the port number specified by the <code>port</code> property is used.
     */
    protected int proxyPort = 0;


    /**
     * The redirect port for non-SSL to SSL redirects.
     */
    protected int redirectPort = 443;


    /**
     * The request scheme that will be set on all requests received
     * through this connector.
     */
    protected String scheme = "http";


    /**
     * The secure connection flag that will be set on all requests received
     * through this connector.
     */
    protected boolean secure = false;


    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    /**
     * The maximum number of parameters (GET plus POST) which will be
     * automatically parsed by the container. 10000 by default. A value of less
     * than 0 means no limit.
     */
    protected int maxParameterCount = 10000;

    /**
     * Maximum size of a POST which will be automatically parsed by the
     * container. 2MB by default.
     */
    protected int maxPostSize = 2 * 1024 * 1024;


    /**
     * Maximum size of a POST which will be saved by the container
     * during authentication. 4kB by default
     */
    protected int maxSavePostSize = 4 * 1024;

    /**
     * Comma-separated list of HTTP methods that will be parsed according
     * to POST-style rules for application/x-www-form-urlencoded request bodies.
     */
    protected String parseBodyMethods = "POST";

    /**
     * A Set of methods determined by {@link #parseBodyMethods}.
     */
    protected HashSet<String> parseBodyMethodsSet;


    /**
     * Flag to use IP-based virtual hosting.
     */
    protected boolean useIPVHosts = false;


    /**
     * Coyote Protocol handler class name.
     * Defaults to the Coyote HTTP/1.1 protocolHandler.
     */
    protected String protocolHandlerClassName =
        "org.apache.coyote.http11.Http11Protocol";


    /**
     * Coyote protocol handler.
     */
    protected ProtocolHandler protocolHandler = null;


    /**
     * Coyote adapter.
     */
    protected Adapter adapter = null;


     /**
      * Mapper.
      */
     protected Mapper mapper = new Mapper();


     /**
      * Mapper listener.
      */
     protected MapperListener mapperListener = new MapperListener(mapper, this);


     /**
      * URI encoding.
      */
     protected String URIEncoding = null;


     /**
      * URI encoding as body.
      */
     protected boolean useBodyEncodingForURI = false;


     protected static HashMap<String,String> replacements =
         new HashMap<String,String>();
     static {
         replacements.put("acceptCount", "backlog");
         replacements.put("connectionLinger", "soLinger");
         replacements.put("connectionTimeout", "soTimeout");
         replacements.put("rootFile", "rootfile");
     }


    // ------------------------------------------------------------- Properties

    /**
     * Return a configured property.
     */
    public Object getProperty(String name) {
        String repl = name;
        if (replacements.get(name) != null) {
            repl = replacements.get(name);
        }
        return IntrospectionUtils.getProperty(protocolHandler, repl);
    }


    /**
     * Set a configured property.
     */
    public boolean setProperty(String name, String value) {
        String repl = name;
        if (replacements.get(name) != null) {
            repl = replacements.get(name);
        }
        return IntrospectionUtils.setProperty(protocolHandler, repl, value);
    }

    /**
     * Return a configured property.
     */
    public Object getAttribute(String name) {
        return getProperty(name);
    }


    /**
     * Set a configured property.
     */
    public void setAttribute(String name, Object value) {
        setProperty(name, String.valueOf(value));
    }


    /**
     * Return the <code>Service</code> with which we are associated (if any).
     */
    public Service getService() {

        return (this.service);

    }


    /**
     * Set the <code>Service</code> with which we are associated (if any).
     *
     * @param service The service that owns this Engine
     */
    public void setService(Service service) {

        this.service = service;

    }


    /**
     * True if the TRACE method is allowed.  Default value is "false".
     */
    public boolean getAllowTrace() {

        return (this.allowTrace);

    }


    /**
     * Set the allowTrace flag, to disable or enable the TRACE HTTP method.
     *
     * @param allowTrace The new allowTrace flag
     */
    public void setAllowTrace(boolean allowTrace) {

        this.allowTrace = allowTrace;
        setProperty("allowTrace", String.valueOf(allowTrace));

    }


    /**
     * Return the default timeout for async requests in ms.
     */
    public long getAsyncTimeout() {

        return asyncTimeout;

    }


    /**
     * Set the default timeout for async requests.
     *
     * @param asyncTimeout The new timeout in ms.
     */
    public void setAsyncTimeout(long asyncTimeout) {

        this.asyncTimeout= asyncTimeout;
        setProperty("asyncTimeout", String.valueOf(asyncTimeout));

    }


    /**
     * Return the "enable DNS lookups" flag.
     */
    public boolean getEnableLookups() {

        return (this.enableLookups);

    }


    /**
     * Set the "enable DNS lookups" flag.
     *
     * @param enableLookups The new "enable DNS lookups" flag value
     */
    public void setEnableLookups(boolean enableLookups) {

        this.enableLookups = enableLookups;
        setProperty("enableLookups", String.valueOf(enableLookups));

    }


    /**
     * Return descriptive information about this Connector implementation.
     */
    public String getInfo() {

        return (info);

    }


    /**
     * Return the mapper.
     */
    public Mapper getMapper() {
        return (mapper);
    }


    /**
     * Return the maximum number of headers that are allowed by the container. A
     * value of less than 0 means no limit.
     */
    public int getMaxHeaderCount() {
        return ((Integer) getProperty("maxHeaderCount")).intValue();
    }

    /**
     * Set the maximum number of headers in a request that are allowed by the
     * container. A value of less than 0 means no limit.
     *
     * @param maxHeaderCount The new setting
     */
    public void setMaxHeaderCount(int maxHeaderCount) {
        setProperty("maxHeaderCount", String.valueOf(maxHeaderCount));
    }

    /**
     * Return the maximum number of parameters (GET plus POST) that will be
     * automatically parsed by the container. A value of less than 0 means no
     * limit.
     */
    public int getMaxParameterCount() {
        return maxParameterCount;
    }


    /**
     * Set the maximum number of parameters (GET plus POST) that will be
     * automatically parsed by the container. A value of less than 0 means no
     * limit.
     *
     * @param maxParameterCount The new setting
     */
    public void setMaxParameterCount(int maxParameterCount) {
        this.maxParameterCount = maxParameterCount;
    }


    /**
     * Return the maximum size of a POST which will be automatically
     * parsed by the container.
     */
    public int getMaxPostSize() {

        return (maxPostSize);

    }


    /**
     * Set the maximum size of a POST which will be automatically
     * parsed by the container.
     *
     * @param maxPostSize The new maximum size in bytes of a POST which will
     * be automatically parsed by the container
     */
    public void setMaxPostSize(int maxPostSize) {

        this.maxPostSize = maxPostSize;
    }


    /**
     * Return the maximum size of a POST which will be saved by the container
     * during authentication.
     */
    public int getMaxSavePostSize() {

        return (maxSavePostSize);

    }


    /**
     * Set the maximum size of a POST which will be saved by the container
     * during authentication.
     *
     * @param maxSavePostSize The new maximum size in bytes of a POST which will
     * be saved by the container during authentication.
     */
    public void setMaxSavePostSize(int maxSavePostSize) {

        this.maxSavePostSize = maxSavePostSize;
        setProperty("maxSavePostSize", String.valueOf(maxSavePostSize));
    }


    public String getParseBodyMethods() {

        return this.parseBodyMethods;

    }

    public void setParseBodyMethods(String methods) {

        HashSet<String> methodSet = new HashSet<String>();

        if( null != methods ) {
            methodSet.addAll(Arrays.asList(methods.split("\\s*,\\s*")));
        }

        if( methodSet.contains("TRACE") ) {
            throw new IllegalArgumentException(sm.getString("coyoteConnector.parseBodyMethodNoTrace"));
        }

        this.parseBodyMethods = methods;
        this.parseBodyMethodsSet = methodSet;

    }

    protected boolean isParseBodyMethod(String method) {

        return parseBodyMethodsSet.contains(method);

    }

    /**
     * Return the port number on which this connector is configured to listen
     * for requests. The special value of 0 means select a random free port
     * when the socket is bound.
     */
    public int getPort() {

        return (this.port);

    }


    /**
     * Set the port number on which we listen for requests.
     *
     * @param port The new port number
     */
    public void setPort(int port) {

        this.port = port;
        setProperty("port", String.valueOf(port));

    }


    /**
     * Return the port number on which this connector is listening to requests.
     * If the special value for {@link #port} of zero is used then this method
     * will report the actual port bound.
     */
    public int getLocalPort() {
        return ((Integer) getProperty("localPort")).intValue();
    }


    /**
     * Return the Coyote protocol handler in use.
     */
    public String getProtocol() {

        if ("org.apache.coyote.http11.Http11Protocol".equals
            (getProtocolHandlerClassName())
            || "org.apache.coyote.http11.Http11AprProtocol".equals
            (getProtocolHandlerClassName())) {
            return "HTTP/1.1";
        } else if ("org.apache.coyote.ajp.AjpProtocol".equals
                   (getProtocolHandlerClassName())
                   || "org.apache.coyote.ajp.AjpAprProtocol".equals
                   (getProtocolHandlerClassName())) {
            return "AJP/1.3";
        }
        return getProtocolHandlerClassName();

    }


    /**
     * Set the Coyote protocol which will be used by the connector.
     *
     * @param protocol The Coyote protocol name
     */
    public void setProtocol(String protocol) {

        if (AprLifecycleListener.isAprAvailable()) {
            if ("HTTP/1.1".equals(protocol)) {
                setProtocolHandlerClassName
                    ("org.apache.coyote.http11.Http11AprProtocol");
            } else if ("AJP/1.3".equals(protocol)) {
                setProtocolHandlerClassName
                    ("org.apache.coyote.ajp.AjpAprProtocol");
            } else if (protocol != null) {
                setProtocolHandlerClassName(protocol);
            } else {
                setProtocolHandlerClassName
                    ("org.apache.coyote.http11.Http11AprProtocol");
            }
        } else {
            if ("HTTP/1.1".equals(protocol)) {
                setProtocolHandlerClassName
                    ("org.apache.coyote.http11.Http11Protocol");
            } else if ("AJP/1.3".equals(protocol)) {
                setProtocolHandlerClassName
                    ("org.apache.coyote.ajp.AjpProtocol");
            } else if (protocol != null) {
                setProtocolHandlerClassName(protocol);
            }
        }

    }


    /**
     * Return the class name of the Coyote protocol handler in use.
     */
    public String getProtocolHandlerClassName() {

        return (this.protocolHandlerClassName);

    }


    /**
     * Set the class name of the Coyote protocol handler which will be used
     * by the connector.
     *
     * @param protocolHandlerClassName The new class name
     */
    public void setProtocolHandlerClassName(String protocolHandlerClassName) {

        this.protocolHandlerClassName = protocolHandlerClassName;

    }


    /**
     * Return the protocol handler associated with the connector.
     */
    public ProtocolHandler getProtocolHandler() {

        return (this.protocolHandler);

    }


    /**
     * Return the proxy server name for this Connector.
     */
    public String getProxyName() {

        return (this.proxyName);

    }


    /**
     * Set the proxy server name for this Connector.
     *
     * @param proxyName The new proxy server name
     */
    public void setProxyName(String proxyName) {

        if(proxyName != null && proxyName.length() > 0) {
            this.proxyName = proxyName;
            setProperty("proxyName", proxyName);
        } else {
            this.proxyName = null;
        }

    }


    /**
     * Return the proxy server port for this Connector.
     */
    public int getProxyPort() {

        return (this.proxyPort);

    }


    /**
     * Set the proxy server port for this Connector.
     *
     * @param proxyPort The new proxy server port
     */
    public void setProxyPort(int proxyPort) {

        this.proxyPort = proxyPort;
        setProperty("proxyPort", String.valueOf(proxyPort));

    }


    /**
     * Return the port number to which a request should be redirected if
     * it comes in on a non-SSL port and is subject to a security constraint
     * with a transport guarantee that requires SSL.
     */
    public int getRedirectPort() {

        return (this.redirectPort);

    }


    /**
     * Set the redirect port number.
     *
     * @param redirectPort The redirect port number (non-SSL to SSL)
     */
    public void setRedirectPort(int redirectPort) {

        this.redirectPort = redirectPort;
        setProperty("redirectPort", String.valueOf(redirectPort));

    }


    /**
     * Return the scheme that will be assigned to requests received
     * through this connector.  Default value is "http".
     */
    public String getScheme() {

        return (this.scheme);

    }


    /**
     * Set the scheme that will be assigned to requests received through
     * this connector.
     *
     * @param scheme The new scheme
     */
    public void setScheme(String scheme) {

        this.scheme = scheme;

    }


    /**
     * Return the secure connection flag that will be assigned to requests
     * received through this connector.  Default value is "false".
     */
    public boolean getSecure() {

        return (this.secure);

    }


    /**
     * Set the secure connection flag that will be assigned to requests
     * received through this connector.
     *
     * @param secure The new secure connection flag
     */
    public void setSecure(boolean secure) {

        this.secure = secure;
        setProperty("secure", Boolean.toString(secure));
    }

     /**
      * Return the character encoding to be used for the URI.
      */
     public String getURIEncoding() {

         return (this.URIEncoding);

     }


     /**
      * Set the URI encoding to be used for the URI.
      *
      * @param URIEncoding The new URI character encoding.
      */
     public void setURIEncoding(String URIEncoding) {

         this.URIEncoding = URIEncoding;
         setProperty("uRIEncoding", URIEncoding);

     }


     /**
      * Return the true if the entity body encoding should be used for the URI.
      */
     public boolean getUseBodyEncodingForURI() {

         return (this.useBodyEncodingForURI);

     }


     /**
      * Set if the entity body encoding should be used for the URI.
      *
      * @param useBodyEncodingForURI The new value for the flag.
      */
     public void setUseBodyEncodingForURI(boolean useBodyEncodingForURI) {

         this.useBodyEncodingForURI = useBodyEncodingForURI;
         setProperty
             ("useBodyEncodingForURI", String.valueOf(useBodyEncodingForURI));

     }

    /**
     * Indicates whether the generation of an X-Powered-By response header for
     * servlet-generated responses is enabled or disabled for this Connector.
     *
     * @return true if generation of X-Powered-By response header is enabled,
     * false otherwise
     */
    public boolean getXpoweredBy() {
        return xpoweredBy;
    }


    /**
     * Enables or disables the generation of an X-Powered-By header (with value
     * Servlet/2.5) for all servlet-generated responses returned by this
     * Connector.
     *
     * @param xpoweredBy true if generation of X-Powered-By response header is
     * to be enabled, false otherwise
     */
    public void setXpoweredBy(boolean xpoweredBy) {
        this.xpoweredBy = xpoweredBy;
        setProperty("xpoweredBy", String.valueOf(xpoweredBy));
    }

    /**
     * Enable the use of IP-based virtual hosting.
     *
     * @param useIPVHosts <code>true</code> if Hosts are identified by IP,
     *                    <code>false/code> if Hosts are identified by name.
     */
    public void setUseIPVHosts(boolean useIPVHosts) {
        this.useIPVHosts = useIPVHosts;
        setProperty("useIPVHosts", String.valueOf(useIPVHosts));
    }

    /**
     * Test if IP-based virtual hosting is enabled.
     */
    public boolean getUseIPVHosts() {
        return useIPVHosts;
    }


    public String getExecutorName() {
        Object obj = protocolHandler.getExecutor();
        if (obj instanceof org.apache.catalina.Executor) {
            return ((org.apache.catalina.Executor) obj).getName();
        }
        return "Internal";
    }

    // --------------------------------------------------------- Public Methods


    /**
     * Create (or allocate) and return a Request object suitable for
     * specifying the contents of a Request to the responsible Container.
     */
    public Request createRequest() {

        Request request = new Request();
        request.setConnector(this);
        return (request);

    }


    /**
     * Create (or allocate) and return a Response object suitable for
     * receiving the contents of a Response from the responsible Container.
     */
    public Response createResponse() {

        Response response = new Response();
        response.setConnector(this);
        return (response);

    }


    protected String createObjectNameKeyProperties(String type) {

        Object addressObj = getProperty("address");

        StringBuilder sb = new StringBuilder("type=");
        sb.append(type);
        sb.append(",port=");
        int port = getPort();
        if (port > 0) {
            sb.append(port);
        } else {
            sb.append("auto-");
            sb.append(getProperty("nameIndex"));
        }
        String address = "";
        if (addressObj instanceof InetAddress) {
            address = ((InetAddress) addressObj).getHostAddress();
        } else if (addressObj != null) {
            address = addressObj.toString();
        }
        if (address.length() > 0) {
            sb.append(",address=");
            sb.append(ObjectName.quote(address));
        }
        return sb.toString();
    }


    /**
     * Pause the connector.
     */
    public void pause() {
        try {
            protocolHandler.pause();
        } catch (Exception e) {
            log.error(sm.getString
                      ("coyoteConnector.protocolHandlerPauseFailed"), e);
        }
    }


    /**
     * Pause the connector.
     */
    public void resume() {
        try {
            protocolHandler.resume();
        } catch (Exception e) {
            log.error(sm.getString
                      ("coyoteConnector.protocolHandlerResumeFailed"), e);
        }
    }


    @Override
    protected void initInternal() throws LifecycleException {

        super.initInternal();

        // Initialize adapter
        adapter = new CoyoteAdapter(this);
        protocolHandler.setAdapter(adapter);

        // Make sure parseBodyMethodsSet has a default
        if( null == parseBodyMethodsSet ) {
            setParseBodyMethods(getParseBodyMethods());
        }

        if (protocolHandler.isAprRequired() &&
                !AprLifecycleListener.isAprAvailable()) {
            throw new LifecycleException(
                    sm.getString("coyoteConnector.protocolHandlerNoApr",
                            getProtocolHandlerClassName()));
        }

        try {
            protocolHandler.init();
        } catch (Exception e) {
            throw new LifecycleException
                (sm.getString
                 ("coyoteConnector.protocolHandlerInitializationFailed"), e);
        }

        // Initialize mapper listener
        mapperListener.init();
    }


    /**
     * Begin processing requests via this Connector.
     *
     * @exception LifecycleException if a fatal startup error occurs
     */
    @Override
    protected void startInternal() throws LifecycleException {

        // Validate settings before starting
        if (getPort() < 0) {
            throw new LifecycleException(sm.getString(
                    "coyoteConnector.invalidPort", Integer.valueOf(getPort())));
        }

        setState(LifecycleState.STARTING);

        try {
            protocolHandler.start();
        } catch (Exception e) {
            String errPrefix = "";
            if(this.service != null) {
                errPrefix += "service.getName(): \"" + this.service.getName() + "\"; ";
            }

            throw new LifecycleException
                (errPrefix + " " + sm.getString
                 ("coyoteConnector.protocolHandlerStartFailed"), e);
        }

        mapperListener.start();
    }


    /**
     * Terminate processing requests via this Connector.
     *
     * @exception LifecycleException if a fatal shutdown error occurs
     */
    @Override
    protected void stopInternal() throws LifecycleException {

        setState(LifecycleState.STOPPING);

        try {
            protocolHandler.stop();
        } catch (Exception e) {
            throw new LifecycleException
                (sm.getString
                 ("coyoteConnector.protocolHandlerStopFailed"), e);
        }

        mapperListener.stop();
    }


    @Override
    protected void destroyInternal() throws LifecycleException {
        mapperListener.destroy();

        try {
            protocolHandler.destroy();
        } catch (Exception e) {
            throw new LifecycleException
                (sm.getString
                 ("coyoteConnector.protocolHandlerDestroyFailed"), e);
        }

        if (getService() != null) {
            getService().removeConnector(this);
        }

        super.destroyInternal();
    }


    /**
     * Provide a useful toString() implementation as it may be used when logging
     * Lifecycle errors to identify the component.
     */
    @Override
    public String toString() {
        // Not worth caching this right now
        StringBuilder sb = new StringBuilder("Connector[");
        sb.append(getProtocol());
        sb.append('-');
        int port = getPort();
        if (port > 0) {
            sb.append(port);
        } else {
            sb.append("auto-");
            sb.append(getProperty("nameIndex"));
        }
        sb.append(']');
        return sb.toString();
    }


    // -------------------- JMX registration  --------------------

    @Override
    protected String getDomainInternal() {
        return MBeanUtils.getDomain(getService());
    }

    @Override
    protected String getObjectNameKeyProperties() {
        return createObjectNameKeyProperties("Connector");
    }

}
