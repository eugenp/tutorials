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
    public void givenCharArray_ConvertedToString_ThenSuccessful() {
        char[] charArrayToBeConverted = { 'h', 'e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd'};
        assertEquals(new String(charArrayToBeConverted), "hello world");
        assertEquals(String.valueOf(charArrayToBeConverted), "hello world");
    }

}
