package com.baeldung.temporal.workflows.hello;

import com.baeldung.temporal.worker.TemporalWorker;
import com.baeldung.temporal.workflows.hello.activities.SayHelloActivityImpl;
import io.temporal.worker.Worker;

public class SayHelloWorker implements TemporalWorker {

    private Worker worker;

    @Override
    public void init(Worker worker) {
        this.worker = worker;
        worker.registerWorkflowImplementationTypes(HelloWorkflowImpl.class);
        worker.registerActivitiesImplementations(new SayHelloActivityImpl());
    }

}
