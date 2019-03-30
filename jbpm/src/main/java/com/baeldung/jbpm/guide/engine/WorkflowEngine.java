package com.baeldung.jbpm.guide.engine;

import org.kie.api.runtime.manager.Context;

public interface WorkflowEngine {
   
    public void runjBPMEngineForProcess(String processId, Context<String> initialContext, String kbaseId, String persistenceUnit);
    
}
