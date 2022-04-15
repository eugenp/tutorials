package com.bealdung.datescanner;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Test;

public class DateScannerUnitTest {
    @Test
    public void whenDateIsString_thenResultIsDate(){
        Assert.assertEquals("2019-05-30", DateScanner.txtDate("Thursday 30 May 2019"));
    }
}