package com.baeldung.logging.log4j2.tests;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ConsoleAppenderUsingPatternLayoutWithColorsTest {

    @Rule
    public LoggerContextRule contextRule = new LoggerContextRule("log4j2-console-appender_pattern-layout.xml");

    @Test
    public void givenLoggerWithConsoleConfig_shouldLogToConsoleInColors() throws Exception {
        Logger logger = contextRule.getLogger(getClass().getSimpleName());
        logger.trace("This is a colored message at TRACE level.");
        logger.debug("This is a colored message at DEBUG level. This is the minimum visible level.");
        logger.info("This is a colored message at INFO level.");
        logger.warn("This is a colored message at WARN level.");
        Exception e = new RuntimeException("This is only a test!");
        logger.error("This is a colored message at ERROR level.", e);
        logger.fatal("This is a colored message at FATAL level.");
    }

    @Test
    public void givenLoggerWithConsoleConfig_shouldFilterByMarker() throws Exception {
        Logger logger = contextRule.getLogger("ConnTrace");
        Marker appError = MarkerManager.getMarker("APP_ERROR");
        logger.error(appError, "This marker message at ERROR level should be hidden.");
        Marker connectionTrace = MarkerManager.getMarker("CONN_TRACE");
        logger.trace(connectionTrace, "This is a marker message at TRACE level.");
    }

    @Test
    public void givenLoggerWithConsoleConfig_shouldFilterByThreadContext() throws Exception {
        Logger logger = contextRule.getLogger("UserAudit");
        ThreadContext.put("userId", "1000");
        logger.info("This is a log-visible user login. Maybe from an admin account?");
        ThreadContext.put("userId", "1001");
        logger.info("This is a log-invisible user login.");
        boolean b = true;
    }
}
