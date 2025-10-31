package com.baeldung.temporal.workflows.hellov2.activities;

import io.temporal.activity.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class HelloV2ActivitiesImpl implements HelloV2Activities {
    private static final Logger log = LoggerFactory.getLogger(HelloV2ActivitiesImpl.class);

    @Override
    public String sayHello(String person) {
        var info = Activity.getExecutionContext().getInfo();

        log.info("Saying hello to {}, workflowId={}, attempt={}", person,
          info.getWorkflowId(),
          info.getAttempt());
        return "Step1 - OK";
    }

    @Override
    public String sayGoodbye(String person) {

        var info = Activity.getExecutionContext().getInfo();

        log.info("Saying goodbye to {}, workflowId={}, attempt={}", person,
          info.getWorkflowId(),
          info.getAttempt());

        if ( info.getAttempt() == 1 ) {
            throw new IllegalStateException("Simulating task failure");
        }
        else {
            return "Step2 - OK";
        }
    }
}
