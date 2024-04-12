package com.baeldung.map.multikey;

import java.util.HashMap;
import java.util.Map;

public class BaseClassUserCache {
    private final Map<Object, User> cache = new HashMap<>();

    public User getById(String id) {
        return cache.get(id);
    }

    public User getById(Long id) {
        return cache.get(id);
    }

    public void storeById(String id, User user) {
        cache.put(id, user);
    }

    public void storeById(Long id, User user) {
        cache.put(id, user);
    }
}
