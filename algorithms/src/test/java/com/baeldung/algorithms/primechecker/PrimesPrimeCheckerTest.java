package com.baeldung.algorithms.primechecker;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.baeldung.algorithms.primechecker.PrimeChecker;
import com.baeldung.algorithms.primechecker.PrimesPrimeChecker;

public class PrimesPrimeCheckerTest {
    PrimesPrimeChecker primeChecker = new PrimesPrimeChecker();

    @Test
    public void givenPrimeNumber_whenCheckIsPrime_thenTrue() {
        assertTrue(primeChecker.isPrime(13));
        assertTrue(primeChecker.isPrime(1009));
    }

    @Test
    public void givenNonPrimeNumber_whenCheckIsPrime_thenFalse() {
        assertTrue(!primeChecker.isPrime(50));
        assertTrue(!primeChecker.isPrime(1001));
    }

}
