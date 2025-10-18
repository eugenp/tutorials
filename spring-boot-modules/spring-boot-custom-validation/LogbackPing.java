package com.baeldung.validation;

import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;

public class LogbackPing {
    public static void ping() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(context);
    }
}