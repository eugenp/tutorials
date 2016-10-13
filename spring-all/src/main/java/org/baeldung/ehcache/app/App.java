package org.baeldung.ehcache.app;

import org.baeldung.ehcache.calculator.SquaredCalculator;
import org.baeldung.ehcache.config.CacheHelper;

public class App {

    public static void main(String[] args) {

        SquaredCalculator squaredCalculator = new SquaredCalculator();
        CacheHelper cacheHelper = new CacheHelper();

        squaredCalculator.setCache(cacheHelper);

        calculate(squaredCalculator);
        calculate(squaredCalculator);
    }

    private static void calculate(SquaredCalculator squaredCalculator) {
        for (int i = 10; i < 15; i++) {
            System.out.println("Square value of " + i + " is: " + squaredCalculator.getSquareValueOfNumber(i) + "\n");
        }
    }
}
