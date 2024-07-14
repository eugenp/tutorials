package com.baeldung.timestamptozoneddatetime;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

import org.joda.time.DateTime;

public class TimestampAndZonedDateTimeConversion {
    public static ZonedDateTime convertToZonedDateTimeUsingInstant(Timestamp timestamp) {
        Instant instant = timestamp.toInstant();
        return instant.atZone(ZoneId.systemDefault());
    }

    public static ZonedDateTime convertToZonedDateTimeUsingCalendar(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        return calendar.toInstant().atZone(ZoneId.systemDefault());
    }

    public static ZonedDateTime convertToZonedDateTimeUsingLocalDateTime(Timestamp timestamp) {
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return localDateTime.atZone(ZoneId.systemDefault());
    }

    public static ZonedDateTime convertToZonedDateTimeUsingJodaTime(Timestamp timestamp) {
        DateTime dateTime = new DateTime(timestamp.getTime());
        return dateTime.toGregorianCalendar().toZonedDateTime();
    }

    public static Timestamp convertToTimestampUsingJodaTime(ZonedDateTime zonedDateTime) {
        DateTime dateTime = new DateTime(zonedDateTime.toInstant().toEpochMilli());
        return new Timestamp(dateTime.getMillis());
    }

    public static Timestamp convertToTimeStampUsingInstant(ZonedDateTime zonedDateTime) {
        Instant instant = zonedDateTime.toInstant();
        return Timestamp.from(instant);
    }

    public static Timestamp convertToTimeStampUsingLocalDateTime(ZonedDateTime zonedDateTime) {
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        return Timestamp.valueOf(localDateTime);
    }


}
