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
import java.security.Principal;
import java.security.PrivilegedActionException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Globals;
import org.apache.catalina.InstanceEvent;
import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometFilter;
import org.apache.catalina.comet.CometFilterChain;
import org.apache.catalina.comet.CometProcessor;
import org.apache.catalina.security.SecurityUtil;
import org.apache.catalina.util.InstanceSupport;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.res.StringManager;

/**
 * Implementation of <code>javax.servlet.FilterChain</code> used to manage
 * the execution of a set of filters for a particular request.  When the
 * set of defined filters has all been executed, the next call to
 * <code>doFilter()</code> will execute the servlet's <code>service()</code>
 * method itself.
 *
 * @author Craig R. McClanahan
 */
final class ApplicationFilterChain implements FilterChain, CometFilterChain {

    // Used to enforce requirements of SRV.8.2 / SRV.14.2.5.1
    private static final ThreadLocal<ServletRequest> lastServicedRequest;
    private static final ThreadLocal<ServletResponse> lastServicedResponse;

    static {
        if (ApplicationDispatcher.WRAP_SAME_OBJECT) {
            lastServicedRequest = new ThreadLocal<ServletRequest>();
            lastServicedResponse = new ThreadLocal<ServletResponse>();
        } else {
            lastServicedRequest = null;
            lastServicedResponse = null;
        }
    }

    // -------------------------------------------------------------- Constants


