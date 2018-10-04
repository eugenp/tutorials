package com.baeldung.nth.root.calculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class NthRootCalculatorUnitTest {

    @InjectMocks
    private NthRootCalculator nthRootCalculator;

    @Test
    public void giventThatTheBaseIs125_andTheExpIs3_whenCalculateIsCalled_thenTheResultIsTheCorrectOne() {
       Double result = nthRootCalculator.calculate(125.0, 3.0);
       assertEquals(result, (Double) 5.0d);
    }
}
