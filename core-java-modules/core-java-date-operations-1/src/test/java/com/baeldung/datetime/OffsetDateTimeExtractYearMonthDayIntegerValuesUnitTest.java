package com.baeldung.datetime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.OffsetDateTime;

import org.junit.Test;

public class OffsetDateTimeExtractYearMonthDayIntegerValuesUnitTest {

    OffsetDateTimeExtractYearMonthDayIntegerValues offsetDateTimeExtractYearMonthDayIntegerValues = new OffsetDateTimeExtractYearMonthDayIntegerValues();
    
    OffsetDateTime offsetDateTime=OffsetDateTime.parse("2007-12-03T10:15:30+01:00");
    
    @Test
    public void whenGetYear_thenCorrectYear()
    {
       int actualYear=offsetDateTimeExtractYearMonthDayIntegerValues.getYear(offsetDateTime);
       assertThat(actualYear,is(2007));
    }
    
    @Test
    public void whenGetMonth_thenCorrectMonth()
    {
       int actualMonth=offsetDateTimeExtractYearMonthDayIntegerValues.getMonth(offsetDateTime);
       assertThat(actualMonth,is(12));
    }
    
    @Test
    public void whenGetDay_thenCorrectDay()
    {
       int actualDayOfMonth=offsetDateTimeExtractYearMonthDayIntegerValues.getDay(offsetDateTime);
       assertThat(actualDayOfMonth,is(03));
    }
}
