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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter that attempts to force MS WebDAV clients connecting on port 80 to use
 * a WebDAV client that actually works. Other workarounds that might help
 * include:
 * <ul>
 *   <li>Specifying the port, even if it is port 80, when trying to
 *       connect.</li>
 *   <li>Cancelling the first authentication dialog box and then trying to
 *       reconnect.</li>
 * </ul>
 * 
 * Generally each different version of the MS client has a different set of
 * problems.
 * <p>
 * TODO: Update this filter to recognise specific MS clients and apply the
 *       appropriate workarounds for that particular client
 * <p>      
 * As a filter, this is configured in web.xml like any other Filter. You usually
 * want to map this filter to whatever your WebDAV servlet is mapped to.
 * <p>
 * In addition to the issues fixed by this Filter, the following issues have
 * also been observed that cannot be fixed by this filter. Where possible the
 * filter will add an message to the logs.
 * <p>
 * XP x64 SP2 (MiniRedir Version 3790)
 * <ul>
 *   <li>Only connects to port 80</li>
 *   <li>Unknown issue means it doesn't work</li>
 * </ul>
 */
public class WebdavFixFilter implements Filter {

    private static final String LOG_MESSAGE_PREAMBLE =
        "WebdavFixFilter: Detected client problem: ";

    /* Start string for all versions */
    private static final String UA_MINIDIR_START =
        "Microsoft-WebDAV-MiniRedir";
    /* XP 32-bit SP3 */
    private static final String UA_MINIDIR_5_1_2600 =
        "Microsoft-WebDAV-MiniRedir/5.1.2600";
    
    /* XP 64-bit SP2 */
    private static final String UA_MINIDIR_5_2_3790 =
        "Microsoft-WebDAV-MiniRedir/5.2.3790";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // NOOP
    }

    @Override
    public void destroy() {
        // NOOP
    }

    /**
     * Check for the broken MS WebDAV client and if detected issue a re-direct
     * that hopefully will cause the non-broken client to be used.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) ||
                !(response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        HttpServletResponse httpResponse = ((HttpServletResponse) response);
        String ua = httpRequest.getHeader("User-Agent");
        
        if (ua == null || ua.length() == 0 ||
                !ua.startsWith(UA_MINIDIR_START)) {
            // No UA or starts with non MS value
            // Hope everything just works...
            chain.doFilter(request, response);
        } else if (ua.startsWith(UA_MINIDIR_5_1_2600)) {
            // XP 32-bit SP3 - needs redirect with explicit port
            httpResponse.sendRedirect(buildRedirect(httpRequest));
        } else if (ua.startsWith(UA_MINIDIR_5_2_3790)) {
            // XP 64-bit SP2
            if (!"".equals(httpRequest.getContextPath())) {
                log(request,
                        "XP-x64-SP2 clients only work with the root context");
            }
            // Namespace issue maybe
            // see http://greenbytes.de/tech/webdav/webdav-redirector-list.html
            log(request, "XP-x64-SP2 is known not to work with WebDAV Servlet");
            
            chain.doFilter(request, response);
        } else {
            // Don't know which MS client it is - try the redirect with an
            // explicit port in the hope that it moves the client to a different
            // WebDAV implementation that works
            httpResponse.sendRedirect(buildRedirect(httpRequest));
        }        
    }

    private String buildRedirect(HttpServletRequest request) {
        StringBuilder location =
            new StringBuilder(request.getRequestURL().length());
        location.append(request.getScheme());
        location.append("://");
        location.append(request.getServerName());
        location.append(':');
        // If we include the port, even if it is 80, then MS clients will use
        // a WebDAV client that works rather than the MiniRedir that has
        // problems with BASIC authentication
        location.append(request.getServerPort());
        location.append(request.getRequestURI());
        return location.toString();
    }

    private void log(ServletRequest request, String msg) {
        StringBuilder builder = new StringBuilder(LOG_MESSAGE_PREAMBLE);
        builder.append(msg);
        request.getServletContext().log(builder.toString());
    }
}
