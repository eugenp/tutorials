package com.baeldung.opentelemetry;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Price price;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
