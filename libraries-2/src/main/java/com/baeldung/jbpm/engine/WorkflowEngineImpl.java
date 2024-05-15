package com.baeldung.jbpm.engine;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jbpm.test.JBPMHelper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.Context;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;

public class WorkflowEngineImpl implements WorkflowEngine {

    @Override
    public ProcessInstance runjBPMEngineForProcess(String processId, Context<String> initialContext, String kbaseId, String persistenceUnit) {
        RuntimeManager manager = null;
        RuntimeEngine engine = null;
        ProcessInstance pInstance = null;
        try {
            KieBase kbase = getKieBase(kbaseId);
            manager = createJBPMRuntimeManager(kbase, persistenceUnit);
            engine = manager.getRuntimeEngine(initialContext);
            pInstance = executeProcessInstance(processId, manager, initialContext, engine);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (manager != null && engine != null)
                manager.disposeRuntimeEngine(engine);
            System.exit(0);
        }
        return pInstance;
    }

    private ProcessInstance executeProcessInstance(String processId, RuntimeManager manager, Context<String> initialContext, RuntimeEngine engine) {
        KieSession ksession = engine.getKieSession();
        ProcessInstance pInstance = ksession.startProcess(processId);
        return pInstance;
    }

    private KieBase getKieBase(String kbaseId) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kbase = kContainer.getKieBase(kbaseId);
        return kbase;
    }

    private RuntimeManager createJBPMRuntimeManager(KieBase kbase, String persistenceUnit) {
        JBPMHelper.startH2Server();
        JBPMHelper.setupDataSource();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        RuntimeEnvironmentBuilder runtimeEnvironmentBuilder = RuntimeEnvironmentBuilder.Factory.get()
            .newDefaultBuilder();
        RuntimeEnvironment runtimeEnvironment = runtimeEnvironmentBuilder.entityManagerFactory(emf)
            .knowledgeBase(kbase)
            .get();
        return RuntimeManagerFactory.Factory.get()
            .newSingletonRuntimeManager(runtimeEnvironment);
    }
}
