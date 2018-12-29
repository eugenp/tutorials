/**
 *
 */
package com.baeldung.ddd.event;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@SpringBootTest
class Aggregate2EventsIntegrationTest {
    @MockBean
    private TestEventHandler eventHandler;
    @Autowired
    private Aggregate2Repository repository;

    // @formatter:off
    @DisplayName("given aggregate with @AfterDomainEventPublication,"
        + " when do domain operation and save twice,"
        + " then an event is published only for the first time")
    // @formatter:on
    @Test
    void afterDomainEvents() {
        // given
        Aggregate2 aggregate = new Aggregate2();

        // when
        aggregate.domainOperation();
        repository.save(aggregate);
        repository.save(aggregate);

        // then
        verify(eventHandler, times(1)).handleEvent(any(DomainEvent.class));
    }

    @BeforeEach
    void beforeEach() {
        repository.deleteAll();
    }

    // @formatter:off
    @DisplayName("given aggregate with @DomainEvents,"
        + " when do domain operation and save,"
        + " then an event is published")
    // @formatter:on
    @Test
    void domainEvents() {
        // given
        Aggregate2 aggregate = new Aggregate2();

        // when
        aggregate.domainOperation();
        repository.save(aggregate);

        // then
        verify(eventHandler, times(1)).handleEvent(any(DomainEvent.class));
    }

}
