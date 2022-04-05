package com.baeldung.branchprediction;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortingUnitTest {
    private static final Logger LOG = LoggerFactory.getLogger(SortingUnitTest.class);
    public static final int BIG = 10000000;
    public static final int SMALL = 100000;

    @Test
    public void sortedBig() {
        test(BIG, false);
    }

    @Test
    public void shuffledBig() {
        test(BIG, true);
    }

    @Test
    public void sortedSmall() {
        test(SMALL, false);
    }

    @Test
    public void shuffledSmall() {
        test(SMALL, true);
    }

    private void test(long top, boolean shuffle) {
        List<Long> numbers = LongStream.range(0, top)
            .boxed()
            .collect(Collectors.toList());

        if (shuffle) {
            Collections.shuffle(numbers);
        }

        long cutoff = top / 2;
        long count = 0;

        long start = System.currentTimeMillis();
        for (Long number : numbers) {
            if (number < cutoff) {
                ++count;
            }
        }
        long end = System.currentTimeMillis();

        LOG.info("Counted {}/{} {} numbers in {}ms",
            count, top, shuffle ? "shuffled" : "sorted", end - start);
    }
}
