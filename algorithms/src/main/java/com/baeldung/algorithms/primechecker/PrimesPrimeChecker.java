package com.baeldung.algorithms.primechecker;

import org.apache.commons.math3.primes.Primes;

public class PrimesPrimeChecker implements PrimeChecker{

    @Override
    public boolean isPrime(int number) {
        return Primes.isPrime(number);
    }

}
