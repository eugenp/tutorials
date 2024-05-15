package com.baeldung.testhashcode;

public class MyClass {
    private String value;

    public MyClass(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

}
