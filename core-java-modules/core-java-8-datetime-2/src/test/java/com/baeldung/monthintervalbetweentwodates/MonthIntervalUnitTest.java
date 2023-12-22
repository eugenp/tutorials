package com.baeldung.monthintervalbetweentwodates;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonthIntervalUnitTest {

    @Test
    void givenTwoDates_whenCalculatingMonthsBetweenUsingJodaTime_thenReturnMonthsDifference() {
        DateTime firstDate = new DateTime(2023, 5, 25, 0, 0);
        DateTime secondDate = new DateTime(2023, 11, 23, 0, 0);

        int monthsBetween = Months.monthsBetween(firstDate, secondDate)
          .getMonths();
        assertEquals(5, monthsBetween);
    }

    @Test
    void givenTwoDates_whenCalculatingMonthsBetweenUsingJodaTimeSetTimeToFirstDayOfMonth_thenReturnMonthsDifference() {
        DateTime firstDate = new DateTime(2023, 5, 25, 0, 0).withDayOfMonth(1);
        DateTime secondDate = new DateTime(2023, 11, 23, 0, 0).withDayOfMonth(1);

        int monthsBetween = Months.monthsBetween(firstDate, secondDate)
          .getMonths();
        assertEquals(6, monthsBetween);
    }

    @Test
    void givenTwoDates_whenCalculatingMonthsBetweenUsingPeriodClass_thenReturnMonthsDifference() {
        Period diff = Period.between(LocalDate.parse("2023-05-25"), LocalDate.parse("2023-11-23"));
        assertEquals(5, diff.getMonths());
    }

    @Test
    void givenTwoDates_whenCalculatingMonthsBetweenUsingPeriodClassAndAdjsutingDatesToFirstDayOfTheMonth_thenReturnMonthsDifference() {
        Period diff = Period.between(LocalDate.parse("2023-05-25")
          .withDayOfMonth(1), LocalDate.parse("2023-11-23")
          .withDayOfMonth(1));
        assertEquals(6, diff.getMonths());
    }

    @Test
    void givenTwoDates_whenCalculatingMonthsBetweenUsingChronoUnitAndYearMonth_thenReturnMonthsDifference() {
        long diff = ChronoUnit.MONTHS.between(YearMonth.from(LocalDate.parse("2023-05-25")), LocalDate.parse("2023-11-23"));
        assertEquals(6, diff);
    }

    @Test
    void givenTwoDates_whenCalculatingMonthsBetweenUsingChronoUnitEnum_thenReturnMonthsDifference() {
        long monthsBetween = ChronoUnit.MONTHS.between(LocalDate.parse("2023-05-25"), LocalDate.parse("2023-11-23"));
        assertEquals(5, monthsBetween);
    }

    @Test
    void givenTwoDates_whenCalculatingMonthsBetweenUsingChronoUnitEnumdSetTimeToFirstDayOfMonth_thenReturnMonthsDifference() {
        long monthsBetween = ChronoUnit.MONTHS.between(LocalDate.parse("2023-05-25")
          .withDayOfMonth(1), LocalDate.parse("2023-11-23")
          .withDayOfMonth(1));
        assertEquals(6, monthsBetween);
    }

    @Test
    void givenTwoDates_whenCalculatingMonthsBetweenUsingLegacyDateApi_thenReturnMonthsDifference() throws ParseException {
        MonthInterval monthDifference = new MonthInterval();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2016-05-31");
        Date endDate = sdf.parse("2016-11-30");
        int monthsBetween = monthDifference.monthsBetween(startDate, endDate);
        assertEquals(6, monthsBetween);

    }

    @Test
    void givenTwoDates_whenCalculatingMonthsBetweenUsingLegacyDateApiDayValueConsidered_thenReturnMonthsDifference() throws ParseException {
        MonthInterval monthDifference = new MonthInterval();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2016-05-31");
        Date endDate = sdf.parse("2016-11-28");
        int monthsBetween = monthDifference.monthsBetweenWithDayValue(startDate, endDate);
        assertEquals(5, monthsBetween);
    }
}
