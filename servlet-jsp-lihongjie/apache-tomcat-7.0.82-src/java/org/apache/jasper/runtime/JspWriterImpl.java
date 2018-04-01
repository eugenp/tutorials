/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jasper.runtime;

import java.io.IOException;
import java.io.Writer;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspWriter;

import org.apache.jasper.Constants;
import org.apache.jasper.compiler.Localizer;
import org.apache.jasper.security.SecurityUtil;

/**
 * Write text to a character-output stream, buffering characters so as
 * to provide for the efficient writing of single characters, arrays,
 * and strings. 
 *
 * Provide support for discarding for the output that has been 
 * buffered. 
 * 
 * This needs revisiting when the buffering problems in the JSP spec
 * are fixed -akv 
 *
 * @author Anil K. Vijendran
 */
public class JspWriterImpl extends JspWriter {
    
    private Writer out;
    private ServletResponse response;    
    private char cb[];
    private int nextChar;
    private boolean flushed = false;
    private boolean closed = false;
    
    public JspWriterImpl() {
        super( Constants.DEFAULT_BUFFER_SIZE, true );
    }
    
    /**
     * Create a buffered character-output stream that uses a default-sized
     * output buffer.
     *
     * @param  response  A Servlet Response
     */
    public JspWriterImpl(ServletResponse response) {
        this(response, Constants.DEFAULT_BUFFER_SIZE, true);
    }
    
    /**
     * Create a new buffered character-output stream that uses an output
     * buffer of the given size.
     *
     * @param  response A Servlet Response
     * @param  sz       Output-buffer size, a positive integer
     *
     * @exception  IllegalArgumentException  If sz is <= 0
     */
    public JspWriterImpl(ServletResponse response, int sz, 
            boolean autoFlush) {
        super(sz, autoFlush);
        if (sz < 0)
            throw new IllegalArgumentException("Buffer size <= 0");
        this.response = response;
        cb = sz == 0 ? null : new char[sz];
        nextChar = 0;
    }
    
    void init( ServletResponse response, int sz, boolean autoFlush ) {
        this.response= response;
        if( sz > 0 && ( cb == null || sz > cb.length ) )
            cb=new char[sz];
        nextChar = 0;
        this.autoFlush=autoFlush;
        this.bufferSize=sz;
    }
    
    /** Package-level access
     */
    void recycle() {
        flushed = false;
        closed = false;
        out = null;
        nextChar = 0;
        response = null;
    }
    
    /**
     * Flush the output buffer to the underlying character stream, without
     * flushing the stream itself.  This method is non-private only so that it
     * may be invoked by PrintStream.
     */
    protected final void flushBuffer() throws IOException {
        if (bufferSize == 0)
            return;
        flushed = true;
        ensureOpen();
        if (nextChar == 0)
            return;
        initOut();
        out.write(cb, 0, nextChar);
        nextChar = 0;
    }
    
    private void initOut() throws IOException {
        if (out == null) {
            out = response.getWriter();
        }
    }
    
    private String getLocalizeMessage(final String message){
        if (SecurityUtil.isPackageProtectionEnabled()){
            return AccessController.doPrivileged(new PrivilegedAction<String>(){
                @Override
                public String run(){
                    return Localizer.getMessage(message); 
                }
            });
        } else {
            return Localizer.getMessage(message);
        }
    }
    
    /**
     * Discard the output buffer.
     */
    @Override
    public final void clear() throws IOException {
        if ((bufferSize == 0) && (out != null))
            // clear() is illegal after any unbuffered output (JSP.5.5)
            throw new IllegalStateException(
                    getLocalizeMessage("jsp.error.ise_on_clear"));
        if (flushed)
            throw new IOException(
                    getLocalizeMessage("jsp.error.attempt_to_clear_flushed_buffer"));
        ensureOpen();
        nextChar = 0;
    }
    
    @Override
    public void clearBuffer() throws IOException {
        if (bufferSize == 0)
            throw new IllegalStateException(
                    getLocalizeMessage("jsp.error.ise_on_clear"));
        ensureOpen();
        nextChar = 0;
    }
    
    private final void bufferOverflow() throws IOException {
        throw new IOException(getLocalizeMessage("jsp.error.overflow"));
    }
    
    /**
     * Flush the stream.
     *
     */
    @Override
    public void flush()  throws IOException {
        flushBuffer();
        if (out != null) {
            out.flush();
        }
    }
    
