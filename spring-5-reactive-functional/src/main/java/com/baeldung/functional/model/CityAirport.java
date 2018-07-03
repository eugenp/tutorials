package com.baeldung.functional.model;

public class CityAirport {

    public CityAirport(){}

    private String cityName;
    private String airportName;
    
    public CityAirport(String cityName, String airportName) {
        super();
        this.cityName = cityName;
        this.airportName = airportName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    @Override
    public String toString() {
        return "CityAirport [cityName=" + cityName + ", airportName=" + airportName + "]";
    }    
}