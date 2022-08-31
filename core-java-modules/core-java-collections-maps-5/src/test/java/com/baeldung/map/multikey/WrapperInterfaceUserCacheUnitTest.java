package com.baeldung.map.multikey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WrapperInterfaceUserCacheUnitTest {
    private WrapperInterfaceUserCache cache = new WrapperInterfaceUserCache();

    @BeforeEach
    public void setup() {
        cache.storeById(new WrapperInterfaceUserCache.StringCacheKey("a"), new User("User A"));
        cache.storeById(new WrapperInterfaceUserCache.StringCacheKey("b"), new User("User B"));
        cache.storeById(new WrapperInterfaceUserCache.LongCacheKey(3L), new User("User 3"));
        cache.storeById(new WrapperInterfaceUserCache.LongCacheKey(4L), new User("User 4"));
    }

    @Test
    public void getByString() {
        User user = cache.getById(new WrapperInterfaceUserCache.StringCacheKey("b"));
        assertNotNull(user);
        assertEquals("User B", user.getName());
    }

    @Test
    public void getByLong() {
        User user = cache.getById(new WrapperInterfaceUserCache.LongCacheKey(4L));
        assertNotNull(user);
        assertEquals("User 4", user.getName());
    }
}