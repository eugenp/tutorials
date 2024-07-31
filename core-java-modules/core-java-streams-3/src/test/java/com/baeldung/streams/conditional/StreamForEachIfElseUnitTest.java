package com.baeldung.streams.conditional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class StreamForEachIfElseUnitTest {

    @Test
    public final void givenIntegerStream_whenCheckingIntegerParityWithIfElse_thenEnsureCorrectParity() {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        ints.stream()
            .forEach(i -> {
                if (i.intValue() % 2 == 0) {
                    Assert.assertTrue(i.intValue() + " is not even", i.intValue() % 2 == 0);
                } else {
                    Assert.assertTrue(i.intValue() + " is not odd", i.intValue() % 2 != 0);
                }
            });

    }

    @Test
    public final void givenIntegerStream_whenCheckingIntegerParityWithCustomConsumer_thenEnsureCorrectParity() {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Consumer<Integer> integerParityAsserter = i -> {
            if (i % 2 == 0) {
                Assert.assertTrue(i + " is not even", i % 2 == 0);
            } else {
                Assert.assertTrue(i + " is not odd", i % 2 != 0);
            }
        };

        ints.stream()
            .forEach(integerParityAsserter);
    }

    @Test
    public final void givenIntegerStream_whenCheckingIntegerParityWithStreamFilter_thenEnsureCorrectParity() {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Stream<Integer> evenIntegers = ints.stream()
            .filter(i -> i.intValue() % 2 == 0);
        Stream<Integer> oddIntegers = ints.stream()
            .filter(i -> i.intValue() % 2 != 0);

        evenIntegers.forEach(i -> Assert.assertTrue(i.intValue() + " is not even", i.intValue() % 2 == 0));
        oddIntegers.forEach(i -> Assert.assertTrue(i.intValue() + " is not odd", i.intValue() % 2 != 0));

    }

    @Test
    public final void givenIntegerStream_whenCheckingIntegerParityWithStreamPartitioningBy_thenEnsureCorrectParity() {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Map<Boolean, List<Integer>> resultMap = ints.stream()
            .collect(Collectors.partitioningBy(i -> i % 2 == 0));

        Assert.assertEquals(Arrays.asList(2, 4, 6, 8, 10), resultMap.get(true));
        Assert.assertEquals(Arrays.asList(1, 3, 5, 7, 9), resultMap.get(false));
    }

    @Test
    public final void givenIntegerStream_whenCheckingIntegerParityWithStreamGroupingBy_thenEnsureCorrectParity() {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Map<String, List<Integer>> resultMap = ints.stream()
            .collect(Collectors.groupingBy(i -> i % 2 == 0 ? "even" : "odd"));

        Assert.assertEquals(Arrays.asList(2, 4, 6, 8, 10), resultMap.get("even"));
        Assert.assertEquals(Arrays.asList(1, 3, 5, 7, 9), resultMap.get("odd"));
    }

}
