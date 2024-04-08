package com.baeldung.daterangeoverlap;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.time.LocalDate;
import java.util.Calendar;

public class DateRangeOverlapChecker {

    public static boolean isOverlapUsingCalendarAndDuration(Calendar start1, Calendar end1, Calendar start2, Calendar end2) {
        long overlap = Math.min(end1.getTimeInMillis(), end2.getTimeInMillis()) - Math.max(start1.getTimeInMillis(), start2.getTimeInMillis());
        return overlap >= 0;
    }

    public static boolean isOverlapUsingLocalDateAndDuration(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        long overlap = Math.min(end1.toEpochDay(), end2.toEpochDay()) - Math.max(start1.toEpochDay(), start2.toEpochDay());
        return overlap >= 0;
    }

    public static boolean isOverlapUsingJodaTime(DateTime start1, DateTime end1, DateTime start2, DateTime end2) {
        Interval interval1 = new Interval(start1, end1);
        Interval interval2 = new Interval(start2, end2);
        return interval1.overlaps(interval2);
    }

    public static boolean isOverlapUsingCalendarAndCondition(Calendar start1, Calendar end1, Calendar start2, Calendar end2) {
        return !(end1.before(start2) || start1.after(end2));
    }

    public static boolean isOverlapUsingLocalDateAndCondition(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !(end1.isBefore(start2) || start1.isAfter(end2));
    }

    public static boolean isOverlapUsingCalendarAndFindMin(Calendar start1, Calendar end1, Calendar start2, Calendar end2) {
        long overlap1 = Math.min(end1.getTimeInMillis() - start1.getTimeInMillis(), end1.getTimeInMillis() - start2.getTimeInMillis());
        long overlap2 = Math.min(end2.getTimeInMillis() - start2.getTimeInMillis(), end2.getTimeInMillis() - start1.getTimeInMillis());
        return Math.min(overlap1, overlap2) / (24 * 60 * 60 * 1000) >= 0;
    }

    public static boolean isOverlapUsingLocalDateAndFindMin(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        long overlap1 = Math.min(end1.toEpochDay() - start1.toEpochDay(), end1.toEpochDay() - start2.toEpochDay());
        long overlap2 = Math.min(end2.toEpochDay() - start2.toEpochDay(), end2.toEpochDay() - start1.toEpochDay());
        return Math.min(overlap1, overlap2) >= 0;
    }
}
