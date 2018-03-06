

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
    * A FilterChain is an object provided by the servlet container to the developer
    * giving a view into the invocation chain of a filtered request for a resource. Filters
    * use the FilterChain to invoke the next filter in the chain, or if the calling filter
    * is the last filter in the chain, to invoke the resource at the end of the chain.
    *
    * @see Filter
    * @since Servlet 2.3
    **/

public interface FilterChain {
	
	/**
	* Causes the next filter in the chain to be invoked, or if the calling filter is the last filter
	* in the chain, causes the resource at the end of the chain to be invoked.
	*
	* @param request the request to pass along the chain.
	* @param response the response to pass along the chain.
	*
	* @since 2.3
	*/
	
    public void doFilter ( ServletRequest request, ServletResponse response ) throws IOException, ServletException;

}

