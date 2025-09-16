package com.baeldung.temporal.workflows.flakyhello;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface FlakyHelloWorkflow {

    @WorkflowMethod
    String hello(String person);

}
