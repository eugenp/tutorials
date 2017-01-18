package com.baeldung.java.stringconversion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class StringConversionTest {
    @Test
    public void testStringToCharArray() {
        String testString = "String";
        char[] expectedArr = {'S', 't', 'r', 'i', 'n', 'g'};
        char[] testStringCharArr = testString.toCharArray();
        assertArrayEquals(expectedArr, testStringCharArr);
    }

    @Test
    public void testCharArrayToString() {
        char[] testStringCharArr = {'S', 't', 'r', 'i', 'n', 'g'};
        String expectedString = "String";
        String testString = new String(testStringCharArr);
        assertEquals(expectedString, testString);
    }  
}
