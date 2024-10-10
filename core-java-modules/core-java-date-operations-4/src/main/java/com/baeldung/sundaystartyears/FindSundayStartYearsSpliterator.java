package com.baeldung.sundaystartyears;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FindSundayStartYearsSpliterator {

    public static List<Integer> getYearsStartingOnSunday(int startYear, int endYear) {
        List<Integer> years = new ArrayList<>();

        Spliterator<LocalDate> spliterator = Spliterators.spliteratorUnknownSize(
            Stream.iterate(LocalDate.of(startYear, 1, 1), date -> date.plus(1, ChronoUnit.YEARS))
                  .limit(endYear - startYear + 1)
                  .iterator(), Spliterator.ORDERED);

        Stream<LocalDate> stream = StreamSupport.stream(spliterator, false);

        stream.filter(date -> date.getDayOfWeek() == DayOfWeek.SUNDAY)
              .forEach(date -> years.add(date.getYear()));
        return years;
    }
}