package org.baeldung.jackson.field;

public class MyDtoGetter {

    private String stringValue;
    int intValue;
    public boolean booleanValue;

    public MyDtoGetter() {
        super();
    }

    public MyDtoGetter(final String stringValue, final int intValue, final boolean booleanValue) {
        super();

        this.stringValue = stringValue;
        this.intValue = intValue;
        this.booleanValue = booleanValue;
    }

    // API

    public String getStringValue() {
        return stringValue;
    }

}
