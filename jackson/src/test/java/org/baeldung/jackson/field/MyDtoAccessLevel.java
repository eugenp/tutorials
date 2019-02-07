package org.baeldung.jackson.field;

public class MyDtoAccessLevel {

    private String stringValue;
    int intValue;
    public boolean booleanValue;

    public MyDtoAccessLevel() {
        super();
    }

    public MyDtoAccessLevel(final String stringValue, final int intValue, final boolean booleanValue) {
        super();

        this.stringValue = stringValue;
        this.intValue = intValue;
        this.booleanValue = booleanValue;
    }

}
