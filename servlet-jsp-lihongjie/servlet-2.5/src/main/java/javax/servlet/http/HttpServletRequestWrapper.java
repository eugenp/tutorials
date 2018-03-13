

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

package javax.servlet.http;

import javax.servlet.ServletRequestWrapper;
import java.util.Enumeration;

/**
 * 
 * Provides a convenient implementation of the HttpServletRequest interface that
 * can be subclassed by developers wishing to adapt the request to a Servlet.
 * This class implements the Wrapper or Decorator pattern. Methods default to
 * calling through to the wrapped request object.
 * 
 *
 * @see 	javax.servlet.http.HttpServletRequest
  * @since	v 2.3
 *
 */


public class HttpServletRequestWrapper extends ServletRequestWrapper implements HttpServletRequest {

	/** 
	* Constructs a request object wrapping the given request.
	* @throws java.lang.IllegalArgumentException if the request is null
	*/
    public HttpServletRequestWrapper(HttpServletRequest request) {
	    super(request);
    }
    
    private HttpServletRequest _getHttpServletRequest() {
	return (HttpServletRequest) super.getRequest();
    }

    /**
     * The default behavior of this method is to return getAuthType()
     * on the wrapped request object.
     */

    public String getAuthType() {
	return this._getHttpServletRequest().getAuthType();
    }
   
    /**
     * The default behavior of this method is to return getCookies()
     * on the wrapped request object.
     */
    public Cookie[] getCookies() {
	return this._getHttpServletRequest().getCookies();
    }

    /**
     * The default behavior of this method is to return getDateHeader(String name)
     * on the wrapped request object.
     */
    public long getDateHeader(String name) {
	return this._getHttpServletRequest().getDateHeader(name);
    }
        	
    /**
     * The default behavior of this method is to return getHeader(String name)
     * on the wrapped request object.
     */
    public String getHeader(String name) {
	return this._getHttpServletRequest().getHeader(name);
    }
    
    /**
     * The default behavior of this method is to return getHeaders(String name)
     * on the wrapped request object.
     */
    public Enumeration getHeaders(String name) {
	return this._getHttpServletRequest().getHeaders(name);
    }  

    /**
     * The default behavior of this method is to return getHeaderNames()
     * on the wrapped request object.
     */
  
    public Enumeration getHeaderNames() {
	return this._getHttpServletRequest().getHeaderNames();
    }
    
    /**
     * The default behavior of this method is to return getIntHeader(String name)
     * on the wrapped request object.
     */

     public int getIntHeader(String name) {
	return this._getHttpServletRequest().getIntHeader(name);
    }
    
    /**
     * The default behavior of this method is to return getMethod()
     * on the wrapped request object.
     */
    public String getMethod() {
	return this._getHttpServletRequest().getMethod();
    }
    
    /**
     * The default behavior of this method is to return getPathInfo()
     * on the wrapped request object.
     */
    public String getPathInfo() {
	return this._getHttpServletRequest().getPathInfo();
    }

    /**
     * The default behavior of this method is to return getPathTranslated()
     * on the wrapped request object.
     */

     public String getPathTranslated() {
	return this._getHttpServletRequest().getPathTranslated();
    }

    /**
     * The default behavior of this method is to return getContextPath()
     * on the wrapped request object.
     */
    public String getContextPath() {
	return this._getHttpServletRequest().getContextPath();
    }
    
    /**
     * The default behavior of this method is to return getQueryString()
     * on the wrapped request object.
     */
    public String getQueryString() {
	return this._getHttpServletRequest().getQueryString();
    }
    
    /**
     * The default behavior of this method is to return getRemoteUser()
     * on the wrapped request object.
     */
    public String getRemoteUser() {
	return this._getHttpServletRequest().getRemoteUser();
    }
    
 
    /**
     * The default behavior of this method is to return isUserInRole(String role)
     * on the wrapped request object.
     */
    public boolean isUserInRole(String role) {
	return this._getHttpServletRequest().isUserInRole(role);
    }
    
    
    
    /**
     * The default behavior of this method is to return getUserPrincipal()
     * on the wrapped request object.
     */
    public java.security.Principal getUserPrincipal() {
	return this._getHttpServletRequest().getUserPrincipal();
    }
    
   
    /**
     * The default behavior of this method is to return getRequestedSessionId()
     * on the wrapped request object.
     */
    public String getRequestedSessionId() {
	return this._getHttpServletRequest().getRequestedSessionId();
    }
    
    /**
     * The default behavior of this method is to return getRequestURI()
     * on the wrapped request object.
     */
    public String getRequestURI() {
	return this._getHttpServletRequest().getRequestURI();
    }
	/**
     * The default behavior of this method is to return getRequestURL()
     * on the wrapped request object.
     */
    public StringBuffer getRequestURL() {
	return this._getHttpServletRequest().getRequestURL();
    }
	
    
    /**
     * The default behavior of this method is to return getServletPath()
     * on the wrapped request object.
     */
    public String getServletPath() {
	return this._getHttpServletRequest().getServletPath();
    }
    
    
    /**
     * The default behavior of this method is to return getSession(boolean create)
     * on the wrapped request object.
     */
    public HttpSession getSession(boolean create) {
	return this._getHttpServletRequest().getSession(create);
    }
    
    /**
     * The default behavior of this method is to return getSession()
     * on the wrapped request object.
     */
    public HttpSession getSession() {
	return this._getHttpServletRequest().getSession();
    }
    
    /**
     * The default behavior of this method is to return isRequestedSessionIdValid()
     * on the wrapped request object.
     */ 

    public boolean isRequestedSessionIdValid() {
	return this._getHttpServletRequest().isRequestedSessionIdValid();
    }
     
    
    /**
     * The default behavior of this method is to return isRequestedSessionIdFromCookie()
     * on the wrapped request object.
     */
    public boolean isRequestedSessionIdFromCookie() {
	return this._getHttpServletRequest().isRequestedSessionIdFromCookie();
    }
    
    	  /**
     * The default behavior of this method is to return isRequestedSessionIdFromURL()
     * on the wrapped request object.
     */ 
    public boolean isRequestedSessionIdFromURL() {
	return this._getHttpServletRequest().isRequestedSessionIdFromURL();
    }
    
    /**
     * The default behavior of this method is to return isRequestedSessionIdFromUrl()
     * on the wrapped request object.
     */
    public boolean isRequestedSessionIdFromUrl() {
	return this._getHttpServletRequest().isRequestedSessionIdFromUrl();
    }


    
}
