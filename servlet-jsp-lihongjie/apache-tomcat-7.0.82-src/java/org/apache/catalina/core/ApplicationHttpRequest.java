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


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.util.ParameterMap;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.http.Parameters;


/**
 * Wrapper around a <code>javax.servlet.http.HttpServletRequest</code>
 * that transforms an application request object (which might be the original
 * one passed to a servlet, or might be based on the 2.3
 * <code>javax.servlet.http.HttpServletRequestWrapper</code> class)
 * back into an internal <code>org.apache.catalina.HttpRequest</code>.
 * <p>
 * <strong>WARNING</strong>:  Due to Java's lack of support for multiple
 * inheritance, all of the logic in <code>ApplicationRequest</code> is
 * duplicated in <code>ApplicationHttpRequest</code>.  Make sure that you
 * keep these two classes in synchronization when making changes!
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 */
class ApplicationHttpRequest extends HttpServletRequestWrapper {


    // ------------------------------------------------------- Static Variables


    /**
     * The set of attribute names that are special for request dispatchers.
     */
    protected static final String specials[] =
    { RequestDispatcher.INCLUDE_REQUEST_URI,
      RequestDispatcher.INCLUDE_CONTEXT_PATH,
      RequestDispatcher.INCLUDE_SERVLET_PATH,
      RequestDispatcher.INCLUDE_PATH_INFO,
      RequestDispatcher.INCLUDE_QUERY_STRING,
      RequestDispatcher.FORWARD_REQUEST_URI, 
      RequestDispatcher.FORWARD_CONTEXT_PATH,
      RequestDispatcher.FORWARD_SERVLET_PATH, 
      RequestDispatcher.FORWARD_PATH_INFO,
      RequestDispatcher.FORWARD_QUERY_STRING };


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new wrapped request around the specified servlet request.
     *
     * @param request The servlet request being wrapped
     */
    public ApplicationHttpRequest(HttpServletRequest request, Context context,
                                  boolean crossContext) {

        super(request);
        this.context = context;
        this.crossContext = crossContext;
        setRequest(request);

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The context for this request.
     */
    protected Context context = null;


    /**
     * The context path for this request.
     */
    protected String contextPath = null;


    /**
     * If this request is cross context, since this changes session access
     * behavior.
     */
    protected boolean crossContext = false;


    /**
     * The current dispatcher type.
     */
    protected DispatcherType dispatcherType = null;


    /**
     * Descriptive information about this implementation.
     */
    protected static final String info =
        "org.apache.catalina.core.ApplicationHttpRequest/1.0";


    /**
     * The request parameters for this request.  This is initialized from the
     * wrapped request.
     */
    protected Map<String, String[]> parameters = null;


    /**
     * Have the parameters for this request already been parsed?
     */
    private boolean parsedParams = false;


    /**
     * The path information for this request.
     */
    protected String pathInfo = null;


    /**
     * The query parameters for the current request.
     */
    private String queryParamString = null;


    /**
     * The query string for this request.
     */
    protected String queryString = null;


    /**
     * The current request dispatcher path.
     */
    protected Object requestDispatcherPath = null;


    /**
     * The request URI for this request.
     */
    protected String requestURI = null;


    /**
     * The servlet path for this request.
     */
    protected String servletPath = null;


    /**
     * The currently active session for this request.
     */
    protected Session session = null;


    /**
     * Special attributes.
     */
    protected Object[] specialAttributes = new Object[specials.length];
    

    // ------------------------------------------------- ServletRequest Methods

    @Override
    public ServletContext getServletContext() {
        if (context == null) {
            return null;
        }
        return context.getServletContext();
    }


    /**
     * Override the <code>getAttribute()</code> method of the wrapped request.
     *
     * @param name Name of the attribute to retrieve
     */
    @Override
    public Object getAttribute(String name) {

        if (name.equals(Globals.DISPATCHER_TYPE_ATTR)) {
            return dispatcherType;
        } else if (name.equals(Globals.DISPATCHER_REQUEST_PATH_ATTR)) {
            if ( requestDispatcherPath != null ){
                return requestDispatcherPath.toString();
            } else {
                return null;   
            }
        }

        int pos = getSpecial(name);
        if (pos == -1) {
            return getRequest().getAttribute(name);
        } else {
            if ((specialAttributes[pos] == null) 
                && (specialAttributes[5] == null) && (pos >= 5)) {
                // If it's a forward special attribute, and null, it means this
                // is an include, so we check the wrapped request since 
                // the request could have been forwarded before the include
                return getRequest().getAttribute(name);
            } else {
                return specialAttributes[pos];
            }
        }

    }


    /**
     * Override the <code>getAttributeNames()</code> method of the wrapped
     * request.
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        return (new AttributeNamesEnumerator());
    }


    /**
     * Override the <code>removeAttribute()</code> method of the
     * wrapped request.
     *
     * @param name Name of the attribute to remove
     */
    @Override
    public void removeAttribute(String name) {

        if (!removeSpecial(name))
            getRequest().removeAttribute(name);

    }


    /**
     * Override the <code>setAttribute()</code> method of the
     * wrapped request.
     *
     * @param name Name of the attribute to set
     * @param value Value of the attribute to set
     */
    @Override
    public void setAttribute(String name, Object value) {

        if (name.equals(Globals.DISPATCHER_TYPE_ATTR)) {
            dispatcherType = (DispatcherType)value;
            return;
        } else if (name.equals(Globals.DISPATCHER_REQUEST_PATH_ATTR)) {
            requestDispatcherPath = value;
            return;
        }

        if (!setSpecial(name, value)) {
            getRequest().setAttribute(name, value);
        }

    }


    /**
     * Return a RequestDispatcher that wraps the resource at the specified
     * path, which may be interpreted as relative to the current request path.
     *
     * @param path Path of the resource to be wrapped
     */
    @Override
    public RequestDispatcher getRequestDispatcher(String path) {

        if (context == null)
            return (null);

        // If the path is already context-relative, just pass it through
        if (path == null)
            return (null);
        else if (path.startsWith("/"))
            return (context.getServletContext().getRequestDispatcher(path));

        // Convert a request-relative path to a context-relative one
        String servletPath = 
            (String) getAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH);
        if (servletPath == null)
            servletPath = getServletPath();

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
        if (pos >= 0) {
            relative = requestPath.substring(0, pos + 1) + path;
        } else {
            relative = requestPath + path;
        }

        return (context.getServletContext().getRequestDispatcher(relative));

    }

    
    /**
     * Override the getDispatcherType() method of the wrapped request.
     * 
     */
    @Override
    public DispatcherType getDispatcherType() {
        return dispatcherType;
    }


