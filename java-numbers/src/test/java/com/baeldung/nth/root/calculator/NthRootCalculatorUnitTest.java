package com.baeldung.nth.root.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NthRootCalculatorUnitTest {

    private NthRootCalculator nthRootCalculator = new NthRootCalculator();

    @Test
    public void whenBaseIs125AndNIs3_thenNthIs5() {
        double nth = nthRootCalculator.calculateWithRound(125,3);
        assertEquals(5, nth, 0);
    }

    @Test
    public void whenBaseIs625AndNIs4_thenNthIs5() {
        double nth = nthRootCalculator.calculate(625,4);
        assertEquals(5, nth, 0.00001);
    }
}
