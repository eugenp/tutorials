package com.baeldung.datetime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.Assert;
import org.junit.Test;

public class UseLocalDateTimeUnitTest {

    UseLocalDateTime useLocalDateTime = new UseLocalDateTime();

    @Test
    public void givenString_whenUsingParse_thenLocalDateTime() {
        Assert.assertEquals(LocalDate.of(2016, Month.MAY, 10), useLocalDateTime.getLocalDateTimeUsingParseMethod("2016-05-10T06:30").toLocalDate());
        Assert.assertEquals(LocalTime.of(6, 30), useLocalDateTime.getLocalDateTimeUsingParseMethod("2016-05-10T06:30").toLocalTime());
    }
}
