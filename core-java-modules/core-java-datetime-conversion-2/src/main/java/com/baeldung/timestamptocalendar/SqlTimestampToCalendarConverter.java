package com.baeldung.timestamptocalendar;

import java.sql.Timestamp;
import java.util.Calendar;

public class SqlTimestampToCalendarConverter {

    public static Calendar timestampToCalendar(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        return calendar;
    }

    public static Timestamp calendarToTimestamp(Calendar calendar) {
        return new Timestamp(calendar.getTimeInMillis());
    }
}
