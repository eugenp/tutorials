package com.baeldung.streams.gatherer;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

public class SlidingWindowGatherer implements Gatherer<Integer, Deque<Integer>, List<Integer>> {

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
                if (state.size() == 3) {
                    downstream.push(new ArrayList<>(state));
                    state.removeFirst();
                }
                return true;
            }
        };
    }

    @Override
    public BiConsumer<Deque<Integer>, Downstream<? super List<Integer>>> finisher() {
        return (state, downstream) -> {};
    }
}
