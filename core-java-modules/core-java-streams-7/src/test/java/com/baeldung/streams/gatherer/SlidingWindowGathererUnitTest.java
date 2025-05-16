package com.baeldung.streams.gatherer;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SlidingWindowGathererUnitTest {

    @Test
    void givenNumbers_whenWindowedSliding_thenOverlappingWindowsAreEmitted() {
        List<List<Integer>> expectedOutput = List.of(List.of(1, 2, 3), List.of(2, 3, 4), List.of(3, 4, 5));
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5);
        List<List<Integer>> resultList = numbers.gather(new SlidingWindowGatherer())
            .toList();
        Assertions.assertEquals(3, resultList.size());
        Assertions.assertEquals(expectedOutput, resultList);
    }

}