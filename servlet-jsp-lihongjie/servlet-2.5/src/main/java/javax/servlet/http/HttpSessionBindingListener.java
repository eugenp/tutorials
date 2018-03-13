

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
 * Causes an object to be notified when it is bound to
 * or unbound from a session. The object is notified
 * by an {@link HttpSessionBindingEvent} object. This may be as a result
 * of a servlet programmer explicitly unbinding an attribute from a session,
 * due to a session being invalidated, or due to a session timing out.
 *
 *
 * @author		Various
 *
 * @see HttpSession
 * @see HttpSessionBindingEvent
 *
 */

public interface HttpSessionBindingListener extends EventListener {



    /**
     *
     * Notifies the object that it is being bound to
     * a session and identifies the session.
     *
     * @param event		the event that identifies the
     *				session 
     *
     * @see #valueUnbound
     *
     */ 

    public void valueBound(HttpSessionBindingEvent event);
    
    

    /**
     *
     * Notifies the object that it is being unbound
     * from a session and identifies the session.
     *
     * @param event		the event that identifies
     *				the session 
     *	
     * @see #valueBound
     *
     */

    public void valueUnbound(HttpSessionBindingEvent event);
    
    
}

