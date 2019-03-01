package com.baeldung.datetime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;

public class LocalDateTimeExtractYearMonthDayIntegerValuesUnitTest {

    LocalDateTimeExtractYearMonthDayIntegerValues localDateTimeExtractYearMonthDayIntegerValues = new LocalDateTimeExtractYearMonthDayIntegerValues();
    
    LocalDateTime localDateTime=LocalDateTime.parse("2007-12-03T10:15:30");
    
    @Test
    public void whenGetYear_thenCorrectYear()
    {
       int actualYear=localDateTimeExtractYearMonthDayIntegerValues.getYear(localDateTime);
       assertThat(actualYear,is(2007));
    }
    
    @Test
    public void whenGetMonth_thenCorrectMonth()
    {
       int actualMonth=localDateTimeExtractYearMonthDayIntegerValues.getMonth(localDateTime);
       assertThat(actualMonth,is(12));
    }
    
    @Test
    public void whenGetDay_thenCorrectDay()
    {
       int actualDayOfMonth=localDateTimeExtractYearMonthDayIntegerValues.getDay(localDateTime);
       assertThat(actualDayOfMonth,is(03));
    }
}
