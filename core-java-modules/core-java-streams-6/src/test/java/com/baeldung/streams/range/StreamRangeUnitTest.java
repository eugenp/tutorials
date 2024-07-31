package com.baeldung.streams.range;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamRangeUnitTest {

    @Test
    public void whenRangeStreamUsingLimitSkip_thenPrintsRange() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> expectedRange = Arrays.asList(3, 4, 5, 6, 7);

        List<Integer> range = numbers.stream()
                .skip(2)
                .limit(5)
                .collect(Collectors.toList());

        assertEquals(expectedRange, range);
    }

    @Test
    public void whenRangeStreamUsingCollectingAndThen_thenPrintsRange() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> expectedRange = Arrays.asList(3, 4, 5, 6, 7);

        List<Integer> range = numbers.stream()
                .filter(n -> n >= 3 && n <= 7)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

        assertEquals(expectedRange, range);
    }
}
