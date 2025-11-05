package com.baeldung.integerToBinary;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegerToBinaryUnitTest {

    @Test
    public void givenAnInteger_whenConvertToBinary_thenGetBinaryString() {
        int n = 7;
        String binaryString = IntegerToBinary.convertIntegerToBinary(n);
        assertEquals("111", binaryString);

        int negativeN = -2;
        String negativeBinaryString = IntegerToBinary.convertIntegerToBinary(negativeN);
        assertEquals("", negativeBinaryString);
    }

    @Test
    public void givenAnInteger_whenToBinaryStringCalled_thenGetBinaryString() {
        int n = 7;
        String binaryString = Integer.toBinaryString(n);
        assertEquals("111", binaryString);

        int negativeN = -2;
        String negativeBinaryString = Integer.toBinaryString(negativeN);
        assertEquals("11111111111111111111111111111110", negativeBinaryString);
    }

    @Test
    public void givenAnInteger_whenToStringCalled_thenGetBinaryString() {
        int n = 7;
        String binaryString = Integer.toString(n, 2);
        assertEquals("111", binaryString);

        int negativeN = -2;
        String negativeBinaryString = Integer.toString(negativeN, 2);
        assertEquals("-10", negativeBinaryString);
    }

    @Test
    public void givenAnInteger_whenFormatAndReplaceCalled_thenZeroPaddedBinaryString() {
        int n = 7;
        String binaryString = String.format("%8s", Integer.toBinaryString(n))
            .replace(" ", "0");
        assertEquals("00000111", binaryString);
    }

    @Test
    public void givenAnInteger_whenUsingApacheStringUtils_thenZeroPaddedBinaryString() {
        int n = 7;
        String binaryString = StringUtils.leftPad(Integer.toBinaryString(n), 8, "0");
        assertEquals("00000111", binaryString);
    }
}