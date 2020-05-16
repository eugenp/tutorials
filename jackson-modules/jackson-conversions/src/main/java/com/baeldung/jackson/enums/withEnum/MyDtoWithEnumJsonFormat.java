package com.baeldung.jackson.enums.withEnum;

public class MyDtoWithEnumJsonFormat {

    private String stringValue;
    private int intValue;
    private boolean booleanValue;
    private DistanceEnumWithJsonFormat distanceType;

    public MyDtoWithEnumJsonFormat() {
        super();
    }

    public MyDtoWithEnumJsonFormat(final String stringValue, final int intValue, final boolean booleanValue, final DistanceEnumWithJsonFormat type) {
        super();

        this.stringValue = stringValue;
        this.intValue = intValue;
        this.booleanValue = booleanValue;
        this.distanceType = type;
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

    public DistanceEnumWithJsonFormat getDistanceType() {
        return distanceType;
    }

    public void setDistanceType(final DistanceEnumWithJsonFormat type) {
        this.distanceType = type;
    }

}
