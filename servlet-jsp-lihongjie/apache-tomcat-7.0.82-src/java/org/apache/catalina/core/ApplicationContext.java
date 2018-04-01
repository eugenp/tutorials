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
package org.apache.catalina.core;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.naming.Binding;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Globals;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Service;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.util.ResourceSet;
import org.apache.catalina.util.ServerInfo;
import org.apache.catalina.util.URLEncoder;
import org.apache.naming.resources.DirContextURLStreamHandler;
import org.apache.naming.resources.Resource;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.buf.CharChunk;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.http.RequestUtil;
import org.apache.tomcat.util.http.mapper.MappingData;
import org.apache.tomcat.util.res.StringManager;


/**
 * Standard implementation of <code>ServletContext</code> that represents
 * a web application's execution environment.  An instance of this class is
 * associated with each instance of <code>StandardContext</code>.
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 */
public class ApplicationContext implements ServletContext {

    protected static final boolean STRICT_SERVLET_COMPLIANCE;

    protected static final boolean GET_RESOURCE_REQUIRE_SLASH;


    static {
        STRICT_SERVLET_COMPLIANCE = Globals.STRICT_SERVLET_COMPLIANCE;

        String requireSlash = System.getProperty(
                "org.apache.catalina.core.ApplicationContext.GET_RESOURCE_REQUIRE_SLASH");
        if (requireSlash == null) {
            GET_RESOURCE_REQUIRE_SLASH = STRICT_SERVLET_COMPLIANCE;
        } else {
            GET_RESOURCE_REQUIRE_SLASH = Boolean.parseBoolean(requireSlash);
        }
    }

    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new instance of this class, associated with the specified
     * Context instance.
     *
     * @param context The associated Context instance
     */
    public ApplicationContext(StandardContext context) {
        super();
        this.context = context;
        this.sessionCookieConfig = new ApplicationSessionCookieConfig(context);

        // Populate session tracking modes
        populateSessionTrackingModes();
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The context attributes for this context.
     */
    protected Map<String,Object> attributes =
        new ConcurrentHashMap<String,Object>();


    /**
     * List of read only attributes for this context.
     */
    private final Map<String,String> readOnlyAttributes = new ConcurrentHashMap<String,String>();


    /**
     * The Context instance with which we are associated.
     */
    private StandardContext context = null;


    /**
     * Empty String collection to serve as the basis for empty enumerations.
     */
    private static final List<String> emptyString = Collections.emptyList();

    /**
     * Empty Servlet collection to serve as the basis for empty enumerations.
     */
    private static final List<Servlet> emptyServlet = Collections.emptyList();


    /**
     * The facade around this object.
     */
    private ServletContext facade = new ApplicationContextFacade(this);


    /**
     * The merged context initialization parameters for this Context.
     */
    private final ConcurrentMap<String,String> parameters = new ConcurrentHashMap<String,String>();


    /**
     * The string manager for this package.
     */
    private static final StringManager sm =
      StringManager.getManager(Constants.Package);


    /**
     * Thread local data used during request dispatch.
     */
    private ThreadLocal<DispatchData> dispatchData =
        new ThreadLocal<DispatchData>();


    /**
     * Session Cookie config
     */
    private SessionCookieConfig sessionCookieConfig;

    /**
     * Session tracking modes
     */
    private Set<SessionTrackingMode> sessionTrackingModes = null;
    private Set<SessionTrackingMode> defaultSessionTrackingModes = null;
    private Set<SessionTrackingMode> supportedSessionTrackingModes = null;

    /**
     * Flag that indicates if a new {@link ServletContextListener} may be added
     * to the application. Once the first {@link ServletContextListener} is
     * called, no more may be added.
     */
    private boolean newServletContextListenerAllowed = true;


    // --------------------------------------------------------- Public Methods

    @Deprecated
    public DirContext getResources() {
        return context.getResources();
    }


    // ------------------------------------------------- ServletContext Methods

    /**
     * Return the value of the specified context attribute, if any;
     * otherwise return <code>null</code>.
     *
     * @param name Name of the context attribute to return
     */
    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }


    /**
     * Return an enumeration of the names of the context attributes
     * associated with this context.
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        Set<String> names = new HashSet<String>();
        names.addAll(attributes.keySet());
        return Collections.enumeration(names);
    }


    /**
     * Return a <code>ServletContext</code> object that corresponds to a
     * specified URI on the server.  This method allows servlets to gain
     * access to the context for various parts of the server, and as needed
     * obtain <code>RequestDispatcher</code> objects or resources from the
     * context.  The given path must be absolute (beginning with a "/"),
     * and is interpreted based on our virtual host's document root.
     *
     * @param uri Absolute URI of a resource on the server
     */
    @Override
    public ServletContext getContext(String uri) {

        // Validate the format of the specified argument
        if (uri == null || !uri.startsWith("/")) {
            return null;
        }

        Context child = null;
        try {
            // Look for an exact match
            Container host = context.getParent();
            child = (Context) host.findChild(uri);

            // Non-running contexts should be ignored.
            if (child != null && !child.getState().isAvailable()) {
                child = null;
            }

            // Remove any version information and use the mapper
            if (child == null) {
                int i = uri.indexOf("##");
                if (i > -1) {
                    uri = uri.substring(0, i);
                }
                // Note: This could be more efficient with a dedicated Mapper
                //       method but such an implementation would require some
                //       refactoring of the Mapper to avoid copy/paste of
                //       existing code.
                MessageBytes hostMB = MessageBytes.newInstance();
                hostMB.setString(host.getName());

                MessageBytes pathMB = MessageBytes.newInstance();
                pathMB.setString(uri);

                MappingData mappingData = new MappingData();
                ((Engine) host.getParent()).getService().findConnectors()[0].getMapper().map(
                        hostMB, pathMB, null, mappingData);
                child = (Context) mappingData.context;
            }
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            return null;
        }

        if (child == null) {
            return null;
        }

        if (context.getCrossContext()) {
            // If crossContext is enabled, can always return the context
            return child.getServletContext();
        } else if (child == context) {
            // Can still return the current context
            return context.getServletContext();
        } else {
            // Nothing to return
            return null;
        }
    }


