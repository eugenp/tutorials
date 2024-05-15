package com.baeldung.jacksonannotation.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
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
