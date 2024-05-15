package com.baeldung.percentile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class CalculatePercentileUnitTest {

    public static <T extends Comparable<T>> T getPercentile(Collection<T> input, double percentile) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("The input dataset cannot be null or empty.");
        }
        if (percentile < 0 || percentile > 100) {
            throw new IllegalArgumentException("Percentile must be between 0 and 100(exclusive)");
        }
        List<T> sortedList = input.stream()
            .sorted()
            .collect(Collectors.toList());

        int rank = percentile == 0 ? 1 : (int) Math.ceil(percentile / 100.0 * input.size());
        return sortedList.get(rank - 1);
    }

    @Test
    void whenCallingGetPercentileWithAList_thenGetExpectedResult() {
        assertThrows(IllegalArgumentException.class, () -> getPercentile(List.of(1, 2, 3), -1));
        assertThrows(IllegalArgumentException.class, () -> getPercentile(List.of(1, 2, 3), 101));

        List<Integer> list100 = IntStream.rangeClosed(1, 100)
            .boxed()
            .collect(Collectors.toList());
        Collections.shuffle(list100);

        assertEquals(1, getPercentile(list100, 0));
        assertEquals(10, getPercentile(list100, 10));
        assertEquals(25, getPercentile(list100, 25));
        assertEquals(50, getPercentile(list100, 50));
        assertEquals(76, getPercentile(list100, 75.3));
        assertEquals(100, getPercentile(list100, 100));

        List<Integer> list8 = IntStream.of(-1, 200, 30, 42, -5, 7, 8, 92)
            .boxed()
            .collect(Collectors.toList());

        assertEquals(-5, getPercentile(list8, 0));
        assertEquals(-5, getPercentile(list8, 10));
        assertEquals(-1, getPercentile(list8, 25));
        assertEquals(8, getPercentile(list8, 50));
        assertEquals(92, getPercentile(list8, 75.3));
        assertEquals(200, getPercentile(list8, 100));
    }

    @Test
    void whenCallingGetPercentileWithAnArray_thenGetExpectedResult() {

        long[] theArray = new long[] { -1, 200, 30, 42, -5, 7, 8, 92 };

        //convert the long[] array to a List<Long>
        List<Long> list8 = Arrays.stream(theArray)
            .boxed()
            .toList();

        assertEquals(-5, getPercentile(list8, 0));
        assertEquals(-5, getPercentile(list8, 10));
        assertEquals(-1, getPercentile(list8, 25));
        assertEquals(8, getPercentile(list8, 50));
        assertEquals(92, getPercentile(list8, 75.3));
        assertEquals(200, getPercentile(list8, 100));
    }
}