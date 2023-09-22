package com.bealdung.rsocket.requester;

import org.springframework.messaging.rsocket.service.RSocketExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageClient {

    @RSocketExchange("MyDestination")
    Mono<String> sendMessage(Mono<String> input);

    @RSocketExchange("Counter")
    Flux<String> Counter();

    @RSocketExchange("Warning")
    Mono<Void> Warning(Mono<String> warning);

    @RSocketExchange("channel")
    Flux<String> channel(Flux<String> input);
}
