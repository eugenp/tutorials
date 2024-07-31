package com.baeldung.shallowdeepcopy;

public class Address implements Cloneable {

    String street;
    String city;
    
    Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    Address(Address original) {
        this(original.getStreet(), original.getCity());
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}