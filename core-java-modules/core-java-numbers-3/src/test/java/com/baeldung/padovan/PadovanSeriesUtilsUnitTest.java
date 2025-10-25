package com.baeldung.padovan;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PadovanSeriesUtilsUnitTest {

    @Test
    public void givenTermToCalculate_thenReturnThatTermUsingRecursion() {
        int term = 10;
        int expectedValue = 12;
        assertEquals(PadovanSeriesUtils.nthPadovanTermRecursiveMethod(term), expectedValue);
    }

    @Test
    public void givenTermToCalculate_thenReturnThatTermUsingIteration() {
        int term = 10;
        int expectedValue = 12;
        assertEquals(PadovanSeriesUtils.nthPadovanTermIterativeMethod(term), expectedValue);
    }

    @Test
    public void givenTermToCalculate_thenReturnThatTermUsingFormula() {
        int term = 10;
        int expectedValue = 12;
        assertEquals(PadovanSeriesUtils.nthPadovanTermUsingFormula(term), expectedValue);
    }
}

