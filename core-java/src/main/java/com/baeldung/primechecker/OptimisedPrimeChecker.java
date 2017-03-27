package com.baeldung.primechecker;

import java.util.stream.IntStream;

public class OptimisedPrimeChecker implements PrimeChecker{

    @Override
    public boolean isPrime(int number) {
        return IntStream.range(2, (int)Math.sqrt(number) + 1)
            .filter(n -> (number % n == 0))
            .count() == 0;
    }

    
}
