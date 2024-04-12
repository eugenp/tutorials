package com.baeldung.convertinttochar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConvertCharToIntUnitTest {

    @Test
    public void givenAChar_whenUsingGetNumericValue_thenExpectedNumericType() {
        //char value
        char c = '7';
        // using getNumericValue
        int n = Character.getNumericValue(c);

        assertEquals(7, n);
    }

    @Test
    public void givenAChar_whenSubtracting0_thenExpectedNumericType() {
        //char value
        char c = '7';
        // subtract '0' from the char
        int n = c - '0';

        assertEquals(7, n);
    }

    @Test
    public void givenAChar_whenUsingParseInt_thenExpectedNumericType() {
        //char value
        char c = '7';
        // using parseInt
        int n = Integer.parseInt(String.valueOf(c));

        assertEquals(7, n);
    }

    @Test
    public void givenAChar_whenCastingFromCharToInt_thenExpectedUnicodeRepresentation() {
        //char value
        char c = '7';
        //cast to int
        assertEquals(55, (int) c);
    }

}
