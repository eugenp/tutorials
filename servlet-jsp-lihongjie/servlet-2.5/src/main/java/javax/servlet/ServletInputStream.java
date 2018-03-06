

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

import java.io.InputStream;
import java.io.IOException;

/**
 * 
 * Provides an input stream for reading binary data from a client
 * request, including an efficient <code>readLine</code> method
 * for reading data one line at a time. With some protocols, such
 * as HTTP POST and PUT, a <code>ServletInputStream</code>
 * object can be used to read data sent from the client.
 *
 * <p>A <code>ServletInputStream</code> object is normally retrieved via
 * the {@link ServletRequest#getInputStream} method.
 *
 *
 * <p>This is an abstract class that a servlet container implements.
 * Subclasses of this class
 * must implement the <code>java.io.InputStream.read()</code> method.
 *
 *
 * @author 	Various
 *
 * @see		ServletRequest 
 * 
 * 描述： ServletInputStream 抽象类, 定义名为readLine()的方法,用于从客户端读取二进制数据
 */

public abstract class ServletInputStream extends InputStream {



    /**
     * Does nothing, because this is an abstract class.
     *
     */

    protected ServletInputStream() { }

  
  
    
    /**
     *
     * Reads the input stream, one line at a time. Starting at an
     * offset, reads bytes into an array, until it reads a certain number
     * of bytes or reaches a newline character, which it reads into the
     * array as well.
     *
     * <p>This method returns -1 if it reaches the end of the input
     * stream before reading the maximum number of bytes.
     *
     *
     *
     * @param b 		an array of bytes into which data is read
     *
     * @param off 		an integer specifying the character at which
     *				this method begins reading
     *
     * @param len		an integer specifying the maximum number of 
     *				bytes to read
     *
     * @return			an integer specifying the actual number of bytes 
     *				read, or -1 if the end of the stream is reached
     *
     * @exception IOException	if an input or output exception has occurred
     *
     */
     
	public int readLine(byte[] b, int off, int len) throws IOException {

		if (len <= 0) {
			return 0;
		}
		int count = 0, c;

		while ((c = read()) != -1) {
			b[off++] = (byte) c;
			count++;
			if (c == '\n' || count == len) {
				break;
			}
		}
		return count > 0 ? count : -1;
	}
}



