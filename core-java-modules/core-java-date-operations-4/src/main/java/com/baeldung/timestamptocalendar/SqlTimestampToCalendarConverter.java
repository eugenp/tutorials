package com.baeldung.timestamptocalendar;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;

public class SqlTimestampToCalendarConverter {

    public static void main(String... args) {
        Timestamp timestamp = new Timestamp(124, 3, 19, 9, 30, 0, 801789562);
        Calendar calendar = timestampToCalendar(timestamp);
        System.out.println(calendar.getTime());
        Instant instant = timestampToInstant(timestamp);
        System.out.println(instant);
    }

    public static Calendar timestampToCalendar(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        return calendar;
    }

    public static Instant timestampToInstant(Timestamp timestamp) {
        return timestamp.toInstant();
    }
}
