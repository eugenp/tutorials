package com.baeldung.datetime.dayofweek;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

import org.junit.Test;

public class DayOfWeekExtractorUnitTest {
    
    private DateFormat oldDateParser = new SimpleDateFormat("yyyy-MM-dd"); 

    @Test
    public void givenFeb29_2020_thenOldSaturdayNumber() throws ParseException {
        assertThat(DayOfWeekExtractor.getDayNumberOld(oldDateParser.parse("2020-02-29")) == Calendar.SATURDAY);
    }

    @Test
    public void givenFeb29_2020_and_localeUS_thenOldSaturdayText() throws ParseException {
        assertThat("Saturday".equals(DayOfWeekExtractor.getDayStringOld(oldDateParser.parse("2020-02-29"), Locale.US)) );
    }

    @Test
    public void givenFeb29_2020_and_localeDE_thenOldSaturdayText() throws ParseException {
        assertThat("Samstag".equals(DayOfWeekExtractor.getDayStringOld(oldDateParser.parse("2020-02-29"), Locale.GERMANY)) );
    }

    @Test
    public void givenFeb29_2020_thenNewSaturdayNumber() throws ParseException {
        assertThat(DayOfWeekExtractor.getDayNumberNew(LocalDate.parse("2020-02-29")) == Calendar.SATURDAY);
    }

    @Test
    public void givenFeb29_2020_and_localeUS_thenNewSaturdayText() throws ParseException {
        assertThat("Saturday".equals(DayOfWeekExtractor.getDayStringOld(oldDateParser.parse("2020-02-29"), Locale.US)) );
    }

    @Test
    public void givenFeb29_2020_and_localeDE_thenNewSaturdayText() throws ParseException {
        assertThat("Samstag".equals(DayOfWeekExtractor.getDayStringOld(oldDateParser.parse("2020-02-29"), Locale.GERMANY)) );
    }


}



