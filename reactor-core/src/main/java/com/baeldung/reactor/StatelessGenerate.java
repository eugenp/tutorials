package com.baeldung.reactor;

import reactor.core.publisher.Flux;

public class StatelessGenerate {

    public Flux<String> statelessGenerate() {
        return Flux.generate((sink) -> {
            sink.next("hello");
        });
    }
}
