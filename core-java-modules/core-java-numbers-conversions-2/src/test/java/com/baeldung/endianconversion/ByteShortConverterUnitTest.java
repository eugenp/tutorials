package com.baeldung.endianconversion;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ByteShortConverterUnitTest {

    @Test
    void givenBytes_whenBytesToShortsBigEndian_thenCorrectValues() {
        byte[] bytes = new byte[] { 0x7F, 0x1B, 0x10, 0x11 };
        short[] shorts = ByteShortConverter.bytesToShortsBigEndian(bytes);

        assertEquals(2, shorts.length);
        assertEquals(32539, shorts[0]);
        assertEquals(4113, shorts[1]);
    }

    @Test
    void givenBytes_whenBytesToShortsLittleEndian_thenCorrectValues() {
        byte[] bytes = new byte[] { 0x1B, 0x7F, 0x11, 0x10 };
        short[] shorts = ByteShortConverter.bytesToShortsLittleEndian(bytes);

        assertEquals(2, shorts.length);
        assertEquals(32539, shorts[0]);
        assertEquals(4113, shorts[1]);
    }

    @Test
    void givenEmptyBytes_whenBytesToShorts_thenEmptyResult() {
        byte[] emptyBytes = new byte[0];
        short[] shorts = ByteShortConverter.bytesToShortsBigEndian(emptyBytes);
        assertEquals(0, shorts.length);
    }

    @Test
    void givenBigEndianBytes_whenBytesToShortsBigEndianUsingLoop_thenSameAsByteBuffer() {
        byte[] bytes = new byte[] { 0x7F, 0x1B, 0x10, 0x11 };
        short[] loopResult = ByteShortConverter.bytesToShortsBigEndianUsingLoop(bytes);
        short[] bufferResult = ByteShortConverter.bytesToShortsBigEndian(bytes);
        assertArrayEquals(bufferResult, loopResult);
    }

    @Test
    void givenBigEndianBytes_whenBytesToShortsBigEndianUsingStream_thenSameAsByteBuffer() {
        byte[] bytes = new byte[] { 0x7F, 0x1B, 0x10, 0x11 };
        short[] streamResult = ByteShortConverter.bytesToShortsBigEndianUsingStream(bytes);
        short[] bufferResult = ByteShortConverter.bytesToShortsBigEndian(bytes);
        assertArrayEquals(bufferResult, streamResult);
    }

    @Test
    void givenLittleEndianBytes_whenBytesToShortsLittleEndianUsingLoop_thenSameAsByteBuffer() {
        byte[] bytes = new byte[] { 0x1B, 0x7F, 0x11, 0x10 };
        short[] loopResult = ByteShortConverter.bytesToShortsLittleEndianUsingLoop(bytes);
        short[] bufferResult = ByteShortConverter.bytesToShortsLittleEndian(bytes);
        assertArrayEquals(bufferResult, loopResult);
    }

    @Test
    void givenNegativeBytes_whenBytesToShortsBigEndian_thenCorrectValues() {
        byte[] bytes = new byte[] { (byte) 0x80, 0x00, (byte) 0xFF, (byte) 0xFE };
        short[] shorts = ByteShortConverter.bytesToShortsBigEndian(bytes);
        assertEquals((short) 0x8000, shorts[0]);
        assertEquals((short) 0xFFFE, shorts[1]);
    }

    @Test
    void givenShorts_whenShortsToBytesBigEndian_thenCorrectByteOrder() {
        short[] shorts = new short[] { (short) 0x7F1B, (short) 0x1011 };
        byte[] bytes = ByteShortConverter.shortsToBytesBigEndian(shorts);
        assertArrayEquals(new byte[] { 0x7F, 0x1B, 0x10, 0x11 }, bytes);
    }

    @Test
    void givenShorts_whenShortsToBytesLittleEndian_thenCorrectByteOrder() {
        short[] shorts = new short[] { (short) 0x7F1B, (short) 0x1011 };
        byte[] bytes = ByteShortConverter.shortsToBytesLittleEndian(shorts);
        assertArrayEquals(new byte[] { 0x1B, 0x7F, 0x11, 0x10 }, bytes);
    }

    @Test
    void givenEmptyShorts_whenShortsToBytes_thenEmptyResult() {
        short[] emptyShorts = new short[0];
        assertArrayEquals(new byte[0], ByteShortConverter.shortsToBytesBigEndian(emptyShorts));
        assertArrayEquals(new byte[0], ByteShortConverter.shortsToBytesLittleEndian(emptyShorts));
    }

    @Test
    void givenBigEndianShorts_whenShortsToBytesBigEndianUsingLoop_thenSameAsByteBuffer() {
        short[] shorts = new short[] { (short) 0x7F1B, (short) 0x1011, (short) 0x8000, (short) 0xFFFE };
        byte[] loopResult = ByteShortConverter.shortsToBytesBigEndianUsingLoop(shorts);
        byte[] bufferResult = ByteShortConverter.shortsToBytesBigEndian(shorts);
        assertArrayEquals(bufferResult, loopResult);
    }

    @Test
    void givenLittleEndianShorts_whenShortsToBytesLittleEndianUsingLoop_thenSameAsByteBuffer() {
        short[] shorts = new short[] { (short) 0x7F1B, (short) 0x1011, (short) 0x8000, (short) 0xFFFE };
        byte[] loopResult = ByteShortConverter.shortsToBytesLittleEndianUsingLoop(shorts);
        byte[] bufferResult = ByteShortConverter.shortsToBytesLittleEndian(shorts);
        assertArrayEquals(bufferResult, loopResult);
    }

    @Test
    void givenShorts_whenShortsToBytesAndBack_thenRoundTrips() {
        short[] shorts = new short[] { (short) 0x7F1B, (short) 0x1011, (short) 0x8000, (short) 0xFFFE };

        byte[] bigEndianBytes = ByteShortConverter.shortsToBytesBigEndianUsingLoop(shorts);
        assertArrayEquals(shorts, ByteShortConverter.bytesToShortsBigEndianUsingLoop(bigEndianBytes));

        byte[] littleEndianBytes = ByteShortConverter.shortsToBytesLittleEndianUsingLoop(shorts);
        assertArrayEquals(shorts, ByteShortConverter.bytesToShortsLittleEndianUsingLoop(littleEndianBytes));
    }
}
