package com.baeldung.jackson.deserialization.enums.jsonproperty;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Distance {

    @JsonProperty("distance-in-km")
    KILOMETER("km", 1000), 
    
    @JsonProperty("distance-in-miles")
    MILE("miles", 1609.34),
    
    @JsonProperty("distance-in-meters")
    METER("meters", 1), 
    
    @JsonProperty("distance-in-inches")
    INCH("inches", 0.0254), 
    
    @JsonProperty("distance-in-cm")
    CENTIMETER("cm", 0.01), 
    
    @JsonProperty("distance-in-mm")
    MILLIMETER("mm", 0.001);

    private String unit;
    private double meters;

    private Distance(String unit, double meters) {
        this.unit = unit;
        this.meters = meters;
    }

    public void setMeters(double meters) {
        this.meters = meters;
    }
    
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


