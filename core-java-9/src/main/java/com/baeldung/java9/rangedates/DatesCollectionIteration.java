package com.baeldung.java9.rangedates;

import java.util.Collection;
import java.util.Date;

public class DatesCollectionIteration {

    public void iteratingRangeOfDatesJava7(Collection<Date> dates) {

        for (Date date : dates) {
            processDate(date);
        }
    }

    public void iteratingRangeOfDatesJava8(Collection<Date> dates) {
        dates.stream()
            .forEach(this::processDate);
    }

    private void processDate(Date date) {
        System.out.println(date);
    }

}
