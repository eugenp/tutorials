package com.baeldung.java9.rangedates;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class DatesCollectionIteration {

    public void iteratingRangeOfDatesJava7(Collection<Date> dates) {
        Iterator<Date> iterator = dates.iterator();

        while (iterator.hasNext()) {
            Date date = iterator.next();

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
