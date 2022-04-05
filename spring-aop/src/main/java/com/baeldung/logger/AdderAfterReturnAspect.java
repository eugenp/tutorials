package com.baeldung.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdderAfterReturnAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void afterReturn(final Object returnValue) throws Throwable {
        logger.info("value return was {}",  returnValue);
    }
}
