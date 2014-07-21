package org.baeldung.jackson.field;

public class MyDtoSetter {

    private String stringValue;
    int intValue;

    public MyDtoSetter() {
        super();
    }

    public MyDtoSetter(final String stringValue, final int intValue, final boolean booleanValue) {
        super();

        this.stringValue = stringValue;
        this.intValue = intValue;
    }

    // API

    public String getStringValue() {
        return stringValue;
    }

    public void setIntValue(final int intValue) {
        this.intValue = intValue;
    }

    public int anotherGetIntValue() {
        return intValue;
    }

}
