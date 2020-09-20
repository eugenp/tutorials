package com.baeldung.resttemplate.users.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {
    private String addressLine1;
    private String addressLine2;
    private String town;
    private String postCode;

    public Address(String addressLine1, String addressLine2, String town, String postCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.town = town;
        this.postCode = postCode;
    }

    public Address() {
        super();
    }
    @JsonProperty("addressLine1")
    public String getAddressLine1() {
        return addressLine1;
    }
    @JsonProperty("addressLine1")
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    @JsonProperty("addressLine2")
    public String getAddressLine2() {
        return addressLine2;
    }
    @JsonProperty("addressLine2")
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @JsonProperty("town")
    public String getTown() {
        return town;
    }

    @JsonProperty("town")
    public void setTown(String town) {
        this.town = town;
    }

    @JsonProperty("postCode")
    public String getPostCode() {
        return postCode;
    }

    @JsonProperty("postCode")
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                       "addressLine1='" + addressLine1 + '\'' +
                       ", addressLine2='" + addressLine2 + '\'' +
                       ", town='" + town + '\'' +
                       ", postCode='" + postCode + '\'' +
                       '}';
    }
}
