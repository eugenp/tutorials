package com.baeldung.temporal.workflows.hello.activities;

import io.temporal.activity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SayHelloActivityImpl implements SayHelloActivity {
    private static final Logger log = LoggerFactory.getLogger(SayHelloActivityImpl.class);

    public String sayHello(String person) {
        log.info("Saying hello to {}", person);
        return "Hello, " + person;
    }
}
