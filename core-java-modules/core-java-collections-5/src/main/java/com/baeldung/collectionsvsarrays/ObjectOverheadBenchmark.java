package com.baeldung.collectionsvsarrays;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@Measurement(iterations = 2, time = 10, timeUnit = TimeUnit.MINUTES)
@Warmup(iterations = 5, time = 10)
@Fork(value = 2)
public class ObjectOverheadBenchmark {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    @State(Scope.Benchmark)
    public static class Input {
        public Supplier<List<Integer>> randomNumbers = () -> RANDOM.ints().limit(10000).boxed().collect(Collectors.toList());
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void sortingPrimitiveArray(Input input, Blackhole blackhole) {
        final int[] array = input.randomNumbers.get().stream().mapToInt(Integer::intValue).toArray();
        Arrays.sort(array);
        final List<Integer> result = Arrays.stream(array).boxed().collect(Collectors.toList());
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void sortingObjectArray(Input input, Blackhole blackhole) {
        final Integer[] array = input.randomNumbers.get().toArray(new Integer[0]);
        Arrays.sort(array);
        blackhole.consume(array);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void sortingObjects(Input input, Blackhole blackhole) {
        final List<Integer> list = input.randomNumbers.get();
        Collections.sort(list);
        blackhole.consume(list);
    }
}
