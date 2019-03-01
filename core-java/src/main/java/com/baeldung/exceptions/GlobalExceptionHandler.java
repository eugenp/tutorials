package com.baeldung.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalExceptionHandler {

    public static void main(String[] args) {

        Handler globalExceptionHandler = new Handler();
        Thread.setDefaultUncaughtExceptionHandler(globalExceptionHandler);
        new GlobalExceptionHandler().performArithmeticOperation(10, 0);
    }

    public int performArithmeticOperation(int num1, int num2) {
        return num1/num2;
    }

}

class Handler implements Thread.UncaughtExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.info("Unhandled exception caught!");
    }
}
