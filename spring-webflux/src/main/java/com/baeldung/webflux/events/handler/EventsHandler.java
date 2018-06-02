
package com.baeldung.webflux.events.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


public interface EventsHandler<T extends ServerResponse> {
    Mono<T> handleEvent(ServerRequest request);
}
