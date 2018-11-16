package com.baeldung.timestamp;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class StringToTimestampConverter {

    public static Timestamp convert(String timestampAsString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return Timestamp.valueOf(LocalDateTime.from(formatter.parse(timestampAsString)));
    }
}