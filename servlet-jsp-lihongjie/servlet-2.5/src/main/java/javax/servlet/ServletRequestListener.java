

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
     * A ServletRequestListener can be implemented by the developer
     * interested in being notified of requests coming in and out of
     * scope in a web component. A request is defined as coming into
     * scope when it is about to enter the first servlet or filter
     * in each web application, as going out of scope when it exits
     * the last servlet or the first filter in the chain.
     *
     * @since Servlet 2.4
     */


public interface ServletRequestListener extends EventListener {

    /** The request is about to go out of scope of the web application. */
    public void requestDestroyed ( ServletRequestEvent sre );

    /** The request is about to come into scope of the web application. */
    public void requestInitialized ( ServletRequestEvent sre );
}
