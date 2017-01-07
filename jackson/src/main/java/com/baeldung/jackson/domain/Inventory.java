package com.baeldung.jackson.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class Inventory {

    private Map<Author, Item> stock;

    private Map<String, Float> countryDeliveryCost = new HashMap<>();

    public Map<Author, Item> getStock() {
        return stock;
    }

    public void setStock(Map<Author, Item> stock) {
        this.stock = stock;
    }

    public Map<String, Float> getCountryDeliveryCost() {
        return countryDeliveryCost;
    }
}
