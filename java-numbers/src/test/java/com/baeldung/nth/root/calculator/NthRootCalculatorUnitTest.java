package com.baeldung.nth.root.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

public class NthRootCalculatorUnitTest {

    private NthRootCalculator nthRootCalculator = new NthRootCalculator();

    @Test
    public void whenBaseIs125AndNIs3_thenNthRootIs5() {
       Double result = nthRootCalculator.calculate(125.0, 3.0);
       assertEquals(result, (Double) 5.0d);
    }
}
