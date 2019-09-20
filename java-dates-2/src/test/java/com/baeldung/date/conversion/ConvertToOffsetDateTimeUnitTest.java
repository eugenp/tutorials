package com.baeldung.date.conversion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.OffsetDateTime;
import java.util.Date;

import org.junit.Test;

public class ConvertToOffsetDateTimeUnitTest {

    @Test
    public void whenDateIsNotNull_thenConvertToOffsetDateTime() {
        Date date = new Date();
        assertTrue(ConvertToOffsetDateTime.convert(date) instanceof OffsetDateTime);
    }

    @Test
    public void givenDate_whenHasOffset_thenConvertWithOffset() {
        Date date = new Date();
        date.setHours(6);
        date.setMinutes(30);
        OffsetDateTime odt = ConvertToOffsetDateTime.convert(date, 3, 30);
        assertEquals(10, odt.getHour());
        assertEquals(0, odt.getMinute());
    }

}
