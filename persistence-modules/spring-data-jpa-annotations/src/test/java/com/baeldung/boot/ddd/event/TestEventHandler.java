/**
 *
 */
package com.baeldung.boot.ddd.event;

import org.springframework.transaction.event.TransactionalEventListener;

import com.baeldung.boot.ddd.event.DomainEvent;

interface TestEventHandler {
    @TransactionalEventListener
    void handleEvent(DomainEvent event);

}
