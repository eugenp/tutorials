package com.baeldung.architecture.hexagonal.callers;

import java.util.List;

import com.baeldung.architecture.hexagonal.component.Component;

public class Caller1Adapter implements CallerAdapter<List<String>> {
    private final Component component;

    public Caller1Adapter(Component component) {
        this.component = component;
    }

    @Override
    public void adaptAndCallComponent(List<String> strings) {
        component.accept(String.join("", strings));
    }
}
