package com.baeldung.branchprediction;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IfUnitTest {
    private static final Logger LOG = LoggerFactory.getLogger(IfUnitTest.class);

    public static final int TOP = 10000000;

    @Test
    public void majorBranchSorted() {
        test(TOP, 0.9, false);
    }

    @Test
    public void minorBranchSorted() {
        test(TOP, 0.1, false);
    }

    @Test
    public void equalBranchSorted() {
        test(TOP, 0.5, false);
    }

    @Test
    public void allBranchSorted() {
        test(TOP, 1, false);
    }

    @Test
    public void noneBranchSorted() {
        test(TOP, 0, false);
    }

    @Test
    public void majorBranchShuffled() {
        test(TOP, 0.9, true);
    }

    @Test
    public void minorBranchShuffled() {
        test(TOP, 0.1, true);
    }

    @Test
    public void equalBranchShuffled() {
        test(TOP, 0.5, true);
    }

    @Test
    public void allBranchShuffled() {
        test(TOP, 1, true);
    }

    @Test
    public void noneBranchShuffled() {
        test(TOP, 0, true);
    }

    private void test(long top, double cutoffPercentage, boolean shuffle) {
        List<Long> numbers = LongStream.range(0, top)
            .boxed()
            .collect(Collectors.toList());
        if (shuffle) {
            Collections.shuffle(numbers);
        }

        long cutoff = (long)(top * cutoffPercentage);
        long low = 0;
        long high = 0;

        long start = System.currentTimeMillis();
        for (Long number : numbers) {
            if (number < cutoff) {
                ++low;
            } else {
                ++high;
            }
        }
        long end = System.currentTimeMillis();

        LOG.info("Counted {}/{} {} numbers in {}ms", low, high, shuffle ? "shuffled" : "sorted", end - start);

    }
}
