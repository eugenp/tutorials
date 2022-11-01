package com.baeldung.factorygeneric;

import java.util.Date;

class Main {
    public static void main(String[] args) {
        NotifierFactory factory = new NotifierFactory();
        Notifier<String> stringNotifier = factory.getNotifier(String.class);
        Notifier<Date> dateNotifier = factory.getNotifier(Date.class);

        stringNotifier.notify("Hello world!");
        dateNotifier.notify(new Date());
    }
}
