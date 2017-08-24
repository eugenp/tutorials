package com.baeldung;

import static org.junit.Assert.*;

import org.junit.Test;

public class CharArrayToStringUnitTest {

    @Test
    public void givenCharArray_whenCallingStringConstructor_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = new String(charArray);
        String expectedValue = "character";

        assertEquals(expectedValue, result);
    }

    @Test
    public void givenCharArray_whenCallingStringConstructorWithOffsetAndLength_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = new String(charArray, 4, 3);
        String expectedValue = "act";

        assertEquals(expectedValue, result);
    }

    @Test
    public void givenCharArray_whenCallingStringCopyValueOf_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = String.copyValueOf(charArray);
        String expectedValue = "character";

        assertEquals(expectedValue, result);
    }

    @Test
    public void givenCharArray_whenCallingStringCopyValueOfWithOffsetAndLength_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = String.copyValueOf(charArray, 0, 4);
        String expectedValue = "char";

        assertEquals(expectedValue, result);
    }

    @Test
    public void givenCharArray_whenCallingStringValueOf_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = String.valueOf(charArray);
        String expectedValue = "character";

        assertEquals(expectedValue, result);
    }

    @Test
    public void givenCharArray_whenCallingStringValueOfWithOffsetAndLength_shouldConvertToString() {
        char[] charArray = { 'c', 'h', 'a', 'r', 'a', 'c', 't', 'e', 'r' };
        String result = String.valueOf(charArray, 3, 4);
        String expectedValue = "ract";

        assertEquals(expectedValue, result);
    }
}
