package com.baeldung.gson.entities;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName(value = "name")
    private String countryName;
    @SerializedName(value = "capital")
    private String countryCapital;
    @SerializedName(value = "continent")
    private String continentName;

    public Country(String countryName, String countryCapital, String continentName) {
        this.countryName = countryName;
        this.countryCapital = countryCapital;
        this.continentName = continentName;
    }
    @Override
    public String toString() {
        return "Country{" +
                "countryName='" + countryName + '\'' +
                ", countryCapital='" + countryCapital + '\'' +
                ", continentName='" + continentName + '\'' +
                '}';
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCapital() {
        return countryCapital;
    }

    public void setCountryCapital(String countryCapital) {
        this.countryCapital = countryCapital;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }
}
