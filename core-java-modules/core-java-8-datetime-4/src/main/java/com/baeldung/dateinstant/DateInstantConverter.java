package com.baeldung.dateinstant;

import java.time.Instant;
import java.util.Date;

public final class DateInstantConverter {

    private DateInstantConverter() {
    }

    public static Instant toInstant(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant();
    }

    public static Date toDate(Instant instant) {
        if (instant == null) {
            return null;
        }
        return Date.from(instant);
    }
}
