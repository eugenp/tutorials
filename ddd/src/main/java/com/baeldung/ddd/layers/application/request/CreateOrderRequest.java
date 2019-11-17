package com.baeldung.ddd.layers.application.request;

import com.baeldung.ddd.layers.domain.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderRequest {
    private List<Product> products;

    @JsonCreator
    public CreateOrderRequest(@JsonProperty("products") final List<Product> productList) {
        this.products = new ArrayList<>(productList);
    }

    public List<Product> getProducts() {
        return products;
    }
}
