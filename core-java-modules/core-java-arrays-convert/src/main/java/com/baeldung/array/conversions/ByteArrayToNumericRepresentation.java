package com.baeldung.array.conversions;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Conversion;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

class ByteArrayToNumericRepresentation {

    static int convertByteArrayToIntUsingShiftOperator(byte[] bytes) {
        int value = 0;
        for (byte b : bytes) {
            value = (value << 8) + (b & 0xFF);
        }
        return value;
    }

    static byte[] convertIntToByteArrayUsingShiftOperator(int value) {
        byte[] bytes = new byte[Integer.BYTES];
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            bytes[length - i - 1] = (byte) (value & 0xFF);
            value >>= 8;
        }
        return bytes;
    }

    static long convertByteArrayToLongUsingShiftOperator(byte[] bytes) {
        long value = 0;
        for (byte b : bytes) {
            value <<= 8;
            value |= (b & 0xFF);
        }
        return value;
    }

    static byte[] convertLongToByteArrayUsingShiftOperator(long value) {
        byte[] bytes = new byte[Long.BYTES];
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            bytes[length - i - 1] = (byte) (value & 0xFF);
            value >>= 8;
        }
        return bytes;
    }

    static float convertByteArrayToFloatUsingShiftOperator(byte[] bytes) {
        // convert bytes to int
        int intValue = 0;
        for (byte b : bytes) {
            intValue = (intValue << 8) + (b & 0xFF);
        }

        // convert int to float
        return Float.intBitsToFloat(intValue);
    }

    static byte[] convertFloatToByteArrayUsingShiftOperator(float value) {
        // convert float to int
        int intValue = Float.floatToIntBits(value);

        // convert int to bytes
        byte[] bytes = new byte[Float.BYTES];
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            bytes[length - i - 1] = (byte) (intValue & 0xFF);
            intValue >>= 8;
        }
        return bytes;
    }

    static double convertingByteArrayToDoubleUsingShiftOperator(byte[] bytes) {
        long longValue = 0;
        for (byte b : bytes) {
            longValue = (longValue << 8) + (b & 0xFF);
        }

        return Double.longBitsToDouble(longValue);
    }

    static byte[] convertDoubleToByteArrayUsingShiftOperator(double value) {
        long longValue = Double.doubleToLongBits(value);

        byte[] bytes = new byte[Double.BYTES];
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            bytes[length - i - 1] = (byte) (longValue & 0xFF);
            longValue >>= 8;
        }
        return bytes;
    }

    static int convertByteArrayToIntUsingByteBuffer(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.put(bytes);
        buffer.rewind();
        return buffer.getInt();
    }

    static byte[] convertIntToByteArrayUsingByteBuffer(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(value);
        buffer.rewind();
        return buffer.array();
    }

    static long convertByteArrayToLongUsingByteBuffer(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.rewind();
        return buffer.getLong();
    }

    static byte[] convertLongToByteArrayUsingByteBuffer(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(value);
        buffer.rewind();
        return buffer.array();
    }

    static float convertByteArrayToFloatUsingByteBuffer(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        buffer.put(bytes);
        buffer.rewind();
        return buffer.getFloat();
    }

    static byte[] convertFloatToByteArrayUsingByteBuffer(float value) {
        ByteBuffer buffer = ByteBuffer.allocate(Float.BYTES);
        buffer.putFloat(value);
        buffer.rewind();
        return buffer.array();
    }

    static double convertByteArrayToDoubleUsingByteBuffer(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.put(bytes);
        buffer.rewind();
        return buffer.getDouble();
    }

    static byte[] convertDoubleToByteArrayUsingByteBuffer(double value) {
        ByteBuffer buffer = ByteBuffer.allocate(Double.BYTES);
        buffer.putDouble(value);
        buffer.rewind();
        return buffer.array();
    }

    static int convertByteArrayToIntUsingBigInteger(byte[] bytes) {
        return new BigInteger(bytes).intValue();
    }

    static byte[] convertIntToByteArrayUsingBigInteger(int value) {
        return BigInteger.valueOf(value).toByteArray();
    }

    static long convertByteArrayToLongUsingBigInteger(byte[] bytes) {
        return new BigInteger(bytes).longValue();
    }

    static byte[] convertLongToByteArrayUsingBigInteger(long value) {
        return BigInteger.valueOf(value).toByteArray();
    }

    static float convertByteArrayToFloatUsingBigInteger(byte[] bytes) {
        int intValue = new BigInteger(bytes).intValue();
        return Float.intBitsToFloat(intValue);
    }

    static byte[] convertFloatToByteArrayUsingBigInteger(float value) {
        int intValue = Float.floatToIntBits(value);
        return BigInteger.valueOf(intValue).toByteArray();
    }

    static double convertByteArrayToDoubleUsingBigInteger(byte[] bytes) {
        long longValue = new BigInteger(bytes).longValue();
        return Double.longBitsToDouble(longValue);
    }

    static byte[] convertDoubleToByteArrayUsingBigInteger(double value) {
        long longValue = Double.doubleToLongBits(value);
        return BigInteger.valueOf(longValue).toByteArray();
    }

    static int convertingByteArrayToIntUsingGuava(byte[] bytes) {
        return Ints.fromByteArray(bytes);
    }

    static byte[] convertIntToByteArrayUsingGuava(int value) {
        return Ints.toByteArray(value);
    }

    static long convertByteArrayToLongUsingGuava(byte[] bytes) {
        return Longs.fromByteArray(bytes);
    }

    static byte[] convertLongToByteArrayUsingGuava(long value) {
        return Longs.toByteArray(value);
    }

    static float convertByteArrayToFloatUsingGuava(byte[] bytes) {
        int intValue = Ints.fromByteArray(bytes);
        return Float.intBitsToFloat(intValue);
    }

    static byte[] convertFloatToByteArrayUsingGuava(float value) {
        int intValue = Float.floatToIntBits(value);
        return Ints.toByteArray(intValue);
    }

    static double convertByteArrayToDoubleUsingGuava(byte[] bytes) {
        long longValue = Longs.fromByteArray(bytes);
        return Double.longBitsToDouble(longValue);
    }

    static byte[] convertDoubleToByteArrayUsingGuava(double value) {
        long longValue = Double.doubleToLongBits(value);
        return Longs.toByteArray(longValue);
    }

    static int convertByteArrayToIntUsingCommonsLang(byte[] bytes) {
        byte[] copyBytes = Arrays.copyOf(bytes, bytes.length);
        ArrayUtils.reverse(copyBytes);
        return Conversion.byteArrayToInt(copyBytes, 0, 0, 0, copyBytes.length);
    }

    static byte[] convertIntToByteArrayUsingCommonsLang(int value) {
        byte[] bytes = new byte[Integer.BYTES];
        Conversion.intToByteArray(value, 0, bytes, 0, bytes.length);
        ArrayUtils.reverse(bytes);
        return bytes;
    }

    static long convertByteArrayToLongUsingCommonsLang(byte[] bytes) {
        byte[] copyBytes = Arrays.copyOf(bytes, bytes.length);
        ArrayUtils.reverse(copyBytes);
        return Conversion.byteArrayToLong(copyBytes, 0, 0, 0, copyBytes.length);
    }

    static byte[] convertLongToByteArrayUsingCommonsLang(long value) {
        byte[] bytes = new byte[Long.BYTES];
        Conversion.longToByteArray(value, 0, bytes, 0, bytes.length);
        ArrayUtils.reverse(bytes);
        return bytes;
    }

    static float convertByteArrayToFloatUsingCommonsLang(byte[] bytes) {
        byte[] copyBytes = Arrays.copyOf(bytes, bytes.length);
        ArrayUtils.reverse(copyBytes);
        int intValue = Conversion.byteArrayToInt(copyBytes, 0, 0, 0, copyBytes.length);
        return Float.intBitsToFloat(intValue);
    }

    static byte[] convertFloatToByteArrayUsingCommonsLang(float value) {
        int intValue = Float.floatToIntBits(value);
        byte[] bytes = new byte[Float.BYTES];
        Conversion.intToByteArray(intValue, 0, bytes, 0, bytes.length);
        ArrayUtils.reverse(bytes);
        return bytes;
    }

    static double convertByteArrayToDoubleUsingCommonsLang(byte[] bytes) {
        byte[] copyBytes = Arrays.copyOf(bytes, bytes.length);
        ArrayUtils.reverse(copyBytes);
        long longValue = Conversion.byteArrayToLong(copyBytes, 0, 0, 0, copyBytes.length);
        return Double.longBitsToDouble(longValue);
    }

    static byte[] convertDoubleToByteArrayUsingCommonsLang(double value) {
        long longValue = Double.doubleToLongBits(value);
        byte[] bytes = new byte[Long.BYTES];
        Conversion.longToByteArray(longValue, 0, bytes, 0, bytes.length);
        ArrayUtils.reverse(bytes);
        return bytes;
    }

}