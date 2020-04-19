package com.baeldung.javers.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String address;
    private Integer zipCode;

    public Address(String address, Integer zipCode) {
        this.address = address;
        this.zipCode = zipCode;
    }

    public Address() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }
}
