package com.baeldung.map.multikey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WrapperClassUserCacheUnitTest {
    private WrapperClassUserCache cache = new WrapperClassUserCache();

    @BeforeEach
    public void setup() {
        cache.storeById(new WrapperClassUserCache.CacheKey("a"), new User("User A"));
        cache.storeById(new WrapperClassUserCache.CacheKey("b"), new User("User B"));
        cache.storeById(new WrapperClassUserCache.CacheKey(3L), new User("User 3"));
        cache.storeById(new WrapperClassUserCache.CacheKey(4L), new User("User 4"));
    }

    @Test
    public void getByString() {
        User user = cache.getById(new WrapperClassUserCache.CacheKey("b"));
        assertNotNull(user);
        assertEquals("User B", user.getName());
    }

    @Test
    public void getByLong() {
        User user = cache.getById(new WrapperClassUserCache.CacheKey(4L));
        assertNotNull(user);
        assertEquals("User 4", user.getName());
    }
}