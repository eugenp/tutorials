package com.baeldung.gson.deserialization;

public class Foo {
    public int intValue;
    public String stringValue;

    public Foo(final int intValue, final String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    public Foo(final String stringValue) {
        this.stringValue = stringValue;
    }

    public Foo() {
        super();
    }

    // API

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + intValue;
        result = (prime * result) + ((stringValue == null) ? 0 : stringValue.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Foo other = (Foo) obj;
        if (intValue != other.intValue) {
            return false;
        }
        if (stringValue == null) {
            if (other.stringValue != null) {
                return false;
            }
        } else if (!stringValue.equals(other.stringValue)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TargetClass{" + "intValue= " + intValue + ", stringValue= " + stringValue + '}';
    }

}
