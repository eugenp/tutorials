package com.baeldung.web.log.data;

public class RateCard {

    private String nightSurcharge;
    private String ratePerMile;

    public RateCard() {
        nightSurcharge = "Extra $ 100";
        ratePerMile = "$ 10 Per Mile";
    }


    public String getNightSurcharge() {
        return nightSurcharge;
    }

    public void setNightSurcharge(String nightSurcharge) {
        this.nightSurcharge = nightSurcharge;
    }

    public String getRatePerMile() {
        return ratePerMile;
    }

    public void setRatePerMile(String ratePerMile) {
        this.ratePerMile = ratePerMile;
    }


}
