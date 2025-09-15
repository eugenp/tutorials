package com.baeldung.temporal.workflows.flakyhello.activities;

import io.temporal.activity.Activity;
import io.temporal.api.enums.v1.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

public class FlakySayHelloActivityImpl implements FlakySayHelloActivity {
    private static final Logger log = LoggerFactory.getLogger(FlakySayHelloActivityImpl.class);

    private static AtomicLong counter = new AtomicLong(2L);

    @Override
    public String sayHello(String person) {

        var ctx = Activity.getExecutionContext();
        var info = ctx.getInfo();
        var history = ctx.getWorkflowClient().fetchHistory(info.getWorkflowId());
        var event = history.getLastEvent();

        if ( counter.decrementAndGet() > 0 ) {
            throw new IllegalStateException("Simulating task failure");
        }
        else {
            return "Hello, " + person;
        }

    }
}
