package com.baeldung.java9.rangedates;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.function.Consumer;

public class DatesCollectionIteration {

    public void iteratingRangeOfDatesJava7(Collection<Date> dates, Execution<Date> execution) {
        Iterator<Date> iterator = dates.iterator();

        while (iterator.hasNext()) {
            Date date = iterator.next();

            execution.execute(date);
        }
    }

    public void iteratingRangeOfDatesJava8(Collection<Date> dates, Consumer<Date> process) {
        dates.stream()
            .forEach(process);
    }

}
