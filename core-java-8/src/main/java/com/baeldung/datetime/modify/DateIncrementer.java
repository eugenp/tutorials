package com.baeldung.datetime.modify;

import org.joda.time.DateTime;

import java.time.LocalDate;
import java.util.logging.Logger;

public class DateIncrementer {
    private static final Logger log = Logger.getLogger(DateIncrementer.class.getName());
    private static final int INCREMENT_BY_IN_DAYS = 1;

    /**
     * Using Java 8
     */
    public static String addOneDay(String date) {
        return LocalDate.parse(date)
            .plusDays(INCREMENT_BY_IN_DAYS)
            .toString();
    }

    /**
     * Using Joda-Time
     */
    public static String addOneDayJodaTime(String date) {
        DateTime dateTime = new DateTime(date);
        // increment dateTime by one day and return in yyyy-MM-dd format
        return dateTime.plusDays(INCREMENT_BY_IN_DAYS)
            .toString("yyyy-MM-dd");
    }

    public static void main(String[] args) {
        // String representation of current date
        String date = LocalDate.now()
            .toString();
        log.info("Current date = " + date);

        String incrementedDateJava8 = DateIncrementer.addOneDay(date);
        log.info("Date incremented by one day using (Java 8): " + incrementedDateJava8);

        String incrementedDateJodaTime = DateIncrementer.addOneDayJodaTime(date);
        log.info("Date incremented by one day using (Joda-Time): " + incrementedDateJodaTime);
    }
}
