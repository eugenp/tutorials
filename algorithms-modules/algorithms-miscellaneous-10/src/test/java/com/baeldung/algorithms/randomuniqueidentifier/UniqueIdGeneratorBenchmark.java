package com.baeldung.algorithms.randomuniqueidentifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/*
 * For Logging
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniqueIdGeneratorBenchmark {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueIdGeneratorBenchmark.class);

    private static final int WARMUP_RUNS = 5;
    private static final int TEST_RUNS = 10;
    private static final int OPERATIONS_PER_RUN = 10_000;

    public static void main(String[] args) {
        UniqueIdGenerator generator = new UniqueIdGenerator();
        Set<String> smallSet = new HashSet<>();
        Set<String> largeSet = new HashSet<>();

        LOGGER.debug("---> Preparing Large Set for collision testing <---");
        for (int i = 0; i < 1_000_000; i++) {
            largeSet.add(UUID.randomUUID().toString().substring(0, 8));
        }

        LOGGER.debug("---> Starting Warmup Phase <---");
        runSuite(generator, smallSet, largeSet, WARMUP_RUNS, true);

        LOGGER.debug("\n---> Final Performance Comparison <---");
        runSuite(generator, smallSet, largeSet, TEST_RUNS, false);
    }

    private static void runSuite(UniqueIdGenerator generator, Set<String> smallSet, Set<String> largeSet, int runs, boolean isWarmup) {
        // I set  Custom Generator over small set
        List<Long> customSmall = runBenchmark(() -> generator.generateUniqueId(smallSet), runs);
        if (!isWarmup) calculateStats(customSmall, "Custom Generator (Small Set)");

        // Custom Generator (over 1M Set)
        List<Long> customLarge = runBenchmark(() -> generator.generateUniqueId(largeSet), runs);
        if (!isWarmup) calculateStats(customLarge, "Custom Generator (1M Set)");

        // Standard java.util.UUID
        List<Long> uuidResults = runBenchmark(() -> UUID.randomUUID().toString(), runs);
        if (!isWarmup) calculateStats(uuidResults, "Standard java.util.UUID");

        // Timestamp-based (System.nanoTime + random suffix) no lookup so fastest
        List<Long> timestampResults = runBenchmark(() -> {
            String id = Long.toString(System.nanoTime(), 36) +
                Integer.toString(ThreadLocalRandom.current().nextInt(1000), 36);
        }, runs);
        if (!isWarmup) calculateStats(timestampResults, "Timestamp-based (Hybrid)");
    }

    private static List<Long> runBenchmark(Runnable task, int runs) {
        List<Long> timings = new ArrayList<>();
        for (int i = 0; i < runs; i++) {
            long start = System.nanoTime();
            for (int j = 0; j < OPERATIONS_PER_RUN; j++) {
                task.run();
            }
            long end = System.nanoTime();
            timings.add(end - start);
        }
        return timings;
    }

    private static void calculateStats(List<Long> timings, String label) {
        int n = timings.size();
        double totalNanosecs = 0;
        for (long t : timings) totalNanosecs += t;
        double meanNanosecs = totalNanosecs / n;

        double sumOfSquares = 0;
        for (long t : timings) {
            sumOfSquares += Math.pow(t - meanNanosecs, 2);
        }
        double stdDevNanosecs = Math.sqrt(sumOfSquares / n);

        double avgUsPerOp = (meanNanosecs / OPERATIONS_PER_RUN) / 1000.0;
        double stdDevUsPerOp = (stdDevNanosecs / OPERATIONS_PER_RUN) / 1000.0;

        //LOGGER.info(String.format("%-30s | Mean: %8.3f µs/op | StdDev: %8.3f µs/op", label, avgUsPerOp, stdDevUsPerOp));
        LOGGER.info(String.format("%-30s | Mean: %8.3f (ms) | StdDev: %8.3f (ms)", label, meanNanosecs/1000000.0, stdDevNanosecs/1000000.0));
    }
}