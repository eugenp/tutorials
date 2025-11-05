package com.baeldung.formatinstantobjectmapper.utils;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public interface Instants {

    ZoneOffset TIMEZONE = ZoneOffset.UTC;
    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT)
        .withZone(TIMEZONE);
}
