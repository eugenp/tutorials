package com.baeldung.architecture.hexagonal.callers;

import java.util.Arrays;

public class Caller1 {
    private final Caller1Adapter caller1Adapter;

    public Caller1(Caller1Adapter caller1Adapter) {
        this.caller1Adapter = caller1Adapter;
    }

    public void call() {
        caller1Adapter.adaptAndCallComponent(Arrays.asList("h", "e", "l", "l", "o"));
    }
}
