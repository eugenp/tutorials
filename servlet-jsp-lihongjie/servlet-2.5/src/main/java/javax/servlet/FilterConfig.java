

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


import java.util.Enumeration;

	 /** 
	 *
	 * A filter configuration object used by a servlet container
	 * to pass information to a filter during initialization.
	 * @see Filter 
	  * @since	Servlet 2.3
	 *
	 */


public interface FilterConfig {

	/** 
	* Returns the filter-name of this filter as defined in the deployment descriptor. 
	*/
	
	public String getFilterName();


 /**
     * Returns a reference to the {@link ServletContext} in which the caller
     * is executing.
     *
     *
     * @return		a {@link ServletContext} object, used
     *			by the caller to interact with its servlet 
     *                  container
     * 
     * @see		ServletContext
     *
     */

    public ServletContext getServletContext();
    
    /**
     * Returns a <code>String</code> containing the value of the 
     * named initialization parameter, or <code>null</code> if 
     * the parameter does not exist.
     *
     * @param name	a <code>String</code> specifying the name
     *			of the initialization parameter
     *
     * @return		a <code>String</code> containing the value 
     *			of the initialization parameter
     *
     */

    public String getInitParameter(String name);


    /**
     * Returns the names of the filter's initialization parameters
     * as an <code>Enumeration</code> of <code>String</code> objects, 
     * or an empty <code>Enumeration</code> if the filter has
     * no initialization parameters.
     *
     * @return		an <code>Enumeration</code> of <code>String</code> 
     *			objects containing the names of the filter's 
     *			initialization parameters
     *
     *
     *
     */

    public Enumeration getInitParameterNames();




}
