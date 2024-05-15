package com.baeldung.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayOfTheWeek {

    public LocalDate getDateOfFirstDayOfTheWeek_UsingLocale(LocalDate localDate, Locale locale){
        TemporalField fieldISO = WeekFields.of(locale).dayOfWeek();
        return localDate.with(fieldISO, 1);
    }

    public LocalDate getDateOfFirstDayOfTheWeek_UsingISODayOfWeek(LocalDate localDate){
        TemporalField dayOfWeek = WeekFields.ISO.dayOfWeek();
        return localDate.with(dayOfWeek, dayOfWeek.range().getMinimum());
    }

    public LocalDate getDateOfFirstDayOfTheWeek_UsingDayOfWeek(LocalDate localDate){
        DayOfWeek weekStart = DayOfWeek.MONDAY;
        return localDate.with(TemporalAdjusters.previousOrSame(weekStart));
    }

    public LocalDate getDateOfFirstDayOfTheWeek_UsingCalendar(LocalDate localDate){
        Calendar calendar = Calendar.getInstance();
        ZoneId zoneId = ZoneId.systemDefault();
        Date date = Date.from(localDate.atStartOfDay(zoneId).toInstant());
        calendar.setTime(date);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
       return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
    }
}
