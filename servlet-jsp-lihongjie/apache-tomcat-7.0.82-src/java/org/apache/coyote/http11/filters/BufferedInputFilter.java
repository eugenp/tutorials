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

/**
 * Input filter responsible for reading and buffering the request body, so that
 * it does not interfere with client SSL handshake messages.
 */
public class BufferedInputFilter implements InputFilter {

    // -------------------------------------------------------------- Constants

    private static final String ENCODING_NAME = "buffered";
    private static final ByteChunk ENCODING = new ByteChunk();


    // ----------------------------------------------------- Instance Variables

    private ByteChunk buffered = null;
    private ByteChunk tempRead = new ByteChunk(1024);
    private InputBuffer buffer;
    private boolean hasRead = false;


    // ----------------------------------------------------- Static Initializer

    static {
        ENCODING.setBytes(ENCODING_NAME.getBytes(Charset.defaultCharset()), 0,
                ENCODING_NAME.length());
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Set the buffering limit. This should be reset every time the buffer is
     * used.
     *
     * @param limit The maximum number of bytes that will be buffered
     */
    public void setLimit(int limit) {
        if (buffered == null) {
            buffered = new ByteChunk(4048);
            buffered.setLimit(limit);
        }
    }


    // ---------------------------------------------------- InputBuffer Methods


    /**
     * Reads the request body and buffers it.
     */
    @Override
    public void setRequest(Request request) {
        // save off the Request body
        try {
            while (buffer.doRead(tempRead, request) >= 0) {
                buffered.append(tempRead);
                tempRead.recycle();
            }
        } catch(IOException ioe) {
            // No need for i18n - this isn't going to get logged anywhere
            throw new IllegalStateException(
                    "Request body too large for buffer");
        }
    }

    /**
     * Fills the given ByteChunk with the buffered request body.
     */
    @Override
    public int doRead(ByteChunk chunk, Request request) throws IOException {
        if (hasRead || buffered.getLength() <= 0) {
            return -1;
        }

        chunk.setBytes(buffered.getBytes(), buffered.getStart(),
                buffered.getLength());
        hasRead = true;
        return chunk.getLength();
    }

    @Override
    public void setBuffer(InputBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void recycle() {
        if (buffered != null) {
            if (buffered.getBuffer().length > 65536) {
                buffered = null;
            } else {
                buffered.recycle();
            }
        }
        tempRead.recycle();
        hasRead = false;
        buffer = null;
    }

    @Override
    public ByteChunk getEncodingName() {
        return ENCODING;
    }

    @Override
    public long end() throws IOException {
        return 0;
    }

    @Override
    public int available() {
        return buffered.getLength();
    }
    
}
