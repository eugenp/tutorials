package com.baeldung.rounddate;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class RoundDate {
    public static Date getDate(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date roundToDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date roundToNearestUnit(Date date, int unit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        switch (unit) {
            case Calendar.HOUR:
                int minute = calendar.get(Calendar.MINUTE);
                if (minute >= 0 && minute < 15) {
                    calendar.set(Calendar.MINUTE, 0);
                } else if (minute >= 15 && minute < 45) {
                    calendar.set(Calendar.MINUTE, 30);
                } else {
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.add(Calendar.HOUR_OF_DAY, 1);
                }
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;

            case Calendar.DAY_OF_MONTH:
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                if (hour >= 12) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;

            case Calendar.MONTH:
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                if (day >= 15) {
                    calendar.add(Calendar.MONTH, 1);
                }
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                break;
        }

        return calendar.getTime();
    }

    public static LocalDateTime roundToStartOfMonthUsingLocalDateTime(LocalDateTime dateTime) {
        return dateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalDateTime roundToEndOfWeekUsingLocalDateTime(LocalDateTime dateTime) {
        return dateTime.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999);
    }

    public static ZonedDateTime roundToStartOfMonthUsingZonedDateTime(ZonedDateTime dateTime) {
        return dateTime.withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .with(ChronoField.MILLI_OF_SECOND, 0)
                .with(ChronoField.MICRO_OF_SECOND, 0)
                .with(ChronoField.NANO_OF_SECOND, 0);
    }

    public static ZonedDateTime roundToEndOfWeekUsingZonedDateTime(ZonedDateTime dateTime) {
        return dateTime.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .with(ChronoField.MILLI_OF_SECOND, 999)
                .with(ChronoField.MICRO_OF_SECOND, 999)
                .with(ChronoField.NANO_OF_SECOND, 999);
    }

}