package com.baeldung.java9.rangedates;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
        Calendar today = Calendar.getInstance();
        Calendar next10Ahead = (Calendar) today.clone();
        next10Ahead.add(Calendar.DATE, 10);

        iterateInColleciton.iteratingRangeOfDatesJava7(createRangeDates(today.getTime(), next10Ahead.getTime()));
    }

    @Test
    public void givenIteratingListOfDatesJava8_WhenStartTodayAndEnd10DaysAhead() {
        DatesCollectionIteration iterateInColleciton = new DatesCollectionIteration();

        iterateInColleciton.iteratingRangeOfDatesJava8(dates);
    }

    private List<Date> createRangeDates(Date start, Date end) {

        List<Date> dates = new ArrayList<>();
        Date current = start;

        while (current.before(end)) {
            dates.add(current);
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE, 1);

            current = calendar.getTime();
        }

        return dates;
    }
}
