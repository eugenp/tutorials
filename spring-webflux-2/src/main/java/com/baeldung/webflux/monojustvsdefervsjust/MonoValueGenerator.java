package com.baeldung.webflux.monojustvsdefervsjust;

import reactor.core.publisher.Mono;

public class MonoValueGenerator {

    public Mono<String> getStaticMono() {
        return Mono.just("Static Value");
    }

    public Mono<String> getGreetingMono(String name) {
        return Mono.defer(() -> {
            String greeting = "Hello, " + name;
            return Mono.just(greeting);
        });
    }

    public Mono<String> performOperation(boolean success) {
        return Mono.create(sink -> {
            if (success) {
                sink.success("Operation Success");
            } else {
                sink.error(new RuntimeException("Operation Failed"));
            }
        });
    }

}
