package com.baeldung.prime;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.baeldung.prime.PrimeGenerator.*;
import static org.junit.Assert.*;

public class PrimeGeneratorUnitTest {
    @Test
    public void whenBruteForced_returnsSuccessfully() {
        final List<Integer> primeNumbers = primeNumbersBruteForce(20);
        assertEquals(Arrays.asList(new Integer[] { 2, 3, 5, 7, 11, 13, 17, 19 }), primeNumbers);
    }

    @Test
    public void whenOptimized_returnsSuccessfully() {
        final List<Integer> primeNumbers = primeNumbersTill(20);
        assertEquals(Arrays.asList(new Integer[] { 2, 3, 5, 7, 11, 13, 17, 19 }), primeNumbers);
    }

    @Test
    public void whenSieveOfEratosthenes_returnsSuccessfully() {
        final List<Integer> primeNumbers = sieveOfEratosthenes(20);
        assertEquals(Arrays.asList(new Integer[] { 2, 3, 5, 7, 11, 13, 17, 19 }), primeNumbers);
    }
}
