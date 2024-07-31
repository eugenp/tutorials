package com.baeldung.samples.config;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.tck.AnyContextObservationHandlerCompatibilityKit;

class SimpleLoggingHandlerUnitTest
  extends AnyContextObservationHandlerCompatibilityKit {

    SimpleLoggingHandler handler = new SimpleLoggingHandler();

    @Override
    public ObservationHandler<Observation.Context> handler() {
        return handler;
    }

}
