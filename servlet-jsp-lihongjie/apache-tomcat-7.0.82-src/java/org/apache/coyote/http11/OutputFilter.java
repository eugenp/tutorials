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

package org.apache.coyote.http11;

import java.io.IOException;

import org.apache.coyote.OutputBuffer;
import org.apache.coyote.Response;
import org.apache.tomcat.util.buf.ByteChunk;

/**
 * Output filter.
 * 
 * @author Remy Maucherat
 */
public interface OutputFilter extends OutputBuffer {


    /**
     * Write some bytes.
     * 
     * @return number of bytes written by the filter
     */
    @Override
    public int doWrite(ByteChunk chunk, Response unused)
        throws IOException;


    /**
     * Some filters need additional parameters from the response. All the 
     * necessary reading can occur in that method, as this method is called
     * after the response header processing is complete.
     */
    public void setResponse(Response response);


    /**
     * Make the filter ready to process the next request.
     */
    public void recycle();


    /**
     * Set the next buffer in the filter pipeline.
     */
    public void setBuffer(OutputBuffer buffer);


    /**
     * End the current request. It is acceptable to write extra bytes using
     * buffer.doWrite during the execution of this method.
     * 
     * @return Should return 0 unless the filter does some content length 
     * delimitation, in which case the number is the amount of extra bytes or
     * missing bytes, which would indicate an error. 
     * Note: It is recommended that extra bytes be swallowed by the filter.
     */
    public long end()
        throws IOException;


}
