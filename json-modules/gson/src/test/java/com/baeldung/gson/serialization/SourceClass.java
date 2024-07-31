package com.baeldung.gson.serialization;

public class SourceClass {
    private int intValue;
    private String stringValue;

    public SourceClass(final int intValue, final String stringValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
    }

    // API

    public int getIntValue() {
        return intValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    //

    @Override
    public String toString() {
        return "SourceClass{" + "intValue=" + intValue + ", stringValue='" + stringValue + '\'' + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SourceClass))
            return false;

        final SourceClass that = (SourceClass) o;

        if (intValue != that.intValue)
            return false;
        if (!stringValue.equals(that.stringValue))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = intValue;
        result = 31 * result + stringValue.hashCode();
        return result;
    }
}
