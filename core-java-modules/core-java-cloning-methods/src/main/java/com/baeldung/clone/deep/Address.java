package com.baeldung.clone.deep;

public class Address implements Cloneable {

    private String houseNumber;
    private String street;
    private String zipCode;
    private String city;
    private String country;

    public Address() {
        super();
    }

    public Address(String houseNumber, String street, String zipCode, String city, String country) {
        super();
        this.houseNumber = houseNumber;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public Address(Address address) {
        super();
        this.houseNumber = address.getHouseNumber();
        this.street = address.getStreet();
        this.zipCode = address.getZipCode();
        this.city = address.getCity();
        this.country = address.getCountry();
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" + "houseNumber='" + houseNumber + '\'' + ", street='" + street + '\'' + ", zipCode='" + zipCode + '\'' + ", city='" + city + '\'' + ", country='" + country + '\'' + '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
