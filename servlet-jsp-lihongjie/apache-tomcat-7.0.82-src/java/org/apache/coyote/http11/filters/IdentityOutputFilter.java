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

import org.apache.coyote.OutputBuffer;
import org.apache.coyote.Response;
import org.apache.coyote.http11.OutputFilter;
import org.apache.tomcat.util.buf.ByteChunk;

/**
 * Identity output filter.
 * 
 * @author Remy Maucherat
 */
public class IdentityOutputFilter implements OutputFilter {


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
    protected OutputBuffer buffer;


    // --------------------------------------------------- OutputBuffer Methods


    /**
     * Write some bytes.
     * 
     * @return number of bytes written by the filter
     */
    @Override
    public int doWrite(ByteChunk chunk, Response res)
        throws IOException {

        int result = -1;

        if (contentLength >= 0) {
            if (remaining > 0) {
                result = chunk.getLength();
                if (result > remaining) {
                    // The chunk is longer than the number of bytes remaining
                    // in the body; changing the chunk length to the number
                    // of bytes remaining
                    chunk.setBytes(chunk.getBytes(), chunk.getStart(), 
                                   (int) remaining);
                    result = (int) remaining;
                    remaining = 0;
                } else {
                    remaining = remaining - result;
                }
                buffer.doWrite(chunk, res);
            } else {
                // No more bytes left to be written : return -1 and clear the 
                // buffer
                chunk.recycle();
                result = -1;
            }
        } else {
            // If no content length was set, just write the bytes
            buffer.doWrite(chunk, res);
            result = chunk.getLength();
        }

        return result;

    }


    @Override
    public long getBytesWritten() {
        return buffer.getBytesWritten();
    }


    // --------------------------------------------------- OutputFilter Methods


    /**
     * Some filters need additional parameters from the response. All the 
     * necessary reading can occur in that method, as this method is called
     * after the response header processing is complete.
     */
    @Override
    public void setResponse(Response response) {
        contentLength = response.getContentLengthLong();
        remaining = contentLength;
    }


    /**
     * Set the next buffer in the filter pipeline.
     */
    @Override
    public void setBuffer(OutputBuffer buffer) {
        this.buffer = buffer;
    }


    /**
     * End the current request. It is acceptable to write extra bytes using
     * buffer.doWrite during the execution of this method.
     */
    @Override
    public long end()
        throws IOException {

        if (remaining > 0)
            return remaining;
        return 0;

    }


    /**
     * Make the filter ready to process the next request.
     */
    @Override
    public void recycle() {
        contentLength = -1;
        remaining = 0;
    }
}
