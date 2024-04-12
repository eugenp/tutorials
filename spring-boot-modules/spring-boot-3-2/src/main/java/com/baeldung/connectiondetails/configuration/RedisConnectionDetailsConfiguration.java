package com.baeldung.connectiondetails.configuration;

import org.springframework.boot.autoconfigure.data.redis.RedisConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("redis")
public class RedisConnectionDetailsConfiguration {
    @Bean
    @Primary
    public RedisConnectionDetails getRedisCacheConnection() {
        return new RedisCacheConnectionDetails();
    }
}
