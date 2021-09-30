package com.baeldung.array.conversions;

import org.junit.Test;

import java.util.Arrays;

import static com.baeldung.array.conversions.ByteArrayToNumericRepresentation.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ByteArrayToNumericRepresentationUnitTest {
    private static final byte[] INT_BYTE_ARRAY = new byte[]{
            (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE
    };
    private static final int INT_VALUE = 0xCAFEBABE;


    private static final byte[] FLOAT_BYTE_ARRAY = new byte[]{
            (byte) 0x40, (byte) 0x48, (byte) 0xF5, (byte) 0xC3
    };
    private static final float FLOAT_VALUE = 3.14F;


    private static final byte[] LONG_BYTE_ARRAY = new byte[]{
            (byte) 0x01, (byte) 0x23, (byte) 0x45, (byte) 0x67,
            (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF
    };
    private static final long LONG_VALUE = 0x0123456789ABCDEFL;


    private static final byte[] DOUBLE_BYTE_ARRAY = new byte[]{
            (byte) 0x3F, (byte) 0xE3, (byte) 0xC6, (byte) 0xA7,
            (byte) 0xEF, (byte) 0x9D, (byte) 0xB2, (byte) 0x2D
    };
    private static final double DOUBLE_VALUE = 0.618D;


    private int getIntValue() {
        return INT_VALUE;
    }

    private byte[] getIntByteArray() {
        return Arrays.copyOf(INT_BYTE_ARRAY, INT_BYTE_ARRAY.length);
    }

    private long getLongValue() {
        return LONG_VALUE;
    }

    private byte[] getLongByteArray() {
        return Arrays.copyOf(LONG_BYTE_ARRAY, LONG_BYTE_ARRAY.length);
    }

    private float getFloatValue() {
        return FLOAT_VALUE;
    }

    private byte[] getFloatByteArray() {
        return Arrays.copyOf(FLOAT_BYTE_ARRAY, FLOAT_BYTE_ARRAY.length);
    }

    private double getDoubleValue() {
        return DOUBLE_VALUE;
    }

    private byte[] getDoubleByteArray() {
        return Arrays.copyOf(DOUBLE_BYTE_ARRAY, DOUBLE_BYTE_ARRAY.length);
    }


    @Test
    public void givenShiftOperator_whenConvertingByteArrayToInt_thenSuccess() {
        byte[] bytes = getIntByteArray();
        int value = convertByteArrayToIntUsingShiftOperator(bytes);

        assertEquals(INT_VALUE, value);
    }

    @Test
    public void givenShiftOperator_whenConvertingIntToByteArray_thenSuccess() {
        int value = getIntValue();
        byte[] bytes = convertIntToByteArrayUsingShiftOperator(value);

        assertArrayEquals(INT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenShiftOperator_whenConvertingByteArrayToLong_thenSuccess() {
        byte[] bytes = getLongByteArray();
        long value = convertByteArrayToLongUsingShiftOperator(bytes);

        assertEquals(LONG_VALUE, value);
    }

    @Test
    public void givenShiftOperator_whenConvertingLongToByteArray_thenSuccess() {
        long value = getLongValue();
        byte[] bytes = convertLongToByteArrayUsingShiftOperator(value);

        assertArrayEquals(LONG_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenShiftOperator_whenConvertingByteArrayToFloat_thenSuccess() {
        byte[] bytes = getFloatByteArray();
        float value = convertByteArrayToFloatUsingShiftOperator(bytes);

        assertEquals(Float.floatToIntBits(FLOAT_VALUE), Float.floatToIntBits(value));
    }

    @Test
    public void givenShiftOperator_whenConvertingFloatToByteArray_thenSuccess() {
        float value = getFloatValue();
        byte[] bytes = convertFloatToByteArrayUsingShiftOperator(value);

        assertArrayEquals(FLOAT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenShiftOperator_whenConvertingByteArrayToDouble_thenSuccess() {
        byte[] bytes = getDoubleByteArray();
        double value = convertingByteArrayToDoubleUsingShiftOperator(bytes);

        assertEquals(Double.doubleToLongBits(DOUBLE_VALUE), Double.doubleToLongBits(value));
    }

    @Test
    public void givenShiftOperator_whenConvertingDoubleToByteArray_thenSuccess() {
        double value = getDoubleValue();
        byte[] bytes = convertDoubleToByteArrayUsingShiftOperator(value);

        assertArrayEquals(DOUBLE_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenByteBuffer_whenConvertingByteArrayToInt_thenSuccess() {
        byte[] bytes = getIntByteArray();
        int value = convertByteArrayToIntUsingByteBuffer(bytes);

        assertEquals(INT_VALUE, value);
    }

    @Test
    public void givenByteBuffer_whenConvertingIntToByteArray_thenSuccess() {
        int value = getIntValue();
        byte[] bytes = convertIntToByteArrayUsingByteBuffer(value);

        assertArrayEquals(INT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenByteBuffer_whenConvertingByteArrayToLong_thenSuccess() {
        byte[] bytes = getLongByteArray();
        long value = convertByteArrayToLongUsingByteBuffer(bytes);

        assertEquals(LONG_VALUE, value);
    }

    @Test
    public void givenByteBuffer_whenConvertingLongToByteArray_thenSuccess() {
        long value = getLongValue();
        byte[] bytes = convertLongToByteArrayUsingByteBuffer(value);

        assertArrayEquals(LONG_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenByteBuffer_whenConvertingByteArrayToFloat_thenSuccess() {
        byte[] bytes = getFloatByteArray();
        float value = convertByteArrayToFloatUsingByteBuffer(bytes);

        assertEquals(Float.floatToIntBits(FLOAT_VALUE), Float.floatToIntBits(value));
    }

    @Test
    public void givenByteBuffer_whenConvertingFloatToByteArray_thenSuccess() {
        float value = getFloatValue();
        byte[] bytes = convertFloatToByteArrayUsingByteBuffer(value);

        assertArrayEquals(FLOAT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenByteBuffer_whenConvertingByteArrayToDouble_thenSuccess() {
        byte[] bytes = getDoubleByteArray();
        double value = convertByteArrayToDoubleUsingByteBuffer(bytes);

        assertEquals(Double.doubleToLongBits(DOUBLE_VALUE), Double.doubleToLongBits(value));
    }

    @Test
    public void givenByteBuffer_whenConvertingDoubleToByteArray_thenSuccess() {
        double value = getDoubleValue();
        byte[] bytes = convertDoubleToByteArrayUsingByteBuffer(value);

        assertArrayEquals(DOUBLE_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenBigInteger_whenConvertingByteArrayToInt_thenSuccess() {
        byte[] bytes = getIntByteArray();
        int value = convertByteArrayToIntUsingBigInteger(bytes);

        assertEquals(INT_VALUE, value);
    }

    @Test
    public void givenBigInteger_whenConvertingIntToByteArray_thenSuccess() {
        int value = getIntValue();
        byte[] bytes = convertIntToByteArrayUsingBigInteger(value);

        assertArrayEquals(INT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenBigInteger_whenConvertingByteArrayToLong_thenSuccess() {
        byte[] bytes = getLongByteArray();
        long value = convertByteArrayToLongUsingBigInteger(bytes);

        assertEquals(LONG_VALUE, value);
    }

    @Test
    public void givenBigInteger_whenConvertingLongToByteArray_thenSuccess() {
        long value = getLongValue();
        byte[] bytes = convertLongToByteArrayUsingBigInteger(value);

        assertArrayEquals(LONG_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenBigInteger_whenConvertingByteArrayToFloat_thenSuccess() {
        byte[] bytes = getFloatByteArray();
        float value = convertByteArrayToFloatUsingBigInteger(bytes);

        assertEquals(Float.floatToIntBits(FLOAT_VALUE), Float.floatToIntBits(value));
    }

    @Test
    public void givenBigInteger_whenConvertingFloatToByteArray_thenSuccess() {
        float value = getFloatValue();
        byte[] bytes = convertFloatToByteArrayUsingBigInteger(value);

        assertArrayEquals(FLOAT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenBigInteger_whenConvertingByteArrayToDouble_thenSuccess() {
        byte[] bytes = getDoubleByteArray();
        double value = convertByteArrayToDoubleUsingBigInteger(bytes);

        assertEquals(Double.doubleToLongBits(DOUBLE_VALUE), Double.doubleToLongBits(value));
    }

    @Test
    public void givenBigInteger_whenConvertingDoubleToByteArray_thenSuccess() {
        double value = getDoubleValue();
        byte[] bytes = convertDoubleToByteArrayUsingBigInteger(value);

        assertArrayEquals(DOUBLE_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenGuava_whenConvertingByteArrayToInt_thenSuccess() {
        byte[] bytes = getIntByteArray();
        int value = convertingByteArrayToIntUsingGuava(bytes);

        assertEquals(INT_VALUE, value);
    }

    @Test
    public void givenGuava_whenConvertingIntToByteArray_thenSuccess() {
        int value = getIntValue();
        byte[] bytes = convertIntToByteArrayUsingGuava(value);

        assertArrayEquals(INT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenGuava_whenConvertingByteArrayToLong_thenSuccess() {
        byte[] bytes = getLongByteArray();
        long value = convertByteArrayToLongUsingGuava(bytes);

        assertEquals(LONG_VALUE, value);
    }

    @Test
    public void givenGuava_whenConvertingLongToByteArray_thenSuccess() {
        long value = getLongValue();
        byte[] bytes = convertLongToByteArrayUsingGuava(value);

        assertArrayEquals(LONG_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenGuava_whenConvertingByteArrayToFloat_thenSuccess() {
        byte[] bytes = getFloatByteArray();
        float value = convertByteArrayToFloatUsingGuava(bytes);

        assertEquals(Float.floatToIntBits(FLOAT_VALUE), Float.floatToIntBits(value));
    }

    @Test
    public void givenGuava_whenConvertingFloatToByteArray_thenSuccess() {
        float value = getFloatValue();
        byte[] bytes = convertFloatToByteArrayUsingGuava(value);

        assertArrayEquals(FLOAT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenGuava_whenConvertingByteArrayToDouble_thenSuccess() {
        byte[] bytes = getDoubleByteArray();
        double value = convertByteArrayToDoubleUsingGuava(bytes);

        assertEquals(Double.doubleToLongBits(DOUBLE_VALUE), Double.doubleToLongBits(value));
    }

    @Test
    public void givenGuava_whenConvertingDoubleToByteArray_thenSuccess() {
        double value = getDoubleValue();
        byte[] bytes = convertDoubleToByteArrayUsingGuava(value);

        assertArrayEquals(DOUBLE_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenCommonsLang_whenConvertingByteArrayToInt_thenSuccess() {
        byte[] bytes = getIntByteArray();
        int value = convertByteArrayToIntUsingCommonsLang(bytes);

        assertEquals(INT_VALUE, value);
    }

    @Test
    public void givenCommonsLang_whenConvertingIntToByteArray_thenSuccess() {
        int value = getIntValue();
        byte[] bytes = convertIntToByteArrayUsingCommonsLang(value);

        assertArrayEquals(INT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenCommonsLang_whenConvertingByteArrayToLong_thenSuccess() {
        byte[] bytes = getLongByteArray();
        long value = convertByteArrayToLongUsingCommonsLang(bytes);

        assertEquals(LONG_VALUE, value);
    }

    @Test
    public void givenCommonsLang_whenConvertingLongToByteArray_thenSuccess() {
        long value = getLongValue();
        byte[] bytes = convertLongToByteArrayUsingCommonsLang(value);

        assertArrayEquals(LONG_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenCommonsLang_whenConvertingByteArrayToFloat_thenSuccess() {
        byte[] bytes = getFloatByteArray();
        float value = convertByteArrayToFloatUsingCommonsLang(bytes);

        assertEquals(Float.floatToIntBits(FLOAT_VALUE), Float.floatToIntBits(value));
    }

    @Test
    public void givenCommonsLang_whenConvertingFloatToByteArray_thenSuccess() {
        float value = getFloatValue();
        byte[] bytes = convertFloatToByteArrayUsingCommonsLang(value);

        assertArrayEquals(FLOAT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenCommonsLang_whenConvertingByteArrayToDouble_thenSuccess() {
        byte[] bytes = getDoubleByteArray();
        double value = convertByteArrayToDoubleUsingCommonsLang(bytes);

        assertEquals(Double.doubleToLongBits(DOUBLE_VALUE), Double.doubleToLongBits(value));
    }

    @Test
    public void givenCommonsLang_whenConvertingDoubleToByteArray_thenSuccess() {
        double value = getDoubleValue();
        byte[] bytes = convertDoubleToByteArrayUsingCommonsLang(value);

        assertArrayEquals(DOUBLE_BYTE_ARRAY, bytes);
    }

}