    /**
     * Close the stream.
     *
     */
    @Override
    public void close() throws IOException {
        if (response == null || closed)
            // multiple calls to close is OK
            return;
        flush();
        if (out != null)
            out.close();
        out = null;
        closed = true;
    }
    
    /**
     * @return the number of bytes unused in the buffer
     */
    @Override
    public int getRemaining() {
        return bufferSize - nextChar;
    }
    
    /** check to make sure that the stream has not been closed */
    private void ensureOpen() throws IOException {
        if (response == null || closed)
            throw new IOException("Stream closed");
    }
    
    
    /**
     * Write a single character.
     */
    @Override
    public void write(int c) throws IOException {
        ensureOpen();
        if (bufferSize == 0) {
            initOut();
            out.write(c);
        }
        else {
            if (nextChar >= bufferSize)
                if (autoFlush)
                    flushBuffer();
                else
                    bufferOverflow();
            cb[nextChar++] = (char) c;
        }
    }
    
    /**
     * Our own little min method, to avoid loading java.lang.Math if we've run
     * out of file descriptors and we're trying to print a stack trace.
     */
    private static int min(int a, int b) {
        if (a < b) return a;
        return b;
    }
    
    /**
     * Write a portion of an array of characters.
     *
     * <p> Ordinarily this method stores characters from the given array into
     * this stream's buffer, flushing the buffer to the underlying stream as
     * needed.  If the requested length is at least as large as the buffer,
     * however, then this method will flush the buffer and write the characters
     * directly to the underlying stream.  Thus redundant
     * <code>DiscardableBufferedWriter</code>s will not copy data unnecessarily.
     *
     * @param  cbuf  A character array
     * @param  off   Offset from which to start reading characters
     * @param  len   Number of characters to write
     */
    @Override
    public void write(char cbuf[], int off, int len) 
    throws IOException 
    {
        ensureOpen();
        
        if (bufferSize == 0) {
            initOut();
            out.write(cbuf, off, len);
            return;
        }
        
        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
                ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        } 
        
        if (len >= bufferSize) {
            /* If the request length exceeds the size of the output buffer,
             flush the buffer and then write the data directly.  In this
             way buffered streams will cascade harmlessly. */
            if (autoFlush)
                flushBuffer();
            else
                bufferOverflow();
            initOut();
            out.write(cbuf, off, len);
            return;
        }
        
