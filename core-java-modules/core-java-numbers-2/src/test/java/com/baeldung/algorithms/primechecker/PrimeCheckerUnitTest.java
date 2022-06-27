package com.baeldung.algorithms.primechecker;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrimeCheckerUnitTest {

    private final BigIntegerPrimeChecker primeChecker = new BigIntegerPrimeChecker();

    @Test
    public void whenCheckIsPrime_thenTrue() {
        assertTrue(primeChecker.isPrime(2L));
        assertTrue(primeChecker.isPrime(13L));
        assertTrue(primeChecker.isPrime(1009L));
        assertTrue(primeChecker.isPrime(74207281L));
    }

    @Test
    public void whenCheckIsPrime_thenFalse() {
        assertFalse(primeChecker.isPrime(50L));
        assertFalse(primeChecker.isPrime(1001L));
        assertFalse(primeChecker.isPrime(74207282L));
    }

    private final BruteForcePrimeChecker bfPrimeChecker = new BruteForcePrimeChecker();

    @Test
    public void whenBFCheckIsPrime_thenTrue() {
        assertTrue(bfPrimeChecker.isPrime(2));
        assertTrue(bfPrimeChecker.isPrime(13));
        assertTrue(bfPrimeChecker.isPrime(1009));
    }

    @Test
    public void whenBFCheckIsPrime_thenFalse() {
        assertFalse(bfPrimeChecker.isPrime(50));
        assertFalse(bfPrimeChecker.isPrime(1001));
    }

    private final OptimisedPrimeChecker optimisedPrimeChecker = new OptimisedPrimeChecker();

    @Test
    public void whenOptCheckIsPrime_thenTrue() {
        assertTrue(optimisedPrimeChecker.isPrime(2));
        assertTrue(optimisedPrimeChecker.isPrime(13));
        assertTrue(optimisedPrimeChecker.isPrime(1009));
    }

    @Test
    public void whenOptCheckIsPrime_thenFalse() {
        assertFalse(optimisedPrimeChecker.isPrime(50));
        assertFalse(optimisedPrimeChecker.isPrime(1001));
    }

    private final PrimesPrimeChecker primesPrimeChecker = new PrimesPrimeChecker();

    @Test
    public void whenPrimesCheckIsPrime_thenTrue() {
        assertTrue(primesPrimeChecker.isPrime(2));
        assertTrue(primesPrimeChecker.isPrime(13));
        assertTrue(primesPrimeChecker.isPrime(1009));
    }

    @Test
    public void whenPrimesCheckIsPrime_thenFalse() {
        assertFalse(primesPrimeChecker.isPrime(50));
        assertFalse(primesPrimeChecker.isPrime(1001));
    }

}
