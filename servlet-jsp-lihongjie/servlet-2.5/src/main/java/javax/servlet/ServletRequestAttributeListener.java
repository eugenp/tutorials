

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
     * A ServletRequestAttributeListener can be implemented by the
     * developer interested in being notified of request attribute
     * changes. Notifications will be generated while the request
     * is within the scope of the web application in which the listener
     * is registered. A request is defined as coming into scope when
     * it is about to enter the first servlet or filter in each web
     * application, as going out of scope when it exits the last servlet
     * or the first filter in the chain.
     *
     * @since Servlet 2.4
     */

public interface ServletRequestAttributeListener extends EventListener {
    /** Notification that a new attribute was added to the
     ** servlet request. Called after the attribute is added.
     */
    public void attributeAdded(ServletRequestAttributeEvent srae);

    /** Notification that an existing attribute has been removed from the
     ** servlet request. Called after the attribute is removed.
     */
    public void attributeRemoved(ServletRequestAttributeEvent srae);

    /** Notification that an attribute was replaced on the
     ** servlet request. Called after the attribute is replaced.
     */
    public void attributeReplaced(ServletRequestAttributeEvent srae);
}

