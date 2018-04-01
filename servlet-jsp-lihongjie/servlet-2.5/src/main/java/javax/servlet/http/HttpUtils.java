
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

import javax.servlet.ServletInputStream;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.io.IOException;

/**
 * @deprecated As of Java(tm) Servlet API 2.3. These methods were only useful
 *             with the default encoding and have been moved to the request
 *             interfaces.
 * 
 */

public class HttpUtils {

	private static final String LSTRING_FILE = "javax.servlet.http.LocalStrings";
	private static ResourceBundle lStrings = ResourceBundle
			.getBundle(LSTRING_FILE);

	/**
	 * Constructs an empty <code>HttpUtils</code> object.
	 * 
	 */

	public HttpUtils() {
	}

	/**
	 * 
	 * Parses a query string passed from the client to the server and builds a
	 * <code>HashTable</code> object with key-value pairs. The query string
	 * should be in the form of a string packaged by the GET or POST method,
	 * that is, it should have key-value pairs in the form <i>key=value</i>,
	 * with each pair separated from the next by a &amp; character.
	 * 
	 * <p>
	 * A key can appear more than once in the query string with different
	 * values. However, the key appears only once in the hashtable, with its
	 * value being an array of strings containing the multiple values sent by
	 * the query string.
	 * 
	 * <p>
	 * The keys and values in the hashtable are stored in their decoded form, so
	 * any + characters are converted to spaces, and characters sent in
	 * hexadecimal notation (like <i>%xx</i>) are converted to ASCII characters.
	 * 
	 * @param s
	 *            a string containing the query to be parsed
	 * 
	 * @return a <code>HashTable</code> object built from the parsed key-value
	 *         pairs
	 * 
	 * @exception IllegalArgumentException
	 *                if the query string is invalid
	 * 
	 */

	static public Hashtable parseQueryString(String s) {

		String valArray[] = null;

		if (s == null) {
			throw new IllegalArgumentException();
		}
		Hashtable ht = new Hashtable();
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(s, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				// XXX
				// should give more detail about the illegal argument
				throw new IllegalArgumentException();
			}
			String key = parseName(pair.substring(0, pos), sb);
			String val = parseName(pair.substring(pos + 1, pair.length()), sb);
			if (ht.containsKey(key)) {
				String oldVals[] = (String[]) ht.get(key);
				valArray = new String[oldVals.length + 1];
				for (int i = 0; i < oldVals.length; i++)
					valArray[i] = oldVals[i];
				valArray[oldVals.length] = val;
			} else {
				valArray = new String[1];
				valArray[0] = val;
			}
			ht.put(key, valArray);
		}
		return ht;
	}

	/**
	 * 
	 * Parses data from an HTML form that the client sends to the server using
	 * the HTTP POST method and the <i>application/x-www-form-urlencoded</i>
	 * MIME type.
	 * 
	 * <p>
	 * The data sent by the POST method contains key-value pairs. A key can
	 * appear more than once in the POST data with different values. However,
	 * the key appears only once in the hashtable, with its value being an array
	 * of strings containing the multiple values sent by the POST method.
	 * 
	 * <p>
	 * The keys and values in the hashtable are stored in their decoded form, so
	 * any + characters are converted to spaces, and characters sent in
	 * hexadecimal notation (like <i>%xx</i>) are converted to ASCII characters.
	 * 
	 * 
	 * 
	 * @param len
	 *            an integer specifying the length, in characters, of the
	 *            <code>ServletInputStream</code> object that is also passed to
	 *            this method
	 * 
	 * @param in
	 *            the <code>ServletInputStream</code> object that contains the
	 *            data sent from the client
	 * 
	 * @return a <code>HashTable</code> object built from the parsed key-value
	 *         pairs
	 * 
	 * 
	 * @exception IllegalArgumentException
	 *                if the data sent by the POST method is invalid
	 * 
	 */

	static public Hashtable parsePostData(int len, ServletInputStream in) {
		// XXX
		// should a length of 0 be an IllegalArgumentException

		if (len <= 0)
			return new Hashtable(); // cheap hack to return an empty hash

		if (in == null) {
			throw new IllegalArgumentException();
		}

		//
		// Make sure we read the entire POSTed body.
		//
		byte[] postedBytes = new byte[len];
		try {
			int offset = 0;

			do {
				int inputLen = in.read(postedBytes, offset, len - offset);
				if (inputLen <= 0) {
					String msg = lStrings.getString("err.io.short_read");
					throw new IllegalArgumentException(msg);
				}
				offset += inputLen;
			} while ((len - offset) > 0);

		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		// XXX we shouldn't assume that the only kind of POST body
		// is FORM data encoded using ASCII or ISO Latin/1 ... or
		// that the body should always be treated as FORM data.
		//

		try {
			String postedBody = new String(postedBytes, 0, len, "8859_1");
			return parseQueryString(postedBody);
		} catch (java.io.UnsupportedEncodingException e) {
			// XXX function should accept an encoding parameter & throw this
			// exception. Otherwise throw something expected.
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/*
	 * Parse a name in the query string.
	 */

	static private String parseName(String s, StringBuffer sb) {
		sb.setLength(0);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					sb.append((char) Integer.parseInt(
							s.substring(i + 1, i + 3), 16));
					i += 2;
				} catch (NumberFormatException e) {
					// XXX
					// need to be more specific about illegal arg
					throw new IllegalArgumentException();
				} catch (StringIndexOutOfBoundsException e) {
					String rest = s.substring(i);
					sb.append(rest);
					if (rest.length() == 2)
						i++;
				}

				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * Reconstructs the URL the client used to make the request, using
	 * information in the <code>HttpServletRequest</code> object. The returned
	 * URL contains a protocol, server name, port number, and server path, but
	 * it does not include query string parameters.
	 * 
	 * <p>
	 * Because this method returns a <code>StringBuffer</code>, not a string,
	 * you can modify the URL easily, for example, to append query parameters.
	 * 
	 * <p>
	 * This method is useful for creating redirect messages and for reporting
	 * errors.
	 * 
	 * @param req
	 *            a <code>HttpServletRequest</code> object containing the
	 *            client's request
	 * 
	 * @return a <code>StringBuffer</code> object containing the reconstructed
	 *         URL
	 * 
	 */

	public static StringBuffer getRequestURL(HttpServletRequest req) {
		StringBuffer url = new StringBuffer();
		String scheme = req.getScheme();
		int port = req.getServerPort();
		String urlPath = req.getRequestURI();

		// String servletPath = req.getServletPath ();
		// String pathInfo = req.getPathInfo ();

		url.append(scheme); // http, https
		url.append("://");
		url.append(req.getServerName());
		if ((scheme.equals("http") && port != 80)
				|| (scheme.equals("https") && port != 443)) {
			url.append(':');
			url.append(req.getServerPort());
		}
		// if (servletPath != null)
		// url.append (servletPath);
		// if (pathInfo != null)
		// url.append (pathInfo);
		url.append(urlPath);
		return url;
	}
}
