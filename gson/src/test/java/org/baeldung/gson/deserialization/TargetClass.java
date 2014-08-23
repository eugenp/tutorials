package org.baeldung.gson.deserialization;

public class TargetClass {
    public int intValue;
    public String stringValue;

    public TargetClass(final int intValue, final String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    // API

    @Override
    public String toString() {
        return "TargetClass{" + "intValue= " + intValue + ", stringValue= " + stringValue + '}';
    }

}
