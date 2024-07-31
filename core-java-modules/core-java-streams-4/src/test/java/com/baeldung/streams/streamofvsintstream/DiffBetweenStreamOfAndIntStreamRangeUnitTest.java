package com.baeldung.streams.streamofvsintstream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class DiffBetweenStreamOfAndIntStreamRangeUnitTest {
    @Test
    void givenStreamOfAndIntStreamRange_whenPeekSortAndFirst_shouldResultDifferent() {
        Stream<Integer> normalStream = Stream.of(1, 2, 3, 4, 5);
        IntStream intStreamByRange = IntStream.range(1, 6);
        List<Integer> normalStreamPeekResult = new ArrayList<>();
        List<Integer> intStreamPeekResult = new ArrayList<>();

        // First, the regular Stream
        normalStream.peek(normalStreamPeekResult::add)
          .sorted()
          .findFirst();
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), normalStreamPeekResult);

        // Then, the IntStream
        intStreamByRange.peek(intStreamPeekResult::add)
          .sorted()
          .findFirst();
        assertEquals(Arrays.asList(1), intStreamPeekResult);
    }

    @Test
    void givenStream_whenPeekAndFirst_shouldHaveOnlyFirstElement() {
        Stream<Integer> normalStream = Stream.of(1, 2, 3, 4, 5);
        IntStream intStreamByRange = IntStream.range(1, 6);
        List<Integer> normalStreamPeekResult = new ArrayList<>();
        List<Integer> intStreamPeekResult = new ArrayList<>();

        // First, the regular Stream
        normalStream.peek(normalStreamPeekResult::add)
          .findFirst();
        assertEquals(Arrays.asList(1), normalStreamPeekResult);

        // Then, the IntStream
        intStreamByRange.peek(intStreamPeekResult::add)
          .findFirst();
        assertEquals(Arrays.asList(1), intStreamPeekResult);
    }

    @Test
    void givenSortedStream_whenPeekSortAndFirst_shouldOnlyHaveOneElement() {
        List<String> peekResult = new ArrayList<>();

        TreeSet<String> treeSet = new TreeSet<>(Arrays.asList("CCC", "BBB", "AAA", "DDD", "KKK"));

        treeSet.stream()
          .peek(peekResult::add)
          .sorted()
          .findFirst();

        assertEquals(Arrays.asList("AAA"), peekResult);
    }
}