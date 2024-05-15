package com.baeldung.timestamptozoneddatetime;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimestampAndZonedDateTimeConversionUnitTest {
    @Test
     void givenTimestamp_whenUsingInstant_thenConvertToZonedDateTime() {
        Timestamp timestamp = Timestamp.valueOf("2024-04-17 12:30:00");
        ZonedDateTime actualResult = TimestampAndZonedDateTimeConversion.convertToZonedDateTimeUsingInstant(timestamp);
        ZonedDateTime expectedResult = ZonedDateTime.of(2024, 4, 17, 12, 30, 0, 0, ZoneId.systemDefault());
        Assertions.assertEquals(expectedResult.toLocalDate(), actualResult.toLocalDate());
        Assertions.assertEquals(expectedResult.toLocalTime(), actualResult.toLocalTime());
    }

    @Test
    void givenTimestamp_whenUsingCalendar_thenConvertToZonedDateTime() {
        Timestamp timestamp = Timestamp.valueOf("2024-04-17 12:30:00");
        ZonedDateTime actualResult = TimestampAndZonedDateTimeConversion.convertToZonedDateTimeUsingCalendar(timestamp);
        ZonedDateTime expectedResult = ZonedDateTime.of(2024, 4, 17, 12, 30, 0, 0, ZoneId.systemDefault());
        Assertions.assertEquals(expectedResult.toLocalDate(), actualResult.toLocalDate());
        Assertions.assertEquals(expectedResult.toLocalTime(), actualResult.toLocalTime());
    }

    @Test
    void givenTimestamp_whenUsingLocalDateTime_thenConvertToZonedDateTime() {
        Timestamp timestamp = Timestamp.valueOf("2024-04-17 12:30:00");
        ZonedDateTime actualResult = TimestampAndZonedDateTimeConversion.convertToZonedDateTimeUsingLocalDateTime(timestamp);
        ZonedDateTime expectedResult = ZonedDateTime.of(2024, 4, 17, 12, 30, 0, 0, ZoneId.systemDefault());
        Assertions.assertEquals(expectedResult.toLocalDate(), actualResult.toLocalDate());
        Assertions.assertEquals(expectedResult.toLocalTime(), actualResult.toLocalTime());
    }

    @Test
    void givenTimestamp_whenUsingJodaTime_thenConvertToZonedDateTime() {
        Timestamp timestamp = Timestamp.valueOf("2024-04-17 12:30:00");
        ZonedDateTime actualResult = TimestampAndZonedDateTimeConversion.convertToZonedDateTimeUsingJodaTime(timestamp);
        ZonedDateTime expectedResult = ZonedDateTime.of(2024, 4, 17, 12, 30, 0, 0, ZoneId.systemDefault());
        Assertions.assertEquals(expectedResult.toLocalDate(), actualResult.toLocalDate());
        Assertions.assertEquals(expectedResult.toLocalTime(), actualResult.toLocalTime());
    }

    @Test
    void givenZonedDateTime_whenUsingInstant_thenConvertToTimestamp() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2024, 4, 17, 12, 30, 0, 0, ZoneId.systemDefault());
        Timestamp actualResult = TimestampAndZonedDateTimeConversion.convertToTimeStampUsingInstant(zonedDateTime);
        Timestamp expectedResult = Timestamp.valueOf("2024-04-17 12:30:00");
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void givenZonedDateTime_whenUsingLocalDateTime_thenConvertToTimestamp() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2024, 4, 17, 12, 30, 0, 0, ZoneId.systemDefault());
        Timestamp actualResult = TimestampAndZonedDateTimeConversion.convertToTimeStampUsingLocalDateTime(zonedDateTime);
        Timestamp expectedResult = Timestamp.valueOf("2024-04-17 12:30:00");
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void givenZonedDateTime_whenUsingJodaDateTime_thenConvertToTimestamp() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2024, 4, 17, 12, 30, 0, 0, ZoneId.systemDefault());
        Timestamp actualResult = TimestampAndZonedDateTimeConversion.convertToTimestampUsingJodaTime(zonedDateTime);
        Timestamp expectedResult = Timestamp.valueOf("2024-04-17 12:30:00");
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
