package com.baeldung.testing.easymock;

import java.math.BigDecimal;

public class Location {
    private String name;
    private BigDecimal minimumTemperature;
    private BigDecimal maximumTemparature;
    
    public Location(String name) {
        this.name = name;
    }   
    
    public String getName() {
        return name;
    }
    
    public BigDecimal getMinimumTemperature() {
        return minimumTemperature;
    }
    
    public void setMinimumTemperature(BigDecimal minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }
    
    public BigDecimal getMaximumTemparature() {
        return maximumTemparature;
    }
    
    public void setMaximumTemparature(BigDecimal maximumTemparature) {
        this.maximumTemparature = maximumTemparature;
    }
}
