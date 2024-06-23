package com.baeldung.algorithms.primechecker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.math3.primes.Primes;
import org.junit.jupiter.api.Test;

public class PrimeCheckerUnitTest {

    @Test
    void whenCheckIsPrime_thenGetExpectedResult() {
        assertTrue(PrimeChecker.isPrime(2));
        assertTrue(PrimeChecker.isPrime(13));
        assertTrue(PrimeChecker.isPrime(1009));

        assertFalse(PrimeChecker.isPrime(50));
        assertFalse(PrimeChecker.isPrime(1001));
    }

    @Test
    void whenCheckIsPrimeByBigInteger_thenGetExpectedResult() {
        assertTrue(PrimeChecker.isPrimeByBigInteger(2));
        assertTrue(PrimeChecker.isPrimeByBigInteger(13));
        assertTrue(PrimeChecker.isPrimeByBigInteger(1009));
        assertTrue(PrimeChecker.isPrimeByBigInteger(74207281));

        assertFalse(PrimeChecker.isPrimeByBigInteger(50));
        assertFalse(PrimeChecker.isPrimeByBigInteger(1001));
        assertFalse(PrimeChecker.isPrimeByBigInteger(74207282));
    }

    @Test
    void whenPrimesCheckByApacheCommonMath_thenGetExpectedResult() {
        assertTrue(Primes.isPrime(2));
        assertTrue(Primes.isPrime(13));
        assertTrue(Primes.isPrime(1009));

        assertFalse(Primes.isPrime(50));
        assertFalse(Primes.isPrime(1001));
    }

    @Test
    void whenFindAllPrimesFromAnArray_thenGetExpectedResult() {
        int[] theArray = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
        Set<Integer> expected = Set.of(2, 3, 5, 7, 11, 13);

        Set<Integer> result = Arrays.stream(theArray)
            .filter(PrimeChecker::isPrime)
            .boxed()
            .collect(Collectors.toSet());
        assertEquals(expected, result);

        Set<Integer> resultByBigIntegerApproach = Arrays.stream(theArray)
            .filter(PrimeChecker::isPrimeByBigInteger)
            .boxed()
            .collect(Collectors.toSet());
        assertEquals(expected, resultByBigIntegerApproach);

        Set<Integer> resultByApacheCommonsMath = Arrays.stream(theArray)
            .filter(Primes::isPrime)
            .boxed()
            .collect(Collectors.toSet());
        assertEquals(expected, resultByApacheCommonsMath);
    }
}