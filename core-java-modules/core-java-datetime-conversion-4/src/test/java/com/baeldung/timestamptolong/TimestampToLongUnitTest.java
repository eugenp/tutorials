package com.baeldung.timestamptolong;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

public class TimestampToLongUnitTest {
    private static final String timestampString = "2023-11-15 01:02:03";
    private static final long timestampInMillis = 1700010123000L;

    @Test
    public void givenSimpleDateFormat_whenFormattingDate_thenConvertToLong() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = sdf.parse(timestampString);

        String currentDateString = sdf.format(date);
        long actualTimestamp = sdf.parse(currentDateString).getTime();
        assertEquals(1700010123000L, actualTimestamp);
    }

    @Test
    public void givenInstantClass_whenGettingTimestamp_thenConvertToLong() {
        Instant instant = LocalDateTime.parse(timestampString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .atZone(ZoneId.of("UTC"))
                .toInstant();
        long actualTimestamp = instant.toEpochMilli();
        assertEquals(1700010123000L, actualTimestamp);
    }

    @Test
    public void givenJava8DateTime_whenGettingTimestamp_thenConvertToLong() {
        LocalDateTime localDateTime = LocalDateTime.parse(timestampString.replace(" ", "T"));
        long actualTimestamp = localDateTime.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();
        assertEquals(1700010123000L, actualTimestamp);
    }

    @Test
    public void givenJodaTime_whenGettingTimestamp_thenConvertToLong() {
        DateTime dateTime = new DateTime(timestampInMillis, DateTimeZone.UTC);
        org.joda.time.LocalDateTime localDateTime = dateTime.toLocalDateTime();

        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String actualTimestamp = formatter.print(localDateTime);

        assertEquals(timestampString, actualTimestamp);
    }
}
