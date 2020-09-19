/**
 *
 */
package com.baeldung.boot.ddd.event;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

@Entity
public class Aggregate2 {
    @Transient
    private final Collection<DomainEvent> domainEvents;
    @Id
    @GeneratedValue
    private long id;

    public Aggregate2() {
        domainEvents = new ArrayList<>();
    }

    @AfterDomainEventPublication
    public void clearEvents() {
        domainEvents.clear();
    }

    public void domainOperation() {
        // some domain operation
        domainEvents.add(new DomainEvent());
    }

    @DomainEvents
    public Collection<DomainEvent> events() {
        return domainEvents;
    }

}
