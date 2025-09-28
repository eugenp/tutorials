package com.baeldung.overridemethod.subclass;

import com.baeldung.overridemethod.Calculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubclassingTest {

    @Test
    void givenACalculatorClass_whenSubclassingToAddLogging_thenLoggingCalculatorCanBeUsed() {
        Calculator calculator = new LoggingCalculator();
        assertEquals(8, calculator.add(5, 3));
        assertEquals(2, calculator.add(5, 3));
    }
}
