package com.baeldung.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class AddSubtractDaysSkippingWeekendsUtils {

    public static LocalDate addSubtractDaysSkippingWeekendsIterativeMethod(LocalDate date, int days) {
        LocalDate result = date;
        int addedDays = 0;
        int absDays = Math.abs(days);
        int day = (days > 0) ? 1 : -1;
        while (addedDays < absDays) {
            result = result.plusDays(day);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }
        return result;
    }
}
