package com.baeldung.noexception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandardExceptionHandling {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(NoExceptionHandling.class);
        try {
            System.out.println("Result is " + Integer.parseInt("foobar"));
        } catch (Throwable exception) {
            logger.error("Caught exception:", exception);
        }
    }

}
