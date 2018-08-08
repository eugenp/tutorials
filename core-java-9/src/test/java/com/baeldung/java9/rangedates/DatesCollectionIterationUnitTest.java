package com.baeldung.java9.rangedates;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.junit.Test;

public class DatesCollectionIterationUnitTest {

    private Collection<LocalDate> localDates = LocalDate.now()
        .datesUntil(LocalDate.now()
            .plus(10L, ChronoUnit.DAYS))
        .collect(Collectors.toList());

    private Collection<Date> dates = localDates.stream()
        .map(localDate -> Date.from(localDate.atStartOfDay(ZoneId.systemDefault())
            .toInstant()))
        .collect(Collectors.toList());

    @Test
    public void givenIteratingListOfDatesJava7_WhenStartTodayAndEnding10DaysAhead() {
        DatesCollectionIteration iterateInColleciton = new DatesCollectionIteration();

        iterateInColleciton.iteratingRangeOfDatesJava7(dates, new DateAssertion());
    }

    @Test
    public void givenIteratingListOfDatesJava8_WhenStartTodayAndEnd10DaysAhead() {
        DatesCollectionIteration iterateInColleciton = new DatesCollectionIteration();

        iterateInColleciton.iteratingRangeOfDatesJava8(dates, new DateAssertion()::execute);
    }

    private class DateAssertion implements Execution<Date> {

        Iterator<Date> iterator = dates.iterator();

        @Override
        public void execute(Date date) {
            assertThat(date)
                .isEqualTo(iterator.next());
        }

    }
}
