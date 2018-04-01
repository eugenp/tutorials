
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
 * Defines a general exception a servlet can throw when it encounters
 * difficulty.
 * 
 * @author Various
 */

public class ServletException extends Exception {

	private Throwable rootCause;

	/**
	 * Constructs a new servlet exception.
	 * 
	 */

	public ServletException() {
		super();
	}

	/**
	 * Constructs a new servlet exception with the specified message. The
	 * message can be written to the server log and/or displayed for the user.
	 * 
	 * @param message
	 *            a <code>String</code> specifying the text of the exception
	 *            message
	 * 
	 */

	public ServletException(String message) {
		super(message);
	}

	/**
	 * Constructs a new servlet exception when the servlet needs to throw an
	 * exception and include a message about the "root cause" exception that
	 * interfered with its normal operation, including a description message.
	 * 
	 * 
	 * @param message
	 *            a <code>String</code> containing the text of the exception
	 *            message
	 * 
	 * @param rootCause
	 *            the <code>Throwable</code> exception that interfered with the
	 *            servlet's normal operation, making this servlet exception
	 *            necessary
	 * 
	 */

	public ServletException(String message, Throwable rootCause) {
		super(message, rootCause);
		this.rootCause = rootCause;
	}

	/**
	 * Constructs a new servlet exception when the servlet needs to throw an
	 * exception and include a message about the "root cause" exception that
	 * interfered with its normal operation. The exception's message is based on
	 * the localized message of the underlying exception.
	 * 
	 * <p>
	 * This method calls the <code>getLocalizedMessage</code> method on the
	 * <code>Throwable</code> exception to get a localized exception message.
	 * When subclassing <code>ServletException</code>, this method can be
	 * overridden to create an exception message designed for a specific locale.
	 * 
	 * @param rootCause
	 *            the <code>Throwable</code> exception that interfered with the
	 *            servlet's normal operation, making the servlet exception
	 *            necessary
	 * 
	 */

	public ServletException(Throwable rootCause) {
		super(rootCause);
		this.rootCause = rootCause;
	}

	/**
	 * Returns the exception that caused this servlet exception.
	 * 
	 * 
	 * @return the <code>Throwable</code> that caused this servlet exception
	 * 
	 */

	public Throwable getRootCause() {
		return rootCause;
	}
}
