package com.baeldung.temporal.worker;

import io.temporal.worker.Worker;

/**
 * Interface for registering Workflows and Activities to a Temporal Worker.
 */
public interface TemporalWorkerRegistrar {
    void register(Worker worker);
}
