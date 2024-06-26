package com.baeldung.timestamptocalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Test;

public class SqlTimestampToCalendarConverterUnitTest {

    @Test
    public void givenTimestamp_whenConvertToCalendar_thenEqualMillis() {
        Timestamp timestamp = new Timestamp(1713544200801L);
        Calendar calendar = SqlTimestampToCalendarConverter.timestampToCalendar(timestamp);
        assertEquals(calendar.getTimeInMillis(), timestamp.getTime());
    }

    @Test
    public void givenCalendarFromTimestamp_whenConvertBackToTimestamp_thenEqualMillis() {
        Timestamp timestamp = new Timestamp(1713544200801L);
        Calendar calendar = SqlTimestampToCalendarConverter.timestampToCalendar(timestamp);
        timestamp = SqlTimestampToCalendarConverter.calendarToTimestamp(calendar);
        assertEquals(calendar.getTimeInMillis(), timestamp.getTime());
    }

    @Test
    public void givenTimestamp_whenConvertToCalendarAndBack_thenLoseNanos() {
        int nanos = 801789562;
        int losslessNanos = 801000000;
        Timestamp timestamp = new Timestamp(1713544200801L);
        timestamp.setNanos(nanos);
        assertEquals(nanos, timestamp.getNanos());
        Calendar calendar = SqlTimestampToCalendarConverter.timestampToCalendar(timestamp);
        timestamp = SqlTimestampToCalendarConverter.calendarToTimestamp(calendar);
        assertEquals(losslessNanos, timestamp.getNanos());
    }
}
