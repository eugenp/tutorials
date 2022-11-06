package com.baeldung.clone;

public class ValueObject {

    private String value;

    public ValueObject(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}