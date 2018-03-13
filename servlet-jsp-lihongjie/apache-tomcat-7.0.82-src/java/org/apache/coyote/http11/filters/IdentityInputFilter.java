/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.coyote.http11.filters;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.coyote.InputBuffer;
import org.apache.coyote.Request;
import org.apache.coyote.http11.InputFilter;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.res.StringManager;

/**
 * Identity input filter.
 * 
 * @author Remy Maucherat
 */
public class IdentityInputFilter implements InputFilter {

    private static final StringManager sm = StringManager.getManager(
            IdentityInputFilter.class.getPackage().getName());


    // -------------------------------------------------------------- Constants


    protected static final String ENCODING_NAME = "identity";
    protected static final ByteChunk ENCODING = new ByteChunk();


    // ----------------------------------------------------- Static Initializer


    static {
        ENCODING.setBytes(ENCODING_NAME.getBytes(Charset.defaultCharset()), 0,
                ENCODING_NAME.length());
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Content length.
     */
    protected long contentLength = -1;


    /**
     * Remaining bytes.
     */
    protected long remaining = 0;


    /**
     * Next buffer in the pipeline.
     */
    protected InputBuffer buffer;


    /**
     * Chunk used to read leftover bytes.
     */
    protected ByteChunk endChunk = new ByteChunk();


    private final int maxSwallowSize;


    // ------------------------------------------------------------- Properties

    /**
     * Get content length.
     *
     * @deprecated  Unused - will be removed in 8.0.x
     */
    @Deprecated
    public long getContentLength() {
        return contentLength;
    }


    /**
     * Get remaining bytes.
     *
     * @deprecated  Unused - will be removed in 8.0.x
     */
    @Deprecated
    public long getRemaining() {
        return remaining;
    }


    // ------------------------------------------------------------ Constructor

    public IdentityInputFilter(int maxSwallowSize) {
        this.maxSwallowSize = maxSwallowSize;
    }


    // ---------------------------------------------------- InputBuffer Methods


    /**
     * Read bytes.
     * 
     * @return If the filter does request length control, this value is
     * significant; it should be the number of bytes consumed from the buffer,
     * up until the end of the current request body, or the buffer length, 
     * whichever is greater. If the filter does not do request body length
     * control, the returned value should be -1.
     */
    @Override
    public int doRead(ByteChunk chunk, Request req)
        throws IOException {

        int result = -1;

        if (contentLength >= 0) {
            if (remaining > 0) {
                int nRead = buffer.doRead(chunk, req);
                if (nRead > remaining) {
                    // The chunk is longer than the number of bytes remaining
                    // in the body; changing the chunk length to the number
                    // of bytes remaining
                    chunk.setBytes(chunk.getBytes(), chunk.getStart(), 
                                   (int) remaining);
                    result = (int) remaining;
                } else {
                    result = nRead;
                }
                if (nRead > 0) {
                    remaining = remaining - nRead;
                }
            } else {
                // No more bytes left to be read : return -1 and clear the 
                // buffer
                chunk.recycle();
                result = -1;
            }
        }

        return result;

    }


    // ---------------------------------------------------- InputFilter Methods


    /**
     * Read the content length from the request.
     */
    @Override
    public void setRequest(Request request) {
        contentLength = request.getContentLengthLong();
        remaining = contentLength;
    }


    @Override
    public long end() throws IOException {

        final boolean maxSwallowSizeExceeded = (maxSwallowSize > -1 && remaining > maxSwallowSize);
        long swallowed = 0;

        // Consume extra bytes.
        while (remaining > 0) {

            int nread = buffer.doRead(endChunk, null);
            if (nread > 0 ) {
                swallowed += nread;
                remaining = remaining - nread;
                if (maxSwallowSizeExceeded && swallowed > maxSwallowSize) {
                    // Note: We do not fail early so the client has a chance to
                    // read the response before the connection is closed. See:
                    // http://httpd.apache.org/docs/2.0/misc/fin_wait_2.html#appendix
                    throw new IOException(sm.getString("inputFilter.maxSwallow"));
                }
            } else { // errors are handled higher up.
                remaining = 0;
            }
        }

        // If too many bytes were read, return the amount.
        return -remaining;

    }


    /**
     * Amount of bytes still available in a buffer.
     */
    @Override
    public int available() {
        return 0;
    }
    

    /**
     * Set the next buffer in the filter pipeline.
     */
    @Override
    public void setBuffer(InputBuffer buffer) {
        this.buffer = buffer;
    }


    /**
     * Make the filter ready to process the next request.
     */
    @Override
    public void recycle() {
        contentLength = -1;
        remaining = 0;
        endChunk.recycle();
    }


    /**
     * Return the name of the associated encoding; Here, the value is 
     * "identity".
     */
    @Override
    public ByteChunk getEncodingName() {
        return ENCODING;
    }


}
