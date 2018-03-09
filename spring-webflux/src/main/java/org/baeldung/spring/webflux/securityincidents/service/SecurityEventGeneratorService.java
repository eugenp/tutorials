package org.baeldung.spring.webflux.securityincidents.service;

import org.baeldung.spring.webflux.securityincidents.domain.SecurityEvent;

import reactor.core.publisher.Flux;

public interface SecurityEventGeneratorService {
    Flux<SecurityEvent> getSecurityEventStream();
}
