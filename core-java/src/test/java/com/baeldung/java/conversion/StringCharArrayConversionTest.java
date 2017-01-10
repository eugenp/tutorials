package com.baeldung.java.conversion;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringCharArrayConversionTest {

    @Test
    public void whenConvertedToCharArray_thenCorrect() {
        String str = "baeldung";
        char[] charArrExpected = { 'b', 'a', 'e', 'l', 'd', 'u', 'n', 'g' };
        char[] charArrAftrConv = str.toCharArray();
        assertArrayEquals(charArrExpected, charArrAftrConv);
    }

    @Test
    public void whenConvertedToString_thenCorrect() {
        char[] charArr = { 'b', 'a', 'e', 'l', 'd', 'u', 'n', 'g' };
        String strExpected = "baeldung";
        String strAftrConstrctConv = new String(charArr);
        String strAftrValueOfConv = String.valueOf(charArr);
        String strAftrCopyValueOfConv = String.copyValueOf(charArr);
        assertEquals(strExpected, strAftrConstrctConv);
        assertEquals(strExpected, strAftrValueOfConv);
        assertEquals(strExpected, strAftrCopyValueOfConv);
    }

}
