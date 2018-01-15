package org.baeldung.guava.memoizer;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.math.BigInteger;

public class Factorial {

    private static LoadingCache<Integer, BigInteger> memo = CacheBuilder.newBuilder()
            .build(CacheLoader.from(Factorial::getFactorial));
    static {
        memo.put(0, BigInteger.ONE);
    }

    public static BigInteger getFactorial(int n) {
        return BigInteger.valueOf(n).multiply(memo.getUnchecked(n -1 ));
    }

}
