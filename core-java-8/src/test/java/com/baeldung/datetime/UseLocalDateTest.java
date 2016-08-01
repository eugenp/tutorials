package com.baeldung.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Assert;
import org.junit.Test;

public class UseLocalDateTest {
    
    UseLocalDate useLocalDate = new UseLocalDate();
    
    @Test
    public void givenValues_whenUsingFactoryOf_thenLocalDate(){
        Assert.assertEquals("2016-05-10",useLocalDate.getLocalDateUsingFactoryOfMethod(2016,5,10).toString());
    }
    
    @Test
    public void givenString_whenUsingParse_thenLocalDate(){
        Assert.assertEquals("2016-05-10",useLocalDate.getLocalDateUsingParseMethod("2016-05-10").toString());
    }
    
    @Test
    public void whenUsingClock_thenLocalDate(){
        Assert.assertEquals(LocalDate.now(),useLocalDate.getLocalDateFromClock());
    }
    
    @Test
    public void givenDate_whenUsingPlus_thenNextDay(){
        Assert.assertEquals(LocalDate.now().plusDays(1),useLocalDate.getNextDay(LocalDate.now()));
    }
    
    @Test
    public void givenDate_whenUsingMinus_thenPreviousDay(){
        Assert.assertEquals(LocalDate.now().minusDays(1),useLocalDate.getPreviousDay(LocalDate.now()));
    }
    
    @Test
    public void givenToday_whenUsingGetDayOfWeek_thenDayOfWeek(){
        Assert.assertEquals(DayOfWeek.SUNDAY,useLocalDate.getDayOfWeek(LocalDate.parse("2016-05-22")));
    }
    
    @Test
    public void givenToday_whenUsingWithTemporalAdjuster_thenFirstDayOfMonth(){
        Assert.assertEquals(1,useLocalDate.getFirstDayOfMonth().getDayOfMonth());
    }
    
    @Test
    public void givenLocalDate_whenUsingAtStartOfDay_thenReturnMidnight(){
        Assert.assertEquals(LocalDateTime.parse("2016-05-22T00:00:00"),useLocalDate.getStartOfDay(LocalDate.parse("2016-05-22")));
    }

}
