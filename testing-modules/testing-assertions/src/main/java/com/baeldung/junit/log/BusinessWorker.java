package com.baeldung.junit.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessWorker {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessWorker.class);

    public void generateLogs(String msg) {
        LOGGER.trace(msg);
        LOGGER.debug(msg);
        LOGGER.info(msg);
        LOGGER.warn(msg);
        LOGGER.error(msg);
    }
}
