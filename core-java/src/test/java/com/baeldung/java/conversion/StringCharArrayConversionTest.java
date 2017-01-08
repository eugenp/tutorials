package com.baeldung.java.conversion;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class StringCharArrayConversionTest {

    @Test
    public void whenStringConvrtdToCharArray_thenEquals() {
        StringConvertion conversion = new StringConvertion();
        String testString = "madagascar";
        char[] expectedArray = { 'm', 'a', 'd', 'a', 'g', 'a', 's', 'c', 'a', 'r' };
        assertEquals(Arrays.equals(expectedArray, conversion.convertToCharArray(testString)), true);
    }

    @Test
    public void whenCharArrayConvrtdToString_thenEquals() {
        StringConvertion conversion = new StringConvertion();
        String expectedString = "madagascar";
        char[] testArray = { 'm', 'a', 'd', 'a', 'g', 'a', 's', 'c', 'a', 'r' };

        assertEquals((expectedString.equals(conversion.convertCharArrayToStringWithConstructor(testArray))), true);
    }

    @Test
    public void whenCalledValueOf_thenEquals() {
        StringConvertion conversion = new StringConvertion();
        String expectedString = "madagascar";
        char[] testArray = { 'm', 'a', 'd', 'a', 'g', 'a', 's', 'c', 'a', 'r' };

        assertEquals((expectedString.equals(conversion.convertCharArrayToStringWithValueOf(testArray))), true);
    }

}
