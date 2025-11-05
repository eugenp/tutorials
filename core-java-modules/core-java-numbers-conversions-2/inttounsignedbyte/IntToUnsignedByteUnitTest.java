package com.baeldung.inttounsignedbyte;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntToUnsignedByteUnitTest {
    int value = 200;

    @Test
    public void givenInt_whenUsingTypeCastingAndBitMasking_thenConvertToUnsignedByte() {
        byte unsignedByte = (byte) (value & 0xFF);

        assertEquals(value, Byte.toUnsignedInt(unsignedByte));
    }

    @Test
    public void givenIntInRange_whenUsingByteBuffer_thenConvertToUnsignedByte() {
        int value = 200;
        ByteBuffer buffer = ByteBuffer.allocate(4).putInt(value);
        byte unsignedByte = buffer.array()[3];

        assertEquals(value, Byte.toUnsignedInt(unsignedByte));
    }
}
