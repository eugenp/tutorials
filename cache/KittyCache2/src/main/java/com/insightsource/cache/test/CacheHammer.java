package com.insightsource.cache.test;

import com.insightsource.cache.KCache;

import java.util.concurrent.Callable;

public class CacheHammer implements Callable<Long> {
    private KCache cache;

    public CacheHammer(KCache cache) {
        this.cache = cache;
    }

    public Long call() throws Exception {
        long duration = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            cache.put("key" + i, "value" + i, 5000);
        }
        return System.currentTimeMillis() - duration;
    }

}