        int b = off, t = off + len;
        while (b < t) {
            int d = min(bufferSize - nextChar, t - b);
            System.arraycopy(cbuf, b, cb, nextChar, d);
            b += d;
            nextChar += d;
            if (nextChar >= bufferSize) 
                if (autoFlush)
                    flushBuffer();
                else
                    bufferOverflow();
        }
        
    }
    
    /**
     * Write an array of characters.  This method cannot be inherited from the
     * Writer class because it must suppress I/O exceptions.
     */
    @Override
    public void write(char buf[]) throws IOException {
        write(buf, 0, buf.length);
    }
    
    /**
     * Write a portion of a String.
     *
     * @param  s     String to be written
     * @param  off   Offset from which to start reading characters
     * @param  len   Number of characters to be written
     */
    @Override
    public void write(String s, int off, int len) throws IOException {
        ensureOpen();
        if (bufferSize == 0) {
            initOut();
            out.write(s, off, len);
            return;
        }
        int b = off, t = off + len;
        while (b < t) {
            int d = min(bufferSize - nextChar, t - b);
            s.getChars(b, b + d, cb, nextChar);
            b += d;
            nextChar += d;
            if (nextChar >= bufferSize) 
                if (autoFlush)
                    flushBuffer();
                else
                    bufferOverflow();
        }
    }
    
    
    static String lineSeparator = System.getProperty("line.separator");
    
    /**
     * Write a line separator.  The line separator string is defined by the
     * system property <tt>line.separator</tt>, and is not necessarily a single
     * newline ('\n') character.
     *
     * @exception  IOException  If an I/O error occurs
     */
    
    @Override
    public void newLine() throws IOException {
        write(lineSeparator);
    }
    
    
    /* Methods that do not terminate lines */
    
    /**
     * Print a boolean value.  The string produced by <code>{@link
     * java.lang.String#valueOf(boolean)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param      b   The <code>boolean</code> to be printed
     */
    @Override
    public void print(boolean b) throws IOException {
        write(b ? "true" : "false");
    }
    
    /**
     * Print a character.  The character is translated into one or more bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param      c   The <code>char</code> to be printed
     */
    @Override
    public void print(char c) throws IOException {
        write(String.valueOf(c));
    }
    
    /**
     * Print an integer.  The string produced by <code>{@link
     * java.lang.String#valueOf(int)}</code> is translated into bytes according
     * to the platform's default character encoding, and these bytes are
     * written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param      i   The <code>int</code> to be printed
     */
    @Override
    public void print(int i) throws IOException {
        write(String.valueOf(i));
    }
    
    /**
     * Print a long integer.  The string produced by <code>{@link
     * java.lang.String#valueOf(long)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param      l   The <code>long</code> to be printed
     */
    @Override
    public void print(long l) throws IOException {
        write(String.valueOf(l));
    }
    
    /**
     * Print a floating-point number.  The string produced by <code>{@link
     * java.lang.String#valueOf(float)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param      f   The <code>float</code> to be printed
     */
    @Override
    public void print(float f) throws IOException {
        write(String.valueOf(f));
    }
    
    /**
     * Print a double-precision floating-point number.  The string produced by
     * <code>{@link java.lang.String#valueOf(double)}</code> is translated into
     * bytes according to the platform's default character encoding, and these
     * bytes are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param      d   The <code>double</code> to be printed
     */
    @Override
    public void print(double d) throws IOException {
        write(String.valueOf(d));
    }
    
    /**
     * Print an array of characters.  The characters are converted into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param      s   The array of chars to be printed
     *
     * @throws  NullPointerException  If <code>s</code> is <code>null</code>
     */
    @Override
    public void print(char s[]) throws IOException {
        write(s);
    }
    
    /**
     * Print a string.  If the argument is <code>null</code> then the string
     * <code>"null"</code> is printed.  Otherwise, the string's characters are
     * converted into bytes according to the platform's default character
     * encoding, and these bytes are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * @param      s   The <code>String</code> to be printed
     */
    @Override
    public void print(String s) throws IOException {
        if (s == null) {
            s = "null";
        }
        write(s);
    }
    
    /**
     * Print an object.  The string produced by the <code>{@link
     * java.lang.String#valueOf(Object)}</code> method is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param      obj   The <code>Object</code> to be printed
     */
    @Override
    public void print(Object obj) throws IOException {
        write(String.valueOf(obj));
    }
    
    /* Methods that do terminate lines */
    
    /**
     * Terminate the current line by writing the line separator string.  The
     * line separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     *
     * Need to change this from PrintWriter because the default
     * println() writes  to the sink directly instead of through the
     * write method...  
     */
    @Override
    public void println() throws IOException {
        newLine();
    }
    
    /**
     * Print a boolean value and then terminate the line.  This method behaves
     * as though it invokes <code>{@link #print(boolean)}</code> and then
     * <code>{@link #println()}</code>.
     */
    @Override
    public void println(boolean x) throws IOException {
        print(x);
        println();
    }
    
    /**
     * Print a character and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(char)}</code> and then <code>{@link
     * #println()}</code>.
     */
    @Override
    public void println(char x) throws IOException {
        print(x);
        println();
    }
    
    /**
     * Print an integer and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(int)}</code> and then <code>{@link
     * #println()}</code>.
     */
    @Override
    public void println(int x) throws IOException {
        print(x);
        println();
    }
    
    /**
     * Print a long integer and then terminate the line.  This method behaves
     * as though it invokes <code>{@link #print(long)}</code> and then
     * <code>{@link #println()}</code>.
     */
    @Override
    public void println(long x) throws IOException {
        print(x);
        println();
    }
    
    /**
     * Print a floating-point number and then terminate the line.  This method
     * behaves as though it invokes <code>{@link #print(float)}</code> and then
     * <code>{@link #println()}</code>.
     */
    @Override
    public void println(float x) throws IOException {
        print(x);
        println();
    }
    
    /**
     * Print a double-precision floating-point number and then terminate the
     * line.  This method behaves as though it invokes <code>{@link
     * #print(double)}</code> and then <code>{@link #println()}</code>.
     */
    @Override
    public void println(double x) throws IOException {
        print(x);
        println();
    }
    
    /**
     * Print an array of characters and then terminate the line.  This method
     * behaves as though it invokes <code>{@link #print(char[])}</code> and then
     * <code>{@link #println()}</code>.
     */
    @Override
    public void println(char x[]) throws IOException {
        print(x);
        println();
    }
    
    /**
     * Print a String and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     */
    @Override
    public void println(String x) throws IOException {
        print(x);
        println();
    }
    
    /**
     * Print an Object and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(Object)}</code> and then
     * <code>{@link #println()}</code>.
     */
    @Override
    public void println(Object x) throws IOException {
        print(x);
        println();
    }
    
}
