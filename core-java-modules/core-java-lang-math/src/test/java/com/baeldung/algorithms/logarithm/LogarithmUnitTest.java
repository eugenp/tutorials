package com.baeldung.algorithms.logarithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class LogarithmUnitTest {

    @Test
    public void givenLog10_shouldReturnValidResults() {
        assertEquals(Math.log10(100), 2);
        assertEquals(Math.log10(1000), 3);
    }

    @Test
    public void givenLogE_shouldReturnValidResults() {
        assertEquals(Math.log(Math.E), 1);
        assertEquals(Math.log(10), 2.30258, 0.00001);
    }

    @Test
    public void givenCustomLog_shouldReturnValidResults() {
        assertEquals(customLog(2, 256), 8);
        assertEquals(customLog(10, 100), 2);
    }

    private static double customLog(double base, double logNumber) {
        return Math.log(logNumber) / Math.log(base);
    }

}
