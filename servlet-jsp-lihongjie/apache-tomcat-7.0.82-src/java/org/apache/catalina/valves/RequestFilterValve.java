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
package org.apache.catalina.valves;


import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.juli.logging.Log;

/**
 * Implementation of a Valve that performs filtering based on comparing the
 * appropriate request property (selected based on which subclass you choose
 * to configure into your Container's pipeline) against the regular expressions
 * configured for this Valve.
 * <p>
 * This valve is configured by setting the <code>allow</code> and/or
 * <code>deny</code> properties to a regular expressions (in the syntax
 * supported by {@link Pattern}) to which the appropriate request property will
 * be compared. Evaluation proceeds as follows:
 * <ul>
 * <li>The subclass extracts the request property to be filtered, and
 *     calls the common <code>process()</code> method.
 * <li>If there is a deny expression configured, the property will be compared
 *     to the expression. If a match is found, this request will be rejected
 *     with a "Forbidden" HTTP response.</li>
 * <li>If there is a allow expression configured, the property will be compared
 *     to each such expression.  If a match is found, this request will be
 *     allowed to pass through to the next Valve in the current pipeline.</li>
 * <li>If a deny expression was specified but no allow expression, allow this
 *     request to pass through (because none of the deny expressions matched
 *     it).
 * <li>The request will be rejected with a "Forbidden" HTTP response.</li>
 * </ul>
 * <p>
 * As an option the valve can generate an invalid <code>authenticate</code>
 * header instead of denying the request. This can be combined with the
 * context attribute <code>preemptiveAuthentication="true"</code> and an
 * authenticator to force authentication instead of denial.
 * <p>
 * This Valve may be attached to any Container, depending on the granularity
 * of the filtering you wish to perform.
 *
 * @author Craig R. McClanahan
 */
public abstract class RequestFilterValve extends ValveBase {

    //------------------------------------------------------ Constructor
    public RequestFilterValve() {
        super(true);
    }

    // ----------------------------------------------------- Class Variables


    /**
     * The descriptive information related to this implementation.
     */
    private static final String info =
        "org.apache.catalina.valves.RequestFilterValve/1.0";


    // ----------------------------------------------------- Instance Variables


    /**
     * The regular expression used to test for allowed requests.
     */
    protected volatile Pattern allow = null;


    /**
     * The current allow configuration value that may or may not compile into a
     * valid {@link Pattern}.
     */
    protected volatile String allowValue = null;


    /**
     * Helper variable to catch configuration errors.
     * It is <code>true</code> by default, but becomes <code>false</code>
     * if there was an attempt to assign an invalid value to the
     * <code>allow</code> pattern.
     */
    protected volatile boolean allowValid = true;


    /**
     * The regular expression used to test for denied requests.
     */
    protected volatile Pattern deny = null;


    /**
     * The current deny configuration value that may or may not compile into a
     * valid {@link Pattern}.
     */
    protected volatile String denyValue = null;


    /**
     * Helper variable to catch configuration errors.
     * It is <code>true</code> by default, but becomes <code>false</code>
     * if there was an attempt to assign an invalid value to the
     * <code>deny</code> pattern.
     */
    protected volatile boolean denyValid = true;


    /**
     * The HTTP response status code that is used when rejecting denied
     * request. It is 403 by default, but may be changed to be 404.
     */
    protected int denyStatus = HttpServletResponse.SC_FORBIDDEN;

    /**
     * <p>If <code>invalidAuthenticationWhenDeny</code> is true
     * and the context has <code>preemptiveAuthentication</code>
     * set, set an invalid authorization header to trigger basic auth
     * instead of denying the request..
     */
    private boolean invalidAuthenticationWhenDeny = false;

    /**
     * Flag deciding whether we add the server connector port to the property
     * compared in the filtering method. The port will be appended
     * using a ";" as a separator.
     */
    private volatile boolean addConnectorPort = false;

    // ------------------------------------------------------------- Properties


    /**
     * Return the regular expression used to test for allowed requests for this
     * Valve, if any; otherwise, return <code>null</code>.
     */
    public String getAllow() {
        return allowValue;
    }


    /**
     * Set the regular expression used to test for allowed requests for this
     * Valve, if any.
     *
     * @param allow The new allow expression
     */
    public void setAllow(String allow) {
        if (allow == null || allow.length() == 0) {
            this.allow = null;
            allowValue = null;
            allowValid = true;
        } else {
            boolean success = false;
            try {
                allowValue = allow;
                this.allow = Pattern.compile(allow);
                success = true;
            } finally {
                allowValid = success;
            }
        }
    }


    /**
     * Return the regular expression used to test for denied requests for this
     * Valve, if any; otherwise, return <code>null</code>.
     */
    public String getDeny() {
        return denyValue;
    }


