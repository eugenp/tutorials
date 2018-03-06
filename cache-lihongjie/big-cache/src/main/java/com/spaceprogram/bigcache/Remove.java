package com.spaceprogram.bigcache;

import java.util.concurrent.Callable;

/**
 * User: treeder
 * Date: Jan 13, 2009
 * Time: 7:47:45 PM
 */
public class Remove implements Callable<Object> {

    private BigCache cache;

    private String key;

    public Remove(BigCache cache, String key) {
        this.cache = cache;
        this.key = key;
    }

    public Object call() throws Exception {
        cache.remove(key);
        return null;
    }
}
