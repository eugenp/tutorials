package com.baeldung.designpatterns.chainofresponsibility;

import org.junit.Test;

public class ChainOfResponsibilityTest {

    private static AbstractLogger getChainOfLoggers() {

        AbstractLogger debugLogger = new DebugLogger(AbstractLogger.DEBUG, null);
        AbstractLogger infoLogger = new InfoLogger(AbstractLogger.INFO, debugLogger);
        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR, infoLogger);

        return errorLogger;
    }

    @Test
    public void givenMessage_whenLogLevelDebug_thenMessageStartsWithDebugKeyword() {
        AbstractLogger loggerChain = getChainOfLoggers();

        loggerChain.logMessage(AbstractLogger.DEBUG, "This is an debug level information.");

    }

    @Test
    public void givenMessage_whenLogLevelInfo_thenMessageStartsWithInfoKeyword() {
        AbstractLogger loggerChain = getChainOfLoggers();

        loggerChain.logMessage(AbstractLogger.INFO, "This is an info level information.");

    }

    @Test
    public void givenMessage_whenLogLevelError_thenMessageStartsWithErrorKeyword() {
        AbstractLogger loggerChain = getChainOfLoggers();

        loggerChain.logMessage(AbstractLogger.ERROR, "This is an error level information.");
    }

    @Test
    public void givenMessage_whenLogLevelUnknown_thenMessageStartsWithConsoleKeyword() {
        AbstractLogger loggerChain = getChainOfLoggers();

        loggerChain.logMessage(4, "This is an unknown level information.");
    }

}
