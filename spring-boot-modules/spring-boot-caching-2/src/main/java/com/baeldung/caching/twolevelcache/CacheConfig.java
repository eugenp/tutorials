package com.baeldung.caching.twolevelcache;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager caffeineCacheManager(CaffeineCache caffeineCache) {
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(caffeineCache));
        return manager;
    }

    @Bean
    public CaffeineCache caffeineCacheConfig() {
        return new CaffeineCache("customerCache", Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(3))
            .initialCapacity(1)
            .maximumSize(2000)
            .build());
    }

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory, RedisCacheConfiguration redisCacheConfiguration) {
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
            .withCacheConfiguration("customerCache", redisCacheConfiguration)
            .build();
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(5))
            .disableCachingNullValues()
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public CacheInterceptor cacheInterceptor(CacheManager caffeineCacheManager, CacheOperationSource cacheOperationSource) {
        CacheInterceptor interceptor = new CustomerCacheInterceptor(caffeineCacheManager);
        interceptor.setCacheOperationSources(cacheOperationSource);
        return interceptor;
    }

    @Bean
    public CacheOperationSource cacheOperationSource() {
        return new AnnotationCacheOperationSource();
    }
}