package com.baeldung.array.conversions;

import static com.baeldung.array.conversions.FloatToByteArray.byteArrayToFloat;
import static com.baeldung.array.conversions.FloatToByteArray.byteArrayToFloatWithByteBuffer;
import static com.baeldung.array.conversions.FloatToByteArray.floatToByteArray;
import static com.baeldung.array.conversions.FloatToByteArray.floatToByteArrayWithByteBuffer;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class FloatToByteArrayUnitTest {

    @Test
    public void givenAFloat_thenConvertToByteArray() {
        assertArrayEquals(new byte[] { 63, -116, -52, -51}, floatToByteArray(1.1f));
    }

    @Test
    public void givenAByteArray_thenConvertToFloat() {
        assertEquals(1.1f, byteArrayToFloat(new byte[] { 63, -116, -52, -51}), 0);
    }

    @Test
    public void givenAFloat_thenConvertToByteArrayUsingByteBuffer() {
        assertArrayEquals(new byte[] { 63, -116, -52, -51}, floatToByteArrayWithByteBuffer(1.1f));
    }

    @Test
    public void givenAByteArray_thenConvertToFloatUsingByteBuffer() {
        assertEquals(1.1f, byteArrayToFloatWithByteBuffer(new byte[] { 63, -116, -52, -51}), 0);
    }

    @Test
    public void givenAFloat_thenConvertToByteArray_thenConvertToFloat() {
        float floatToConvert = 200.12f;
        byte[] byteArray = floatToByteArray(floatToConvert);
        assertEquals(200.12f, byteArrayToFloat(byteArray), 0);
    }
    
    @Test
    public void givenAFloat_thenConvertToByteArrayWithByteBuffer_thenConvertToFloatWithByteBuffer() {
        float floatToConvert = 30100.42f;
        byte[] byteArray = floatToByteArrayWithByteBuffer(floatToConvert);
        assertEquals(30100.42f, byteArrayToFloatWithByteBuffer(byteArray), 0);
    }
}
