package com.baeldung.inttoshort;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ConvertIntToShortUnitTest {

    @Test
    void whenUsingCasting_thenCorrect() {
        short expected = 42;
        int i = 42;
        short result = (short) i;
        assertEquals(expected, result);
    }

    @Test
    void whenUsingIntegerShortValue_thenCorrect() {
        short expected = 42;
        Integer intObj = 42;
        short result = intObj.shortValue();
        assertEquals(expected, result);
    }

    @Test
    void whenIntOutOfShortRange_thenGetTheUnexpectedResult() {
        int oneMillion = 1_000_000;
        short result = (short) oneMillion;
        assertEquals(16960, result);

        int twoMillion = 2_000_000;
        result = (short) twoMillion;
        assertEquals(-31616, result);
    }

}