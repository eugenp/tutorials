package org.baeldung.jackson.field;

public class MyDtoGetter {

    private String stringValue;
    private int intValue;

    public MyDtoGetter() {
        super();
    }

    public MyDtoGetter(final String stringValue, final int intValue) {
        super();

        this.stringValue = stringValue;
        this.intValue = intValue;
    }

    // API

    public String getStringValue() {
        return stringValue;
    }

}
