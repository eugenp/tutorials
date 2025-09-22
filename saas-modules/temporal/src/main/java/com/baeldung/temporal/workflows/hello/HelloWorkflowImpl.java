package com.baeldung.temporal.workflows.hello;

import com.baeldung.temporal.workflows.hello.activities.SayHelloActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class HelloWorkflowImpl implements HelloWorkflow {

    private final SayHelloActivity activity = Workflow.newActivityStub(
      SayHelloActivity.class,
      ActivityOptions.newBuilder()
        .setStartToCloseTimeout(Duration.ofSeconds(10))
        .build()
    );

    @Override
    public String hello(String person) {
        return activity.sayHello(person);
    }
}
