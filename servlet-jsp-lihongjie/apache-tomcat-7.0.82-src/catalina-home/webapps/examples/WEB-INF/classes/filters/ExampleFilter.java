/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package filters;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * Example filter that can be attached to either an individual servlet
 * or to a URL pattern.  This filter performs the following functions:
 * <ul>
 * <li>Attaches itself as a request attribute, under the attribute name
 *     defined by the value of the <code>attribute</code> initialization
 *     parameter.</li>
 * <li>Calculates the number of milliseconds required to perform the
 *     servlet processing required by this request, including any
 *     subsequently defined filters, and logs the result to the servlet
 *     context log for this application.
 * </ul>
 *
 * @author Craig McClanahan
 */
public final class ExampleFilter implements Filter {


    // ----------------------------------------------------- Instance Variables


    /**
     * The request attribute name under which we store a reference to ourself.
     */
    private String attribute = null;


    /**
     * The filter configuration object we are associated with.  If this value
     * is null, this filter instance is not currently configured.
     */
    private FilterConfig filterConfig = null;


    // --------------------------------------------------------- Public Methods


    /**
     * Take this filter out of service.
     */
    @Override
    public void destroy() {

        this.attribute = null;
        this.filterConfig = null;

    }


    /**
     * Time the processing that is performed by all subsequent filters in the
     * current filter stack, including the ultimately invoked servlet.
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {

        // Store ourselves as a request attribute (if requested)
        if (attribute != null)
            request.setAttribute(attribute, this);

        // Time and log the subsequent processing
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        long stopTime = System.currentTimeMillis();
        filterConfig.getServletContext().log
            (this.toString() + ": " + (stopTime - startTime) +
             " milliseconds");

    }


    /**
     * Place this filter into service.
     *
     * @param fConfig The filter configuration object
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {

        this.filterConfig = fConfig;
        this.attribute = fConfig.getInitParameter("attribute");

    }


    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {

        if (filterConfig == null)
            return ("TimingFilter()");
        StringBuilder sb = new StringBuilder("TimingFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());

    }


}

