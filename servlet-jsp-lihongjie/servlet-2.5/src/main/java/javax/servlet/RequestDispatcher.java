

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * glassfish/bootstrap/legal/CDDLv1.0.txt or
 * https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * glassfish/bootstrap/legal/CDDLv1.0.txt.  If applicable,
 * add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your
 * own identifying information: Portions Copyright [yyyy]
 * [name of copyright owner]
 *
 * Copyright 2005 Sun Microsystems, Inc. All rights reserved.
 *
 * Portions Copyright Apache Software Foundation.
 */ 


package javax.servlet;

import java.io.IOException;


/**
 * Defines an object that receives requests from the client
 * and sends them to any resource (such as a servlet, 
 * HTML file, or JSP file) on the server. The servlet
 * container creates the <code>RequestDispatcher</code> object,
 * which is used as a wrapper around a server resource located
 * at a particular path or given by a particular name.
 *
 * <p>This interface is intended to wrap servlets,
 * but a servlet container can create <code>RequestDispatcher</code>
 * objects to wrap any type of resource.
 *
 * @author 	Various
 *
 * @see 	ServletContext#getRequestDispatcher(java.lang.String)
 * @see 	ServletContext#getNamedDispatcher(java.lang.String)
 * @see 	ServletRequest#getRequestDispatcher(java.lang.String)
 *
 */
 
public interface RequestDispatcher {

/**
 * Forwards a request from
 * a servlet to another resource (servlet, JSP file, or
 * HTML file) on the server. This method allows
 * one servlet to do preliminary processing of
 * a request and another resource to generate
 * the response.
 *
 * <p>For a <code>RequestDispatcher</code> obtained via 
 * <code>getRequestDispatcher()</code>, the <code>ServletRequest</code> 
 * object has its path elements and parameters adjusted to match
 * the path of the target resource.
 *
 * <p><code>forward</code> should be called before the response has been 
 * committed to the client (before response body output has been flushed).  
 * If the response already has been committed, this method throws
 * an <code>IllegalStateException</code>.
 * Uncommitted output in the response buffer is automatically cleared 
 * before the forward.
 *
 * <p>The request and response parameters must be either the same
 * objects as were passed to the calling servlet's service method or be
 * subclasses of the {@link ServletRequestWrapper} or {@link ServletResponseWrapper} classes
 * that wrap them.
 *
 *
 * @param request		a {@link ServletRequest} object
 *				that represents the request the client
 * 				makes of the servlet
 *
 * @param response		a {@link ServletResponse} object
 *				that represents the response the servlet
 *				returns to the client
 *
 * @exception ServletException	if the target resource throws this exception
 *
 * @exception IOException	if the target resource throws this exception
 *
 * @exception IllegalStateException	if the response was already committed
 *
 */

	public void forward(ServletRequest request, ServletResponse response)
			throws ServletException, IOException;




    /**
     *
     * Includes the content of a resource (servlet, JSP page,
     * HTML file) in the response. In essence, this method enables 
     * programmatic server-side includes.
     *
     * <p>The {@link ServletResponse} object has its path elements
     * and parameters remain unchanged from the caller's. The included
     * servlet cannot change the response status code or set headers;
     * any attempt to make a change is ignored.
     *
     * <p>The request and response parameters must be either the same
     * objects as were passed to the calling servlet's service method or be
     * subclasses of the {@link ServletRequestWrapper} or {@link ServletResponseWrapper} classes
     * that wrap them.
     * 
     *
     *
     * @param request 			a {@link ServletRequest} object 
     *					that contains the client's request
     *
     * @param response 			a {@link ServletResponse} object 
     * 					that contains the servlet's response
     *
     * @exception ServletException 	if the included resource throws this exception
     *
     * @exception IOException 		if the included resource throws this exception
     *
     *
     */
     
	public void include(ServletRequest request, ServletResponse response)
			throws ServletException, IOException;
}








