package com.baeldung.timestamptocalendar;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;

import org.junit.Test;

public class SqlTimestampToCalendarConverterTest {

    @Test
    public void whenConvert_thenEqualMillis() {
        Timestamp timestamp = new Timestamp(124, 3, 19, 9, 30, 0, 801789562);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        assertEquals(calendar.getTimeInMillis(), timestamp.getTime());
        System.out.println(timestamp);// 2024-04-19 09:30:00.801789562
        timestamp.setTime(calendar.getTimeInMillis());
        System.out.println(timestamp);// 2024-04-19 09:30:00.801
    }

    @Test
    public void whenConvert_thenEqualNanos() {
        Timestamp timestamp = new Timestamp(124, 3, 19, 9, 30, 0, 801789562);
        Instant instantNoTZ = timestamp.toInstant();
        Instant instantTZ = timestamp.toInstant().atZone(ZoneId.systemDefault()).toInstant();
        assertEquals(instantNoTZ.getEpochSecond(), instantTZ.getEpochSecond());
        assertEquals(instantNoTZ.getNano(), instantTZ.getNano());
    }
}
