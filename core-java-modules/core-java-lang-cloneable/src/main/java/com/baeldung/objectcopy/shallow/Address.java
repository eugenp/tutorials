package com.baeldung.objectcopy.shallow;

public class Address {
    private String addressLine;
    private String postCode;

    public Address(String addressLine, String postCode) {
        this.addressLine = addressLine;
        this.postCode = postCode;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}