package com.baeldung.springevents.asynchronous;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Async;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;
import com.baeldung.springevents.synchronous.CustomSpringEvent;

@Component
public class AnnotationDrivenEventListener {

    @EventListener
    @Async
    public void handleAsyncEvent(CustomSpringEvent event) {
        System.out.println("Handle event asynchronously: " + event.getMessage());
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    void handleCustom(CustomSpringEvent event) { 
        /* … */ 
    }
}
