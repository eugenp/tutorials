package com.baeldung.reactor.creation;

import java.util.List;
import java.util.function.Consumer;

import reactor.core.publisher.Flux;

public class CharacterCreator {
    
    public Consumer<List<Character>> consumer;

    public Flux<Character> createCharacterSequence() {
        return Flux.create(sink -> CharacterCreator.this.consumer = items -> items.forEach(sink::next));
    }
}
