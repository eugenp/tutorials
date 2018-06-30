package com.baeldung.system;

import java.util.Date;

public class DateTimeService {

    // One hour from now
    public long nowPlusOneHour() {
        return System.currentTimeMillis() + 3600 * 1000L;
    }

    // Human-readable format
    public String nowPrettyPrinted() {
        return new Date(System.currentTimeMillis()).toString();
    }
}
