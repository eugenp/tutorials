package com.baeldung.inttolong;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class IntToLongUnitTest {

    @Test
    void whenUsingTheAutoboxing_thenGetTheExpectedLong() {
        int intTen = 10;
        Long longTen = (long) intTen;
        assertEquals(intTen, longTen);
    }

    @Test
    void whenUsingTheAutoboxing_thenGetTheExpectedPrimitiveLong() {
        int intTen = 10;

        long longNoCast = intTen;
        assertEquals(intTen, longNoCast);

        long longCastLong = (long) intTen;
        assertEquals(intTen, longCastLong);
    }

    @Test
    void whenUsingTheValueOf_thenGetTheExpectedLong() {
        int intTen = 10;
        Long longTen = Long.valueOf(intTen);
        assertEquals(intTen, longTen);
    }

    @Test
    void whenIntegerUsingTheValueOf_thenGetTheExpectedLong() {
        Integer integerTen = 10;
        Long integerToLongTen = Long.valueOf(integerTen);
        assertEquals(integerTen.longValue(), integerToLongTen.longValue());
    }

    @Test
    void whenUsingTheConstructor_thenGetTheExpectedLong() {
        int intTen = 10;
        Long longTen = Long.valueOf(intTen);
        assertEquals(intTen, longTen);
    }

    @Test
    void whenIntegerUsingTheLongConstructor_thenGetTheExpectedLong() {
        Integer integerTen = 10;
        Long integerToLongTen = Long.valueOf(integerTen.longValue());
        assertEquals(integerTen.longValue(), integerToLongTen.longValue());
    }

    @Test
    void whenUsingTheParseLong_thenGetTheExpectedLong() {
        int intTen = 10;
        Long longTen = Long.parseLong(String.valueOf(intTen));
        assertEquals(intTen, longTen);
    }

    @Test
    void whenUsingTheIntegerLongValue_thenGetTheExpectedLong() {
        Integer integerTen = 10;

        Long integerLongValueTen = integerTen.longValue();
        assertEquals(integerTen.longValue(), integerLongValueTen.longValue());

        Long longValueOfTen = Long.valueOf(integerTen.longValue());
        assertEquals(integerTen.longValue(), longValueOfTen.longValue());
    }

    @Test
    void whenUsingTheIntegerLongValue_thenCheckIfNullAndGetTheExpectedLong() {
        Integer integerTen = 10;

        Long integerToValueOfLongNullCheck = Optional.ofNullable(integerTen)
            .map(Long::valueOf)
            .orElse(null);
        assertEquals(integerTen.longValue(), integerToValueOfLongNullCheck.longValue());

        Long integerToLongValueNullCheck = Optional.ofNullable(integerTen)
            .map(Integer::longValue)
            .orElse(null);
        assertEquals(integerTen.longValue(), integerToLongValueNullCheck.longValue());
    }
}