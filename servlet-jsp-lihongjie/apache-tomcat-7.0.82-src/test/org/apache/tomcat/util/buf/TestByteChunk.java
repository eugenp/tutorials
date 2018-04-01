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

package org.apache.tomcat.util.buf;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test cases for {@link ByteChunk}.
 */
public class TestByteChunk {

    @Test
    public void testConvertToBytes() throws UnsupportedEncodingException {
        String string = "HTTP/1.1 100 Continue\r\n";
        byte[] bytes = ByteChunk.convertToBytes(string);
        byte[] expected = string.getBytes("ISO-8859-1");
        assertTrue(Arrays.equals(bytes, expected));
    }

    /**
     * Test for {@code findByte} vs. {@code indexOf} methods difference.
     *
     * <p>
     * As discussed in the "Re: r944918" thread on dev@, {@code
     * ByteChunk.indexOf()} works for 0-127 ASCII chars only, and cannot find
     * any chars outside of the range. {@code ByteChunk.findByte()} works for
     * any ISO-8859-1 chars.
     */
    @Test
    public void testFindByte() throws UnsupportedEncodingException {
        // 0xa0 = 160 = &nbsp; character
        byte[] bytes = "Hello\u00a0world".getBytes("ISO-8859-1");
        final int len = bytes.length;

        // indexOf() does not work outside of 0-127
        assertEquals(5, ByteChunk.findByte(bytes, 0, len, (byte) '\u00a0'));
        assertEquals(-1, ByteChunk.indexOf(bytes, 0, len, '\u00a0'));

        assertEquals(0, ByteChunk.findByte(bytes, 0, len, (byte) 'H'));
        assertEquals(0, ByteChunk.indexOf(bytes, 0, len, 'H'));

        assertEquals(len - 1, ByteChunk.findByte(bytes, 0, len, (byte) 'd'));
        assertEquals(len - 1, ByteChunk.indexOf(bytes, 0, len, 'd'));

        assertEquals(-1, ByteChunk.findByte(bytes, 0, len, (byte) 'x'));
        assertEquals(-1, ByteChunk.indexOf(bytes, 0, len, 'x'));

        assertEquals(7, ByteChunk.findByte(bytes, 5, len, (byte) 'o'));
        assertEquals(7, ByteChunk.indexOf(bytes, 5, len, 'o'));

        assertEquals(-1, ByteChunk.findByte(bytes, 2, 5, (byte) 'w'));
        assertEquals(-1, ByteChunk.indexOf(bytes, 5, 5, 'w'));
    }

    @Test
    public void testIndexOf_Char() throws UnsupportedEncodingException {
        byte[] bytes = "Hello\u00a0world".getBytes("ISO-8859-1");
        final int len = bytes.length;

        ByteChunk bc = new ByteChunk();
        bc.setBytes(bytes, 0, len);

        assertEquals(0, bc.indexOf('H', 0));
        assertEquals(6, bc.indexOf('w', 0));

        // Does not work outside of 0-127
        assertEquals(-1, bc.indexOf('\u00a0', 0));

        bc.setBytes(bytes, 6, 5);
        assertEquals(1, bc.indexOf('o', 0));

        bc.setBytes(bytes, 6, 2);
        assertEquals(0, bc.indexOf('w', 0));
        assertEquals(-1, bc.indexOf('d', 0));
    }

    @Test
    public void testIndexOf_String() throws UnsupportedEncodingException {
        byte[] bytes = "Hello\u00a0world".getBytes("ISO-8859-1");
        final int len = bytes.length;

        ByteChunk bc = new ByteChunk();
        bc.setBytes(bytes, 0, len);

        assertEquals(0, bc.indexOf("Hello", 0, "Hello".length(), 0));
        assertEquals(2, bc.indexOf("ll", 0, 2, 0));
        assertEquals(2, bc.indexOf("Hello", 2, 2, 0));

        assertEquals(7, bc.indexOf("o", 0, 1, 5));

        // Does not work outside of 0-127
        assertEquals(-1, bc.indexOf("\u00a0", 0, 1, 0));

        bc.setBytes(bytes, 6, 5);
        assertEquals(1, bc.indexOf("o", 0, 1, 0));

        bc.setBytes(bytes, 6, 2);
        assertEquals(0, bc.indexOf("wo", 0, 1, 0));
        assertEquals(-1, bc.indexOf("d", 0, 1, 0));
    }

    @Test
    public void testFindBytes() throws UnsupportedEncodingException {
        byte[] bytes = "Hello\u00a0world".getBytes("ISO-8859-1");
        final int len = bytes.length;

        assertEquals(0, ByteChunk.findBytes(bytes, 0, len, new byte[] { 'H' }));
        assertEquals(5, ByteChunk.findBytes(bytes, 0, len, new byte[] {
                (byte) '\u00a0', 'x' }));
        assertEquals(5, ByteChunk.findBytes(bytes, 0, len - 4, new byte[] {
                'x', (byte) '\u00a0' }));
        assertEquals(len - 1, ByteChunk.findBytes(bytes, 2, len, new byte[] {
                'x', 'd' }));
        assertEquals(1, ByteChunk.findBytes(bytes, 0, len, new byte[] { 'o',
                'e' }));
        assertEquals(-1, ByteChunk.findBytes(bytes, 2, 5, new byte[] { 'w' }));
    }

    @Test
    @Deprecated
    public void testFindNotBytes() throws UnsupportedEncodingException {
        byte[] bytes = "Hello\u00a0world".getBytes("ISO-8859-1");
        final int len = bytes.length;

        assertEquals(4, ByteChunk.findNotBytes(bytes, 0, len, new byte[] { 'l',
                'e', 'H' }));
        assertEquals(-1, ByteChunk.findNotBytes(bytes, 0, len, bytes));
        assertEquals(-1, ByteChunk.findNotBytes(bytes, 2, 3, new byte[] { 'l',
                'e', 'H' }));
    }
}
