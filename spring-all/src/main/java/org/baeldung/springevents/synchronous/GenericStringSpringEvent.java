package org.baeldung.springevents.synchronous;

public class GenericStringSpringEvent extends GenericSpringEvent<String> {

    public GenericStringSpringEvent(final String what, final boolean success) {
        super(what, success);
    }

}
