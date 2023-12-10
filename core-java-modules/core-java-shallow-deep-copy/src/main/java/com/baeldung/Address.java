package com.baeldung;

import java.io.Serializable;

public class Address implements Serializable {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
