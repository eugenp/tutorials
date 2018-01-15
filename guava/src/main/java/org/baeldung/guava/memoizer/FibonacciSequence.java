package org.baeldung.guava.memoizer;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class FibonacciSequence {

    private static LoadingCache<Integer, BigInteger> memo = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build(CacheLoader.from(FibonacciSequence::getFibonacciNumber));
    static {
        memo.put(0, BigInteger.ZERO);
        memo.put(1, BigInteger.ONE);
    }

    public static BigInteger getFibonacciNumber(int n) {
        return memo.getUnchecked(n - 1).add(memo.getUnchecked(n - 2));
    }

}
