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
import java.nio.ByteBuffer;

import org.apache.coyote.ActionCode;
import org.apache.coyote.OutputBuffer;
import org.apache.coyote.Response;
import org.apache.tomcat.jni.Socket;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.http.HttpMessages;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.net.SocketWrapper;

/**
 * Output buffer.
 *
 * @author <a href="mailto:remm@apache.org">Remy Maucherat</a>
 */
public class InternalAprOutputBuffer extends AbstractOutputBuffer<Long> {


    // ----------------------------------------------------------- Constructors

    /**
     * Default constructor.
     */
    public InternalAprOutputBuffer(Response response, int headerBufferSize) {

        this.response = response;

        buf = new byte[headerBufferSize];
        if (headerBufferSize < (8 * 1024)) {
            bbuf = ByteBuffer.allocateDirect(6 * 1500);
        } else {
            bbuf = ByteBuffer.allocateDirect((headerBufferSize / 1500 + 1) * 1500);
        }

        outputStreamOutputBuffer = new SocketOutputBuffer();

        filterLibrary = new OutputFilter[0];
        activeFilters = new OutputFilter[0];
        lastActiveFilter = -1;

        committed = false;
        finished = false;

        // Cause loading of HttpMessages
        HttpMessages.getInstance(response.getLocale()).getMessage(200);

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Underlying socket.
     */
    private long socket;


    /**
     * Direct byte buffer used for writing.
     */
    private ByteBuffer bbuf = null;


    // --------------------------------------------------------- Public Methods

    @Override
    public void init(SocketWrapper<Long> socketWrapper,
            AbstractEndpoint<Long> endpoint) throws IOException {

        socket = socketWrapper.getSocket().longValue();
        Socket.setsbb(this.socket, bbuf);
    }


    /**
     * Flush the response.
     *
     * @throws IOException an underlying I/O error occurred
     */
    @Override
    public void flush()
        throws IOException {

        super.flush();

        // Flush the current buffer
        flushBuffer();
    }


    /**
     * Recycle the output buffer. This should be called when closing the
     * connection.
     */
    @Override
    public void recycle() {

        super.recycle();

        bbuf.clear();
    }


    /**
     * End request.
     *
     * @throws IOException an underlying I/O error occurred
     */
    @Override
    public void endRequest()
        throws IOException {

        if (!committed) {

            // Send the connector a request for commit. The connector should
            // then validate the headers, send them (using sendHeader) and
            // set the filters accordingly.
            response.action(ActionCode.COMMIT, null);

        }

        if (finished)
            return;

        if (lastActiveFilter != -1)
            activeFilters[lastActiveFilter].end();

        flushBuffer();

        finished = true;

    }


    // ------------------------------------------------ HTTP/1.1 Output Methods


    /**
     * Send an acknowledgment.
     */
    @Override
    public void sendAck()
        throws IOException {

        if (!committed) {
            if (Socket.send(socket, Constants.ACK_BYTES, 0, Constants.ACK_BYTES.length) < 0)
                throw new IOException(sm.getString("iib.failedwrite"));
        }

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Commit the response.
     *
     * @throws IOException an underlying I/O error occurred
     */
    @Override
    protected void commit() throws IOException {

        // The response is now committed
        committed = true;
        response.setCommitted(true);

        if (pos > 0) {
            // Sending the response header buffer
            bbuf.put(buf, 0, pos);
        }

    }


    /**
     * Callback to write data from the buffer.
     */
    private void flushBuffer()
        throws IOException {
        if (bbuf.position() > 0) {
            if (Socket.sendbb(socket, 0, bbuf.position()) < 0) {
                throw new IOException();
            }
            bbuf.clear();
        }
    }


    // ----------------------------------- OutputStreamOutputBuffer Inner Class


    /**
     * This class is an output buffer which will write data to an output
     * stream.
     */
    protected class SocketOutputBuffer implements OutputBuffer {


        /**
         * Write chunk.
         */
        @Override
        public int doWrite(ByteChunk chunk, Response res) throws IOException {
            try {
                int len = chunk.getLength();
                int start = chunk.getStart();
                byte[] b = chunk.getBuffer();
                while (len > 0) {
                    int thisTime = len;
                    if (bbuf.position() == bbuf.capacity()) {
                        flushBuffer();
                    }
                    if (thisTime > bbuf.capacity() - bbuf.position()) {
                        thisTime = bbuf.capacity() - bbuf.position();
                    }
                    bbuf.put(b, start, thisTime);
                    len = len - thisTime;
                    start = start + thisTime;
                }
                byteCount += chunk.getLength();
                return chunk.getLength();
            } catch (IOException ioe) {
                response.action(ActionCode.CLOSE_NOW, ioe);
                // Re-throw
                throw ioe;
            }
        }

        @Override
        public long getBytesWritten() {
            return byteCount;
        }
    }
}
