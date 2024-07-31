package com.baeldung.algorithms.primeundernumber;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LargestPrimeFinderUnitTest {

    @Test
    public void givenNormalCases_whenFindPrime_ThenFoundResult() {
        assertEquals(7, LargestPrimeFinder.findByBruteForce(10));
        assertEquals(97, LargestPrimeFinder.findByBruteForce(100));
        assertEquals(7, LargestPrimeFinder.findBySieveOfEratosthenes(10));
        assertEquals(97, LargestPrimeFinder.findBySieveOfEratosthenes(100));
    }

    @Test
    public void givenEdgeCases_whenFindPrime_ThenNotFoundResult() {
        assertEquals(-1, LargestPrimeFinder.findByBruteForce(0));
        assertEquals(-1, LargestPrimeFinder.findByBruteForce(1));
        assertEquals(-1, LargestPrimeFinder.findByBruteForce(2));
        assertEquals(-1, LargestPrimeFinder.findBySieveOfEratosthenes(0));
        assertEquals(-1, LargestPrimeFinder.findBySieveOfEratosthenes(1));
        assertEquals(-1, LargestPrimeFinder.findBySieveOfEratosthenes(2));
    }

    @Test
    public void givenLargeInput_whenFindPrime_ThenFoundResult() {
        assertEquals(99991, LargestPrimeFinder.findByBruteForce(100000));
        assertEquals(99991, LargestPrimeFinder.findBySieveOfEratosthenes(100000));
    }
}
