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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Globals;
import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometFilter;
import org.apache.catalina.comet.CometFilterChain;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.http.Parameters.FailReason;

/**
 * Filter that will reject requests if there was a failure during parameter
 * parsing. This filter can be used to ensure that none parameter values
 * submitted by client are lost.
 *
 * <p>
 * Note that it has side effect that it triggers parameter parsing and thus
 * consumes the body for POST requests. Parameter parsing does check content
 * type of the request, so there should not be problems with addresses that use
 * <code>request.getInputStream()</code> and <code>request.getReader()</code>,
 * if requests parsed by them do not use standard value for content mime-type.
 */
public class FailedRequestFilter extends FilterBase implements CometFilter {

    private static final Log log = LogFactory.getLog(FailedRequestFilter.class);

    @Override
    protected Log getLogger() {
        return log;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (!isGoodRequest(request)) {
            FailReason reason = (FailReason) request.getAttribute(
                    Globals.PARAMETER_PARSE_FAILED_REASON_ATTR);

            int status;

            switch (reason) {
                case IO_ERROR:
                    // Not the client's fault
                    status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                    break;
                case POST_TOO_LARGE:
                    status = HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE;
                    break;
                case TOO_MANY_PARAMETERS:
                    // 413/414 aren't really correct here since the request body
                    // and/or URI could be well below any limits set. Use the
                    // default.
                case UNKNOWN: // Assume the client is at fault
                // Various things that the client can get wrong that don't have
                // a specific status code so use the default.
                case INVALID_CONTENT_TYPE:
                case MULTIPART_CONFIG_INVALID:
                case NO_NAME:
                case REQUEST_BODY_INCOMPLETE:
                case URL_DECODING:
                case CLIENT_DISCONNECT:
                    // Client is never going to see this so this is really just
                    // for the access logs. The default is fine.
                default:
                    // 400
                    status = HttpServletResponse.SC_BAD_REQUEST;
                    break;
            }

            ((HttpServletResponse) response).sendError(status);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void doFilterEvent(CometEvent event, CometFilterChain chain)
            throws IOException, ServletException {
        if (event.getEventType() == CometEvent.EventType.BEGIN
                && !isGoodRequest(event.getHttpServletRequest())) {
            event.getHttpServletResponse().sendError(
                    HttpServletResponse.SC_BAD_REQUEST);
            event.close();
            return;
        }
        chain.doFilterEvent(event);
    }

    private boolean isGoodRequest(ServletRequest request) {
        // Trigger parsing of parameters
        request.getParameter("none");
        // Detect failure
        if (request.getAttribute(Globals.PARAMETER_PARSE_FAILED_ATTR) != null) {
            return false;
        }
        return true;
    }

    @Override
    protected boolean isConfigProblemFatal() {
        return true;
    }

}
