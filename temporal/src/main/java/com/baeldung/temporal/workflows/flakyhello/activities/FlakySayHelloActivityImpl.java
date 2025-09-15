package com.baeldung.temporal.workflows.flakyhello.activities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class FlakySayHelloActivityImpl implements FlakySayHelloActivity {
    private static final Logger log = LoggerFactory.getLogger(FlakySayHelloActivityImpl.class);
    private static final AtomicLong counter = new AtomicLong(2L);

    @Override
    public String sayHello(String person) {
        log.info("Saying hello to {}", person);

        if ( counter.decrementAndGet() > 0 ) {
            throw new IllegalStateException("Simulating task failure");
        }
        else {
            return "Hello, " + person;
        }
    }
}
