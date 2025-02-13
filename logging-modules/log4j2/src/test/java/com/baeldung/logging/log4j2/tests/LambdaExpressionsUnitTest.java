package com.baeldung.logging.log4j2.tests;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class LambdaExpressionsUnitTest {

    private static final Logger logger = LogManager.getRootLogger();

    @Test
    public void whenCheckLogMessage_thenOk() {
        if (logger.isTraceEnabled()) {
            logger.trace("Numer is {}", getRandomNumber());
        }
    }

    @Test
    public void whenUseLambdaExpression_thenOk() {
        logger.trace("Number is {}", () -> getRandomNumber());
        logger.trace("Name is {} and age is {}", () -> getName(), () -> getRandomNumber());
    }

    private int getRandomNumber() {
        return (new Random()).nextInt(10);
    }

    private String getName() {
        return "John";
    }
}
