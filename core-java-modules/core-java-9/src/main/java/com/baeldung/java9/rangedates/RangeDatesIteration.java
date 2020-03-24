package com.baeldung.java9.rangedates;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class RangeDatesIteration {

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
        System.out.println(date);
    }

    private void processDate(Date date) {
        System.out.println(date);
    }
}
