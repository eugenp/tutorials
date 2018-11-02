package com.baeldung.reactor;

import java.util.concurrent.atomic.AtomicInteger;

import reactor.core.publisher.Flux;

public class ProgrammaticSequences {

    public Flux<String> statefulImutableGenerate() {
        return Flux.generate(() -> 1, (state, sink) -> {
            sink.next("2 + " + state + " = " + (2 + state));
            if (state == 101)
                sink.complete();
            return state + 1;
        });
    }

    public Flux<String> statefulMutableGenerate() {
        return Flux.generate(AtomicInteger::new, (state, sink) -> {
            int i = state.getAndIncrement();
            sink.next("2 + " + i + " = " + (2 + i));
            if (i == 101)
                sink.complete();
            return state;
        });
    }

    public Flux<String> handle() {
        return Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .handle((i, sink) -> {
                String animal = "Elephant nr " + i;
                if (i % 2 == 0) {
                    sink.next(animal);
                }
            });
    }

}
