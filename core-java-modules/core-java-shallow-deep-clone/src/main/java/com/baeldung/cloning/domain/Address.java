package com.baeldung.cloning.domain;

public class Address implements Cloneable {

    public Address(String street, String zip, String apt, String city) {

        this.street = street;
        this.zip = zip;
        this.apt = apt;
        this.city = city;

    }

     private String street;
    private String zip;
    private String apt;

    private String city;

    public String getApt() {
        return apt;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public void setApt(String apt) {
        this.apt = apt;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public Address clone() throws CloneNotSupportedException {
        return (Address)super.clone();
    }

    @Override
    public String toString() {
        return "{" +
                "street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", apt='" + apt + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
