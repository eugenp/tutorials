package com.baeldung.multiplecachemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;

import com.baeldung.multiplecachemanager.config.MultipleCacheResolver;
import com.github.benmanes.caffeine.cache.Caffeine;

@SpringBootApplication
@EnableCaching
public class MultiplecachemanagerApplication extends CachingConfigurerSupport {

    public static void main(String[] args) {
        SpringApplication.run(MultiplecachemanagerApplication.class, args);
    }

    @Bean
    // @Primary
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("customers", "orders");
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .initialCapacity(200)
            .maximumSize(500)
            .weakKeys()
            .recordStats());
        return cacheManager;
    }

    @Bean
    public CacheManager alternateCacheManager() {
        return new ConcurrentMapCacheManager("customerOrders", "orderprice");
    }

    @Bean
    public CacheResolver cacheResolver() {
        return new MultipleCacheResolver(alternateCacheManager(), cacheManager());
    }
}
