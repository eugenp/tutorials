package com.baeldung.concurrent.threadreturnvalue.task;

import java.math.BigInteger;

public class FactorialCalculator {

    public static BigInteger factorial(BigInteger end) {
        BigInteger start = BigInteger.ONE;
        BigInteger res = BigInteger.ONE;

        for (int i = start.add(BigInteger.ONE)
          .intValue(); i <= end.intValue(); i++) {
            res = res.multiply(BigInteger.valueOf(i));
        }

        return res;
    }

    public static BigInteger factorial(BigInteger start, BigInteger end) {
        BigInteger res = start;

        for (int i = start.add(BigInteger.ONE)
          .intValue(); i <= end.intValue(); i++) {
            res = res.multiply(BigInteger.valueOf(i));
        }

        return res;
    }
}