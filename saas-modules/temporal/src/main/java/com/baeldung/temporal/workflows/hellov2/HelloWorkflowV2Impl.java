package com.baeldung.temporal.workflows.hellov2;

import com.baeldung.temporal.workflows.hellov2.activities.HelloV2Activities;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class HelloWorkflowV2Impl implements HelloWorkflowV2 {

    private static final Logger log = LoggerFactory.getLogger(HelloWorkflowV2Impl.class);


    private final HelloV2Activities activity = Workflow.newActivityStub(
      HelloV2Activities.class,
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

        var info = Workflow.getInfo();

        log.info("Running workflow for person {}: id={}, attempt={}",
          person,
          info.getWorkflowId(),
          info.getAttempt());

        var step1result = activity.sayHello(person);
        var step2result = activity.sayGoodbye(person);

        return "Workflow OK";
    }

}
