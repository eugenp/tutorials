package com.baeldung.calculateweekdays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;

public class CalculateWeekdays {

    public long getWorkingDaysWithStream(LocalDate start, LocalDate end){
        return start.datesUntil(end)
          .map(LocalDate::getDayOfWeek)
          .filter(day -> !Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(day))
          .count();
    }

    public long getWorkingDaysWithoutStream(LocalDate start, LocalDate end) {
        boolean startOnWeekend = false;

        // If starting at the weekend, move to following Monday
        if(start.getDayOfWeek().getValue() > 5){
            start = start.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            startOnWeekend = true;
        }
        boolean endOnWeekend = false;
        // If ending at the weekend, move to previous Friday
        if(end.getDayOfWeek().getValue() > 5){
            end = end.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
            endOnWeekend = true;
        }
        // Cover case where starting on Saturday and ending following Sunday
        if(start.isAfter(end)){
            return 0;
        }
        // Get total weeks
        long weeks = ChronoUnit.WEEKS.between(start, end);

        long addValue = startOnWeekend || endOnWeekend ? 1 : 0;

        // Add on days that did not make up a full week
        return ( weeks * 5 ) + ( end.getDayOfWeek().getValue() - start.getDayOfWeek().getValue() ) + addValue;
    }


}
