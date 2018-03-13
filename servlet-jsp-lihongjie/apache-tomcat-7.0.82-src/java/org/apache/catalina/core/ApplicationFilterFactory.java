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


import javax.servlet.DispatcherType;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;

import org.apache.catalina.Globals;
import org.apache.catalina.Wrapper;
import org.apache.catalina.comet.CometFilter;
import org.apache.catalina.connector.Request;
import org.apache.catalina.deploy.FilterMap;
import org.apache.tomcat.util.ExceptionUtils;

/**
 * Factory for the creation and caching of Filters and creation 
 * of Filter Chains.
 *
 * @author Greg Murray
 * @author Remy Maucherat
 */

public final class ApplicationFilterFactory {


    // -------------------------------------------------------------- Constants


    /**
     * @deprecated  Use {@link Globals#DISPATCHER_TYPE_ATTR}
     */
    @Deprecated
    public static final String DISPATCHER_TYPE_ATTR =
            Globals.DISPATCHER_TYPE_ATTR;
    /**
     * @deprecated  Use {@link Globals#DISPATCHER_REQUEST_PATH_ATTR}
     */
    @Deprecated
    public static final String DISPATCHER_REQUEST_PATH_ATTR = 
            Globals.DISPATCHER_REQUEST_PATH_ATTR;

    private static ApplicationFilterFactory factory = null;


    // ----------------------------------------------------------- Constructors

