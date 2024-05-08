package com.baeldung.memoization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class FibonacciMemoization {

    private static final Map<Integer, Long> cache = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FibonacciMemoization.class);

    public static long fibonacci(int n) {
        if (n <= 1) {
            return n;
        }

        if (cache.containsKey(n)) {
            return cache.get(n);
        }

        long result = fibonacci(n - 1) + fibonacci(n - 2);
        logger.info("First occurrence of " + n);
        cache.put(n, result);

        return result;
    }

}
