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

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;

import org.apache.jasper.Constants;

/**
 * Write text to a character-output stream, buffering characters so as
 * to provide for the efficient writing of single characters, arrays,
 * and strings. 
 *
 * Provide support for discarding for the output that has been buffered. 
 *
 * @author Rajiv Mordani
 * @author Jan Luehe
 */
public class BodyContentImpl extends BodyContent {
    
    private static final String LINE_SEPARATOR = 
        System.getProperty("line.separator");
    private static final boolean LIMIT_BUFFER = 
        Boolean.parseBoolean(System.getProperty("org.apache.jasper.runtime.BodyContentImpl.LIMIT_BUFFER", "false"));
    
    private char[] cb;
    private int nextChar;
    private boolean closed;
    
    // Enclosed writer to which any output is written
    private Writer writer;
    
    /**
     * Constructor.
     */
    public BodyContentImpl(JspWriter enclosingWriter) {
        super(enclosingWriter);
        cb = new char[Constants.DEFAULT_TAG_BUFFER_SIZE];
        bufferSize = cb.length;
        nextChar = 0;
        closed = false;
    }
    
    /**
     * Write a single character.
     */
    @Override
    public void write(int c) throws IOException {
        if (writer != null) {
            writer.write(c);
        } else {
            ensureOpen();
            if (nextChar >= bufferSize) {
                reAllocBuff (1);
            }
            cb[nextChar++] = (char) c;
        }
    }
    
    /**
     * Write a portion of an array of characters.
     *
     * <p> Ordinarily this method stores characters from the given array into
     * this stream's buffer, flushing the buffer to the underlying stream as
     * needed.  If the requested length is at least as large as the buffer,
     * however, then this method will flush the buffer and write the characters
     * directly to the underlying stream.  Thus redundant
     * <code>DiscardableBufferedWriter</code>s will not copy data
     * unnecessarily.
     *
     * @param cbuf A character array
     * @param off Offset from which to start reading characters
     * @param len Number of characters to write
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        if (writer != null) {
            writer.write(cbuf, off, len);
        } else {
            ensureOpen();
            
            if ((off < 0) || (off > cbuf.length) || (len < 0) ||
                    ((off + len) > cbuf.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return;
            } 
            
            if (len >= bufferSize - nextChar)
                reAllocBuff (len);
            
            System.arraycopy(cbuf, off, cb, nextChar, len);
            nextChar+=len;
        }
    }
    
    /**
     * Write an array of characters.  This method cannot be inherited from the
     * Writer class because it must suppress I/O exceptions.
     */
    @Override
    public void write(char[] buf) throws IOException {
        if (writer != null) {
            writer.write(buf);
        } else {
            write(buf, 0, buf.length);
        }
    }
    
    /**
     * Write a portion of a String.
     *
     * @param s String to be written
     * @param off Offset from which to start reading characters
     * @param len Number of characters to be written
     */
    @Override
    public void write(String s, int off, int len) throws IOException {
        if (writer != null) {
            writer.write(s, off, len);
        } else {
            ensureOpen();
            if (len >= bufferSize - nextChar)
                reAllocBuff(len);
            
            s.getChars(off, off + len, cb, nextChar);
            nextChar += len;
        }
    }
    
    /**
     * Write a string.  This method cannot be inherited from the Writer class
     * because it must suppress I/O exceptions.
     */
    @Override
    public void write(String s) throws IOException {
        if (writer != null) {
            writer.write(s);
        } else {
            write(s, 0, s.length());
        }
    }
    
    /**
     * Write a line separator.  The line separator string is defined by the
     * system property <tt>line.separator</tt>, and is not necessarily a single
     * newline ('\n') character.
     *
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void newLine() throws IOException {
        if (writer != null) {
            writer.write(LINE_SEPARATOR);
        } else {
            write(LINE_SEPARATOR);
        }
    }
    
    /**
     * Print a boolean value.  The string produced by <code>{@link
     * java.lang.String#valueOf(boolean)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param b The <code>boolean</code> to be printed
     * @throws IOException
     */
    @Override
    public void print(boolean b) throws IOException {
        if (writer != null) {
            writer.write(b ? "true" : "false");
        } else {
            write(b ? "true" : "false");
        }
    }
    
