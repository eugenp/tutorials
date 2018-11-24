package com.baeldung.algorithms.factorial;

import java.math.BigInteger;
import java.util.stream.LongStream;

import org.apache.commons.math3.util.CombinatoricsUtils;

import com.google.common.math.BigIntegerMath;

public class Factorial {

    public long factorialUsingForLoop(long n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }

    public long factorialUsingStreams(long n) {
        return LongStream.rangeClosed(1, n)
            .reduce(1, (long x, long y) -> x * y);
    }

    public long factorialUsingRecursion(long n) {
        if (n < 2) {
            return 1;
        }
        return n * factorialUsingRecursion(n - 1);
    }

    private Long[] factorials = new Long[20];

    public long factorialUsingMemoize(int n) {

        if (factorials[n] != null) {
            return factorials[n];
        }

        if (n < 2) {
            return 1;
        }
        long nthValue = n * factorialUsingMemoize(n - 1);
        factorials[n] = nthValue;
        return nthValue;
    }

    public BigInteger factorialHavingLargeResult(long n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++)
            result = result.multiply(BigInteger.valueOf(i));
        return result;
    }

    public long factorialUsingApacheCommons(int n) {
        return CombinatoricsUtils.factorial(n);
    }

    public BigInteger factorialUsingGuava(int n) {
        return BigIntegerMath.factorial(n);
    }

}
