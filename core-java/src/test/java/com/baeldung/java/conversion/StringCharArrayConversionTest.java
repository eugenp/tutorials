package com.baeldung.java.conversion;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

<<<<<<< HEAD
=======
import org.junit.AfterClass;
import org.junit.BeforeClass;
>>>>>>> b09122accf977a5d0d13a2b7aeb8907bad022885
import org.junit.Test;

public class StringCharArrayConversionTest {

<<<<<<< HEAD
    @Test
    public void whenStringConvrtdToCharArray_thenEquals() {
        StringConvertion conversion = new StringConvertion();
        String testString = "madagascar";
        char[] expectedArray = { 'm', 'a', 'd', 'a', 'g', 'a', 's', 'c', 'a', 'r' };
        assertEquals(Arrays.equals(expectedArray, conversion.convertToCharArray(testString)), true);
=======
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

>>>>>>> b09122accf977a5d0d13a2b7aeb8907bad022885
    }

    @Test
    public void whenCharArrayConvrtdToString_thenEquals() {
<<<<<<< HEAD
        StringConvertion conversion = new StringConvertion();
=======
>>>>>>> b09122accf977a5d0d13a2b7aeb8907bad022885
        String expectedString = "madagascar";
        char[] testArray = { 'm', 'a', 'd', 'a', 'g', 'a', 's', 'c', 'a', 'r' };

        assertEquals((expectedString.equals(conversion.convertCharArrayToStringWithConstructor(testArray))), true);
    }
<<<<<<< HEAD

    @Test
    public void whenCalledValueOf_thenEquals() {
        StringConvertion conversion = new StringConvertion();
=======
    
    @Test
    public void whenCalledValueOf_thenEquals(){
>>>>>>> b09122accf977a5d0d13a2b7aeb8907bad022885
        String expectedString = "madagascar";
        char[] testArray = { 'm', 'a', 'd', 'a', 'g', 'a', 's', 'c', 'a', 'r' };

        assertEquals((expectedString.equals(conversion.convertCharArrayToStringWithValueOf(testArray))), true);
    }

}
