package com.baeldung.timestamp;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class TimestampToStringConverter {

    public static String convert(Timestamp timestamp, String desiredPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(desiredPattern);
        return formatter.format(timestamp.toLocalDateTime());
    }
}