    /**
     * Return the main path associated with this context.
     */
    @Override
    public String getContextPath() {
        return context.getPath();
    }


    /**
     * Return the value of the specified initialization parameter, or
     * <code>null</code> if this parameter does not exist.
     *
     * @param name Name of the initialization parameter to retrieve
     */
    @Override
    public String getInitParameter(final String name) {
        // Special handling for XML settings as the context setting must
        // always override anything that might have been set by an application.
        if (Globals.JASPER_XML_VALIDATION_TLD_INIT_PARAM.equals(name) &&
                context.getTldValidation()) {
            return "true";
        }
        if (Globals.JASPER_XML_VALIDATION_INIT_PARAM.equals(name) &&
                context.getXmlValidation()) {
            return "true";
        }
        if (Globals.JASPER_XML_BLOCK_EXTERNAL_INIT_PARAM.equals(name)) {
            if (!context.getXmlBlockExternal()) {
                // System admin has explicitly changed the default
                return "false";
            }
        }
        return parameters.get(name);
    }


    /**
     * Return the names of the context's initialization parameters, or an
     * empty enumeration if the context has no initialization parameters.
     */
    @Override
    public Enumeration<String> getInitParameterNames() {
        Set<String> names = new HashSet<String>();
        names.addAll(parameters.keySet());
        // Special handling for XML settings as these attributes will always be
        // available if they have been set on the context
        if (context.getTldValidation()) {
            names.add(Globals.JASPER_XML_VALIDATION_TLD_INIT_PARAM);
        }
        if (context.getXmlValidation()) {
            names.add(Globals.JASPER_XML_VALIDATION_INIT_PARAM);
        }
        if (!context.getXmlBlockExternal()) {
            names.add(Globals.JASPER_XML_BLOCK_EXTERNAL_INIT_PARAM);
        }
        return Collections.enumeration(names);
    }


    /**
     * Return the major version of the Java Servlet API that we implement.
     */
    @Override
    public int getMajorVersion() {

        return (Constants.MAJOR_VERSION);

    }


    /**
     * Return the minor version of the Java Servlet API that we implement.
     */
    @Override
    public int getMinorVersion() {

        return (Constants.MINOR_VERSION);

    }


    /**
     * Return the MIME type of the specified file, or <code>null</code> if
     * the MIME type cannot be determined.
     *
     * @param file Filename for which to identify a MIME type
     */
    @Override
    public String getMimeType(String file) {

        if (file == null)
            return (null);
        int period = file.lastIndexOf('.');
        if (period < 0)
            return (null);
        String extension = file.substring(period + 1);
        if (extension.length() < 1)
            return (null);
        return (context.findMimeMapping(extension));

    }


    /**
     * Return a <code>RequestDispatcher</code> object that acts as a
     * wrapper for the named servlet.
     *
     * @param name Name of the servlet for which a dispatcher is requested
     */
    @Override
    public RequestDispatcher getNamedDispatcher(String name) {

        // Validate the name argument
        if (name == null)
            return (null);

        // Create and return a corresponding request dispatcher
        Wrapper wrapper = (Wrapper) context.findChild(name);
        if (wrapper == null)
            return (null);

        return new ApplicationDispatcher(wrapper, null, null, null, null, name);

    }


    /**
     * Return the real path for a given virtual path, if possible; otherwise
     * return <code>null</code>.
     *
     * @param path The path to the desired resource
     */
    @Override
    public String getRealPath(String path) {
        return context.getRealPath(path);
    }


    /**
     * Return a <code>RequestDispatcher</code> instance that acts as a
     * wrapper for the resource at the given path.  The path must begin
     * with a "/" and is interpreted as relative to the current context root.
     *
     * @param path The path to the desired resource.
     */
    @Override
    public RequestDispatcher getRequestDispatcher(final String path) {

        // Validate the path argument
        if (path == null) {
            return (null);
        }
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException(
                    sm.getString("applicationContext.requestDispatcher.iae", path));
        }

        // Need to separate the query string and the uri. This is required for
        // the ApplicationDispatcher constructor. Mapping also requires the uri
        // without the query string.
        String uri;
        String queryString;
        int pos = path.indexOf('?');
        if (pos >= 0) {
            uri = path.substring(0, pos);
            queryString = path.substring(pos + 1);
        } else {
            uri = path;
            queryString = null;
        }

