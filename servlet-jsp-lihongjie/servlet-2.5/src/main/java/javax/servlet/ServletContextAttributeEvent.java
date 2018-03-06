

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
	* This is the event class for notifications about changes to the attributes of the
	*  servlet context of a web application.
	* @see ServletContextAttributeListener
	 * @since	v 2.3
	*/

public class ServletContextAttributeEvent extends ServletContextEvent { 
	private String name;
	private Object value;

	/** Construct a ServletContextAttributeEvent from the given context for the
	** given attribute name and attribute value. 
	*/
	public ServletContextAttributeEvent(ServletContext source, String name, Object value) {
	    super(source);
	    this.name = name;
	    this.value = value;
	}
	
	/**
	* Return the name of the attribute that changed on the ServletContext.
	*
	*/
	public String getName() {
		return this.name;
	}
	
	/**
	* Returns the value of the attribute that has been added, removed, or replaced.
	* If the attribute was added, this is the value of the attribute. If the attribute was
	* removed, this is the value of the removed attribute. If the attribute was replaced, this
	* is the old value of the attribute.
	*
	*/
	
	public Object getValue() {
	    return this.value;   
	}
}

