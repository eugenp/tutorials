package com.baeldung.datetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class UseToInstant {

    public LocalDateTime convertDateToLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public LocalDateTime convertDateToLocalDate(Calendar calendar) {
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }
}
