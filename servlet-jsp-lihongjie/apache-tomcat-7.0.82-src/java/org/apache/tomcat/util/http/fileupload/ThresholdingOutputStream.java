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
package org.apache.tomcat.util.http.fileupload;

import java.io.IOException;
import java.io.OutputStream;


/**
 * An output stream which triggers an event when a specified number of bytes of
 * data have been written to it. The event can be used, for example, to throw
 * an exception if a maximum has been reached, or to switch the underlying
 * stream type when the threshold is exceeded.
 * <p>
 * This class overrides all <code>OutputStream</code> methods. However, these
 * overrides ultimately call the corresponding methods in the underlying output
 * stream implementation.
 * <p>
 * NOTE: This implementation may trigger the event <em>before</em> the threshold
 * is actually reached, since it triggers when a pending write operation would
 * cause the threshold to be exceeded.
 */
public abstract class ThresholdingOutputStream
    extends OutputStream
{

    // ----------------------------------------------------------- Data members


    /**
     * The threshold at which the event will be triggered.
     */
    private final int threshold;


    /**
     * The number of bytes written to the output stream.
     */
    private long written;


    /**
     * Whether or not the configured threshold has been exceeded.
     */
    private boolean thresholdExceeded;


    // ----------------------------------------------------------- Constructors


    /**
     * Constructs an instance of this class which will trigger an event at the
     * specified threshold.
     *
     * @param threshold The number of bytes at which to trigger an event.
     */
    public ThresholdingOutputStream(int threshold)
    {
        this.threshold = threshold;
    }


    // --------------------------------------------------- OutputStream methods


    /**
     * Writes the specified byte to this output stream.
     *
     * @param b The byte to be written.
     *
     * @exception IOException if an error occurs.
     */
    @Override
    public void write(int b) throws IOException
    {
        checkThreshold(1);
        getStream().write(b);
        written++;
    }


    /**
     * Writes <code>b.length</code> bytes from the specified byte array to this
     * output stream.
     *
     * @param b The array of bytes to be written.
     *
     * @exception IOException if an error occurs.
     */
    @Override
    public void write(byte b[]) throws IOException
    {
        checkThreshold(b.length);
        getStream().write(b);
        written += b.length;
    }


    /**
     * Writes <code>len</code> bytes from the specified byte array starting at
     * offset <code>off</code> to this output stream.
     *
     * @param b   The byte array from which the data will be written.
     * @param off The start offset in the byte array.
     * @param len The number of bytes to write.
     *
     * @exception IOException if an error occurs.
     */
    @Override
    public void write(byte b[], int off, int len) throws IOException
    {
        checkThreshold(len);
        getStream().write(b, off, len);
        written += len;
    }


    /**
     * Flushes this output stream and forces any buffered output bytes to be
     * written out.
     *
     * @exception IOException if an error occurs.
     */
    @Override
    public void flush() throws IOException
    {
        getStream().flush();
    }


    /**
     * Closes this output stream and releases any system resources associated
     * with this stream.
     *
     * @exception IOException if an error occurs.
     */
    @Override
    public void close() throws IOException
    {
        try
        {
            flush();
        }
        catch (IOException ignored)
        {
            // ignore
        }
        getStream().close();
    }


    // --------------------------------------------------------- Public methods


    /**
     * Determines whether or not the configured threshold has been exceeded for
     * this output stream.
     *
     * @return {@code true} if the threshold has been reached;
     *         {@code false} otherwise.
     */
    public boolean isThresholdExceeded()
    {
        return written > threshold;
    }


    // ------------------------------------------------------ Protected methods


    /**
     * Checks to see if writing the specified number of bytes would cause the
     * configured threshold to be exceeded. If so, triggers an event to allow
     * a concrete implementation to take action on this.
     *
     * @param count The number of bytes about to be written to the underlying
     *              output stream.
     *
     * @exception IOException if an error occurs.
     */
    protected void checkThreshold(int count) throws IOException
    {
        if (!thresholdExceeded && written + count > threshold)
        {
            thresholdExceeded = true;
            thresholdReached();
        }
    }

    // ------------------------------------------------------- Abstract methods


    /**
     * Returns the underlying output stream, to which the corresponding
     * <code>OutputStream</code> methods in this class will ultimately delegate.
     *
     * @return The underlying output stream.
     *
     * @exception IOException if an error occurs.
     */
    protected abstract OutputStream getStream() throws IOException;


    /**
     * Indicates that the configured threshold has been reached, and that a
     * subclass should take whatever action necessary on this event. This may
     * include changing the underlying output stream.
     *
     * @exception IOException if an error occurs.
     */
    protected abstract void thresholdReached() throws IOException;
}
