package com.baeldung.algorithms.primechecker;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.baeldung.algorithms.primechecker.BigIntegerPrimeChecker;
import com.baeldung.algorithms.primechecker.PrimeChecker;

public class BigIntegerPrimeCheckerTest {

    BigIntegerPrimeChecker primeChecker = new BigIntegerPrimeChecker();
    
    @Test
    public void givenPrimeNumber_whenCheckIsPrime_thenTrue(){
        assertTrue(primeChecker.isPrime(13l));
        assertTrue(primeChecker.isPrime(1009L));
        assertTrue(primeChecker.isPrime(74207281L));
    }
    
    @Test
    public void givenNonPrimeNumber_whenCheckIsPrime_thenFalse(){
        assertTrue(!primeChecker.isPrime(50L));
        assertTrue(!primeChecker.isPrime(1001L));
        assertTrue(!primeChecker.isPrime(74207282L));
    }
    
}
