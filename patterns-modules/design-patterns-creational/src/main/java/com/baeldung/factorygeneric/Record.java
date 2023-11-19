package com.baeldung.factorygeneric;

import java.util.Date;

public enum Record {
    STRING {
        @Override
        public Notifier<String> make() {
        	return new StringNotifier();
        }
    },
    DATE {
        @Override
        public Notifier<Date> make() {
            return new DateNotifier();
        }
    };

    public abstract <T> Notifier<T> make();
}
