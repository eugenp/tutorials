package com.baeldung.globalexceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arithmetic {

    private static Logger LOGGER = LoggerFactory.getLogger(Arithmetic.class);

    public static void main(String[] args) {

        try {
            int result = 30 / 0; // Trying to divide by zero
        } catch (ArithmeticException e) {
            LOGGER.error("ArithmeticException caught!");
        }

    }

}
