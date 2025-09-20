package com.baeldung.temporal.workflows.hello;

import com.baeldung.temporal.worker.TemporalWorkerRegistrar;
import com.baeldung.temporal.workflows.hello.activities.SayHelloActivityImpl;
import io.temporal.worker.Worker;

public class HelloWorkflowRegistrar implements TemporalWorkerRegistrar {

    private HelloWorkflowRegistrar() {}

    @Override
    public void register(Worker worker) {
        worker.registerWorkflowImplementationTypes(HelloWorkflowImpl.class);
        worker.registerActivitiesImplementations(new SayHelloActivityImpl());
    }

    public static HelloWorkflowRegistrar newInstance() {
        return new HelloWorkflowRegistrar();
    }
}