    /**
     * Print a character.  The character is translated into one or more bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param c The <code>char</code> to be printed
     * @throws IOException
     */
    @Override
    public void print(char c) throws IOException {
        if (writer != null) {
            writer.write(String.valueOf(c));
        } else {
            write(String.valueOf(c));
        }
    }
    
    /**
     * Print an integer.  The string produced by <code>{@link
     * java.lang.String#valueOf(int)}</code> is translated into bytes according
     * to the platform's default character encoding, and these bytes are
     * written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param i The <code>int</code> to be printed
     * @throws IOException
     */
    @Override
    public void print(int i) throws IOException {
        if (writer != null) {
            writer.write(String.valueOf(i));
        } else {
            write(String.valueOf(i));
        }
    }
    
    /**
     * Print a long integer.  The string produced by <code>{@link
     * java.lang.String#valueOf(long)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * @param l The <code>long</code> to be printed
     * @throws IOException
     */
    @Override
    public void print(long l) throws IOException {
        if (writer != null) {
            writer.write(String.valueOf(l));
        } else {
            write(String.valueOf(l));
        }
    }
    
    /**
     * Print a floating-point number.  The string produced by <code>{@link
     * java.lang.String#valueOf(float)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * @param f The <code>float</code> to be printed
     * @throws IOException
     */
    @Override
    public void print(float f) throws IOException {
        if (writer != null) {
            writer.write(String.valueOf(f));
        } else {
            write(String.valueOf(f));
        }
    }
    
    /**
     * Print a double-precision floating-point number.  The string produced by
     * <code>{@link java.lang.String#valueOf(double)}</code> is translated into
     * bytes according to the platform's default character encoding, and these
     * bytes are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param d The <code>double</code> to be printed
     * @throws IOException
     */
    @Override
    public void print(double d) throws IOException {
        if (writer != null) {
            writer.write(String.valueOf(d));
        } else {
            write(String.valueOf(d));
        }
    }
    
    /**
     * Print an array of characters.  The characters are converted into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * @param s The array of chars to be printed
     *
     * @throws NullPointerException If <code>s</code> is <code>null</code>
     * @throws IOException
     */
    @Override
    public void print(char[] s) throws IOException {
        if (writer != null) {
            writer.write(s);
        } else {
            write(s);
        }
    }
    
    /**
     * Print a string.  If the argument is <code>null</code> then the string
     * <code>"null"</code> is printed.  Otherwise, the string's characters are
     * converted into bytes according to the platform's default character
     * encoding, and these bytes are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * @param s The <code>String</code> to be printed
     * @throws IOException
     */
    @Override
    public void print(String s) throws IOException {
        if (s == null) s = "null";
        if (writer != null) {
            writer.write(s);
        } else {
            write(s);
        }
    }
    
    /**
     * Print an object.  The string produced by the <code>{@link
     * java.lang.String#valueOf(Object)}</code> method is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * @param obj The <code>Object</code> to be printed
     * @throws IOException
     */
    @Override
    public void print(Object obj) throws IOException {
        if (writer != null) {
            writer.write(String.valueOf(obj));
        } else {
            write(String.valueOf(obj));
        }
    }
    
    /**
     * Terminate the current line by writing the line separator string.  The
     * line separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     *
     * @throws IOException
     */
    @Override
    public void println() throws IOException {
        newLine();
    }
    
    /**
     * Print a boolean value and then terminate the line.  This method behaves
     * as though it invokes <code>{@link #print(boolean)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @throws IOException
     */
    @Override
    public void println(boolean x) throws IOException {
        print(x);
        println();
    }
    
    /**
     * Print a character and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(char)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @throws IOException
     */
    @Override
    public void println(char x) throws IOException {
        print(x);
        println();
    }
    
    /**
     * Print an integer and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(int)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @throws IOException
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
     *
     * @throws IOException
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
     *
     * @throws IOException
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
     *
     * @throws IOException
     */
    @Override
    public void println(double x) throws IOException{
        print(x);
        println();
    }
    
    /**
     * Print an array of characters and then terminate the line.  This method
     * behaves as though it invokes <code>{@link #print(char[])}</code> and
     * then <code>{@link #println()}</code>.
     *
     * @throws IOException
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
     *
     * @throws IOException
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
     *
     * @throws IOException
     */
    @Override
    public void println(Object x) throws IOException {
        print(x);
        println();
    }
    
