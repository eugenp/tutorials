package com.baeldung.reactor.creation;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Consumer;

public class SequenceCreator {
    public Consumer<List<Integer>> consumer;

    public Flux<Integer> createNumberSequence() {
        return Flux.create(sink -> SequenceCreator.this.consumer = items -> items.forEach(sink::next));
    }
}
