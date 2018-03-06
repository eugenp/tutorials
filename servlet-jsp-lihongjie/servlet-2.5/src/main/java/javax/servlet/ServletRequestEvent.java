

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
      * Events of this kind indicate lifecycle
      * events for a ServletRequest.
      * The source of the event
      * is the ServletContext of this web application.
      * @see ServletRequestListener
      * @since	Servlet 2.4
      */

public class ServletRequestEvent extends java.util.EventObject { 
    private ServletRequest request;

    /** Construct a ServletRequestEvent for the given ServletContext
      * and ServletRequest.
      *
      * @param sc		the ServletContext of the web application.
      * @param request		the ServletRequest that is sending the event.
      */
    public ServletRequestEvent(ServletContext sc, ServletRequest request) {
        super(sc);
        this.request = request;
    }
    
    /**
      * Returns the ServletRequest that is changing.
      */
    public ServletRequest getServletRequest () { 
        return this.request;
    }

    /**
      * Returns the ServletContext of this web application.
      */
    public ServletContext getServletContext () { 
        return (ServletContext) super.getSource();
    }
}
