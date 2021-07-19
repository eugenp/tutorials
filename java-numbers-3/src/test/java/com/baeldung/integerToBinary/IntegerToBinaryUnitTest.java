package com.baeldung.integerToBinary;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class IntegerToBinaryUnitTest {
    @Test
    public void givenAnInteger_whenConvertToBinary_thenGetBinaryString() {
        int n = 7;
        String binaryString = IntegerToBinary.convertIntegerToBinary(n);
        assertEquals("111", binaryString);
    }

    @Test
    public void givenAnInteger_whenToBinaryStringCalled_thenGetBinaryString() {
        int n = 7;
        String binaryString = Integer.toBinaryString(n);
        assertEquals("111", binaryString);
    }

    @Test
    public void givenAnInteger_whenToStringCalled_thenGetBinaryString() {
        int n = 7;
        String binaryString = Integer.toString(n, 2);
        assertEquals("111", binaryString);
    }
}
