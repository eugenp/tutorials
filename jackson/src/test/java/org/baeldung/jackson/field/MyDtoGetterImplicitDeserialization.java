package org.baeldung.jackson.field;

public class MyDtoGetterImplicitDeserialization {

    private String stringValue;
    int intValue;
    public boolean booleanValue;

    public MyDtoGetterImplicitDeserialization() {
        super();
    }

    public MyDtoGetterImplicitDeserialization(final String stringValue, final int intValue, final boolean booleanValue) {
        super();

        this.stringValue = stringValue;
        this.intValue = intValue;
        this.booleanValue = booleanValue;
    }

    // API

    public String getStringValue() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

}
