package com.baeldung.jackson.field;

public class MyDtoWithSetter {

    private int intValue;
    public boolean booleanValue;

    public MyDtoWithSetter() {
        super();
    }

    public MyDtoWithSetter(final int intValue, final boolean booleanValue) {
        super();

        this.intValue = intValue;
        this.booleanValue = booleanValue;
    }

    // API

    public void setIntValue(final int intValue) {
        this.intValue = intValue;
    }

    public int accessIntValue() {
        return intValue;
    }

}
