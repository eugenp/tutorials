package com.baeldung;

import java.util.concurrent.TimeUnit;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.EmbeddedCacheManager;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

@QuarkusMain
public class InfinispanEmbeddedCacheDemo implements QuarkusApplication {

	@Inject
	private EmbeddedCacheManager cacheManager;

	private Configuration initConfig() {
		Configuration cacheConfig = new ConfigurationBuilder().clustering().cacheMode(CacheMode.LOCAL)
				// Cache eviction: Limit cache to 100 entries.
				.memory().maxCount(100)
				// Cache expiration: Entries live for 60 minutes.
				.expiration().lifespan(60, TimeUnit.SECONDS)
				// Passivation: Write evicted entries to disk.
				.persistence().passivation(true).build();
		return cacheConfig;
	}

	@Override
	public int run(String... args) throws Exception {
        // Define a cache called "demoCache" with the above configurations.
        cacheManager.defineConfiguration("demoCache", initConfig());
        Cache<String, String> demoCache = cacheManager.getCache("demoCache");

        // 3. Build a cache with some data.
        for (int i = 0; i < 10; i++) {
			demoCache.put("key" + i, "value" + i);
		}

        System.out.println("Cache created with initial data: %1$s entries.".formatted(demoCache.size()));

        for (int i = 0; i < 10; i++) {
			System.out.println("key" + i + ": " + demoCache.get("key" + i));
		}
        
        // 4. Cache Expiration:
        System.out.println("Waiting 65 seconds to let entries expire...");
        
        Thread.sleep(65 * 1000);
        
        System.out.println("After expiration, key1: " + demoCache.get("key1")); // Expected to be null

        // 5. Cache Eviction:
        //    Add extra entries to trigger eviction based on the memory limit.
        for (int i = 0; i < 200; i++) {
            demoCache.put("k" + i, "v" + i);
        }
        System.out.println("After eviction test, cache size: " + demoCache.size());

        // 6. Passivation of cache is enabled in the configuration. 
        //    When entries are evicted, they are passivated (written to disk).

        cacheManager.stop();
		return 0;
	}

}
