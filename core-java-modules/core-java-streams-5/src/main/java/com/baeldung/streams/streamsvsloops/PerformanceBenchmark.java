package com.baeldung.streams.streamsvsloops;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class PerformanceBenchmark {

    @State(Scope.Thread)
    public static class MyState {
        List<Integer> numbers;

        @Setup(Level.Trial)
        public void setUp() {
            numbers = new ArrayList<>();
            for (int i = 0; i < 1_000_000; i++) {
                numbers.add(i);
            }
        }
    }

    @Benchmark
    public int forLoopBenchmark(MyState state) {
        int sum = 0;
        for (int number : state.numbers) {
            if (number % 2 == 0) {
                sum = sum + (number * number);
            }
        }
        return sum;
    }

    @Benchmark
    public int parallelStreamBenchMark(MyState state) {
        return state.numbers.parallelStream()
          .filter(number -> number % 2 == 0)
          .map(number -> number * number)
          .reduce(0, Integer::sum);
    }

    @Benchmark
    public int concurrentForLoopBenchmark(MyState state) throws InterruptedException, ExecutionException {
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Callable<Integer>> tasks = new ArrayList<>();
        int chunkSize = state.numbers.size() / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int start = i * chunkSize;
            final int end = (i == numThreads - 1) ? state.numbers.size() : (i + 1) * chunkSize;
            tasks.add(() -> {
                int sum = 0;
                for (int j = start; j < end; j++) {
                    int number = state.numbers.get(j);
                    if (number % 2 == 0) {
                        sum = sum + (number * number);
                    }
                }
                return sum;
            });
        }

        int totalSum = 0;
        for (Future<Integer> result : executorService.invokeAll(tasks)) {
            totalSum += result.get();
        }

        executorService.shutdown();
        return totalSum;
    }

    @Benchmark
    public int streamBenchMark(MyState state) {
        return state.numbers.stream()
          .filter(number -> number % 2 == 0)
          .map(number -> number * number)
          .reduce(0, Integer::sum);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(PerformanceBenchmark.class.getSimpleName())
          .build();
        new Runner(options).run();
    }
}
