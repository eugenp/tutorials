package com.baeldung.jackson.enums;

import com.baeldung.jackson.serialization.DistanceSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Use  @JsonFormat to handle representation of Enum as JSON (available since Jackson 2.1.2)
 * Use @JsonSerialize to configure a custom Jackson serializer
 */
// @JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = DistanceSerializer.class)
public enum Distance {
    KILOMETER("km", 1000), MILE("miles", 1609.34), METER("meters", 1), INCH("inches", 0.0254), CENTIMETER("cm", 0.01), MILLIMETER("mm", 0.001);

    private String unit;
    private final double meters;

    private Distance(String unit, double meters) {
        this.unit = unit;
        this.meters = meters;
    }

    /**
     * Use @JsonValue to control marshalling output for an enum
     */
    // @JsonValue
    public double getMeters() {
        return meters;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Usage example: Distance.MILE.convertFromMeters(1205.5);
     */
    public double convertFromMeters(double distanceInMeters) {
        return distanceInMeters / meters;

    }

    /**
     * Usage example: Distance.MILE.convertToMeters(0.5);
     */
    public double convertToMeters(double distanceInMeters) {
        return distanceInMeters * meters;
    }

}