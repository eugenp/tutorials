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
package compressionFilters;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;

/**
 * Implementation of <b>ServletOutputStream</b> that works with
 * the CompressionServletResponseWrapper implementation.
 *
 * @author Amy Roh
 * @author Dmitri Valdin
 */
public class CompressionResponseStream extends ServletOutputStream {

    // ----------------------------------------------------------- Constructors

    /**
     * Construct a servlet output stream associated with the specified Response.
     *
     * @param responseWrapper The associated response wrapper
     * @param originalOutput the output stream
     */
    public CompressionResponseStream(
            CompressionServletResponseWrapper responseWrapper,
            ServletOutputStream originalOutput) {

        super();
        closed = false;
        this.response = responseWrapper;
        this.output = originalOutput;
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The threshold number which decides to compress or not.
     * Users can configure in web.xml to set it to fit their needs.
     */
    protected int compressionThreshold = 0;

    /**
     * The compression buffer size to avoid chunking
     */
    protected int compressionBuffer = 0;

    /**
     * The mime types to compress
     */
    protected String[] compressionMimeTypes = {"text/html", "text/xml", "text/plain"};

    /**
     * Debug level
     */
    private int debug = 0;

    /**
     * The buffer through which all of our output bytes are passed.
     */
    protected byte[] buffer = null;

    /**
     * The number of data bytes currently in the buffer.
     */
    protected int bufferCount = 0;

    /**
     * The underlying gzip output stream to which we should write data.
     */
    protected OutputStream gzipstream = null;

    /**
     * Has this stream been closed?
     */
    protected boolean closed = false;

    /**
     * The content length past which we will not write, or -1 if there is
     * no defined content length.
     */
    protected int length = -1;

    /**
     * The response with which this servlet output stream is associated.
     */
    protected CompressionServletResponseWrapper response = null;

    /**
     * The underlying servlet output stream to which we should write data.
     */
    protected ServletOutputStream output = null;


    // --------------------------------------------------------- Public Methods

    /**
     * Set debug level
     */
    public void setDebugLevel(int debug) {
        this.debug = debug;
    }


    /**
     * Set the compressionThreshold number and create buffer for this size
     */
    protected void setCompressionThreshold(int compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
        buffer = new byte[this.compressionThreshold];
        if (debug > 1) {
            System.out.println("compressionThreshold is set to "+ this.compressionThreshold);
        }
    }

    /**
     * The compression buffer size to avoid chunking
     */
    protected void setCompressionBuffer(int compressionBuffer) {
        this.compressionBuffer = compressionBuffer;
        if (debug > 1) {
            System.out.println("compressionBuffer is set to "+ this.compressionBuffer);
        }
    }

    /**
     * Set supported mime types
     */
    public void setCompressionMimeTypes(String[] compressionMimeTypes) {
        this.compressionMimeTypes = compressionMimeTypes;
        if (debug > 1) {
            System.out.println("compressionMimeTypes is set to " +
                    Arrays.toString(this.compressionMimeTypes));
        }
    }

    /**
     * Close this output stream, causing any buffered data to be flushed and
     * any further output data to throw an IOException.
     */
    @Override
    public void close() throws IOException {

        if (debug > 1) {
            System.out.println("close() @ CompressionResponseStream");
        }
        if (closed)
            throw new IOException("This output stream has already been closed");

        if (gzipstream != null) {
            flushToGZip();
            gzipstream.close();
            gzipstream = null;
        } else {
            if (bufferCount > 0) {
                if (debug > 2) {
                    System.out.print("output.write(");
                    System.out.write(buffer, 0, bufferCount);
                    System.out.println(")");
                }
                output.write(buffer, 0, bufferCount);
                bufferCount = 0;
            }
        }

        output.close();
        closed = true;

    }


    /**
     * Flush any buffered data for this output stream, which also causes the
     * response to be committed.
     */
    @Override
    public void flush() throws IOException {

        if (debug > 1) {
            System.out.println("flush() @ CompressionResponseStream");
        }
        if (closed) {
            throw new IOException("Cannot flush a closed output stream");
        }

        if (gzipstream != null) {
            gzipstream.flush();
        }

    }

    public void flushToGZip() throws IOException {

        if (debug > 1) {
            System.out.println("flushToGZip() @ CompressionResponseStream");
        }
        if (bufferCount > 0) {
            if (debug > 1) {
                System.out.println("flushing out to GZipStream, bufferCount = " + bufferCount);
            }
            writeToGZip(buffer, 0, bufferCount);
            bufferCount = 0;
        }

    }

    /**
     * Write the specified byte to our output stream.
     *
     * @param b The byte to be written
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void write(int b) throws IOException {

        if (debug > 1) {
            System.out.println("write "+b+" in CompressionResponseStream ");
        }
        if (closed)
            throw new IOException("Cannot write to a closed output stream");

        if (bufferCount >= buffer.length) {
            flushToGZip();
        }

        buffer[bufferCount++] = (byte) b;

    }


    /**
     * Write <code>b.length</code> bytes from the specified byte array
     * to our output stream.
     *
     * @param b The byte array to be written
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void write(byte b[]) throws IOException {

        write(b, 0, b.length);

    }


    /**
     * Write <code>len</code> bytes from the specified byte array, starting
     * at the specified offset, to our output stream.
     *
     * @param b The byte array containing the bytes to be written
     * @param off Zero-relative starting offset of the bytes to be written
     * @param len The number of bytes to be written
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public void write(byte b[], int off, int len) throws IOException {

        if (debug > 1) {
            System.out.println("write, bufferCount = " + bufferCount + " len = " + len + " off = " + off);
        }
        if (debug > 2) {
            System.out.print("write(");
            System.out.write(b, off, len);
            System.out.println(")");
        }

        if (closed)
            throw new IOException("Cannot write to a closed output stream");

        if (len == 0)
            return;

        // Can we write into buffer ?
        if (len <= (buffer.length - bufferCount)) {
            System.arraycopy(b, off, buffer, bufferCount, len);
            bufferCount += len;
            return;
        }

        // There is not enough space in buffer. Flush it ...
        flushToGZip();

        // ... and try again. Note, that bufferCount = 0 here !
        if (len <= (buffer.length - bufferCount)) {
            System.arraycopy(b, off, buffer, bufferCount, len);
            bufferCount += len;
            return;
        }

        // write direct to gzip
        writeToGZip(b, off, len);
    }

    public void writeToGZip(byte b[], int off, int len) throws IOException {

        if (debug > 1) {
            System.out.println("writeToGZip, len = " + len);
        }
        if (debug > 2) {
            System.out.print("writeToGZip(");
            System.out.write(b, off, len);
            System.out.println(")");
        }
        if (gzipstream == null) {
            if (debug > 1) {
                System.out.println("new GZIPOutputStream");
            }

            boolean alreadyCompressed = false;
            String contentEncoding = response.getHeader("Content-Encoding");
            if (contentEncoding != null) {
                if (contentEncoding.contains("gzip")) {
                    alreadyCompressed = true;
                    if (debug > 0) {
                        System.out.println("content is already compressed");
                    }
                } else {
                    if (debug > 0) {
                        System.out.println("content is not compressed yet");
                    }
                }
            }

            boolean compressibleMimeType = false;
            // Check for compatible MIME-TYPE
            if (compressionMimeTypes != null) {
                if (startsWithStringArray(compressionMimeTypes, response.getContentType())) {
                    compressibleMimeType = true;
                    if (debug > 0) {
                        System.out.println("mime type " + response.getContentType() + " is compressible");
                    }
                } else {
                    if (debug > 0) {
                        System.out.println("mime type " + response.getContentType() + " is not compressible");
                    }
                }
            }

            if (response.isCommitted()) {
                if (debug > 1)
                    System.out.print("Response already committed. Using original output stream");
                gzipstream = output;
            } else if (alreadyCompressed) {
                if (debug > 1)
                    System.out.print("Response already compressed. Using original output stream");
                gzipstream = output;
            } else if (!compressibleMimeType) {
                if (debug > 1)
                    System.out.print("Response mime type is not compressible. Using original output stream");
                gzipstream = output;
            } else {
                response.addHeader("Content-Encoding", "gzip");
                response.setContentLength(-1);  // don't use any preset content-length as it will be wrong after gzipping
                response.setBufferSize(compressionBuffer);
                gzipstream = new GZIPOutputStream(output);
            }
        }
        gzipstream.write(b, off, len);

    }


    // -------------------------------------------------------- Package Methods


    /**
     * Has this response stream been closed?
     */
    public boolean closed() {

        return (this.closed);

    }

    /**
     * Checks if any entry in the string array starts with the specified value
     *
     * @param sArray the StringArray
     * @param value string
     */
    private boolean startsWithStringArray(String sArray[], String value) {
        if (value == null)
           return false;
        for (int i = 0; i < sArray.length; i++) {
            if (value.startsWith(sArray[i])) {
                return true;
            }
        }
        return false;
    }
}
