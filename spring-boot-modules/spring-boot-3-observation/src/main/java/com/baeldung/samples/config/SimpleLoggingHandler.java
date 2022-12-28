package com.baeldung.samples.config;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleLoggingHandler implements ObservationHandler<Observation.Context> {

    private static final Logger log = LoggerFactory.getLogger(SimpleLoggingHandler.class);

    private static String toString(Observation.Context context) {
        return null == context ? "(no context)" : context.getName()
          + " (" + context.getClass().getName() + "@" + System.identityHashCode(context) + ")";
    }

    private static String toString(Observation.Event event) {
        return null == event ? "(no event)" : event.getName();
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }

    @Override
    public void onStart(Observation.Context context) {
        log.info("Starting context " + toString(context));
    }

    @Override
    public void onError(Observation.Context context) {
        log.info("Error for context " + toString(context));
    }

    @Override
    public void onEvent(Observation.Event event, Observation.Context context) {
        log.info("Event for context " + toString(context) + " [" + toString(event) + "]");
    }

    @Override
    public void onScopeOpened(Observation.Context context) {
        log.info("Scope opened for context " + toString(context));

    }

    @Override
    public void onScopeClosed(Observation.Context context) {
        log.info("Scope closed for context " + toString(context));
    }

    @Override
    public void onStop(Observation.Context context) {
        log.info("Stopping context " + toString(context));
    }

}
