package com.baeldung.arraycopy.model;

public class Address implements Cloneable {
    private String country;
    private String state;
    private String city;
    private String street;
    private String zipcode;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        Address address = new Address();
        address.setCity(this.city);
        address.setCountry(this.country);
        address.setState(this.state);
        address.setStreet(this.street);
        address.setZipcode(this.zipcode);
        return address;
    }
}
