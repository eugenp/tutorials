package com.baeldung.jersey.service;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.jersey.server.model.Fruit;

public class SimpleStorageService {

    private static final Map<String, Fruit> fruits = new HashMap<String, Fruit>();

    public static void storeFruit(final Fruit fruit) {
        fruits.put(fruit.getName(), fruit);
    }
    
    public static Fruit findByName(final String name) {
        return fruits.entrySet()
            .stream()
            .filter(map -> name.equals(map.getKey()))
            .map(map -> map.getValue())
            .findFirst()
            .get();
    }

}
