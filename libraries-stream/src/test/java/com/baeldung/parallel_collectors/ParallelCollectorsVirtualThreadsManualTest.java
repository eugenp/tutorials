package com.baeldung.parallel_collectors;

import com.pivovarit.collectors.ParallelCollectors;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ParallelCollectorsVirtualThreadsManualTest {

    private static final Logger log = LoggerFactory.getLogger(ParallelCollectorsVirtualThreadsManualTest.class);

    // increase the number of parallel processes to find the max number of threads on your machine
    @Test
    public void givenParallelism_whenUsingOSThreads_thenShouldRunOutOfThreads() {
        int parallelProcesses = 50_000;

        var e = Executors.newFixedThreadPool(parallelProcesses);

        var result = timed(() -> Stream.iterate(0, i -> i + 1).limit(parallelProcesses)
          .collect(ParallelCollectors.parallel(i -> fetchById(i), toList(), e, parallelProcesses))
          .join());

        log.info("{}", result);
    }

    @Test
    public void givenParallelism_whenUsingVThreads_thenShouldProcessInParallel() {
        int parallelProcesses = 1000_000;

        var result = timed(() -> Stream.iterate(0, i -> i + 1).limit(parallelProcesses)
          .collect(ParallelCollectors.parallel(i -> fetchById(i), toList()))
          .join());

        log.info("{}", result);
    }

    @Test
    public void givenParallelismAndPCollectors2_whenUsingVThreads_thenShouldProcessInParallel() {
        int parallelProcesses = 1000_000;

        var result = timed(() -> Stream.iterate(0, i -> i + 1).limit(parallelProcesses)
          .collect(ParallelCollectors.parallel(i -> fetchById(i), toList(), Executors.newVirtualThreadPerTaskExecutor(), Integer.MAX_VALUE))
          .join());

        log.info("{}", result);
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
        log.info("Execution time: {} ms", Duration.between(before, after).toMillis());
        return result;
    }
}
