package com.baeldung.collectionutilsguide.model;

public class Address {

    String locality;
    String city;
    String zip;

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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Address [locality=").append(locality).append(", city=").append(city).append(", zip=").append(zip).append("]");
        return builder.toString();
    }

    public Address(String locality, String city, String zip) {
        super();
        this.locality = locality;
        this.city = city;
        this.zip = zip;
    }

}
