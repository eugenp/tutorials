package com.baeldung.springevents.synchronous;

import org.springframework.context.ApplicationEvent;

public class GenericSpringAppEvent<T> extends ApplicationEvent {

    private final T what;

    public GenericSpringAppEvent(final Object source, final T what) {
        super(source);
        this.what = what;
    }

    public T getWhat() {
        return what;
    }

}
