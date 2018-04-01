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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPInputStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.apache.catalina.util.IOTools;

/**
 * Reproduces what current appears to be a JVM bug. Note: This test case is not
 * part of the Standard test suite that is execute by <code>ant test</code>.
 */
public class TestFlushableGZIPOutputStream {

    @Test
    public void testBug52121() throws Exception {

        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        OutputStream output = new FlushableGZIPOutputStream(byteOutStream);

        File sourcesDir = new File("test/org/apache/coyote/http11/filters/");
        List<byte[]> parts = new ArrayList<byte[]>();
        byte[] part;

        part = loadFile(new File(sourcesDir, "bug52121-part1"));
        parts.add(part);
        flowBytes(part, output);
        output.flush();

        part = loadFile(new File(sourcesDir, "bug52121-part2"));
        parts.add(part);
        flowBytes(part, output);
        output.flush();

        part = "data2".getBytes("ASCII");
        parts.add(part);
        output.write(part);
        output.flush();

        output.close();

        ByteArrayInputStream byteInStream =
                new ByteArrayInputStream(byteOutStream.toByteArray());

        GZIPInputStream inflaterStream = new GZIPInputStream(byteInStream);
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        try {
            IOTools.flow(inflaterStream, sink);
        } finally {
            sink.close();
        }

        byte[] decompressedBytes = sink.toByteArray();
        int originalLength = 0;
        for (byte[] bytes : parts) {
            assertArrayEquals(bytes, Arrays.copyOfRange(decompressedBytes,
                    originalLength, originalLength + bytes.length));
            originalLength += bytes.length;
        }
        assertEquals(originalLength, decompressedBytes.length);
    }

    /**
     * Test for {@code write(int)}.
     */
    @Test
    public void testWriteChar() throws Exception {
        String phrase = "Apache Tomcat "
                + "\u0410\u043f\u0430\u0447\u0435 \u0422\u043e\u043c\u043a\u0430\u0442 ";
        byte[] data = phrase.getBytes("UTF-8");

        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        OutputStream output = new FlushableGZIPOutputStream(byteOutStream);

        output.write(data);
        for (int i=0; i<data.length; i++) {
            output.write(data[i]);
        }
        output.flush();
        for (int i=0; i<data.length; i++) {
            output.write(data[i]);
        }
        output.write(data);
        output.close();

        ByteArrayInputStream byteInStream =
                new ByteArrayInputStream(byteOutStream.toByteArray());

        GZIPInputStream inflaterStream = new GZIPInputStream(byteInStream);
        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        try {
            IOTools.flow(inflaterStream, sink);
        } finally {
            sink.close();
        }

        byte[] decompressedBytes = sink.toByteArray();
        assertEquals(data.length * 4, decompressedBytes.length);
        for (int i = 0; i < 4; i++) {
            assertArrayEquals(data, Arrays.copyOfRange(decompressedBytes,
                    data.length * i, data.length * (i + 1)));
        }
    }

    /**
     * Loads file into memory.
     */
    private byte[] loadFile(File file) throws IOException {
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        FileInputStream input = new FileInputStream(file);
        try {
            IOTools.flow(input, byteOutStream);
        } finally {
            input.close();
        }
        return byteOutStream.toByteArray();
    }

    /**
     * Writes data to the stream and returns the size of the file.
     */
    private void flowBytes(byte[] bytes, OutputStream output)
            throws IOException {
        // Could use output.write(), but IOTools writes in small portions, and
        // that is more natural
        ByteArrayInputStream byteInStream = new ByteArrayInputStream(bytes);
        try {
            IOTools.flow(byteInStream, output);
        } finally {
            byteInStream.close();
        }
    }
}
