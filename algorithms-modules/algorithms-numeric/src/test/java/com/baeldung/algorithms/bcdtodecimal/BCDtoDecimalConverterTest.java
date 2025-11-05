package com.baeldung.algorithms.bcdtodecimal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BCDtoDecimalConverterTest {

    // 1. Tests for convertPackedByte(byte bcdByte)

    @Test
    void testConvertPackedByteValidValues() {
        // Test 05 (0x05) ->
        assertEquals(5, BCDtoDecimalConverter.convertPackedByte((byte) 0x05));

        // Test 22 (0x22) -> 22
        assertEquals(22, BCDtoDecimalConverter.convertPackedByte((byte) 0x22));

        // Test 97 (0x97) -> 97
        assertEquals(97, BCDtoDecimalConverter.convertPackedByte((byte) 0x097));
    }

    @Test
    void testConvertPackedByteInvalidUpperNibbleThrowsException() {
        // Test Upper nibble is A (1010), Lower nibble is 1 (0001) -> 0xA1
        byte invalidByte = (byte) 0xA1;
        assertThrows(IllegalArgumentException.class, () -> BCDtoDecimalConverter.convertPackedByte(invalidByte),
            "Received non-BCD upper nibble (A). Provide valid BCD nibbles (0-9).");
    }

    @Test
    void testConvertPackedByteBothInvalidThrowsException() {
        // test Upper nibble is B, Lower nibble is E -> 0xBE
        byte invalidByte = (byte) 0xBE;
        assertThrows(IllegalArgumentException.class,
            () -> BCDtoDecimalConverter.convertPackedByte(invalidByte),
            "Received both nibbles as non-BCD. Provide valid BCD nibbles (0-9)."
        );
    }

    // -------------------------------------------------------------------------

    // 2. Tests for convertPackedByteArray(byte[] bcdArray)

    @Test
    void testConvertPackedByteArrayValidValues() {
        // Test 0 -> [0x00]
        assertEquals(0L, BCDtoDecimalConverter.convertPackedByteArray(new byte[]{(byte) 0x00}));

        // Test 99 -> [0x99]
        assertEquals(99L, BCDtoDecimalConverter.convertPackedByteArray(new byte[]{(byte) 0x99}));

        // Test 1234 -> [0x12, 0x34]
        byte[] bcd1234 = {(byte) 0x12, (byte) 0x34};
        assertEquals(1234L, BCDtoDecimalConverter.convertPackedByteArray(bcd1234));

        // Test 12345678 -> [0x12, 0x34, 0x56, 0x78]
        byte[] bcdLarge = {(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78};
        assertEquals(12345678L, BCDtoDecimalConverter.convertPackedByteArray(bcdLarge));
    }

    @Test
    void testConvertPackedByteArrayEmptyArray() {
        // Test empty array -> 0
        assertEquals(0L, BCDtoDecimalConverter.convertPackedByteArray(new byte[]{}));
    }

    @Test
    void testConvertPackedByteArrayMaximumSafeLong() {
        // Test a large number that fits within a long (18 digits)
        // 999,999,999,999,999,999 (18 nines)
        byte[] bcdMax = {(byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99, (byte) 0x99};
        assertEquals(999999999999999999L, BCDtoDecimalConverter.convertPackedByteArray(bcdMax));
    }

    @Test
    void testConvertPackedByteArrayInvalidNibbleThrowsException() {
        // Contains 0x1A (A is an invalid BCD digit)
        byte[] bcdInvalid = {(byte) 0x12, (byte) 0x1A, (byte) 0x34};
        assertThrows(IllegalArgumentException.class,
            () -> BCDtoDecimalConverter.convertPackedByteArray(bcdInvalid),
            "Received array containing an invalid BCD byte. Provide valid BCD nibbles (0-9)."
        );
    }

    @Test
    void testConvertPackedByteArray_InvalidFirstByteThrowsException() {
        // Invalid BCD byte at the start
        byte[] bcdInvalid = {(byte) 0xF0, (byte) 0x12};
        assertThrows(IllegalArgumentException.class,
            () -> BCDtoDecimalConverter.convertPackedByteArray(bcdInvalid),
            "Received first byte as an invalid BCD byte. Provide valid BCD nibbles (0-9)."
        );
    }
}