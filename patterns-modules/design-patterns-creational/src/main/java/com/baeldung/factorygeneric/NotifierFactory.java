package com.baeldung.factorygeneric;

import java.util.Date;

public class NotifierFactory {

    public <T> Notifier<T> getNotifier(Class<T> c) {
        if (c == String.class) {
            return Record.STRING.make();
        }
        if (c == Date.class) {
            return Record.DATE.make();
        }
        return null;
    }

}
