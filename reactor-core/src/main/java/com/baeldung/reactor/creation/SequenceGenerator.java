package com.baeldung.reactor.creation;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceGenerator {
    public int finalState;

    public Flux<Integer> generateFibonacciSequence(int limit) {
        return Flux.generate(
                () -> Tuples.of(0, 1),
                (state, sink) -> {
                    sink.next(state.getT1());
                    if (state.getT2() > limit) {
                        sink.complete();
                    }
                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                }
        );
    }

    public Flux<Integer> generateNumbersInAscendingOrder(int limit) {
        return Flux.generate(
                AtomicInteger::new,
                (state, sink) -> {
                    sink.next(state.getAndIncrement());
                    if (state.get() > limit) {
                        sink.complete();
                    }
                    return state;
                },
                state -> finalState = state.updateAndGet(s -> 0));
    }
}
