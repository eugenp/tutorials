package com.baeldung.abstractconstructors.noargs;

public abstract class Counter {

    int value;

    private Counter() {
        this.value = 0;
        System.out.println("Counter No-Arguments constructor");
    }

    public Counter(int value) {
        this.value = value;
        System.out.println("Parametrized Counter constructor");
    }

    abstract int increment();
}
