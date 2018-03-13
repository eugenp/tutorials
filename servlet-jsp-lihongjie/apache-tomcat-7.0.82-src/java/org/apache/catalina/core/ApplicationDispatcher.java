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
import java.io.PrintWriter;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.AsyncDispatcher;
import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.InstanceEvent;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.Response;
import org.apache.catalina.connector.ResponseFacade;
import org.apache.catalina.util.InstanceSupport;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.res.StringManager;

/**
 * Standard implementation of <code>RequestDispatcher</code> that allows a
 * request to be forwarded to a different resource to create the ultimate
 * response, or to include the output of another resource in the response
 * from this resource.  This implementation allows application level servlets
 * to wrap the request and/or response objects that are passed on to the
 * called resource, as long as the wrapping classes extend
 * <code>javax.servlet.ServletRequestWrapper</code> and
 * <code>javax.servlet.ServletResponseWrapper</code>.
 *
 * @author Craig R. McClanahan
 */
final class ApplicationDispatcher implements AsyncDispatcher, RequestDispatcher {

    static final boolean STRICT_SERVLET_COMPLIANCE;

    static final boolean WRAP_SAME_OBJECT;


    static {
        STRICT_SERVLET_COMPLIANCE = Globals.STRICT_SERVLET_COMPLIANCE;
        
        String wrapSameObject = System.getProperty(
                "org.apache.catalina.core.ApplicationDispatcher.WRAP_SAME_OBJECT");
        if (wrapSameObject == null) {
            WRAP_SAME_OBJECT = STRICT_SERVLET_COMPLIANCE;
        } else {
            WRAP_SAME_OBJECT = Boolean.parseBoolean(wrapSameObject);
        }
    }


    protected class PrivilegedForward
            implements PrivilegedExceptionAction<Void> {
        private ServletRequest request;
        private ServletResponse response;

        PrivilegedForward(ServletRequest request, ServletResponse response) {
            this.request = request;
            this.response = response;
        }

        @Override
        public Void run() throws java.lang.Exception {
            doForward(request,response);
            return null;
        }
    }

    protected class PrivilegedInclude implements
            PrivilegedExceptionAction<Void> {
        private ServletRequest request;
        private ServletResponse response;

        PrivilegedInclude(ServletRequest request, ServletResponse response) {
            this.request = request;
            this.response = response;
        }

        @Override
        public Void run() throws ServletException, IOException {
            doInclude(request, response);
            return null;
        }
    }

    protected class PrivilegedDispatch implements
            PrivilegedExceptionAction<Void> {
        private final ServletRequest request;
        private final ServletResponse response;

        PrivilegedDispatch(ServletRequest request, ServletResponse response) {
            this.request = request;
            this.response = response;
        }

        @Override
        public Void run() throws ServletException, IOException {
            doDispatch(request, response);
            return null;
        }
    }


    /**
     * Used to pass state when the request dispatcher is used. Using instance
     * variables causes threading issues and state is too complex to pass and
     * return single ServletRequest or ServletResponse objects.
     */
    private static class State {
        State(ServletRequest request, ServletResponse response,
                boolean including) {
            this.outerRequest = request;
            this.outerResponse = response;
            this.including = including;
        }

        /**
         * The outermost request that will be passed on to the invoked servlet.
         */
        ServletRequest outerRequest = null;


        /**
         * The outermost response that will be passed on to the invoked servlet.
         */
        ServletResponse outerResponse = null;
        
        /**
         * The request wrapper we have created and installed (if any).
         */
        ServletRequest wrapRequest = null;


        /**
         * The response wrapper we have created and installed (if any).
         */
        ServletResponse wrapResponse = null;
        
        /**
         * Are we performing an include() instead of a forward()?
         */
        boolean including = false;

        /**
         * Outermost HttpServletRequest in the chain
         */
        HttpServletRequest hrequest = null;

