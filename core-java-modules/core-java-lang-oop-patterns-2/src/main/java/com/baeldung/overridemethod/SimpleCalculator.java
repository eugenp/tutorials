package com.baeldung.overridemethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleCalculator implements Calculator {
    private static final Logger log = LoggerFactory.getLogger(SimpleCalculator.class);

    @Override
    public int add(int a, int b) {
        log.info("SimpleCalculator: Adding {} and {}", a, b); // Use parameterized logging {}
        return a + b;
    }

    @Override
    public int subtract(int a, int b) {
        log.info("SimpleCalculator: Subtracting {} from {}", b, a);
        return a - b;
    }
}
