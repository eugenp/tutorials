package com.baeldung.jackson.enums.withEnum;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DistanceEnumWithValue {
    KILOMETER("km", 1000), MILE("miles", 1609.34), METER("meters", 1), INCH("inches", 0.0254), CENTIMETER("cm", 0.01), MILLIMETER("mm", 0.001);

    private String unit;
    private final double meters;

    private DistanceEnumWithValue(String unit, double meters) {
        this.unit = unit;
        this.meters = meters;
    }

    @JsonValue
    public double getMeters() {
        return meters;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}