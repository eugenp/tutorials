package com.baeldung.guava.mapmaker;

import com.google.common.collect.MapMaker;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;

public class GuavaMapMakerUnitTest {
    @Test
    public void whenCreateCaches_thenCreated() {
        ConcurrentMap<User, Session> sessionCache = new MapMaker().makeMap();
        assertNotNull(sessionCache);

        ConcurrentMap<User, Profile> profileCache = new MapMaker().makeMap();
        assertNotNull(profileCache);

        User userA = new User(1, "UserA");

        sessionCache.put(userA, new Session(100));
        Assert.assertThat(sessionCache.size(), equalTo(1));

        profileCache.put(userA, new Profile(1000, "Personal"));
        Assert.assertThat(profileCache.size(), equalTo(1));
    }

    @Test
    public void whenCreateCacheWithInitialCapacity_thenCreated() {
        ConcurrentMap<User, Profile> profileCache = new MapMaker().initialCapacity(100).makeMap();
        assertNotNull(profileCache);
    }

    @Test
    public void whenCreateCacheWithConcurrencyLevel_thenCreated() {
        ConcurrentMap<User, Session> sessionCache = new MapMaker().concurrencyLevel(10).makeMap();
        assertNotNull(sessionCache);
    }

    @Test
    public void whenCreateCacheWithWeakKeys_thenCreated() {
        ConcurrentMap<User, Session> sessionCache = new MapMaker().weakKeys().makeMap();
        assertNotNull(sessionCache);
    }

    @Test
    public void whenCreateCacheWithWeakValues_thenCreated() {
        ConcurrentMap<User, Profile> profileCache = new MapMaker().weakValues().makeMap();
        assertNotNull(profileCache);
    }
}
