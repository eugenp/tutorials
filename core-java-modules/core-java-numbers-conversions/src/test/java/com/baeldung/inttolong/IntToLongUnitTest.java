package com.baeldung.inttolong;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IntToLongUnitTest {

    @Test
    void whenUsingTheAutoboxing_thenGetTheExpectedLong() {
        int intTen = 10;
        Long longTen = (long) intTen;
        assertEquals(intTen, longTen);
    }

    @Test
    void whenUsingTheValueOf_thenGetTheExpectedLong() {
        int intTen = 10;
        Long longTen = Long.valueOf(intTen);
        assertEquals(intTen, longTen);
    }

    @Test
    void whenUsingTheConstructor_thenGetTheExpectedLong() {
        int intTen = 10;
        Long longTen = new Long(intTen);
        assertEquals(intTen, longTen);
    }

    @Test
    void whenUsingTheParseLong_thenGetTheExpectedLong() {
        int intTen = 10;
        Long longTen = Long.parseLong(String.valueOf(intTen));
        assertEquals(intTen, longTen);
    }
}