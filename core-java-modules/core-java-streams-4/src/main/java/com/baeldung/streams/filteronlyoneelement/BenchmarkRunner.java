package com.baeldung.streams.filteronlyoneelement;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @State(Scope.Benchmark)
    public static class MyState {
        final Stream<Integer> getIntegers() {
            return IntStream.range(1, 1000000)
                .boxed();
        }

        final Predicate<Integer> PREDICATE = i -> i == 751879;
    }

    @Benchmark
    public void evaluateFindUniqueElementMatchingPredicate_WithReduction(Blackhole blackhole, MyState state) {
        blackhole.consume(FilterUtils.findUniqueElementMatchingPredicate_WithReduction(state.getIntegers(), state.PREDICATE));
    }

    @Benchmark
    public void evaluateFindUniqueElementMatchingPredicate_WithCollectingAndThen(Blackhole blackhole, MyState state) {
        blackhole.consume(FilterUtils.findUniqueElementMatchingPredicate_WithCollectingAndThen(state.getIntegers(), state.PREDICATE));
    }

    @Benchmark
    public void evaluateGetUniqueElementMatchingPredicate_WithReduction(Blackhole blackhole, MyState state) {
        try {
            FilterUtils.getUniqueElementMatchingPredicate_WithReduction(state.getIntegers(), state.PREDICATE);
        } catch (IllegalStateException exception) {
            blackhole.consume(exception);
        }
    }

    @Benchmark
    public void evaluateGetUniqueElementMatchingPredicate_WithCollectingAndThen(Blackhole blackhole, MyState state) {
        try {
            FilterUtils.getUniqueElementMatchingPredicate_WithCollectingAndThen(state.getIntegers(), state.PREDICATE);
        } catch (IllegalStateException exception) {
            blackhole.consume(exception);
        }
    }

}
