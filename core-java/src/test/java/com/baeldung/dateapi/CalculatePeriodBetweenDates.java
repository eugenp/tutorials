package com.baeldung.dateapi;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import static junit.framework.TestCase.assertEquals;

public class CalculatePeriodBetweenDates {

    @Test
    public void givenTwoDates_whenUsingBetween_thenPeriodInDaysMonthsYears() {

        //given
        LocalDate startDate = LocalDate.of(2017, Month.MAY, 1);
        LocalDate endDate = LocalDate.of(2018, Month.AUGUST, 21);

        //when
        Period period = Period.between(startDate, endDate);

        //then
        assertEquals(20, period.get(ChronoUnit.DAYS));
        assertEquals(3, period.get(ChronoUnit.MONTHS));
        assertEquals(1, period.get(ChronoUnit.YEARS));

    }
}
