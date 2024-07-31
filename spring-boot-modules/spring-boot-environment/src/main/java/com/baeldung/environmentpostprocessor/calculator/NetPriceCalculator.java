package com.baeldung.environmentpostprocessor.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetPriceCalculator implements PriceCalculator {

    private static final Logger logger = LoggerFactory.getLogger(GrossPriceCalculator.class);

    @Override
    public double calculate(double singlePrice, int quantity) {
        logger.info("Net based price calculation with input parameters [singlePrice = {},quantity= {} ], NO tax applied.", singlePrice, quantity);
        double result = Math.round(singlePrice * quantity);
        logger.info("Calcuation result is {}", result);
        return result;
    }
}
