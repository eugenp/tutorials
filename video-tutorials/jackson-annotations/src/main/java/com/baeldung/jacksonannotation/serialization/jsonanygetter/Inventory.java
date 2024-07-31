package com.baeldung.jacksonannotation.serialization.jsonanygetter;

import com.baeldung.jacksonannotation.domain.Author;
import com.baeldung.jacksonannotation.domain.Item;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class Inventory {

    private String location;

    private Map<Author, Item> stock = new HashMap<>();

    private Map<String, Float> countryDeliveryCost = new HashMap<>();

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @JsonIgnore
    public Map<Author, Item> getStock() {
        return stock;
    }

    @JsonAnyGetter
    public Map<String, Float> getCountryDeliveryCost() {
        return countryDeliveryCost;
    }

}
