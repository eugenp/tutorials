package com.baeldung.factorygeneric;

import java.util.Date;

public class DateNotifier implements Notifier<Date> {

    @Override
    public void notify(Date date) {
        System.out.println("Notifying: " + date);
    }
}
