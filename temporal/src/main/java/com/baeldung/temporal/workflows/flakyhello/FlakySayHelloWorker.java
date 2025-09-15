package com.baeldung.temporal.workflows.flakyhello;

import com.baeldung.temporal.worker.TemporalWorker;
import com.baeldung.temporal.workflows.flakyhello.activities.FlakySayHelloActivityImpl;
import io.temporal.worker.Worker;

public class FlakySayHelloWorker implements TemporalWorker {

    private Worker worker;

    @Override
    public void init(Worker worker) {
        this.worker = worker;
        worker.registerWorkflowImplementationTypes(FlakyHelloWorkflowImpl.class);
        worker.registerActivitiesImplementations(new FlakySayHelloActivityImpl());
    }

}
