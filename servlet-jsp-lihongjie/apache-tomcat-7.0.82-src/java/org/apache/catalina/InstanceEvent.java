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


package org.apache.catalina;


import java.util.EventObject;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * General event for notifying listeners of significant events related to
 * a specific instance of a Servlet, or a specific instance of a Filter,
 * as opposed to the Wrapper component that manages it.
 *
 * @author Craig R. McClanahan
 */
public final class InstanceEvent extends EventObject {

    private static final long serialVersionUID = 1L;


    /**
     * The event indicating that the <code>init()</code> method is about
     * to be called for this instance.
     */
    public static final String BEFORE_INIT_EVENT = "beforeInit";


    /**
     * The event indicating that the <code>init()</code> method has returned.
     */
    public static final String AFTER_INIT_EVENT = "afterInit";


    /**
     * The event indicating that the <code>service()</code> method is about
     * to be called on a servlet.  The <code>servlet</code> property contains
     * the servlet being called, and the <code>request</code> and
     * <code>response</code> properties contain the current request and
     * response being processed.
     */
    public static final String BEFORE_SERVICE_EVENT = "beforeService";


    /**
     * The event indicating that the <code>service()</code> method has
     * returned.  The <code>servlet</code> property contains the servlet
     * that was called, and the <code>request</code> and
     * <code>response</code> properties contain the current request and
     * response being processed.
     */
    public static final String AFTER_SERVICE_EVENT = "afterService";


    /**
     * The event indicating that the <code>destroy</code> method is about
     * to be called for this instance.
     */
    public static final String BEFORE_DESTROY_EVENT = "beforeDestroy";


    /**
     * The event indicating that the <code>destroy()</code> method has
     * returned.
     */
    public static final String AFTER_DESTROY_EVENT = "afterDestroy";


    /**
     * The event indicating that the <code>service()</code> method of a
     * servlet accessed via a request dispatcher is about to be called.
     * The <code>servlet</code> property contains a reference to the
     * dispatched-to servlet instance, and the <code>request</code> and
     * <code>response</code> properties contain the current request and
     * response being processed.  The <code>wrapper</code> property will
     * contain a reference to the dispatched-to Wrapper.
     */
    public static final String BEFORE_DISPATCH_EVENT = "beforeDispatch";


    /**
     * The event indicating that the <code>service()</code> method of a
     * servlet accessed via a request dispatcher has returned.  The
     * <code>servlet</code> property contains a reference to the
     * dispatched-to servlet instance, and the <code>request</code> and
     * <code>response</code> properties contain the current request and
     * response being processed.  The <code>wrapper</code> property will
     * contain a reference to the dispatched-to Wrapper.
     */
    public static final String AFTER_DISPATCH_EVENT = "afterDispatch";


    /**
     * The event indicating that the <code>doFilter()</code> method of a
     * Filter is about to be called.  The <code>filter</code> property
     * contains a reference to the relevant filter instance, and the
     * <code>request</code> and <code>response</code> properties contain
     * the current request and response being processed.
     */
    public static final String BEFORE_FILTER_EVENT = "beforeFilter";


    /**
     * The event indicating that the <code>doFilter()</code> method of a
     * Filter has returned.  The <code>filter</code> property contains
     * a reference to the relevant filter instance, and the
     * <code>request</code> and <code>response</code> properties contain
     * the current request and response being processed.
     */
    public static final String AFTER_FILTER_EVENT = "afterFilter";


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new InstanceEvent with the specified parameters.  This
     * constructor is used for filter lifecycle events.
     *
     * @param wrapper Wrapper managing this servlet instance
     * @param filter Filter instance for which this event occurred
     * @param type Event type (required)
     */
    public InstanceEvent(Wrapper wrapper, Filter filter, String type) {

      super(wrapper);
      this.filter = filter;
      this.servlet = null;
      this.type = type;

    }


    /**
     * Construct a new InstanceEvent with the specified parameters.  This
     * constructor is used for filter lifecycle events.
     *
     * @param wrapper Wrapper managing this servlet instance
     * @param filter Filter instance for which this event occurred
     * @param type Event type (required)
     * @param exception Exception that occurred
     */
    public InstanceEvent(Wrapper wrapper, Filter filter, String type,
                         Throwable exception) {

      super(wrapper);
      this.filter = filter;
      this.servlet = null;
      this.type = type;
      this.exception = exception;

    }


    /**
     * Construct a new InstanceEvent with the specified parameters.  This
     * constructor is used for filter processing events.
     *
     * @param wrapper Wrapper managing this servlet instance
     * @param filter Filter instance for which this event occurred
     * @param type Event type (required)
     * @param request Servlet request we are processing
     * @param response Servlet response we are processing
     */
    public InstanceEvent(Wrapper wrapper, Filter filter, String type,
                         ServletRequest request, ServletResponse response) {

      super(wrapper);
      this.filter = filter;
      this.servlet = null;
      this.type = type;
      this.request = request;
      this.response = response;

    }


