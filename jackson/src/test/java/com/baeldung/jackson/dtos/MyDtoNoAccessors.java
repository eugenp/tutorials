package com.baeldung.jackson.dtos;

public class MyDtoNoAccessors {

    private String stringValue;
    private int intValue;
    private boolean booleanValue;

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
