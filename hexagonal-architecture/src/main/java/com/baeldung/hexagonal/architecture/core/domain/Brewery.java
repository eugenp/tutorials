package com.baeldung.hexagonal.architecture.core.domain;

public class Brewery {
    private int id;
    private String name;
    private String breweryType;
    private String city;
    private String state;
    private String country;
    private String websiteUrl;

    public String getBrewTypes() {
        if (this.breweryType.equals("brewpub")) {
            return "Brown Ale, Pale Ale, India Pale Ale";
        }
        return "Porter, Stout, Belgian-Style Ale";
    }

    public boolean acceptsBitcoinAsPayMethod() {
        if (this.breweryType.equals("micro")) {
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