        /**
         * Outermost HttpServletResponse in the chain
         */
        HttpServletResponse hresponse = null;
    }

    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new instance of this class, configured according to the
     * specified parameters.  If both servletPath and pathInfo are
     * <code>null</code>, it will be assumed that this RequestDispatcher
     * was acquired by name, rather than by path.
     *
     * @param wrapper The Wrapper associated with the resource that will
     *  be forwarded to or included (required)
     * @param requestURI The request URI to this resource (if any)
     * @param servletPath The revised servlet path to this resource (if any)
     * @param pathInfo The revised extra path information to this resource
     *  (if any)
     * @param queryString Query string parameters included with this request
     *  (if any)
     * @param name Servlet name (if a named dispatcher was created)
     *  else <code>null</code>
     */
    public ApplicationDispatcher
        (Wrapper wrapper, String requestURI, String servletPath,
         String pathInfo, String queryString, String name) {

        super();

        // Save all of our configuration parameters
        this.wrapper = wrapper;
        this.context = (Context) wrapper.getParent();
        this.requestURI = requestURI;
        this.servletPath = servletPath;
        this.pathInfo = pathInfo;
        this.queryString = queryString;
        this.name = name;
        if (wrapper instanceof StandardWrapper)
            this.support = ((StandardWrapper) wrapper).getInstanceSupport();
        else
            this.support = new InstanceSupport(wrapper);

    }


    // ----------------------------------------------------- Instance Variables

    /**
     * The Context this RequestDispatcher is associated with.
     */
    private Context context = null;


    /**
     * Descriptive information about this implementation.
     */
    private static final String info =
        "org.apache.catalina.core.ApplicationDispatcher/1.0";


    /**
     * The servlet name for a named dispatcher.
     */
    private String name = null;


    /**
     * The extra path information for this RequestDispatcher.
     */
    private String pathInfo = null;


    /**
     * The query string parameters for this RequestDispatcher.
     */
    private String queryString = null;


    /**
     * The request URI for this RequestDispatcher.
     */
    private String requestURI = null;


    /**
     * The servlet path for this RequestDispatcher.
     */
    private String servletPath = null;


    /**
     * The StringManager for this package.
     */
    private static final StringManager sm =
      StringManager.getManager(Constants.Package);


    /**
     * The InstanceSupport instance associated with our Wrapper (used to
     * send "before dispatch" and "after dispatch" events.
     */
    private InstanceSupport support = null;


    /**
     * The Wrapper associated with the resource that will be forwarded to
     * or included.
     */
    private Wrapper wrapper = null;


    // ------------------------------------------------------------- Properties


