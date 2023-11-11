package com.baeldung.timestamptolong;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class TimestampToLongUnitTest {

    private static final long TOLERANCE = 1000;

    @Test
    public void givenSimpleDateFormat_whenFormattingDate_thenTConvertToLong() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentDateString = sdf.format(new Date());
        long actualTimestamp = sdf.parse(currentDateString).getTime();

        assertTrue(Math.abs(System.currentTimeMillis() - actualTimestamp) < TOLERANCE);
    }

    @Test
    public void givenInstantClass_whenGettingTimestamp_thenTConvertToLong() {
        Instant instant = Instant.now();
        long actualTimestamp = instant.toEpochMilli();

        assertTrue(Math.abs(System.currentTimeMillis() - actualTimestamp) < TOLERANCE);
    }

    @Test
    public void givenTimestamp_whenCreatingTimestamp_thenTConvertToLong() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long actualTimestamp = timestamp.getTime();

        assertTrue(Math.abs(System.currentTimeMillis() - actualTimestamp) < TOLERANCE);
    }

    @Test
    public void givenJava8DateTime_whenGettingTimestamp_thenTConvertToLong() {
        LocalDateTime localDateTime = LocalDateTime.now();
        long actualTimestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        assertTrue(Math.abs(System.currentTimeMillis() - actualTimestamp) < TOLERANCE);
    }
}
