package com.baeldung.hexagon.architecture.entity;

public class CountryResponse {
    private Country country;

    public CountryResponse(final Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }
}
