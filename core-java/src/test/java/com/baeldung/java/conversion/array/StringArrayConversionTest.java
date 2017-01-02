package com.baeldung.java.conversion.array;

import static org.junit.Assert.assertEquals;

import com.google.common.primitives.Chars;

import org.junit.Test;

import java.util.Arrays;

public class StringArrayConversionTest {

    @Test
    public void whenConvertedStringToCharArray_thenCorrect() {
        String beforeConvStr = "abc";
        char[] afterConvArr = new char[] { 'a', 'b', 'c' };

        assertEquals(Arrays.equals(beforeConvStr.toCharArray(), afterConvArr), true);
    }

    @Test
    public void whenConvertedCharArrayToString_thenCorrect() {
        char[] beforeConvStr = new char[] { 'a', 'b', 'c' };
        String afterConvArr = "abc";

        assertEquals(new String(beforeConvStr), afterConvArr);
    }

    @Test
    public void whenConvertedCharArrayToStringValueOf_thenCorrect() {
        char[] beforeConvStr = new char[] { 'a', 'b', 'c' };
        String afterConvArr = "abc";

        assertEquals(String.valueOf(beforeConvStr), afterConvArr);
    }

    @Test
    public void givenGuava_whenConvertedCharArrayToString_thenCorrect() {
        char[] beforeConvStr = new char[] { 'a', 'b', 'c' };
        String afterConvArr = "abc";

        assertEquals(Chars.join("", beforeConvStr), afterConvArr);
    }
}
