package com.baeldung.spring.data.redis_ttl.configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.baeldung.spring.data.redis_ttl.entity.Book;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Book> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Book> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration shortDurationCache = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60));

        RedisCacheConfiguration longDurationCache = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(20));

        // Setting the expiration time of the cache
        Map<String, RedisCacheConfiguration> configurations = new HashMap<>();
        configurations.put("subscriber", shortDurationCache);
        configurations.put("gatekeeper", longDurationCache);

        // RedisCacheManager redisCacheManager =
        // RedisCacheManager.builder(connectionFactory)
        // .transactionAware()
        // .cacheDefaults(config).build();

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .withInitialCacheConfigurations(configurations).build();

        return redisCacheManager;
    }

}