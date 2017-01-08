package com.baeldung.java.conversion;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class StringCharArrayConversionTest {

    private static StringConvertion conversion;

    @BeforeClass
    public static void setup() {
        conversion = new StringConvertion();
    }

    @AfterClass
    public static void tearDown() {
        conversion = null;
    }

    @Test
    public void whenStringConvrtdToCharArray_thenEquals() {
        String testString = "madagascar";
        char[] expectedArray = { 'm', 'a', 'd', 'a', 'g', 'a', 's', 'c', 'a', 'r' };

        assertEquals(Arrays.equals(expectedArray, conversion.convertToCharArray(testString)), true);

    }

    @Test
    public void whenCharArrayConvrtdToString_thenEquals() {
        String expectedString = "madagascar";
        char[] testArray = { 'm', 'a', 'd', 'a', 'g', 'a', 's', 'c', 'a', 'r' };

        assertEquals((expectedString.equals(conversion.convertCharArrayToStringWithConstructor(testArray))), true);
    }
    
    @Test
    public void whenCalledValueOf_thenEquals(){
        String expectedString = "madagascar";
        char[] testArray = { 'm', 'a', 'd', 'a', 'g', 'a', 's', 'c', 'a', 'r' };

        assertEquals((expectedString.equals(conversion.convertCharArrayToStringWithValueOf(testArray))), true);
    }

}
