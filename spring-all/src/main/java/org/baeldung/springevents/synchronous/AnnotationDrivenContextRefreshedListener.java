package org.baeldung.springevents.synchronous;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AnnotationDrivenContextStartedListener {

    @EventListener
    public void handleContextStart(final ContextStartedEvent cre) {
        System.out.println("Handling context started event.");
    }

    @EventListener(condition = "#event.success")
    public void handleContextRefresh(final GenericSpringEvent<String> event) {
        System.out.println("Handling generic event (conditional).");
    }

}
