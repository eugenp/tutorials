package com.baeldung.shorttobytearray;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.Assert.assertArrayEquals;

public class ShortToByteArrayUnitTest {
    short shortValue = 12345;
    byte[] expectedByteArray = {48, 57};

    @Test
    public void givenShort_whenUsingByteBuffer_thenConvertToByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(shortValue);
        byte[] byteArray = buffer.array();

        assertArrayEquals(expectedByteArray, byteArray);
    }

    @Test
    public void givenShort_whenUsingDataOutputStream_thenConvertToByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeShort(shortValue);
        dos.close();

        byte[] byteArray = baos.toByteArray();

        assertArrayEquals(expectedByteArray, byteArray);
    }

    @Test
    public void givenShort_whenUsingManualBitManipulation_thenConvertToByteArray() {
        byte[] byteArray = new byte[2];
        byteArray[0] = (byte) (shortValue >> 8);
        byteArray[1] = (byte) shortValue;

        assertArrayEquals(expectedByteArray, byteArray);
    }
}
