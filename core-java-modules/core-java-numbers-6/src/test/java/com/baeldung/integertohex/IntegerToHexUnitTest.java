package com.baeldung.integertohex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

class IntegerToHexUnitTest {

    @Test
    void givenIntegerValue_whenUseRawMethod_thenWillGetHexValue() {
        String result = IntegerToHex.integerToHex(1055);
        assertEquals("41F", result);
    }

    @Test
    void givenIntegerNegativeValue_whenUseRawMethod_thenZeroValue() {
        String result = IntegerToHex.integerToHex(-1055);
        assertEquals("0", result);
    }

    @Test
    void givenIntegerPositiveValue_whenUseStringFormat_thenWillGetHexValue() {
        String result = String.format("%02x", 255);
        assertEquals("ff", result);
    }

    @Test
    void givenIntegerPositiveValue_whenUseStringFormat_thenWillGetHexValueWithLeftZeros() {
        String result = String.format("%04x", 255);
        assertEquals("00ff", result);
    }

    @Test
    void givenIntegerPositiveValue_whenUseStringFormat_thenWillGetHexValueWithLeftZerosAndUpperLetter() {
        String result = String.format("%04X", 255);
        assertEquals("00FF", result);
    }

    @Test
    void givenIntegerValue_whenUseIntegerToHexString_thenWillGetHexValue() {
        String result = Integer.toHexString(1000);
        assertEquals("3e8", result);
    }

    @Test
    void givenIntegerValue_whenUseLongToHexString_thenWillGetHexValue() {
        String result = Long.toHexString(255L);
        assertEquals("ff", result);
    }

    @Test
    public void givenNegativeIntegerValue_whenUseIntegerToString_thenWillGetHexValue() {
        String result = Integer.toString(-1458, 16);
        assertEquals("-5b2", result);
    }

    @Test
    public void givenIntegerValue_whenUseIntegerToString_thenWillGetHexValue() {
        String result = Integer.toString(1458, 16);
        assertEquals("5b2", result);
    }

    @Test
    public void givenLongValue_whenUseLongToString_thenWillGetHexValue() {
        String result = Long.toString(158, 16);
        assertEquals("9e", result);
    }

    @Test
    public void givenIntegerValue_whenUseApacheCommons_thenWillGetHexSignedValue() {
        String result = Hex.encodeHexString(new byte[] { (byte) 254 });
        assertEquals("fe", result);
    }
}