package com.baeldung.algorithms.primechecker;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class PrimeChecker {

    public static boolean isPrime(Integer number) {
        return number > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(number))
            .noneMatch(n -> (number % n == 0));
    }

    public static boolean isPrimeByBigInteger(int number) {
        BigInteger bigInt = BigInteger.valueOf(number);
        return bigInt.isProbablePrime(100);
    }

}