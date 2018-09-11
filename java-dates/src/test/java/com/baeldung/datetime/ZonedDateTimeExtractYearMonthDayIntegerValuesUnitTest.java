package com.baeldung.datetime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.ZonedDateTime;

import org.junit.Test;

public class ZonedDateTimeExtractYearMonthDayIntegerValuesUnitTest {

    ZonedDateTimeExtractYearMonthDayIntegerValues zonedDateTimeExtractYearMonthDayIntegerValues = new ZonedDateTimeExtractYearMonthDayIntegerValues();
    
    ZonedDateTime zonedDateTime=ZonedDateTime.parse("2007-12-03T10:15:30+01:00");
    
    @Test
    public void whenGetYear_thenCorrectYear()
    {
       int actualYear=zonedDateTimeExtractYearMonthDayIntegerValues.getYear(zonedDateTime);
       assertThat(actualYear,is(2007));
    }
    
    @Test
    public void whenGetMonth_thenCorrectMonth()
    {
       int actualMonth=zonedDateTimeExtractYearMonthDayIntegerValues.getMonth(zonedDateTime);
       assertThat(actualMonth,is(12));
    }
    
    @Test
    public void whenGetDay_thenCorrectDay()
    {
       int actualDayOfMonth=zonedDateTimeExtractYearMonthDayIntegerValues.getDay(zonedDateTime);
       assertThat(actualDayOfMonth,is(03));
    }
}
