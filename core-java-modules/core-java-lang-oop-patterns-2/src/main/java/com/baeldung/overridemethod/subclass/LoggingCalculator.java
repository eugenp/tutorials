package com.baeldung.overridemethod.subclass;

import com.baeldung.overridemethod.SimpleCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingCalculator extends SimpleCalculator {
    private static final Logger log = LoggerFactory.getLogger(LoggingCalculator.class);

    @Override
    public int add(int a, int b) {
        log.debug("LOG: Before addition.");
        int result = super.add(a, b);
        log.debug("LOG: After addition. Result: {}", result);
        return result;
    }
}
