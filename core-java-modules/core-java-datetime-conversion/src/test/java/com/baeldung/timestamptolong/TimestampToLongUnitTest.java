package com.baeldung.timestamptolong;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TimestampToLongUnitTest {
    private static final String timestampString = "2023-11-15 01:02:03";

    @Test
    public void givenSimpleDateFormat_whenFormattingDate_thenConvertToLong() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(timestampString);

        String currentDateString = sdf.format(date);
        long actualTimestamp = sdf.parse(currentDateString).getTime();
        assertEquals(1700010123000L, actualTimestamp);
    }

    @Test
    public void givenInstantClass_whenGettingTimestamp_thenConvertToLong() {
        Instant instant = LocalDateTime.parse(timestampString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.systemDefault())
                .toInstant();
        long actualTimestamp = instant.toEpochMilli();
        assertEquals(1700010123000L, actualTimestamp);
    }

    @Test
    public void givenJava8DateTime_whenGettingTimestamp_thenConvertToLong() {
        LocalDateTime localDateTime = LocalDateTime.parse(timestampString.replace(" ", "T"));
        long actualTimestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        assertEquals(1700010123000L, actualTimestamp);
    }
}
