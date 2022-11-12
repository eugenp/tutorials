package com.baeldung.clone;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ValueObject other = (ValueObject) obj;
        return Objects.equals(value, other.value);
    }

}