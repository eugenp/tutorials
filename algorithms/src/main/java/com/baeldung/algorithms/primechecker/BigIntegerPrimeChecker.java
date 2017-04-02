package com.baeldung.algorithms.primechecker;

import java.math.BigInteger;

public class BigIntegerPrimeChecker implements PrimeChecker{

    @Override
    public boolean isPrime(int number) {
        BigInteger bigInt = BigInteger.valueOf(number);
        return bigInt.isProbablePrime(100);
    }

}