    // --------------------------------------------- HttpServletRequest Methods


    /**
     * Override the <code>getContextPath()</code> method of the wrapped
     * request.
     */
    @Override
    public String getContextPath() {

        return (this.contextPath);

    }


    /**
     * Override the <code>getParameter()</code> method of the wrapped request.
     *
     * @param name Name of the requested parameter
     */
    @Override
    public String getParameter(String name) {

        parseParameters();

        String[] value = parameters.get(name);
        if (value == null) {
            return null;
        }
        return value[0];

    }


    /**
     * Override the <code>getParameterMap()</code> method of the
     * wrapped request.
     */
    @Override
    public Map<String, String[]> getParameterMap() {

        parseParameters();
        return (parameters);

    }


    /**
     * Override the <code>getParameterNames()</code> method of the
     * wrapped request.
     */
    @Override
    public Enumeration<String> getParameterNames() {

        parseParameters();
        return Collections.enumeration(parameters.keySet());
    }


    /**
     * Override the <code>getParameterValues()</code> method of the
     * wrapped request.
     *
     * @param name Name of the requested parameter
     */
    @Override
    public String[] getParameterValues(String name) {

        parseParameters();
        return parameters.get(name);

    }


    /**
     * Override the <code>getPathInfo()</code> method of the wrapped request.
     */
    @Override
    public String getPathInfo() {

        return (this.pathInfo);

    }


    /**
     * Override the <code>getPathTranslated()</code> method of the wrapped
     * request.
     */
    @Override
    public String getPathTranslated() {
        if (getPathInfo() == null || getServletContext() == null) {
            return null;
        }

        return getServletContext().getRealPath(getPathInfo());
    }


    /**
     * Override the <code>getQueryString()</code> method of the wrapped
     * request.
     */
    @Override
    public String getQueryString() {

        return (this.queryString);

    }


    /**
     * Override the <code>getRequestURI()</code> method of the wrapped
     * request.
     */
    @Override
    public String getRequestURI() {

        return (this.requestURI);

    }


