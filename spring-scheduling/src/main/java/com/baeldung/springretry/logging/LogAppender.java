package com.baeldung.springretry.logging;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.AppenderBase;

public class LogAppender extends AppenderBase<ch.qos.logback.classic.spi.ILoggingEvent> {

    private static final Logger logger = LoggerFactory.getLogger(LogAppender.class);
    private static final List<String> logMessages = new ArrayList<>();

    @Override
    protected void append(ch.qos.logback.classic.spi.ILoggingEvent eventObject) {
        // Custom logic to capture logs
        logMessages.add(eventObject.getFormattedMessage());
    }

    public static List<String> getLogMessages() {
        return logMessages;
    }
}
