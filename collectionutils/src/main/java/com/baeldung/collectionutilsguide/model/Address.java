package com.baeldung.collectionutilsguide.model;

public class Address {

    String locality;
    String city;

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Address [locality=").append(locality).append(", city=").append(city).append("]");
        return builder.toString();
    }

    public Address(String locality, String city) {
        super();
        this.locality = locality;
        this.city = city;
    }

}
