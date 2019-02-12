package com.baeldung.reactor.creation;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Consumer;

public class SequenceCreator {
    public Consumer<List<String>> consumer;

    public Flux<String> createStringSequence() {
        return Flux.create(sink -> SequenceCreator.this.consumer = items -> items.forEach(sink::next));
    }
}
