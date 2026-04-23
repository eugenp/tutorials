package com.baeldung.reactor.generate.create;

import reactor.core.publisher.Flux;

public class CharacterGenerator {
    public Flux<Character> generateCharacters() {
        return Flux.generate(() -> 97, (state, sink) -> {
            char value = (char) state.intValue();
            sink.next(value);
            if (value == 'z') {
                sink.complete();
            }
            return state + 1;
        });
    }
}
