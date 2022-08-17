package com.baeldung.inttostring;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntToStringUnitTest {

    @Test
    public void whenValidIntIsPassed_thenShouldConvertToString() {
        assertEquals("11", Integer.toString(11)); 
        assertEquals("11", Integer.toString(+11)); 
        assertEquals("-11", Integer.toString(-11));
    }
    
    @Test
    public void whenValidIntIsPassed_thenShouldConvertToValidString() {
        assertEquals("11", String.valueOf(11)); 
        assertEquals("11", String.valueOf(+11));
        assertEquals("-11", String.valueOf(-11));
    }
    
    @Test(expected = NullPointerException.class)
    public void whenNullIntegerObjectIsPassed_thenShouldThrowException() {
        Integer i = null;
        // NOTE: primitive int can never be null, we are checking this example to check in what case the exception is thrown by these methods.
        System.out.println(String.valueOf(i)); // prints "null" as the call goes to String.valueOf(Object obj) method
        System.out.println(i.toString()); // throws NPE
    }
}
