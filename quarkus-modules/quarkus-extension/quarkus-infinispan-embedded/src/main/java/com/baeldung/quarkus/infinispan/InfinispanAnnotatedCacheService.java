package com.baeldung.quarkus.infinispan;

import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class InfinispanAnnotatedCacheService {

	public static final String CACHE_NAME = "anotherCache";

	@CacheName(CACHE_NAME)
    @Inject
    Cache anotherCache;
			
    @CacheResult(cacheName = CACHE_NAME)
    String getValueFromCache(String key) {
		return key + "Value";
	}
}
