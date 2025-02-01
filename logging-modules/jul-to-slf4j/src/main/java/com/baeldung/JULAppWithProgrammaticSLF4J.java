package com.baeldung;

import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Logger;

public class JULAppWithProgrammaticSLF4J {

    private static final Logger julLogger = Logger.getLogger(JULAppWithProgrammaticSLF4J.class.getName());

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JULAppWithProgrammaticSLF4J.class);

    public static void main(String[] args) {
        // Test output before slf4j bridge was installed
        BusinessLogic.julLog(julLogger);

        // Remove existing handlers
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // Install SLF4J bridge
        SLF4JBridgeHandler.install();

        // Test output after slf4j bridge was installed
        BusinessLogic.julLog(julLogger);

        BusinessLogic.slf4jLog(logger);
    }
}
