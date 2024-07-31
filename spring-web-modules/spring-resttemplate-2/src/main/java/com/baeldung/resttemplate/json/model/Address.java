package com.baeldung.resttemplate.json.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {
    private final String addressLine1;
    private final String addressLine2;
    private final String town;
    private final String postCode;

    @JsonCreator
    public Address(
      @JsonProperty("addressLine1") String addressLine1,
      @JsonProperty("addressLine2") String addressLine2,
      @JsonProperty("town") String town,
      @JsonProperty("postCode") String postCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.town = town;
        this.postCode = postCode;
    }
    public String getPostCode() {
        return postCode;
    }
}
