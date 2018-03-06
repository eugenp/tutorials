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

package javax.servlet.jsp;

import java.io.IOException;

/**
 * <p>
 * The actions and template data in a JSP page is written using the JspWriter
 * object that is referenced by the implicit variable out which is initialized
 * automatically using methods in the PageContext object.
 *<p>
 * This abstract class emulates some of the functionality found in the
 * java.io.BufferedWriter and java.io.PrintWriter classes, however it differs in
 * that it throws java.io.IOException from the print methods while PrintWriter
 * does not.
 * <p>
 * <B>Buffering</B>
 * <p>
 * The initial JspWriter object is associated with the PrintWriter object of the
 * ServletResponse in a way that depends on whether the page is or is not
 * buffered. If the page is not buffered, output written to this JspWriter
 * object will be written through to the PrintWriter directly, which will be
 * created if necessary by invoking the getWriter() method on the response
 * object. But if the page is buffered, the PrintWriter object will not be
 * created until the buffer is flushed and operations like setContentType() are
 * legal. Since this flexibility simplifies programming substantially, buffering
 * is the default for JSP pages.
 * <p>
 * Buffering raises the issue of what to do when the buffer is exceeded. Two
 * approaches can be taken:
 * <ul>
 * <li>Exceeding the buffer is not a fatal error; when the buffer is exceeded,
 * just flush the output.
 * <li>Exceeding the buffer is a fatal error; when the buffer is exceeded, raise
 * an exception.
 * </ul>
 * <p>
 * Both approaches are valid, and thus both are supported in the JSP technology.
 * The behavior of a page is controlled by the autoFlush attribute, which
 * defaults to true. In general, JSP pages that need to be sure that correct and
 * complete data has been sent to their client may want to set autoFlush to
 * false, with a typical case being that where the client is an application
 * itself. On the other hand, JSP pages that send data that is meaningful even
 * when partially constructed may want to set autoFlush to true; such as when
 * the data is sent for immediate display through a browser. Each application
 * will need to consider their specific needs.
 * <p>
 * An alternative considered was to make the buffer size unbounded; but, this
 * had the disadvantage that runaway computations would consume an unbounded
 * amount of resources.
 * <p>
 * The "out" implicit variable of a JSP implementation class is of this type. If
 * the page directive selects autoflush="true" then all the I/O operations on
 * this class shall automatically flush the contents of the buffer if an
 * overflow condition would result if the current operation were performed
 * without a flush. If autoflush="false" then all the I/O operations on this
 * class shall throw an IOException if performing the current operation would
 * result in a buffer overflow condition.
 * 
 * @see java.io.Writer
 * @see java.io.BufferedWriter
 * @see java.io.PrintWriter
 */
public abstract class JspWriter extends java.io.Writer {

    /**
     * Constant indicating that the Writer is not buffering output.
     */
    public static final int NO_BUFFER = 0;

    /**
     * Constant indicating that the Writer is buffered and is using the
     * implementation default buffer size.
     */
    public static final int DEFAULT_BUFFER = -1;

    /**
     * Constant indicating that the Writer is buffered and is unbounded; this is
     * used in BodyContent.
     */
    public static final int UNBOUNDED_BUFFER = -2;

    /**
     * Protected constructor.
     * 
     * @param bufferSize
     *            the size of the buffer to be used by the JspWriter
     * @param autoFlush
     *            whether the JspWriter should be autoflushing
     */
    protected JspWriter(int bufferSize, boolean autoFlush) {
        this.bufferSize = bufferSize;
        this.autoFlush = autoFlush;
    }

    /**
     * Write a line separator. The line separator string is defined by the
     * system property <tt>line.separator</tt>, and is not necessarily a single
     * newline ('\n') character.
     * 
     * @exception IOException
     *                If an I/O error occurs
     */
    public abstract void newLine() throws IOException;

