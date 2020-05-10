package com.baeldung.jackson.enums.withEnum;

import com.baeldung.jackson.enums.serialization.Distance;

public class MyDtoWithEnumCustom {

    private String stringValue;
    private int intValue;
    private boolean booleanValue;
    private Distance type;

    public MyDtoWithEnumCustom() {
        super();
    }

    public MyDtoWithEnumCustom(final String stringValue, final int intValue, final boolean booleanValue, final Distance type) {
        super();

        this.stringValue = stringValue;
        this.intValue = intValue;
        this.booleanValue = booleanValue;
        this.type = type;
    }

    // API

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(final String stringValue) {
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(final int intValue) {
        this.intValue = intValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(final boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public Distance getType() {
        return type;
    }

    public void setType(final Distance type) {
        this.type = type;
    }

}
