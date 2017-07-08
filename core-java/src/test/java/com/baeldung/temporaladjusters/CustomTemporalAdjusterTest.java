package com.baeldung.temporaladjusters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.temporaladjuster.CustomTemporalAdjuster;

public class CustomTemporalAdjusterTest {

    @Test
    public void whenAdjustAndImplementInterface_thenNextWorkingDay() {
        LocalDate localDate = LocalDate.of(2017, 07, 8);
        CustomTemporalAdjuster temporalAdjuster = new CustomTemporalAdjuster();
        LocalDate nextWorkingDay = localDate.with(temporalAdjuster);

        Assert.assertEquals("2017-07-10", nextWorkingDay.toString());
    }

    @Test
    public void whenAdjust_thenNextWorkingDay() {
        LocalDate localDate = LocalDate.of(2017, 07, 8);
        TemporalAdjuster temporalAdjuster = NEXT_WORKING_DAY;
        LocalDate date = localDate.with(temporalAdjuster);

        Assert.assertEquals("2017-07-10", date.toString());
    }

    @Test
    public void whenAdjust_thenFourteenDaysAfterDate() {
        LocalDate localDate = LocalDate.of(2017, 07, 8);
        TemporalAdjuster temporalAdjuster = (t) -> t.plus(Period.ofDays(14));
        LocalDate result = localDate.with(temporalAdjuster);

        String fourteenDaysAfterDate = "2017-07-22";

        Assert.assertEquals(fourteenDaysAfterDate, result.toString());
    }

    static TemporalAdjuster NEXT_WORKING_DAY = TemporalAdjusters.ofDateAdjuster(date -> {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int daysToAdd;
        if (dayOfWeek == DayOfWeek.FRIDAY)
            daysToAdd = 3;
        else if (dayOfWeek == DayOfWeek.SATURDAY)
            daysToAdd = 2;
        else
            daysToAdd = 1;
        return date.plusDays(daysToAdd);
    });
}
