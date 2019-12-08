package com.baeldung.mappingexception;

public class MyDtoNoAccessors {

    String stringValue;
    int intValue;
    boolean booleanValue;

    public MyDtoNoAccessors() {
        super();
    }

    public MyDtoNoAccessors(final String stringValue, final int intValue, final boolean booleanValue) {
        super();

        this.stringValue = stringValue;
        this.intValue = intValue;
        this.booleanValue = booleanValue;
    }

}
