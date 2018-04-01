

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

import java.util.Enumeration;

/**
 *
 * @author		Various
 *
 * @deprecated		As of Java(tm) Servlet API 2.1
 *			for security reasons, with no replacement.
 *			This interface will be removed in a future
 *			version of this API.
 *
 * @see			HttpSession
 * @see			HttpSessionBindingEvent
 * @see			HttpSessionBindingListener
 *
 */


public interface HttpSessionContext {

    /**
     *
     * @deprecated 	As of Java Servlet API 2.1 with
     *			no replacement. This method must 
     *			return null and will be removed in
     *			a future version of this API.
     *
     */

    public HttpSession getSession(String sessionId);
    
    
    
  
    /**
     *
     * @deprecated	As of Java Servlet API 2.1 with
     *			no replacement. This method must return 
     *			an empty <code>Enumeration</code> and will be removed
     *			in a future version of this API.
     *
     */

    public Enumeration getIds();
}





