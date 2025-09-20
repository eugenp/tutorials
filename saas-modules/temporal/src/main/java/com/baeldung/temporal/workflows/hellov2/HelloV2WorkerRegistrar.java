package com.baeldung.temporal.workflows.hellov2;

import com.baeldung.temporal.worker.TemporalWorkerRegistrar;
import com.baeldung.temporal.workflows.hellov2.activities.HelloV2ActivitiesImpl;
import io.temporal.worker.Worker;

public class HelloV2WorkerRegistrar implements TemporalWorkerRegistrar {


    private HelloV2WorkerRegistrar() {
    }

    @Override
    public void register(Worker worker) {
        worker.registerWorkflowImplementationTypes(HelloWorkflowV2Impl.class);
        worker.registerActivitiesImplementations(new HelloV2ActivitiesImpl());
    }

    public static HelloV2WorkerRegistrar newInstance() {
        return new HelloV2WorkerRegistrar();
    }
}
