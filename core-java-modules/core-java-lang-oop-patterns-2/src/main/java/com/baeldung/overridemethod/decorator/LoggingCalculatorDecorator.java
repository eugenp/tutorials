package com.baeldung.overridemethod.decorator;

import com.baeldung.overridemethod.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingCalculatorDecorator implements Calculator {
    private static final Logger log = LoggerFactory.getLogger(LoggingCalculatorDecorator.class);
    private final Calculator wrappedCalculator;

    public LoggingCalculatorDecorator(Calculator calculator) {
        this.wrappedCalculator = calculator;
    }

    @Override
    public int add(int a, int b) {
        log.debug("DECORATOR LOG: Entering add({}, {})", a, b);
        int result = wrappedCalculator.add(a, b); // Delegation
        log.debug("DECORATOR LOG: Exiting add. Result: {}", result);
        return result;
    }

    @Override
    public int subtract(int a, int b) {
        // Just delegate
        return wrappedCalculator.subtract(a, b); 
    }
}