    public static final int INCREMENT = 10;


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new chain instance with no defined filters.
     */
    public ApplicationFilterChain() {

        super();

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Filters.
     */
    private ApplicationFilterConfig[] filters = 
        new ApplicationFilterConfig[0];


    /**
     * The int which is used to maintain the current position 
     * in the filter chain.
     */
    private int pos = 0;


    /**
     * The int which gives the current number of filters in the chain.
     */
    private int n = 0;


    /**
     * The servlet instance to be executed by this chain.
     */
    private Servlet servlet = null;


    /**
     * The string manager for our package.
     */
    private static final StringManager sm =
      StringManager.getManager(Constants.Package);


    /**
     * The InstanceSupport instance associated with our Wrapper (used to
     * send "before filter" and "after filter" events.
     */
    private InstanceSupport support = null;

    
    /**
     * Static class array used when the SecurityManager is turned on and 
     * <code>doFilter</code> is invoked.
     */
    private static Class<?>[] classType = new Class[]{ServletRequest.class, 
                                                      ServletResponse.class,
                                                      FilterChain.class};
                                                   
    /**
     * Static class array used when the SecurityManager is turned on and 
     * <code>service</code> is invoked.
     */                                                 
    private static Class<?>[] classTypeUsedInService = new Class[]{
                                                         ServletRequest.class,
                                                         ServletResponse.class};

    /**
     * Static class array used when the SecurityManager is turned on and 
     * <code>doFilterEvent</code> is invoked.
     */
    private static Class<?>[] cometClassType = 
        new Class[]{ CometEvent.class, CometFilterChain.class};
                                                   
    /**
     * Static class array used when the SecurityManager is turned on and 
     * <code>event</code> is invoked.
     */                                                 
    private static Class<?>[] classTypeUsedInEvent = 
        new Class[] { CometEvent.class };


    // ---------------------------------------------------- FilterChain Methods


    /**
     * Invoke the next filter in this chain, passing the specified request
     * and response.  If there are no more filters in this chain, invoke
     * the <code>service()</code> method of the servlet itself.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response)
        throws IOException, ServletException {

        if( Globals.IS_SECURITY_ENABLED ) {
            final ServletRequest req = request;
            final ServletResponse res = response;
            try {
                java.security.AccessController.doPrivileged(
                    new java.security.PrivilegedExceptionAction<Void>() {
                        @Override
                        public Void run() 
                            throws ServletException, IOException {
                            internalDoFilter(req,res);
                            return null;
                        }
                    }
                );
            } catch( PrivilegedActionException pe) {
                Exception e = pe.getException();
                if (e instanceof ServletException)
                    throw (ServletException) e;
                else if (e instanceof IOException)
                    throw (IOException) e;
                else if (e instanceof RuntimeException)
                    throw (RuntimeException) e;
                else
                    throw new ServletException(e.getMessage(), e);
            }
        } else {
            internalDoFilter(request,response);
        }
    }

    private void internalDoFilter(ServletRequest request, 
                                  ServletResponse response)
        throws IOException, ServletException {

        // Call the next filter if there is one
        if (pos < n) {
            ApplicationFilterConfig filterConfig = filters[pos++];
            Filter filter = null;
            try {
                filter = filterConfig.getFilter();
                support.fireInstanceEvent(InstanceEvent.BEFORE_FILTER_EVENT,
                                          filter, request, response);
                
                if (request.isAsyncSupported() && "false".equalsIgnoreCase(
                        filterConfig.getFilterDef().getAsyncSupported())) {
                    request.setAttribute(Globals.ASYNC_SUPPORTED_ATTR,
                            Boolean.FALSE);
                }
                if( Globals.IS_SECURITY_ENABLED ) {
                    final ServletRequest req = request;
                    final ServletResponse res = response;
                    Principal principal = 
                        ((HttpServletRequest) req).getUserPrincipal();

                    Object[] args = new Object[]{req, res, this};
                    SecurityUtil.doAsPrivilege
                        ("doFilter", filter, classType, args, principal);
                    
                } else {  
                    filter.doFilter(request, response, this);
                }

                support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                                          filter, request, response);
            } catch (IOException e) {
                if (filter != null)
                    support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                                              filter, request, response, e);
                throw e;
            } catch (ServletException e) {
                if (filter != null)
                    support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                                              filter, request, response, e);
                throw e;
            } catch (RuntimeException e) {
                if (filter != null)
                    support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                                              filter, request, response, e);
                throw e;
            } catch (Throwable e) {
                e = ExceptionUtils.unwrapInvocationTargetException(e);
                ExceptionUtils.handleThrowable(e);
                if (filter != null)
                    support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                                              filter, request, response, e);
                throw new ServletException
                  (sm.getString("filterChain.filter"), e);
            }
            return;
        }

        // We fell off the end of the chain -- call the servlet instance
        try {
            if (ApplicationDispatcher.WRAP_SAME_OBJECT) {
                lastServicedRequest.set(request);
                lastServicedResponse.set(response);
            }

            support.fireInstanceEvent(InstanceEvent.BEFORE_SERVICE_EVENT,
                                      servlet, request, response);
            if (request.isAsyncSupported()
                    && !support.getWrapper().isAsyncSupported()) {
                request.setAttribute(Globals.ASYNC_SUPPORTED_ATTR,
                        Boolean.FALSE);
            }
            // Use potentially wrapped request from this point
            if ((request instanceof HttpServletRequest) &&
                (response instanceof HttpServletResponse)) {
                    
                if( Globals.IS_SECURITY_ENABLED ) {
                    final ServletRequest req = request;
                    final ServletResponse res = response;
                    Principal principal = 
                        ((HttpServletRequest) req).getUserPrincipal();
                    Object[] args = new Object[]{req, res};
                    SecurityUtil.doAsPrivilege("service",
                                               servlet,
                                               classTypeUsedInService, 
                                               args,
                                               principal);   
                } else {  
                    servlet.service(request, response);
                }
            } else {
                servlet.service(request, response);
            }
            support.fireInstanceEvent(InstanceEvent.AFTER_SERVICE_EVENT,
                                      servlet, request, response);
        } catch (IOException e) {
            support.fireInstanceEvent(InstanceEvent.AFTER_SERVICE_EVENT,
                                      servlet, request, response, e);
            throw e;
        } catch (ServletException e) {
            support.fireInstanceEvent(InstanceEvent.AFTER_SERVICE_EVENT,
                                      servlet, request, response, e);
            throw e;
        } catch (RuntimeException e) {
            support.fireInstanceEvent(InstanceEvent.AFTER_SERVICE_EVENT,
                                      servlet, request, response, e);
            throw e;
        } catch (Throwable e) {
            ExceptionUtils.handleThrowable(e);
            support.fireInstanceEvent(InstanceEvent.AFTER_SERVICE_EVENT,
                                      servlet, request, response, e);
            throw new ServletException
              (sm.getString("filterChain.servlet"), e);
        } finally {
            if (ApplicationDispatcher.WRAP_SAME_OBJECT) {
                lastServicedRequest.set(null);
                lastServicedResponse.set(null);
            }
        }

    }


    /**
     * Process the event, using the security manager if the option is enabled.
     * 
     * @param event the event to process
     * 
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    @Override
    public void doFilterEvent(CometEvent event)
        throws IOException, ServletException {

        if( Globals.IS_SECURITY_ENABLED ) {
            final CometEvent ev = event;
            try {
                java.security.AccessController.doPrivileged(
                    new java.security.PrivilegedExceptionAction<Void>() {
                        @Override
                        public Void run() 
                            throws ServletException, IOException {
                            internalDoFilterEvent(ev);
                            return null;
                        }
                    }
                );
            } catch( PrivilegedActionException pe) {
                Exception e = pe.getException();
                if (e instanceof ServletException)
                    throw (ServletException) e;
                else if (e instanceof IOException)
                    throw (IOException) e;
                else if (e instanceof RuntimeException)
                    throw (RuntimeException) e;
                else
                    throw new ServletException(e.getMessage(), e);
            }
        } else {
            internalDoFilterEvent(event);
        }
    }

    
    /**
     * The last request passed to a servlet for servicing from the current
     * thread.
     * 
     * @return The last request to be serviced. 
     */
    public static ServletRequest getLastServicedRequest() {
        return lastServicedRequest.get();
    }

    
    /**
     * The last response passed to a servlet for servicing from the current
     * thread.
     * 
     * @return The last response to be serviced. 
     */
    public static ServletResponse getLastServicedResponse() {
        return lastServicedResponse.get();
    }
    
    
    private void internalDoFilterEvent(CometEvent event)
        throws IOException, ServletException {

        // Call the next filter if there is one
        if (pos < n) {
            ApplicationFilterConfig filterConfig = filters[pos++];
            CometFilter filter = null;
            try {
                filter = (CometFilter) filterConfig.getFilter();
                // FIXME: No instance listener processing for events for now
                /*
                support.fireInstanceEvent(InstanceEvent.BEFORE_FILTER_EVENT,
                        filter, event);
                        */

                if( Globals.IS_SECURITY_ENABLED ) {
                    final CometEvent ev = event;
                    Principal principal = 
                        ev.getHttpServletRequest().getUserPrincipal();

                    Object[] args = new Object[]{ev, this};
                    SecurityUtil.doAsPrivilege("doFilterEvent", filter,
                            cometClassType, args, principal);

                } else {  
                    filter.doFilterEvent(event, this);
                }

                /*support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                        filter, event);*/
            } catch (IOException e) {
                /*
                if (filter != null)
                    support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                            filter, event, e);
                            */
                throw e;
            } catch (ServletException e) {
                /*
                if (filter != null)
                    support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                            filter, event, e);
                            */
                throw e;
            } catch (RuntimeException e) {
                /*
                if (filter != null)
                    support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                            filter, event, e);
                            */
                throw e;
            } catch (Throwable e) {
                e = ExceptionUtils.unwrapInvocationTargetException(e);
                ExceptionUtils.handleThrowable(e);
                /*if (filter != null)
                    support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                            filter, event, e);*/
                throw new ServletException
                    (sm.getString("filterChain.filter"), e);
            }
            return;
        }

        // We fell off the end of the chain -- call the servlet instance
        try {
            /*
            support.fireInstanceEvent(InstanceEvent.BEFORE_SERVICE_EVENT,
                    servlet, request, response);
                    */
            if( Globals.IS_SECURITY_ENABLED ) {
                final CometEvent ev = event;
                Principal principal = 
                    ev.getHttpServletRequest().getUserPrincipal();
                Object[] args = new Object[]{ ev };
                SecurityUtil.doAsPrivilege("event",
                        servlet,
                        classTypeUsedInEvent, 
                        args,
                        principal);
            } else {  
                ((CometProcessor) servlet).event(event);
            }
            /*
            support.fireInstanceEvent(InstanceEvent.AFTER_SERVICE_EVENT,
                    servlet, request, response);*/
        } catch (IOException e) {
            /*
            support.fireInstanceEvent(InstanceEvent.AFTER_SERVICE_EVENT,
                    servlet, request, response, e);
                    */
            throw e;
        } catch (ServletException e) {
            /*
            support.fireInstanceEvent(InstanceEvent.AFTER_SERVICE_EVENT,
                    servlet, request, response, e);
                    */
            throw e;
        } catch (RuntimeException e) {
            /*
            support.fireInstanceEvent(InstanceEvent.AFTER_SERVICE_EVENT,
                    servlet, request, response, e);
                    */
            throw e;
        } catch (Throwable e) {
            ExceptionUtils.handleThrowable(e);
            /*
            support.fireInstanceEvent(InstanceEvent.AFTER_SERVICE_EVENT,
                    servlet, request, response, e);
                    */
            throw new ServletException
                (sm.getString("filterChain.servlet"), e);
        }

    }


    // -------------------------------------------------------- Package Methods


    /**
     * Add a filter to the set of filters that will be executed in this chain.
     *
     * @param filterConfig The FilterConfig for the servlet to be executed
     */
    void addFilter(ApplicationFilterConfig filterConfig) {

        // Prevent the same filter being added multiple times
        for(ApplicationFilterConfig filter:filters)
            if(filter==filterConfig)
                return;

        if (n == filters.length) {
            ApplicationFilterConfig[] newFilters =
                new ApplicationFilterConfig[n + INCREMENT];
            System.arraycopy(filters, 0, newFilters, 0, n);
            filters = newFilters;
        }
        filters[n++] = filterConfig;

    }


    /**
     * Release references to the filters and wrapper executed by this chain.
     */
    void release() {

        for (int i = 0; i < n; i++) {
            filters[i] = null;
        }
        n = 0;
        pos = 0;
        servlet = null;
        support = null;

    }


    /**
     * Prepare for reuse of the filters and wrapper executed by this chain.
     */
    void reuse() {
        pos = 0;
    }


    /**
     * Set the servlet that will be executed at the end of this chain.
     *
     * @param servlet The Wrapper for the servlet to be executed
     */
    void setServlet(Servlet servlet) {

        this.servlet = servlet;

    }


    /**
     * Set the InstanceSupport object used for event notifications
     * for this filter chain.
     *
     * @param support The InstanceSupport object for our Wrapper
     */
    void setSupport(InstanceSupport support) {

        this.support = support;

    }
}
