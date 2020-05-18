package com.baeldung.architecture.hexagonal.callers;

public class Caller2 {
    private final Caller2Adapter caller2Adapter;

    public Caller2(Caller2Adapter caller2Adapter) {
        this.caller2Adapter = caller2Adapter;
    }

    public void call(String[] args) {
        caller2Adapter.adaptAndCallComponent(String.format("<arg>%s</arg>", args[0]));
    }
}
