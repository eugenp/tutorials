package com.baeldung.probability;

import org.assertj.core.data.Offset;
import org.junit.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomInvokerUnitTest {
    @Test
    public void givenProbability_whenInvoked_invokeWithProbability() {
        RandomInvoker randomInvoker = new RandomInvoker();

        int numberOfSamples = 1_000_000;
        int probability = 10;
        int howManyTimesInvoked = Stream.generate(() -> randomInvoker.withProbability(() -> 1, () -> 0, probability))
                .limit(numberOfSamples)
                .mapToInt(e -> e).sum();
        int monteCarloProbability = (howManyTimesInvoked * 100) / numberOfSamples;

        assertThat(monteCarloProbability).isCloseTo(probability, Offset.offset(1));
    }
}
