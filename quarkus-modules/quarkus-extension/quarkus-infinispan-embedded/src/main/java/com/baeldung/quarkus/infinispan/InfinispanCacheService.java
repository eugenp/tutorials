package com.baeldung.quarkus.infinispan;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.infinispan.Cache;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.EmbeddedCacheManager;

//import io.quarkus.cache.CacheResult;
import jakarta.inject.Inject;

@ApplicationScoped
public class InfinispanCacheService {

	public static final String CACHE_NAME = "demoCache";
	
	@Inject
	EmbeddedCacheManager cacheManager;

	private Cache<String, String> demoCache;
	

	@PostConstruct
	void init() {
		Configuration cacheConfig = new ConfigurationBuilder().clustering().cacheMode(CacheMode.LOCAL).memory()
				.maxCount(10).expiration().lifespan(600, TimeUnit.MILLISECONDS).persistence().passivation(true).build();

		demoCache = cacheManager.administration().withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
				.getOrCreateCache(CACHE_NAME, cacheConfig);
//		demoCache = cacheManager.getCache(CACHE_NAME);
	}

	
	public void put(String key, String value) {
		demoCache.put(key, value);
	}

	public String get(String key) {
		return demoCache.get(key);
	}
//
//    @CacheResult(cacheName = CACHE_NAME)
//    String getValueFromCache(String key) {
//		return key + "Value";
//	}

	public void bulkPut(Map<String, String> entries) {
		demoCache.putAll(entries);
	}

	public int size() {
		return demoCache.size();
	}

	public void clear() {
		demoCache.clear();
	}

	public boolean isPassivationEnabled() {
		return cacheManager.getCacheConfiguration(CACHE_NAME).persistence().passivation();
	}
	
	public void stop() {
		cacheManager.stop();
	}
}
