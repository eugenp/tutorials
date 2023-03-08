package com.baeldung.deep_shallow_copy_2;

import java.util.Objects;

public class Example {

    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        return ((Example) o).getValue()
          .equals(this.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}