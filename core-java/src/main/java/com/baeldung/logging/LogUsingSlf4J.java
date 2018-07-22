package com.baeldung.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUsingSlf4J {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LogUsingSlf4J.class);
        String[] names = { "This", "will", "be", "displayed" };

        logger.error("An exception occurred!");
        logger.error("An exception occurred! {} {} {} {}.", (Object[]) names);
        logger.error("An exception occurred! {}", (Object[]) names, new Exception("Custom exception"));
    }

}
