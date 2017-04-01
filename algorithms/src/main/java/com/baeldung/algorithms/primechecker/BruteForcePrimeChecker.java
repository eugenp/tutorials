package com.baeldung.algorithms.primechecker;

import java.util.stream.IntStream;

public class BruteForcePrimeChecker implements PrimeChecker{

    @Override
    public boolean isPrime(int number) {
        return IntStream.range(2, number).noneMatch(n -> (number % n == 0));
    }

    
}       
