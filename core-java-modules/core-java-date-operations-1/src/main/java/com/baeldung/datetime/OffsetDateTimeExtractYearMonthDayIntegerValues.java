package com.baeldung.datetime;

import java.time.OffsetDateTime;

public class OffsetDateTimeExtractYearMonthDayIntegerValues {

    int getYear(OffsetDateTime offsetDateTime) {
        return offsetDateTime.getYear();
    }

    int getMonth(OffsetDateTime offsetDateTime) {
        return offsetDateTime.getMonthValue();
    }

    int getDay(OffsetDateTime offsetDateTime) {
        return offsetDateTime.getDayOfMonth();
    }
}
