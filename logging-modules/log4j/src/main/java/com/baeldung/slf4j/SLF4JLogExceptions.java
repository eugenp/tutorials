package com.baeldung.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JLogExceptions {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(SLF4JLogExceptions.class);

        logger.error("An exception occurred!");
        logger.error("An exception occurred!", new Exception("Custom exception"));
        logger.error("{}, {}! An exception occurred!", "Hello", "World", new Exception("Custom exception"));
    }

}
