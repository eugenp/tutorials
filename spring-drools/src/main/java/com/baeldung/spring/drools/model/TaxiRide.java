package com.baeldung.spring.drools.model;

public class TaxiRide {
    
    private Boolean bNightSurcharge;
    private Long distanceInMile;

    public Boolean getbNightSurcharge() {
        return bNightSurcharge;
    }

    public void setbNightSurcharge(Boolean bNightSurcharge) {
        this.bNightSurcharge = bNightSurcharge;
    }

    public Long getDistanceInMile() {
        return distanceInMile;
    }

    public void setDistanceInMile(Long distanceInMile) {
        this.distanceInMile = distanceInMile;
    }

}
