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

/**
 * @deprecated  Will be removed in Tomcat 8.0.x.
 */
@Deprecated
public class UpgradeNioProcessor extends UpgradeProcessor<NioChannel> {

    private final NioChannel nioChannel;
    private final NioSelectorPool pool;
    private final int maxRead;
    private final int maxWrite;

    public UpgradeNioProcessor(SocketWrapper<NioChannel> wrapper,
            UpgradeInbound upgradeInbound, NioSelectorPool pool) {
        super(upgradeInbound);

        wrapper.setTimeout(upgradeInbound.getReadTimeout());

        this.nioChannel = wrapper.getSocket();
        this.pool = pool;
        this.maxRead = nioChannel.getBufHandler().getReadBuffer().capacity();
        this.maxWrite = nioChannel.getBufHandler().getWriteBuffer().capacity();
    }


    /*
     * Output methods
     */
    @Override
    public void flush() throws IOException {
        NioEndpoint.KeyAttachment att =
                (NioEndpoint.KeyAttachment) nioChannel.getAttachment();
        if (att == null) {
            throw new IOException("Key must be cancelled");
        }
        long writeTimeout = att.getTimeout();
        Selector selector = null;
        try {
            selector = pool.get();
        } catch ( IOException x ) {
            //ignore
        }
        try {
            do {
                if (nioChannel.flush(true, selector, writeTimeout)) {
                    break;
                }
            } while (true);
        } finally {
            if (selector != null) {
                pool.put(selector);
            }
        }
    }

    @Override
    public void write(int b) throws IOException {
        writeToSocket(new byte[] {(byte) b}, 0, 1);
    }

    @Override
    public void write(byte[]b, int off, int len) throws IOException {
        int written = 0;
        while (len - written > maxWrite) {
            written += writeToSocket(b, off + written, maxWrite);
        }
        writeToSocket(b, off + written, len - written);
    }

    /*
     * Input methods
     */
    @Override
    public int read() throws IOException {
        byte[] bytes = new byte[1];
        int result = readSocket(true, bytes, 0, 1);
        if (result == -1) {
            return -1;
        } else {
            return bytes[0] & 0xFF;
        }
    }

    @Override
    public int read(boolean block, byte[] bytes, int off, int len)
            throws IOException {
        if (len > maxRead) {
            return readSocket(block, bytes, off, maxRead);
        } else {
            return readSocket(block, bytes, off, len);
        }
    }


    /*
     * Adapted from the NioInputBuffer.
     */
    private int readSocket(boolean block, byte[] bytes, int offset, int len)
            throws IOException {

        ByteBuffer readBuffer = nioChannel.getBufHandler().getReadBuffer();
        int remaining = readBuffer.remaining();

        // Is there enough data in the read buffer to satisfy this request?
        if (remaining >= len) {
            readBuffer.get(bytes, offset, len);
            return len;
        }

        // Copy what data there is in the read buffer to the byte array
        int leftToWrite = len;
        int newOffset = offset;
        if (remaining > 0) {
            readBuffer.get(bytes, offset, remaining);
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
            readBuffer.limit(nRead);
            if (nRead > leftToWrite) {
                readBuffer.get(bytes, newOffset, leftToWrite);
                leftToWrite = 0;
            } else {
                readBuffer.get(bytes, newOffset, nRead);
                leftToWrite -= nRead;
            }
        } else if (nRead == 0) {
            readBuffer.flip();
            readBuffer.limit(nRead);
        } else if (nRead == -1) {
            throw new EOFException(sm.getString("nio.eof.error"));
        }

        return len - leftToWrite;
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
                        (NioEndpoint.KeyAttachment) nioChannel.getAttachment();
                if (att == null) {
                    throw new IOException("Key must be cancelled.");
                }
                nRead = pool.read(nioChannel.getBufHandler().getReadBuffer(),
                        nioChannel, selector, att.getTimeout());
            } catch (EOFException eof) {
                nRead = -1;
            } finally {
                if (selector != null) {
                    pool.put(selector);
                }
            }
        } else {
            nRead = nioChannel.read(nioChannel.getBufHandler().getReadBuffer());
        }
        return nRead;
    }

    /*
     * Adapted from the NioOutputBuffer
     */
    private synchronized int writeToSocket(byte[] bytes, int off, int len)
            throws IOException {

        nioChannel.getBufHandler().getWriteBuffer().clear();
        nioChannel.getBufHandler().getWriteBuffer().put(bytes, off, len);
        nioChannel.getBufHandler().getWriteBuffer().flip();

        int written = 0;
        NioEndpoint.KeyAttachment att =
                (NioEndpoint.KeyAttachment) nioChannel.getAttachment();
        if (att == null) {
            throw new IOException("Key must be cancelled");
        }
        long writeTimeout = att.getTimeout();
        Selector selector = null;
        try {
            selector = pool.get();
        } catch ( IOException x ) {
            //ignore
        }
        try {
            written = pool.write(nioChannel.getBufHandler().getWriteBuffer(),
                    nioChannel, selector, writeTimeout, true);
        } finally {
            if (selector != null) {
                pool.put(selector);
            }
        }
        return written;
    }
}
