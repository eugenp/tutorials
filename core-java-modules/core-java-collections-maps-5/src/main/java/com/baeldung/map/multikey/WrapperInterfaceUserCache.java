package com.baeldung.map.multikey;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WrapperInterfaceUserCache {
    private Map<CacheKey, User> cache = new HashMap<>();

    public User getById(CacheKey key) {
        return cache.get(key);
    }

    public void storeById(CacheKey key, User user) {
        cache.put(key, user);
    }

    public interface CacheKey {
    }

    public static class StringCacheKey implements CacheKey{
        private final String value;

        public StringCacheKey(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StringCacheKey that = (StringCacheKey) o;
            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static class LongCacheKey implements CacheKey {
        private final Long value;

        public LongCacheKey(Long value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LongCacheKey that = (LongCacheKey) o;
            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
