package com.baeldung.jackson.ignore;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MyDtoIgnoreField {

    private String stringValue;
    @JsonIgnore
    private int intValue;
    private boolean booleanValue;

    public MyDtoIgnoreField() {
        super();
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

}
