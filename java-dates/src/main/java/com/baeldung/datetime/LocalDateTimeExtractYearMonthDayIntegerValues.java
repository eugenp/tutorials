package com.baeldung.datetime;

import java.time.LocalDateTime;

public class LocalDateTimeExtractYearMonthDayIntegerValues {

    int getYear(LocalDateTime localDateTime) {
        return localDateTime.getYear();
    }

    int getMonth(LocalDateTime localDateTime) {
        return localDateTime.getMonthValue();
    }

    int getDay(LocalDateTime localDateTime) {
        return localDateTime.getDayOfMonth();
    }
}
