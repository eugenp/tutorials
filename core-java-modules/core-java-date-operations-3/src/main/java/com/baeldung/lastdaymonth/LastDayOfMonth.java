package com.baeldung.lastdaymonth;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

public class LastDayOfMonth {

    static int getLastDayOfMonthUsingCalendar(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    static int getLastDayOfMonthUsingTemporalAdjusters(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth())
          .getDayOfMonth();
    }

    static int getLastDayOfMonthUsingYearMonth(YearMonth date) {
        return date.atEndOfMonth()
          .getDayOfMonth();
    }

    static int getLastDayOfMonthUsingJodaTime(org.joda.time.LocalDate date) {
        return date.dayOfMonth()
          .withMaximumValue()
          .getDayOfMonth();
    }

}
