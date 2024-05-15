package com.baeldung.webflux.logging;

import reactor.core.publisher.Flux;

public class WebFluxLoggingExample {

    public static void main(String[] args) {
        Flux<Integer> reactiveStream = Flux.range(1, 5).log();

        reactiveStream.subscribe();

        reactiveStream = Flux.range(1, 5).log().take(3);

        reactiveStream.subscribe();

        reactiveStream = Flux.range(1, 5).take(3).log();

        reactiveStream.subscribe();
    }

}
