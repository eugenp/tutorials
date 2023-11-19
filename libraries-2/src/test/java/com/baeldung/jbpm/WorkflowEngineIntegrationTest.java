package com.baeldung.jbpm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

public class WorkflowEngineIntegrationTest extends JbpmJUnitBaseTestCase {

    private String[] triggeredNodesArray = { "Start", "HelloWorld", "End" };
    private RuntimeManager manager = null;
    private RuntimeEngine runtimeEngine = null;
    private KieSession ksession = null;
    private ProcessInstance processInstance = null;

    @Before
    public void setup() {
        manager = createRuntimeManager(Strategy.SINGLETON, "manager", "com/baeldung/process/helloworld.bpmn");
        runtimeEngine = getRuntimeEngine(ProcessInstanceIdContext.get());
        ksession = runtimeEngine.getKieSession();
        processInstance = ksession.startProcess("com.baeldung.bpmn.helloworld");
    }

    @After
    public void cleanup() {
        manager.disposeRuntimeEngine(runtimeEngine);
    }

    @Test
    public void givenProcessInstance_whenExecutionCompleted_thenVerifyNodesExecutionOrder() {
        assertNodeTriggered(processInstance.getId(), triggeredNodesArray);
    }

    @Test
    public void givenProcessInstance_whenExecutionCompleted_thenVerifyKnowledgeSessionId() {
        int ksessionID = ksession.getId();
        runtimeEngine = getRuntimeEngine(ProcessInstanceIdContext.get(processInstance.getId()));
        ksession = runtimeEngine.getKieSession();
        assertEquals(ksessionID, ksession.getId());
    }

    @Test
    public void givenProcessInstance_whenExecutionCompleted_thenVerifyProcessInstanceStatus() {
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        assertEquals("ProcessInstance completed with status 2", 2, processInstance.getState());
    }
}
