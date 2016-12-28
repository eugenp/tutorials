package com.baeldung.java.conversion;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StringCharArrayConversionTest {

    @Test
    public void whenConvertedFromStringToCharArr_thenCorrect() {
        String beforeConvStr = "hello";
        char[] afterConvCharArr = {'h', 'e', 'l', 'l', 'o'};

        assertEquals(Arrays.equals(beforeConvStr.toCharArray(), afterConvCharArr), true);
    }

    @Test
    public void whenConvertedFromCharArrayToString_thenCorrect() {

        char[] beforeConvCharArr = {'h', 'e', 'l', 'l', 'o'};
        String afterConvStr = "hello";

        assertEquals(afterConvStr.equals(String.valueOf(beforeConvCharArr)), true);
    }

    @Test
    public void whenConvertedFromCharArrayToString2_thenCorrect() {

        char[] beforeConvCharArr = {'h', 'e', 'l', 'l', 'o'};
        String afterConvStr = "hello";

        assertEquals(afterConvStr.equals(new String(beforeConvCharArr)), true);
    }

}
