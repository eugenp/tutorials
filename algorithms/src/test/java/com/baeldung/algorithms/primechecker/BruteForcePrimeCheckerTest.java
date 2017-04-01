package com.baeldung.algorithms.primechecker;

import org.junit.Test;

import com.baeldung.algorithms.primechecker.BruteForcePrimeChecker;

import static org.junit.Assert.*;

public class BruteForcePrimeCheckerTest {

    BruteForcePrimeChecker primeChecker = new BruteForcePrimeChecker();
    
    @Test
    public void givenPrimeNumber_whenCheckIsPrime_thenTrue(){
        assertTrue(primeChecker.isPrime(13));
        assertTrue(primeChecker.isPrime(1009));
    }
    
    @Test
    public void givenNonPrimeNumber_whenCheckIsPrime_thenFalse(){
        assertTrue(!primeChecker.isPrime(50));
        assertTrue(!primeChecker.isPrime(1001));
    }
    
    
    
}
