package com.baeldung.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdderBeforeAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void beforeAdvice() throws Throwable {
        logger.info("I would be executed just before method starts");
    }
}
