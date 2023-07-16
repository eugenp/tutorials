package com.baeldung.akkahttp;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUnitTest {

    static Logger LOGGER = LoggerFactory.getLogger(LoggerUnitTest.class);

    @Test
    public void whenPositiveArgument_thenReturnIndentedString() {
        LOGGER.debug("THIS IS DEBUG LEVEL");
        LOGGER.info("THIS IS INFO LEVEL");
        LOGGER.warn("THIS IS WARN LEVEL");
        LOGGER.error("THIS IS ERROR LEVEL");
    }

}
