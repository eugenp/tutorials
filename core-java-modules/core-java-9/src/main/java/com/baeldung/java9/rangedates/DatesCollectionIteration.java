package com.baeldung.java9.rangedates;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatesCollectionIteration {
    private static final Logger log = LoggerFactory.getLogger(DatesCollectionIteration.class);

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
        log.debug(date.toString());
    }

}
