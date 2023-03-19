package com.baeldung.shallowvsdeepcopy;

class Address implements Cloneable{

    private String streetName;
    private String zipCode;
    private String cityName;
    private String country;

    public Address(String streetName, String zipCode, String cityName, String country) {
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.cityName = cityName;
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public Address clone() {
        try {
            Address clone = (Address) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}