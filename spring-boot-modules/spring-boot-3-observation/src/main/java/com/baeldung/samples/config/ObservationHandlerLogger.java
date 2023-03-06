package com.baeldung.samples.config;

import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ObservationHandlerLogger {

    private static final Logger log = LoggerFactory.getLogger(ObservationHandlerLogger.class);

    private static String toString(ObservationHandler<?> handler) {
        return handler.getClass().getName() + " [ " + handler + "]";
    }

    @EventListener(ContextRefreshedEvent.class)
    public void logObservationHandlers(ContextRefreshedEvent evt) {
        evt.getApplicationContext().getBeansOfType(ObservationHandler.class)
          .values()
          .stream()
          .map(ObservationHandlerLogger::toString)
          .forEach(log::info);
    }

}
