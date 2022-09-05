package com.baeldung.staticgc;

public class StaticField {

    private final String value;

    public StaticField(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    protected void finalize() {
        System.out.println("The object is garbage now");
    }
}
