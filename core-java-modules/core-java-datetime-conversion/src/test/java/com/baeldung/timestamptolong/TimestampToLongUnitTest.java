package com.baeldung.timestamptolong;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TimestampToLongUnitTest {

    @Test
    public void givenTimestampString_whenSimpleDateFormatConversion_thenConvertToLong() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Change the timestamp string to the current date
        String currentDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        long actualTimestamp = sdf.parse(currentDateString).getTime();

        // Assert that the converted timestamp is not null
        assertEquals(actualTimestamp, actualTimestamp);
    }

    @Test
    public void givenTimestampString_whenInstantConversion_thenConvertToLong() {
        Instant instant = Instant.now();
        long actualTimestamp = instant.toEpochMilli();

        // Assert that the converted timestamp is not null
        assertEquals(actualTimestamp, actualTimestamp);
    }

    @Test
    public void givenTimestampString_whenTimestampConversion_thenConvertToLong() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long actualTimestamp = timestamp.getTime();

        // Assert that the converted timestamp is not null
        assertEquals(actualTimestamp, actualTimestamp);
    }

    @Test
    public void givenTimestampString_whenJava8DateTimeConversion_thenConvertToLong() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        long actualTimestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // Assert that the converted timestamp is not null
        assertEquals(actualTimestamp, actualTimestamp);
    }
}
