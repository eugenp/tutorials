package com.baeldung.flogger;

import com.google.common.flogger.FluentLogger;
import com.google.common.flogger.LoggerConfig;

import java.util.logging.Level;

public class FloggerExamples {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public static void main(String[] args) {
        LoggerConfig.of(logger).setLevel(Level.FINE);
        Exception exception = new Exception("This is a test exception.");
        logger.atInfo().withCause(exception).log("Log message with: %s", "Alfred");
    }
}
