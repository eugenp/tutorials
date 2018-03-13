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
package org.apache.catalina.websocket;

import java.io.IOException;
import java.io.InputStream;

import org.apache.coyote.http11.upgrade.UpgradeProcessor;
import org.apache.tomcat.util.res.StringManager;

/**
 * This class is used to read WebSocket frames from the underlying socket and
 * makes the payload available for reading as an {@link InputStream}. It only
 * makes the number of bytes declared in the payload length available for
 * reading even if more bytes are available from the socket.
 * 
 * @deprecated  Replaced by the JSR356 WebSocket 1.1 implementation and will be
 *              removed in Tomcat 8.0.x.  
 */
@Deprecated
public class WsInputStream extends InputStream {

    private static final StringManager sm =
            StringManager.getManager(Constants.Package);


    private final UpgradeProcessor<?> processor;
    private final WsOutbound outbound;

    private WsFrame frame;
    private long remaining;
    private long readThisFragment;

    private String error = null;


    public WsInputStream(UpgradeProcessor<?> processor, WsOutbound outbound) {
        this.processor = processor;
        this.outbound = outbound;
    }


    /**
     * Process the next WebSocket frame.
     *
     * @param block Should this method block until a frame is presented if no
     *              data is currently available to process. Note that if a
     *              single byte is available, this method will block until the
     *              complete frame (excluding payload for non-control frames) is
     *              available.
     *
     * @return  The next frame to be processed or <code>null</code> if block is
     *          <code>false</code> and there is no data to be processed.
     *
     * @throws IOException  If a problem occurs reading the data for the frame.
     */
    public WsFrame nextFrame(boolean block) throws IOException {
        frame = WsFrame.nextFrame(processor, block);
        if (frame != null) {
            readThisFragment = 0;
            remaining = frame.getPayLoadLength();
        }
        return frame;
    }


    // ----------------------------------------------------- InputStream methods

    @Override
    public int read() throws IOException {

        makePayloadDataAvailable();

        if (remaining == 0) {
            return -1;
        }

        remaining--;
        readThisFragment++;

        int masked = processor.read();
        if(masked == -1) {
            return -1;
        }
        return masked ^
                (frame.getMask()[(int) ((readThisFragment - 1) % 4)] & 0xFF);
    }


    @Override
    public int read(byte b[], int off, int len) throws IOException {

        makePayloadDataAvailable();

        if (remaining == 0) {
            return -1;
        }

        if (len > remaining) {
            len = (int) remaining;
        }
        int result = processor.read(true, b, off, len);
        if(result == -1) {
            return -1;
        }

        for (int i = off; i < off + result; i++) {
            b[i] = (byte) (b[i] ^
                    frame.getMask()[(int) ((readThisFragment + i - off) % 4)]);
        }
        remaining -= result;
        readThisFragment += result;
        return result;
    }


    /*
     * Ensures that there is payload data ready to read.
     */
    private void makePayloadDataAvailable() throws IOException {
        if (error != null) {
            throw new IOException(error);
        }
        while (remaining == 0 && !frame.getFin()) {
            // Need more data - process next frame
            nextFrame(true);
            while (frame.isControl()) {
                if (frame.getOpCode() == Constants.OPCODE_PING) {
                    outbound.pong(frame.getPayLoad());
                } else if (frame.getOpCode() == Constants.OPCODE_PONG) {
                    // NO-OP. Swallow it.
                } else if (frame.getOpCode() == Constants.OPCODE_CLOSE) {
                    outbound.close(frame);
                } else{
                    throw new IOException(sm.getString("is.unknownOpCode",
                            Byte.valueOf(frame.getOpCode())));
                }
                nextFrame(true);
            }
            if (frame.getOpCode() != Constants.OPCODE_CONTINUATION) {
                error = sm.getString("is.notContinuation",
                        Byte.valueOf(frame.getOpCode()));
                throw new IOException(error);
            }
        }
    }
}
