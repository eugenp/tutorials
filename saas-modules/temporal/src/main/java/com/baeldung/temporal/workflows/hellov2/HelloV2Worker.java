package com.baeldung.temporal.workflows.hellov2;

import com.baeldung.temporal.worker.TemporalWorker;
import com.baeldung.temporal.workflows.hellov2.activities.HelloV2ActivitiesImpl;
import io.temporal.worker.Worker;

public class HelloV2Worker implements TemporalWorker {

    private Worker worker;

    @Override
    public void init(Worker worker) {
        this.worker = worker;
        worker.registerWorkflowImplementationTypes(HelloWorkflowV2Impl.class);
        worker.registerActivitiesImplementations(new HelloV2ActivitiesImpl());
    }

}
