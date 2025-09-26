package com.baeldung.overridemethod.decorator;

import com.baeldung.overridemethod.Calculator;
import com.baeldung.overridemethod.SimpleCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecoratorPatternTest {

    @Test
    void testDecoratorWrapping() {
        Calculator simpleCalc = new SimpleCalculator();
        Calculator decoratedCalc = new LoggingCalculatorDecorator(simpleCalc);
        assertEquals(15, decoratedCalc.add(10, 5));
    }
}
