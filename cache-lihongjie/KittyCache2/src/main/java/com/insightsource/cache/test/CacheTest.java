package com.insightsource.cache.test;

import org.junit.Assert;
import org.junit.Test;

import com.insightsource.cache.KittyCache;

public class CacheTest {
    @Test
    public void basicOps() {
        String s = "This is a string I want to cache...";
        KittyCache cache = new KittyCache(100);
        cache.put("x", s, 500);

        Assert.assertEquals(s, cache.get("x"));
        Assert.assertEquals(1, cache.size());

        Object o = cache.remove("x");
        Assert.assertEquals(o, s);

        Assert.assertEquals(0, cache.size());
        Assert.assertEquals(0, cache.mapSize());
    }

    @Test
    public void testOverage() {
        KittyCache cache = new KittyCache(100);
        for (int i = 0; i < 110; i++) {
            cache.put(i + "", i, 500);
        }
        Assert.assertEquals(100, cache.size());
        Assert.assertEquals(100, cache.mapSize());
        Assert.assertEquals(100, cache.queueSize());
    }

}
