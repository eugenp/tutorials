package com.baeldung.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Processor {

    public void processSerially() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Thread.sleep(10);
        }
    }

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
