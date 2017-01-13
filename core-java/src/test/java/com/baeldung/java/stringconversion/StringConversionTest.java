package com.baeldung.java.stringconversion;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringConversionTest {
    @Test
    public void testStringToCharArray() {
        String testString = "JavaString";
        char[] testStringCharArr = testString.toCharArray();
        for (int i = 0; i < testString.length(); i++) {
            assertEquals("Invalid StringToCharArray conversion", testString.charAt(i), testStringCharArr[i]);
        }
    }

    @Test
    public void testCharArrayToString() {
        char[] testStringCharArr = {'J', 'a', 'v', 'a', 'S', 't', 'r', 'i', 'n', 'g'};
        String testString = new String(testStringCharArr);
        for (int i = 0; i < testString.length(); i++) {
            assertEquals("Invalid CharArrayToString conversion", testString.charAt(i), testStringCharArr[i]);
        }
    }  
}
