package com.baeldung.convertdateandzoneddatetime;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateAndZonedDateTimeConverterUnitTest {

    @Test
    public void givenZonedDateTime_whenConvertToDate_thenCorrect() {
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("UTC"));
        Date date = DateAndZonedDateTimeConverter.convertToDate(zdt);
        assertEquals(Date.from(zdt.toInstant()), date);
    }

    @Test
    public void givenDate_whenConvertToZonedDateTime_thenCorrect() {
        Date date = new Date();
        ZoneId zoneId = ZoneId.of("UTC");
        ZonedDateTime zdt = DateAndZonedDateTimeConverter.convertToZonedDateTime(date, zoneId);
        assertEquals(date.toInstant().atZone(zoneId), zdt);
    }

}
