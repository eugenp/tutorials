package com.baeldung.spring.drools.model;

import lombok.Data;

@Data
public class Fare {

    private Long nightSurcharge=0L;
    private Long rideFare=0L;

    public Long getTotalFare() {
        return nightSurcharge + rideFare;
    }

}