package com.baeldung.quarkus.infinispan;

import io.quarkiverse.infinispan.embedded.Embedded;
import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheInvalidateAll;
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
	
	@Embedded(CACHE_NAME)
	@Inject
	org.infinispan.Cache<String, String> embeddedCache;
			
    @CacheResult(cacheName = CACHE_NAME)
    String getValueFromCache(String key) {
    	// simulate a long running computation
    	try {
    		System.out.println("getting value for "+ key);
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace(System.err);
		}
		return key + "Value";
	}
    
    public org.infinispan.Cache<String, String> getEmbeddedCache() {
		return embeddedCache;
	}
    
    public Cache getQuarkusCache() {
    	return anotherCache;
    }
    
    @CacheInvalidateAll(cacheName = CACHE_NAME)
    public void clearAll() {
		
	}
    
    @CacheInvalidate(cacheName = CACHE_NAME)
    public void clear(String key) {
		
	}
}
