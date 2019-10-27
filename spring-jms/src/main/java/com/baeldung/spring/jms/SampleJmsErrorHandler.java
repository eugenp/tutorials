package com.baeldung.spring.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class SampleJmsErrorHandler implements ErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SampleJmsErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        LOG.warn("In default jms error handler...");
        LOG.error("Error Message : {}", t.getMessage());
    }

}
