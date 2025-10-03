package com.baeldung.overridemethod.decorator;

import com.baeldung.overridemethod.Calculator;
import com.baeldung.overridemethod.SimpleCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecoratorPatternTest {

    @Test
    void givenACalculator_whenUsingMeteredDecorator_thenMethodCallsAreCountedCorrectly() {
        // ARRANGE
        Calculator simpleCalc = new SimpleCalculator();
        
        // Use the MeteredCalculator decorator
        MeteredCalculator decoratedCalc = new MeteredCalculator(simpleCalc);

        // ACT
        // Call add twice
        decoratedCalc.add(10, 5);
        decoratedCalc.add(2, 3);
        
        // Call subtract once
        decoratedCalc.subtract(10, 5);

        // ASSERT Core Functionality (optional, but good practice)
        assertEquals(15, decoratedCalc.add(10, 5), "Core functionality must still work.");

        // ASSERT the call counts
        // 1. Assert 'add' was called 3 times (2 from ACT + 1 from ASSERT Core)
        assertEquals(3, decoratedCalc.getCallCount("add"), "The 'add' method should have been called 3 times.");

        // 2. Assert 'subtract' was called 1 time
        assertEquals(1, decoratedCalc.getCallCount("subtract"), "The 'subtract' method should have been called 1 time.");
    }
}
