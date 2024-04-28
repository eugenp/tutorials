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
        Assertions.assertEquals(ZoneId.of("Asia/Kolkata"), actualResult.getZone());
        Assertions.assertEquals(2024, actualResult.getYear());
        Assertions.assertEquals(4, actualResult.getMonthValue());
        Assertions.assertEquals(17, actualResult.getDayOfMonth());
    }

    @Test
    void givenTimestamp_whenUsingCalendar_thenConvertToZonedDateTime() {
        Timestamp timestamp = Timestamp.valueOf("2024-04-17 12:30:00");
        ZonedDateTime actualResult = TimestampAndZonedDateTimeConversion.convertToZonedDateTimeUsingCalendar(timestamp);
        Assertions.assertEquals(ZoneId.of("Asia/Kolkata"), actualResult.getZone());
        Assertions.assertEquals(2024, actualResult.getYear());
        Assertions.assertEquals(4, actualResult.getMonthValue());
        Assertions.assertEquals(17, actualResult.getDayOfMonth());
    }

    @Test
    void givenTimestamp_whenUsingLocalDateTime_thenConvertToZonedDateTime() {
        Timestamp timestamp = Timestamp.valueOf("2024-04-17 12:30:00");
        ZonedDateTime actualResult = TimestampAndZonedDateTimeConversion.convertToZonedDateTimeUsingLocalDateTime(timestamp);
        Assertions.assertEquals(ZoneId.of("Asia/Kolkata"), actualResult.getZone());
        Assertions.assertEquals(2024, actualResult.getYear());
        Assertions.assertEquals(4, actualResult.getMonthValue());
        Assertions.assertEquals(17, actualResult.getDayOfMonth());
    }

    @Test
    void givenTimestamp_whenUsingJodaTime_thenConvertToZonedDateTime() {
        Timestamp timestamp = Timestamp.valueOf("2024-04-17 12:30:00");
        ZonedDateTime actualResult = TimestampAndZonedDateTimeConversion.convertToZonedDateTimeUsingJodaTime(timestamp);
        Assertions.assertEquals(ZoneId.of("Asia/Kolkata"), actualResult.getZone());
        Assertions.assertEquals(2024, actualResult.getYear());
        Assertions.assertEquals(4, actualResult.getMonthValue());
        Assertions.assertEquals(17, actualResult.getDayOfMonth());
    }

    @Test
    void givenZonedDateTime_whenUsingInstant_thenConvertToTimestamp() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2024-04-17T12:30+05:30[Asia/Kolkata]");
        Timestamp timestamp = TimestampAndZonedDateTimeConversion.convertToTimeStampUsingInstant(zonedDateTime);
        Assertions.assertEquals(timestamp.toString(), "2024-04-17 12:30:00.0");
    }

    @Test
    void givenZonedDateTime_whenUsingLocalDateTime_thenConvertToTimestamp() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2024-04-17T12:30+05:30[Asia/Kolkata]");
        Timestamp timestamp = TimestampAndZonedDateTimeConversion.convertToTimeStampUsingLocalDateTime(zonedDateTime);
        Assertions.assertEquals(timestamp.toString(), "2024-04-17 12:30:00.0");
    }

    @Test
    void givenZonedDateTime_whenUsingJodaDateTime_thenConvertToTimestamp() {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2024-04-17T12:30+05:30[Asia/Kolkata]");
        Timestamp timestamp = TimestampAndZonedDateTimeConversion.convertToTimestampUsingJodaTime(zonedDateTime);
        Assertions.assertEquals(timestamp.toString(), "2024-04-17 12:30:00.0");
    }
}
