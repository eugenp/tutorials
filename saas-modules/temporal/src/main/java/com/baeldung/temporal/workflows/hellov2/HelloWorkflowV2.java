package com.baeldung.temporal.workflows.hellov2;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface HelloWorkflowV2 {
    @WorkflowMethod
    String hello(String person);
}
