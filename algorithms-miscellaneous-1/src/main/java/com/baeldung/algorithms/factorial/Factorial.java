package com.baeldung.algorithms.factorial;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

import org.apache.commons.math3.util.CombinatoricsUtils;

import com.google.common.math.BigIntegerMath;

public class Factorial {

    public long factorialUsingForLoop(long n) {
        long fact = 1;
        for (int i = 1; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }

    public long factorialUsingWhileLoop(long n) {
        long fact = 1;
        while (n >= 1) {
            fact = fact * n;
            n--;
        }
        return fact;
    }

    public long factorialUsingStreams(long n) {
        return LongStream.rangeClosed(1, n)
            .reduce(1, (long x, long y) -> x * y);
    }

    public int factorialUsingRecursion(int n) {
        if (n == 1) {
            return 1;
        }
        return n * factorialUsingRecursion(n - 1);
    }

    private Map<Long, Long> factorials = new HashMap<Long, Long>();

    public long factorialUsingMemoize(long n) {

        if (factorials.containsKey(n)) {
            return factorials.get(n);
        }

        if (n == 1) {
            return 1;
        }
        long nthValue = n * factorialUsingMemoize(n - 1);
        factorials.put(n, nthValue);
        return nthValue;
    }

    public BigInteger factorialHavingLargeResult(long n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= n; i++)
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
