package com.insightsource.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QCacheTest {
    QCache<String, String> cache;

    @Before
    public void setup() {
        cache = new QCacheImpl<String, String>();
    }

    @Test
    public void testQCacheUsage() {
        cache.put("first", "aaaaaaaaaaaaa");
        cache.put("second", "bbbbbbbbbbbb");

        Assert.assertEquals(cache.get("first"), "aaaaaaaaaaaaa");
    }
}
