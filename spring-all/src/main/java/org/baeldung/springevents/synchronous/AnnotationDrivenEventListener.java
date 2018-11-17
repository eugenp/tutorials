package org.baeldung.springevents.synchronous;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class AnnotationDrivenEventListener {

    // for tests
    private boolean hitContextStartedHandler = false;
    private boolean hitSuccessfulEventHandler = false;
    private boolean hitCustomEventHandler = false;

    @EventListener
    public void handleContextStart(final ContextStartedEvent cse) {
        System.out.println("Handling context started event.");
        hitContextStartedHandler = true;
    }

    @EventListener(condition = "#event.success")
    public void handleSuccessful(final GenericSpringEvent<String> event) {
        System.out.println("Handling generic event (conditional): " + event.getWhat());
        hitSuccessfulEventHandler = true;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleCustom(final CustomSpringEvent event) {
        System.out.println("Handling event inside a transaction BEFORE COMMIT.");
        hitCustomEventHandler = true;
    }

    boolean isHitContextStartedHandler() {
        return hitContextStartedHandler;
    }

    boolean isHitSuccessfulEventHandler() {
        return hitSuccessfulEventHandler;
    }

    boolean isHitCustomEventHandler() {
        return hitCustomEventHandler;
    }
}