        String normalizedPath = RequestUtil.normalize(uri);
        if (normalizedPath == null) {
            return (null);
        }

        if (getContext().getDispatchersUseEncodedPaths()) {
            // Decode
            String decodedPath;
            try {
                decodedPath = URLDecoder.decode(normalizedPath, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // Impossible
                return null;
            }

            // Security check to catch attempts to encode /../ sequences
            normalizedPath = RequestUtil.normalize(decodedPath);
            if (!decodedPath.equals(normalizedPath)) {
                getContext().getLogger().warn(
                        sm.getString("applicationContext.illegalDispatchPath", path),
                        new IllegalArgumentException());
                return null;
            }

            // URI needs to include the context path
            uri = URLEncoder.DEFAULT.encode(getContextPath(), "UTF-8") + uri;
        } else {
            // uri is passed to the constructor for ApplicationDispatcher and is
            // ultimately used as the value for getRequestURI() which returns
            // encoded values. Therefore, since the value passed in for path
            // was decoded, encode uri here.
            uri = URLEncoder.DEFAULT.encode(getContextPath() + uri, "UTF-8");
        }

        pos = normalizedPath.length();

        // Use the thread local URI and mapping data
        DispatchData dd = dispatchData.get();
        if (dd == null) {
            dd = new DispatchData();
            dispatchData.set(dd);
        }

        MessageBytes uriMB = dd.uriMB;
        uriMB.recycle();

        // Use the thread local mapping data
        MappingData mappingData = dd.mappingData;

        // Map the URI
        CharChunk uriCC = uriMB.getCharChunk();
        try {
            uriCC.append(context.getPath(), 0, context.getPath().length());
            /*
             * Ignore any trailing path params (separated by ';') for mapping
             * purposes
             */
            int semicolon = normalizedPath.indexOf(';');
            if (pos >= 0 && semicolon > pos) {
                semicolon = -1;
            }
            uriCC.append(normalizedPath, 0, semicolon > 0 ? semicolon : pos);
            context.getMapper().map(uriMB, mappingData);
            if (mappingData.wrapper == null) {
                return (null);
            }
            /*
             * Append any trailing path params (separated by ';') that were
             * ignored for mapping purposes, so that they're reflected in the
             * RequestDispatcher's requestURI
             */
            if (semicolon > 0) {
                uriCC.append(normalizedPath, semicolon, pos - semicolon);
            }
        } catch (Exception e) {
            // Should never happen
            log(sm.getString("applicationContext.mapping.error"), e);
            return (null);
        }

        Wrapper wrapper = (Wrapper) mappingData.wrapper;
        String wrapperPath = mappingData.wrapperPath.toString();
        String pathInfo = mappingData.pathInfo.toString();

        mappingData.recycle();

        // Construct a RequestDispatcher to process this request
        return new ApplicationDispatcher(wrapper, uri, wrapperPath, pathInfo,
                queryString, null);
    }



    /**
     * Return the URL to the resource that is mapped to a specified path.
     * The path must begin with a "/" and is interpreted as relative to the
     * current context root.
     *
     * @param path The path to the desired resource
     *
     * @exception MalformedURLException if the path is not given
     *  in the correct form
     */
    @Override
    public URL getResource(String path)
        throws MalformedURLException {

        if (path == null ||
                !path.startsWith("/") && GET_RESOURCE_REQUIRE_SLASH)
            throw new MalformedURLException(sm.getString(
                    "applicationContext.requestDispatcher.iae", path));

        String normPath = RequestUtil.normalize(path);
        if (normPath == null)
            return (null);

        DirContext resources = context.getResources();
        if (resources != null) {
            String fullPath = context.getPath() + normPath;
            String hostName = context.getParent().getName();
            try {
                resources.lookup(normPath);
                URI uri = new URI("jndi", null, "", -1,
                        getJNDIUri(hostName, fullPath), null, null);
                return new URL(null, uri.toString(),
                        new DirContextURLStreamHandler(resources));
            } catch (NamingException e) {
                // Ignore
            } catch (Exception e) {
                // Unexpected
                log(sm.getString("applicationContext.lookup.error", path,
                        getContextPath()), e);
            }
        }

        return (null);

    }


    /**
     * Return the requested resource as an <code>InputStream</code>.  The
     * path must be specified according to the rules described under
     * <code>getResource</code>.  If no such resource can be identified,
     * return <code>null</code>.
     *
     * @param path The path to the desired resource.
     */
    @Override
    public InputStream getResourceAsStream(String path) {

        if (path == null)
            return (null);

        if (!path.startsWith("/") && GET_RESOURCE_REQUIRE_SLASH)
            return null;

        String normalizedPath = RequestUtil.normalize(path);
        if (normalizedPath == null)
            return (null);

        DirContext resources = context.getResources();
        if (resources != null) {
            try {
                Object resource = resources.lookup(normalizedPath);
                if (resource instanceof Resource)
                    return (((Resource) resource).streamContent());
            } catch (NamingException e) {
                // Ignore
            } catch (Exception e) {
                // Unexpected
                log(sm.getString("applicationContext.lookup.error", path,
                        getContextPath()), e);
            }
        }
        return (null);

    }


