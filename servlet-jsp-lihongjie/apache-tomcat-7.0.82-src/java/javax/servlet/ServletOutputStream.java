/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.servlet;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Provides an output stream for sending binary data to the client. A
 * <code>ServletOutputStream</code> object is normally retrieved via the
 * {@link ServletResponse#getOutputStream} method.
 * <p>
 * This is an abstract class that the servlet container implements. Subclasses
 * of this class must implement the <code>java.io.OutputStream.write(int)</code>
 * method.
 * 
 * @author Various
 * @see ServletResponse
 */
public abstract class ServletOutputStream extends OutputStream {

    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    /**
     * Does nothing, because this is an abstract class.
     */
    protected ServletOutputStream() {
        // NOOP
    }

    /**
     * Writes a <code>String</code> to the client, without a carriage
     * return-line feed (CRLF) character at the end.
     * 
     * @param s
     *            the <code>String</code> to send to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void print(String s) throws IOException {
        if (s == null)
            s = "null";
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);

            //
            // XXX NOTE: This is clearly incorrect for many strings,
            // but is the only consistent approach within the current
            // servlet framework. It must suffice until servlet output
            // streams properly encode their output.
            //
            if ((c & 0xff00) != 0) { // high order byte must be zero
                String errMsg = lStrings.getString("err.not_iso8859_1");
                Object[] errArgs = new Object[1];
                errArgs[0] = Character.valueOf(c);
                errMsg = MessageFormat.format(errMsg, errArgs);
                throw new CharConversionException(errMsg);
            }
            write(c);
        }
    }

    /**
     * Writes a <code>boolean</code> value to the client, with no carriage
     * return-line feed (CRLF) character at the end.
     * 
     * @param b
     *            the <code>boolean</code> value to send to the client
     * @exception IOException
     *                if an input or output exception occurred
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
     * Writes a character to the client, with no carriage return-line feed
     * (CRLF) at the end.
     * 
     * @param c
     *            the character to send to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void print(char c) throws IOException {
        print(String.valueOf(c));
    }

    /**
     * Writes an int to the client, with no carriage return-line feed (CRLF) at
     * the end.
     * 
     * @param i
     *            the int to send to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void print(int i) throws IOException {
        print(String.valueOf(i));
    }

    /**
     * Writes a <code>long</code> value to the client, with no carriage
     * return-line feed (CRLF) at the end.
     * 
     * @param l
     *            the <code>long</code> value to send to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void print(long l) throws IOException {
        print(String.valueOf(l));
    }

    /**
     * Writes a <code>float</code> value to the client, with no carriage
     * return-line feed (CRLF) at the end.
     * 
     * @param f
     *            the <code>float</code> value to send to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void print(float f) throws IOException {
        print(String.valueOf(f));
    }

    /**
     * Writes a <code>double</code> value to the client, with no carriage
     * return-line feed (CRLF) at the end.
     * 
     * @param d
     *            the <code>double</code> value to send to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void print(double d) throws IOException {
        print(String.valueOf(d));
    }

    /**
     * Writes a carriage return-line feed (CRLF) to the client.
     * 
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void println() throws IOException {
        print("\r\n");
    }

    /**
     * Writes a <code>String</code> to the client, followed by a carriage
     * return-line feed (CRLF).
     * 
     * @param s
     *            the <code>String</code> to write to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void println(String s) throws IOException {
        print(s);
        println();
    }

    /**
     * Writes a <code>boolean</code> value to the client, followed by a carriage
     * return-line feed (CRLF).
     * 
     * @param b
     *            the <code>boolean</code> value to write to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void println(boolean b) throws IOException {
        print(b);
        println();
    }

    /**
     * Writes a character to the client, followed by a carriage return-line feed
     * (CRLF).
     * 
     * @param c
     *            the character to write to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void println(char c) throws IOException {
        print(c);
        println();
    }

    /**
     * Writes an int to the client, followed by a carriage return-line feed
     * (CRLF) character.
     * 
     * @param i
     *            the int to write to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void println(int i) throws IOException {
        print(i);
        println();
    }

    /**
     * Writes a <code>long</code> value to the client, followed by a carriage
     * return-line feed (CRLF).
     * 
     * @param l
     *            the <code>long</code> value to write to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void println(long l) throws IOException {
        print(l);
        println();
    }

    /**
     * Writes a <code>float</code> value to the client, followed by a carriage
     * return-line feed (CRLF).
     * 
     * @param f
     *            the <code>float</code> value to write to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void println(float f) throws IOException {
        print(f);
        println();
    }

    /**
     * Writes a <code>double</code> value to the client, followed by a carriage
     * return-line feed (CRLF).
     * 
     * @param d
     *            the <code>double</code> value to write to the client
     * @exception IOException
     *                if an input or output exception occurred
     */
    public void println(double d) throws IOException {
        print(d);
        println();
    }
}
