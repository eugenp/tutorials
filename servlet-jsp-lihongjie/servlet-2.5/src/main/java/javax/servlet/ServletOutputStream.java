

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

import java.io.OutputStream;
import java.io.IOException;
import java.io.CharConversionException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Provides an output stream for sending binary data to the
 * client. A <code>ServletOutputStream</code> object is normally retrieved 
 * via the {@link ServletResponse#getOutputStream} method.
 *
 * <p>This is an abstract class that the servlet container implements.
 * Subclasses of this class
 * must implement the <code>java.io.OutputStream.write(int)</code>
 * method.
 *
 * 
 * @author 	Various
 *
 * @see 	ServletResponse
 * 
 * 描述： ServletOutputStream 抽象类 ,向客户端发送二进制数据.
 */

public abstract class ServletOutputStream extends OutputStream {

    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static ResourceBundle lStrings =
	ResourceBundle.getBundle(LSTRING_FILE);


    
    /**
     *
     * Does nothing, because this is an abstract class.
     *
     */

    protected ServletOutputStream() { }


    /**
     * Writes a <code>String</code> to the client, 
     * without a carriage return-line feed (CRLF) 
     * character at the end.
     *
     *
     * @param s			the <code>String</code> to send to the client
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */

    public void print(String s) throws IOException {
	if (s==null) s="null";
	int len = s.length();
	for (int i = 0; i < len; i++) {
	    char c = s.charAt (i);

	    //
	    // XXX NOTE:  This is clearly incorrect for many strings,
	    // but is the only consistent approach within the current
	    // servlet framework.  It must suffice until servlet output
	    // streams properly encode their output.
	    //
	    if ((c & 0xff00) != 0) {	// high order byte must be zero
		String errMsg = lStrings.getString("err.not_iso8859_1");
		Object[] errArgs = new Object[1];
		errArgs[0] = new Character(c);
		errMsg = MessageFormat.format(errMsg, errArgs);
		throw new CharConversionException(errMsg);
	    }
	    write (c);
	}
    }



    /**
     * Writes a <code>boolean</code> value to the client,
     * with no carriage return-line feed (CRLF) 
     * character at the end.
     *
     * @param b			the <code>boolean</code> value 
     *				to send to the client
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */

    public void print(boolean b) throws IOException {
	String msg;
	if (b) {
	    msg = lStrings.getString("value.true");
	} else {
	    msg = lStrings.getString("value.false");
	}
	print(msg);
    }



    /**
     * Writes a character to the client,
     * with no carriage return-line feed (CRLF) 
     * at the end.
     *
     * @param c			the character to send to the client
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */

    public void print(char c) throws IOException {
	print(String.valueOf(c));
    }




    /**
     *
     * Writes an int to the client,
     * with no carriage return-line feed (CRLF) 
     * at the end.
     *
     * @param i			the int to send to the client
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */  

    public void print(int i) throws IOException {
	print(String.valueOf(i));
    }



 
    /**
     * 
     * Writes a <code>long</code> value to the client,
     * with no carriage return-line feed (CRLF) at the end.
     *
     * @param l			the <code>long</code> value 
     *				to send to the client
     *
     * @exception IOException 	if an input or output exception 
     *				occurred
     * 
     */

    public void print(long l) throws IOException {
	print(String.valueOf(l));
    }



    /**
     *
     * Writes a <code>float</code> value to the client,
     * with no carriage return-line feed (CRLF) at the end.
     *
     * @param f			the <code>float</code> value
     *				to send to the client
     *
     * @exception IOException	if an input or output exception occurred
     *
     *
     */

    public void print(float f) throws IOException {
	print(String.valueOf(f));
    }



    /**
     *
     * Writes a <code>double</code> value to the client,
     * with no carriage return-line feed (CRLF) at the end.
     * 
     * @param d			the <code>double</code> value
     *				to send to the client
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */

    public void print(double d) throws IOException {
	print(String.valueOf(d));
    }



    /**
     * Writes a carriage return-line feed (CRLF)
     * to the client.
     *
     *
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */

    public void println() throws IOException {
	print("\r\n");
    }



    /**
     * Writes a <code>String</code> to the client, 
     * followed by a carriage return-line feed (CRLF).
     *
     *
     * @param s			the <code>String</code> to write to the client
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */

    public void println(String s) throws IOException {
	print(s);
	println();
    }




    /**
     *
     * Writes a <code>boolean</code> value to the client, 
     * followed by a 
     * carriage return-line feed (CRLF).
     *
     *
     * @param b			the <code>boolean</code> value 
     *				to write to the client
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */

    public void println(boolean b) throws IOException {
	print(b);
	println();
    }



    /**
     *
     * Writes a character to the client, followed by a carriage
     * return-line feed (CRLF).
     *
     * @param c			the character to write to the client
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */

    public void println(char c) throws IOException {
	print(c);
	println();
    }



    /**
     *
     * Writes an int to the client, followed by a 
     * carriage return-line feed (CRLF) character.
     *
     *
     * @param i			the int to write to the client
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */

    public void println(int i) throws IOException {
	print(i);
	println();
    }



    /**  
     *
     * Writes a <code>long</code> value to the client, followed by a 
     * carriage return-line feed (CRLF).
     *
     *
     * @param l			the <code>long</code> value to write to the client
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */  

    public void println(long l) throws IOException {
	print(l);
	println();
    }



    /**
     *
     * Writes a <code>float</code> value to the client, 
     * followed by a carriage return-line feed (CRLF).
     *
     * @param f			the <code>float</code> value 
     *				to write to the client
     *
     *
     * @exception IOException 	if an input or output exception 
     *				occurred
     *
     */

    public void println(float f) throws IOException {
	print(f);
	println();
    }



    /**
     *
     * Writes a <code>double</code> value to the client, 
     * followed by a carriage return-line feed (CRLF).
     *
     *
     * @param d			the <code>double</code> value
     *				to write to the client
     *
     * @exception IOException 	if an input or output exception occurred
     *
     */

    public void println(double d) throws IOException {
	print(d);
	println();
    }
}
