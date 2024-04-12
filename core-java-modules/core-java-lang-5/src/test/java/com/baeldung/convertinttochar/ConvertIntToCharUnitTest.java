package com.baeldung.convertinttochar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConvertIntToCharUnitTest {

    @Test
    public void givenAnInt_whenAdding0_thenExpectedCharType() {
        int num = 7;
        //add '0' to convert int to char
        char c = (char) ('0' + num);

        assertEquals('7', c);
    }

    @Test
    public void givenAnInt_whenUsingForDigit_thenExpectedCharType() {
        int num = 7;
        // Convert using forDigit() method
        char c = Character.forDigit(num, 10);

        assertEquals('7', c);
    }

    @Test
    public void givenAnInt_whenUsingToString_thenExpectedCharType() {
        int num = 7;
        //convert int to char using toString()
        char c = Integer.toString(num)
          .charAt(0);

        assertEquals('7', c);
    }
}
