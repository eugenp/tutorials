package com.baeldung.spring.drools.model;

import lombok.Data;

@Data
public class TaxiRide {

    private Boolean isNightSurcharge;
    private Long distanceInMile;

}