    /**
     * Set the regular expression used to test for denied requests for this
     * Valve, if any.
     *
     * @param deny The new deny expression
     */
    public void setDeny(String deny) {
        if (deny == null || deny.length() == 0) {
            this.deny = null;
            denyValue = null;
            denyValid = true;
        } else {
            boolean success = false;
            try {
                denyValue = deny;
                this.deny = Pattern.compile(deny);
                success = true;
            } finally {
                denyValid = success;
            }
        }
    }


    /**
     * Returns {@code false} if the last change to the {@code allow} pattern did
     * not apply successfully. E.g. if the pattern is syntactically
     * invalid.
     */
    public final boolean isAllowValid() {
        return allowValid;
    }


    /**
     * Returns {@code false} if the last change to the {@code deny} pattern did
     * not apply successfully. E.g. if the pattern is syntactically
     * invalid.
     */
    public final boolean isDenyValid() {
        return denyValid;
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


    /**
     * Return descriptive information about this Valve implementation.
     */
    @Override
    public String getInfo() {

        return (info);

    }


    /**
     * Return true if a deny is handled by setting an invalid auth header.
     */
    public boolean getInvalidAuthenticationWhenDeny() {
        return invalidAuthenticationWhenDeny;
    }


    /**
     * Set invalidAuthenticationWhenDeny property.
     */
    public void setInvalidAuthenticationWhenDeny(boolean value) {
        invalidAuthenticationWhenDeny = value;
    }


    /**
     * Get the flag deciding whether we add the server connector port to the
     * property compared in the filtering method. The port will be appended
     * using a ";" as a separator.
     * @return <code>true</code> to add the connector port
     */
    public boolean getAddConnectorPort() {
        return addConnectorPort;
    }


    /**
     * Set the flag deciding whether we add the server connector port to the
     * property compared in the filtering method. The port will be appended
     * using a ";" as a separator.
     *
     * @param addConnectorPort The new flag
     */
    public void setAddConnectorPort(boolean addConnectorPort) {
        this.addConnectorPort = addConnectorPort;
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
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public abstract void invoke(Request request, Response response)
        throws IOException, ServletException;


    // ------------------------------------------------------ Protected Methods


    @Override
    protected void initInternal() throws LifecycleException {
        super.initInternal();
        if (!allowValid || !denyValid) {
            throw new LifecycleException(
                    sm.getString("requestFilterValve.configInvalid"));
        }
    }


    @Override
    protected synchronized void startInternal() throws LifecycleException {
        if (!allowValid || !denyValid) {
            throw new LifecycleException(
                    sm.getString("requestFilterValve.configInvalid"));
        }
        super.startInternal();
    }


    /**
     * Perform the filtering that has been configured for this Valve, matching
     * against the specified request property.
     *
     * @param property The request property on which to filter
     * @param request The servlet request to be processed
     * @param response The servlet response to be processed
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    protected void process(String property, Request request, Response response)
            throws IOException, ServletException {

        if (isAllowed(property)) {
            getNext().invoke(request, response);
            return;
        }

        if (getLog().isDebugEnabled()) {
            getLog().debug(sm.getString("requestFilterValve.deny",
                    request.getRequestURI(), property));
        }

        // Deny this request
        denyRequest(request, response);
    }


    protected abstract Log getLog();


    /**
     * Reject the request that was denied by this valve.
     * <p>If <code>invalidAuthenticationWhenDeny</code> is true
     * and the context has <code>preemptiveAuthentication</code>
     * set, set an invalid authorization header to trigger basic auth.
     *
     * @param request The servlet request to be processed
     * @param response The servlet response to be processed
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    protected void denyRequest(Request request, Response response)
            throws IOException, ServletException {
        if (invalidAuthenticationWhenDeny) {
            Context context = request.getContext();
            if (context != null && context.getPreemptiveAuthentication()) {
                if (request.getCoyoteRequest().getMimeHeaders().getValue("authorization") == null) {
                    request.getCoyoteRequest().getMimeHeaders().addValue("authorization").setString("invalid");
                }
                getNext().invoke(request, response);
                return;
            }
        }
        response.sendError(denyStatus);
    }


    /**
     * Perform the test implemented by this Valve, matching against the
     * specified request property value. This method is public so that it can be
     * called through JMX, e.g. to test whether certain IP address is allowed or
     * denied by the valve configuration.
     *
     * @param property
     *            The request property value on which to filter
     */
    public boolean isAllowed(String property) {
        // Use local copies for thread safety
        Pattern deny = this.deny;
        Pattern allow = this.allow;

        // Check the deny patterns, if any
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
}
