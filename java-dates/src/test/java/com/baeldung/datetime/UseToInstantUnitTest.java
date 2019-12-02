package com.baeldung.datetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class UseToInstantUnitTest {

    private UseToInstant subject = new UseToInstant();

    @Test
    public void givenAGregorianCalenderDate_whenConvertingToLocalDate_thenAsExpected() {
        GregorianCalendar givenCalender = new GregorianCalendar(2018, Calendar.JULY, 28);

        LocalDateTime localDateTime = subject.convertDateToLocalDate(givenCalender);

        assertThat(localDateTime).isEqualTo("2018-07-28T00:00:00");
    }

    @Test
    public void givenADate_whenConvertingToLocalDate_thenAsExpected() {
        Date givenDate = new Date(1465817690000L);

        LocalDateTime localDateTime = subject.convertDateToLocalDate(givenDate);

        assertThat(localDateTime).isEqualTo("2016-06-13T13:34:50");
    }
}