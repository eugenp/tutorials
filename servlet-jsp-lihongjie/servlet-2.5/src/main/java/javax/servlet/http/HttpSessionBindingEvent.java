

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



/**
 *
 * Events of this type are either sent to an object that implements
 * {@link HttpSessionBindingListener} when it is bound or 
 * unbound from a session, or to a {@link HttpSessionAttributeListener} 
 * that has been configured in the deployment descriptor when any attribute is
 * bound, unbound or replaced in a session.
 *
 * <p>The session binds the object by a call to
 * <code>HttpSession.setAttribute</code> and unbinds the object
 * by a call to <code>HttpSession.removeAttribute</code>.
 *
 *
 *
 * @author		Various
 * 
 * @see 		HttpSession
 * @see 		HttpSessionBindingListener
 * @see			HttpSessionAttributeListener
 */

public class HttpSessionBindingEvent extends HttpSessionEvent {




    /* The name to which the object is being bound or unbound */

    private String name;
    
    /* The object is being bound or unbound */

    private Object value;
    
  

    /**
     *
     * Constructs an event that notifies an object that it
     * has been bound to or unbound from a session. 
     * To receive the event, the object must implement
     * {@link HttpSessionBindingListener}.
     *
     *
     *
     * @param session 	the session to which the object is bound or unbound
     *
     * @param name 	the name with which the object is bound or unbound
     *
     * @see			#getName
     * @see			#getSession
     *
     */

    public HttpSessionBindingEvent(HttpSession session, String name) {
	super(session);
	this.name = name;
    }
    
    /**
     *
     * Constructs an event that notifies an object that it
     * has been bound to or unbound from a session. 
     * To receive the event, the object must implement
     * {@link HttpSessionBindingListener}.
     *
     *
     *
     * @param session 	the session to which the object is bound or unbound
     *
     * @param name 	the name with which the object is bound or unbound
     *
     * @see			#getName
     * @see			#getSession
     *
     */
    
    public HttpSessionBindingEvent(HttpSession session, String name, Object value) {
	super(session);
	this.name = name;
	this.value = value;
    }
    
    
   	/** Return the session that changed. */
    public HttpSession getSession () { 
	return super.getSession();
    }
 
   
  
    
    /**
     *
     * Returns the name with which the attribute is bound to or
     * unbound from the session.
     *
     *
     * @return		a string specifying the name with which
     *			the object is bound to or unbound from
     *			the session
     *
     *
     */

    public String getName() {
	return name;
    }
    
    /**
	* Returns the value of the attribute that has been added, removed or replaced.
	* If the attribute was added (or bound), this is the value of the attribute. If the attribute was
	* removed (or unbound), this is the value of the removed attribute. If the attribute was replaced, this
	* is the old value of the attribute.
	*
        * @since 2.3
	*/
	
	public Object getValue() {
	    return this.value;   
	}
    
}







