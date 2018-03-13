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
package org.apache.coyote.http11.upgrade;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

import org.apache.tomcat.util.net.NioChannel;
import org.apache.tomcat.util.net.NioEndpoint;
import org.apache.tomcat.util.net.NioSelectorPool;
import org.apache.tomcat.util.net.SocketWrapper;

public class NioServletInputStream extends AbstractServletInputStream {

    private final NioChannel channel;
    private final NioSelectorPool pool;

    public NioServletInputStream(SocketWrapper<NioChannel> wrapper,
            NioSelectorPool pool) {
        this.channel = wrapper.getSocket();
        this.pool = pool;
    }

    @Override
    protected boolean doIsReady() throws IOException {
        ByteBuffer readBuffer = channel.getBufHandler().getReadBuffer();

        if (readBuffer.remaining() > 0) {
            return true;
        }

        readBuffer.clear();
        fillReadBuffer(false);

        boolean isReady = readBuffer.position() > 0;
        readBuffer.flip();
        return isReady;
    }

    @Override
    protected int doRead(boolean block, byte[] b, int off, int len)
            throws IOException {

        ByteBuffer readBuffer = channel.getBufHandler().getReadBuffer();
        int remaining = readBuffer.remaining();

        // Is there enough data in the read buffer to satisfy this request?
        if (remaining >= len) {
            readBuffer.get(b, off, len);
            return len;
        }

        // Copy what data there is in the read buffer to the byte array
        int leftToWrite = len;
        int newOffset = off;
        if (remaining > 0) {
            readBuffer.get(b, off, remaining);
            leftToWrite -= remaining;
            newOffset += remaining;
        }

        // Fill the read buffer as best we can
        readBuffer.clear();
        int nRead = fillReadBuffer(block);

        // Full as much of the remaining byte array as possible with the data
        // that was just read
        if (nRead > 0) {
            readBuffer.flip();
            if (nRead > leftToWrite) {
                readBuffer.get(b, newOffset, leftToWrite);
                leftToWrite = 0;
            } else {
                readBuffer.get(b, newOffset, nRead);
                leftToWrite -= nRead;
            }
        } else if (nRead == 0) {
            readBuffer.flip();
        } else if (nRead == -1) {
            // TODO i18n
            throw new EOFException();
        }

        return len - leftToWrite;
    }



    @Override
    protected void doClose() throws IOException {
        channel.close();
    }


    private int fillReadBuffer(boolean block) throws IOException {
        int nRead;
        if (block) {
            Selector selector = null;
            try {
                selector = pool.get();
            } catch ( IOException x ) {
                // Ignore
            }
            try {
                NioEndpoint.KeyAttachment att =
                        (NioEndpoint.KeyAttachment) channel.getAttachment();
                if (att == null) {
                    throw new IOException("Key must be cancelled.");
                }
                nRead = pool.read(channel.getBufHandler().getReadBuffer(),
                        channel, selector, att.getTimeout());
            } catch (EOFException eof) {
                nRead = -1;
            } finally {
                if (selector != null) {
                    pool.put(selector);
                }
            }
        } else {
            nRead = channel.read(channel.getBufHandler().getReadBuffer());
        }
        return nRead;
    }
}
