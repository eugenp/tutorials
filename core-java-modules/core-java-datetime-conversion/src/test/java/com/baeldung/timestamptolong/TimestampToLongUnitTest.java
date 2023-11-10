package com.baeldung.timestamptolong;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class TimestampToLongUnitTest {

    @Test
    public void givenTimestampString_whenSimpleDateFormatConversion_thenConvertToLong() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long actualTimestamp = sdf.parse("2015-07-24 09:39:14").getTime();

        assertEquals(1437723554000L, actualTimestamp);
    }

    @Test
    public void givenTimestampString_whenInstantConversion_thenConvertToLong() {
        Instant instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse("2015-07-24T09:39:14.000Z"));
        long actualTimestamp = instant.toEpochMilli();

        assertEquals(1437730754000L, actualTimestamp);
    }

    @Test
    public void givenTimestampString_whenTimestampConversion_thenConvertToLong() {
        Timestamp timestamp = Timestamp.valueOf("2015-07-24 09:39:14");
        long actualTimestamp = timestamp.getTime();

        assertEquals(1437723554000L, actualTimestamp);
    }

    @Test
    public void givenTimestampString_whenJava8DateTimeConversion_thenConvertToLong() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("2015-07-24 09:39:14", formatter);
        long actualTimestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        assertEquals(1437723554000L, actualTimestamp);
    }
}
