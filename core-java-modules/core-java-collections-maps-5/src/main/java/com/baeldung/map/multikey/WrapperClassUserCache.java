package com.baeldung.map.multikey;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WrapperClassUserCache {
    private Map<CacheKey, User> cache = new HashMap<>();

    public User getById(CacheKey key) {
        return cache.get(key);
    }

    public void storeById(CacheKey key, User user) {
        cache.put(key, user);
    }

    public static class CacheKey {
        private final Object value;

        public CacheKey(String value) {
            this.value = value;
        }

        public CacheKey(Long value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return value.equals(cacheKey.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
