package com.baeldung.opentelemetry;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Price {

    @JsonProperty("productId")
    private long productId;

    @JsonProperty("price_amount")
    private double priceAmount;

    @JsonProperty("discount")
    private double discount;

}
