package com.baeldung.java9.rangedates;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RangeDatesIteration {
    private static final Logger log = LoggerFactory.getLogger(RangeDatesIteration.class);

    public void iterateBetweenDatesJava9(LocalDate startDate, LocalDate endDate) {

        startDate.datesUntil(endDate)
            .forEach(this::processDate);
    }

    public void iterateBetweenDatesJava8(LocalDate start, LocalDate end) {
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            processDate(date);
        }
    }

    public void iterateBetweenDatesJava7(Date start, Date end) {
        Date current = start;

        while (current.before(end)) {
            processDate(current);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);

            calendar.add(Calendar.DATE, 1);

            current = calendar.getTime();
        }
    }

    private void processDate(LocalDate date) {
        log.debug(date.toString());
    }

    private void processDate(Date date) {
        log.debug(date.toString());
    }
}
