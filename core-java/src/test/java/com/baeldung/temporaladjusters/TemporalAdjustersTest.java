package com.baeldung.temporaladjusters;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.temporaladjuster.TemporalAdjusterUtil;

public class TemporalAdjustersTest {

    @Test
    public void whenAdjust_thenNextSunday() {
        LocalDate localDate = LocalDate.now();
        LocalDate nextSunday = localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        Calendar calendar = TemporalAdjusterUtil.nextDayOfWeek(Calendar.SUNDAY);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setCalendar(calendar);
        String formattedDate = format.format(calendar.getTime());

        Assert.assertEquals(formattedDate, nextSunday.toString());
    }

}
