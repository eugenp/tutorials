package com.baeldung.streams.gatherer;

import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GathererUnitTest {

    @Test
    void givenNumbers_whenFolded_thenSumIsEmitted() {
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> folded = numbers.gather(Gatherers.fold(() -> 0, Integer::sum));
        List<Integer> resultList = folded.toList();
        Assertions.assertEquals(1, resultList.size());
        Assertions.assertEquals(Integer.valueOf(15), resultList.getFirst());
    }

    @Test
    void givenWords_whenMappedConcurrently_thenUppercasedWordsAreEmitted() {
        Stream<String> words = Stream.of("a", "b", "c", "d");
        List<String> resultList = words.gather(Gatherers.mapConcurrent(2, String::toUpperCase))
            .toList();
        Assertions.assertEquals(4, resultList.size());
        Assertions.assertEquals(List.of("A", "B", "C", "D"), resultList);
    }

    @Test
    void givenNumbers_whenScanned_thenRunningTotalsAreEmitted() {
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4);
        List<Integer> resultList = numbers.gather(Gatherers.scan(() -> 0, Integer::sum))
            .toList();
        Assertions.assertEquals(4, resultList.size());
        Assertions.assertEquals(List.of(1, 3, 6, 10), resultList);
    }

    @Test
    void givenNumbers_whenWindowedSliding_thenOverlappingWindowsAreEmitted() {
        List<List<Integer>> expectedOutput = List.of(List.of(1, 2, 3), List.of(2, 3, 4), List.of(3, 4, 5));
        Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5);
        List<List<Integer>> resultList = numbers.gather(Gatherers.windowSliding(3))
            .toList();
        Assertions.assertEquals(3, resultList.size());
        Assertions.assertEquals(expectedOutput, resultList);
    }

    @Test
    void givenStrings_whenUsingCustomGatherer_thenLengthsAreCalculated() {
        List<Integer> expectedOutput = List.of(5, 6, 3);
        Stream<String> inputStrings = Stream.of("apple", "banana", "cat");
        List<Object> resultList = inputStrings.gather(Gatherer.of((state, element, downstream) -> {
                downstream.push(element.length());
                return true;
            }))
            .toList();
        Assertions.assertEquals(3, resultList.size());
        Assertions.assertEquals(expectedOutput, resultList);
    }
}
