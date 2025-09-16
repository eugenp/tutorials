package com.baeldung.temporal.workflows.hello;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface HelloWorkflow {

    @WorkflowMethod
    String hello(String person);

}
