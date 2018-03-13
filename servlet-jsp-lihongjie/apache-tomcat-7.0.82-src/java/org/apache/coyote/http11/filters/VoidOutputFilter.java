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
 * Void output filter, which silently swallows bytes written. Used with a 204
 * status (no content) or a HEAD request.
 * 
 * @author Remy Maucherat
 */
public class VoidOutputFilter implements OutputFilter {


    // --------------------------------------------------- OutputBuffer Methods


    /**
     * Write some bytes.
     * 
     * @return number of bytes written by the filter
     */
    @Override
    public int doWrite(ByteChunk chunk, Response res)
        throws IOException {

        return chunk.getLength();

    }


    @Override
    public long getBytesWritten() {
        return 0;
    }


    // --------------------------------------------------- OutputFilter Methods


    /**
     * Some filters need additional parameters from the response. All the 
     * necessary reading can occur in that method, as this method is called
     * after the response header processing is complete.
     */
    @Override
    public void setResponse(Response response) {
        // NOOP: No need for parameters from response in this filter
    }


    /**
     * Set the next buffer in the filter pipeline.
     */
    @Override
    public void setBuffer(OutputBuffer buffer) {
        // NO-OP
    }


    /**
     * Make the filter ready to process the next request.
     */
    @Override
    public void recycle() {
        // NOOP: Nothing to recycle
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


}
