package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoggingHelper {
    private Logger logger;
    private int maxChars;

    @Autowired
    public LoggingHelper(Logger logger, int maxChars) {
        this.logger = logger;
        this.maxChars = maxChars;
    }

    public void logMessage(String message) {
        logger.logMessage(message, maxChars);
    }
}
