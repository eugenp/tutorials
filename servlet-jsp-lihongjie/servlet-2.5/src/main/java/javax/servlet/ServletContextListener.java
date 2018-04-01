

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

import java.util.EventListener;

	/** 
	 * Implementations of this interface receive notifications about
	 * changes to the servlet context of the web application they are
	 * part of.
	 * To receive notification events, the implementation class
	 * must be configured in the deployment descriptor for the web
	 * application.
	 * @see ServletContextEvent
	 * @since	v 2.3
	 */

public interface ServletContextListener extends EventListener {
	/**
	 ** Notification that the web application initialization
	 ** process is starting.
	 ** All ServletContextListeners are notified of context
	 ** initialization before any filter or servlet in the web
	 ** application is initialized.
	 */

    public void contextInitialized ( ServletContextEvent sce );

	/**
	 ** Notification that the servlet context is about to be shut down.
	 ** All servlets and filters have been destroy()ed before any
	 ** ServletContextListeners are notified of context
	 ** destruction.
	 */
    public void contextDestroyed ( ServletContextEvent sce );
}

