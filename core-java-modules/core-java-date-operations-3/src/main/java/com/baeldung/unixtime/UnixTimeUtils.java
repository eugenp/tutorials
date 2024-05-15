package com.baeldung.unixtime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class UnixTimeUtils {

    private UnixTimeUtils() {
    }

    public static Date dateFrom(long timestamp) {
        return new Date(timestamp);
    }

    public static Calendar calendarFrom(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return calendar;
    }

    public static Instant fromNanos(long timestamp) {
        long seconds = timestamp / 1_000_000_000;
        long nanos = timestamp % 1_000_000_000;

        return Instant.ofEpochSecond(seconds, nanos);
    }

    public static Instant fromTimestamp(long timestamp) {
        return Instant.ofEpochMilli(millis(timestamp));
    }

    public static String format(Instant instant) {
        LocalDateTime time = localTimeUtc(instant);
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static LocalDateTime localTimeUtc(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    private static long millis(long timestamp) {
        if (timestamp >= 1E16 || timestamp <= -1E16) {
            return timestamp / 1_000_000;
        }

        if (timestamp >= 1E14 || timestamp <= -1E14) {
            return timestamp / 1_000;
        }

        if (timestamp >= 1E11 || timestamp <= -3E10) {
            return timestamp;
        }

        return timestamp * 1_000;
    }
}