    /**
     * Clear the contents of the buffer. If the buffer has been already
     * been flushed then the clear operation shall throw an IOException
     * to signal the fact that some data has already been irrevocably 
     * written to the client response stream.
     *
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void clear() throws IOException {
        if (writer != null) {
            throw new IOException();
        } else {
            nextChar = 0;
            if (LIMIT_BUFFER && (cb.length > Constants.DEFAULT_TAG_BUFFER_SIZE)) {
                cb = new char[Constants.DEFAULT_TAG_BUFFER_SIZE];
                bufferSize = cb.length;
            }
        }
    }
    
    /**
     * Clears the current contents of the buffer. Unlike clear(), this
     * method will not throw an IOException if the buffer has already been
     * flushed. It merely clears the current content of the buffer and
     * returns.
     *
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void clearBuffer() throws IOException {
        if (writer == null) {
            this.clear();
        }
    }
    
    /**
     * Close the stream, flushing it first.  Once a stream has been closed,
     * further write() or flush() invocations will cause an IOException to be
     * thrown.  Closing a previously-closed stream, however, has no effect.
     *
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        } else {
            closed = true;
        }
    }
    
    /**
     * This method returns the size of the buffer used by the JspWriter.
     *
     * @return the size of the buffer in bytes, or 0 is unbuffered.
     */
    @Override
    public int getBufferSize() {
        // According to the spec, the JspWriter returned by 
        // JspContext.pushBody(java.io.Writer writer) must behave as
        // though it were unbuffered. This means that its getBufferSize()
        // must always return 0.
        return (writer == null) ? bufferSize : 0;
    }
    
    /**
     * @return the number of bytes unused in the buffer
     */
    @Override
    public int getRemaining() {
        return (writer == null) ? bufferSize-nextChar : 0;
    }
    
    /**
     * Return the value of this BodyJspWriter as a Reader.
     * Note: this is after evaluation!!  There are no scriptlets,
     * etc in this stream.
     *
     * @return the value of this BodyJspWriter as a Reader
     */
    @Override
    public Reader getReader() {
        return (writer == null) ? new CharArrayReader (cb, 0, nextChar) : null;
    }
    
    /**
     * Return the value of the BodyJspWriter as a String.
     * Note: this is after evaluation!!  There are no scriptlets,
     * etc in this stream.
     *
     * @return the value of the BodyJspWriter as a String
     */
    @Override
    public String getString() {
        return (writer == null) ? new String(cb, 0, nextChar) : null;
    }
    
    /**
     * Write the contents of this BodyJspWriter into a Writer.
     * Subclasses are likely to do interesting things with the
     * implementation so some things are extra efficient.
     *
     * @param out The writer into which to place the contents of this body
     * evaluation
     */
    @Override
    public void writeOut(Writer out) throws IOException {
        if (writer == null) {
            out.write(cb, 0, nextChar);
            // Flush not called as the writer passed could be a BodyContent and
            // it doesn't allow to flush.
        }
    }
    
    /**
     * Sets the writer to which all output is written.
     */
    void setWriter(Writer writer) {
        this.writer = writer;
        closed = false;
        if (writer == null) {
            clearBody();
        }
    }

    /**
     * This method shall "reset" the internal state of a BodyContentImpl,
     * releasing all internal references, and preparing it for potential
     * reuse by a later invocation of {@link PageContextImpl#pushBody(Writer)}.
     *
     * <p>Note, that BodyContentImpl instances are usually owned by a
     * PageContextImpl instance, and PageContextImpl instances are recycled
     * and reused.
     *
     * @see PageContextImpl#release()
     */
    protected void recycle() {
        this.writer = null;
        try {
            this.clear();
        } catch (IOException ex) {
            // ignore
        }
    }

    private void ensureOpen() throws IOException {
        if (closed) throw new IOException("Stream closed");
    }
    
    /**
     * Reallocates buffer since the spec requires it to be unbounded.
     */
    private void reAllocBuff(int len) {
        
        if (bufferSize + len <= cb.length) {
            bufferSize = cb.length;
            return;
        }
        
        if (len < cb.length) {
            len = cb.length;
        }
        
        char[] tmp = new char[cb.length + len];
        System.arraycopy(cb, 0, tmp, 0, cb.length);
        cb = tmp;
        bufferSize = cb.length;
    }
    
    
}
