package com.baeldung.java9.rangedates;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class RangeDatesIterationUnitTest {

    private Collection<LocalDate> localDates = LocalDate.now()
        .datesUntil(LocalDate.now()
            .plus(10L, ChronoUnit.DAYS))
        .collect(Collectors.toList());

    private Collection<Date> dates = localDates.stream()
        .map(localDate -> Date.from(localDate.atStartOfDay(ZoneId.systemDefault())
            .toInstant()))
        .collect(Collectors.toList());

    @Test
    public void givenIterateBetweenDatesJava9_WhenStartDateAsTodayAndEndDateAs10DaysAhead() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plus(10L, ChronoUnit.DAYS);

        RangeDatesIteration iteration = new RangeDatesIteration();

        iteration.iterateBetweenDatesJava9(start, end, this::assertionJava8And9);
    }

    @Test
    public void givenIterateBetweenDatesJava8_WhenStartDateAsTodayAndEndDateAs10DaysAhead() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plus(10L, ChronoUnit.DAYS);

        RangeDatesIteration iteration = new RangeDatesIteration();

        iteration.iterateBetweenDatesJava8(start, end, this::assertionJava8And9);
    }

    @Test
    public void givenIterateBetweenDatesJava8_() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plus(10L, ChronoUnit.DAYS);
        
        RangeDatesIteration iteration = new RangeDatesIteration();
        
        iteration.createRangeDates(start, end).stream()
            .forEach(System.out::println);
    }

    @Test
    public void givenIterateBetweenDatesJava7_WhenStartDateAsTodayAndEndDateAs10DaysAhead() {
        Calendar today = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));
        Date start = calendar.getTime();

        calendar.add(Calendar.DATE, 10);
        Date end = calendar.getTime();

        RangeDatesIteration iteration = new RangeDatesIteration();

        iteration.iterateBetweenDatesJava7(start, end, new Java7Assertion());
    }

    private void assertionJava8And9(LocalDate date) {
        Assert.assertThat(date, Matchers.isIn(localDates));
        System.out.println(date);
    }

    private class Java7Assertion implements Execution<Date> {

        Iterator<Date> iterator = dates.iterator();

        @Override
        public void execute(Date date) {
            assertThat(date)
                .isEqualTo(iterator.next());
        }

    }
}