    /**
     * Override the <code>getRequestURL()</code> method of the wrapped
     * request.
     */
    @Override
    public StringBuffer getRequestURL() {

        StringBuffer url = new StringBuffer();
        String scheme = getScheme();
        int port = getServerPort();
        if (port < 0)
            port = 80; // Work around java.net.URL bug

        url.append(scheme);
        url.append("://");
        url.append(getServerName());
        if ((scheme.equals("http") && (port != 80))
            || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(getRequestURI());

        return (url);

    }


    /**
     * Override the <code>getServletPath()</code> method of the wrapped
     * request.
     */
    @Override
    public String getServletPath() {

        return (this.servletPath);

    }


    /**
     * Return the session associated with this Request, creating one
     * if necessary.
     */
    @Override
    public HttpSession getSession() {
        return (getSession(true));
    }


    /**
     * Return the session associated with this Request, creating one
     * if necessary and requested.
     *
     * @param create Create a new session if one does not exist
     */
    @Override
    public HttpSession getSession(boolean create) {

        if (crossContext) {
            
            // There cannot be a session if no context has been assigned yet
            if (context == null)
                return (null);

            // Return the current session if it exists and is valid
            if (session != null && session.isValid()) {
                return (session.getSession());
            }

            HttpSession other = super.getSession(false);
            if (create && (other == null)) {
                // First create a session in the first context: the problem is
                // that the top level request is the only one which can 
                // create the cookie safely
                other = super.getSession(true);
            }
            if (other != null) {
                Session localSession = null;
                try {
                    localSession =
                        context.getManager().findSession(other.getId());
                    if (localSession != null && !localSession.isValid()) {
                        localSession = null;
                    }
                } catch (IOException e) {
                    // Ignore
                }
                if (localSession == null && create) {
                    localSession = 
                        context.getManager().createSession(other.getId());
                }
                if (localSession != null) {
                    localSession.access();
                    session = localSession;
                    return session.getSession();
                }
            }
            return null;

        } else {
            return super.getSession(create);
        }

    }


    /**
     * Returns true if the request specifies a JSESSIONID that is valid within
     * the context of this ApplicationHttpRequest, false otherwise.
     *
     * @return true if the request specifies a JSESSIONID that is valid within
     * the context of this ApplicationHttpRequest, false otherwise.
     */
    @Override
    public boolean isRequestedSessionIdValid() {

        if (crossContext) {

            String requestedSessionId = getRequestedSessionId();
            if (requestedSessionId == null)
                return (false);
            if (context == null)
                return (false);
            Manager manager = context.getManager();
            if (manager == null)
                return (false);
            Session session = null;
            try {
                session = manager.findSession(requestedSessionId);
            } catch (IOException e) {
                // Ignore
            }
            if ((session != null) && session.isValid()) {
                return (true);
            } else {
                return (false);
            }

        } else {
            return super.isRequestedSessionIdValid();
        }
    }


    // -------------------------------------------------------- Package Methods


    /**
     * Recycle this request
     */
    public void recycle() {
        if (session != null) {
            session.endAccess();
        }
    }


    /**
     * Return descriptive information about this implementation.
     */
    public String getInfo() {
        return info;
    }


    /**
     * Set the context path for this request.
     *
     * @param contextPath The new context path
     */
    void setContextPath(String contextPath) {

        this.contextPath = contextPath;

    }


    /**
     * Set the path information for this request.
     *
     * @param pathInfo The new path info
     */
    void setPathInfo(String pathInfo) {

        this.pathInfo = pathInfo;

    }


    /**
     * Set the query string for this request.
     *
     * @param queryString The new query string
     */
    void setQueryString(String queryString) {

        this.queryString = queryString;

    }


    /**
     * Set the request that we are wrapping.
     *
     * @param request The new wrapped request
     */
    void setRequest(HttpServletRequest request) {

        super.setRequest(request);

        // Initialize the attributes for this request
        dispatcherType = (DispatcherType)request.getAttribute(Globals.DISPATCHER_TYPE_ATTR);
        requestDispatcherPath = 
            request.getAttribute(Globals.DISPATCHER_REQUEST_PATH_ATTR);

        // Initialize the path elements for this request
        contextPath = request.getContextPath();
        pathInfo = request.getPathInfo();
        queryString = request.getQueryString();
        requestURI = request.getRequestURI();
        servletPath = request.getServletPath();

    }


    /**
     * Set the request URI for this request.
     *
     * @param requestURI The new request URI
     */
    void setRequestURI(String requestURI) {

        this.requestURI = requestURI;

    }


    /**
     * Set the servlet path for this request.
     *
     * @param servletPath The new servlet path
     */
    void setServletPath(String servletPath) {

        this.servletPath = servletPath;

    }


    /**
     * Parses the parameters of this request.
     *
     * If parameters are present in both the query string and the request
     * content, they are merged.
     */
    void parseParameters() {

        if (parsedParams) {
            return;
        }

        parameters = new ParameterMap<String, String[]>();
        parameters.putAll(getRequest().getParameterMap());
        mergeParameters();
        ((ParameterMap<String,String[]>) parameters).setLocked(true);
        parsedParams = true;
    }


    /**
     * Save query parameters for this request.
     *
     * @param queryString The query string containing parameters for this
     *                    request
     */
    void setQueryParams(String queryString) {
        this.queryParamString = queryString;
    }

    // ------------------------------------------------------ Protected Methods

    /**
     * Is this attribute name one of the special ones that is added only for
     * included servlets?
     *
     * @param name Attribute name to be tested
     */
    protected boolean isSpecial(String name) {

        for (int i = 0; i < specials.length; i++) {
            if (specials[i].equals(name))
                return (true);
        }
        return (false);

    }


    /**
     * Get a special attribute.
     *
     * @return the special attribute pos, or -1 if it is not a special 
     *         attribute
     */
    protected int getSpecial(String name) {
        for (int i = 0; i < specials.length; i++) {
            if (specials[i].equals(name)) {
                return (i);
            }
        }
        return (-1);
    }


    /**
     * Set a special attribute.
     * 
     * @return true if the attribute was a special attribute, false otherwise
     */
    protected boolean setSpecial(String name, Object value) {
        for (int i = 0; i < specials.length; i++) {
            if (specials[i].equals(name)) {
                specialAttributes[i] = value;
                return (true);
            }
        }
        return (false);
    }


    /**
     * Remove a special attribute.
     * 
     * @return true if the attribute was a special attribute, false otherwise
     */
    protected boolean removeSpecial(String name) {
        for (int i = 0; i < specials.length; i++) {
            if (specials[i].equals(name)) {
                specialAttributes[i] = null;
                return (true);
            }
        }
        return (false);
    }


    /**
     * Merge the two sets of parameter values into a single String array.
     *
     * @param values1 First set of values
     * @param values2 Second set of values
     */
    protected String[] mergeValues(Object values1, Object values2) {

        ArrayList<Object> results = new ArrayList<Object>();

        if (values1 == null) {
            // Skip - nothing to merge
        } else if (values1 instanceof String[]) {
            for (String value : (String[]) values1) {
                results.add(value);
            }
        } else { // String
            results.add(values1.toString());
        }

        if (values2 == null) {
            // Skip - nothing to merge
        } else if (values2 instanceof String[]) {
            for (String value : (String[]) values2) {
                results.add(value);
            }
        } else { // String
            results.add(values2.toString());
        }

        String values[] = new String[results.size()];
        return results.toArray(values);

    }


    // ------------------------------------------------------ Private Methods


    /**
     * Merge the parameters from the saved query parameter string (if any), and
     * the parameters already present on this request (if any), such that the
     * parameter values from the query string show up first if there are
     * duplicate parameter names.
     */
    private void mergeParameters() {

        if ((queryParamString == null) || (queryParamString.length() < 1))
            return;

        // Parse the query string from the dispatch target
        Parameters paramParser = new Parameters();
        MessageBytes queryMB = MessageBytes.newInstance();
        queryMB.setString(queryParamString);

        String encoding = getCharacterEncoding();
        // No need to process null value, as ISO-8859-1 is the default encoding
        // in MessageBytes.toBytes().
        if (encoding != null) {
            try {
                queryMB.setCharset(B2CConverter.getCharset(encoding));
            } catch (UnsupportedEncodingException ignored) {
                // Fall-back to ISO-8859-1
            }
        }

        paramParser.setQuery(queryMB);
        paramParser.setQueryStringEncoding(encoding);
        paramParser.handleQueryParameters();

        // Insert the additional parameters from the dispatch target
        Enumeration<String> dispParamNames = paramParser.getParameterNames();
        while (dispParamNames.hasMoreElements()) {
            String dispParamName = dispParamNames.nextElement();
            String[] dispParamValues = paramParser.getParameterValues(dispParamName);
            String[] originalValues = parameters.get(dispParamName);
            if (originalValues == null) {
                parameters.put(dispParamName, dispParamValues);
                continue;
            }
            parameters.put(dispParamName, mergeValues(dispParamValues, originalValues));
        }
    }


    // ----------------------------------- AttributeNamesEnumerator Inner Class


    /**
     * Utility class used to expose the special attributes as being available
     * as request attributes.
     */
    protected class AttributeNamesEnumerator implements Enumeration<String> {

        protected int pos = -1;
        protected int last = -1;
        protected Enumeration<String> parentEnumeration = null;
        protected String next = null;

        public AttributeNamesEnumerator() {
            parentEnumeration = getRequest().getAttributeNames();
            for (int i = specialAttributes.length - 1; i >= 0; i--) {
                if (getAttribute(specials[i]) != null) {
                    last = i;
                    break;
                }
            }
        }

        @Override
        public boolean hasMoreElements() {
            return ((pos != last) || (next != null) 
                    || ((next = findNext()) != null));
        }

        @Override
        public String nextElement() {
            if (pos != last) {
                for (int i = pos + 1; i <= last; i++) {
                    if (getAttribute(specials[i]) != null) {
                        pos = i;
                        return (specials[i]);
                    }
                }
            }
            String result = next;
            if (next != null) {
                next = findNext();
            } else {
                throw new NoSuchElementException();
            }
            return result;
        }

        protected String findNext() {
            String result = null;
            while ((result == null) && (parentEnumeration.hasMoreElements())) {
                String current = parentEnumeration.nextElement();
                if (!isSpecial(current)) {
                    result = current;
                }
            }
            return result;
        }

    }


}
