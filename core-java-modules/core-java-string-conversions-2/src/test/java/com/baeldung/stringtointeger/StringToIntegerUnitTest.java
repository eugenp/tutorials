package com.baeldung.stringtointeger;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringToIntegerUnitTest {

    @Test
    public void whenValidNumericStringIsPassed_thenShouldConvertToPrimitiveInt() {
        assertEquals(11, Integer.parseInt("11")); 
        assertEquals(11, Integer.parseInt("+11")); 
        assertEquals(-11, Integer.parseInt("-11"));
    }

    @Test
    public void whenValidNumericStringWithRadixIsPassed_thenShouldConvertToPrimitiveInt() {
        assertEquals(17, Integer.parseInt("11", 16)); 
        assertEquals(10, Integer.parseInt("A", 16)); 
        assertEquals(7, Integer.parseInt("7", 8));
    }

//    public static int parseInt(CharSequence s, int beginIndex, int endIndex, int radix) throws NumberFormatException 
//    This method is available in JDK 9 and above  
//    @Test
//    public void whenValidNumericStringWithRadixAndSubstringIsPassed_thenShouldConvertToPrimitiveInt() {
//        assertEquals(5, Integer.parseInt("100101", 3, 6, 2));
//        assertEquals(101, Integer.parseInt("100101", 3, 6, 10));
//    }

    @Test(expected = NumberFormatException.class)
    public void whenInValidNumericStringIsPassed_thenShouldThrowNumberFormatException() {
        int number = Integer.parseInt("abcd");
    }

    @Test
    public void whenValidNumericStringIsPassed_thenShouldConvertToInteger() {
        Integer expectedNumber = 11;
        Integer expectedNegativeNumber = -11;
        
        assertEquals(expectedNumber, Integer.valueOf("11"));
        assertEquals(expectedNumber, Integer.valueOf("+11"));
        assertEquals(expectedNegativeNumber, Integer.valueOf("-11"));
    }

    @Test
    public void whenNumberIsPassed_thenShouldConvertToInteger() {
        Integer expectedNumber = 11;
        Integer expectedNegativeNumber = -11;
        Integer expectedUnicodeValue = 65;
        
        assertEquals(expectedNumber, Integer.valueOf(11));
        assertEquals(expectedNumber, Integer.valueOf(+11));
        assertEquals(expectedNegativeNumber, Integer.valueOf(-11));
        assertEquals(expectedUnicodeValue, Integer.valueOf('A'));
    }

    @Test
    public void whenValidNumericStringWithRadixIsPassed_thenShouldConvertToInetger() {
        Integer expectedNumber1 = 17;
        Integer expectedNumber2 = 10;
        Integer expectedNumber3 = 7;
        
        assertEquals(expectedNumber1, Integer.valueOf("11", 16));
        assertEquals(expectedNumber2, Integer.valueOf("A", 16));
        assertEquals(expectedNumber3, Integer.valueOf("7", 8));
    }

    @Test(expected = NumberFormatException.class)
    public void whenInvalidValueOfNumericStringPassed_thenShouldThrowException() {
        Integer number = Integer.valueOf("abcd");
    }
}