    /**
     * Print a boolean value. The string produced by <code>{@link
     * java.lang.String#valueOf(boolean)}</code>
     * is written to the JspWriter's buffer or, if no buffer is used, directly
     * to the underlying writer.
     * 
     * @param b
     *            The <code>boolean</code> to be printed
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void print(boolean b) throws IOException;

    /**
     * Print a character. The character is written to the JspWriter's buffer or,
     * if no buffer is used, directly to the underlying writer.
     * 
     * @param c
     *            The <code>char</code> to be printed
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void print(char c) throws IOException;

    /**
     * Print an integer. The string produced by <code>{@link
     * java.lang.String#valueOf(int)}</code>
     * is written to the JspWriter's buffer or, if no buffer is used, directly
     * to the underlying writer.
     * 
     * @param i
     *            The <code>int</code> to be printed
     * @see java.lang.Integer#toString(int)
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void print(int i) throws IOException;

    /**
     * Print a long integer. The string produced by <code>{@link
     * java.lang.String#valueOf(long)}</code>
     * is written to the JspWriter's buffer or, if no buffer is used, directly
     * to the underlying writer.
     * 
     * @param l
     *            The <code>long</code> to be printed
     * @see java.lang.Long#toString(long)
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void print(long l) throws IOException;

    /**
     * Print a floating-point number. The string produced by <code>{@link
     * java.lang.String#valueOf(float)}</code>
     * is written to the JspWriter's buffer or, if no buffer is used, directly
     * to the underlying writer.
     * 
     * @param f
     *            The <code>float</code> to be printed
     * @see java.lang.Float#toString(float)
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void print(float f) throws IOException;

    /**
     * Print a double-precision floating-point number. The string produced by
     * <code>{@link java.lang.String#valueOf(double)}</code> is written to the
     * JspWriter's buffer or, if no buffer is used, directly to the underlying
     * writer.
     * 
     * @param d
     *            The <code>double</code> to be printed
     * @see java.lang.Double#toString(double)
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void print(double d) throws IOException;

    /**
     * Print an array of characters. The characters are written to the
     * JspWriter's buffer or, if no buffer is used, directly to the underlying
     * writer.
     * 
     * @param s
     *            The array of chars to be printed
     * @throws NullPointerException
     *             If <code>s</code> is <code>null</code>
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void print(char s[]) throws IOException;

    /**
     * Print a string. If the argument is <code>null</code> then the string
     * <code>"null"</code> is printed. Otherwise, the string's characters are
     * written to the JspWriter's buffer or, if no buffer is used, directly to
     * the underlying writer.
     * 
     * @param s
     *            The <code>String</code> to be printed
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void print(String s) throws IOException;

    /**
     * Print an object. The string produced by the <code>{@link
     * java.lang.String#valueOf(Object)}</code>
     * method is written to the JspWriter's buffer or, if no buffer is used,
     * directly to the underlying writer.
     * 
     * @param obj
     *            The <code>Object</code> to be printed
     * @see java.lang.Object#toString()
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void print(Object obj) throws IOException;

    /**
     * Terminate the current line by writing the line separator string. The line
     * separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     * 
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void println() throws IOException;

    /**
     * Print a boolean value and then terminate the line. This method behaves as
     * though it invokes <code>{@link #print(boolean)}</code> and then
     * <code>{@link #println()}</code>.
     * 
     * @param x
     *            the boolean to write
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void println(boolean x) throws IOException;

    /**
     * Print a character and then terminate the line. This method behaves as
     * though it invokes <code>{@link #print(char)}</code> and then <code>{@link
     * #println()}</code>
     * .
     * 
     * @param x
     *            the char to write
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void println(char x) throws IOException;

    /**
     * Print an integer and then terminate the line. This method behaves as
     * though it invokes <code>{@link #print(int)}</code> and then <code>{@link
     * #println()}</code>
     * .
     * 
     * @param x
     *            the int to write
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void println(int x) throws IOException;

    /**
     * Print a long integer and then terminate the line. This method behaves as
     * though it invokes <code>{@link #print(long)}</code> and then
     * <code>{@link #println()}</code>.
     * 
     * @param x
     *            the long to write
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void println(long x) throws IOException;

    /**
     * Print a floating-point number and then terminate the line. This method
     * behaves as though it invokes <code>{@link #print(float)}</code> and then
     * <code>{@link #println()}</code>.
     * 
     * @param x
     *            the float to write
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void println(float x) throws IOException;

    /**
     * Print a double-precision floating-point number and then terminate the
     * line. This method behaves as though it invokes <code>{@link
     * #print(double)}</code> and
     * then <code>{@link #println()}</code>.
     * 
     * @param x
     *            the double to write
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void println(double x) throws IOException;

    /**
     * Print an array of characters and then terminate the line. This method
     * behaves as though it invokes <code>print(char[])</code> and then
     * <code>println()</code>.
     * 
     * @param x
     *            the char[] to write
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void println(char x[]) throws IOException;

    /**
     * Print a String and then terminate the line. This method behaves as though
     * it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     * 
     * @param x
     *            the String to write
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void println(String x) throws IOException;

    /**
     * Print an Object and then terminate the line. This method behaves as
     * though it invokes <code>{@link #print(Object)}</code> and then
     * <code>{@link #println()}</code>.
     * 
     * @param x
     *            the Object to write
     * @throws java.io.IOException
     *             If an error occurred while writing
     */
    public abstract void println(Object x) throws IOException;

