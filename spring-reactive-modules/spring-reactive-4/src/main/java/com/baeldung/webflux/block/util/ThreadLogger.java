package com.baeldung.webflux.block.util;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public final class ThreadLogger {
    private ThreadLogger() {}

    private static final String MESSAGE_FORMAT = "[%s] ThreadName: %s, Time: %s%n";
    private static final String ZONE_ID = "UTC";

    public static void log(String identifier) {
        System.out.printf(MESSAGE_FORMAT, identifier, Thread.currentThread().getName(), OffsetDateTime.now(
            ZoneId.of(ZONE_ID)));
    }
}
