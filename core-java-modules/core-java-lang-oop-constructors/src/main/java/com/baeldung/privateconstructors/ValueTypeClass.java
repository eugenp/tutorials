package com.baeldung.privateconstructors;

public class ValueTypeClass {

    private final String value;
    private final String type;

    public ValueTypeClass(int x) {
        this(Integer.toString(x), "int");
    }

    public ValueTypeClass(boolean x) {
        this(Boolean.toString(x), "boolean");
    }

    private ValueTypeClass(String value, String type) {
        this.value = value;
        this.type = type;
    }

    // getters and setters
}
