package com.baeldung.stream.conditional;

import java.util.Arrays;
import java.util.List;
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
    public final void givenIntegerStream_whenCheckingIntegerParityWithStreamFilter_thenEnsureCorrectParity() {
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Stream<Integer> evenIntegers = ints.stream()
            .filter(i -> i.intValue() % 2 == 0);
        Stream<Integer> oddIntegers = ints.stream()
            .filter(i -> i.intValue() % 2 != 0);

        evenIntegers.forEach(i -> Assert.assertTrue(i.intValue() + " is not even", i.intValue() % 2 == 0));
        oddIntegers.forEach(i -> Assert.assertTrue(i.intValue() + " is not odd", i.intValue() % 2 != 0));

    }

}
