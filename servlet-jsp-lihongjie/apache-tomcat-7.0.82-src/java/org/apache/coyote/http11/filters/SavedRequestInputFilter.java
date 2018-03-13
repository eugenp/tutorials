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

import org.apache.coyote.InputBuffer;
import org.apache.coyote.http11.InputFilter;
import org.apache.tomcat.util.buf.ByteChunk;

/**
 * Input filter responsible for replaying the request body when restoring the
 * saved request after FORM authentication.
 */
public class SavedRequestInputFilter implements InputFilter {

    /**
     * The original request body.
     */
    protected ByteChunk input = null;

    /**
     * Create a new SavedRequestInputFilter.
     * 
     * @param input The saved request body to be replayed.
     */
    public SavedRequestInputFilter(ByteChunk input) {
        this.input = input;
    }

    /**
     * Read bytes.
     */
    @Override
    public int doRead(ByteChunk chunk, org.apache.coyote.Request request)
            throws IOException {
        int writeLength = 0;
        
        if (chunk.getLimit() > 0 && chunk.getLimit() < input.getLength()) {
            writeLength = chunk.getLimit();
        } else {
            writeLength = input.getLength();
        }
        
        if(input.getOffset()>= input.getEnd())
            return -1;
        
        input.substract(chunk.getBuffer(), 0, writeLength);
        chunk.setOffset(0);
        chunk.setEnd(writeLength);
        
        return writeLength;
    }

    /**
     * Set the content length on the request.
     */
    @Override
    public void setRequest(org.apache.coyote.Request request) {
        request.setContentLength(input.getLength());
    }

    /**
     * Make the filter ready to process the next request.
     */
    @Override
    public void recycle() {
        input = null;
    }

    /**
     * Return the name of the associated encoding; here, the value is null.
     */
    @Override
    public ByteChunk getEncodingName() {
        return null;
    }

    /**
     * Set the next buffer in the filter pipeline (has no effect).
     */
    @Override
    public void setBuffer(InputBuffer buffer) {
        // NOOP since this filter will be providing the request body
    }

    /**
     * Amount of bytes still available in a buffer.
     */
    @Override
    public int available() {
        return input.getLength();
    }
    
    /**
     * End the current request (has no effect).
     */
    @Override
    public long end() throws IOException {
        return 0;
    }

}
