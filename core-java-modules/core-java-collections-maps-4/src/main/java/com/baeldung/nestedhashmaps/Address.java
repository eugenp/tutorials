package com.baeldung.nestedhashmaps;

public class Address {

    private Integer addressId;
    private String addressLocation;

    public Address() {
    }

    public Address(Integer addressId, String addressLocation) {
        this.addressId = addressId;
        this.addressLocation = addressLocation;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(String addressLocation) {
        this.addressLocation = addressLocation;
    }

}
