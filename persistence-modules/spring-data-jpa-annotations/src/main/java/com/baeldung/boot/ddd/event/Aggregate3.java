/**
 *
 */
package com.baeldung.boot.ddd.event;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
public class Aggregate3 extends AbstractAggregateRoot<Aggregate3> {
    @Id
    @GeneratedValue
    private long id;

    public void domainOperation() {
        // some domain operation
        registerEvent(new DomainEvent());
    }

}
