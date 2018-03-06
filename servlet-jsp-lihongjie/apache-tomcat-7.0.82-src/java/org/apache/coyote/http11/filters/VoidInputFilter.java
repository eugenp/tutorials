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
 * Void input filter, which returns -1 when attempting a read. Used with a GET,
 * HEAD, or a similar request.
 * 
 * @author Remy Maucherat
 */
public class VoidInputFilter implements InputFilter {


    // -------------------------------------------------------------- Constants


    protected static final String ENCODING_NAME = "void";
    protected static final ByteChunk ENCODING = new ByteChunk();


    // ----------------------------------------------------- Static Initializer


    static {
        ENCODING.setBytes(ENCODING_NAME.getBytes(Charset.defaultCharset()), 0,
                ENCODING_NAME.length());
    }


    // ----------------------------------------------------- Instance Variables


    // --------------------------------------------------- OutputBuffer Methods


    /**
     * Write some bytes.
     * 
     * @return number of bytes written by the filter
     */
    @Override
    public int doRead(ByteChunk chunk, Request req)
        throws IOException {

        return -1;

    }


    // --------------------------------------------------- OutputFilter Methods


    /**
     * Set the associated request.
     */
    @Override
    public void setRequest(Request request) {
        // NOOP: Request isn't used so ignore it
    }


    /**
     * Set the next buffer in the filter pipeline.
     */
    @Override
    public void setBuffer(InputBuffer buffer) {
        // NOOP: No body to read
    }


    /**
     * Make the filter ready to process the next request.
     */
    @Override
    public void recycle() {
        // NOOP: Nothing to recycle
    }


    /**
     * Return the name of the associated encoding; Here, the value is 
     * "void".
     */
    @Override
    public ByteChunk getEncodingName() {
        return ENCODING;
    }


    /**
     * End the current request. It is acceptable to write extra bytes using
     * buffer.doWrite during the execution of this method.
     * 
     * @return Should return 0 unless the filter does some content length 
     * delimitation, in which case the number is the amount of extra bytes or
     * missing bytes, which would indicate an error. 
     * Note: It is recommended that extra bytes be swallowed by the filter.
     */
    @Override
    public long end()
        throws IOException {
        return 0;
    }


    /**
     * Amount of bytes still available in a buffer.
     */
    @Override
    public int available() {
        return 0;
    }
    
}
