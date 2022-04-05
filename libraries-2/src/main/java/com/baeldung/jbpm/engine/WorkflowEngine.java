package com.baeldung.jbpm.engine;

import org.kie.api.runtime.manager.Context;
import org.kie.api.runtime.process.ProcessInstance;

public interface WorkflowEngine {
   
    public ProcessInstance runjBPMEngineForProcess(String processId, Context<String> initialContext, String kbaseId, String persistenceUnit);
    
}
