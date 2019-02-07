package org.baeldung.jackson.field;

public class MyDtoGetterImplicitDeserialization {

    private String stringValue;

    public MyDtoGetterImplicitDeserialization() {
        super();
    }

    public MyDtoGetterImplicitDeserialization(final String stringValue) {
        super();

        this.stringValue = stringValue;
    }

    // API

    public String getStringValue() {
        return stringValue;
    }

}
