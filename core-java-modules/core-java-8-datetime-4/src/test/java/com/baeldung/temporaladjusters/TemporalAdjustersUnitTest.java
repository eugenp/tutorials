package com.baeldung.temporaladjusters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.junit.Assert;
import org.junit.Test;

public class TemporalAdjustersUnitTest {

    @Test
    public void whenAdjust_thenNextSunday() {
        LocalDate localDate = LocalDate.of(2017, 07, 8);
        LocalDate nextSunday = localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        String expected = "2017-07-09";

        Assert.assertEquals(expected, nextSunday.toString());
    }

}
