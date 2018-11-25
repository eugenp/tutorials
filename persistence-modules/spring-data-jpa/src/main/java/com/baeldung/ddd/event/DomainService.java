/**
 *
 */
package com.baeldung.ddd.event;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DomainService {
    private final ApplicationEventPublisher eventPublisher;
    private final AggregateRepository repository;

    public DomainService(AggregateRepository repository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void serviceDomainOperation(long entityId) {
        repository.findById(entityId)
            .ifPresent(entity -> {
                entity.domainOperation();
                repository.save(entity);
                eventPublisher.publishEvent(new DomainEvent());
            });
    }

}
