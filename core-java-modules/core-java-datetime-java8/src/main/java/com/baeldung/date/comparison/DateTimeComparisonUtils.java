package com.baeldung.date.comparison;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.temporal.ChronoUnit.*;

public class DateTimeComparisonUtils {

    public static boolean isSameDay(LocalDateTime timestamp, LocalDate localDateToCompare) {
        return timestamp.toLocalDate().isEqual(localDateToCompare);
    }

    public static boolean isSameDay(LocalDateTime timestamp, LocalDateTime timestampToCompare) {
        return timestamp.truncatedTo(DAYS).isEqual(timestampToCompare.truncatedTo(DAYS));
    }

    public static boolean isSameHour(LocalDateTime timestamp, LocalDateTime timestampToCompare) {
        return timestamp.truncatedTo(HOURS).isEqual(timestampToCompare.truncatedTo(HOURS));
    }

    public static boolean isSameMinute(LocalDateTime timestamp, LocalDateTime timestampToCompare) {
        return timestamp.truncatedTo(MINUTES).isEqual(timestampToCompare.truncatedTo(MINUTES));
    }

    public static boolean isSameHour(ZonedDateTime zonedTimestamp, ZonedDateTime zonedTimestampToCompare) {
        return zonedTimestamp.truncatedTo(HOURS).isEqual(zonedTimestampToCompare.truncatedTo(HOURS));
    }

    public static boolean isSameHour(ZonedDateTime zonedDateTime, LocalDateTime localDateTime, ZoneId zoneId) {
        return isSameHour(zonedDateTime, localDateTime.atZone(zoneId));
    }
}