    /**
     * Construct a new InstanceEvent with the specified parameters.  This
     * constructor is used for filter processing events.
     *
     * @param wrapper Wrapper managing this servlet instance
     * @param filter Filter instance for which this event occurred
     * @param type Event type (required)
     * @param request Servlet request we are processing
     * @param response Servlet response we are processing
     * @param exception Exception that occurred
     */
    public InstanceEvent(Wrapper wrapper, Filter filter, String type,
                         ServletRequest request, ServletResponse response,
                         Throwable exception) {

      super(wrapper);
      this.filter = filter;
      this.servlet = null;
      this.type = type;
      this.request = request;
      this.response = response;
      this.exception = exception;

    }


    /**
     * Construct a new InstanceEvent with the specified parameters.  This
     * constructor is used for processing servlet lifecycle events.
     *
     * @param wrapper Wrapper managing this servlet instance
     * @param servlet Servlet instance for which this event occurred
     * @param type Event type (required)
     */
    public InstanceEvent(Wrapper wrapper, Servlet servlet, String type) {

      super(wrapper);
      this.filter = null;
      this.servlet = servlet;
      this.type = type;

    }


    /**
     * Construct a new InstanceEvent with the specified parameters.  This
     * constructor is used for processing servlet lifecycle events.
     *
     * @param wrapper Wrapper managing this servlet instance
     * @param servlet Servlet instance for which this event occurred
     * @param type Event type (required)
     * @param exception Exception that occurred
     */
    public InstanceEvent(Wrapper wrapper, Servlet servlet, String type,
                         Throwable exception) {

      super(wrapper);
      this.filter = null;
      this.servlet = servlet;
      this.type = type;
      this.exception = exception;

    }


    /**
     * Construct a new InstanceEvent with the specified parameters.  This
     * constructor is used for processing servlet processing events.
     *
     * @param wrapper Wrapper managing this servlet instance
     * @param servlet Servlet instance for which this event occurred
     * @param type Event type (required)
     * @param request Servlet request we are processing
     * @param response Servlet response we are processing
     */
    public InstanceEvent(Wrapper wrapper, Servlet servlet, String type,
                         ServletRequest request, ServletResponse response) {

      super(wrapper);
      this.filter = null;
      this.servlet = servlet;
      this.type = type;
      this.request = request;
      this.response = response;

    }


    /**
     * Construct a new InstanceEvent with the specified parameters.  This
     * constructor is used for processing servlet processing events.
     *
     * @param wrapper Wrapper managing this servlet instance
     * @param servlet Servlet instance for which this event occurred
     * @param type Event type (required)
     * @param request Servlet request we are processing
     * @param response Servlet response we are processing
     * @param exception Exception that occurred
     */
    public InstanceEvent(Wrapper wrapper, Servlet servlet, String type,
                         ServletRequest request, ServletResponse response,
                         Throwable exception) {

      super(wrapper);
      this.filter = null;
      this.servlet = servlet;
      this.type = type;
      this.request = request;
      this.response = response;
      this.exception = exception;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The exception that was thrown during the processing being reported
     * by this event (AFTER_INIT_EVENT, AFTER_SERVICE_EVENT,
     * AFTER_DESTROY_EVENT, AFTER_DISPATCH_EVENT, and AFTER_FILTER_EVENT only).
     */
    private Throwable exception = null;


    /**
     * The Filter instance for which this event occurred (BEFORE_FILTER_EVENT
     * and AFTER_FILTER_EVENT only).
     */
    private transient Filter filter = null;


    /**
     * The servlet request being processed (BEFORE_FILTER_EVENT,
     * AFTER_FILTER_EVENT, BEFORE_SERVICE_EVENT, and AFTER_SERVICE_EVENT).
     */
    private transient ServletRequest request = null;


    /**
     * The servlet response being processed (BEFORE_FILTER_EVENT,
     * AFTER_FILTER_EVENT, BEFORE_SERVICE_EVENT, and AFTER_SERVICE_EVENT).
     */
    private transient ServletResponse response = null;


    /**
     * The Servlet instance for which this event occurred (not present on
     * BEFORE_FILTER_EVENT or AFTER_FILTER_EVENT events).
     */
    private transient Servlet servlet = null;


    /**
     * The event type this instance represents.
     */
    private String type = null;


    // ------------------------------------------------------------- Properties


    /**
     * Return the exception that occurred during the processing
     * that was reported by this event.
     */
    public Throwable getException() {

        return (this.exception);

    }


    /**
     * Return the filter instance for which this event occurred.
     */
    public Filter getFilter() {

        return (this.filter);

    }


    /**
     * Return the servlet request for which this event occurred.
     */
    public ServletRequest getRequest() {

        return (this.request);

    }


    /**
     * Return the servlet response for which this event occurred.
     */
    public ServletResponse getResponse() {

        return (this.response);

    }


    /**
     * Return the servlet instance for which this event occurred.
     */
    public Servlet getServlet() {

        return (this.servlet);

    }


    /**
     * Return the event type of this event.
     */
    public String getType() {

        return (this.type);

    }


    /**
     * Return the Wrapper managing the servlet instance for which this
     * event occurred.
     */
    public Wrapper getWrapper() {

        return (Wrapper) getSource();

    }


}
