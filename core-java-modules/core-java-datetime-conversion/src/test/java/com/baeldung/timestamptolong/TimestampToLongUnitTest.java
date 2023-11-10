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
        String timestampString = "2015-07-24 09:39:14";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long expectedTimestamp = 1437723554000L;
        long actualTimestamp = sdf.parse(timestampString).getTime();

        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void givenTimestampString_whenInstantConversion_thenConvertToLong() {
        String timestampString = "2015-07-24T09:39:14.000Z";

        long expectedTimestamp = 1437730754000L;
        Instant instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(timestampString));
        long actualTimestamp = instant.toEpochMilli();

        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void givenTimestampString_whenTimestampConversion_thenConvertToLong() {
        String timestampString = "2015-07-24 09:39:14";

        long expectedTimestamp = 1437723554000L;
        Timestamp timestamp = Timestamp.valueOf(timestampString);
        long actualTimestamp = timestamp.getTime();

        assertEquals(expectedTimestamp, actualTimestamp);
    }

    @Test
    public void givenTimestampString_whenJava8DateTimeConversion_thenConvertToLong() {
        String timestampString = "2015-07-24 09:39:14";

        long expectedTimestamp = 1437723554000L;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(timestampString, formatter);
        long actualTimestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        assertEquals(expectedTimestamp, actualTimestamp);
    }
}
