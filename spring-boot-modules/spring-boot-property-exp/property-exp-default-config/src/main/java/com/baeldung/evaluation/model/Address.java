package com.baeldung.evaluation.model;

public class  Address {
    
    private String street;
    private String country;
    
    
    public Address() {
        super();
    }

    public Address(String street, String country) {
        super();
        this.street = street;
        this.country = country;
    }
    
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
}

