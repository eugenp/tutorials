package com.baeldung.streams.gatherer;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumericSumGathererUnitTest {

    @Test
    void givenNumbers_whenUsingCustomManyToOneGatherer_thenSumIsCalculated() {
        Stream<Integer> inputValues = Stream.of(1, 2, 3, 4, 5, 6);
        List<Integer> result = inputValues.gather(new NumericSumGatherer())
            .toList();
        Assertions.assertEquals(Integer.valueOf(21), result.getFirst());
    }
}