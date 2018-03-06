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
package javax.servlet;

import java.io.IOException;

/**
 * Defines an object that receives requests from the client and sends them to
 * any resource (such as a servlet, HTML file, or JSP file) on the server. The
 * servlet container creates the <code>RequestDispatcher</code> object, which is
 * used as a wrapper around a server resource located at a particular path or
 * given by a particular name.
 * 
 * <p>
 * This interface is intended to wrap servlets, but a servlet container can
 * create <code>RequestDispatcher</code> objects to wrap any type of resource.
 * 
 * @author Various
 * 
 * @see ServletContext#getRequestDispatcher(java.lang.String)
 * @see ServletContext#getNamedDispatcher(java.lang.String)
 * @see ServletRequest#getRequestDispatcher(java.lang.String)
 * 
 */
public interface RequestDispatcher {

    /**
     * The name of the request attribute that should be set by the container
     * when custom error-handling servlet or JSP page is invoked. The value of
     * the attribute is of type {@code java.lang.Throwable}. See the chapter
     * "Error Handling" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";

    /**
     * The name of the request attribute that should be set by the container
     * when custom error-handling servlet or JSP page is invoked. The value of
     * the attribute is of type {@code java.lang.Class}. See the chapter
     * "Error Handling" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";

    /**
     * The name of the request attribute that should be set by the container
     * when custom error-handling servlet or JSP page is invoked. The value of
     * the attribute is of type {@code java.lang.String}. See the chapter
     * "Error Handling" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";

    /**
     * The name of the request attribute that should be set by the container
     * when custom error-handling servlet or JSP page is invoked. The value of
     * the attribute is of type {@code java.lang.String}. See the chapter
     * "Error Handling" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";

    /**
     * The name of the request attribute that should be set by the container
     * when custom error-handling servlet or JSP page is invoked. The value of
     * the attribute is of type {@code java.lang.String}. See the chapter
     * "Error Handling" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";

    /**
     * The name of the request attribute that should be set by the container
     * when custom error-handling servlet or JSP page is invoked. The value of
     * the attribute is of type {@code java.lang.Integer}. See the chapter
     * "Error Handling" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    /**
     * The name of the request attribute that should be set by the container
     * when the {@link #forward(ServletRequest, ServletResponse)} method is
     * called. It provides the original value of a path-related property of the
     * request. See the chapter "Forwarded Request Parameters" in the Servlet
     * Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String FORWARD_CONTEXT_PATH = "javax.servlet.forward.context_path";

    /**
     * The name of the request attribute that should be set by the container
     * when the {@link #forward(ServletRequest, ServletResponse)} method is
     * called. It provides the original value of a path-related property of the
     * request. See the chapter "Forwarded Request Parameters" in the Servlet
     * Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String FORWARD_PATH_INFO = "javax.servlet.forward.path_info";

    /**
     * The name of the request attribute that should be set by the container
     * when the {@link #forward(ServletRequest, ServletResponse)} method is
     * called. It provides the original value of a path-related property of the
     * request. See the chapter "Forwarded Request Parameters" in the Servlet
     * Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String FORWARD_QUERY_STRING = "javax.servlet.forward.query_string";

    /**
     * The name of the request attribute that should be set by the container
     * when the {@link #forward(ServletRequest, ServletResponse)} method is
     * called. It provides the original value of a path-related property of the
     * request. See the chapter "Forwarded Request Parameters" in the Servlet
     * Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String FORWARD_REQUEST_URI = "javax.servlet.forward.request_uri";

    /**
     * The name of the request attribute that should be set by the container
     * when the {@link #forward(ServletRequest, ServletResponse)} method is
     * called. It provides the original value of a path-related property of the
     * request. See the chapter "Forwarded Request Parameters" in the Servlet
     * Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String FORWARD_SERVLET_PATH = "javax.servlet.forward.servlet_path";

    /**
     * The name of the request attribute that should be set by the container
     * when the {@link #include(ServletRequest, ServletResponse)} method is
     * called on the {@code RequestDispatcher} obtained by a path and not by a
     * name. It provides information on the path that was used to obtain the
     * {@code RequestDispatcher} instance for this include call. See the chapter
     * "Included Request Parameters" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String INCLUDE_CONTEXT_PATH = "javax.servlet.include.context_path";

    /**
     * The name of the request attribute that should be set by the container
     * when the {@link #include(ServletRequest, ServletResponse)} method is
     * called on the {@code RequestDispatcher} obtained by a path and not by a
     * name. It provides information on the path that was used to obtain the
     * {@code RequestDispatcher} instance for this include call. See the chapter
     * "Included Request Parameters" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String INCLUDE_PATH_INFO = "javax.servlet.include.path_info";

    /**
     * The name of the request attribute that should be set by the container
     * when the {@link #include(ServletRequest, ServletResponse)} method is
     * called on the {@code RequestDispatcher} obtained by a path and not by a
     * name. It provides information on the path that was used to obtain the
     * {@code RequestDispatcher} instance for this include call. See the chapter
     * "Included Request Parameters" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String INCLUDE_QUERY_STRING = "javax.servlet.include.query_string";

    /**
     * The name of the request attribute that should be set by the container
     * when the {@link #include(ServletRequest, ServletResponse)} method is
     * called on the {@code RequestDispatcher} obtained by a path and not by a
     * name. It provides information on the path that was used to obtain the
     * {@code RequestDispatcher} instance for this include call. See the chapter
     * "Included Request Parameters" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String INCLUDE_REQUEST_URI = "javax.servlet.include.request_uri";

    /**
     * The name of the request attribute that should be set by the container
     * when the {@link #include(ServletRequest, ServletResponse)} method is
     * called on the {@code RequestDispatcher} obtained by a path and not by a
     * name. It provides information on the path that was used to obtain the
     * {@code RequestDispatcher} instance for this include call. See the chapter
     * "Included Request Parameters" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String INCLUDE_SERVLET_PATH = "javax.servlet.include.servlet_path";

    /**
     * Forwards a request from a servlet to another resource (servlet, JSP file,
     * or HTML file) on the server. This method allows one servlet to do
     * preliminary processing of a request and another resource to generate the
     * response.
     * 
     * <p>
     * For a <code>RequestDispatcher</code> obtained via
     * <code>getRequestDispatcher()</code>, the <code>ServletRequest</code>
     * object has its path elements and parameters adjusted to match the path of
     * the target resource.
     * 
     * <p>
     * <code>forward</code> should be called before the response has been
     * committed to the client (before response body output has been flushed).
     * If the response already has been committed, this method throws an
     * <code>IllegalStateException</code>. Uncommitted output in the response
     * buffer is automatically cleared before the forward.
     * 
     * <p>
     * The request and response parameters must be either the same objects as
     * were passed to the calling servlet's service method or be subclasses of
     * the {@link ServletRequestWrapper} or {@link ServletResponseWrapper}
     * classes that wrap them.
     * 
     * 
     * @param request
     *            a {@link ServletRequest} object that represents the request
     *            the client makes of the servlet
     * 
     * @param response
     *            a {@link ServletResponse} object that represents the response
     *            the servlet returns to the client
     * 
     * @exception ServletException
     *                if the target resource throws this exception
     * 
     * @exception IOException
     *                if the target resource throws this exception
     * 
     * @exception IllegalStateException
     *                if the response was already committed
     */
    public void forward(ServletRequest request, ServletResponse response)
            throws ServletException, IOException;

    /**
     * Includes the content of a resource (servlet, JSP page, HTML file) in the
     * response. In essence, this method enables programmatic server-side
     * includes.
     * 
     * <p>
     * The {@link ServletResponse} object has its path elements and parameters
     * remain unchanged from the caller's. The included servlet cannot change
     * the response status code or set headers; any attempt to make a change is
     * ignored.
     * 
     * <p>
     * The request and response parameters must be either the same objects as
     * were passed to the calling servlet's service method or be subclasses of
     * the {@link ServletRequestWrapper} or {@link ServletResponseWrapper}
     * classes that wrap them.
     * 
     * @param request
     *            a {@link ServletRequest} object that contains the client's
     *            request
     * 
     * @param response
     *            a {@link ServletResponse} object that contains the servlet's
     *            response
     * 
     * @exception ServletException
     *                if the included resource throws this exception
     * 
     * @exception IOException
     *                if the included resource throws this exception
     */
    public void include(ServletRequest request, ServletResponse response)
            throws ServletException, IOException;
}