    /**
     * Return a Set containing the resource paths of resources member of the
     * specified collection. Each path will be a String starting with
     * a "/" character. The returned set is immutable.
     *
     * @param path Collection path
     */
    @Override
    public Set<String> getResourcePaths(String path) {

        // Validate the path argument
        if (path == null) {
            return null;
        }
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException
                (sm.getString("applicationContext.resourcePaths.iae", path));
        }

        String normalizedPath;
        if (File.separatorChar == '\\') {
            // On Windows '\\' is a separator so in case a Windows style
            // separator has managed to make it into the path, replace it.
            normalizedPath = RequestUtil.normalize(path, true);
        } else {
            // On UNIX and similar systems, '\\' is a valid file name so do not
            // convert it to '/'
            normalizedPath = RequestUtil.normalize(path, false);
        }
        if (normalizedPath == null)
            return (null);

        DirContext resources = context.getResources();
        if (resources != null) {
            return (getResourcePathsInternal(resources, normalizedPath));
        }
        return (null);

    }


    /**
     * Internal implementation of getResourcesPath() logic.
     *
     * @param resources Directory context to search
     * @param path Collection path
     */
    private Set<String> getResourcePathsInternal(DirContext resources,
            String path) {

        ResourceSet<String> set = new ResourceSet<String>();
        try {
            listCollectionPaths(set, resources, path);
        } catch (NamingException e) {
            return (null);
        }
        set.setLocked(true);
        return (set);

    }


    /**
     * Return the name and version of the servlet container.
     */
    @Override
    public String getServerInfo() {

        return (ServerInfo.getServerInfo());

    }


    /**
     * @deprecated As of Java Servlet API 2.1, with no direct replacement.
     */
    @Override
    @Deprecated
    public Servlet getServlet(String name) {

        return (null);

    }


    /**
     * Return the display name of this web application.
     */
    @Override
    public String getServletContextName() {

        return (context.getDisplayName());

    }


    /**
     * @deprecated As of Java Servlet API 2.1, with no direct replacement.
     */
    @Override
    @Deprecated
    public Enumeration<String> getServletNames() {
        return Collections.enumeration(emptyString);
    }


    /**
     * @deprecated As of Java Servlet API 2.1, with no direct replacement.
     */
    @Override
    @Deprecated
    public Enumeration<Servlet> getServlets() {
        return Collections.enumeration(emptyServlet);
    }


    /**
     * Writes the specified message to a servlet log file.
     *
     * @param message Message to be written
     */
    @Override
    public void log(String message) {

        context.getLogger().info(message);

    }


    /**
     * Writes the specified exception and message to a servlet log file.
     *
     * @param exception Exception to be reported
     * @param message Message to be written
     *
     * @deprecated As of Java Servlet API 2.1, use
     *  <code>log(String, Throwable)</code> instead
     */
    @Override
    @Deprecated
    public void log(Exception exception, String message) {

        context.getLogger().error(message, exception);

    }


    /**
     * Writes the specified message and exception to a servlet log file.
     *
     * @param message Message to be written
     * @param throwable Exception to be reported
     */
    @Override
    public void log(String message, Throwable throwable) {

        context.getLogger().error(message, throwable);

    }


    /**
     * Remove the context attribute with the specified name, if any.
     *
     * @param name Name of the context attribute to be removed
     */
    @Override
    public void removeAttribute(String name) {

        Object value = null;

        // Remove the specified attribute
        // Check for read only attribute
        if (readOnlyAttributes.containsKey(name)){
            return;
        }
        value = attributes.remove(name);
        if (value == null) {
            return;
        }

        // Notify interested application event listeners
        Object listeners[] = context.getApplicationEventListeners();
        if ((listeners == null) || (listeners.length == 0))
            return;
        ServletContextAttributeEvent event =
          new ServletContextAttributeEvent(context.getServletContext(),
                                            name, value);
        for (int i = 0; i < listeners.length; i++) {
            if (!(listeners[i] instanceof ServletContextAttributeListener))
                continue;
            ServletContextAttributeListener listener =
                (ServletContextAttributeListener) listeners[i];
            try {
                context.fireContainerEvent("beforeContextAttributeRemoved",
                                           listener);
                listener.attributeRemoved(event);
                context.fireContainerEvent("afterContextAttributeRemoved",
                                           listener);
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                context.fireContainerEvent("afterContextAttributeRemoved",
                                           listener);
                // FIXME - should we do anything besides log these?
                log(sm.getString("applicationContext.attributeEvent"), t);
            }
        }

    }


    /**
     * Bind the specified value with the specified context attribute name,
     * replacing any existing value for that name.
     *
     * @param name Attribute name to be bound
     * @param value New attribute value to be bound
     */
    @Override
    public void setAttribute(String name, Object value) {

        // Name cannot be null
        if (name == null)
            throw new IllegalArgumentException
                (sm.getString("applicationContext.setAttribute.namenull"));

        // Null value is the same as removeAttribute()
        if (value == null) {
            removeAttribute(name);
            return;
        }

        // Add or replace the specified attribute
        // Check for read only attribute
        if (readOnlyAttributes.containsKey(name))
            return;

        Object oldValue = attributes.put(name, value);
        boolean replaced = oldValue != null;

        // Notify interested application event listeners
        Object listeners[] = context.getApplicationEventListeners();
        if ((listeners == null) || (listeners.length == 0))
            return;
        ServletContextAttributeEvent event = null;
        if (replaced)
            event =
                new ServletContextAttributeEvent(context.getServletContext(),
                                                 name, oldValue);
        else
            event =
                new ServletContextAttributeEvent(context.getServletContext(),
                                                 name, value);

        for (int i = 0; i < listeners.length; i++) {
            if (!(listeners[i] instanceof ServletContextAttributeListener))
                continue;
            ServletContextAttributeListener listener =
                (ServletContextAttributeListener) listeners[i];
            try {
                if (replaced) {
                    context.fireContainerEvent
                        ("beforeContextAttributeReplaced", listener);
                    listener.attributeReplaced(event);
                    context.fireContainerEvent("afterContextAttributeReplaced",
                                               listener);
                } else {
                    context.fireContainerEvent("beforeContextAttributeAdded",
                                               listener);
                    listener.attributeAdded(event);
                    context.fireContainerEvent("afterContextAttributeAdded",
                                               listener);
                }
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                if (replaced)
                    context.fireContainerEvent("afterContextAttributeReplaced",
                                               listener);
                else
                    context.fireContainerEvent("afterContextAttributeAdded",
                                               listener);
                // FIXME - should we do anything besides log these?
                log(sm.getString("applicationContext.attributeEvent"), t);
            }
        }

    }


    /**
     * Add filter to context.
     * @param   filterName  Name of filter to add
     * @param   filterClass Name of filter class
     * @return  <code>null</code> if the filter has already been fully defined,
     *          else a {@link javax.servlet.FilterRegistration.Dynamic} object
     *          that can be used to further configure the filter
     * @throws IllegalStateException if the context has already been initialised
     * @throws UnsupportedOperationException - if this context was passed to the
     *         {@link ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)}
     *         method of a {@link ServletContextListener} that was not declared
     *         in web.xml, a web-fragment or annotated with
     *         {@link javax.servlet.annotation.WebListener}.
     */
    @Override
    public FilterRegistration.Dynamic addFilter(String filterName,
            String filterClass) throws IllegalStateException {

        return addFilter(filterName, filterClass, null);
    }


    /**
     * Add filter to context.
     * @param   filterName  Name of filter to add
     * @param   filter      Filter to add
     * @return  <code>null</code> if the filter has already been fully defined,
     *          else a {@link javax.servlet.FilterRegistration.Dynamic} object
     *          that can be used to further configure the filter
     * @throws IllegalStateException if the context has already been initialised
     * @throws UnsupportedOperationException - if this context was passed to the
     *         {@link ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)}
     *         method of a {@link ServletContextListener} that was not declared
     *         in web.xml, a web-fragment or annotated with
     *         {@link javax.servlet.annotation.WebListener}.
     */
    @Override
    public FilterRegistration.Dynamic addFilter(String filterName,
            Filter filter) throws IllegalStateException {

        return addFilter(filterName, null, filter);
    }


    /**
     * Add filter to context.
     * @param   filterName  Name of filter to add
     * @param   filterClass Class of filter to add
     * @return  <code>null</code> if the filter has already been fully defined,
     *          else a {@link javax.servlet.FilterRegistration.Dynamic} object
     *          that can be used to further configure the filter
     * @throws IllegalStateException if the context has already been initialised
     * @throws UnsupportedOperationException - if this context was passed to the
     *         {@link ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)}
     *         method of a {@link ServletContextListener} that was not declared
     *         in web.xml, a web-fragment or annotated with
     *         {@link javax.servlet.annotation.WebListener}.
     */
    @Override
    public FilterRegistration.Dynamic addFilter(String filterName,
            Class<? extends Filter> filterClass) throws IllegalStateException {

        return addFilter(filterName, filterClass.getName(), null);
    }

    private FilterRegistration.Dynamic addFilter(String filterName,
            String filterClass, Filter filter) throws IllegalStateException {

        if (filterName == null || filterName.equals("")) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.invalidFilterName", filterName));
        }

        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            //TODO Spec breaking enhancement to ignore this restriction
            throw new IllegalStateException(
                    sm.getString("applicationContext.addFilter.ise",
                            getContextPath()));
        }

        FilterDef filterDef = context.findFilterDef(filterName);

        // Assume a 'complete' FilterRegistration is one that has a class and
        // a name
        if (filterDef == null) {
            filterDef = new FilterDef();
            filterDef.setFilterName(filterName);
            context.addFilterDef(filterDef);
        } else {
            if (filterDef.getFilterName() != null &&
                    filterDef.getFilterClass() != null) {
                return null;
            }
        }

        if (filter == null) {
            filterDef.setFilterClass(filterClass);
        } else {
            filterDef.setFilterClass(filter.getClass().getName());
            filterDef.setFilter(filter);
        }

        return new ApplicationFilterRegistration(filterDef, context);
    }

    @Override
    public <T extends Filter> T createFilter(Class<T> c)
    throws ServletException {
        try {
            @SuppressWarnings("unchecked")
            T filter = (T) context.getInstanceManager().newInstance(c.getName());
            return filter;
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        } catch (InvocationTargetException e) {
            ExceptionUtils.handleThrowable(e.getCause());
            throw new ServletException(e);
        } catch (NamingException e) {
            throw new ServletException(e);
        } catch (InstantiationException e) {
            throw new ServletException(e);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }


    @Override
    public FilterRegistration getFilterRegistration(String filterName) {
        FilterDef filterDef = context.findFilterDef(filterName);
        if (filterDef == null) {
            return null;
        }
        return new ApplicationFilterRegistration(filterDef, context);
    }


    /**
     * Add servlet to context.
     * @param   servletName  Name of servlet to add
     * @param   servletClass Name of servlet class
     * @return  <code>null</code> if the servlet has already been fully defined,
     *          else a {@link javax.servlet.ServletRegistration.Dynamic} object
     *          that can be used to further configure the servlet
     * @throws IllegalStateException if the context has already been initialised
     * @throws UnsupportedOperationException - if this context was passed to the
     *         {@link ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)}
     *         method of a {@link ServletContextListener} that was not declared
     *         in web.xml, a web-fragment or annotated with
     *         {@link javax.servlet.annotation.WebListener}.
     */
    @Override
    public ServletRegistration.Dynamic addServlet(String servletName,
            String servletClass) throws IllegalStateException {

        return addServlet(servletName, servletClass, null);
    }


    /**
     * Add servlet to context.
     * @param   servletName Name of servlet to add
     * @param   servlet     Servlet instance to add
     * @return  <code>null</code> if the servlet has already been fully defined,
     *          else a {@link javax.servlet.ServletRegistration.Dynamic} object
     *          that can be used to further configure the servlet
     * @throws IllegalStateException if the context has already been initialised
     * @throws UnsupportedOperationException - if this context was passed to the
     *         {@link ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)}
     *         method of a {@link ServletContextListener} that was not declared
     *         in web.xml, a web-fragment or annotated with
     *         {@link javax.servlet.annotation.WebListener}.
     */
    @Override
    public ServletRegistration.Dynamic addServlet(String servletName,
            Servlet servlet) throws IllegalStateException {

        return addServlet(servletName, null, servlet);
    }


    /**
     * Add servlet to context.
     * @param   servletName  Name of servlet to add
     * @param   servletClass Class of servlet to add
     * @return  <code>null</code> if the servlet has already been fully defined,
     *          else a {@link javax.servlet.ServletRegistration.Dynamic} object
     *          that can be used to further configure the servlet
     * @throws IllegalStateException if the context has already been initialised
     * @throws UnsupportedOperationException - if this context was passed to the
     *         {@link ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)}
     *         method of a {@link ServletContextListener} that was not declared
     *         in web.xml, a web-fragment or annotated with
     *         {@link javax.servlet.annotation.WebListener}.
     */
    @Override
    public ServletRegistration.Dynamic addServlet(String servletName,
            Class<? extends Servlet> servletClass)
    throws IllegalStateException {

        return addServlet(servletName, servletClass.getName(), null);
    }

    private ServletRegistration.Dynamic addServlet(String servletName,
            String servletClass, Servlet servlet) throws IllegalStateException {

        if (servletName == null || servletName.equals("")) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.invalidServletName", servletName));
        }

        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            //TODO Spec breaking enhancement to ignore this restriction
            throw new IllegalStateException(
                    sm.getString("applicationContext.addServlet.ise",
                            getContextPath()));
        }

        Wrapper wrapper = (Wrapper) context.findChild(servletName);

        // Assume a 'complete' ServletRegistration is one that has a class and
        // a name
        if (wrapper == null) {
            wrapper = context.createWrapper();
            wrapper.setName(servletName);
            context.addChild(wrapper);
        } else {
            if (wrapper.getName() != null &&
                    wrapper.getServletClass() != null) {
                if (wrapper.isOverridable()) {
                    wrapper.setOverridable(false);
                } else {
                    return null;
                }
            }
        }

        if (servlet == null) {
            wrapper.setServletClass(servletClass);
        } else {
            wrapper.setServletClass(servlet.getClass().getName());
            wrapper.setServlet(servlet);
        }

        return context.dynamicServletAdded(wrapper);
    }


    @Override
    public <T extends Servlet> T createServlet(Class<T> c)
    throws ServletException {
        try {
            @SuppressWarnings("unchecked")
            T servlet = (T) context.getInstanceManager().newInstance(c.getName());
            context.dynamicServletCreated(servlet);
            return servlet;
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        } catch (InvocationTargetException e) {
            ExceptionUtils.handleThrowable(e.getCause());
            throw new ServletException(e);
        } catch (NamingException e) {
            throw new ServletException(e);
        } catch (InstantiationException e) {
            throw new ServletException(e);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }


    @Override
    public ServletRegistration getServletRegistration(String servletName) {
        Wrapper wrapper = (Wrapper) context.findChild(servletName);
        if (wrapper == null) {
            return null;
        }

        return new ApplicationServletRegistration(wrapper, context);
    }


    /**
     * By default {@link SessionTrackingMode#URL} is always supported, {@link
     * SessionTrackingMode#COOKIE} is supported unless the <code>cookies</code>
     * attribute has been set to <code>false</code> for the context and {@link
     * SessionTrackingMode#SSL} is supported if at least one of the connectors
     * used by this context has the attribute <code>secure</code> set to
     * <code>true</code>.
     */
    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return defaultSessionTrackingModes;
    }

    private void populateSessionTrackingModes() {
        // URL re-writing is always enabled by default
        defaultSessionTrackingModes = EnumSet.of(SessionTrackingMode.URL);
        supportedSessionTrackingModes = EnumSet.of(SessionTrackingMode.URL);

        if (context.getCookies()) {
            defaultSessionTrackingModes.add(SessionTrackingMode.COOKIE);
            supportedSessionTrackingModes.add(SessionTrackingMode.COOKIE);
        }

        // SSL not enabled by default as it can only used on its own
        // Context > Host > Engine > Service
        Service s = ((Engine) context.getParent().getParent()).getService();
        Connector[] connectors = s.findConnectors();
        // Need at least one SSL enabled connector to use the SSL session ID.
        for (Connector connector : connectors) {
            if (Boolean.TRUE.equals(connector.getAttribute("SSLEnabled"))) {
                supportedSessionTrackingModes.add(SessionTrackingMode.SSL);
                break;
            }
        }
    }

    /**
     * Return the supplied value if one was previously set, else return the
     * defaults.
     */
    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        if (sessionTrackingModes != null) {
            return sessionTrackingModes;
        }
        return defaultSessionTrackingModes;
    }


    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        return sessionCookieConfig;
    }


    /**
     * @throws IllegalStateException if the context has already been initialised
     * @throws IllegalArgumentException If SSL is requested in combination with
     *                                  anything else or if an unsupported
     *                                  tracking mode is requested
     */
    @Override
    public void setSessionTrackingModes(
            Set<SessionTrackingMode> sessionTrackingModes) {

        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(
                    sm.getString("applicationContext.setSessionTracking.ise",
                            getContextPath()));
        }

        // Check that only supported tracking modes have been requested
        for (SessionTrackingMode sessionTrackingMode : sessionTrackingModes) {
            if (!supportedSessionTrackingModes.contains(sessionTrackingMode)) {
                throw new IllegalArgumentException(sm.getString(
                        "applicationContext.setSessionTracking.iae.invalid",
                        sessionTrackingMode.toString(), getContextPath()));
            }
        }

        // Check SSL has not be configured with anything else
        if (sessionTrackingModes.contains(SessionTrackingMode.SSL)) {
            if (sessionTrackingModes.size() > 1) {
                throw new IllegalArgumentException(sm.getString(
                        "applicationContext.setSessionTracking.iae.ssl",
                        getContextPath()));
            }
        }

        this.sessionTrackingModes = sessionTrackingModes;
    }


    @Override
    public boolean setInitParameter(String name, String value) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(
                    sm.getString("applicationContext.setInitParam.ise",
                            getContextPath()));
        }

        return parameters.putIfAbsent(name, value) == null;
    }


    @Override
    public void addListener(Class<? extends EventListener> listenerClass) {
        EventListener listener;
        try {
            listener = createListener(listenerClass);
        } catch (ServletException e) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.init",
                    listenerClass.getName()), e);
        }
        addListener(listener);
    }


    @Override
    public void addListener(String className) {

        try {
            if (context.getInstanceManager() != null) {
                Object obj = context.getInstanceManager().newInstance(className);

                if (!(obj instanceof EventListener)) {
                    throw new IllegalArgumentException(sm.getString(
                            "applicationContext.addListener.iae.wrongType",
                            className));
                }

                EventListener listener = (EventListener) obj;
                addListener(listener);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.cnfe", className),
                    e);
        } catch (InvocationTargetException e) {
            ExceptionUtils.handleThrowable(e.getCause());
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.cnfe", className),
                    e);
        } catch (NamingException e) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.cnfe", className),
                    e);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.cnfe", className),
                    e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.cnfe", className),
                    e);
        }

    }


    @Override
    public <T extends EventListener> void addListener(T t) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(
                    sm.getString("applicationContext.addListener.ise",
                            getContextPath()));
        }

        boolean match = false;
        if (t instanceof ServletContextAttributeListener ||
                t instanceof ServletRequestListener ||
                t instanceof ServletRequestAttributeListener ||
                t instanceof HttpSessionAttributeListener) {
            context.addApplicationEventListener(t);
            match = true;
        }

        if (t instanceof HttpSessionListener
                || (t instanceof ServletContextListener &&
                        newServletContextListenerAllowed)) {
            // Add listener directly to the list of instances rather than to
            // the list of class names.
            context.addApplicationLifecycleListener(t);
            match = true;
        }

        if (match) return;

        if (t instanceof ServletContextListener) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.sclNotAllowed",
                    t.getClass().getName()));
        } else {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.wrongType",
                    t.getClass().getName()));
        }
    }


    @Override
    public <T extends EventListener> T createListener(Class<T> c)
            throws ServletException {
        try {
            @SuppressWarnings("unchecked")
            T listener =
                (T) context.getInstanceManager().newInstance(c);
            if (listener instanceof ServletContextListener ||
                    listener instanceof ServletContextAttributeListener ||
                    listener instanceof ServletRequestListener ||
                    listener instanceof ServletRequestAttributeListener ||
                    listener instanceof HttpSessionListener ||
                    listener instanceof HttpSessionAttributeListener) {
                return listener;
            }
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.wrongType",
                    listener.getClass().getName()));
        } catch (IllegalAccessException e) {
            throw new ServletException(e);
        } catch (InvocationTargetException e) {
            ExceptionUtils.handleThrowable(e.getCause());
            throw new ServletException(e);
        } catch (NamingException e) {
            throw new ServletException(e);
        } catch (InstantiationException e) {
            throw new ServletException(e);
        }
    }


    @Override
    public void declareRoles(String... roleNames) {

        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            //TODO Spec breaking enhancement to ignore this restriction
            throw new IllegalStateException(
                    sm.getString("applicationContext.addRole.ise",
                            getContextPath()));
        }

        if (roleNames == null) {
            throw new IllegalArgumentException(
                    sm.getString("applicationContext.roles.iae",
                            getContextPath()));
        }

        for (String role : roleNames) {
            if (role == null || "".equals(role)) {
                throw new IllegalArgumentException(
                        sm.getString("applicationContext.role.iae",
                                getContextPath()));
            }
            context.addSecurityRole(role);
        }
    }


    @Override
    public ClassLoader getClassLoader() {
        ClassLoader result = context.getLoader().getClassLoader();
        if (Globals.IS_SECURITY_ENABLED) {
            ClassLoader tccl = Thread.currentThread().getContextClassLoader();
            ClassLoader parent = result;
            while (parent != null) {
                if (parent == tccl) {
                    break;
                }
                parent = parent.getParent();
            }
            if (parent == null) {
                System.getSecurityManager().checkPermission(
                        new RuntimePermission("getClassLoader"));
            }
        }

        return result;
    }


    @Override
    public int getEffectiveMajorVersion() {
        return context.getEffectiveMajorVersion();
    }


    @Override
    public int getEffectiveMinorVersion() {
        return context.getEffectiveMinorVersion();
    }


    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        Map<String, ApplicationFilterRegistration> result =
            new HashMap<String, ApplicationFilterRegistration>();

        FilterDef[] filterDefs = context.findFilterDefs();
        for (FilterDef filterDef : filterDefs) {
            result.put(filterDef.getFilterName(),
                    new ApplicationFilterRegistration(filterDef, context));
        }

        return result;
    }


    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        JspConfigDescriptor jspConfigDescriptor = context
                .getJspConfigDescriptor();
        if (jspConfigDescriptor.getJspPropertyGroups().isEmpty()
                && jspConfigDescriptor.getTaglibs().isEmpty()) {
            return null;
        } else {
            return jspConfigDescriptor;
        }
    }


    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        Map<String, ApplicationServletRegistration> result =
            new HashMap<String, ApplicationServletRegistration>();

        Container[] wrappers = context.findChildren();
        for (Container wrapper : wrappers) {
            result.put(((Wrapper) wrapper).getName(),
                    new ApplicationServletRegistration(
                            (Wrapper) wrapper, context));
        }

        return result;
    }


    // -------------------------------------------------------- Package Methods
    protected StandardContext getContext() {
        return this.context;
    }

    @Deprecated
    protected Map<String,String> getReadonlyAttributes() {
        return this.readOnlyAttributes;
    }
    /**
     * Clear all application-created attributes.
     */
    protected void clearAttributes() {

        // Create list of attributes to be removed
        ArrayList<String> list = new ArrayList<String>();
        Iterator<String> iter = attributes.keySet().iterator();
        while (iter.hasNext()) {
            list.add(iter.next());
        }

        // Remove application originated attributes
        // (read only attributes will be left in place)
        Iterator<String> keys = list.iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            removeAttribute(key);
        }

    }


    /**
     * Return the facade associated with this ApplicationContext.
     */
    protected ServletContext getFacade() {

        return (this.facade);

    }


    /**
     * Set an attribute as read only.
     */
    void setAttributeReadOnly(String name) {

        if (attributes.containsKey(name))
            readOnlyAttributes.put(name, name);

    }


    protected void setNewServletContextListenerAllowed(boolean allowed) {
        this.newServletContextListenerAllowed = allowed;
    }

    /**
     * List resource paths (recursively), and store all of them in the given
     * Set.
     */
    private static void listCollectionPaths(Set<String> set,
            DirContext resources, String path) throws NamingException {

        Enumeration<Binding> childPaths = resources.listBindings(path);
        while (childPaths.hasMoreElements()) {
            Binding binding = childPaths.nextElement();
            String name = binding.getName();
            StringBuilder childPath = new StringBuilder(path);
            if (!"/".equals(path) && !path.endsWith("/"))
                childPath.append("/");
            childPath.append(name);
            Object object = binding.getObject();
            if (object instanceof DirContext) {
                childPath.append("/");
            }
            set.add(childPath.toString());
        }

    }


    /**
     * Get full path, based on the host name and the context path.
     */
    private static String getJNDIUri(String hostName, String path) {
        String result;

        if (path.startsWith("/")) {
            result = "/" + hostName + path;
        } else {
            result = "/" + hostName + "/" + path;
        }

        return result;
    }


    /**
     * Internal class used as thread-local storage when doing path
     * mapping during dispatch.
     */
    private static final class DispatchData {

        public MessageBytes uriMB;
        public MappingData mappingData;

        public DispatchData() {
            uriMB = MessageBytes.newInstance();
            CharChunk uriCC = uriMB.getCharChunk();
            uriCC.setLimit(-1);
            mappingData = new MappingData();
        }
    }


}
