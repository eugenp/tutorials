package org.baeldung.spring.webflux.securityincidents.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.baeldung.spring.webflux.securityincidents.domain.SecurityEvent;
import org.baeldung.spring.webflux.securityincidents.service.SecurityEventGeneratorService;

@Component
public class SecurityEventHandler {
    @Autowired
    private SecurityEventGeneratorService securityEventGeneratorService;

    public Mono<ServerResponse> getSecurityEvents(ServerRequest request) {
        int size = Integer.parseInt(request.pathVariable("size"));

        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(securityEventGeneratorService.getSecurityEventStream().take(size), SecurityEvent.class);
    }

    public Mono<ServerResponse> getSecurityEventsStream(ServerRequest request) {

        return ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(securityEventGeneratorService.getSecurityEventStream(), SecurityEvent.class);
    }
}