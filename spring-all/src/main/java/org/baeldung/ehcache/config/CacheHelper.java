package org.baeldung.ehcache.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CacheHelper {

    private CacheManager cacheManager;
    private Cache<Integer, Integer> squareNumberCache;

    public CacheHelper() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("squaredNumber", CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, Integer.class, ResourcePoolsBuilder.heap(10))).build();
        cacheManager.init();

        squareNumberCache = cacheManager.getCache("squaredNumber", Integer.class, Integer.class);
    }

    public Cache<Integer, Integer> getSquareNumberCache() {
        return squareNumberCache;
    }

}
