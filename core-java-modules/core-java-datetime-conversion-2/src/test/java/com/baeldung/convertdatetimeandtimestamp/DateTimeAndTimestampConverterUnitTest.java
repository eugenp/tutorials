package com.baeldung.convertdatetimeandtimestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;


public class DateTimeAndTimestampConverterUnitTest {

    @Test
    public void givenTimestamp_whenUsingConstructor_thenConvertToDateTime() {
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        DateTime expectedDateTime = new DateTime(currentTimeMillis);
        DateTime convertedDateTime = DateTimeAndTimestampConverter.convertToDateTimeUsingConstructor(timestamp);
        assertEquals(expectedDateTime, convertedDateTime);
    }

    @Test
    public void givenTimestamp_whenUsingInstant_thenConvertToDateTime() {
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        DateTime expectedDateTime = new DateTime(currentTimeMillis);
        DateTime convertedDateTime = DateTimeAndTimestampConverter.convertToDateTimeUsingInstant(timestamp);
        assertEquals(expectedDateTime, convertedDateTime);
    }

    @Test
    public void givenTimestamp_whenUsingLocalDateTime_thenConvertToDateTime() {
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(currentTimeMillis);
        DateTime expectedDateTime = new DateTime(currentTimeMillis);
        DateTime convertedDateTime = DateTimeAndTimestampConverter.convertToDateTimeUsingLocalDateTime(timestamp);
        assertEquals(expectedDateTime, convertedDateTime);
    }

    @Test
    public void givenDateTime_whenUsingConstructor_thenConvertToTimestamp() {
        long currentTimeMillis = System.currentTimeMillis();
        DateTime dateTime = new DateTime(currentTimeMillis);
        Timestamp expectedTimestamp = new Timestamp(currentTimeMillis);
        Timestamp convertedTimestamp = DateTimeAndTimestampConverter.convertToTimestampUsingConstructor(dateTime);
        assertEquals(expectedTimestamp, convertedTimestamp);
    }

    @Test
    public void givenDateTime_whenUsingInstant_thenConvertToTimestamp() {
        long currentTimeMillis = System.currentTimeMillis();
        DateTime dateTime = new DateTime(currentTimeMillis);
        Timestamp expectedTimestamp = new Timestamp(currentTimeMillis);
        Timestamp convertedTimestamp = DateTimeAndTimestampConverter.convertToTimestampUsingInstant(dateTime);
        assertEquals(expectedTimestamp, convertedTimestamp);
    }

    @Test
    public void givenDateTime_whenUsingLocalDateTime_thenConvertToTimestamp() {
        long currentTimeMillis = System.currentTimeMillis();
        DateTime dateTime = new DateTime(currentTimeMillis);
        Timestamp expectedTimestamp = new Timestamp(currentTimeMillis);
        Timestamp convertedTimestamp = DateTimeAndTimestampConverter.convertToTimestampUsingLocalDateTime(dateTime);
        assertEquals(expectedTimestamp, convertedTimestamp);
    }
}
