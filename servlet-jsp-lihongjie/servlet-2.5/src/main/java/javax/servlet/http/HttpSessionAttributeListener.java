

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

	/** This listener interface can be implemented in order to
	* get notifications of changes to the attribute lists of sessions within
	* this web application.
	* @since	v 2.3
*/

public interface HttpSessionAttributeListener extends EventListener {
	/** Notification that an attribute has been added to a session. Called after the attribute is added.*/
    public void attributeAdded ( HttpSessionBindingEvent se );
	/** Notification that an attribute has been removed from a session. Called after the attribute is removed. */
    public void attributeRemoved ( HttpSessionBindingEvent se );
	/** Notification that an attribute has been replaced in a session. Called after the attribute is replaced. */
    public void attributeReplaced ( HttpSessionBindingEvent se );

}