    /**
     * Return the descriptive information about this implementation.
     */
    public String getInfo() {

        return (info);

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Forward this request and response to another resource for processing.
     * Any runtime exception, IOException, or ServletException thrown by the
     * called servlet will be propagated to the caller.
     *
     * @param request The servlet request to be forwarded
     * @param response The servlet response to be forwarded
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    @Override
    public void forward(ServletRequest request, ServletResponse response)
        throws ServletException, IOException
    {
        if (Globals.IS_SECURITY_ENABLED) {
            try {
                PrivilegedForward dp = new PrivilegedForward(request,response);
                AccessController.doPrivileged(dp);
            } catch (PrivilegedActionException pe) {
                Exception e = pe.getException();
                if (e instanceof ServletException)
                    throw (ServletException) e;
                throw (IOException) e;
            }
        } else {
            doForward(request,response);
        }
    }

    private void doForward(ServletRequest request, ServletResponse response)
        throws ServletException, IOException
    {
        
        // Reset any output that has been buffered, but keep headers/cookies
        if (response.isCommitted()) {
            throw new IllegalStateException
                (sm.getString("applicationDispatcher.forward.ise"));
        }
        try {
            response.resetBuffer();
        } catch (IllegalStateException e) {
            throw e;
        }

        // Set up to handle the specified request and response
        State state = new State(request, response, false);

        if (WRAP_SAME_OBJECT) {
            // Check SRV.8.2 / SRV.14.2.5.1 compliance
            checkSameObjects(request, response);
        }

        wrapResponse(state);
        // Handle an HTTP named dispatcher forward
        if ((servletPath == null) && (pathInfo == null)) {

            ApplicationHttpRequest wrequest =
                (ApplicationHttpRequest) wrapRequest(state);
            HttpServletRequest hrequest = state.hrequest;
            wrequest.setRequestURI(hrequest.getRequestURI());
            wrequest.setContextPath(hrequest.getContextPath());
            wrequest.setServletPath(hrequest.getServletPath());
            wrequest.setPathInfo(hrequest.getPathInfo());
            wrequest.setQueryString(hrequest.getQueryString());

            processRequest(request,response,state);
        }

        // Handle an HTTP path-based forward
        else {

            ApplicationHttpRequest wrequest =
                (ApplicationHttpRequest) wrapRequest(state);
            String contextPath = context.getPath();
            HttpServletRequest hrequest = state.hrequest;
            if (hrequest.getAttribute(
                    RequestDispatcher.FORWARD_REQUEST_URI) == null) {
                wrequest.setAttribute(RequestDispatcher.FORWARD_REQUEST_URI,
                                      hrequest.getRequestURI());
                wrequest.setAttribute(RequestDispatcher.FORWARD_CONTEXT_PATH,
                                      hrequest.getContextPath());
                wrequest.setAttribute(RequestDispatcher.FORWARD_SERVLET_PATH,
                                      hrequest.getServletPath());
                wrequest.setAttribute(RequestDispatcher.FORWARD_PATH_INFO,
                                      hrequest.getPathInfo());
                wrequest.setAttribute(RequestDispatcher.FORWARD_QUERY_STRING,
                                      hrequest.getQueryString());
            }
 
            wrequest.setContextPath(contextPath);
            wrequest.setRequestURI(requestURI);
            wrequest.setServletPath(servletPath);
            wrequest.setPathInfo(pathInfo);
            if (queryString != null) {
                wrequest.setQueryString(queryString);
                wrequest.setQueryParams(queryString);
            }

            processRequest(request,response,state);
        }

        if (request.isAsyncStarted()) {
            // An async request was started during the forward, don't close the
            // response as it may be written to during the async handling
            return;
        }

        // This is not a real close in order to support error processing
        if (wrapper.getLogger().isDebugEnabled() )
            wrapper.getLogger().debug(" Disabling the response for further output");

        if  (response instanceof ResponseFacade) {
            ((ResponseFacade) response).finish();
        } else {
            // Servlet SRV.6.2.2. The Request/Response may have been wrapped
            // and may no longer be instance of RequestFacade 
            if (wrapper.getLogger().isDebugEnabled()){
                wrapper.getLogger().debug( " The Response is vehiculed using a wrapper: " 
                           + response.getClass().getName() );
            }

            // Close anyway
            try {
                PrintWriter writer = response.getWriter();
                writer.close();
            } catch (IllegalStateException e) {
                try {
                    ServletOutputStream stream = response.getOutputStream();
                    stream.close();
                } catch (IllegalStateException f) {
                    // Ignore
                } catch (IOException f) {
                    // Ignore
                }
            } catch (IOException e) {
                // Ignore
            }
        }

    }

    
    /**
     * Prepare the request based on the filter configuration.
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param state The RD state
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    private void processRequest(ServletRequest request, 
                                ServletResponse response,
                                State state)
        throws IOException, ServletException {
                
        DispatcherType disInt = (DispatcherType) request.getAttribute(Globals.DISPATCHER_TYPE_ATTR);
        if (disInt != null) {
            boolean doInvoke = true;
            
            if (context.getFireRequestListenersOnForwards() &&
                    !context.fireRequestInitEvent(request)) {
                doInvoke = false;
            }

            if (doInvoke) {
                if (disInt != DispatcherType.ERROR) {
                    state.outerRequest.setAttribute(
                            Globals.DISPATCHER_REQUEST_PATH_ATTR,
                            getCombinedPath());
                    state.outerRequest.setAttribute(
                            Globals.DISPATCHER_TYPE_ATTR,
                            DispatcherType.FORWARD);
                    invoke(state.outerRequest, response, state);
                } else {
                    invoke(state.outerRequest, response, state);
                }
                
                if (context.getFireRequestListenersOnForwards()) {
                    context.fireRequestDestroyEvent(request);
                }
            }
        }
    }
    
    
    /**
     * Combine the servletPath and the pathInfo. If pathInfo is
     * <code>null</code> it is ignored. If servletPath is <code>null</code> then
     * <code>null</code> is returned.
     * @return The combined path with pathInfo appended to servletInfo
     */
    private String getCombinedPath() {
        if (servletPath == null) {
            return null;
        }
        if (pathInfo == null) {
            return servletPath;
        }
        return servletPath + pathInfo;
    }


    /**
     * Include the response from another resource in the current response.
     * Any runtime exception, IOException, or ServletException thrown by the
     * called servlet will be propagated to the caller.
     *
     * @param request The servlet request that is including this one
     * @param response The servlet response to be appended to
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    @Override
    public void include(ServletRequest request, ServletResponse response)
        throws ServletException, IOException
    {
        if (Globals.IS_SECURITY_ENABLED) {
            try {
                PrivilegedInclude dp = new PrivilegedInclude(request,response);
                AccessController.doPrivileged(dp);
            } catch (PrivilegedActionException pe) {
                Exception e = pe.getException();

                if (e instanceof ServletException)
                    throw (ServletException) e;
                throw (IOException) e;
            }
        } else {
            doInclude(request, response);
        }
    }

    private void doInclude(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        // Set up to handle the specified request and response
        State state = new State(request, response, true);

        if (WRAP_SAME_OBJECT) {
            // Check SRV.8.2 / SRV.14.2.5.1 compliance
            checkSameObjects(request, response);
        }
        
        // Create a wrapped response to use for this request
        wrapResponse(state);

        // Handle an HTTP named dispatcher include
        if (name != null) {

            ApplicationHttpRequest wrequest =
                (ApplicationHttpRequest) wrapRequest(state);
            wrequest.setAttribute(Globals.NAMED_DISPATCHER_ATTR, name);
            if (servletPath != null)
                wrequest.setServletPath(servletPath);
            wrequest.setAttribute(Globals.DISPATCHER_TYPE_ATTR,
                    DispatcherType.INCLUDE);
            wrequest.setAttribute(Globals.DISPATCHER_REQUEST_PATH_ATTR,
                    getCombinedPath());
            invoke(state.outerRequest, state.outerResponse, state);
        }

        // Handle an HTTP path based include
        else {

            ApplicationHttpRequest wrequest =
                (ApplicationHttpRequest) wrapRequest(state);
            String contextPath = context.getPath();
            if (requestURI != null)
                wrequest.setAttribute(RequestDispatcher.INCLUDE_REQUEST_URI,
                                      requestURI);
            if (contextPath != null)
                wrequest.setAttribute(RequestDispatcher.INCLUDE_CONTEXT_PATH,
                                      contextPath);
            if (servletPath != null)
                wrequest.setAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH,
                                      servletPath);
            if (pathInfo != null)
                wrequest.setAttribute(RequestDispatcher.INCLUDE_PATH_INFO,
                                      pathInfo);
            if (queryString != null) {
                wrequest.setAttribute(RequestDispatcher.INCLUDE_QUERY_STRING,
                                      queryString);
                wrequest.setQueryParams(queryString);
            }
            
            wrequest.setAttribute(Globals.DISPATCHER_TYPE_ATTR,
                    DispatcherType.INCLUDE);
            wrequest.setAttribute(Globals.DISPATCHER_REQUEST_PATH_ATTR,
                    getCombinedPath());
            invoke(state.outerRequest, state.outerResponse, state);
        }

    }


    @Override
    public void dispatch(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        if (Globals.IS_SECURITY_ENABLED) {
            try {
                PrivilegedDispatch dp = new PrivilegedDispatch(request,response);
                AccessController.doPrivileged(dp);
            } catch (PrivilegedActionException pe) {
                Exception e = pe.getException();

                if (e instanceof ServletException)
                    throw (ServletException) e;
                throw (IOException) e;
            }
        } else {
            doDispatch(request, response);
        }
    }

    private void doDispatch(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        // Set up to handle the specified request and response
        State state = new State(request, response, false);

        // Create a wrapped response to use for this request
        wrapResponse(state);

        ApplicationHttpRequest wrequest =
            (ApplicationHttpRequest) wrapRequest(state);

        wrequest.setAttribute(Globals.DISPATCHER_TYPE_ATTR,
                DispatcherType.ASYNC);
        wrequest.setAttribute(Globals.DISPATCHER_REQUEST_PATH_ATTR,
                getCombinedPath());

        wrequest.setContextPath(context.getPath());
        wrequest.setRequestURI(requestURI);
        wrequest.setServletPath(servletPath);
        wrequest.setPathInfo(pathInfo);
        if (queryString != null) {
            wrequest.setQueryString(queryString);
            wrequest.setQueryParams(queryString);
        }

        invoke(state.outerRequest, state.outerResponse, state);
    }


    // -------------------------------------------------------- Private Methods


    /**
     * Ask the resource represented by this RequestDispatcher to process
     * the associated request, and create (or append to) the associated
     * response.
     * <p>
     * <strong>IMPLEMENTATION NOTE</strong>: This implementation assumes
     * that no filters are applied to a forwarded or included resource,
     * because they were already done for the original request.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    private void invoke(ServletRequest request, ServletResponse response,
            State state) throws IOException, ServletException {

        // Checking to see if the context classloader is the current context
        // classloader. If it's not, we're saving it, and setting the context
        // classloader to the Context classloader
        ClassLoader oldCCL = Thread.currentThread().getContextClassLoader();
        ClassLoader contextClassLoader = context.getLoader().getClassLoader();

        if (oldCCL != contextClassLoader) {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        } else {
            oldCCL = null;
        }

        // Initialize local variables we may need
        HttpServletResponse hresponse = state.hresponse;
        Servlet servlet = null;
        IOException ioException = null;
        ServletException servletException = null;
        RuntimeException runtimeException = null;
        boolean unavailable = false;

        // Check for the servlet being marked unavailable
        if (wrapper.isUnavailable()) {
            wrapper.getLogger().warn(
                    sm.getString("applicationDispatcher.isUnavailable", 
                    wrapper.getName()));
            long available = wrapper.getAvailable();
            if ((available > 0L) && (available < Long.MAX_VALUE))
                hresponse.setDateHeader("Retry-After", available);
            hresponse.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, sm
                    .getString("applicationDispatcher.isUnavailable", wrapper
                            .getName()));
            unavailable = true;
        }

        // Allocate a servlet instance to process this request
        try {
            if (!unavailable) {
                servlet = wrapper.allocate();
            }
        } catch (ServletException e) {
            wrapper.getLogger().error(sm.getString("applicationDispatcher.allocateException",
                             wrapper.getName()), StandardWrapper.getRootCause(e));
            servletException = e;
        } catch (Throwable e) {
            ExceptionUtils.handleThrowable(e);
            wrapper.getLogger().error(sm.getString("applicationDispatcher.allocateException",
                             wrapper.getName()), e);
            servletException = new ServletException
                (sm.getString("applicationDispatcher.allocateException",
                              wrapper.getName()), e);
            servlet = null;
        }
                
        // Get the FilterChain Here
        ApplicationFilterFactory factory = ApplicationFilterFactory.getInstance();
        ApplicationFilterChain filterChain = factory.createFilterChain(request,
                                                                wrapper,servlet);
        
        // Call the service() method for the allocated servlet instance
        try {
            support.fireInstanceEvent(InstanceEvent.BEFORE_DISPATCH_EVENT,
                                      servlet, request, response);
            // for includes/forwards
            if ((servlet != null) && (filterChain != null)) {
               filterChain.doFilter(request, response);
             }
            // Servlet Service Method is called by the FilterChain
            support.fireInstanceEvent(InstanceEvent.AFTER_DISPATCH_EVENT,
                                      servlet, request, response);
        } catch (ClientAbortException e) {
            support.fireInstanceEvent(InstanceEvent.AFTER_DISPATCH_EVENT,
                                      servlet, request, response);
            ioException = e;
        } catch (IOException e) {
            support.fireInstanceEvent(InstanceEvent.AFTER_DISPATCH_EVENT,
                                      servlet, request, response);
            wrapper.getLogger().error(sm.getString("applicationDispatcher.serviceException",
                             wrapper.getName()), e);
            ioException = e;
        } catch (UnavailableException e) {
            support.fireInstanceEvent(InstanceEvent.AFTER_DISPATCH_EVENT,
                                      servlet, request, response);
            wrapper.getLogger().error(sm.getString("applicationDispatcher.serviceException",
                             wrapper.getName()), e);
            servletException = e;
            wrapper.unavailable(e);
        } catch (ServletException e) {
            support.fireInstanceEvent(InstanceEvent.AFTER_DISPATCH_EVENT,
                                      servlet, request, response);
            Throwable rootCause = StandardWrapper.getRootCause(e);
            if (!(rootCause instanceof ClientAbortException)) {
                wrapper.getLogger().error(sm.getString("applicationDispatcher.serviceException",
                        wrapper.getName()), rootCause);
            }
            servletException = e;
        } catch (RuntimeException e) {
            support.fireInstanceEvent(InstanceEvent.AFTER_DISPATCH_EVENT,
                                      servlet, request, response);
            wrapper.getLogger().error(sm.getString("applicationDispatcher.serviceException",
                             wrapper.getName()), e);
            runtimeException = e;
        }

        // Release the filter chain (if any) for this request
        try {
            if (filterChain != null)
                filterChain.release();
        } catch (Throwable e) {
            ExceptionUtils.handleThrowable(e);
            wrapper.getLogger().error(sm.getString("standardWrapper.releaseFilters",
                             wrapper.getName()), e);
            // FIXME: Exception handling needs to be similar to what is in the StandardWrapperValue
        }

        // Deallocate the allocated servlet instance
        try {
            if (servlet != null) {
                wrapper.deallocate(servlet);
            }
        } catch (ServletException e) {
            wrapper.getLogger().error(sm.getString("applicationDispatcher.deallocateException",
                             wrapper.getName()), e);
            servletException = e;
        } catch (Throwable e) {
            ExceptionUtils.handleThrowable(e);
            wrapper.getLogger().error(sm.getString("applicationDispatcher.deallocateException",
                             wrapper.getName()), e);
            servletException = new ServletException
                (sm.getString("applicationDispatcher.deallocateException",
                              wrapper.getName()), e);
        }

        // Reset the old context class loader
        if (oldCCL != null)
            Thread.currentThread().setContextClassLoader(oldCCL);
        
        // Unwrap request/response if needed
        // See Bugzilla 30949
        unwrapRequest(state);
        unwrapResponse(state);
        // Recycle request if necessary (also BZ 30949)
        recycleRequestWrapper(state);
        
        // Rethrow an exception if one was thrown by the invoked servlet
        if (ioException != null)
            throw ioException;
        if (servletException != null)
            throw servletException;
        if (runtimeException != null)
            throw runtimeException;

    }


    /**
     * Unwrap the request if we have wrapped it.
     */
    private void unwrapRequest(State state) {

        if (state.wrapRequest == null)
            return;

        if (state.outerRequest.isAsyncStarted()) {
            if (!state.outerRequest.getAsyncContext().hasOriginalRequestAndResponse()) {
                return;
            }
        }

        ServletRequest previous = null;
        ServletRequest current = state.outerRequest;
        while (current != null) {

            // If we run into the container request we are done
            if ((current instanceof Request)
                || (current instanceof RequestFacade))
                break;

            // Remove the current request if it is our wrapper
            if (current == state.wrapRequest) {
                ServletRequest next =
                  ((ServletRequestWrapper) current).getRequest();
                if (previous == null)
                    state.outerRequest = next;
                else
                    ((ServletRequestWrapper) previous).setRequest(next);
                break;
            }

            // Advance to the next request in the chain
            previous = current;
            current = ((ServletRequestWrapper) current).getRequest();

        }

    }
    
    /**
     * Unwrap the response if we have wrapped it.
     */
    private void unwrapResponse(State state) {

        if (state.wrapResponse == null)
            return;

        if (state.outerRequest.isAsyncStarted()) {
            if (!state.outerRequest.getAsyncContext().hasOriginalRequestAndResponse()) {
                return;
            }
        }

        ServletResponse previous = null;
        ServletResponse current = state.outerResponse;
        while (current != null) {

            // If we run into the container response we are done
            if ((current instanceof Response)
                || (current instanceof ResponseFacade))
                break;

            // Remove the current response if it is our wrapper
            if (current == state.wrapResponse) {
                ServletResponse next =
                  ((ServletResponseWrapper) current).getResponse();
                if (previous == null)
                    state.outerResponse = next;
                else
                    ((ServletResponseWrapper) previous).setResponse(next);
                break;
            }

            // Advance to the next response in the chain
            previous = current;
            current = ((ServletResponseWrapper) current).getResponse();

        }

    }


    /**
     * Create and return a request wrapper that has been inserted in the
     * appropriate spot in the request chain.
     */
    private ServletRequest wrapRequest(State state) {

        // Locate the request we should insert in front of
        ServletRequest previous = null;
        ServletRequest current = state.outerRequest;
        while (current != null) {
            if(state.hrequest == null && (current instanceof HttpServletRequest))
                state.hrequest = (HttpServletRequest)current;
            if (!(current instanceof ServletRequestWrapper))
                break;
            if (current instanceof ApplicationHttpRequest)
                break;
            if (current instanceof ApplicationRequest)
                break;
            previous = current;
            current = ((ServletRequestWrapper) current).getRequest();
        }

        // Instantiate a new wrapper at this point and insert it in the chain
        ServletRequest wrapper = null;
        if ((current instanceof ApplicationHttpRequest) ||
            (current instanceof Request) ||
            (current instanceof HttpServletRequest)) {
            // Compute a crossContext flag
            HttpServletRequest hcurrent = (HttpServletRequest) current;
            boolean crossContext = false;
            if ((state.outerRequest instanceof ApplicationHttpRequest) ||
                (state.outerRequest instanceof Request) ||
                (state.outerRequest instanceof HttpServletRequest)) {
                HttpServletRequest houterRequest = 
                    (HttpServletRequest) state.outerRequest;
                Object contextPath = houterRequest.getAttribute(
                        RequestDispatcher.INCLUDE_CONTEXT_PATH);
                if (contextPath == null) {
                    // Forward
                    contextPath = houterRequest.getContextPath();
                }
                crossContext = !(context.getPath().equals(contextPath));
            }
            wrapper = new ApplicationHttpRequest
                (hcurrent, context, crossContext);
        } else {
            wrapper = new ApplicationRequest(current);
        }
        if (previous == null)
            state.outerRequest = wrapper;
        else
            ((ServletRequestWrapper) previous).setRequest(wrapper);
        state.wrapRequest = wrapper;
        return (wrapper);

    }


    /**
     * Create and return a response wrapper that has been inserted in the
     * appropriate spot in the response chain.
     */
    private ServletResponse wrapResponse(State state) {

        // Locate the response we should insert in front of
        ServletResponse previous = null;
        ServletResponse current = state.outerResponse;
        while (current != null) {
            if(state.hresponse == null && (current instanceof HttpServletResponse)) {
                state.hresponse = (HttpServletResponse)current;
                if(!state.including) // Forward only needs hresponse
                    return null;
            }
            if (!(current instanceof ServletResponseWrapper))
                break;
            if (current instanceof ApplicationHttpResponse)
                break;
            if (current instanceof ApplicationResponse)
                break;
            previous = current;
            current = ((ServletResponseWrapper) current).getResponse();
        }

        // Instantiate a new wrapper at this point and insert it in the chain
        ServletResponse wrapper = null;
        if ((current instanceof ApplicationHttpResponse) ||
            (current instanceof Response) ||
            (current instanceof HttpServletResponse))
            wrapper =
                new ApplicationHttpResponse((HttpServletResponse) current,
                        state.including);
        else
            wrapper = new ApplicationResponse(current, state.including);
        if (previous == null)
            state.outerResponse = wrapper;
        else
            ((ServletResponseWrapper) previous).setResponse(wrapper);
        state.wrapResponse = wrapper;
        return (wrapper);

    }

    private void checkSameObjects(ServletRequest appRequest,
            ServletResponse appResponse) throws ServletException {
        ServletRequest originalRequest =
            ApplicationFilterChain.getLastServicedRequest();
        ServletResponse originalResponse =
            ApplicationFilterChain.getLastServicedResponse();
        
        // Some forwards, eg from valves will not set original values 
        if (originalRequest == null || originalResponse == null) {
            return;
        }
        
        boolean same = false;
        ServletRequest dispatchedRequest = appRequest;
        
        //find the request that was passed into the service method
        while (originalRequest instanceof ServletRequestWrapper &&
                ((ServletRequestWrapper) originalRequest).getRequest()!=null ) {
            originalRequest =
                ((ServletRequestWrapper) originalRequest).getRequest();
        }
        //compare with the dispatched request
        while (!same) {
            if (originalRequest.equals(dispatchedRequest)) {
                same = true;
            }
            if (!same && dispatchedRequest instanceof ServletRequestWrapper) {
                dispatchedRequest =
                    ((ServletRequestWrapper) dispatchedRequest).getRequest();
            } else {
                break;
            }
        }
        if (!same) {
            throw new ServletException(sm.getString(
                    "applicationDispatcher.specViolation.request"));
        }
        
        same = false;
        ServletResponse dispatchedResponse = appResponse;
        
        //find the response that was passed into the service method
        while (originalResponse instanceof ServletResponseWrapper &&
                ((ServletResponseWrapper) originalResponse).getResponse() != 
                    null ) {
            originalResponse =
                ((ServletResponseWrapper) originalResponse).getResponse();
        }
        //compare with the dispatched response
        while (!same) {
            if (originalResponse.equals(dispatchedResponse)) {
                same = true;
            }
            
            if (!same && dispatchedResponse instanceof ServletResponseWrapper) {
                dispatchedResponse =
                    ((ServletResponseWrapper) dispatchedResponse).getResponse();
            } else {
                break;
            }
        }

        if (!same) {
            throw new ServletException(sm.getString(
                    "applicationDispatcher.specViolation.response"));
        }
    }

    private void recycleRequestWrapper(State state) {
        if (state.wrapRequest instanceof ApplicationHttpRequest) {
            ((ApplicationHttpRequest) state.wrapRequest).recycle();        }
    }
}
