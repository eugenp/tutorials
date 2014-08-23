package org.baeldung.gson.deserialization;

public class SourceClass {
    int intValue;
    String stringValue;

    public SourceClass(int intValue, String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "SourceClass{" +
                "intValue=" + intValue +
                ", stringValue='" + stringValue + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SourceClass)) return false;

        SourceClass that = (SourceClass) o;

        if (intValue != that.intValue) return false;
        if (!stringValue.equals(that.stringValue)) return false;

        return true;
    }
}
