package com.baeldung.webflux.block.util;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ThreadLogger {
    private static final Logger logger = LoggerFactory.getLogger(ThreadLogger.class);
    private ThreadLogger() {}

    private static final String MESSAGE_FORMAT = "[{}] ThreadName: {}, Time: {}";
    private static final String ZONE_ID = "UTC";

    public static void log(String identifier) {
       logger.info(MESSAGE_FORMAT, identifier, Thread.currentThread().getName(), OffsetDateTime.now(
            ZoneId.of(ZONE_ID)));
    }
}
