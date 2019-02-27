package com.baeldung.cachetest.config;

import java.math.BigDecimal;
import java.time.Duration;

import javax.cache.CacheManager;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

	private static final int ON_HEAP_CACHE_SIZE_ENTRIES = 2;
	private static final int OFF_HEAP_CACHE_SIZE_MB = 10;
	private static final int CACHE_EXPIRY_SECONDS = 30;

	@Bean
	public JCacheManagerCustomizer jcacheManagerCustomizer() {
		return new JCacheManagerCustomizer() {

			@Override
			public void customize(CacheManager cacheManager) {
				ResourcePools resourcePools = ResourcePoolsBuilder.newResourcePoolsBuilder()
						.heap(ON_HEAP_CACHE_SIZE_ENTRIES, EntryUnit.ENTRIES)
						.offheap(OFF_HEAP_CACHE_SIZE_MB, MemoryUnit.MB).build();

				CacheEventListenerConfigurationBuilder eventLoggerConfig = CacheEventListenerConfigurationBuilder
						.newEventListenerConfiguration(new CacheEventLogger(), EventType.CREATED, EventType.EXPIRED)
						.unordered().asynchronous();

				CacheConfiguration<?, ?> cacheConfiguration = CacheConfigurationBuilder
						.newCacheConfigurationBuilder(Long.class, BigDecimal.class, resourcePools)
						.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(CACHE_EXPIRY_SECONDS)))
						.add(eventLoggerConfig).build();

				cacheManager.createCache("squareCache",
						Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfiguration));

			}
		};
	}

}