    private ApplicationFilterFactory() {
        // Prevent instantiation outside of the getInstanceMethod().
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Return the factory instance.
     */
    public static ApplicationFilterFactory getInstance() {
        if (factory == null) {
            factory = new ApplicationFilterFactory();
        }
        return factory;
    }


    /**
     * Construct and return a FilterChain implementation that will wrap the
     * execution of the specified servlet instance.  If we should not execute
     * a filter chain at all, return <code>null</code>.
     *
     * @param request The servlet request we are processing
     * @param servlet The servlet instance to be wrapped
     */
    public ApplicationFilterChain createFilterChain
        (ServletRequest request, Wrapper wrapper, Servlet servlet) {

        // get the dispatcher type
        DispatcherType dispatcher = null; 
        if (request.getAttribute(Globals.DISPATCHER_TYPE_ATTR) != null) {
            dispatcher = (DispatcherType) request.getAttribute(
                    Globals.DISPATCHER_TYPE_ATTR);
        }
        String requestPath = null;
        Object attribute = request.getAttribute(
                Globals.DISPATCHER_REQUEST_PATH_ATTR);
        
        if (attribute != null){
            requestPath = attribute.toString();
        }
        
        // If there is no servlet to execute, return null
        if (servlet == null)
            return (null);

        boolean comet = false;
        
        // Create and initialize a filter chain object
        ApplicationFilterChain filterChain = null;
        if (request instanceof Request) {
            Request req = (Request) request;
            comet = req.isComet();
            if (Globals.IS_SECURITY_ENABLED) {
                // Security: Do not recycle
                filterChain = new ApplicationFilterChain();
                if (comet) {
                    req.setFilterChain(filterChain);
                }
            } else {
                filterChain = (ApplicationFilterChain) req.getFilterChain();
                if (filterChain == null) {
                    filterChain = new ApplicationFilterChain();
                    req.setFilterChain(filterChain);
                }
            }
        } else {
            // Request dispatcher in use
            filterChain = new ApplicationFilterChain();
        }

        filterChain.setServlet(servlet);

        filterChain.setSupport
            (((StandardWrapper)wrapper).getInstanceSupport());

        // Acquire the filter mappings for this Context
        StandardContext context = (StandardContext) wrapper.getParent();
        FilterMap filterMaps[] = context.findFilterMaps();

        // If there are no filter mappings, we are done
        if ((filterMaps == null) || (filterMaps.length == 0))
            return (filterChain);

        // Acquire the information we will need to match filter mappings
        String servletName = wrapper.getName();

        // Add the relevant path-mapped filters to this filter chain
        for (int i = 0; i < filterMaps.length; i++) {
            if (!matchDispatcher(filterMaps[i] ,dispatcher)) {
                continue;
            }
            if (!matchFiltersURL(filterMaps[i], requestPath))
                continue;
            ApplicationFilterConfig filterConfig = (ApplicationFilterConfig)
                context.findFilterConfig(filterMaps[i].getFilterName());
            if (filterConfig == null) {
                // FIXME - log configuration problem
                continue;
            }
            boolean isCometFilter = false;
            if (comet) {
                try {
                    isCometFilter = filterConfig.getFilter() instanceof CometFilter;
                } catch (Exception e) {
                    // Note: The try catch is there because getFilter has a lot of 
                    // declared exceptions. However, the filter is allocated much
                    // earlier
                    Throwable t = ExceptionUtils.unwrapInvocationTargetException(e);
                    ExceptionUtils.handleThrowable(t);
                }
                if (isCometFilter) {
                    filterChain.addFilter(filterConfig);
                }
            } else {
                filterChain.addFilter(filterConfig);
            }
        }

        // Add filters that match on servlet name second
        for (int i = 0; i < filterMaps.length; i++) {
            if (!matchDispatcher(filterMaps[i] ,dispatcher)) {
                continue;
            }
            if (!matchFiltersServlet(filterMaps[i], servletName))
                continue;
            ApplicationFilterConfig filterConfig = (ApplicationFilterConfig)
                context.findFilterConfig(filterMaps[i].getFilterName());
            if (filterConfig == null) {
                // FIXME - log configuration problem
                continue;
            }
            boolean isCometFilter = false;
            if (comet) {
                try {
                    isCometFilter = filterConfig.getFilter() instanceof CometFilter;
                } catch (Exception e) {
                    // Note: The try catch is there because getFilter has a lot of 
                    // declared exceptions. However, the filter is allocated much
                    // earlier
                }
                if (isCometFilter) {
                    filterChain.addFilter(filterConfig);
                }
            } else {
                filterChain.addFilter(filterConfig);
            }
        }

        // Return the completed filter chain
        return (filterChain);

    }


    // -------------------------------------------------------- Private Methods


    /**
     * Return <code>true</code> if the context-relative request path
     * matches the requirements of the specified filter mapping;
     * otherwise, return <code>false</code>.
     *
     * @param filterMap Filter mapping being checked
     * @param requestPath Context-relative request path of this request
     */
    private boolean matchFiltersURL(FilterMap filterMap, String requestPath) {

        // Check the specific "*" special URL pattern, which also matches
        // named dispatches
        if (filterMap.getMatchAllUrlPatterns())
            return (true);
        
        if (requestPath == null)
            return (false);

        // Match on context relative request path
        String[] testPaths = filterMap.getURLPatterns();
        
        for (int i = 0; i < testPaths.length; i++) {
            if (matchFiltersURL(testPaths[i], requestPath)) {
                return (true);
            }
        }
        
        // No match
        return (false);
        
    }
    

    /**
     * Return <code>true</code> if the context-relative request path
     * matches the requirements of the specified filter mapping;
     * otherwise, return <code>false</code>.
     *
     * @param testPath URL mapping being checked
     * @param requestPath Context-relative request path of this request
     */
    private boolean matchFiltersURL(String testPath, String requestPath) {
        
        if (testPath == null)
            return (false);

        // Case 1 - Exact Match
        if (testPath.equals(requestPath))
            return (true);

        // Case 2 - Path Match ("/.../*")
        if (testPath.equals("/*"))
            return (true);
        if (testPath.endsWith("/*")) {
            if (testPath.regionMatches(0, requestPath, 0, 
                                       testPath.length() - 2)) {
                if (requestPath.length() == (testPath.length() - 2)) {
                    return (true);
                } else if ('/' == requestPath.charAt(testPath.length() - 2)) {
                    return (true);
                }
            }
            return (false);
        }

        // Case 3 - Extension Match
        if (testPath.startsWith("*.")) {
            int slash = requestPath.lastIndexOf('/');
            int period = requestPath.lastIndexOf('.');
            if ((slash >= 0) && (period > slash) 
                && (period != requestPath.length() - 1)
                && ((requestPath.length() - period) 
                    == (testPath.length() - 1))) {
                return (testPath.regionMatches(2, requestPath, period + 1,
                                               testPath.length() - 2));
            }
        }

        // Case 4 - "Default" Match
        return (false); // NOTE - Not relevant for selecting filters

    }


    /**
     * Return <code>true</code> if the specified servlet name matches
     * the requirements of the specified filter mapping; otherwise
     * return <code>false</code>.
     *
     * @param filterMap Filter mapping being checked
     * @param servletName Servlet name being checked
     */
    private boolean matchFiltersServlet(FilterMap filterMap, 
                                        String servletName) {

        if (servletName == null) {
            return (false);
        }
        // Check the specific "*" special servlet name
        else if (filterMap.getMatchAllServletNames()) {
            return (true);
        } else {
            String[] servletNames = filterMap.getServletNames();
            for (int i = 0; i < servletNames.length; i++) {
                if (servletName.equals(servletNames[i])) {
                    return (true);
                }
            }
            return false;
        }

    }


    /**
     * Convenience method which returns true if  the dispatcher type
     * matches the dispatcher types specified in the FilterMap
     */
    private boolean matchDispatcher(FilterMap filterMap, DispatcherType type) {
        switch (type) {
            case FORWARD : {
                if ((filterMap.getDispatcherMapping() & FilterMap.FORWARD) != 0) {
                    return true;
                }
                break;
            }
            case INCLUDE : {
                if ((filterMap.getDispatcherMapping() & FilterMap.INCLUDE) != 0) {
                    return true;
                }
                break;
            }
            case REQUEST : {
                if ((filterMap.getDispatcherMapping() & FilterMap.REQUEST) != 0) {
                    return true;
                }
                break;
            }
            case ERROR : {
                if ((filterMap.getDispatcherMapping() & FilterMap.ERROR) != 0) {
                    return true;
                }
                break;
            }
            case ASYNC : {
                if ((filterMap.getDispatcherMapping() & FilterMap.ASYNC) != 0) {
                    return true;
                }
                break;
            }
        }
        return false;
    }


}
