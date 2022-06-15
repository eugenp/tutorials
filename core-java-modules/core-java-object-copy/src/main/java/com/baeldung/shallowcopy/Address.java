package com.baeldung.shallowcopy;

public class Address {

    private String addressLine;
    private String city;
    private String zipCode;

    public Address(String addressLine, String city, String zipCode) {
        this.addressLine = addressLine;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

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
}
