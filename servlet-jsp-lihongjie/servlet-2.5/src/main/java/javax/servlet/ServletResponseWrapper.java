
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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * 
 * Provides a convenient implementation of the ServletResponse interface that
 * can be subclassed by developers wishing to adapt the response from a Servlet.
 * This class implements the Wrapper or Decorator pattern. Methods default to
 * calling through to the wrapped response object.
 * 
 * @author Various
 * @since v 2.3
 * 
 * @see javax.servlet.ServletResponse
 * 
 */

public class ServletResponseWrapper implements ServletResponse {
	private ServletResponse response;

	/**
	 * Creates a ServletResponse adaptor wrapping the given response object.
	 * 
	 * @throws java.lang.IllegalArgumentException
	 *             if the response is null.
	 */

	public ServletResponseWrapper(ServletResponse response) {
		if (response == null) {
			throw new IllegalArgumentException("Response cannot be null");
		}
		this.response = response;
	}

	/**
	 * Return the wrapped ServletResponse object.
	 */

	public ServletResponse getResponse() {
		return this.response;
	}

	/**
	 * Sets the response being wrapped.
	 * 
	 * @throws java.lang.IllegalArgumentException
	 *             if the response is null.
	 */

	public void setResponse(ServletResponse response) {
		if (response == null) {
			throw new IllegalArgumentException("Response cannot be null");
		}
		this.response = response;
	}

	/**
	 * The default behavior of this method is to call
	 * setCharacterEncoding(String charset) on the wrapped response object.
	 * 
	 * @since 2.4
	 */

	public void setCharacterEncoding(String charset) {
		this.response.setCharacterEncoding(charset);
	}

	/**
	 * The default behavior of this method is to return getCharacterEncoding()
	 * on the wrapped response object.
	 */

	public String getCharacterEncoding() {
		return this.response.getCharacterEncoding();
	}

	/**
	 * The default behavior of this method is to return getOutputStream() on the
	 * wrapped response object.
	 */

	public ServletOutputStream getOutputStream() throws IOException {
		return this.response.getOutputStream();
	}

	/**
	 * The default behavior of this method is to return getWriter() on the
	 * wrapped response object.
	 */

	public PrintWriter getWriter() throws IOException {
		return this.response.getWriter();
	}

	/**
	 * The default behavior of this method is to call setContentLength(int len)
	 * on the wrapped response object.
	 */

	public void setContentLength(int len) {
		this.response.setContentLength(len);
	}

	/**
	 * The default behavior of this method is to call setContentType(String
	 * type) on the wrapped response object.
	 */

	public void setContentType(String type) {
		this.response.setContentType(type);
	}

	/**
	 * The default behavior of this method is to return getContentType() on the
	 * wrapped response object.
	 * 
	 * @since 2.4
	 */

	public String getContentType() {
		return this.response.getContentType();
	}

	/**
	 * The default behavior of this method is to call setBufferSize(int size) on
	 * the wrapped response object.
	 */
	public void setBufferSize(int size) {
		this.response.setBufferSize(size);
	}

	/**
	 * The default behavior of this method is to return getBufferSize() on the
	 * wrapped response object.
	 */
	public int getBufferSize() {
		return this.response.getBufferSize();
	}

	/**
	 * The default behavior of this method is to call flushBuffer() on the
	 * wrapped response object.
	 */

	public void flushBuffer() throws IOException {
		this.response.flushBuffer();
	}

	/**
	 * The default behavior of this method is to return isCommitted() on the
	 * wrapped response object.
	 */
	public boolean isCommitted() {
		return this.response.isCommitted();
	}

	/**
	 * The default behavior of this method is to call reset() on the wrapped
	 * response object.
	 */

	public void reset() {
		this.response.reset();
	}

	/**
	 * The default behavior of this method is to call resetBuffer() on the
	 * wrapped response object.
	 */

	public void resetBuffer() {
		this.response.resetBuffer();
	}

	/**
	 * The default behavior of this method is to call setLocale(Locale loc) on
	 * the wrapped response object.
	 */

	public void setLocale(Locale loc) {
		this.response.setLocale(loc);
	}

	/**
	 * The default behavior of this method is to return getLocale() on the
	 * wrapped response object.
	 */
	public Locale getLocale() {
		return this.response.getLocale();
	}

}
