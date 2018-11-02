package org.baeldung.springevents.synchronous;

import org.springframework.context.ApplicationEvent;

public class GenericSpringAppEvent<T> extends ApplicationEvent {

    private final T what;
    protected final boolean success;

    public GenericSpringAppEvent(final Object source, final T what, final boolean success) {
        super(source);
        this.what = what;
        this.success = success;
    }

    public T getWhat() {
        return what;
    }

    public boolean isSuccess() {
        return success;
    }

}
