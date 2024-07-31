package com.baeldung.firstdaymonth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

public class FirstDayOfMonthUnitTest {

    @Test
    void givenMonth_whenUsingCalendar_thenReturnFirstDay() {
        Date currentDate = new GregorianCalendar(2023, Calendar.NOVEMBER, 23).getTime();
        Date expectedDate = new GregorianCalendar(2023, Calendar.NOVEMBER, 1).getTime();

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        assertEquals(expectedDate, cal.getTime());
    }

    @Test
    void givenMonth_whenUsingLocalDate_thenReturnFirstDay() {
        LocalDate currentDate = LocalDate.of(2023, 9, 6);
        LocalDate expectedDate = LocalDate.of(2023, 9, 1);

        assertEquals(expectedDate, currentDate.withDayOfMonth(1));
    }

    @Test
    void givenMonth_whenUsingTemporalAdjusters_thenReturnFirstDay() {
        LocalDate currentDate = LocalDate.of(2023, 7, 19);
        LocalDate expectedDate = LocalDate.of(2023, 7, 1);

        assertEquals(expectedDate, currentDate.with(TemporalAdjusters.firstDayOfMonth()));
    }

    @Test
    void givenMonth_whenUsingYearMonth_thenReturnFirstDay() {
        YearMonth currentDate = YearMonth.of(2023, 4);
        LocalDate expectedDate = LocalDate.of(2023, 4, 1);

        assertEquals(expectedDate, currentDate.atDay(1));
    }

    @Test
    void givenMonth_whenUsingJodaTime_thenReturnFirstDay() {
        org.joda.time.LocalDate currentDate = org.joda.time.LocalDate.parse("2023-5-10");
        org.joda.time.LocalDate expectedDate = org.joda.time.LocalDate.parse("2023-5-1");

        assertEquals(expectedDate, currentDate.dayOfMonth()
          .withMinimumValue());
    }

}
