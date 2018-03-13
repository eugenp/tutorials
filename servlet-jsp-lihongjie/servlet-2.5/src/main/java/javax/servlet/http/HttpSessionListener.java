

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

import java.util.EventListener;

	/** 
	* Implementations of this interface are notified of changes to the 
	* list of active sessions in a web application.
	* To receive notification events, the implementation class
	* must be configured in the deployment descriptor for the web application.
	* @see HttpSessionEvent
	 * @since	v 2.3
	*/

public interface HttpSessionListener extends EventListener {
    
	/** 
	* Notification that a session was created.
	* @param se the notification event
	*/
    public void sessionCreated ( HttpSessionEvent se );
    
	/** 
	* Notification that a session is about to be invalidated.
	* @param se the notification event
	*/
    public void sessionDestroyed ( HttpSessionEvent se );
    
}

