package com.baeldung.gson.entities;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName(value = "location", alternate = "place")
    private String location;

    @SerializedName(value = "temp", alternate = "temperature")
    private int temp;

    @SerializedName(value = "outlook", alternate = "weather")
    private String outlook;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getOutlook() {
        return outlook;
    }

    public void setOutlook(String outlook) {
        this.outlook = outlook;
    }

}
