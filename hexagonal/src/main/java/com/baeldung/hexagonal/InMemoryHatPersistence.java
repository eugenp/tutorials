package com.baeldung.hexagonal;

import java.util.HashMap;
import java.util.Map;

class InMemoryHatPersistence implements HatPersistence {
    private Map<String, String> hats = new HashMap<>();
 
    @Override
    public void save(String name, String hat) {
        hats.put(name, hat);
    }
 
    @Override
    public String findByName(String name) {
        return hats.get(name);
    }

}
