package com.baeldung.architecture.hexagonal.callers;

import com.baeldung.architecture.hexagonal.component.Component;

public class Caller2Adapter implements CallerAdapter<String> {
    private static final String OPENING_TAG = "<arg>";
    private static final String CLOSING_TAG = "</arg>";

    private final Component component;

    public Caller2Adapter(Component component) {
        this.component = component;
    }

    @Override
    public void adaptAndCallComponent(String s) {
        component.accept(s.substring(s.indexOf(OPENING_TAG) + OPENING_TAG.length(), s.indexOf(CLOSING_TAG)));
    }
}
