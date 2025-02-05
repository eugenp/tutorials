package com.baeldung;

import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class JULAppWithLoggingProperties {

    private static final Logger julLogger = Logger.getLogger(JULAppWithLoggingProperties.class.getName());

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JULAppWithLoggingProperties.class);

    public static void main(String[] args) {
        // Test output after slf4j bridge was installed
        BusinessLogic.julLog(julLogger);

        BusinessLogic.slf4jLog(logger);
    }
}
