package com.baeldung.log4j2.logtest;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

public class LogPrinter {
    private Logger logger = LoggerContext.getContext(false)
        .getLogger("com");

    public void printlog() {
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
    }
}
