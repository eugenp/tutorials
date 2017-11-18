package com.baeldung.jackson.deserialization.jsonanysetter;

import com.baeldung.jackson.domain.Author;
import com.baeldung.jackson.domain.Item;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private Map<Author, Item> stock = new HashMap<>();

    private Map<String, Float> countryDeliveryCost = new HashMap<>();

    @JsonIgnore
    public Map<Author, Item> getStock() {
        return stock;
    }

    public Map<String, Float> getCountryDeliveryCost() {
        return countryDeliveryCost;
    }

    @JsonAnySetter
    public void addCountryDeliveryCost(String country, Float cost) {
        countryDeliveryCost.put(country, cost);
    }
}
