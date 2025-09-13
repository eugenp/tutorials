package com.baeldung.streams.gatherer;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

public class NumericSumGatherer implements Gatherer<Integer, ArrayList<Integer>, Integer> {

    @Override
    public Supplier<ArrayList<Integer>> initializer() {
        return ArrayList::new;
    }

    @Override
    public Integrator<ArrayList<Integer>, Integer, Integer> integrator() {
        return new Integrator<>() {
            @Override
            public boolean integrate(ArrayList<Integer> state, Integer element, Downstream<? super Integer> downstream) {
                if (state.isEmpty()) {
                    state.add(element);
                } else {
                    state.addFirst(state.getFirst() + element);
                }
                return true;
            }
        };
    }

    @Override
    public BiConsumer<ArrayList<Integer>, Downstream<? super Integer>> finisher() {
        return (state, downstream) -> {
            if (!downstream.isRejecting() && !state.isEmpty()) {
                downstream.push(state.getFirst());
                state.clear();
            }
        };
    }
}
