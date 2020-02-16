package com.baeldung.hexagonalarchitecture;

public class ApplicationCore {
    private LoggerPort logger;

    public ApplicationCore(LoggerPort injectedLogger) {
        logger = injectedLogger;
    }

    public void run() {
        someCoreFunction();
        logger.writeLog("Core Process Is Running");
    }

    public static void someCoreFunction() {
    }
}