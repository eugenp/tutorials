package com.baeldung.jackson.deserialization.jsonanysetter;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class Inventory {

    private Map<String, Float> countryDeliveryCost = new HashMap<>();

    public Map<String, Float> getCountryDeliveryCost() {
        return countryDeliveryCost;
    }

    @JsonAnySetter
    public void addCountryDeliveryCost(String country, Float cost) {
        countryDeliveryCost.put(country, cost);
    }
}
