package com.baeldung.chararraytostring;

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
        System.out.println(String.valueOf(i)); // it prints "null"
        System.out.println(i.toString()); 
    }
}
