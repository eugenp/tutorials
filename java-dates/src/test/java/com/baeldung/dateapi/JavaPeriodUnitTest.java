package com.baeldung.dateapi;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JavaPeriodUnitTest {

    private static final Logger LOG = Logger.getLogger(JavaPeriodUnitTest.class);

    @Test
    public void whenTestPeriod_thenOk() {

        LocalDate startDate = LocalDate.of(2015, 2, 15);
        LocalDate endDate = LocalDate.of(2017, 1, 21);

        Period period = Period.between(startDate, endDate);

        LOG.info(String.format("Years:%d months:%d days:%d", period.getYears(), period.getMonths(), period.getDays()));

        assertFalse(period.isNegative());
        assertEquals(56, period.plusDays(50).getDays());
        assertEquals(9, period.minusMonths(2).getMonths());

        Period fromUnits = Period.of(3, 10, 10);
        Period fromDays = Period.ofDays(50);
        Period fromMonths = Period.ofMonths(5);
        Period fromYears = Period.ofYears(10);
        Period fromWeeks = Period.ofWeeks(40);

        assertEquals(280, fromWeeks.getDays());

        Period fromCharYears = Period.parse("P2Y");
        assertEquals(2, fromCharYears.getYears());
        Period fromCharUnits = Period.parse("P2Y3M5D");
        assertEquals(5, fromCharUnits.getDays());
    }

}
