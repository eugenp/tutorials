package com.baeldung.map.multikey;

import java.util.HashMap;
import java.util.Map;

public class MultipleMapsUserCache {
    private final Map<String, User> stringCache = new HashMap<>();
    private final Map<Long, User> longCache = new HashMap<>();

    public User getById(String id) {
        return stringCache.get(id);
    }

    public User getById(Long id) {
        return longCache.get(id);
    }

    public void storeById(String id, User user) {
        stringCache.put(id, user);
    }

    public void storeById(Long id, User user) {
        longCache.put(id, user);
    }
}
