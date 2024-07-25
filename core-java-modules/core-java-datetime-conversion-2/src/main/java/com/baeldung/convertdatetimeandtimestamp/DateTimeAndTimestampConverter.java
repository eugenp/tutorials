package com.baeldung.convertdatetimeandtimestamp;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.joda.time.DateTime;

public class DateTimeAndTimestampConverter {

    public static DateTime convertToDateTimeUsingConstructor(Timestamp timestamp) {
        return new DateTime(timestamp.getTime());
    }

    public static DateTime convertToDateTimeUsingInstant(Timestamp timestamp) {
        Instant instant = timestamp.toInstant();
        return new DateTime(instant.toEpochMilli());
    }

    public static DateTime convertToDateTimeUsingLocalDateTime(Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return new DateTime(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    public static Timestamp convertToTimestampUsingConstructor(DateTime dateTime) {
        return new Timestamp(dateTime.getMillis());
    }

    public static Timestamp convertToTimestampUsingInstant(DateTime dateTime) {
        Instant instant = Instant.ofEpochMilli(dateTime.getMillis());
        return Timestamp.from(instant);
    }

    public static Timestamp convertToTimestampUsingLocalDateTime(DateTime dateTime) {
        Instant instant = Instant.ofEpochMilli(dateTime.getMillis());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return Timestamp.valueOf(localDateTime);
    }
}
