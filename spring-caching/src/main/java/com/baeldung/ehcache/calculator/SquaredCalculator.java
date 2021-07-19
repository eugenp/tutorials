package com.baeldung.ehcache.calculator;

import com.baeldung.ehcache.config.CacheHelper;

public class SquaredCalculator {
    private CacheHelper cache;

    public int getSquareValueOfNumber(int input) {
        if (cache.getSquareNumberCache().containsKey(input)) {
            return cache.getSquareNumberCache().get(input);
        }

        System.out.println("Calculating square value of " + input + " and caching result.");

        int squaredValue = (int) Math.pow(input, 2);
        cache.getSquareNumberCache().put(input, squaredValue);

        return squaredValue;
    }

    public void setCache(CacheHelper cache) {
        this.cache = cache;
    }
}
