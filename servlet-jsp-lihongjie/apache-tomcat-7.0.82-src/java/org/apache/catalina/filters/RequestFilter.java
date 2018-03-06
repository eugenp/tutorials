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
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometFilter;
import org.apache.catalina.comet.CometFilterChain;

/**
 * Implementation of a Filter that performs filtering based on comparing the
 * appropriate request property (selected based on which subclass you choose
 * to configure into your Container's pipeline) against the regular expressions
 * configured for this Filter.
 * <p>
 * This filter is configured by setting the <code>allow</code> and/or
 * <code>deny</code> properties to a regular expressions (in the syntax
 * supported by {@link Pattern}) to which the appropriate request property will
 * be compared.  Evaluation proceeds as follows:
 * <ul>
 * <li>The subclass extracts the request property to be filtered, and
 *     calls the common <code>process()</code> method.
 * <li>If there is a deny expression configured, the property will be compared
 *     to the expression. If a match is found, this request will be rejected
 *     with a "Forbidden" HTTP response.</li>
 * <li>If there is a allow expression configured, the property will be compared
 *     to the expression. If a match is found, this request will be allowed to
 *     pass through to the next filter in the current pipeline.</li>
 * <li>If a deny expression was specified but no allow expression, allow this
 *     request to pass through (because none of the deny expressions matched
 *     it).
 * <li>The request will be rejected with a "Forbidden" HTTP response.</li>
 * </ul>
 */
public abstract class RequestFilter extends FilterBase implements CometFilter {


    // ----------------------------------------------------- Instance Variables

    /**
     * The regular expression used to test for allowed requests.
     */
    protected Pattern allow = null;

    /**
     * The regular expression used to test for denied requests.
     */
    protected Pattern deny = null;
    
    /**
     * The HTTP response status code that is used when rejecting denied
     * request. It is 403 by default, but may be changed to be 404.
     */
    protected int denyStatus = HttpServletResponse.SC_FORBIDDEN;

    /**
     * mime type -- "text/plain"
     */
    private static final String PLAIN_TEXT_MIME_TYPE = "text/plain";


    // ------------------------------------------------------------- Properties


    /**
     * Return the regular expression used to test for allowed requests for this
     * Filter, if any; otherwise, return <code>null</code>.
     */
    public String getAllow() {
        if (allow == null) {
            return null;
        }
        return allow.toString();
    }


    /**
     * Set the regular expression used to test for allowed requests for this
     * Filter, if any.
     *
     * @param allow The new allow expression
     */
    public void setAllow(String allow) {
        if (allow == null || allow.length() == 0) {
            this.allow = null;
        } else {
            this.allow = Pattern.compile(allow);
        }
    }


    /**
     * Return the regular expression used to test for denied requests for this
     * Filter, if any; otherwise, return <code>null</code>.
     */
    public String getDeny() {
        if (deny == null) {
            return null;
        }
        return deny.toString();
    }


    /**
     * Set the regular expression used to test for denied requests for this
     * Filter, if any.
     *
     * @param deny The new deny expression
     */
    public void setDeny(String deny) {
        if (deny == null || deny.length() == 0) {
            this.deny = null;
        } else {
            this.deny = Pattern.compile(deny);
        }
    }


    /**
     * Return response status code that is used to reject denied request.
     */
    public int getDenyStatus() {
        return denyStatus;
    }


    /**
     * Set response status code that is used to reject denied request.
     */
    public void setDenyStatus(int denyStatus) {
        this.denyStatus = denyStatus;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Extract the desired request property, and pass it (along with the
     * specified request and response objects) to the protected
     * <code>process()</code> method to perform the actual filtering.
     * This method must be implemented by a concrete subclass.
     *
     * @param request The servlet request to be processed
     * @param response The servlet response to be created
     * @param chain The filter chain
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public abstract void doFilter(ServletRequest request,
            ServletResponse response, FilterChain chain) throws IOException,
            ServletException;

      
    // ------------------------------------------------------ Protected Methods


    @Override
    protected boolean isConfigProblemFatal() {
        return true;
    }


    /**
     * Perform the filtering that has been configured for this Filter, matching
     * against the specified request property.
     *
     * @param property The request property on which to filter
     * @param request The servlet request to be processed
     * @param response The servlet response to be processed
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    protected void process(String property, ServletRequest request,
            ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (isAllowed(property)) {
            chain.doFilter(request, response);
        } else {
            if (response instanceof HttpServletResponse) {
                if (getLogger().isDebugEnabled()) {
                    getLogger().debug(sm.getString("requestFilter.deny",
                            ((HttpServletRequest) request).getRequestURI(), property));
                }
                ((HttpServletResponse) response).sendError(denyStatus);
            } else {
                sendErrorWhenNotHttp(response);
            }
        }
    }


    /**
     * Perform the filtering that has been configured for this Filter, matching
     * against the specified request property.
     * 
     * @param property  The property to check against the allow/deny rules
     * @param event     The comet event to be filtered
     * @param chain     The comet filter chain
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    protected void processCometEvent(String property, CometEvent event,
            CometFilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = event.getHttpServletResponse();
        
        if (isAllowed(property)) {
            chain.doFilterEvent(event);
        } else {
            response.sendError(denyStatus);
            event.close();
        }
    }

    /**
     * Process the allow and deny rules for the provided property.
     * 
     * @param property  The property to test against the allow and deny lists
     * @return          <code>true</code> if this request should be allowed,
     *                  <code>false</code> otherwise
     */
    private boolean isAllowed(String property) {
        if (deny != null && deny.matcher(property).matches()) {
            return false;
        }
     
        // Check the allow patterns, if any
        if (allow != null && allow.matcher(property).matches()) {
            return true;
        }

        // Allow if denies specified but not allows
        if (deny != null && allow == null) {
            return true;
        }

        // Deny this request
        return false;
    }

    private void sendErrorWhenNotHttp(ServletResponse response)
            throws IOException {
        response.setContentType(PLAIN_TEXT_MIME_TYPE);
        response.getWriter().write(sm.getString("http.403"));
        response.getWriter().flush();
    }
}
