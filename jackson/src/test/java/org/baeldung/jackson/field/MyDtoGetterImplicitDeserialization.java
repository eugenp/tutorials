package org.baeldung.jackson.field;

public class MyDtoGetterImplicitDeserialization {

    private String stringValue;
    public boolean booleanValue;

    public MyDtoGetterImplicitDeserialization() {
        super();
    }

    public MyDtoGetterImplicitDeserialization(final String stringValue, final int intValue, final boolean booleanValue) {
        super();

        this.stringValue = stringValue;
        this.booleanValue = booleanValue;
    }

    // API

    public String getStringValue() {
        return stringValue;
    }

}
