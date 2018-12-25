/**
 *
 */
package com.baeldung.ddd.event;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.context.ApplicationEventPublisher;

@Entity
class Aggregate {
    @Transient
    private ApplicationEventPublisher eventPublisher;
    @Id
    private long id;

    private Aggregate() {
    }

    Aggregate(long id, ApplicationEventPublisher eventPublisher) {
        this.id = id;
        this.eventPublisher = eventPublisher;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DomainEntity [id=" + id + "]";
    }

    void domainOperation() {
        // some business logic
        if (eventPublisher != null) {
            eventPublisher.publishEvent(new DomainEvent());
        }
    }

    long getId() {
        return id;
    }

}