    /**
     * Clear the contents of the buffer. If the buffer has been already been
     * flushed then the clear operation shall throw an IOException to signal the
     * fact that some data has already been irrevocably written to the client
     * response stream.
     * 
     * @throws IOException
     *             If an I/O error occurs
     */
    public abstract void clear() throws IOException;

    /**
     * Clears the current contents of the buffer. Unlike clear(), this method
     * will not throw an IOException if the buffer has already been flushed. It
     * merely clears the current content of the buffer and returns.
     * 
     * @throws IOException
     *             If an I/O error occurs
     */
    public abstract void clearBuffer() throws IOException;

    /**
     * Flush the stream. If the stream has saved any characters from the various
     * write() methods in a buffer, write them immediately to their intended
     * destination. Then, if that destination is another character or byte
     * stream, flush it. Thus one flush() invocation will flush all the buffers
     * in a chain of Writers and OutputStreams.
     * <p>
     * The method may be invoked indirectly if the buffer size is exceeded.
     * <p>
     * Once a stream has been closed, further write() or flush() invocations
     * will cause an IOException to be thrown.
     * 
     * @exception IOException
     *                If an I/O error occurs
     */
    @Override
    public abstract void flush() throws IOException;

    /**
     * Close the stream, flushing it first.
     * <p>
     * This method needs not be invoked explicitly for the initial JspWriter as
     * the code generated by the JSP container will automatically include a call
     * to close().
     * <p>
     * Closing a previously-closed stream, unlike flush(), has no effect.
     * 
     * @exception IOException
     *                If an I/O error occurs
     */
    @Override
    public abstract void close() throws IOException;

    /**
     * This method returns the size of the buffer used by the JspWriter.
     * 
     * @return the size of the buffer in bytes, or 0 is unbuffered.
     */
    public int getBufferSize() {
        return bufferSize;
    }

    /**
     * This method returns the number of unused bytes in the buffer.
     * 
     * @return the number of bytes unused in the buffer
     */
    public abstract int getRemaining();

    /**
     * This method indicates whether the JspWriter is autoFlushing.
     * 
     * @return if this JspWriter is auto flushing or throwing IOExceptions on
     *         buffer overflow conditions
     */
    public boolean isAutoFlush() {
        return autoFlush;
    }

    /*
     * fields
     */

    /**
     * The size of the buffer used by the JspWriter.
     */
    protected int bufferSize;

    /**
     * Whether the JspWriter is autoflushing.
     */
    protected boolean autoFlush;
}
