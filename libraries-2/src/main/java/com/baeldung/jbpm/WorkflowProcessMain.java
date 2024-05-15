package com.baeldung.jbpm;

import org.kie.api.runtime.manager.Context;
import org.kie.internal.runtime.manager.context.EmptyContext;

import com.baeldung.jbpm.engine.WorkflowEngine;
import com.baeldung.jbpm.engine.WorkflowEngineImpl;

public class WorkflowProcessMain {

    public static void main(String[] args) {
        WorkflowEngine workflowEngine = new WorkflowEngineImpl();
        String processId = "com.baeldung.bpmn.helloworld";
        String kbaseId = "kbase";
        String persistenceUnit = "org.jbpm.persistence.jpa";
        Context<String> initialContext = EmptyContext.get();
        workflowEngine.runjBPMEngineForProcess(processId, initialContext, kbaseId, persistenceUnit);
    }
}