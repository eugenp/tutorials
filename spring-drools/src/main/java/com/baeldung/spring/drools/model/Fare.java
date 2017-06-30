package com.baeldung.spring.drools.model;

public class Fare {

    private Long nightSurcharge;
    private Long rideFare;
    private Long totalFare;

    public Fare() {
        nightSurcharge = 0L;
        rideFare = 0L;
        totalFare = 0L;
    }

    public Long getNightSurcharge() {
        return nightSurcharge;
    }

    public void setNightSurcharge(Long nightSurcharge) {
        this.nightSurcharge = nightSurcharge;
    }

    public Long getRideFare() {
        return rideFare;
    }

    public void setRideFare(Long rideFare) {
        this.rideFare = rideFare;
    }

    public Long getTotalFare() {
        return nightSurcharge + rideFare;
    }

    public void setTotalFare(Long totalFare) {
        this.totalFare = totalFare;
    }

}
