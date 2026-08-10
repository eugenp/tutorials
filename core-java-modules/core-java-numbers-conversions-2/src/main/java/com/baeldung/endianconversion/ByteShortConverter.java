package com.baeldung.endianconversion;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.stream.IntStream;

public final class ByteShortConverter {

    private ByteShortConverter() {
    }

    public static byte[] shortsToBytesBigEndian(short[] shorts) {
        ByteBuffer buffer = ByteBuffer.allocate(shorts.length * 2).order(ByteOrder.BIG_ENDIAN);
        buffer.asShortBuffer().put(shorts);
        return buffer.array();
    }

    public static byte[] shortsToBytesBigEndianUsingLoop(short[] shorts) {
        byte[] bytes = new byte[shorts.length * 2];
        for (int i = 0; i < shorts.length; i++) {
            short value = shorts[i];
            bytes[2 * i] = (byte) ((value >>> 8) & 0xFF);
            bytes[2 * i + 1] = (byte) (value & 0xFF);
        }
        return bytes;
    }

    public static byte[] shortsToBytesLittleEndian(short[] shorts) {
        ByteBuffer buffer = ByteBuffer.allocate(shorts.length * 2).order(ByteOrder.LITTLE_ENDIAN);
        buffer.asShortBuffer().put(shorts);
        return buffer.array();
    }

    public static byte[] shortsToBytesLittleEndianUsingLoop(short[] shorts) {
        byte[] bytes = new byte[shorts.length * 2];
        for (int i = 0; i < shorts.length; i++) {
            short value = shorts[i];
            bytes[2 * i] = (byte) (value & 0xFF);
            bytes[2 * i + 1] = (byte) ((value >>> 8) & 0xFF);
        }
        return bytes;
    }

    public static short[] bytesToShortsBigEndian(byte[] bytes) {
        short[] shorts = new short[bytes.length / 2];
        ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asShortBuffer().get(shorts);
        return shorts;
    }

    public static short[] bytesToShortsBigEndianUsingLoop(byte[] bytes) {
        int n = bytes.length / 2;
        short[] shorts = new short[n];
        for (int i = 0; i < n; i++) {
            shorts[i] = (short) (((bytes[2 * i] & 0xFF) << 8) | (bytes[2 * i + 1] & 0xFF));
        }
        return shorts;
    }

    public static short[] bytesToShortsBigEndianUsingStream(byte[] bytes) {
        int n = bytes.length / 2;
        short[] shorts = new short[n];
        IntStream.range(0, n).forEach(i ->
            shorts[i] = (short) (((bytes[2 * i] & 0xFF) << 8) | (bytes[2 * i + 1] & 0xFF)));
        return shorts;
    }

    public static short[] bytesToShortsLittleEndianUsingLoop(byte[] bytes) {
        int n = bytes.length / 2;
        short[] shorts = new short[n];
        for (int i = 0; i < n; i++) {
            shorts[i] = (short) ((bytes[2 * i] & 0xFF) | ((bytes[2 * i + 1] & 0xFF) << 8));
        }
        return shorts;
    }

    public static short[] bytesToShortsLittleEndian(byte[] bytes) {
        short[] shorts = new short[bytes.length / 2];
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
        return shorts;
    }
}
