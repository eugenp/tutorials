package com.baeldung.jackson.ignore.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "intValue" })
public class MyDtoIgnoreFieldByName {

    private String stringValue;
    private int intValue;
    private boolean booleanValue;

    public MyDtoIgnoreFieldByName() {
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
