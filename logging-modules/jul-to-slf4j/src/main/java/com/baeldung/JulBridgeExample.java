package com.baeldung;

import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Logger;

public class JulBridgeExample {

    private static final Logger julLogger = Logger.getLogger(JulBridgeExample.class.getName());

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JulBridgeExample.class);


    public static void main(String[] args) {
        // Remove existing handlers
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        // Install SLF4J bridge
        SLF4JBridgeHandler.install();

        julLog();

        slf4jLog();
    }

    public static void julLog() {
        julLogger.info("This is a JUL info log message!");
        julLogger.warning("This is a JUL warning log message!");
    }

    public static void slf4jLog() {
        logger.info("This is an SLF4J info log message!");
        logger.error("This is an SLF4J error log message!");
    }
}
