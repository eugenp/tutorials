package com.baeldung.primechecker;

import java.util.stream.IntStream;

public class BruteForcePrimeChecker implements PrimeChecker{

    @Override
    public boolean isPrime(int number) {
        return IntStream.range(2, number).filter(n -> (number % n == 0)).count() == 0;
    }

    
}       
