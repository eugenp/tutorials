package com.baeldung.architecture.hexagonal.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCache implements Cache {
    private final Map<String, String> store;

    public InMemoryCache() {
        this.store = new HashMap<>();
    }

    @Override
    public void put(String key, String value) {
        store.put(key, value);
    }

    @Override
    public Optional<String> get(String key) {
        return Optional.ofNullable(store.get(key));
    }
}
