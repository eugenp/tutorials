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
 */

package javax.servlet;

import java.util.EventListener;

	/** Implementations of this interface receive notifications of
	** changes to the attribute list on the servlet context of a web application. 
	* To receive notification events, the implementation class
	* must be configured in the deployment descriptor for the web application.
	* @see ServletContextAttributeEvent
	 * @since	v 2.3
	*/

public interface ServletContextAttributeListener extends EventListener {
	/** Notification that a new attribute was added to the servlet context. Called after the attribute is added.*/
public void attributeAdded(ServletContextAttributeEvent scab);
	/** Notification that an existing attribute has been removed from the servlet context. Called after the attribute is removed.*/
public void attributeRemoved(ServletContextAttributeEvent scab);
	/** Notification that an attribute on the servlet context has been replaced. Called after the attribute is replaced. */
public void attributeReplaced(ServletContextAttributeEvent scab);
}

