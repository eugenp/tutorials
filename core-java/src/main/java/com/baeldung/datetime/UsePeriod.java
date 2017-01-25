package com.baeldung.datetime;

import java.time.LocalDate;
import java.time.Period;

public class UsePeriod {

    public LocalDate modifyDates(LocalDate localDate, Period period) {
        return localDate.plus(period);
    }

    public Period getDifferenceBetweenDates(LocalDate localDate1, LocalDate localDate2) {
        return Period.between(localDate1, localDate2);
    }
}
