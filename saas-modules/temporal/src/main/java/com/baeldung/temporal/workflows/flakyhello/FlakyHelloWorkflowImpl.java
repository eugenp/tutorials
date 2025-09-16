package com.baeldung.temporal.workflows.flakyhello;

import com.baeldung.temporal.workflows.flakyhello.activities.FlakySayHelloActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class FlakyHelloWorkflowImpl implements FlakyHelloWorkflow {


    private final FlakySayHelloActivity activity = Workflow.newActivityStub(
      FlakySayHelloActivity.class,
      ActivityOptions.newBuilder()
        .setStartToCloseTimeout(Duration.ofSeconds(10))
        .setRetryOptions(RetryOptions.newBuilder()
          .setMaximumAttempts(3)
          .setInitialInterval(Duration.ofSeconds(1))
          .build())
        .build()
    );


    @Override
    public String hello(String person) {
        return activity.sayHello(person);
    }

}
