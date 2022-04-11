package com.bealdung.datescanner;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Test;

public class DateScannerTest {

    @Test
    public void givenTestGetZonedDateTimeStaticMethod_whenMocked_thenReturnsZonedDateTimeSuccessfully() {
        ZoneId zoneId = ZoneId.of("Africa/Kampala");
        Assert.assertEquals(ZonedDateTime.of(getLocalDateTime(), zoneId), DateScanner.getZonedDateTime());
    }

    @Test
    public void givenTestGetLocalDateTimeStaticMethod_whenMocked_thenReturnsLocalDateTimeSuccessfully() {
        Assert.assertEquals(LocalDateTime.now(), DateScanner.getLocalDateTime());
    }

    @Test
    public void givenTestGetLocalTimeStaticMethod_whenMocked_thenReturnsLocalTimeSuccessfully() {
        Assert.assertEquals(LocalTime.now(), DateScanner.getLocalTime());
    }

    @Test
    public void givenTestGetDayOfWeekStaticMethod_whenMocked_thenReturnsGetDayOfWeekSuccessfully() {
        Assert.assertEquals(LocalDate.parse(getLocalDate().toString()).getDayOfWeek(), DateScanner.getDayOfWeek());
    }

    @Test
    public void givenTestGetLocalDateStaticMethod_whenMocked_thenReturnsLocalDateSuccessfully() {
        Assert.assertEquals(LocalDate.now(), DateScanner.getLocalDate());
    }

    public LocalDate getLocalDate(){
        return LocalDate.now();
    }

    public LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }
}