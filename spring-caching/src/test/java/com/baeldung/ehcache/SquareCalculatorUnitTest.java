package com.baeldung.ehcache;

import com.baeldung.ehcache.calculator.SquaredCalculator;
import com.baeldung.ehcache.config.CacheHelper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SquareCalculatorUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SquareCalculatorUnitTest.class);

    private final SquaredCalculator squaredCalculator = new SquaredCalculator();
    private final CacheHelper cacheHelper = new CacheHelper();

    @Before
    public void setup() {
        squaredCalculator.setCache(cacheHelper);
    }

    @Test
    public void whenCalculatingSquareValueOnce_thenCacheDontHaveValues() {
        for (int i = 10; i < 15; i++) {
            assertFalse(cacheHelper.getSquareNumberCache()
                    .containsKey(i));
            LOGGER.debug("Square value of {} is: {}", i, squaredCalculator.getSquareValueOfNumber(i));
        }
    }

    @Test
    public void whenCalculatingSquareValueAgain_thenCacheHasAllValues() {
        for (int i = 10; i < 15; i++) {
            assertFalse(cacheHelper.getSquareNumberCache()
                    .containsKey(i));
            LOGGER.debug("Square value of {} is: {}", i, squaredCalculator.getSquareValueOfNumber(i));
        }

        for (int i = 10; i < 15; i++) {
            assertTrue(cacheHelper.getSquareNumberCache()
                    .containsKey(i));
            LOGGER.debug("Square value of {} is: {}", i, squaredCalculator.getSquareValueOfNumber(i) + "\n");
        }
    }
}
