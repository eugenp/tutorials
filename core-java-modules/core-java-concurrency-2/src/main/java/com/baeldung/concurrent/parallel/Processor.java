package com.baeldung.concurrent.parallel;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Processor {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void processSerially() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void processParallelyWithExecutorService() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, executorService);
            futures.add(future);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void processParallelyWithStream() {
        IntStream.range(0, 100)
          .parallel()
          .forEach(i -> {
              try {
                  Thread.sleep(10);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          });
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void processParallelyWithStreamSupport() {
        Iterable<Integer> iterable = () -> IntStream.range(0, 100).iterator();
        Stream<Integer> stream = StreamSupport.stream(iterable.spliterator(), true);
        stream.forEach(i -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
