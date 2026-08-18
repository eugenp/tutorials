package com.baeldung.streams.gatherer;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

public class SlidingWindowGatherer implements Gatherer<Integer, Deque<Integer>, List<Integer>> {

    private static final int WINDOW_SIZE = 3;

    @Override
    public Supplier<Deque<Integer>> initializer() {
        return ArrayDeque::new;
    }

    @Override
    public Integrator<Deque<Integer>, Integer, List<Integer>> integrator() {
        return new Integrator<>() {
            @Override
            public boolean integrate(Deque<Integer> state, Integer element, Downstream<? super List<Integer>> downstream) {
                state.addLast(element);
                if (state.size() > WINDOW_SIZE) {
                    state.removeFirst();
                }
                if (state.size() == WINDOW_SIZE) {
                    downstream.push(new ArrayList<>(state));
                }
                return true;
            }
        };
    }
}