package com.baeldung.hexagonal.architecture.application.dtos;

public class BreweryDto {

    private String name;
    private String breweryType;
    private String city;
    private String state;
    private String country;
    private String websiteUrl;
    private String acceptsBitcoin;
    private String availableListOfBeers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreweryType() {
        return breweryType;
    }

    public void setBreweryType(String breweryType) {
        this.breweryType = breweryType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getAcceptsBitcoin() {
        return acceptsBitcoin;
    }

    public void setAcceptsBitcoin(String acceptsBitcoin) {
        this.acceptsBitcoin = acceptsBitcoin;
    }

    public String getAvailableListOfBeers() {
        return availableListOfBeers;
    }

    public void setAvailableListOfBeers(String availableListOfBeers) {
        this.availableListOfBeers = availableListOfBeers;
    }
}
