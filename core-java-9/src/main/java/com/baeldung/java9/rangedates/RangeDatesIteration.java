package com.baeldung.java9.rangedates;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class RangeDatesIteration {

    public void iterateBetweenDatesJava9(LocalDate startDate, LocalDate endDate, Consumer<LocalDate> consumer) {

        startDate.datesUntil(endDate)
            .forEach(consumer);
    }

    public void iterateBetweenDatesJava8(LocalDate start, LocalDate end, Consumer<LocalDate> consumer) {
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            consumer.accept(date);
        }
    }

    public void iterateBetweenDatesJava7(Date start, Date end, Execution<Date> execution) {
        Date current = start;

        while (current.before(end)) {
            execution.execute(current);

            current = nextDate(current);
        }
    }

    public List<LocalDate> createRangeDates(LocalDate start, LocalDate end) {

        List<LocalDate> dates = new ArrayList<>();
        LocalDate current = start;

        while (current.isBefore(end)) {
            dates.add(current);

            current = current.plus(1l, ChronoUnit.DAYS);
        }

        return dates;
    }

    private Date nextDate(Date current) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);

        calendar.add(Calendar.DATE, 1);

        return calendar.getTime();
    }

}
