package com.baeldung.offsetdatetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class ConvertToOffsetDateTimeUnitTest {

    @Test
    public void whenDateIsNotNull_thenConvertToOffsetDateTime() {
        Date date = new Date();
        assertTrue(ConvertToOffsetDateTime.convert(date) instanceof OffsetDateTime);
    }

    @Test
    public void givenDate_whenHasOffset_thenConvertWithOffset() {
        TimeZone prevTimezone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Date date = new Date();
        date.setHours(6);
        date.setMinutes(30);

        OffsetDateTime odt = ConvertToOffsetDateTime.convert(date, 3, 30);
        assertEquals(10, odt.getHour());
        assertEquals(0, odt.getMinute());

        // Reset the timezone to its original value to prevent side effects
        TimeZone.setDefault(prevTimezone);
    }

    @Test
    public void givenDate_whenUsingGetTimezoneOffsetMethod_thenConvertWithOffset() {
        Date date = new Date(98, 1, 14, 5, 30);

        OffsetDateTime offsetDateTime = ConvertToOffsetDateTime.convertWithGetTimeZone(date);
        assertEquals(5, offsetDateTime.getHour());
        assertEquals(30, offsetDateTime.getMinute());
    }

}
