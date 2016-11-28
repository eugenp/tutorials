package com.baeldung.jackson.dtos.withEnum;

public class MyDtoWithEnum {

    private String stringValue;
    private int intValue;
    private boolean booleanValue;
    private TypeEnum type;

    public MyDtoWithEnum() {
        super();
    }

    public MyDtoWithEnum(final String stringValue, final int intValue, final boolean booleanValue, final TypeEnum type) {
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

    public TypeEnum getType() {
        return type;
    }

    public void setType(final TypeEnum type) {
        this.type = type;
    }

}
