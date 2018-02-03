package com.baeldung.designpatterns.chainofresponsibility;

import org.junit.Test;
import static org.junit.Assert.*;

public class ChainOfResponsibilityTest {

    private static AbstractLogger getChainOfLoggers() {

        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger infoLogger = new InfoLogger(AbstractLogger.INFO);
        AbstractLogger debugLogger = new DebugLogger(AbstractLogger.DEBUG);

        errorLogger.setNextLogger(infoLogger);
        infoLogger.setNextLogger(debugLogger);

        return errorLogger;
    }

    @Test
    public void givenMessage_whenLogLevelDebug_thenMessageStartsWithDebugKeyword() {
        AbstractLogger loggerChain = getChainOfLoggers();

        String response = loggerChain.logMessage(AbstractLogger.DEBUG, "This is an debug level information.");
        assertTrue(response.startsWith("DEBUG"));

    }

    @Test
    public void givenMessage_whenLogLevelInfo_thenMessageStartsWithInfoKeyword() {
        AbstractLogger loggerChain = getChainOfLoggers();

        String response = loggerChain.logMessage(AbstractLogger.INFO, "This is an info level information.");
        assertTrue(response.startsWith("INFO"));

    }

    @Test
    public void givenMessage_whenLogLevelError_thenMessageStartsWithErrorKeyword() {
        AbstractLogger loggerChain = getChainOfLoggers();

        String response = loggerChain.logMessage(AbstractLogger.ERROR, "This is an error level information.");
        assertTrue(response.startsWith("ERROR"));

    }

    @Test
    public void givenMessage_whenLogLevelUnknown_thenMessageStartsWithConsoleKeyword() {
        AbstractLogger loggerChain = getChainOfLoggers();

        String response = loggerChain.logMessage(4, "This is an unknown level information.");
        assertTrue(response.startsWith("CONSOLE"));

    }

}
