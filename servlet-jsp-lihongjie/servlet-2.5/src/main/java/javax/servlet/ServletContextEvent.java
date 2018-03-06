

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


	/** 
	 * This is the event class for notifications about changes to
	 * the servlet context of a web application.
	 * @see ServletContextListener
	 * @since	v 2.3
	 */

public class ServletContextEvent extends java.util.EventObject { 

	/** Construct a ServletContextEvent from the given context.
	 *
	 * @param source - the ServletContext that is sending the event.
	 */
    public ServletContextEvent(ServletContext source) {
	super(source);
    }
    
	/**
	 * Return the ServletContext that changed.
	 *
	 * @return the ServletContext that sent the event.
	 */
    public ServletContext getServletContext () { 
	return (ServletContext) super.getSource();
    }
    
}

