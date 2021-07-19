package com.baeldung.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdderAfterThrowAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void afterThrow(final Exception exception) throws Throwable {
        logger.info("Exception thrown was {}",  exception.getMessage());
    }
}
