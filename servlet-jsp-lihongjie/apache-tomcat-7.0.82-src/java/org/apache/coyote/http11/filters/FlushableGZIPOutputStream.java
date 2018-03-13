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
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

/**
 * Extension of {@link GZIPOutputStream} to workaround for a couple of long
 * standing JDK bugs
 * (<a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4255743">Bug
 * 4255743</a> and
 * <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4813885">Bug
 * 4813885</a>) so the GZIP'd output can be flushed. 
 */
public class FlushableGZIPOutputStream extends GZIPOutputStream {
    public FlushableGZIPOutputStream(OutputStream os) throws IOException {
        super(os);
    }

    /**
     * It is used to reserve one byte of real data so that it can be used when
     * flushing the stream.
     */
    private byte[] lastByte = new byte[1];
    private boolean hasLastByte = false;

    /**
     * Flag that compression has to be re-enabled before the next write
     * operation.
     */
    private boolean flagReenableCompression = false;

    @Override
    public void write(byte[] bytes) throws IOException {
        write(bytes, 0, bytes.length);
    }

    @Override
    public synchronized void write(byte[] bytes, int offset, int length)
            throws IOException {
        if (length > 0) {
            flushLastByte();
            if (length > 1) {
                reenableCompression();
                super.write(bytes, offset, length - 1);
            }
            rememberLastByte(bytes[offset + length - 1]);
        }
    }

    @Override
    public synchronized void write(int i) throws IOException {
        flushLastByte();
        rememberLastByte((byte) i);
    }

    @Override
    public synchronized void finish() throws IOException {
        try {
            flushLastByte();
        } catch (IOException ignore) {
            // If our write failed, then trailer write in finish() will fail
            // with IOException as well, but it will leave Deflater in more
            // consistent state.
        }
        super.finish();
    }

    @Override
    public synchronized void close() throws IOException {
        try {
            flushLastByte();
        } catch (IOException ignored) {
            // Ignore. As OutputStream#close() says, the contract of close()
            // is to close the stream. It does not matter much if the
            // stream is not writable any more.
        }
        super.close();
    }

    private void reenableCompression() {
        if (flagReenableCompression && !def.finished()) {
            flagReenableCompression = false;
            def.setLevel(Deflater.DEFAULT_COMPRESSION);
        }
    }

    private void rememberLastByte(byte b) {
        lastByte[0] = b;
        hasLastByte = true;
    }

    private void flushLastByte() throws IOException {
        if (hasLastByte) {
            reenableCompression();
            // Clear the flag first, because write() may fail
            hasLastByte = false;
            super.write(lastByte, 0, 1);
        }
    }

    @Override
    public synchronized void flush() throws IOException {
        if (hasLastByte) {
            // - do not allow the gzip header to be flushed on its own
            // - do not do anything if there is no data to send

            // trick the deflater to flush
            /**
             * Now this is tricky: We force the Deflater to flush its data by
             * switching compression level. As yet, a perplexingly simple workaround
             * for
             * http://developer.java.sun.com/developer/bugParade/bugs/4255743.html
             */
            if (!def.finished()) {
                def.setLevel(Deflater.NO_COMPRESSION);
                flushLastByte();
                flagReenableCompression = true;
            }
        }
        out.flush();
    }

    /*
     * Keep on calling deflate until it runs dry. The default implementation
     * only does it once and can therefore hold onto data when they need to be
     * flushed out.
     */
    @Override
    protected void deflate() throws IOException {
        int len;
        do {
            len = def.deflate(buf, 0, buf.length);
            if (len > 0) {
                out.write(buf, 0, len);
            }
        } while (len != 0);
    }
}
