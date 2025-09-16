package com.baeldung.temporal.worker;

import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;

/**
 *
 */
public interface TemporalWorker{
    void init(Worker worker);

}
