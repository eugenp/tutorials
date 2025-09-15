package com.baeldung.temporal.workflows.hello.activities;

import io.temporal.activity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SayHelloActivityImpl implements SayHelloActivity {
    private static final Logger log = LoggerFactory.getLogger(SayHelloActivityImpl.class);

    public String sayHello(String person) {

        var info = Activity.getExecutionContext().getInfo();
        log.info("SayHelloActivityImpl sayHello({}): info={}", person, info);

        return "Hello, " + person;
    }
}
