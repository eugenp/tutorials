package com.baeldung.parallel_collectors;

import com.pivovarit.collectors.ParallelCollectors;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ParallelCollectorsVirtualThreadsManualTest {

    // increase the number of parallel processes to find the max number of threads on your machine
    @Test
    public void processInParallelOnOSThreads() {
        int parallelProcesses = 5_000;

        var e = Executors.newFixedThreadPool(parallelProcesses);

        var result = timed(() -> Stream.iterate(0, i -> i + 1).limit(parallelProcesses)
          .collect(ParallelCollectors.parallel(i -> fetchById(i), toList(), e, parallelProcesses))
          .join());

        System.out.println(result);
    }

    @Test
    public void processInParallelOnVirtualThreads() {
        int parallelProcesses = 100_000;

        var result = timed(() -> Stream.iterate(0, i -> i + 1).limit(parallelProcesses)
          .collect(ParallelCollectors.parallel(i -> fetchById(i), toList()))
          .join());

        System.out.println(result);
    }

    @Test
    public void processInParallelOnVirtualThreadsParallelCollectors2() {
        int parallelProcesses = 100_000;

        var result = timed(() -> Stream.iterate(0, i -> i + 1).limit(parallelProcesses)
          .collect(ParallelCollectors.parallel(i -> fetchById(i), toList(), Executors.newVirtualThreadPerTaskExecutor(), Integer.MAX_VALUE))
          .join());

        System.out.println(result);
    }

    private static String fetchById(int id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // ignore shamelessly
        }

        return "user-" + id;
    }
    private static <T> T timed(Supplier<T> supplier) {
        var before = Instant.now();
        T result = supplier.get();
        var after = Instant.now();
        System.out.printf("Execution time: %d ms%n", Duration.between(before, after).toMillis());
        return result;
    }
}
