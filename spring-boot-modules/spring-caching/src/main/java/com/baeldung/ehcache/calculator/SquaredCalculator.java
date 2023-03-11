package com.baeldung.ehcache.calculator;

import com.baeldung.ehcache.config.CacheHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SquaredCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SquaredCalculator.class);
    private CacheHelper cache;

    public int getSquareValueOfNumber(int input) {
        if (cache.getSquareNumberCache().containsKey(input)) {
            return cache.getSquareNumberCache().get(input);
        }

        LOGGER.debug("Calculating square value of {} and caching result.", input);

        int squaredValue = (int) Math.pow(input, 2);
        cache.getSquareNumberCache().put(input, squaredValue);

        return squaredValue;
    }

    public void setCache(CacheHelper cache) {
        this.cache = cache;
    }
}
