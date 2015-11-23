package org.baeldung.aop;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleCache {
    private Map<Long, Object> cache = new HashMap<>();

    public void put(Long id, Object value) {
        cache.put(id, value);
    }

    public Object get(Long id) {
        return cache.get(id);
    }
}
