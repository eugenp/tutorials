package com.baeldung.temporaladjusters;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.temporaladjuster.CustomTemporalAdjuster;
import com.baeldung.temporaladjuster.TemporalAdjusterUtil;

public class CustomTemporalAdjusterTest {

    @Test
    public void whenAdjustAndImplementInterface_thenNextWorkingDay() {
        LocalDate localDate = LocalDate.now();
        CustomTemporalAdjuster temporalAdjuster = new CustomTemporalAdjuster();
        LocalDate nextWorkingDay = localDate.with(temporalAdjuster);

        Assert.assertEquals(TemporalAdjusterUtil.getNextWorkingDay(), nextWorkingDay.toString());
    }
    
    @Test
    public void whenAdjust_thenNextWorkingDay() {
        LocalDate localDate = LocalDate.now();
        TemporalAdjuster temporalAdjuster = NEXT_WORKING_DAY;
        LocalDate date = localDate.with(temporalAdjuster);

        Assert.assertEquals(TemporalAdjusterUtil.getNextWorkingDay(), date.toString());
    }

    @Test
    public void whenAdjust_thenFourteenDaysFromToday() {
        LocalDate localDate = LocalDate.now();
        TemporalAdjuster temporalAdjuster = (t) -> t.plus(Period.ofDays(14));
        LocalDate result = localDate.with(temporalAdjuster);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setCalendar(calendar);
        calendar.add(Calendar.DATE, 14);
        String fourteenDaysFromToday = format.format(calendar.getTime());

        Assert.assertEquals(fourteenDaysFromToday, result.toString());
    }

    static TemporalAdjuster NEXT_WORKING_DAY = TemporalAdjusters.ofDateAdjuster(today -> {
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        int daysToAdd;
        if (dayOfWeek == DayOfWeek.FRIDAY)
            daysToAdd = 3;
        else if (dayOfWeek == DayOfWeek.SATURDAY)
            daysToAdd = 2;
        else
            daysToAdd = 1;
        return today.plusDays(daysToAdd);
    });
}
