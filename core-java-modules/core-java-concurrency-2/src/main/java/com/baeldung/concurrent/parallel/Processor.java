package com.baeldung.concurrent.parallel;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Processor {

    @BenchmarkMode(Mode.AverageTime)
    public void processSerially() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
        }
    }

    @BenchmarkMode(Mode.AverageTime)
    public void processParallelyWithExecutor() throws InterruptedException, ExecutionException {
        // create a thread pool of 10 threads
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<?>> futures = new ArrayList<>();
        // submit tasks to the thread pool
        for (int i = 0; i < 100; i++) {
            Future<?> future = executorService.submit(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            futures.add(future);
        }

        // wait for all tasks to complete
        for (Future<?> future : futures) {
            future.get();
        }

        // shut down the executor service - free all threads
        executorService.shutdown();
    }

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
