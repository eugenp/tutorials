package com.baeldung.singleton;

import java.util.HashMap;

public class CacheManager {
    private final HashMap<String, Object> map;

    private static CacheManager instance;

    private CacheManager() {
        map = new HashMap<>();
    }

    public static CacheManager getInstance() {
        if(instance == null) {
            instance = new CacheManager();
        }

        return instance;
    }

    public <T> T getValue(String key, Class<T> clazz) {
        return clazz.cast(map.get(key));
    }

    public Object setValue(String key, Object value) {
        return map.put(key, value);
    }
}