package com.baeldung.map.multikey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MultipleMapsUserCacheUnitTest {
    private MultipleMapsUserCache cache = new MultipleMapsUserCache();

    @BeforeEach
    public void setup() {
        cache.storeById("a", new User("User A"));
        cache.storeById("b", new User("User B"));
        cache.storeById(3L, new User("User 3"));
        cache.storeById(4L, new User("User 4"));
    }

    @Test
    public void getByString() {
        User user = cache.getById("b");
        assertNotNull(user);
        assertEquals("User B", user.getName());
    }

    @Test
    public void getByLong() {
        User user = cache.getById(4L);
        assertNotNull(user);
        assertEquals("User 4", user.getName());
    }
}