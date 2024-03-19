package com.baeldung.deepcopy;

public class UKAddress {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
    private String postCode;

    public UKAddress() {
    }

    public UKAddress(String addressLine1, String addressLine2, String city, String country, String postCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
    }

    public static UKAddress newInstance(UKAddress address) {
        UKAddress copy = new UKAddress();
        copy.addressLine1 = address.addressLine1;
        copy.addressLine2 = address.addressLine2;
        copy.city = address.city;
        copy.country = address.country;
        copy.postCode = address.postCode;

        return copy;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
