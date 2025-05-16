package com.baeldung.streams.gatherer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

public class SlidingWindowGatherer implements Gatherer<Integer, ArrayList<Integer>, List<Integer>> {

    @Override
    public Supplier<ArrayList<Integer>> initializer() {
        return ArrayList::new;
    }

    @Override
    public Integrator<ArrayList<Integer>, Integer, List<Integer>> integrator() {
        return new Integrator<>() {
            @Override
            public boolean integrate(ArrayList<Integer> state, Integer element, Downstream<? super List<Integer>> downstream) {
                state.add(element);
                if (state.size() == 3) {
                    downstream.push(new ArrayList<>(state));
                    state.removeFirst();
                }
                return true;
            }
        };
    }

    @Override
    public BiConsumer<ArrayList<Integer>, Downstream<? super List<Integer>>> finisher() {
        return (state, downstream) -> {
            if (state.size()==3) {
                downstream.push(new ArrayList<>(state));
            }
        };

    }
}
