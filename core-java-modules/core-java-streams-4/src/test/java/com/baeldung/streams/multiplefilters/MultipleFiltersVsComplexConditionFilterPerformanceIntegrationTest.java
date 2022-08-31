package com.baeldung.streams.multiplefilters;

import java.text.MessageFormat;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

public class MultipleFiltersVsComplexConditionFilterPerformanceIntegrationTest {

//    this test is slow, avoid running it on pipeline
    @Test
    public void measureProcessingTimeForEachSolution() {

        averageMultipleMeasurements("Stream with Multiple Filters", this::measureTimeForMultipleFilters, 10_000);
        averageMultipleMeasurements("Stream with Multiple Filters", this::measureTimeForMultipleFilters, 100_000);
        averageMultipleMeasurements("Stream with Multiple Filters", this::measureTimeForMultipleFilters, 10_00_000);
        averageMultipleMeasurements("Stream with Multiple Filters", this::measureTimeForMultipleFilters, 1_0000_000);

        averageMultipleMeasurements("Stream with Complex Condition", this::measureTimeForComplexCondition, 10_000);
        averageMultipleMeasurements("Stream with Complex Condition", this::measureTimeForComplexCondition, 100_000);
        averageMultipleMeasurements("Stream with Complex Condition", this::measureTimeForComplexCondition, 10_00_000);
        averageMultipleMeasurements("Stream with Complex Condition", this::measureTimeForComplexCondition, 1_0000_000);

        averageMultipleMeasurements("Parallel Stream with Multiple Filters", this::measureTimeForParallelStreamWithMultipleFilters, 10_000);
        averageMultipleMeasurements("Parallel Stream with Multiple Filters", this::measureTimeForParallelStreamWithMultipleFilters, 100_000);
        averageMultipleMeasurements("Parallel Stream with Multiple Filters", this::measureTimeForParallelStreamWithMultipleFilters, 10_00_000);
        averageMultipleMeasurements("Parallel Stream with Multiple Filters", this::measureTimeForParallelStreamWithMultipleFilters, 1_0000_000);

        averageMultipleMeasurements("Parallel Stream with Complex Condition", this::measureTimeForParallelStreamWithComplexCondition, 10_000);
        averageMultipleMeasurements("Parallel Stream with Complex Condition", this::measureTimeForParallelStreamWithComplexCondition, 100_000);
        averageMultipleMeasurements("Parallel Stream with Complex Condition", this::measureTimeForParallelStreamWithComplexCondition, 10_00_000);
        averageMultipleMeasurements("Parallel Stream with Complex Condition", this::measureTimeForParallelStreamWithComplexCondition, 1_0000_000);

        averageMultipleMeasurements("Old For Loop with Complex Condition", this::measureTimeForOlfForLoopWithComplexCondition, 10_000);
        averageMultipleMeasurements("Old For Loop with Complex Condition", this::measureTimeForOlfForLoopWithComplexCondition, 100_000);
        averageMultipleMeasurements("Old For Loop with Complex Condition", this::measureTimeForOlfForLoopWithComplexCondition, 10_00_000);
        averageMultipleMeasurements("Old For Loop with Complex Condition", this::measureTimeForOlfForLoopWithComplexCondition, 1_0000_000);

    }

    private void averageMultipleMeasurements(String measurementName, Function<Integer, Long> measurement, int range) {
        double avgTime = IntStream.range(0, 100)
            .mapToLong(i -> measurement.apply(range))
            .average()
            .orElseThrow();

        System.out.println(MessageFormat.format("{0}; Stream size: {1}; Processing Time (ms): {2}", measurementName, range, avgTime));
    }

    public long measureTimeForMultipleFilters(int range) {
        StopWatch watch = new StopWatch();
        watch.start();

        IntStream.range(0, range)
            .boxed()
            .filter(i -> i != 10)
            .filter(i -> i != 20)
            .filter(i -> i != 30)
            .filter(i -> i != 40)
            .filter(i -> i != 50)
            .filter(i -> i != 60)
            .count();

        watch.stop();
        return watch.getTime();
    }

    public long measureTimeForParallelStreamWithMultipleFilters(int range) {
        StopWatch watch = new StopWatch();
        watch.start();

        IntStream.range(0, range)
            .boxed()
            .parallel()
            .filter(i -> i != 10)
            .filter(i -> i != 20)
            .filter(i -> i != 30)
            .filter(i -> i != 40)
            .filter(i -> i != 50)
            .filter(i -> i != 60)
            .count();

        watch.stop();
        return watch.getTime();
    }

    public long measureTimeForComplexCondition(int range) {
        StopWatch watch = new StopWatch();
        watch.start();

        IntStream.range(0, range)
            .boxed()
            .filter(i -> i != 10 && i != 20 && i != 30 && i != 40 && i != 50 && i != 60)
            .count();

        watch.stop();
        return watch.getTime();
    }

    public long measureTimeForParallelStreamWithComplexCondition(int range) {
        StopWatch watch = new StopWatch();
        watch.start();

        IntStream.range(0, range)
            .boxed()
            .parallel()
            .filter(i -> i != 10 && i != 20 && i != 30 && i != 40 && i != 50 && i != 60)
            .count();

        watch.stop();
        return watch.getTime();
    }

    public long measureTimeForOlfForLoopWithComplexCondition(int range) {
        StopWatch watch = new StopWatch();
        watch.start();

        int count = 0;
        for (int i = 0; i <= range; i++) {
            if (i != 10 && i != 20 && i != 30 && i != 40 && i != 50 && i != 60) {
                count++;
            }
        }

        watch.stop();
        return watch.getTime();
    }
}