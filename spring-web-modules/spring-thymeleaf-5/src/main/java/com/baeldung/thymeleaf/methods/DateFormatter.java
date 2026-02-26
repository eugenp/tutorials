package com.baeldung.thymeleaf.methods;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static String DEFAULT_FORMAT = "YYYY-MM-DD hh:mm:ss";

    public static String defaultDateFormat() {
        return DEFAULT_FORMAT;
    }

    public static String format(Instant instant) {
        return DateTimeFormatter.ofPattern(DEFAULT_FORMAT).format(instant.atZone(ZoneId.of("UTC")));
    }
}
