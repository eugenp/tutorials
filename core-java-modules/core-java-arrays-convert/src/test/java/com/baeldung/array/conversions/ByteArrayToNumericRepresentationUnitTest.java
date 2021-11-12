package com.baeldung.array.conversions;

import org.junit.Test;

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


    @Test
    public void givenShiftOperator_whenConvertingByteArrayToInt_thenSuccess() {
        int value = convertByteArrayToIntUsingShiftOperator(INT_BYTE_ARRAY);

        assertEquals(INT_VALUE, value);
    }

    @Test
    public void givenShiftOperator_whenConvertingIntToByteArray_thenSuccess() {
        byte[] bytes = convertIntToByteArrayUsingShiftOperator(INT_VALUE);

        assertArrayEquals(INT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenShiftOperator_whenConvertingByteArrayToLong_thenSuccess() {
        long value = convertByteArrayToLongUsingShiftOperator(LONG_BYTE_ARRAY);

        assertEquals(LONG_VALUE, value);
    }

    @Test
    public void givenShiftOperator_whenConvertingLongToByteArray_thenSuccess() {
        byte[] bytes = convertLongToByteArrayUsingShiftOperator(LONG_VALUE);

        assertArrayEquals(LONG_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenShiftOperator_whenConvertingByteArrayToFloat_thenSuccess() {
        float value = convertByteArrayToFloatUsingShiftOperator(FLOAT_BYTE_ARRAY);

        assertEquals(Float.floatToIntBits(FLOAT_VALUE), Float.floatToIntBits(value));
    }

    @Test
    public void givenShiftOperator_whenConvertingFloatToByteArray_thenSuccess() {
        byte[] bytes = convertFloatToByteArrayUsingShiftOperator(FLOAT_VALUE);

        assertArrayEquals(FLOAT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenShiftOperator_whenConvertingByteArrayToDouble_thenSuccess() {
        double value = convertingByteArrayToDoubleUsingShiftOperator(DOUBLE_BYTE_ARRAY);

        assertEquals(Double.doubleToLongBits(DOUBLE_VALUE), Double.doubleToLongBits(value));
    }

    @Test
    public void givenShiftOperator_whenConvertingDoubleToByteArray_thenSuccess() {
        byte[] bytes = convertDoubleToByteArrayUsingShiftOperator(DOUBLE_VALUE);

        assertArrayEquals(DOUBLE_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenByteBuffer_whenConvertingByteArrayToInt_thenSuccess() {
        int value = convertByteArrayToIntUsingByteBuffer(INT_BYTE_ARRAY);

        assertEquals(INT_VALUE, value);
    }

    @Test
    public void givenByteBuffer_whenConvertingIntToByteArray_thenSuccess() {
        byte[] bytes = convertIntToByteArrayUsingByteBuffer(INT_VALUE);

        assertArrayEquals(INT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenByteBuffer_whenConvertingByteArrayToLong_thenSuccess() {
        long value = convertByteArrayToLongUsingByteBuffer(LONG_BYTE_ARRAY);

        assertEquals(LONG_VALUE, value);
    }

    @Test
    public void givenByteBuffer_whenConvertingLongToByteArray_thenSuccess() {
        byte[] bytes = convertLongToByteArrayUsingByteBuffer(LONG_VALUE);

        assertArrayEquals(LONG_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenByteBuffer_whenConvertingByteArrayToFloat_thenSuccess() {
        float value = convertByteArrayToFloatUsingByteBuffer(FLOAT_BYTE_ARRAY);

        assertEquals(Float.floatToIntBits(FLOAT_VALUE), Float.floatToIntBits(value));
    }

    @Test
    public void givenByteBuffer_whenConvertingFloatToByteArray_thenSuccess() {
        byte[] bytes = convertFloatToByteArrayUsingByteBuffer(FLOAT_VALUE);

        assertArrayEquals(FLOAT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenByteBuffer_whenConvertingByteArrayToDouble_thenSuccess() {
        double value = convertByteArrayToDoubleUsingByteBuffer(DOUBLE_BYTE_ARRAY);

        assertEquals(Double.doubleToLongBits(DOUBLE_VALUE), Double.doubleToLongBits(value));
    }

    @Test
    public void givenByteBuffer_whenConvertingDoubleToByteArray_thenSuccess() {
        byte[] bytes = convertDoubleToByteArrayUsingByteBuffer(DOUBLE_VALUE);

        assertArrayEquals(DOUBLE_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenBigInteger_whenConvertingByteArrayToInt_thenSuccess() {
        int value = convertByteArrayToIntUsingBigInteger(INT_BYTE_ARRAY);

        assertEquals(INT_VALUE, value);
    }

    @Test
    public void givenBigInteger_whenConvertingIntToByteArray_thenSuccess() {
        byte[] bytes = convertIntToByteArrayUsingBigInteger(INT_VALUE);

        assertArrayEquals(INT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenBigInteger_whenConvertingByteArrayToLong_thenSuccess() {
        long value = convertByteArrayToLongUsingBigInteger(LONG_BYTE_ARRAY);

        assertEquals(LONG_VALUE, value);
    }

    @Test
    public void givenBigInteger_whenConvertingLongToByteArray_thenSuccess() {
        byte[] bytes = convertLongToByteArrayUsingBigInteger(LONG_VALUE);

        assertArrayEquals(LONG_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenBigInteger_whenConvertingByteArrayToFloat_thenSuccess() {
        float value = convertByteArrayToFloatUsingBigInteger(FLOAT_BYTE_ARRAY);

        assertEquals(Float.floatToIntBits(FLOAT_VALUE), Float.floatToIntBits(value));
    }

    @Test
    public void givenBigInteger_whenConvertingFloatToByteArray_thenSuccess() {
        byte[] bytes = convertFloatToByteArrayUsingBigInteger(FLOAT_VALUE);

        assertArrayEquals(FLOAT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenBigInteger_whenConvertingByteArrayToDouble_thenSuccess() {
        double value = convertByteArrayToDoubleUsingBigInteger(DOUBLE_BYTE_ARRAY);

        assertEquals(Double.doubleToLongBits(DOUBLE_VALUE), Double.doubleToLongBits(value));
    }

    @Test
    public void givenBigInteger_whenConvertingDoubleToByteArray_thenSuccess() {
        byte[] bytes = convertDoubleToByteArrayUsingBigInteger(DOUBLE_VALUE);

        assertArrayEquals(DOUBLE_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenGuava_whenConvertingByteArrayToInt_thenSuccess() {
        int value = convertingByteArrayToIntUsingGuava(INT_BYTE_ARRAY);

        assertEquals(INT_VALUE, value);
    }

    @Test
    public void givenGuava_whenConvertingIntToByteArray_thenSuccess() {
        byte[] bytes = convertIntToByteArrayUsingGuava(INT_VALUE);

        assertArrayEquals(INT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenGuava_whenConvertingByteArrayToLong_thenSuccess() {
        long value = convertByteArrayToLongUsingGuava(LONG_BYTE_ARRAY);

        assertEquals(LONG_VALUE, value);
    }

    @Test
    public void givenGuava_whenConvertingLongToByteArray_thenSuccess() {
        byte[] bytes = convertLongToByteArrayUsingGuava(LONG_VALUE);

        assertArrayEquals(LONG_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenGuava_whenConvertingByteArrayToFloat_thenSuccess() {
        float value = convertByteArrayToFloatUsingGuava(FLOAT_BYTE_ARRAY);

        assertEquals(Float.floatToIntBits(FLOAT_VALUE), Float.floatToIntBits(value));
    }

    @Test
    public void givenGuava_whenConvertingFloatToByteArray_thenSuccess() {
        byte[] bytes = convertFloatToByteArrayUsingGuava(FLOAT_VALUE);

        assertArrayEquals(FLOAT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenGuava_whenConvertingByteArrayToDouble_thenSuccess() {
        double value = convertByteArrayToDoubleUsingGuava(DOUBLE_BYTE_ARRAY);

        assertEquals(Double.doubleToLongBits(DOUBLE_VALUE), Double.doubleToLongBits(value));
    }

    @Test
    public void givenGuava_whenConvertingDoubleToByteArray_thenSuccess() {
        byte[] bytes = convertDoubleToByteArrayUsingGuava(DOUBLE_VALUE);

        assertArrayEquals(DOUBLE_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenCommonsLang_whenConvertingByteArrayToInt_thenSuccess() {
        int value = convertByteArrayToIntUsingCommonsLang(INT_BYTE_ARRAY);

        assertEquals(INT_VALUE, value);
    }

    @Test
    public void givenCommonsLang_whenConvertingIntToByteArray_thenSuccess() {
        byte[] bytes = convertIntToByteArrayUsingCommonsLang(INT_VALUE);

        assertArrayEquals(INT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenCommonsLang_whenConvertingByteArrayToLong_thenSuccess() {
        long value = convertByteArrayToLongUsingCommonsLang(LONG_BYTE_ARRAY);

        assertEquals(LONG_VALUE, value);
    }

    @Test
    public void givenCommonsLang_whenConvertingLongToByteArray_thenSuccess() {
        byte[] bytes = convertLongToByteArrayUsingCommonsLang(LONG_VALUE);

        assertArrayEquals(LONG_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenCommonsLang_whenConvertingByteArrayToFloat_thenSuccess() {
        float value = convertByteArrayToFloatUsingCommonsLang(FLOAT_BYTE_ARRAY);

        assertEquals(Float.floatToIntBits(FLOAT_VALUE), Float.floatToIntBits(value));
    }

    @Test
    public void givenCommonsLang_whenConvertingFloatToByteArray_thenSuccess() {
        byte[] bytes = convertFloatToByteArrayUsingCommonsLang(FLOAT_VALUE);

        assertArrayEquals(FLOAT_BYTE_ARRAY, bytes);
    }

    @Test
    public void givenCommonsLang_whenConvertingByteArrayToDouble_thenSuccess() {
        double value = convertByteArrayToDoubleUsingCommonsLang(DOUBLE_BYTE_ARRAY);

        assertEquals(Double.doubleToLongBits(DOUBLE_VALUE), Double.doubleToLongBits(value));
    }

    @Test
    public void givenCommonsLang_whenConvertingDoubleToByteArray_thenSuccess() {
        byte[] bytes = convertDoubleToByteArrayUsingCommonsLang(DOUBLE_VALUE);

        assertArrayEquals(DOUBLE_BYTE_ARRAY, bytes);
    }

}