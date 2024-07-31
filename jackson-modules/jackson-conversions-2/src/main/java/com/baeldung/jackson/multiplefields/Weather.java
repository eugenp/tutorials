package com.baeldung.jackson.multiplefields;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {

    @JsonProperty("location")
    @JsonAlias("place")
    private String location;

    @JsonProperty("temp")
    @JsonAlias("temperature")
    private int temp;

    @JsonProperty("outlook")
    @JsonAlias("weather")
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
