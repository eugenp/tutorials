package com.baeldung.caching.ttl.service;

import com.baeldung.caching.ttl.config.GuavaCachingConfig;
import com.baeldung.caching.ttl.model.Hotel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GuavaCacheCustomizer {

    @Value("${caching.guava.hotelItemTTL}")
    Integer hotelItemTTL;

    @Bean
    public GuavaCachingConfig<Hotel> hotelGuavaCacheStore() {
        return new GuavaCachingConfig<>(hotelItemTTL, TimeUnit.MILLISECONDS);
    }
}