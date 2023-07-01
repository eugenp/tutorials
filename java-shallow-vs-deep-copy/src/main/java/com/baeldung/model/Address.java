package com.baeldung.model;

public class Address implements Cloneable {
    String city;
    String zipCode;

    // we can use @Getter and @Setter instead
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Address deepCopy() {
        Address addressCopy = new Address();
        addressCopy.city = this.city;
        addressCopy.zipCode = this.zipCode;
        return addressCopy;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
