package com.baeldung.reactor.creation;

import reactor.core.publisher.Flux;

public class SequenceGenerator {
    public char nextCharacter;

    public Flux<Integer> generateFibonacciSequence(int limit) {
        return Flux.generate(
                () -> new FibonacciState(0, 1),
                (state, sink) -> {
                    sink.next(state.getFormer());
                    if (state.getLatter() > limit) {
                        sink.complete();
                    }
                    int temp = state.getFormer();
                    state.setFormer(state.getLatter());
                    state.setLatter(temp + state.getLatter());
                    return state;
                });
    }

    public Flux<Character> generateCharacterSequence(char limit) {
        return Flux.generate(
                () -> 'A',
                (state, sink) -> {
                    sink.next(state);
                    if (state == limit) {
                        sink.complete();
                    }
                    return (char) (state + 1);
                },
                state -> nextCharacter = state
        );
    }
}
