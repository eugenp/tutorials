package com.baeldung.algorithms.relativelyprime;

import org.junit.Test;

import static com.baeldung.algorithms.relativelyprime.RelativelyPrime.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RelativelyPrimeUnitTest {

    @Test
    public void givenNonRelativelyPrimeNumbers_whenCheckingIteratively_shouldReturnFalse() {

        boolean result = iterativeRelativelyPrime(45, 35);
        assertThat(result).isFalse();
    }

    @Test
    public void givenRelativelyPrimeNumbers_whenCheckingIteratively_shouldReturnTrue() {

        boolean result = iterativeRelativelyPrime(500, 501);
        assertThat(result).isTrue();
    }

    @Test
    public void givenNonRelativelyPrimeNumbers_whenCheckingRecursively_shouldReturnFalse() {

        boolean result = recursiveRelativelyPrime(45, 35);
        assertThat(result).isFalse();
    }

    @Test
    public void givenRelativelyPrimeNumbers_whenCheckingRecursively_shouldReturnTrue() {

        boolean result = recursiveRelativelyPrime(500, 501);
        assertThat(result).isTrue();
    }

    @Test
    public void givenNonRelativelyPrimeNumbers_whenCheckingUsingBigIntegers_shouldReturnFalse() {

        boolean result = bigIntegerRelativelyPrime(45, 35);
        assertThat(result).isFalse();
    }

    @Test
    public void givenRelativelyPrimeNumbers_whenCheckingBigIntegers_shouldReturnTrue() {

        boolean result = bigIntegerRelativelyPrime(500, 501);
        assertThat(result).isTrue();
    }
}
