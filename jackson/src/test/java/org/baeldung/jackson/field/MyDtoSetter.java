package org.baeldung.jackson.field;

public class MyDtoSetter {

    private int intValue;
    public boolean booleanValue;

    public MyDtoSetter() {
        super();
    }

    public MyDtoSetter(final int intValue, final boolean booleanValue) {
        super();

        this.intValue = intValue;
        this.booleanValue = booleanValue;
    }

    // API

    public void setIntValue(final int intValue) {
        this.intValue = intValue;
    }

    public int alternativeGetIntValue() {
        return intValue;
    }

}
