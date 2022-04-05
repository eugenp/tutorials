package com.baeldung.jackson.jsonproperty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyDtoFieldNameChanged {

    private String stringValue;
    private int intValue;
    private boolean booleanValue;

    public MyDtoFieldNameChanged() {
        super();
    }

    public MyDtoFieldNameChanged(final String stringValue, final int intValue, final boolean booleanValue) {
        super();

        this.stringValue = stringValue;
        this.intValue = intValue;
        this.booleanValue = booleanValue;
    }

    // API

    @JsonProperty("strVal")
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
