package com.baeldung;

import org.joda.time.DateTime;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateDiff {
    public long beforeJava8diff(Date firstDate, Date secondDate){
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public long fromJava8Diff(ZonedDateTime firstDate, ZonedDateTime secondDate){
        Duration duration = Duration.between(firstDate, secondDate);
        return Math.abs(duration.toDays());
    }

    public long fromJodaTime(DateTime firstDate, DateTime secondDate){
        org.joda.time.Duration duration = new org.joda.time.Duration(firstDate, secondDate);
        return Math.abs(duration.getStandardDays());
    }

    public long fromDate4j(hirondelle.date4j.DateTime firstDate, hirondelle.date4j.DateTime secondDate){
        long diff = firstDate.numDaysFrom(secondDate);
        return Math.abs(diff);
    }
}