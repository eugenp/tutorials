package com.baeldung.conversion;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class StringToCharArrayTest {

    @Test
    public void givenString_ConvertedToCharArray_ThenSuccessful() {
        String stringToBeConverted = "testString";
        char[] charArray = { 't', 'e', 's', 't', 'S', 't', 'r', 'i', 'n', 'g' };
        char[] stringToCharArray = stringToBeConverted.toCharArray();
        
        assertEquals(Arrays.equals(stringToCharArray, charArray), true);
    }

    @Test
    public void whenConvertedFromCharArrayToString_thenCorrect() {

        char[] beforeConvCharArr = { 'h', 'e', 'l', 'l', 'o' };
        String afterConvStr = "hello";

        assertEquals(afterConvStr.equals(String.valueOf(beforeConvCharArr)), true);
    }

}
