package com.baeldung.algorithms.successivepairs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class SuccessivePairsUnitTest {

    @Test
    public void givenEmptyStream_whenUsingSimpleEntry_thenCollectPairs() {
        Stream<Integer> stream = Stream.empty();
        List<SimpleEntry<Integer, Integer>> pairs = SuccessivePairs.collectSuccessivePairs(stream);
        assertTrue(pairs.isEmpty());
    }

    @Test
    public void givenSingleElement_whenUsingSimpleEntry_thenCollectPairs() {
        Stream<Integer> stream = Stream.of(1);
        List<SimpleEntry<Integer, Integer>> pairs = SuccessivePairs.collectSuccessivePairs(stream);
        assertTrue(pairs.isEmpty());
    }

    @Test
    public void givenMultipleElementStream_whenUsingSimpleEntry_thenCollectPairs() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        List<SimpleEntry<Integer, Integer>> pairs = SuccessivePairs.collectSuccessivePairs(stream);
        assertEquals(4, pairs.size());

        assertEquals(new SimpleEntry<>(1, 2), pairs.get(0));
        assertEquals(new SimpleEntry<>(2, 3), pairs.get(1));
        assertEquals(new SimpleEntry<>(3, 4), pairs.get(2));
        assertEquals(new SimpleEntry<>(4, 5), pairs.get(3));
    }

    @Test
    public void givenStreamWithDuplicate_whenUsingSimpleEntry_thenCollectPairs() {
        Stream<Integer> stream = Stream.of(1, 1, 2, 2, 3, 3);
        List<SimpleEntry<Integer, Integer>> pairs = SuccessivePairs.collectSuccessivePairs(stream);
        assertEquals(5, pairs.size());

        assertEquals(new SimpleEntry<>(1, 1), pairs.get(0));
        assertEquals(new SimpleEntry<>(1, 2), pairs.get(1));
        assertEquals(new SimpleEntry<>(2, 2), pairs.get(2));
        assertEquals(new SimpleEntry<>(2, 3), pairs.get(3));
        assertEquals(new SimpleEntry<>(3, 3), pairs.get(4));
    }

    @Test
    public void givenStreamWithLetters_whenUsingStatefulTransform_thenCollectPairs() {
        Stream<String> stream = Stream.of("a", "b", "c", "d", "e");
        List<List<String>> result = SuccessivePairs.pairwise(stream)
            .collect(Collectors.toList());

        List<List<String>> expected = Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("b", "c"), Arrays.asList("c", "d"), Arrays.asList("d", "e"));

        assertEquals(expected, result);
    }
}
